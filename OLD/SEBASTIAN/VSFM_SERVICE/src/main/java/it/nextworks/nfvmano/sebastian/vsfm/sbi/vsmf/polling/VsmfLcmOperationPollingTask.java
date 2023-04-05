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
package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.polling;

import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChange;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.interfaces.VsLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.vsfm.interfaces.VsLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.vsfm.messages.QueryVsResponse;
import it.nextworks.nfvmano.sebastian.vsfm.messages.VerticalServiceStatusChange;
import it.nextworks.nfvmano.sebastian.vsfm.messages.VerticalServiceStatusChangeNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Class that implements the task to send polling requests
 * to the NSMF in order to detect changes in the status
 * of an operation.
 *
 * @author nextworks
 */
public class VsmfLcmOperationPollingTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(VsmfLcmOperationPollingTask.class);

    private VsmfLcmOperationPollingManager pollingManager;

    private VsLcmProviderInterface vsLcmProviderInterface;

    private VsLcmConsumerInterface vsLcmConsumerInterface;



    public VsmfLcmOperationPollingTask(VsLcmProviderInterface vsLcmProviderInterface, VsLcmConsumerInterface vsLcmConsumerInterface, VsmfLcmOperationPollingManager pollingManager ) {
        this.pollingManager = pollingManager;
        this.vsLcmProviderInterface= vsLcmProviderInterface;
        this.vsLcmConsumerInterface = vsLcmConsumerInterface;
    }

    @Override
    public void run() {
        log.trace("Running NSFM operation polling task");
        Map<String, PolledVsmfLcmOperation> polledOperations = pollingManager.getPolledOperationsCopy();
        List<String> verifiedOperationIds = new ArrayList<>();
        synchronized (polledOperations) {
            for (Map.Entry<String, PolledVsmfLcmOperation> e : polledOperations.entrySet()) {
                PolledVsmfLcmOperation operation = e.getValue();
                OperationStatus expectedStatus = operation.getExpectedStatus();
                switch (expectedStatus) {
                    case SUCCESSFULLY_DONE: {
                        if (checkOperationSuccessfulResult(operation))
                            verifiedOperationIds.add(operation.getOperationId());
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
        log.trace("NSFM polling task terminated");
    }

    private boolean checkOperationSuccessfulResult(PolledVsmfLcmOperation operation) {
        String operationId = operation.getOperationId();
        log.debug("Checking status for operation {} and Vertical service instance {} ", operationId, operation.getVsiId());
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            Filter filter;
            GeneralizedQueryRequest request;
            QueryVsResponse vsResponse;
            VerticalServiceStatusChangeNotification notification;
            if (operation.getOperationType().equals("VSI_CREATION")) {
                parameters.put("VSI_ID", operation.getVsiId());
                filter = new Filter(parameters);
                request = new GeneralizedQueryRequest(filter, new ArrayList<>());
                vsResponse = vsLcmProviderInterface.queryVs(request, operation.getDomainId());
                if (vsResponse.getStatus().equals(VerticalServiceStatus.INSTANTIATED)) {
                    log.debug("Vertical service instance {} successfully instantiated", operation.getVsiId());
                    notification = new VerticalServiceStatusChangeNotification(operation.getVsiId(), VerticalServiceStatusChange.VSI_CREATED, true);
                    vsLcmConsumerInterface.notifyVerticalServiceStatusChange(notification,  operation.getDomainId());
                    return true;
                } else if (vsResponse.getStatus()==VerticalServiceStatus.FAILED) {
                    log.debug("Vertical service instance {} instantiation failed", operation.getVsiId());
                    notification = new VerticalServiceStatusChangeNotification(operation.getVsiId(),VerticalServiceStatusChange.VSI_FAILED, false);
                    vsLcmConsumerInterface.notifyVerticalServiceStatusChange(notification,  operation.getDomainId());
                    return true;
                }
            } else if (operation.getOperationType().equals("VSI_TERMINATION")) {
                    parameters.put("VSI_ID", operation.getVsiId());
                    filter = new Filter(parameters);
                    request = new GeneralizedQueryRequest(filter, new ArrayList<>());
                    vsResponse = vsLcmProviderInterface.queryVs(request, operation.getDomainId());
                    if (vsResponse.getStatus().equals(VerticalServiceStatus.TERMINATED)) {
                        log.debug("Vertical service instance {} successfully terminated", operation.getVsiId());
                        notification = new VerticalServiceStatusChangeNotification(operation.getVsiId(), VerticalServiceStatusChange.VSI_TERMINATED, true);
                        vsLcmConsumerInterface.notifyVerticalServiceStatusChange(notification,  operation.getDomainId());
                        return true;
                    }

            }
        } catch (MethodNotImplementedException | FailedOperationException | MalformattedElementException | NotExistingEntityException e) {
            log.error(e.getMessage());
            log.debug("Stack trace", e);
        } catch (NotPermittedOperationException e) {
            log.error(e.getMessage());
            log.debug("Stack trace", e);
        }
        log.debug("Operation {} and Vertical service instance {}: status not changed yet ", operationId, operation.getVsiId());
        return false;
    }

}
