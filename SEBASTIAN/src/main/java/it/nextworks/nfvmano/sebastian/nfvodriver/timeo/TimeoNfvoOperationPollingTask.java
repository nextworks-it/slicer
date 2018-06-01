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
package it.nextworks.nfvmano.sebastian.nfvodriver.timeo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.nextworks.nfvmano.libs.common.enums.LcmNotificationType;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.NsLifecycleChangeNotification;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoNotificationsManager;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;


/**
 * Class that implements the task to send polling requests
 * to the NFVO in order to detect changes in the status
 * of an operation.
 * 
 * @author nextworks
 *
 */
public class TimeoNfvoOperationPollingTask implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(TimeoNfvoOperationPollingTask.class);
	
	private TimeoNfvoOperationPollingManager pollingManager;
	
	private NfvoService nfvoService;
	
	private NfvoNotificationsManager listener;
	
	public TimeoNfvoOperationPollingTask(TimeoNfvoOperationPollingManager pollingManager,
			NfvoService nfvoService,
			NfvoNotificationsManager listener) {
		this.pollingManager = pollingManager;
		this.nfvoService = nfvoService;
		this.listener = listener;
	}

	@Override
	public void run() {
		log.trace("Running TIMEO NFVO operation polling task");
		Map<String, PolledTimeoNfvoOperation> polledOperations = pollingManager.getPolledOperationsCopy();
		List<String> verifiedOperationIds = new ArrayList<>();
		synchronized (polledOperations) {
			for (Map.Entry<String, PolledTimeoNfvoOperation> e : polledOperations.entrySet()) {
				PolledTimeoNfvoOperation operation = e.getValue();
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
		log.trace("TIMEO NFVO polling task terminated");
	}
	
	private boolean checkOperationSuccessfulResult(PolledTimeoNfvoOperation operation) {
		String operationId = operation.getOperationId();
		log.debug("Check status for operation " + operationId);
		try {
			OperationStatus operationStatus = nfvoService.getOperationStatus(operationId);
			if ((operationStatus.equals(OperationStatus.SUCCESSFULLY_DONE)) || (operationStatus.equals(OperationStatus.FAILED))) {
				log.debug("Operation terminated - sending notification");
				NsLifecycleChangeNotification notification = new NsLifecycleChangeNotification(operation.getNfvNsiId(), 
						operationId,
						operation.getOperationType(), 
						LcmNotificationType.LIFECYCLE_OPERATION_RESULT, 
						null,
						null, 
						null, 
						null, 
						null, 
						null);
				listener.notifyNsLifecycleChange(notification);
				log.debug("Notification sent");
				return true;
			} else {
				log.debug("Operation still under processing - continuing polling.");
				return false;
			}
		} catch (MethodNotImplementedException e) {
			log.error(e.getMessage());
		} catch (NotExistingEntityException e) {
			log.error(e.getMessage());
		} catch (FailedOperationException e) {
			log.error(e.getMessage());
		} catch (MalformattedElementException e) {
			log.error(e.getMessage());
		}
		return false;
	}

}
