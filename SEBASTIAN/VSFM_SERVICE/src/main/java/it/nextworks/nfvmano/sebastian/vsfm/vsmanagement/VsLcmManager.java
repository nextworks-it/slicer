/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.vsfm.vsmanagement;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.catalogue.blueprint.elements.*;
import it.nextworks.nfvmano.catalogue.blueprint.messages.QueryVsBlueprintResponse;
import it.nextworks.nfvmano.catalogue.blueprint.repo.TranslationRuleRepository;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsBlueprintCatalogueService;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;
import it.nextworks.nfvmano.catalogue.template.elements.NstConfigurationRule;
import it.nextworks.nfvmano.catalogues.template.repo.ConfigurationRuleRepository;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.sebastian.arbitrator.elements.NetworkSliceInstanceAction;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.common.VsNssiAction;
import it.nextworks.nfvmano.sebastian.common.VsNssiActionType;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.*;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.*;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsmfUtils;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.*;
import it.nextworks.nfvmano.sebastian.vsfm.interfaces.VsLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.vsfm.interfaces.VsLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.vsfm.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.TerminateVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.VerticalServiceStatusChange;
import it.nextworks.nfvmano.sebastian.vsfm.messages.VerticalServiceStatusChangeNotification;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements.OsmTranslationInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Entity in charge of managing the lifecycle
 * of a single vertical service instance
 *
 * @author nextworks
 */
public class VsLcmManager {

    private static final Logger log = LoggerFactory.getLogger(VsLcmManager.class);
    private String vsiId;
    private String vsiName;
    private VsRecordService vsRecordService;
    private TranslatorService translatorService;
    private ArbitratorService arbitratorService;
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;

    //Used to retrieve the vsb atomic component placement
    private VsBlueprintCatalogueService vsBlueprintCatalogueService;
    private AdminService adminService;
    private VsLcmService vsLcmService;
    private VirtualResourceCalculatorService virtualResourceCalculatorService;
    private VerticalServiceStatus internalStatus;
    private NsmfLcmProviderInterface nsmfLcmProvider;
    private VsmfUtils vsmfUtils;

    private VsLcmProviderInterface vssLcmProviderInterface;
    private List<String> nestedVsi = new ArrayList<>();

    private boolean isMultidomain;
    private String networkSliceId;


    private boolean enableNsmm;
    //Key: VSD ID; Value: VSD
    private Map<String, VsDescriptor> vsDescriptors = new HashMap<>();
    private String tenantId;

    //Key: nssiIdM Value: NfvNsInstantiationInfo
    private Map<String, NfvNsInstantiationInfo> nssNfvInstantiationInfos = new HashMap<>();

    // the following is for the WAITING_FOR_RESOURCES status
    private ArbitratorResponse storedArbitratorResponse;

    private NfvNsInstantiationInfo storedNfvNsInstantiationInfo;
    private InstantiateVsiRequestMessage storedInstantiateVsiRequestMessage;

    private VsLcmConsumerInterface vsLcmConsumerInterface;

    //Registers the list of network slice ids which are still pending to be modified due to
    //an arbitrator imactedNEtworkSliceInstances
    private List<String> pendingNsiModificationIds = new ArrayList<>();

    private TranslationRuleRepository translationRuleRepository;

    private ConfigurationRuleRepository configurationRuleRepository;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor
     *
     * @param vsiId                            ID of the vertical service instance
     * @param vsRecordService                  wrapper of VSI record
     * @param vsDescriptorCatalogueService     repo of VSDs
     * @param translatorService                translator service
     * @param arbitratorService                arbitrator service
     * @param adminService                     admin service
     * @param vsLcmService                     engine
     * @param virtualResourceCalculatorService virtual resource calculator service
     * @param nsmfLcmProvider
     * @param vsmfUtils
     */
    public VsLcmManager(String vsiId,
                        String vsiName,
                        VsRecordService vsRecordService,
                        VsDescriptorCatalogueService vsDescriptorCatalogueService,
                        VsBlueprintCatalogueService vsBlueprintCatalogueService,
                        TranslatorService translatorService,
                        ArbitratorService arbitratorService,
                        AdminService adminService,
                        VsLcmService vsLcmService,
                        VirtualResourceCalculatorService virtualResourceCalculatorService,
                        NsmfLcmProviderInterface nsmfLcmProvider,
                        VsmfUtils vsmfUtils,
                        VsLcmConsumerInterface vsLcmConsumerInterface,
                        TranslationRuleRepository translationRuleRepository,
                        ConfigurationRuleRepository configurationRuleRepository,
                        boolean enableNsmm

    ) {
        this.vsiId = vsiId;
        this.vsiName = vsiName;
        this.vsRecordService = vsRecordService;
        this.vsDescriptorCatalogueService = vsDescriptorCatalogueService;
        this.translatorService = translatorService;
        this.arbitratorService = arbitratorService;
        this.adminService = adminService;
        this.internalStatus = VerticalServiceStatus.INSTANTIATING;
        this.vsLcmService = vsLcmService;
        this.virtualResourceCalculatorService = virtualResourceCalculatorService;
        this.nsmfLcmProvider = nsmfLcmProvider;
        this.vsmfUtils = vsmfUtils;
        this.vsBlueprintCatalogueService = vsBlueprintCatalogueService;
        this.vsLcmConsumerInterface = vsLcmConsumerInterface;
        this.translationRuleRepository = translationRuleRepository;
        this.configurationRuleRepository = configurationRuleRepository;
        this.enableNsmm= enableNsmm;
    }

    /**
     * Method used to receive messages about VSI lifecycle from the Rabbit MQ
     *
     * @param message received message
     */
    public void receiveMessage(String message) {
        log.debug("Received message for VSI " + vsiId + "\n" + message);

        try {
            ObjectMapper mapper = new ObjectMapper();
            VsmfEngineMessage em = mapper.readValue(message, VsmfEngineMessage.class);
            VsmfEngineMessageType type = em.getType();

            switch (type) {
                case INSTANTIATE_VSI_REQUEST: {
                    log.debug("Processing VSI instantiation request.");
                    InstantiateVsiRequestMessage instantiateVsRequestMsg = (InstantiateVsiRequestMessage) em;
                    processInstantiateRequest(instantiateVsRequestMsg);
                    break;
                }

                case MODIFY_VSI_REQUEST: {
                    log.debug("Processing VSI termination request.");
                    ModifyVsiRequestMessage modifyVsRequestMsg = (ModifyVsiRequestMessage) em;
                    processModifyRequest(modifyVsRequestMsg);
                    break;
                }

                case TERMINATE_VSI_REQUEST: {
                    log.debug("Processing VSI termination request.");
                    TerminateVsiRequestMessage terminateVsRequestMsg = (TerminateVsiRequestMessage) em;
                    processTerminateRequest(terminateVsRequestMsg);
                    break;
                }

                case NOTIFY_NSI_STATUS_CHANGE: {
                    log.debug("Processing NSI status change notification.");
                    NotifyNsiStatusChange notifyNsiStatusChangeMsg = (NotifyNsiStatusChange) em;
                    processNsiStatusChangeNotification(notifyNsiStatusChangeMsg);
                    break;
                }
                case NOTIFY_VSI_STATUS_CHANGE: {
                    log.debug("Processing VSI status change notification.");
                    NotifyVsiStatusChange notifyVsiStatusChangeMsg = (NotifyVsiStatusChange) em;
                    processVsiStatusChangeNotification(notifyVsiStatusChangeMsg);
                    break;
                }
                case RESOURCES_GRANTED: {
                    log.debug("Processing resources granted notification.");
                    NotifyResourceGranted notifyResourceGranted = (NotifyResourceGranted) em;
                    processResourcesGrantedNotification(notifyResourceGranted);
                    break;
                }
                case NOTIFY_VSI_NSSI_COORDINATION_START: {
                    log.debug("Processing NSSI COORDINATION START notification.");
                    NotifyVsiNssiCoordinationStart notifyVsiNssiCoordinationStart = (NotifyVsiNssiCoordinationStart) em;
                    processVsiNssiCoordinationStart(notifyVsiNssiCoordinationStart);
                    break;
                }
                case NOTIFY_VSI_NSSI_COORDINATION_END: {
                    log.debug("Processing NSSI COORDINATION END notification.");
                    NotifyVsiNssiCoordinationEnd notifyVsiNssiCoordinationEnd = (NotifyVsiNssiCoordinationEnd) em;
                    processVsiNssiCoordinationEnd(notifyVsiNssiCoordinationEnd);
                    break;
                }
                default:
                    log.error("Received message with not supported type. Skipping.");
                    break;
            }

        } catch (JsonParseException e) {
            manageVsError("Error while parsing message: " + e.getMessage());
        } catch (JsonMappingException e) {
            manageVsError("Error in Json mapping: " + e.getMessage());
        } catch (IOException e) {
            manageVsError("IO error when receiving json message: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unhandled exception");
            manageVsError("Unhandled error: " + e.getMessage());
        }
    }

    private void processVsiNssiCoordinationStart(NotifyVsiNssiCoordinationStart notifyVsiNssiCoordinationStart) {
        log.debug("Processing vsi nssi coordination start notification");
        if (this.internalStatus == VerticalServiceStatus.INSTANTIATED) {
            log.debug("updating network slice subnet instance status");
            try {
                vsRecordService.updateNssiStatusInVsi(this.vsiId, notifyVsiNssiCoordinationStart.getNetworkSliceInstanceId(), NetworkSliceStatus.UNDER_MODIFICATION);
            } catch (NotExistingEntityException e) {
                log.error("Error updating network slice subnet status:", e);
            }
        } else {
            log.warn("received notifynssicoorindationstart in wrong status, ignoring");
        }
    }

    private void processVsiNssiCoordinationEnd(NotifyVsiNssiCoordinationEnd notifyVsiNssiCoordinationEnd) {
        log.debug("Processing vsi nssi coordination start notification");
        if (this.internalStatus == VerticalServiceStatus.INSTANTIATED) {
            log.debug("updating network slice subnet instance status");
            try {
                if (notifyVsiNssiCoordinationEnd.isSuccessful()) {
                    vsRecordService.updateNssiStatusInVsi(this.vsiId, notifyVsiNssiCoordinationEnd.getNetworkSliceInstanceId(), NetworkSliceStatus.INSTANTIATED);
                    Map<String, String> filterParms = new HashMap<>();
                    filterParms.put("NSI_ID", notifyVsiNssiCoordinationEnd.getNetworkSliceInstanceId());
                    Filter filter = new Filter(filterParms);
                    GeneralizedQueryRequest request = new GeneralizedQueryRequest(filter, null);
                    List<NetworkSliceInstance> nsis = nsmfLcmProvider.queryNetworkSliceInstance(request, notifyVsiNssiCoordinationEnd.getDomain(), tenantId);
                    vsRecordService.updateNssiNfvInstantiationInfoInVsi(this.vsiId, notifyVsiNssiCoordinationEnd.getNetworkSliceInstanceId(),
                            notifyVsiNssiCoordinationEnd.getDomain(), nsis.get(0).getDfId(), nsis.get(0).getInstantiationLevelId());

                } else {
                    vsRecordService.updateNssiStatusInVsi(this.vsiId, notifyVsiNssiCoordinationEnd.getNetworkSliceInstanceId(), NetworkSliceStatus.FAILED);
                }

            } catch (NotExistingEntityException e) {
                log.error("Error updating network slice subnet status:", e);
            } catch (MethodNotImplementedException e) {
                log.error("Error updating network slice subnet status:", e);
            } catch (MalformattedElementException e) {
                log.error("Error updating network slice subnet status:", e);
            } catch (FailedOperationException e) {
                log.error("Error updating network slice subnet status:", e);
            }
        } else if (this.internalStatus == VerticalServiceStatus.INSTANTIATING) {
            String nsiId = notifyVsiNssiCoordinationEnd.getNetworkSliceInstanceId();
            if (notifyVsiNssiCoordinationEnd.isSuccessful()) {
                if (pendingNsiModificationIds.contains(nsiId)) {
                    pendingNsiModificationIds.remove(nsiId);
                    if (pendingNsiModificationIds.isEmpty()) {
                        log.debug("Proceeding with vertical service instantiation");
                        try {
                            internalProcessInstantiationRequest();
                        } catch (FailedOperationException e) {
                            manageVsError(e.getMessage(), e);
                        } catch (MalformattedElementException e) {
                            manageVsError(e.getMessage(), e);
                        } catch (NotPermittedOperationException e) {
                            manageVsError(e.getMessage(), e);
                        } catch (NotExistingEntityException e) {
                            manageVsError(e.getMessage(), e);
                        } catch (MethodNotImplementedException e) {
                            manageVsError(e.getMessage(), e);
                        }
                    } else {
                        log.debug("Pending Network Slice Subnet modifications:" + pendingNsiModificationIds);
                    }

                } else {
                    log.debug("Unknown pending network slice subnet modification operation");
                }
            } else {
                log.debug("Network slice modification failed");
                this.internalStatus = VerticalServiceStatus.FAILED;
            }
        } else {
            log.warn("received notifynssicoorindationstart in wrong status, ignoring");
        }
    }

    void setNetworkSliceId(String networkSliceId) {
        this.networkSliceId = networkSliceId;
    }

    void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    void processInstantiateRequest(InstantiateVsiRequestMessage msg) {
        if (internalStatus != VerticalServiceStatus.INSTANTIATING) {
            manageVsError("Received instantiation request in wrong status. Skipping message.");
            return;
        }
        this.storedInstantiateVsiRequestMessage = msg;
        String vsdId = msg.getRequest().getVsdId();

        log.debug("Instantiating Vertical Service " + vsiId + " with VSD " + vsdId);
        log.debug("Vertical Service Instance with id {} is {}", vsiId, internalStatus);

        try {
            VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
            this.vsDescriptors.put(vsdId, vsd);
            this.tenantId = msg.getRequest().getTenantId();
            QueryVsBlueprintResponse vsBlueprintResponse = vsBlueprintCatalogueService.queryVsBlueprint(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVsBlueprintFilter(vsd.getVsBlueprintId(), tenantId), null));
            if (vsBlueprintResponse.getVsBlueprintInfo().get(0).getVsBlueprint().isInterSite()) {
                log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_INSTANTIATION_START,vsBlueprintResponse.getVsBlueprintInfo().get(0).getName()));
                this.internalProcessMultisiteVss(msg, vsd, vsBlueprintResponse.getVsBlueprintInfo().get(0).getVsBlueprint());
                return;
            }
            String vsbName = vsBlueprintResponse.getVsBlueprintInfo().get(0).getName();
            log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_INSTANTIATION_START,vsbName));
            List<String> vsdIds = new ArrayList<>();
            vsdIds.add(vsdId);
            log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_TRANSLATION_START,vsbName));
            Map<String, NfvNsInstantiationInfo> nsInfo = translatorService.translateVsd(vsdIds);
            log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_TRANSLATION_END));
            log.debug("The VSD has been translated in the required network slice characteristics.");
            log.debug("Translator response: " + nsInfo);

            List<ArbitratorRequest> arbitratorRequests = new ArrayList<>();
            //only a single request is supported at the moment
            ArbitratorRequest arbitratorRequest = new ArbitratorRequest("requestId", tenantId, vsd, nsInfo);
            arbitratorRequests.add(arbitratorRequest);
            log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_ARBITRATION_START,vsbName));
            ArbitratorResponse arbitratorResponse = arbitratorService.computeArbitratorSolution(arbitratorRequests).get(0);
            log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_ARBITRATION_END));
            this.storedArbitratorResponse = arbitratorResponse;
            this.storedNfvNsInstantiationInfo = nsInfo.get(vsdId);

            //PERFORM ARBITRATION ACTIONS
            if (!(arbitratorResponse.isAcceptableRequest())) {
                if (arbitratorResponse.getImpactedVerticalServiceInstances().isEmpty()) {
                    manageVsError("Error while instantiating VS " + vsiId + ": no solution returned from the arbitrator");

                } else {
                    //TODO: This is not handled in multi-domain case, since no impacted vertical service instances are returned
                    setInternalStatus(VerticalServiceStatus.WAITING_FOR_RESOURCES);
                    vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.WAITING_FOR_RESOURCES);


                    //invoke engine for VsGroupCoordinator.
                    vsLcmService.requestVsiCoordination(vsiId, arbitratorResponse.getImpactedVerticalServiceInstances());

                }
                return;
            }

            //Trigger NsSubnetSlice scaling to add the resources required by the incoming vertical service
            //This decision is taken at the arbitrator based on the NetworkSliceSubnet VNF placement
            if (arbitratorResponse.getImpactedNetworkSliceInstances() != null && !arbitratorResponse.getImpactedNetworkSliceInstances().isEmpty()) {
                log.debug("Triggering VS NsSliceSubnet scaling due to a shared VNF");
                if (!isMultidomain) {
                    Map<String, VsNssiAction> actions = new HashMap<>();
                    for (Entry<String, NetworkSliceInstanceAction> nsiActionEntry : arbitratorResponse.getImpactedNetworkSliceInstances().entrySet()) {

                        //TODO: add control to see if the vertical service instance is shared
                        NetworkSliceInstanceAction nsiAction = nsiActionEntry.getValue();

                        //TODO: add domain id
                        String nssiId = nsiActionEntry.getKey();

                        String nstIl = nsiAction.getNsInstantiationLevel();
                        String nstDf = nsiAction.getNsDf();

                        VsNssiAction currentAction = new VsNssiAction(
                                VsNssiActionType.MODIFY, nssiId,
                                null, //nstId, added just in case it was needed in the final flow
                                nstDf,
                                nstIl, null);
                        log.debug("adding Vs Nssi action for NSI:" + nssiId);
                        pendingNsiModificationIds.add(nssiId);
                        actions.put(nssiId, currentAction);

                    }
                    for (Entry<String, VsNssiAction> entry : actions.entrySet()) {
                        vsLcmService.requestVsNsiCoordination(this.vsiId, entry.getValue(), tenantId);
                    }
                } else {
                    manageVsError("NsSliceSubnet modification/sharing it is not supported in multidomain NS environments");
                }
                return;
            }

            internalProcessInstantiationRequest();
        } catch (Exception e) {
            manageVsError("Error while instantiating VS " + vsiId + ": " + e.getMessage(), e);
        }
    }

    private void internalProcessMultisiteVss(InstantiateVsiRequestMessage oReq, VsDescriptor vsd, VsBlueprint vsBlueprint) throws FailedOperationException, MalformattedElementException, NotPermittedOperationException, NotExistingEntityException, MethodNotImplementedException {
        log.debug("Processing intersite VSS");

        if (internalStatus != VerticalServiceStatus.INSTANTIATING)
            throw new FailedOperationException("Wrong status:" + internalStatus);
        List<VsComponent> remoteComponents = vsBlueprint.getAtomicComponents().stream().filter(component -> component.getCompatibleSite() != null)
                .collect(Collectors.toList());

        if (remoteComponents != null && !remoteComponents.isEmpty()) {

            for (VsComponent currentComponent : remoteComponents) {
                log.debug("Building instantiation request for component:" + currentComponent.getComponentId());
                String vsdId = vsd.getNestedVsdIds().get(currentComponent.getComponentId());
                Map<String, String> userData = oReq.getRequest().getUserData();
                Map<String, String> tUserData = new HashMap<>();
                for (String key : oReq.getRequest().getUserData().keySet()) {
                    if (key.startsWith(currentComponent.getComponentId())) {
                        tUserData.put(key.replace(currentComponent.getComponentId() + ".", ""), userData.get(key));
                    }
                }

                tUserData.put("blueprintId", currentComponent.getAssociatedVsbId());
                tUserData.put("VSI_PARENT_ID",vsiId);
                //if(oReq.getRequest().getVssData()!=null && oReq.getRequest().getVssData().containsKey(currentComponent.getComponentId())){
                //    Map<String, String> vssData = oReq.getRequest().getVssData().get(currentComponent.getComponentId()).getUserData();
                //   if(vssData!=null){

                //        userData.putAll(vssData);
                //    }

                //}
                InstantiateVsRequest request = new InstantiateVsRequest(oReq.getRequest().getName() + "_" + currentComponent.getComponentId(),
                        oReq.getRequest().getDescription(),
                        vsdId,
                        oReq.getRequest().getTenantId(),
                        oReq.getRequest().getNotificationUrl(),
                        tUserData,
                        oReq.getRequest().getLocationConstraints());

                String receivedId = vsLcmService.instantiateVs(request, currentComponent.getCompatibleSite());
                VerticalSubserviceInstance vssi = new VerticalSubserviceInstance(currentComponent.getCompatibleSite(), currentComponent.getAssociatedVsbId(), vsdId, receivedId,
                        VerticalServiceStatus.INSTANTIATING, tUserData);
                vsRecordService.addVssiInVsi(vsiId, vssi);
            }
            internalStatus = VerticalServiceStatus.INSTANTIATING_REMOTE_VSS;
        } else {
            log.debug("No remote VSS to request, proceeding with local");
            internalProcessLocalSiteVss(oReq, vsd, vsBlueprint);
        }

    }

    private void internalProcessLocalSiteVss(InstantiateVsiRequestMessage oReq, VsDescriptor vsd, VsBlueprint vsBlueprint) throws FailedOperationException, MalformattedElementException, NotPermittedOperationException, NotExistingEntityException, MethodNotImplementedException {

        log.debug("Processing local VSSs");
        if (internalStatus != VerticalServiceStatus.INSTANTIATING && internalStatus != VerticalServiceStatus.INSTANTIATING_REMOTE_VSS)
            throw new FailedOperationException("Wrong status:" + internalStatus);

        List<VsComponent> localComponents = vsBlueprint.getAtomicComponents().stream().filter(component -> component.getCompatibleSite() == null)
                .collect(Collectors.toList());

        if (localComponents != null && !localComponents.isEmpty()) {

            for (VsComponent currentComponent : localComponents) {
                log.debug("Building instantiation request for component:" + currentComponent.getComponentId());
                String vsdId = vsd.getNestedVsdIds().get(currentComponent.getComponentId());
                Map<String, String> userData = oReq.getRequest().getUserData();
                Map<String, String> tUserData = new HashMap<>();
                for (String key : oReq.getRequest().getUserData().keySet()) {
                    if (key.startsWith(currentComponent.getComponentId())) {
                        tUserData.put(key.replace(currentComponent.getComponentId() + ".", ""), userData.get(key));
                    }
                }
                tUserData.put("VSI_PARENT_ID",vsiId);
                InstantiateVsRequest request = new InstantiateVsRequest(oReq.getRequest().getName() + "_" + currentComponent.getComponentId(),
                        oReq.getRequest().getDescription(),
                        vsdId,
                        oReq.getRequest().getTenantId(),
                        oReq.getRequest().getNotificationUrl(),
                        tUserData,
                        oReq.getRequest().getLocationConstraints());

                String receivedId = vsLcmService.instantiateVs(request, null);
                VerticalSubserviceInstance vssi = new VerticalSubserviceInstance(null, currentComponent.getAssociatedVsbId(), vsdId, receivedId,
                        VerticalServiceStatus.INSTANTIATING, tUserData);
                vsRecordService.addVssiInVsi(vsiId, vssi);

            }
            internalStatus = VerticalServiceStatus.INSTANTIATING_LOCAL_VSS;
            vsRecordService.setVsStatus(this.vsiId, internalStatus);
        } else {
            log.debug("No local VSS to requests, setting the service as instantiated");
            internalStatus = VerticalServiceStatus.INSTANTIATED;
            log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_INSTANTIATION_END));
            vsRecordService.setVsStatus(this.vsiId, internalStatus);
        }

    }

    private void internalProcessInstantiationRequest() throws FailedOperationException, MalformattedElementException, NotPermittedOperationException, NotExistingEntityException, MethodNotImplementedException {
        //NSMF level integration
        isMultidomain = !storedNfvNsInstantiationInfo.getNsstDomain().isEmpty();
        VsDescriptor vsd = this.vsDescriptors.get(this.storedInstantiateVsiRequestMessage.getRequest().getVsdId());
        QueryVsBlueprintResponse vsBlueprintResponse = vsBlueprintCatalogueService.queryVsBlueprint(
                new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVsBlueprintFilter(vsd.getVsBlueprintId(), tenantId), null));
        VsBlueprint vsBlueprint = vsBlueprintResponse.getVsBlueprintInfo().get(0).getVsBlueprint();
        if (storedArbitratorResponse.isNewSliceRequired()) {
            log.debug("A new network slice should be instantiated for the Vertical Service instance " + vsiId);

            try {
                log.debug("NfvInstantiationInfo for VSI " + vsiId + ": " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(storedNfvNsInstantiationInfo));
            } catch (JsonProcessingException e) {
                log.error("Failed to deserialize NFV NS instantiation info:", e);
            }
            //TODO: to be extended for composite VSDs
            Map<String, Object> sliceParameters = new HashMap<>();
            if (storedNfvNsInstantiationInfo.getSliceServiceParameters() != null) {
                sliceParameters = storedNfvNsInstantiationInfo.getSliceServiceParameters().getSliceServiceParameters();
            }
            List<String> nsSubnetInstanceIds;
            if (storedArbitratorResponse.getExistingSliceSubnets().isEmpty())
                nsSubnetInstanceIds = new ArrayList<>();
            else
                nsSubnetInstanceIds = new ArrayList<>(storedArbitratorResponse.getExistingSliceSubnets().keySet());

            Map<String, String> userData = this.storedInstantiateVsiRequestMessage.getRequest().getUserData();
            Map<String, String> vsdParameters = vsd.getQosParameters();
            /*
            List<VsdNsdTranslationRule> rules = translationRuleRepository.findByBlueprintId(vsd.getVsBlueprintId());
            VsdNsdTranslationRule targetRule = null;
            for (VsdNsdTranslationRule rule : rules) {
                if (rule.matchesVsdParameters(vsdParameters))
                    targetRule = rule;
            }
            if (targetRule == null) {
                log.debug("Impossible to find a translation rule matching the given descriptor parameters");
                manageVsError("Impossible to find a translation rule matching the given descriptor parameters");
                return;
            }

            //translating vsb parameters to nst parameters
            Map<String, String> parametersMapping = targetRule.getParametersMapping();
            if (parametersMapping != null && !parametersMapping.isEmpty()) {
                for (Map.Entry<String, String> mapping : parametersMapping.entrySet())
                    userData.put(mapping.getValue(), userData.remove(mapping.getKey()));
            }
            */
            CreateNsiIdRequest request;
            List<NstConfigurationRule> configurationRules;
            if (isMultidomain) {
                for (Map.Entry<String, String> nsst : storedNfvNsInstantiationInfo.getNsstDomain().entrySet()) {
                    String nsstId = nsst.getKey();
                    String nsstDomain = nsst.getValue();

                    request = new CreateNsiIdRequest(nsstId,
                            vsiName,
                            "Network slice for VS " + vsiName + " in domain " + nsstDomain);

                    String nssiId = nsmfLcmProvider.createNetworkSliceIdentifier(request, nsstDomain, tenantId);
                    log.debug("Network Slice Subnet ID " + nssiId + " created for VSI " + vsiId);

                    NetworkSliceSubnetInstance nsi = new NetworkSliceSubnetInstance(nssiId, nsstId, nsstDomain, null, null, NetworkSliceStatus.INSTANTIATING, null);
                    vsRecordService.addNssiInVsi(vsiId, nsi);

                    log.debug("Record updated with info about NSSI and VSI association");

                    InstantiateNsiRequest instantiateNssiReq = new InstantiateNsiRequest(nssiId,
                            nsstId,
                            storedNfvNsInstantiationInfo.getDeploymentFlavourId(),
                            storedNfvNsInstantiationInfo.getInstantiationLevelId(),
                            nsSubnetInstanceIds,
                            userData,
                            storedInstantiateVsiRequestMessage.getRequest().getLocationConstraints(),
                            storedInstantiateVsiRequestMessage.getRanEndpointId(), sliceParameters
                    );
                    log.debug("Sending request to instantiate network slice");
                    log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_NSI_INSTANTIATION_START,vsBlueprint.getName(),nssiId));
                    nsmfLcmProvider.instantiateNetworkSlice(instantiateNssiReq, nsstDomain, tenantId);
                }
            } else {
                log.debug("Using VSMF single NSMF domain functionality");
                request = new CreateNsiIdRequest(storedNfvNsInstantiationInfo.getNstId(),
                        "NS - " + vsiName,
                        "Network slice for VS " + vsiName);

                String nsiId = nsmfLcmProvider.createNetworkSliceIdentifier(request, null, tenantId);

				    /*String nsiId = vsRecordService.createNetworkSliceForVsi(vsiId, nsiInfo.getNfvNsdId(), nsiInfo.getVnfdVersion(), nsiInfo.getDeploymentFlavourId(),
				        nsiInfo.getInstantiationLevelId(), nsSubnetInstanceIds, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());*/
                log.debug("Network Slice ID " + nsiId + " created for VSI " + vsiId);
                networkSliceId = nsiId;
                vsRecordService.setNsiInVsi(vsiId, nsiId);

                log.debug("Retrieving VSB atomic component placement");

                Map<String, VsComponentPlacement> vnfPlacement = retrieveVsBlueprintVnfPlacement(vsBlueprint);
                Map<String, NetworkSliceVnfPlacement> nsVnfPlacements = getNetworkSliceVnfPlacements(vnfPlacement);
                Map<String, String> userDataPlacements = getUserDataVnfPlacement(nsVnfPlacements);

                userData.putAll(userDataPlacements);
                NetworkSliceSubnetInstance nsi = new NetworkSliceSubnetInstance(nsiId, request.getNstId(),
                        null,
                        storedNfvNsInstantiationInfo.getDeploymentFlavourId(),
                        storedNfvNsInstantiationInfo.getInstantiationLevelId(),
                        NetworkSliceStatus.INSTANTIATING,
                        null); //The VNF placement is retrieved from the SO once the network service has been instantiated
                vsRecordService.addNssiInVsi(vsiId, nsi);
                log.debug("Record updated with info about NSI and VSI association.");
                if(storedInstantiateVsiRequestMessage.getRequest().getUserData().containsKey("edge_resource_instance")){
                    VerticalServiceInstance resourceInstance  = vsRecordService.getVsInstance(storedInstantiateVsiRequestMessage.getRequest().getUserData().get("edge_resource_instance"));
                    userData.put("vim-id", resourceInstance.getVimId());
                }
                InstantiateNsiRequest instantiateNsiReq = new InstantiateNsiRequest(nsiId,
                        storedNfvNsInstantiationInfo.getNstId(),
                        storedNfvNsInstantiationInfo.getDeploymentFlavourId(),
                        storedNfvNsInstantiationInfo.getInstantiationLevelId(),
                        nsSubnetInstanceIds,
                        userData,
                        storedInstantiateVsiRequestMessage.getRequest().getLocationConstraints(),
                        storedInstantiateVsiRequestMessage.getRanEndpointId(),
                        sliceParameters);
                log.debug("Sending request to instantiate network slice ");
                log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_NSI_INSTANTIATION_START,vsBlueprint.getName(),nsiId));
                nsmfLcmProvider.instantiateNetworkSlice(instantiateNsiReq, null, tenantId);
            }
        }
    }

    void processResourcesGrantedNotification(NotifyResourceGranted message) {
        if (internalStatus != VerticalServiceStatus.WAITING_FOR_RESOURCES) {
            manageVsError("Received resource granted notification in wrong status. Skipping message.");
            return;
        }
        if (!message.getVsiId().equals(vsiId)) {
            manageVsError("Received resource granted notification with wrong vsiId. Skipping message");
            return;
        }

        try {
            internalStatus = VerticalServiceStatus.INSTANTIATING;
            vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.INSTANTIATING);

            internalProcessInstantiationRequest();

            //vsLocalEngine.initNewNsLcmManager(networkSliceId, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
            //vsLocalEngine.instantiateNs(nsiId, tenantId, nsiInfo.getNfvNsdId(), nsiInfo.getVnfdVersion(),
            //        nsiInfo.getDeploymentFlavourId(), nsiInfo.getInstantiationLevelId(), vsiId, nsSubnetInstanceIds);


            // put all stored object @null
            storedInstantiateVsiRequestMessage = null;
            storedNfvNsInstantiationInfo = null;
            storedArbitratorResponse = null;


        } catch (Exception e) {
            manageVsError("Error while instantiating VS " + vsiId + ": " + e.getMessage(), e);
        }
    }

    //TODO: modify this to throw an expection!
    void processModifyRequest(ModifyVsiRequestMessage msg) {
        if (!msg.getVsiId().equals(vsiId)) {
            throw new IllegalArgumentException(String.format("Wrong VSI ID: %s", msg.getVsiId()));
        }
        if (internalStatus != VerticalServiceStatus.INSTANTIATED && internalStatus != VerticalServiceStatus.MODIFIED) {
            manageVsError("Received modify request in wrong status. Skipping message.");
            return;
        }
        log.debug("Modifying Vertical Service " + vsiId);
        this.internalStatus = VerticalServiceStatus.UNDER_MODIFICATION;

        String vsdId = msg.getRequest().getVsdId();

        try {

            VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);

            List<String> vsdIds = new ArrayList<>();
            vsdIds.add(vsdId);

            //Translate VSDId into NfvNsInstantiationInfo
            Map<String, NfvNsInstantiationInfo> nsInfos = translatorService.translateVsd(vsdIds);
            log.debug("The VSD has been translated in the required network slice characteristics.");
            // assuming one
            NfvNsInstantiationInfo nsInfo = nsInfos.get(vsdId);
            String newNstIt = nsInfo.getNstId();
            String newDfId = nsInfo.getDeploymentFlavourId();
            String newInstantiationLevelId = nsInfo.getInstantiationLevelId();
            String newNsdId = nsInfo.getNfvNsdId();

            // retrieve info about current NSI
            //NetworkSliceInstance nsi = vsRecordService.getNsInstance(networkSliceId);


            if (!isMultidomain) {

                NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(networkSliceId, null, tenantId);
                String currentDfId = nsi.getDfId();
                String currentInstantiationLevelId = nsi.getInstantiationLevelId();
                String currentNsdId = nsi.getNsdId();

        		/*compare the triples
				Case 1: only ILs are different -> Scale
				Case 2: nothing is different -> set the new VSDid
				Case 3: either nsdIds or Dfs are different -> Error
			 */
                if (newNsdId.equals(currentNsdId) && newDfId.equals(currentDfId)) {
                    if (newInstantiationLevelId.equals(currentInstantiationLevelId)) {
                        //Case 2
                        log.debug("New vsd set.");
                        this.vsDescriptors.put(vsdId, vsd);
                    } else {
                        //Case 1
                        //Assemble Arbitrator request
                        //this is a trick: in this way, the current Nsi (networkSliceId) and the new one (nsInfos) are both on the same request
                        nsInfos.remove(vsdId);
                        nsInfos.put(networkSliceId, nsInfo);

                        List<ArbitratorRequest> arbitratorRequests = new ArrayList<>();
                        ArbitratorRequest arbitratorRequest = new ArbitratorRequest("scaleRequest", tenantId, vsd, nsInfos);
                        arbitratorRequests.add(arbitratorRequest);
                        ArbitratorResponse arbitratorResponse = arbitratorService.arbitrateVsScaling(arbitratorRequests).get(0);
                        if (!(arbitratorResponse.isAcceptableRequest())) {
                            manageVsError("Error while trying modify VS " + vsiId + ": no solution returned from the arbitrator");
                            return;
                        }
                        //TODO Addititional controls on ArbitratorResponse might be required

                        ModifyNsiRequest modifyNsiRequest = new ModifyNsiRequest(nsi.getNsiId(),
                                newNstIt,
                                newDfId,
                                newInstantiationLevelId,
                                currentNsdId);
                        nsmfLcmProvider.modifyNetworkSlice(modifyNsiRequest, null, tenantId);
                        //vsLocalEngine.modifyNs(nsi.getVsiId(), tenantId, currentNsdId, nsInfo.getVnfdVersion(), newDfId, newInstantiationLevelId, vsiId);
                    }
                } else {
                    // Case 3
                    manageVsError("Error while modifying VS " + vsiId + ": Deployment Flavour and Nsd update are not supported yet");
                }
            } else {
                manageVsError("Scaling not supported for multi-domain scenarios");
                return;
            }
        } catch (Exception e) {
            log.error("Error while modifying VS " + vsiId + ": " + e.getMessage(), e);
            manageVsError("Error while modifying VS " + vsiId + ": " + e.getMessage());
        }

    }

    void processTerminateRequest(TerminateVsiRequestMessage msg) {
        if (!msg.getVsiId().equals(vsiId)) {
            throw new IllegalArgumentException(String.format("Wrong VSI ID: %s", msg.getVsiId()));
        }
        if (internalStatus != VerticalServiceStatus.INSTANTIATED && internalStatus != VerticalServiceStatus.MODIFIED) {
            manageVsError("Received termination request in wrong status. Skipping message.");
            return;
        }

        log.debug("Terminating Vertical Service " + vsiId);
        log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_TERMINATION_START));
        this.internalStatus = VerticalServiceStatus.TERMINATING;
        try {
            vsRecordService.setVsStatus(this.vsiId, internalStatus);
        } catch (NotExistingEntityException e) {
            log.error("Error updating VS status", e);
        }
        try {
            VerticalServiceInstance vsi = vsRecordService.getVsInstance(this.vsiId);

            if (vsi.getVssis() != null && !vsi.getVssis().isEmpty()) {
                log.debug("Terminating multisite VS");
                for (VerticalSubserviceInstance vssi : vsi.getVssis().values()) {
                    log.debug("Requesting termination of VSS bluperintid: " + vssi.getBlueprintId() + " in:" + vssi.getDomainId() + " instance id:" + vssi.getInstanceId());
                    TerminateVsRequest tReq = new TerminateVsRequest(vssi.getInstanceId(), this.tenantId);
                    vsRecordService.updateVssiStatusInVsi(this.vsiId, vssi.getInstanceId(), VerticalServiceStatus.TERMINATING);
                    vsLcmService.terminateVs(tReq, vssi.getDomainId());
                }

                return;
            }
            if (isMultidomain) {
                vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.TERMINATING);

                VerticalServiceInstance verticalServiceInstance = vsRecordService.getVsInstance(vsiId);
                Map<String, NetworkSliceSubnetInstance> nssis = verticalServiceInstance.getNssis();
                log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_NSI_TERMINATION_START));
                for (Map.Entry<String, NetworkSliceSubnetInstance> nssi : nssis.entrySet()) {
                    log.debug("Network slice subnet " + nssi.getValue().getNssiId() + " must be terminated.");
                    nsmfLcmProvider.terminateNetworkSliceInstance(new TerminateNsiRequest(nssi.getValue().getNssiId()), nssi.getValue().getDomainId(), tenantId);
                }
            } else {
                vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.TERMINATING);
                List<VerticalServiceInstance> vsis = vsRecordService.getVsInstancesFromNetworkSlice(networkSliceId);
                log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_NSI_TERMINATION_START));
                // Shared NSI support: if vsis > 1 nsi is shared.
                if (vsis.size() > 1) {
                    log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_NSI_TERMINATION_END));
                    nsStatusChangeOperations(NetworkSliceStatus.TERMINATED, null);
                } else {
                    log.debug("Network slice " + networkSliceId + " must be terminated.");
                    nsmfLcmProvider.terminateNetworkSliceInstance(new TerminateNsiRequest(networkSliceId), null, tenantId);
                    //vsLocalEngine.terminateNs(networkSliceId);
                }
            }
        } catch (Exception e) {
            manageVsError("Error while terminating VS " + vsiId + ": " + e.getMessage());
        }
    }

    void setInternalStatus(VerticalServiceStatus status) {
        this.internalStatus = status;
    }

    VerticalServiceStatus getInternalStatus() {
        return this.internalStatus;
    }


    private void vsStatusChangeOperations(VerticalServiceStatus subserviceStatus, String vssiId) throws NotExistingEntityException {
        try {

            if ((internalStatus == VerticalServiceStatus.INSTANTIATING_LOCAL_VSS || internalStatus == VerticalServiceStatus.INSTANTIATING_REMOTE_VSS)
                    && subserviceStatus == VerticalServiceStatus.INSTANTIATED) {
                //VerticalServiceInstance vsi = vsRecordService.getVsInstance(this.vsiId);
                vsRecordService.updateVssiStatusInVsi(this.vsiId, vssiId, VerticalServiceStatus.INSTANTIATED);
                //Vertical subservice instances for the local domain are created after completing the remote ones
                if (vsRecordService.allVssiStatusInVsi(this.vsiId, VerticalServiceStatus.INSTANTIATED)) {
                    if (internalStatus == VerticalServiceStatus.INSTANTIATING_REMOTE_VSS) {
                        String vsdId = storedInstantiateVsiRequestMessage.getRequest().getVsdId();
                        VsDescriptor vsd = this.vsDescriptors.get(vsdId);

                        VsBlueprint vsBlueprint = this.retrieveVsBlueprint(vsd.getVsBlueprintId());
                        internalProcessLocalSiteVss(storedInstantiateVsiRequestMessage, vsd, vsBlueprint);
                    } else {
                        log.debug("Finished instantiating VSs");
                        this.internalStatus = VerticalServiceStatus.INSTANTIATED;
                        log.debug(MetricsLogger.getLogMessage(this.vsiId,MetricsLogger.VSI_INSTANTIATION_END));
                        vsRecordService.setVsStatus(this.vsiId, internalStatus);
                    }
                }
            } else if ((internalStatus == VerticalServiceStatus.TERMINATING)
                    && subserviceStatus == VerticalServiceStatus.TERMINATED) {
                vsRecordService.updateVssiStatusInVsi(this.vsiId, vssiId, VerticalServiceStatus.TERMINATED);
                //Vertical subservice instances for the local domain are created after completing the remote ones
                if (vsRecordService.allVssiStatusInVsi(this.vsiId, VerticalServiceStatus.TERMINATED)) {
                    log.debug("All vertical subservices terminated");
                    this.internalStatus = VerticalServiceStatus.TERMINATED;
                    log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_TERMINATION_END));
                    vsRecordService.setVsStatus(this.vsiId, internalStatus);
                }
            }
        } catch (FailedOperationException e) {
            this.internalStatus = VerticalServiceStatus.FAILED;
            vsRecordService.setVsStatus(this.vsiId, internalStatus);
            log.error("Error during vs status change", e);
            throw new NotExistingEntityException(e);
        } catch (MethodNotImplementedException e) {
            this.internalStatus = VerticalServiceStatus.FAILED;
            vsRecordService.setVsStatus(this.vsiId, internalStatus);
            log.error("Error during vs status change", e);
        } catch (MalformattedElementException e) {
            this.internalStatus = VerticalServiceStatus.FAILED;
            vsRecordService.setVsStatus(this.vsiId, internalStatus);
            log.error("Error during vs status change", e);
        } catch (NotPermittedOperationException e) {
            this.internalStatus = VerticalServiceStatus.FAILED;
            vsRecordService.setVsStatus(this.vsiId, internalStatus);
            log.error("Error during vs status change", e);
        }
    }

    //TODO: manage the multi-domain case
    private void nsStatusChangeOperations(NetworkSliceStatus status, String nsiId) throws NotExistingEntityException, Exception {
        VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiId);

        NetworkSliceSubnetInstance nssiEnd= vsi.getNssis().get(nsiId);
        if(nssiEnd!=null){
            String domain = nssiEnd.getDomainId();

            Map<String, String> filterParams = new HashMap<>();
            filterParams.put("NSI_ID", nsiId);
            Filter filterNsi = new Filter(filterParams);
            GeneralizedQueryRequest queryNsiRequest = new GeneralizedQueryRequest(filterNsi, null);
            NetworkSliceInstance nsi = nsmfLcmProvider.queryNetworkSliceInstance(queryNsiRequest, domain, this.tenantId).get(0);
            if(status==NetworkSliceStatus.INSTANTIATED){
                log.debug("Retrieving data from Network Slice Instance");
                log.debug("Updating VIM: "+ nsi.getVimId());
                vsRecordService.updateVimId(this.vsiId, nsi.getVimId());

                log.debug("Updating VPN external GW: "+ nsi.getVimId());
                vsRecordService.updateVsiExternalGw(this.vsiId, nsi.getExternalGwAddress());

                log.debug("Updating VPN allocated networks: "+ nsi.getAllocatedVlSubnets());
                vsRecordService.updateVsiAllocatedNetworks(this.vsiId, nsi.getAllocatedVlSubnets());

                log.debug("Updating VPN internal subnet: "+ nsi.getInternalVpnSubnets());
                vsRecordService.updateVsiInternalVpnNetworks(this.vsiId, nsi.getInternalVpnSubnets());


            }
        }


        if (isMultidomain) {
            Map<String, NetworkSliceSubnetInstance> nssis = vsi.getNssis();
            for (Map.Entry<String, NetworkSliceSubnetInstance> nssiEntry : nssis.entrySet()) {
                NetworkSliceSubnetInstance nssi = nssiEntry.getValue();
                String nssiId = nssiEntry.getKey();
                if (nssiId.equalsIgnoreCase(nsiId)) {
                    //VirtualResourceUsage nssiResourceUsage = virtualResourceCalculatorService.computeVirtualResourceUsage(nssi);
                    if (status == NetworkSliceStatus.INSTANTIATED && internalStatus == VerticalServiceStatus.INSTANTIATING) {
                        //adminService.addUsedResourcesInTenant(tenantId, nssiResourceUsage);
                        List<NstConfigurationRule> configurationRules = configurationRuleRepository.findByNstId(nssi.getNsstId());
                        if (!configurationRules.isEmpty()) {
                            vsRecordService.updateNssiStatusInVsi(vsiId, nssiId, NetworkSliceStatus.CONFIGURING);
                            log.info("Processing request to configure network slice instance {}", nssiId);
                            HashMap<String, String> configurationReqParameters = new HashMap<>();
                            // Retrieve parameters for each configuration rule
                            //TODO NOTE:workflow tested with slicer-catalogue on branch feature-arbitrator-policies
                            for (NstConfigurationRule configurationRule : configurationRules) {
                                List<String> configParams = configurationRule.getParams();
                                for (String str : configParams) {
                                    String[] splits = str.split("\\.");
                                    //nst.<nsstid>.extcp.<extcpid>.address
                                    if (splits.length == 5) {
                                        // Get the address of Nssi with id nsstid on the connection point extcpid
                                        try {
                                            NetworkSliceSubnetInstance nssiToFetch = null;
                                            for(NetworkSliceSubnetInstance networkSliceSubnetInstance : nssis.values()){
                                                if(networkSliceSubnetInstance.getNsstId().equals(splits[1]))
                                                    nssiToFetch = networkSliceSubnetInstance;
                                            }
                                            String extCpId = splits[3];
                                            if (nssiToFetch != null) {
                                                //retrieving the ip on extcpdid
                                                HashMap<String, String> filterParams = new HashMap<>();
                                                filterParams.put("NSI_ID", nssiToFetch.getNssiId());
                                                Filter filter = new Filter(filterParams);
                                                List<NetworkSliceInstance> nsiList = nsmfLcmProvider.queryNetworkSliceInstance(new GeneralizedQueryRequest(filter, null), nssiToFetch.getDomainId(), tenantId);
                                                String address = null;
                                                if (!nsiList.isEmpty()) {
                                                    for (NsSapInfo nsSapInfo : nsiList.get(0).getSapInfo()) {
                                                        if (nsSapInfo.getSapName().equals(extCpId)) {
                                                            address = nsSapInfo.getAddress();
                                                            break;
                                                        }
                                                    }
                                                }
                                                configurationReqParameters.put(configurationRule.getId().toString(), address);
                                            } else log.error("Nst with id: " + splits[1] + " not found.");
                                        } catch (Exception e) {
                                            manageVsError("Invalid params in configuration rule.");
                                        }
                                    } else manageVsError("Invalid params in configuration rule.");
                                }
                            }
                            nsmfLcmProvider.configureNetworkSliceInstance(new ConfigureNsiRequest(nssiId, nssi.getNsstId(), configurationReqParameters), nssi.getDomainId(), tenantId);
                        } else {
                            vsRecordService.updateNssiStatusInVsi(vsiId, nssiId, NetworkSliceStatus.INSTANTIATED);
                            if (vsRecordService.allNssiStatusInVsi(vsiId, NetworkSliceStatus.INSTANTIATED)) {
                                internalStatus = VerticalServiceStatus.INSTANTIATED;
                                log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_INSTANTIATION_END));
                                vsRecordService.setVsStatus(vsiId, internalStatus);
                                vsLcmConsumerInterface.notifyVerticalServiceStatusChange(new VerticalServiceStatusChangeNotification(this.vsiId,
                                        VerticalServiceStatusChange.VSI_CREATED, true), null);
                                log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure completed.");
                            }
                        }
                    } else if (status == NetworkSliceStatus.CONFIGURED && internalStatus == VerticalServiceStatus.INSTANTIATING) {
                        //adminService.addUsedResourcesInTenant(tenantId, nssiResourceUsage);
                        vsRecordService.updateNssiStatusInVsi(vsiId, nssiId, NetworkSliceStatus.INSTANTIATED);
                        if (vsRecordService.allNssiStatusInVsi(vsiId, NetworkSliceStatus.INSTANTIATED)) {
                            internalStatus = VerticalServiceStatus.INSTANTIATED;
                            log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_INSTANTIATION_END));
                            vsRecordService.setVsStatus(vsiId, internalStatus);
                            log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure completed");
                            vsLcmConsumerInterface.notifyVerticalServiceStatusChange(new VerticalServiceStatusChangeNotification(this.vsiId,
                                    VerticalServiceStatusChange.VSI_CREATED, true), null);
                        } else {
                            log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure still ongoing");
                        }
                    } else if (status == NetworkSliceStatus.TERMINATED && internalStatus == VerticalServiceStatus.TERMINATING) {
                        //adminService.removeUsedResourcesInTenant(tenantId, nssiResourceUsage);
                        vsRecordService.updateNssiStatusInVsi(vsiId, nssi.getNssiId(), NetworkSliceStatus.TERMINATED);
                        if (vsRecordService.allNssiStatusInVsi(vsiId, NetworkSliceStatus.TERMINATED)) {
                            log.debug("Updated resource usage for tenant " + tenantId + ". Termination procedure completed. - Notifying the engine");
                            vsLcmService.notifyVsiTermination(vsiId);
                            internalStatus = VerticalServiceStatus.TERMINATED;
                            log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_TERMINATION_END));
                            vsRecordService.setVsStatus(vsiId, internalStatus);
                            vsLcmConsumerInterface.notifyVerticalServiceStatusChange(new VerticalServiceStatusChangeNotification(this.vsiId,
                                    VerticalServiceStatusChange.VSI_TERMINATED, true), null);
                        } else {
                            log.debug("Updated resource usage for tenant " + tenantId + ". Termination procedure completed still ongoing");
                        }
                    } else if (status == NetworkSliceStatus.MODIFIED) {
                        manageVsError("NSI modification not yet supported in multi-domain scenario");
                        return;
                    } else {
                        manageVsError("Received notification about NSI + " + nsiId + " creation/termination in wrong status: " + status + "/" + internalStatus);
                        return;
                    }
                }
            }
        } else {

            NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(nsiId, null, tenantId);
            VirtualResourceUsage resourceUsage = virtualResourceCalculatorService.computeVirtualResourceUsage(nsi, true);
            if (status == NetworkSliceStatus.INSTANTIATED && internalStatus == VerticalServiceStatus.INSTANTIATING) {
                log.debug("Updating Vertical service instance internal network slice subnet");
                //In the single domain case, a NetworkSliceSubnet was added to store the information about
                //the VNF placement

                Map<String, NetworkSliceSubnetInstance> nsis = vsi.getNssis();
                //Removed size control
                if (nsis != null && nsis.containsKey(nsiId)) {
                    this.retrieveNssiTree(nsiId, null);
                    List<NstConfigurationRule> nstConfigurationRuleList = configurationRuleRepository.findByNstId(storedNfvNsInstantiationInfo.getNstId());

                    if (nstConfigurationRuleList.isEmpty()) {
                        vsRecordService.updateNssiStatusInVsi(vsiId, nsiId, NetworkSliceStatus.INSTANTIATED);
                        internalStatus = VerticalServiceStatus.INSTANTIATED;
                        log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_INSTANTIATION_END));
                        adminService.addUsedResourcesInTenant(tenantId, resourceUsage);
                        vsLcmConsumerInterface.notifyVerticalServiceStatusChange(new VerticalServiceStatusChangeNotification(this.vsiId,
                                VerticalServiceStatusChange.VSI_CREATED, true), null);
                        log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure completed.");
                    } else {
                        vsRecordService.updateNssiStatusInVsi(vsiId, nsiId, NetworkSliceStatus.CONFIGURING);
                        nsmfLcmProvider.configureNetworkSliceInstance(new ConfigureNsiRequest(nsiId, nsis.get(nsiId).getNsstId(), null), nsis.get(nsiId).getDomainId(), tenantId);
                    }
                } else {
                    log.error("Invalid VSi to NSI mapping. This should not happen");
                    manageVsError("Invalid VSi to NSI mapping. This should not happen");
                }

            } else if (status == NetworkSliceStatus.CONFIGURED && internalStatus == VerticalServiceStatus.INSTANTIATING) {
                log.debug("Updating Vertical service instance internal network slice subnet");
                //In the single domain case, a NetworkSliceSubnet was added to store the information about
                //the VNF placement

                Map<String, NetworkSliceSubnetInstance> nsis = vsi.getNssis();
                //Removed size control
                if (nsis != null && nsis.containsKey(nsiId)) {
                    this.retrieveNssiTree(nsiId, null);
                    vsRecordService.updateNssiStatusInVsi(vsiId, nsiId, NetworkSliceStatus.INSTANTIATED);
                } else {
                    log.error("Invalid VSi to NSI mapping. This should not happen");
                    manageVsError("Invalid VSi to NSI mapping. This should not happen");
                }
                internalStatus = VerticalServiceStatus.INSTANTIATED;
                log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_INSTANTIATION_END));
                adminService.addUsedResourcesInTenant(tenantId, resourceUsage);
                vsLcmConsumerInterface.notifyVerticalServiceStatusChange(new VerticalServiceStatusChangeNotification(this.vsiId,
                        VerticalServiceStatusChange.VSI_CREATED, true), null);
                log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure completed.");

            } else if (status == NetworkSliceStatus.TERMINATED && internalStatus == VerticalServiceStatus.TERMINATING) {
                Map<String, NetworkSliceSubnetInstance> nsis = vsi.getNssis();
                if (nsis != null && nsis.containsKey(nsiId) && nsis.size() == 1) {
                    vsRecordService.updateNssiStatusInVsi(vsiId, nsiId, NetworkSliceStatus.TERMINATED);
                } else {
                    log.error("Invalid VSi to NSI mapping. This should not happen");
                    manageVsError("Invalid VSi to NSI mapping. This should not happen");
                }

                internalStatus = VerticalServiceStatus.TERMINATED;
                log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_TERMINATION_END));
                adminService.removeUsedResourcesInTenant(tenantId, resourceUsage);
                vsLcmConsumerInterface.notifyVerticalServiceStatusChange(new VerticalServiceStatusChangeNotification(this.vsiId,
                        VerticalServiceStatusChange.VSI_TERMINATED, true), null);
                log.debug("Updated resource usage for tenant " + tenantId + ". Termination procedure completed. - Notifying the engine");
                //vsLocalEngine.notifyVsiTermination(vsiId);
                vsLcmService.notifyVsiTermination(vsiId);

            } else if (status == NetworkSliceStatus.MODIFIED && internalStatus == VerticalServiceStatus.UNDER_MODIFICATION) {
                VirtualResourceUsage oldResourceUsage = virtualResourceCalculatorService.computeVirtualResourceUsage(nsi, false);
                adminService.removeUsedResourcesInTenant(tenantId, oldResourceUsage);
                adminService.addUsedResourcesInTenant(tenantId, resourceUsage);

                //TODO: check with Pietro what this is...
                //Shall we put this into the NSMF? Commented at the moment
                //vsRecordService.resetOldNsInstantiationLevel(nsi.getNsiId());

                internalStatus = VerticalServiceStatus.INSTANTIATED;
                log.debug("VS Modification procedure completed.");

            } else {
                manageVsError("Received notification about NSI creation in wrong status.");
                return;
            }
            vsRecordService.setVsStatus(vsiId, internalStatus);
        }
        log.debug("Vertical Service Instance with id {} is {}", vsiId, internalStatus);
    }

    void processNsiStatusChangeNotification(NotifyNsiStatusChange msg) {
        if (!((internalStatus == VerticalServiceStatus.INSTANTIATING) || (internalStatus == VerticalServiceStatus.TERMINATING) || (internalStatus == VerticalServiceStatus.UNDER_MODIFICATION))) {
            manageVsError("Received NSI status change notification in wrong status. Skipping message.");
            return;
        }
        NetworkSliceStatusChange nsStatusChange = msg.getStatusChange();
        String nsiId = msg.getNsiId();
        try {
            switch (nsStatusChange) {
                case NSI_CREATED: {
                    log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_NSI_INSTANTIATION_END));
                    nsStatusChangeOperations(NetworkSliceStatus.INSTANTIATED, nsiId);
                    break;
                }
                case NSI_CONFIGURED: {
                    nsStatusChangeOperations(NetworkSliceStatus.CONFIGURED, nsiId);
                    break;
                }
                case NSI_MODIFIED: {
                    nsStatusChangeOperations(NetworkSliceStatus.MODIFIED, nsiId);
                    break;
                }
                case NSI_TERMINATED: {
                    log.debug(MetricsLogger.getLogMessage(vsiId,MetricsLogger.VSI_NSI_TERMINATION_END));
                    nsStatusChangeOperations(NetworkSliceStatus.TERMINATED, nsiId);
                    break;
                }
                case NSI_FAILED: {
                    manageVsError("Received notification about network slice " + msg.getNsiId() + " failure");
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            manageVsError("Error while processing NSI status change notification: " + e.getMessage());
        }
    }

    void processVsiStatusChangeNotification(NotifyVsiStatusChange msg) {
        if (!((internalStatus == VerticalServiceStatus.INSTANTIATING_LOCAL_VSS) || (internalStatus == VerticalServiceStatus.INSTANTIATING_REMOTE_VSS)
                || (internalStatus == VerticalServiceStatus.TERMINATING))) {
            manageVsError("Received VSSI status change notification in wrong status. Skipping message.");
            return;
        }
        VerticalServiceStatusChange vsStatusChange = msg.getStatusChange();
        String vssiId = msg.getVsiId();
        try {
            switch (vsStatusChange) {
                case VSI_CREATED: {

                    vsStatusChangeOperations(VerticalServiceStatus.INSTANTIATED, vssiId);
                    break;
                }
                case VSI_MODIFIED: {
                    vsStatusChangeOperations(VerticalServiceStatus.MODIFIED, vssiId);
                    break;
                }
                case VSI_TERMINATED: {
                    vsStatusChangeOperations(VerticalServiceStatus.TERMINATED, vssiId);
                    break;
                }

                case VSI_FAILED: {
                    manageVsError("Received notification about vertical subservice" + msg.getVsiId() + " failure");
                    break;
                }

                default:
                    break;

            }
        } catch (Exception e) {
            manageVsError("Error while processing NSI status change notification: " + e.getMessage());
        }
    }

    private Map<String, String> getUserDataVnfPlacement(Map<String, NetworkSliceVnfPlacement> nsVnfPlacements) {
        log.debug("Mapping NS VNF placement to userData parameters");
        Map<String, String> userData = new HashMap<>();
        if (nsVnfPlacements != null) {
            for (Entry<String, NetworkSliceVnfPlacement> placementEntry : nsVnfPlacements.entrySet()) {
                userData.put("vnf.placement." + placementEntry.getKey(), placementEntry.getValue().toString());
            }
        }
        log.debug(userData.toString());
        return userData;

    }

    private Map<String, NetworkSliceVnfPlacement> getNetworkSliceVnfPlacements(Map<String, VsComponentPlacement> vnfPlacement) throws FailedOperationException {
        log.debug("Retrieving VS desired VNF placement in NS");
        Map<String, NetworkSliceVnfPlacement> nsVnfPlacement = new HashMap<>();
        for (Entry<String, VsComponentPlacement> placementEntry : vnfPlacement.entrySet()) {

            NetworkSliceVnfPlacement nsEntryPlacement;
            if (placementEntry.getValue() == VsComponentPlacement.CLOUD) {
                nsEntryPlacement = NetworkSliceVnfPlacement.CLOUD;
            } else if (placementEntry.getValue() == VsComponentPlacement.EDGE) {
                nsEntryPlacement = NetworkSliceVnfPlacement.EDGE;
            } else throw new FailedOperationException("Unsupported VNF placement type:" + placementEntry.getValue());

            nsVnfPlacement.put(placementEntry.getKey(), nsEntryPlacement);
        }
        log.debug(nsVnfPlacement.toString());
        return nsVnfPlacement;
    }

    private VsBlueprint retrieveVsBlueprint(String vsBlueprintId) throws FailedOperationException, NotExistingEntityException {
        log.debug("Retrieving VSB:" + vsBlueprintId);
        try {
            QueryVsBlueprintResponse response =
                    vsBlueprintCatalogueService.queryVsBlueprint(
                            new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVsBlueprintFilter(vsBlueprintId), null));
            log.debug("Assuming one VSB");
            return response.getVsBlueprintInfo().get(0).getVsBlueprint();
        } catch (MethodNotImplementedException e) {
            log.error("Exception while retrieving VSB:" + vsBlueprintId, e);
            throw new FailedOperationException(e);
        } catch (MalformattedElementException e) {
            log.error("Exception while retrieving VSB:" + vsBlueprintId, e);
            throw new FailedOperationException(e);
        }
    }

    /**
     * @return Map key vnfdId, value the correspondent VsComponentPlacement
     */

    private Map<String, VsComponentPlacement> retrieveVsBlueprintVnfPlacement(VsBlueprint vsBlueprint) throws FailedOperationException, NotExistingEntityException {
        log.debug("Retrieving VSB Component Placement info for vsb:" + vsBlueprint.getBlueprintId());

        List<VsComponent> atomicComponents = vsBlueprint.getAtomicComponents();
        if (atomicComponents != null) {
            Map<String, VsComponentPlacement> componentPlacement = new HashMap<>();
            for (VsComponent currentComponent : atomicComponents) {
                if (currentComponent.getType() == VsComponentType.FUNCTION) {
                    if (currentComponent.getPlacement() != null) {
                        String key = currentComponent.getComponentId();
                        log.debug("Adding component placement:" + key + " - " + currentComponent.getPlacement());
                        componentPlacement.put(key, currentComponent.getPlacement());
                    }
                }


            }
            return componentPlacement;
        } else {
            log.debug("No atomic components identified");
            return new HashMap<>();
        }

    }

    //This method is used to retrieve the NSSI hierachy once the NS has been instantiated
    private void retrieveNssiTree(String nssiId, String domain) throws MalformattedElementException, FailedOperationException, MethodNotImplementedException, NotExistingEntityException {
        log.debug("Retrieving NSSI tree for NSI:" + nssiId);
        Map<String, String> filterParms = new HashMap<>();
        filterParms.put("NSI_ID", nssiId);
        Filter filter = new Filter(filterParms);
        GeneralizedQueryRequest request = new GeneralizedQueryRequest(filter, null);

        List<NetworkSliceInstance> nsis = nsmfLcmProvider.queryNetworkSliceInstance(request, domain, tenantId);
        if (nsis != null && !nsis.isEmpty()) {
            NetworkSliceInstance currentNsi = nsis.get(0);
            Map<String, NetworkSliceSubnetInstance> vsiNsi = vsRecordService.getNssiInVsi(this.vsiId);
            if (!vsiNsi.containsKey(nssiId)) {
                NetworkSliceSubnetInstance currentNssi = new NetworkSliceSubnetInstance(nssiId, currentNsi.getNstId(), domain, currentNsi.getDfId(),
                        currentNsi.getInstantiationLevelId(), NetworkSliceStatus.INSTANTIATED, null);
                vsRecordService.addNssiInVsi(vsiId, currentNssi);
            }
            vsRecordService.updateVsiNsiVnfPlacement(this.vsiId, nssiId, currentNsi.getVnfPlacement());
            if(currentNsi.getVimId()!=null){
                log.debug("Updating VIM information from NS");
                vsRecordService.updateVimId(this.vsiId, currentNsi.getVimId());
            }
            if (currentNsi.getNetworkSliceSubnetInstances() != null) {
                for (String subNssiId : currentNsi.getNetworkSliceSubnetInstances()) {
                    retrieveNssiTree(subNssiId, domain);
                }
            }
        } else throw new FailedOperationException("Unable to retrieve NSI with id:" + nssiId);

    }

    public List<String> getPendingNsiModificationIds() {
        return pendingNsiModificationIds;
    }

    private void manageVsError(String errorMessage, Exception e) {
        log.error(errorMessage, e);
        vsRecordService.setVsFailureInfo(vsiId, errorMessage);
    }

    private void manageVsError(String errorMessage) {
        log.error(errorMessage);
        vsRecordService.setVsFailureInfo(vsiId, errorMessage);
    }
}
