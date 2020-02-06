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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChange;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsmfUtils;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.VsmfEngineMessage;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.VsmfEngineMessageType;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.InstantiateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.ModifyVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.NotifyNsiStatusChange;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.NotifyResourceGranted;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.TerminateVsiRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;

import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;

/**
 * Entity in charge of managing the lifecycle
 * of a single vertical service instance
 *
 * @author nextworks
 *
 */
public class VsLcmManager {

    private static final Logger log = LoggerFactory.getLogger(VsLcmManager.class);
    private String vsiId;
    private String vsiName;
    private VsRecordService vsRecordService;
    private TranslatorService translatorService;
    private ArbitratorService arbitratorService;
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;
    private AdminService adminService;
    private VsLcmService vsLcmService;
    private VirtualResourceCalculatorService virtualResourceCalculatorService;
    private VerticalServiceStatus internalStatus;
    private NsmfLcmProviderInterface nsmfLcmProvider;
    private VsmfUtils vsmfUtils;

    private List<String> nestedVsi = new ArrayList<>();

    private String networkSliceId;

    //Key: VSD ID; Value: VSD
    private Map<String, VsDescriptor> vsDescriptors = new HashMap<>();
    private String tenantId;

    // the following is for the WAITING_FOR_RESOURCES status
    ArbitratorResponse storedResponse;
    NfvNsInstantiationInfo storedNfvNsInstantiationInfo;
    InstantiateVsiRequestMessage storedInstantiateVsiRequestMessage;

    /**
     * Constructor
     *
     * @param vsiId                  ID of the vertical service instance
     * @param vsRecordService        wrapper of VSI record
     * @param vsDescriptorCatalogueService repo of VSDs
     * @param translatorService      translator service
     * @param arbitratorService      arbitrator service
     * @param adminService           admin service
     * @param nfvoCatalogueService            NFVO catalogue service
     * @param vsLcmService                 engine
     * @param virtualResourceCalculatorService virtual resource calculator service
     * @param nsmfLcmProvider
     * @param vsmfUtils
     */
    public VsLcmManager(String vsiId,
    		            String vsiName,
                        VsRecordService vsRecordService,
                        VsDescriptorCatalogueService vsDescriptorCatalogueService,
                        TranslatorService translatorService,
                        ArbitratorService arbitratorService,
                        AdminService adminService,
                        VsLcmService vsLcmService, 
                        VirtualResourceCalculatorService virtualResourceCalculatorService,
                        NsmfLcmProviderInterface nsmfLcmProvider,
                        VsmfUtils vsmfUtils) {
        this.vsiId = vsiId;
        this.vsiName=vsiName;
        this.vsRecordService = vsRecordService;
        this.vsDescriptorCatalogueService = vsDescriptorCatalogueService;
        this.translatorService = translatorService;
        this.arbitratorService = arbitratorService;
        this.adminService = adminService;
        this.internalStatus = VerticalServiceStatus.INSTANTIATING;
        this.vsLcmService = vsLcmService;
        this.virtualResourceCalculatorService=virtualResourceCalculatorService;
        this.nsmfLcmProvider = nsmfLcmProvider;
        this.vsmfUtils = vsmfUtils;
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
                case RESOURCES_GRANTED: {
                    log.debug("Processing resources granted notification.");
                    NotifyResourceGranted notifyResourceGranted = (NotifyResourceGranted) em;
                    processResourcesGrantedNotification(notifyResourceGranted);
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
        String vsdId = msg.getRequest().getVsdId();
        log.debug("Instantiating Vertical Service " + vsiId + " with VSD " + vsdId);
        try {
        	VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
        	this.vsDescriptors.put(vsdId, vsd);
        	this.tenantId = msg.getRequest().getTenantId();

        	List<String> vsdIds = new ArrayList<>();
        	vsdIds.add(vsdId);
        
        	Map<String, NfvNsInstantiationInfo> nsInfo = translatorService.translateVsd(vsdIds);
            log.debug("The VSD has been translated in the required network slice characteristics.");

            List<ArbitratorRequest> arbitratorRequests = new ArrayList<>();
            //only a single request is supported at the moment
            ArbitratorRequest arbitratorRequest = new ArbitratorRequest("requestId", tenantId, vsd, nsInfo);
            arbitratorRequests.add(arbitratorRequest);
            ArbitratorResponse arbitratorResponse = arbitratorService.computeArbitratorSolution(arbitratorRequests).get(0);
            if (!(arbitratorResponse.isAcceptableRequest())) {
                if(arbitratorResponse.getImpactedVerticalServiceInstances().isEmpty()) {
                    manageVsError("Error while instantiating VS " + vsiId + ": no solution returned from the arbitrator");

                } else {
                    setInternalStatus(VerticalServiceStatus.WAITING_FOR_RESOURCES);
                    vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.WAITING_FOR_RESOURCES);
                    // store interesting info
                    storedResponse = arbitratorResponse;
                    storedNfvNsInstantiationInfo = nsInfo.get(vsdId);
                    storedInstantiateVsiRequestMessage = msg;

                    //invoke engine for VsGroupCoordinator.
                    vsLcmService.requestVsiCoordination(vsiId, arbitratorResponse.getImpactedVerticalServiceInstances());

                }
                return;
            }
            if (arbitratorResponse.isNewSliceRequired()) {
                log.debug("A new network slice should be instantiated for the Vertical Service instance " + vsiId);


                NfvNsInstantiationInfo nsiInfo = nsInfo.get(vsdId);
                nsiInfo.setDeploymentFlavourId("DF test");
                //TODO: to be extended for composite VSDs

                List<String> nsSubnetInstanceIds;
                if (arbitratorResponse.getExistingSliceSubnets().isEmpty())
                    nsSubnetInstanceIds = new ArrayList<>();
                else
                    nsSubnetInstanceIds = new ArrayList<>(arbitratorResponse.getExistingSliceSubnets().keySet());


                CreateNsiIdRequest request = new CreateNsiIdRequest(nsiInfo.getNstId(), 
                		"NS - " + vsiName, 
                		"Network slice for VS " + vsiName);
                String nsiId = nsmfLcmProvider.createNetworkSliceIdentifier(request, tenantId);

//                String nsiId = vsRecordService.createNetworkSliceForVsi(vsiId, nsiInfo.getNfvNsdId(), nsiInfo.getNsdVersion(), nsiInfo.getDeploymentFlavourId(),
//                        nsiInfo.getInstantiationLevelId(), nsSubnetInstanceIds, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
                log.debug("Network Slice ID " + nsiId + " created for VSI " + vsiId);
                setNetworkSliceId(nsiId);
                vsRecordService.setNsiInVsi(vsiId, nsiId);

                //Add nested VSI if any
                for (String nestedNsiId : nsSubnetInstanceIds) {
                    VerticalServiceInstance nestedVsi = vsRecordService.getVsInstancesFromNetworkSlice(nestedNsiId).get(0);
                    this.nestedVsi.add(nestedVsi.getVsiId());
                    // TODO notify record to add sub-instance to main instance
                    vsRecordService.addNestedVsInVerticalServiceInstance(vsiId, nestedVsi);

                }
                log.debug("Record updated with info about NSI and VSI association.");
                InstantiateNsiRequest instantiateNsiReq = new InstantiateNsiRequest(nsiId, 
                		nsiInfo.getNstId(),
                		nsiInfo.getDeploymentFlavourId(),
                		nsiInfo.getInstantiationLevelId(),
                		nsSubnetInstanceIds,
                		msg.getRequest().getUserData(),
                		msg.getRequest().getLocationConstraints(),
                		msg.getRanEndpointId());
                log.debug("Sending request to instantiate network slice ");
                nsmfLcmProvider.instantiateNetworkSlice(instantiateNsiReq, tenantId);
                
                //vsLocalEngine.initNewNsLcmManager(networkSliceId, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
                //vsLocalEngine.instantiateNs(nsiId, tenantId, nsiInfo.getNfvNsdId(), nsiInfo.getNsdVersion(),
                //         nsiInfo.getDeploymentFlavourId(), nsiInfo.getInstantiationLevelId(), vsiId, nsSubnetInstanceIds);
            } else {
                //slice to be shared, not supported at the moment
                manageVsError("Error while instantiating VS " + vsiId + ": solution with slice sharing returned from the arbitrator. Not supported at the moment.");
            }
        } catch (Exception e) {
            manageVsError("Error while instantiating VS " + vsiId + ": " + e.getMessage());
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

        try{
            internalStatus = VerticalServiceStatus.INSTANTIATING;
            vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.INSTANTIATING);

            InstantiateVsiRequestMessage msg = storedInstantiateVsiRequestMessage;
            if (storedResponse.isNewSliceRequired()) {
                log.debug("A new network slice should be instantiated for the Vertical Service instance " + vsiId);


                NfvNsInstantiationInfo nsiInfo = storedNfvNsInstantiationInfo;
                //TODO: to be extended for composite VSDs

                List<String> nsSubnetInstanceIds;
                if (storedResponse.getExistingSliceSubnets().isEmpty())
                    nsSubnetInstanceIds = new ArrayList<>();
                else
                    nsSubnetInstanceIds = new ArrayList<>(storedResponse.getExistingSliceSubnets().keySet());

                CreateNsiIdRequest request = new CreateNsiIdRequest(nsiInfo.getNstId(), 
                		"NS - " + vsiName, 
                		"Network slice for VS " + vsiName);
                String nsiId = nsmfLcmProvider.createNetworkSliceIdentifier(request, tenantId);
                
                //String nsiId = vsRecordService.createNetworkSliceForVsi(vsiId, nsiInfo.getNfvNsdId(), nsiInfo.getNsdVersion(), nsiInfo.getDeploymentFlavourId(),
                //        nsiInfo.getInstantiationLevelId(), nsSubnetInstanceIds, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
                log.debug("Network Slice ID " + nsiId + " created for VSI " + vsiId);
                setNetworkSliceId(nsiId);
                vsRecordService.setNsiInVsi(vsiId, nsiId);

                //Add nested VSI if any
                for (String nestedNsiId : nsSubnetInstanceIds) {
                    VerticalServiceInstance nestedVsi = vsRecordService.getVsInstancesFromNetworkSlice(nestedNsiId).get(0);
                    this.nestedVsi.add(nestedVsi.getVsiId());
                    // TODO notify record to add sub-instance to main instance
                    vsRecordService.addNestedVsInVerticalServiceInstance(vsiId, nestedVsi);

                }
                log.debug("Record updated with info about NSI and VSI association.");
                
                InstantiateNsiRequest instantiateNsiReq = new InstantiateNsiRequest(nsiId, 
                		nsiInfo.getNstId(),
                		nsiInfo.getDeploymentFlavourId(),
                		nsiInfo.getInstantiationLevelId(),
                		nsSubnetInstanceIds,
                		msg.getRequest().getUserData(),
                		msg.getRequest().getLocationConstraints(),
                		msg.getRanEndpointId());
                log.debug("Sending request to instantiate network slice ");
                nsmfLcmProvider.instantiateNetworkSlice(instantiateNsiReq, tenantId);
                
                //vsLocalEngine.initNewNsLcmManager(networkSliceId, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
                //vsLocalEngine.instantiateNs(nsiId, tenantId, nsiInfo.getNfvNsdId(), nsiInfo.getNsdVersion(),
                //        nsiInfo.getDeploymentFlavourId(), nsiInfo.getInstantiationLevelId(), vsiId, nsSubnetInstanceIds);
                
                
                // put all stored object @null
                storedInstantiateVsiRequestMessage = null;
                storedNfvNsInstantiationInfo = null;
                storedResponse = null;
            } else {
                //slice to be shared, not supported at the moment
                manageVsError("Error while instantiating VS " + vsiId + ": solution with slice sharing returned from the arbitrator. Not supported at the moment.");
            }
        } catch (Exception e) {
            manageVsError("Error while instantiating VS " + vsiId + ": " + e.getMessage());
        }
    }

     void processModifyRequest(ModifyVsiRequestMessage msg){
        if (!msg.getVsiId().equals(vsiId)) {
            throw new IllegalArgumentException(String.format("Wrong VSI ID: %s", msg.getVsiId()));
        }
        if (internalStatus != VerticalServiceStatus.INSTANTIATED) {
            manageVsError("Received termination request in wrong status. Skipping message.");
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
            
            NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(networkSliceId, tenantId);
            String currentDfId = nsi.getDfId();
            String currentInstantiationLevelId = nsi.getInstantiationLevelId();
            String currentNsdId = nsi.getNsdId();

			/*compare the triples
				Case 1: only ILs are different -> Scale
				Case 2: nothing is different -> set the new VSDid
				Case 3: either nsdIds or Dfs are different -> Error
			 */
            if(newNsdId.equals(currentNsdId) && newDfId.equals(currentDfId)){
                if(newInstantiationLevelId.equals(currentInstantiationLevelId)){
                    //Case 2
                    log.debug("New vsd set.");
                    this.vsDescriptors.put(vsdId, vsd);
                }else{
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
                    nsmfLcmProvider.modifyNetworkSlice(modifyNsiRequest, tenantId);
                    //vsLocalEngine.modifyNs(nsi.getNsiId(), tenantId, currentNsdId, nsInfo.getNsdVersion(), newDfId, newInstantiationLevelId, vsiId);
                }
            }else{
                // Case 3
                manageVsError("Error while modifying VS " + vsiId + ": Deployment Flavour and Nsd update are not supported yet");
            }

        } catch (Exception e) {
            manageVsError("Error while modifying VS " + vsiId + ": " + e.getMessage());
        }

    }

    void processTerminateRequest(TerminateVsiRequestMessage msg) {
        if (!msg.getVsiId().equals(vsiId)) {
            throw new IllegalArgumentException(String.format("Wrong VSI ID: %s", msg.getVsiId()));
        }
        if (internalStatus != VerticalServiceStatus.INSTANTIATED) {
            manageVsError("Received termination request in wrong status. Skipping message.");
            return;
        }

        log.debug("Terminating Vertical Service " + vsiId);
        this.internalStatus = VerticalServiceStatus.TERMINATING;
        try {
            vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.TERMINATING);
            List<VerticalServiceInstance> vsis = vsRecordService.getVsInstancesFromNetworkSlice(networkSliceId);
            // Shared NSI support: if vsis > 1 nsi is shared.
            if (vsis.size() > 1) {
                nsStatusChangeOperations(VerticalServiceStatus.TERMINATED);
            } else {
                log.debug("Network slice " + networkSliceId + " must be terminated.");
                nsmfLcmProvider.terminateNetworkSliceInstance(new TerminateNsiRequest(networkSliceId), tenantId);
                //vsLocalEngine.terminateNs(networkSliceId);
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

    private void nsStatusChangeOperations(VerticalServiceStatus status) throws NotExistingEntityException, Exception {

    	NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(networkSliceId, tenantId);
        //NetworkSliceInstance nsi = vsRecordService.getNsInstance(networkSliceId);
        VirtualResourceUsage resourceUsage = virtualResourceCalculatorService.computeVirtualResourceUsage( nsi, true);
        if (status == VerticalServiceStatus.INSTANTIATED && internalStatus == VerticalServiceStatus.INSTANTIATING) {
            adminService.addUsedResourcesInTenant(tenantId, resourceUsage);
            log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure completed.");
        } else if (status == VerticalServiceStatus.TERMINATED && internalStatus == VerticalServiceStatus.TERMINATING) {
            adminService.removeUsedResourcesInTenant(tenantId, resourceUsage);
            log.debug("Updated resource usage for tenant " + tenantId + ". Termination procedure completed. - Notifying the engine");
            //vsLocalEngine.notifyVsiTermination(vsiId);
            vsLcmService.notifyVsiTermination(vsiId);

        } else if (status == VerticalServiceStatus.MODIFIED && internalStatus == VerticalServiceStatus.UNDER_MODIFICATION) {
            VirtualResourceUsage oldResourceUsage = virtualResourceCalculatorService.computeVirtualResourceUsage( nsi, false);
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
        this.internalStatus = status;
        vsRecordService.setVsStatus(vsiId, status);
    }

    void processNsiStatusChangeNotification(NotifyNsiStatusChange msg) {
        if (!((internalStatus == VerticalServiceStatus.INSTANTIATING) || (internalStatus == VerticalServiceStatus.TERMINATING) || (internalStatus == VerticalServiceStatus.UNDER_MODIFICATION))) {
            manageVsError("Received NSI status change notification in wrong status. Skipping message.");
            return;
        }
        NetworkSliceStatusChange nsStatusChange = msg.getStatusChange();
        try {
            switch (nsStatusChange) {
                case NSI_CREATED: {

                    nsStatusChangeOperations(VerticalServiceStatus.INSTANTIATED);
                    break;
                }
                case NSI_MODIFIED: {
                    nsStatusChangeOperations(VerticalServiceStatus.MODIFIED);
                    break;
                }
                case NSI_TERMINATED: {
                    nsStatusChangeOperations(VerticalServiceStatus.TERMINATED);
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

    private void manageVsError(String errorMessage) {
        log.error(errorMessage);
        vsRecordService.setVsFailureInfo(vsiId, errorMessage);
    }






}
