/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.sbi.nfvo.polling;


import it.nextworks.nfvmano.sbi.nfvo.interfaces.NfvoLcmNotificationConsumerInterface;
import it.nextworks.nfvmano.sbi.nfvo.interfaces.NfvoLcmNotificationInterface;
import it.nextworks.nfvmano.sbi.nfvo.messages.InternalNsLifecycleChangeNotification;
import it.nextworks.nfvmano.sbi.nfvo.messages.NetworkServiceStatusChange;
import it.nextworks.nfvmano.sbi.nfvo.messages.NfvoLcmNotificationType;
import it.nextworks.nfvmano.sbi.nfvo.messages.OperationStatus;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * This class handles all the LCM notifications received from the NFVO.
 * 
 * @author nextworks
 *
 */
@Service
public class NfvoLcmNotificationsManager implements NfvoLcmNotificationInterface {

	private static final Logger log = LoggerFactory.getLogger(NfvoLcmNotificationsManager.class);
	

	private Map<String, NfvoLcmNotificationConsumerInterface> engine = new HashMap<>();
	

	
	public NfvoLcmNotificationsManager() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void notifyNetworkServiceStatusChange(InternalNsLifecycleChangeNotification notification) {
		log.debug("Received notification from NFVO driver about a change in the NFV NS lifecycle");
		String nfvNsId = notification.getNsInstanceId();
		String operationId = notification.getOperationId();
		NfvoLcmOperationType operation = notification.getOperationType();
		//acceptable values for operation: NS_INSTANTIATION, NS_SCALING, NS_TERMINATION, NS_UPDATING, NS_HEALING
		log.debug("Notification about operation  with ID " + operationId + " related to NFV NS instance " + nfvNsId);
		NfvoLcmNotificationType notificationType = notification.getLcmNotificationType();
		if (notificationType == NfvoLcmNotificationType.LIFECYCLE_OPERATION_START) {
			log.debug("Notification about operation starting. This message is not processed");
			return;
		} else if (notificationType == NfvoLcmNotificationType.LIFECYCLE_OPERATION_RESULT) {
			log.debug("Notification about operation result. Going to retrieve the status of the operation to verify the status.");
			try {
				OperationStatus operationStatus = notification.getDriver().getOperationStatus(operationId);
				boolean successful = true;
				if (operationStatus == OperationStatus.FAILED) {
					log.error("The operation has failed on the NFVO.");
					successful = false;
				}

				NetworkServiceStatusChange changeType = readChangeType(operation);
				log.debug("Forwarding the notification to the engine.");
				engine.get(nfvNsId).notifyNfvNsStatusChange(nfvNsId, changeType, successful);
			} catch (  FailedOperationException e) {
				log.error("Error while trying to get operation status: " + e.getMessage());
				log.error("Sending a message about a failure at the NFVO.");
				NetworkServiceStatusChange changeType = readChangeType(operation);
				engine.get(nfvNsId).notifyNfvNsStatusChange(nfvNsId, changeType, false);
			}
		} else {
			log.error("Notification type not supported.");
		}
	}

	public void registerNotificationConsumer(String nsInstanceId, NfvoLcmNotificationConsumerInterface consumer){
		engine.put(nsInstanceId, consumer);
	}

	private NetworkServiceStatusChange readChangeType(NfvoLcmOperationType operation) {
		log.debug("Change type for operation:"+operation);
		NetworkServiceStatusChange changeType = NetworkServiceStatusChange.NOT_SPECIFIED;
		if (operation.equals(NfvoLcmOperationType.NS_INSTANTIATION)) changeType=NetworkServiceStatusChange.NS_CREATED;
		else if (operation.equals(NfvoLcmOperationType.NS_TERMINATION)) changeType=NetworkServiceStatusChange.NS_TERMINATED;
		else if (operation.equals(NfvoLcmOperationType.NS_SCALING)) changeType=NetworkServiceStatusChange.NS_MODIFIED;
		return changeType;
	}


}
