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
package it.nextworks.nfvmano.nfvodriver;


import it.nextworks.nfvmano.libs.ifa.common.enums.LcmNotificationType;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.NsIdentifierCreationNotification;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.NsIdentifierDeletionNotification;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.NsLifecycleChangeNotification;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmNotificationInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;


/**
 * This class handles all the LCM notifications received from the NFVO.
 * 
 * @author nextworks
 *
 */
@Service
public class NfvoLcmNotificationsManager implements NfvoLcmNotificationInterface {

	private static final Logger log = LoggerFactory.getLogger(NfvoLcmNotificationsManager.class);
	
	@Autowired
	private NfvoLcmNotificationConsumerInterface engine;
	
	@Autowired
	private NfvoLcmService nfvoLcmService;

	
	public NfvoLcmNotificationsManager() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void notifyNsLifecycleChange(NsLifecycleChangeNotification notification)
			throws MethodNotImplementedException {
		log.debug("Received notification from NFVO driver about a change in the NFV NS lifecycle");
		String nfvNsId = notification.getNsInstanceId();
		String operationId = notification.getLifecycleOperationOccurrenceId();
		String operation = notification.getOperation();
		//acceptable values for operation: NS_INSTANTIATION, NS_SCALING, NS_TERMINATION, NS_UPDATING, NS_HEALING
		log.debug("Notification about operation " + operation + " with ID " + operationId + " related to NFV NS instance " + nfvNsId);
		LcmNotificationType notificationType = notification.getStatus();
		if (notificationType == LcmNotificationType.LIFECYCLE_OPERATION_START) {
			log.debug("Notification about operation starting. This message is not processed");
			return;
		} else if (notificationType == LcmNotificationType.LIFECYCLE_OPERATION_RESULT) {
			log.debug("Notification about operation result. Going to retrieve the status of the operation to verify the status.");
			try {
				OperationStatus operationStatus = nfvoLcmService.getOperationStatus(operationId);
				boolean successful = true;
				if (operationStatus == OperationStatus.FAILED) {
					log.error("The operation has failed on the NFVO.");
					successful = false;
				}
				NsStatusChange changeType = readChangeType(operation);
				log.debug("Forwarding the notification to the engine.");
				engine.notifyNfvNsStatusChange(nfvNsId, changeType, successful);
			} catch (MethodNotImplementedException | NotExistingEntityException | FailedOperationException | MalformattedElementException e) {
				log.error("Error while trying to get operation status: " + e.getMessage());
				log.error("Sending a message about a failure at the NFVO.");
				NsStatusChange changeType = readChangeType(operation);
				engine.notifyNfvNsStatusChange(nfvNsId, changeType, false);
			}
		} else {
			throw new MethodNotImplementedException("Notification type not supported.");
		}
	}

	private NsStatusChange readChangeType(String operation) {
		NsStatusChange changeType = NsStatusChange.NOT_SPECIFIED;
		if (operation.equals("NS_INSTANTIATION")) changeType=NsStatusChange.NS_CREATED;
		else if (operation.equals("NS_TERMINATION")) changeType=NsStatusChange.NS_TERMINATED;
		else if (operation.equals("NS_SCALING")) changeType=NsStatusChange.NS_MODIFIED;
		return changeType;
	}

	@Override
	public void notifyNsIdentifierCreation(NsIdentifierCreationNotification notification)
			throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notifyNsIdentifierObjectDeletion(NsIdentifierDeletionNotification notification)
			throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

}
