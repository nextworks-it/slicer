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
package it.nextworks.nfvmano.sebastian.vsfm.sbi;

import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.enums.LcmNotificationType;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.NsLcmProviderInterface;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.NsLifecycleChangeNotification;

import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChange;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Class that implements the task to send polling requests
 * to the NSMF in order to detect changes in the status
 * of an operation.
 *
 * @author nextworks
 */
public class NsmfLcmOperationPollingTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(NsmfLcmOperationPollingTask.class);

    private NsmfLcmOperationPollingManager pollingManager;

    private NsmfLcmProviderInterface nsmfLcmProvider;

    private VsLcmService vsLcmService;

    public NsmfLcmOperationPollingTask(NsmfLcmOperationPollingManager pollingManager,
                                       NsmfLcmProviderInterface nsmfLcmProvider,
                                       VsLcmService vsLcmService) {
        this.pollingManager = pollingManager;
        this.nsmfLcmProvider = nsmfLcmProvider;
        this.vsLcmService = vsLcmService;
    }

    @Override
    public void run() {
        log.trace("Running NSFM operation polling task");
        Map<String, PolledNsmfLcmOperation> polledOperations = pollingManager.getPolledOperationsCopy();
        List<String> verifiedOperationIds = new ArrayList<>();
        synchronized (polledOperations) {
            for (Map.Entry<String, PolledNsmfLcmOperation> e : polledOperations.entrySet()) {
                PolledNsmfLcmOperation operation = e.getValue();
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

    private boolean checkOperationSuccessfulResult(PolledNsmfLcmOperation operation) {
        String operationId = operation.getOperationId();
        log.debug("Checking status for operation {} and network slice instance {} ", operationId, operation.getNsiId());
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            Filter filter;
            GeneralizedQueryRequest request;
            List<NetworkSliceInstance> nsiInstances;
            NetworkSliceStatusChangeNotification notification;
            if (operation.getOperationType().equals("NSI_CREATION")) {
                parameters.put("NSI_ID", operation.getNsiId());
                parameters.put("REQUEST_TYPE",  "NSI_CREATION");
                filter = new Filter(parameters);
                request = new GeneralizedQueryRequest(filter, new ArrayList<>());
                nsiInstances = nsmfLcmProvider.queryNetworkSliceInstance(request, operation.getDomainId(), operation.getTenantId());
                if (nsiInstances.size() == 1 && nsiInstances.get(0).getStatus().equals(NetworkSliceStatus.INSTANTIATED)) {
                    log.debug("Network slice instance {} successfully instantiated", operation.getNsiId());
                    notification = new NetworkSliceStatusChangeNotification(operation.getNsiId(), NetworkSliceStatusChange.NSI_CREATED, true);
                    vsLcmService.notifyNetworkSliceStatusChange(notification);
                    return true;
                } else if (nsiInstances.size() == 1 && nsiInstances.get(0).getStatus().equals(NetworkSliceStatus.FAILED)) {
                    log.debug("Network slice instance {} instantiation failed", operation.getNsiId());
                    notification = new NetworkSliceStatusChangeNotification(operation.getNsiId(), NetworkSliceStatusChange.NSI_FAILED, false);
                    vsLcmService.notifyNetworkSliceStatusChange(notification);
                    return true;
                }
            } else if (operation.getOperationType().equals("NSI_TERMINATION")) {
                if (operation.getDomainType().equals(NspNbiType.NEUTRAL_HOSTING)) {
                    filter = new Filter(parameters);
                    request = new GeneralizedQueryRequest(filter, new ArrayList<>());
                    nsiInstances = nsmfLcmProvider.queryNetworkSliceInstance(request, operation.getDomainId(), null);
                    if (!nsiInstances.stream().map(NetworkSliceInstance::getNsiId).collect(Collectors.toList()).contains(operation.getNsiId())) {
                        log.debug("Network slice instance {} successfully terminated", operation.getNsiId());
                        notification = new NetworkSliceStatusChangeNotification(operation.getNsiId(), NetworkSliceStatusChange.NSI_TERMINATED, true);
                        vsLcmService.notifyNetworkSliceStatusChange(notification);
                        return true;
                    }//TODO failed?
                } else {
                    parameters.put("NSI_ID", operation.getNsiId());
                    parameters.put("REQUEST_TYPE",  "NSI_TERMINATION");
                    filter = new Filter(parameters);
                    request = new GeneralizedQueryRequest(filter, new ArrayList<>());
                    nsiInstances = nsmfLcmProvider.queryNetworkSliceInstance(request, operation.getDomainId(), null);
                    if (nsiInstances.size() == 1 && nsiInstances.get(0).getStatus().equals(NetworkSliceStatus.TERMINATED)) {
                        log.debug("Network slice instance {} successfully terminated", operation.getNsiId());
                        notification = new NetworkSliceStatusChangeNotification(operation.getNsiId(), NetworkSliceStatusChange.NSI_TERMINATED, true);
                        vsLcmService.notifyNetworkSliceStatusChange(notification);
                        return true;
                    } else if (nsiInstances.size() == 1 && nsiInstances.get(0).getStatus().equals(NetworkSliceStatus.FAILED)) {
                        log.debug("Network slice instance {} termination failed", operation.getNsiId());
                        notification = new NetworkSliceStatusChangeNotification(operation.getNsiId(), NetworkSliceStatusChange.NSI_FAILED, false);
                        vsLcmService.notifyNetworkSliceStatusChange(notification);
                        return true;
                    }
                }
            }
        } catch (MethodNotImplementedException | FailedOperationException | MalformattedElementException e) {
            log.error(e.getMessage());
            log.debug("Stack trace", e);
        }
        log.debug("Operation {} and network slice instance {}: status not changed yet ", operationId, operation.getNsiId());
        return false;
    }

}
