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

import it.nextworks.nfvmano.sbi.nfvo.interfaces.NfvoLcmNotificationInterface;
import it.nextworks.nfvmano.sbi.nfvo.messages.InternalNsLifecycleChangeNotification;
import it.nextworks.nfvmano.sbi.nfvo.messages.NfvoLcmNotificationType;
import it.nextworks.nfvmano.sbi.nfvo.messages.OperationStatus;
import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Class that implements the task to send polling requests
 * to the NFVO in order to detect changes in the status
 * of an operation.
 * 
 * @author nextworks
 *
 */
public class NfvoLcmOperationPollingTask implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(NfvoLcmOperationPollingTask.class);
	
	private NfvoLcmOperationPollingManager pollingManager;
	

	
	private NfvoLcmNotificationInterface listener;
	
	public NfvoLcmOperationPollingTask(NfvoLcmOperationPollingManager pollingManager,

									   NfvoLcmNotificationInterface listener) {
		this.pollingManager = pollingManager;

		this.listener = listener;
	}

	@Override
	public void run() {
		log.trace("Running NFVO operation polling task");
		Map<String, PolledNfvoLcmOperation> polledOperations = pollingManager.getPolledOperationsCopy();
		List<String> verifiedOperationIds = new ArrayList<>();
		synchronized (polledOperations) {
			for (Map.Entry<String, PolledNfvoLcmOperation> e : polledOperations.entrySet()) {
				PolledNfvoLcmOperation operation = e.getValue();
				OperationStatus expectedStatus = operation.getExpectedStatus();
				switch (expectedStatus) {
				case SUCCESSFULLY_DONE: {
					if (checkOperationSuccessfulResult(operation)) verifiedOperationIds.add(operation.getOperationId());
					break;
				}

				default: {
					log.error("Expected status not supported for polled VNFM operation");
					break;
				}
				}
			}
		}
		for (String opId : verifiedOperationIds) {
			pollingManager.removeOperation(opId);
			log.debug("Operation " + opId + " removed from polling processing.");
		}
		log.trace("NFVO polling task terminated");
	}
	
	private boolean checkOperationSuccessfulResult(PolledNfvoLcmOperation operation) {
		String operationId = operation.getOperationId();
		log.debug("Check status for operation " + operationId);
		try {
			OperationStatus operationStatus = operation.getDriver().getOperationStatus(operationId);
			if ((operationStatus.equals(OperationStatus.SUCCESSFULLY_DONE)) || (operationStatus.equals(OperationStatus.FAILED))) {
				log.debug("Operation terminated - sending notification");
				InternalNsLifecycleChangeNotification notification =
						new InternalNsLifecycleChangeNotification(
								operation.getOperationId(),
								operation.getNfvNsiId(),
								operation.getDriver(),
								NfvoLcmNotificationType.LIFECYCLE_OPERATION_RESULT,
								operation.getOperationType()
								);


				listener.notifyNetworkServiceStatusChange(notification);
				log.debug("Notification sent");
				return true;
			} else {
				log.debug("Operation still under processing - continuing polling.");
				return false;
			}
		} catch (  FailedOperationException e) {
			log.error(e.getMessage());
			log.debug("Stack trace", e);
			log.error("Operation terminated with a failure due to an exception");
			InternalNsLifecycleChangeNotification notification = new InternalNsLifecycleChangeNotification(
					operationId,
					operation.getNfvNsiId(),
					operation.getDriver(),
					NfvoLcmNotificationType.LIFECYCLE_OPERATION_RESULT,
					operation.getOperationType()

					);
			listener.notifyNetworkServiceStatusChange(notification);
			log.debug("Notification sent");

			return true;
		} 
	}

}
