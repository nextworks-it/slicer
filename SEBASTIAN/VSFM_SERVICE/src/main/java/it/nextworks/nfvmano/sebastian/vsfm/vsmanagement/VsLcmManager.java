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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.domainLayer.Domain;
import it.nextworks.nfvmano.catalogue.domainLayer.DomainInterface;
import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.common.ActuationRequest;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.*;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.NetworkSliceInternalInfo;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsmfUtils;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity in charge of managing the lifecycle
 * of a single vertical service instance
 *
 * @author nextworks
 *
 */
public class VsLcmManager {

    private static final Logger log = LoggerFactory.getLogger(VsLcmManager.class);
    private String vsiUuid;
    private String vsiName;
    private VsRecordService vsRecordService;
    private TranslatorService translatorService;
    private ArbitratorService arbitratorService;
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;
    private AdminService adminService;
    private VsLcmService vsLcmService;
    private VirtualResourceCalculatorService virtualResourceCalculatorService;
    private DomainCatalogueService domainCatalogueService;
    private VerticalServiceStatus internalStatus;
    private NsmfLcmProviderInterface nsmfLcmProvider;
    private VsmfUtils vsmfUtils;

    private List<String> nestedVsi = new ArrayList<>();

    private String networkSliceUuid;
    private Map<String,NetworkSliceInternalInfo> domainIdNetworkSliceInternalInfoMap;
    //Key: VSD UUID; Value: VSD
    private Map<String, VsDescriptor> vsDescriptors = new HashMap<>();
    private String tenantId;

    //Key: network slice UUID. Value: object containing the nsmf client in order to interact with NSP and the NST used to create and then instanciate the network slice.
    private HashMap<String, NspNetworkSliceInteraction> nsiUuidNetworkSliceInfoMap = new HashMap<String, NspNetworkSliceInteraction>();
    // the following is for the WAITING_FOR_RESOURCES status
    ArbitratorResponse storedResponse;
    NfvNsInstantiationInfo storedNfvNsInstantiationInfo;
    InstantiateVsiRequestMessage storedInstantiateVsiRequestMessage;

    /**
     * Constructor
     *
     * @param vsiUuid                  ID of the vertical service instance
     * @param vsRecordService        wrapper of VSI record
     * @param vsDescriptorCatalogueService repo of VSDs
     * @param translatorService      translator service
     * @param arbitratorService      arbitrator service
     * @param adminService           admin service
     * @param vsLcmService                 engine
     * @param virtualResourceCalculatorService virtual resource calculator service
     * @param nsmfLcmProvider
     * @param vsmfUtils
     */
    public VsLcmManager(String vsiUuid,
    		            String vsiName,
                        VsRecordService vsRecordService,
                        VsDescriptorCatalogueService vsDescriptorCatalogueService,
                        TranslatorService translatorService,
                        ArbitratorService arbitratorService,
                        AdminService adminService,
                        VsLcmService vsLcmService, 
                        VirtualResourceCalculatorService virtualResourceCalculatorService,
                        DomainCatalogueService domainCatalogueService,
                        NsmfLcmProviderInterface nsmfLcmProvider,
                        VsmfUtils vsmfUtils,
                        Map<String, NetworkSliceInternalInfo> domainIdNetworkSliceInternalInfoMap
                        ) {
        this.vsiUuid = vsiUuid;
        this.vsiName=vsiName;
        this.vsRecordService = vsRecordService;
        this.vsDescriptorCatalogueService = vsDescriptorCatalogueService;
        this.translatorService = translatorService;
        this.arbitratorService = arbitratorService;
        this.adminService = adminService;
        this.internalStatus = VerticalServiceStatus.INSTANTIATING;
        this.vsLcmService = vsLcmService;
        this.virtualResourceCalculatorService=virtualResourceCalculatorService;
        this.domainCatalogueService = domainCatalogueService;
        this.nsmfLcmProvider = nsmfLcmProvider;
        this.vsmfUtils = vsmfUtils;
        this.domainIdNetworkSliceInternalInfoMap = domainIdNetworkSliceInternalInfoMap;
    }

    /**
     * Method used to receive messages about VSI lifecycle from the Rabbit MQ
     *
     * @param message received message
     */
    public void receiveMessage(String message) {
        log.debug("Received message for VSI " + vsiUuid + "\n" + message);
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
                    log.debug("Processing VSI modification request.");
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

                case ACTUATION_REQUEST: {
                    log.debug("Processing NSI E2E actuation.");
                    ActauteNsiMessage actauteNsiMessage = (ActauteNsiMessage) em;
                    processActuationRequest(actauteNsiMessage.getActuationRequest());
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

    void setNetworkSliceUuid(String networkSliceUuid) {
        this.networkSliceUuid = networkSliceUuid;
    }

    void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    //TODO to be tested against NSP offline and different error cases
    private void rollbackCreation() throws FailedOperationException, MalformattedElementException, NotPermittedOperationException, NotExistingEntityException, MethodNotImplementedException {
        if(nsiUuidNetworkSliceInfoMap.keySet().size()==0){
            log.info("No network slice has been created yet, thus no rollback is going to be performed.");
            return;
        }
        log.info("Slice creation rollback started.");
        for(String nsiUuid: nsiUuidNetworkSliceInfoMap.keySet()){
            log.info("Sending terminate request for Network Slice with UUID: "+nsiUuid);
            NsmfRestClient nsmfRestClient = nsiUuidNetworkSliceInfoMap.get(nsiUuid).getNsmfRestClient();
            nsmfRestClient.terminateNetworkSliceInstance(new TerminateNsiRequest(nsiUuid), tenantId);
        }
        nsiUuidNetworkSliceInfoMap.clear();
    }

    void processInstantiateRequest(InstantiateVsiRequestMessage msg) {
        if (internalStatus != VerticalServiceStatus.INSTANTIATING) {
            manageVsError("Received instantiation request in wrong status. Skipping message.");
            return;
        }
        String vsdId = msg.getRequest().getVsdId();
        log.debug("Instantiating Vertical Service " + vsiUuid + " with VSD " + vsdId);

        VsDescriptor vsd = null;
        try {
            vsd = vsDescriptorCatalogueService.getVsd(vsdId);
        } catch (NotExistingEntityException e) {
            manageVsError("Error while instantiating VS " + vsiUuid + ": " + e.getMessage());
        }

        this.vsDescriptors.put(vsdId, vsd);
        	this.tenantId = msg.getRequest().getTenantId();
        	List<String> vsdIds = new ArrayList<>();
        	vsdIds.add(vsdId);

            log.debug("The VSD has been translated in the required network slice(s) characteristics.");

            log.info("Going to perform the network slice creation request(s)");
            for(String domainId: domainIdNetworkSliceInternalInfoMap.keySet()) {
                Domain domain = null;
                try {
                    domain = domainCatalogueService.getDomain(domainId);
                } catch (NotExistingEntityException e) {
                    manageVsError("Error while instantiating VS " + vsiUuid + ": " + e.getMessage());
                }
                DomainInterface domainInterface = domain.getDomainInterface();
                final String BASE_URL = "http://" + domainInterface.getUrl() + ":" + domainInterface.getPort();
                String nstUuid = domainIdNetworkSliceInternalInfoMap.get(domainId).getNstUuid();
                if (nstUuid == null) {
                    manageVsError("Error while instantiating VS " + vsiUuid + ": NST for domain with ID " + domainId + " not found.");
                    return;
                }

                //Perform request to create network slice
                CreateNsiUuidRequest request = new CreateNsiUuidRequest(nstUuid, "NS - " + vsiName, "Network slice for VS " + vsiName);
                NsmfRestClient nsmfRestClient = new NsmfRestClient(BASE_URL, adminService);
                String nsiUuid = null;
                try {
                    nsiUuid = nsmfRestClient.createNetworkSliceIdentifier(request, tenantId);
                } catch (Exception e) {
                    try {
                        rollbackCreation();
                    } catch (Exception ex) {
                        log.error("Unable to rollback.");
                        ex.printStackTrace();
                    }
                    manageVsError("Error while instantiating VS " + vsiUuid + " "+ e.getMessage());
                }
                LocationInfo locationInfoVsi =domainIdNetworkSliceInternalInfoMap.get(domainId).getLocationInfo();
                nsiUuidNetworkSliceInfoMap.put(nsiUuid, new NspNetworkSliceInteraction(nsmfRestClient,nstUuid,locationInfoVsi));
                log.info("Network slice with UUID: " + nsiUuid + " has been created.");
            }

            log.info("Performed successfully creation of  "  +nsiUuidNetworkSliceInfoMap.size() + " Network Slice Instance(s).\n");
            log.info("Going to perform the network slice instantiation request(s)");

            for(String nsiUuid: nsiUuidNetworkSliceInfoMap.keySet()){
                InstantiateNsiRequest instantiateNsiReq = new InstantiateNsiRequest(nsiUuid,
                        nsiUuidNetworkSliceInfoMap.get(nsiUuid).getNstId(),
                        null,null, null,
                        msg.getRequest().getUserData(),
                        nsiUuidNetworkSliceInfoMap.get(nsiUuid).getLocationInfoVsi(),
                        msg.getRanEndpointId());
                log.info("Performing request to instantiate network slice with UUID "+nsiUuid+".");
                NsmfRestClient nsmfRestClient=nsiUuidNetworkSliceInfoMap.get(nsiUuid).getNsmfRestClient();
                if(nsmfRestClient==null){
                    manageVsError("Unable to find suitable rest client for Network Slice Instance with UUID equal to "+nsiUuid);
                    return;
                }
                try {
                    nsmfRestClient.instantiateNetworkSlice(instantiateNsiReq, tenantId);
                } catch (Exception e) {
                    //rollbackInstantiation(); TODO to be implemented
                    manageVsError("Error while instantiating VS " + vsiUuid + ": " + e.getMessage());
                }
                try {
                    vsRecordService.addNsiInVsi(vsiUuid,nsiUuid);
                } catch (NotExistingEntityException e) {
                    e.printStackTrace();
                }
            }

            log.info("Performed successfully instantiation of  "  +nsiUuidNetworkSliceInfoMap.size() + "Network Slice Instance(s).\n");
            //TODO remove nst from buckets or mark them as reusable (if any)

    }


    private void processActuationRequest(ActuationRequest actuationRequest){
        //From vsiUuid should be get the nsi associated.
        //From the nsi associated should be get the domains and the resulting Ip address, thus the URLs
        VerticalServiceInstance verticalServiceInstance=null;
        try {
            verticalServiceInstance = vsRecordService.getVsInstance(vsiUuid);
        } catch (NotExistingEntityException e) {
            manageVsError("Vertical service instance with ID " +vsiUuid+ "not found into DB.");
            e.printStackTrace();
        }
        //TODO the vertical slice status must be instantiated. Skipping for now
        for(int i=0; i< verticalServiceInstance.getNetworkSlicesId().size(); i++){
            String nsiId = verticalServiceInstance.getNetworkSlicesId().get(i);
            NspNetworkSliceInteraction nspNetworkSliceInteraction =nsiUuidNetworkSliceInfoMap.get(nsiId);
            if(nspNetworkSliceInteraction ==null){
                manageVsError("No local info found about Network Slice Instance with UUID "+nsiId );
                return;
            }
            NsmfRestClient nsmfRestClient = nspNetworkSliceInteraction.getNsmfRestClient();

            try {
                actuationRequest.setNotificationEndpoint("/vs/notifications/actuation/"+nsiId);
                actuationRequest.setNsiId(nsiId);//The actuation request coming from the north side contain the nsiE2EId (or vsiId).
                //The actuation request to be sent to the NSMF must have the nsiId instanciated by that NSMF.
                nsmfRestClient.actuateNetworkSlice(actuationRequest, tenantId);
            } catch (Exception e) {
                log.error("Error during the request of actuation.");
                e.printStackTrace();
            }
            //break; <-- use this for single domain ;)
        }

    }

    void processResourcesGrantedNotification(NotifyResourceGranted message) {
        if (internalStatus != VerticalServiceStatus.WAITING_FOR_RESOURCES) {
            manageVsError("Received resource granted notification in wrong status. Skipping message.");
            return;
        }
        if (!message.getVsiId().equals(vsiUuid)) {
            manageVsError("Received resource granted notification with wrong vsi UUID. Skipping message");
            return;
        }

        try{
            internalStatus = VerticalServiceStatus.INSTANTIATING;
            vsRecordService.setVsStatus(vsiUuid, VerticalServiceStatus.INSTANTIATING);

            InstantiateVsiRequestMessage msg = storedInstantiateVsiRequestMessage;
            if (storedResponse.isNewSliceRequired()) {
                log.debug("A new network slice should be instantiated for the Vertical Service instance " + vsiUuid);


                NfvNsInstantiationInfo nsiInfo = storedNfvNsInstantiationInfo;
                //TODO: to be extended for composite VSDs

                List<String> nsSubnetInstanceUuids;
                if (storedResponse.getExistingSliceSubnets().isEmpty())
                    nsSubnetInstanceUuids = new ArrayList<>();
                else
                    nsSubnetInstanceUuids = new ArrayList<>(storedResponse.getExistingSliceSubnets().keySet());

                CreateNsiUuidRequest request = new CreateNsiUuidRequest(nsiInfo.getNstId(),
                		"NS - " + vsiName, 
                		"Network slice for VS " + vsiName);
                String nsiUuid = nsmfLcmProvider.createNetworkSliceIdentifier(request, tenantId);
                
                //String nsiId = vsRecordService.createNetworkSliceForVsi(vsiId, nsiInfo.getNfvNsdId(), nsiInfo.getNsdVersion(), nsiInfo.getDeploymentFlavourId(),
                //        nsiInfo.getInstantiationLevelId(), nsSubnetInstanceIds, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
                log.debug("Network Slice ID " + nsiUuid + " created for VSI " + vsiUuid);
                setNetworkSliceUuid(nsiUuid);
                vsRecordService.setNsiInVsi(vsiUuid, nsiUuid);

                //Add nested VSI if any
                for (String nestedNsiUuid : nsSubnetInstanceUuids) {
                    VerticalServiceInstance nestedVsi = vsRecordService.getVsInstancesFromNetworkSlice(nestedNsiUuid).get(0);
                    this.nestedVsi.add(nestedVsi.getVsiId());
                    // TODO notify record to add sub-instance to main instance
                    vsRecordService.addNestedVsInVerticalServiceInstance(vsiUuid, nestedVsi);
                }
                log.debug("Record updated with info about NSI and VSI association.");
                
                InstantiateNsiRequest instantiateNsiReq = new InstantiateNsiRequest(nsiUuid,
                		nsiInfo.getNstId(),
                		nsiInfo.getDeploymentFlavourId(),
                		nsiInfo.getInstantiationLevelId(),
                		nsSubnetInstanceUuids,
                		msg.getRequest().getUserData(),
                		msg.getRequest().getLocationsConstraints().get(0),//Supposed one location. TODO generalise
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
                manageVsError("Error while instantiating VS " + vsiUuid + ": solution with slice sharing returned from the arbitrator. Not supported at the moment.");
            }
        } catch (Exception e) {
            manageVsError("Error while instantiating VS " + vsiUuid + ": " + e.getMessage());
        }
    }


     void processModifyRequest(ModifyVsiRequestMessage msg){
            if (!msg.getVsiId().equals(vsiUuid)) {
                throw new IllegalArgumentException(String.format("Wrong VSI UUID: %s", msg.getVsiId()));
            }
            if (internalStatus != VerticalServiceStatus.INSTANTIATED) {
                manageVsError("Received modification request in wrong status. Skipping message.");
                return;
            }

        log.debug("Modifying Vertical Service " + vsiUuid);
        this.internalStatus = VerticalServiceStatus.UNDER_MODIFICATION;

        String vsdId = msg.getRequest().getVsdId();
        
        try {

        	VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
            List<String> vsdIds = new ArrayList<>();
            vsdIds.add(vsdId);
        	
            //Translate VsdId into NfvNsInstantiationInfo
            //Map<String, NfvNsInstantiationInfo> nsInfos = translatorService.translateVsd(vsdIds);

            Map<String, NfvNsInstantiationInfo> nsInfo = translatorService.translateVsd(vsdIds);
            String newNstId="";
            for(String key: nsInfo.keySet()){
                newNstId=nsInfo.get(key).getNstId();
            }

            log.debug("The VSD has been translated into NST id. Getting the first one: "+newNstId);
            NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(networkSliceUuid, tenantId);
            ModifyNsiRequest modifyNsiRequest = new ModifyNsiRequest(nsi.getNsiId(),
                    newNstId,
                    null,
                    null,
                    null);
            nsmfLcmProvider.modifyNetworkSlice(modifyNsiRequest, tenantId);
           /* Case 1: only ILs are different -> Scale
            Case 2: nothing is different -> set the new VSDid
            Case 3: either nsdIds or Dfs are different -> Error

            */


        /*

            // assuming one
            NfvNsInstantiationInfo nsInfo = nsInfos.get(vsdId);
            String newNstIt = nsInfo.getNstId();
            String newDfId = nsInfo.getDeploymentFlavourId();
            String newInstantiationLevelId = nsInfo.getInstantiationLevelId();
            String newNsdId = nsInfo.getNfvNsdId();
        */
            // retrieve info about current NSI
       /*     NetworkSliceInstance nsi = vsRecordService.getNsInstance(networkSliceId);
            
            NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(networkSliceUuid, tenantId);
            String currentDfId = nsi.getDfId();
            String currentInstantiationLevelId = nsi.getInstantiationLevelId();
            String currentNsdId = nsi.getNsdId();

        compare the triples
				Case 1: only ILs are different -> Scale
				Case 2: nothing is different -> set the new VSDid
				Case 3: either nsdIds or Dfs are different -> Error
			 */
    /*
            if(newNsdId.equals(currentNsdId) && newDfId.equals(currentDfId)){
                if(newInstantiationLevelId.equals(currentInstantiationLevelId)){
                    //Case 2
                    log.debug("New vsd set.");
                    this.vsDescriptors.put(vsdId, vsd);
                }else{
                    //Case 1
                    //Assemble Arbitrator request
                    //this is a trick: in this way, the current Nsi (networkSliceUuid) and the new one (nsInfos) are both on the same request
                    nsInfos.remove(vsdId);
                    nsInfos.put(networkSliceUuid, nsInfo);

                    List<ArbitratorRequest> arbitratorRequests = new ArrayList<>();
                    ArbitratorRequest arbitratorRequest = new ArbitratorRequest("scaleRequest", tenantId, vsd, nsInfos);
                    arbitratorRequests.add(arbitratorRequest);
                    ArbitratorResponse arbitratorResponse = arbitratorService.arbitrateVsScaling(arbitratorRequests).get(0);
                    if (!(arbitratorResponse.isAcceptableRequest())) {
                        manageVsError("Error while trying modify VS " + vsiUuid + ": no solution returned from the arbitrator");
                        return;
                    }

                    //TODO Additional controls on ArbitratorResponse might be required
                    
                    ModifyNsiRequest modifyNsiRequest = new ModifyNsiRequest(nsi.getNsiId(), 
                    		newNstIt, 
                    		null,
                            null,
                            null);
                    nsmfLcmProvider.modifyNetworkSlice(modifyNsiRequest, tenantId);
                    //vsLocalEngine.modifyNs(nsi.getNsiId(), tenantId, currentNsdId, nsInfo.getNsdVersion(), newDfId, newInstantiationLevelId, vsiId);
                }
            }else{
                // Case 3
                manageVsError("Error while modifying VS " + vsiUuid + ": Deployment Flavour and Nsd update are not supported yet");
            }
    */
        } catch (Exception e) {
            manageVsError("Error while modifying VS " + vsiUuid + ": " + e.getMessage());
        }

    }

    void processTerminateRequest(TerminateVsiRequestMessage msg) {
        if (!msg.getVsiId().equals(vsiUuid)) {
            throw new IllegalArgumentException(String.format("Wrong VSI ID: %s", msg.getVsiId()));
        }
        if (internalStatus != VerticalServiceStatus.INSTANTIATED) {
            manageVsError("Received termination request in wrong status. Skipping message.");
            return;
        }

        log.debug("Terminating Vertical Service " + vsiUuid);
        this.internalStatus = VerticalServiceStatus.TERMINATING;
        try {
            vsRecordService.setVsStatus(vsiUuid, VerticalServiceStatus.TERMINATING);
            List<VerticalServiceInstance> vsis = vsRecordService.getVsInstancesFromNetworkSlice(networkSliceUuid);
            // Shared NSI support: if vsis > 1 nsi is shared.
            if (vsis.size() > 1) {
                nsStatusChangeOperations(VerticalServiceStatus.TERMINATED);
            } else {
                log.debug("Network slice " + networkSliceUuid + " must be terminated.");
                List<String> nsiUuidList=vsRecordService.getVsInstance(vsiUuid).getNetworkSlicesId();
                for(String nsiUuid: nsiUuidList){
                    NsmfRestClient nsmfRestClient=nsiUuidNetworkSliceInfoMap.get(nsiUuid).getNsmfRestClient();
                    nsmfRestClient.terminateNetworkSliceInstance(new TerminateNsiRequest(networkSliceUuid), tenantId);
                }
                //nsmfLcmProvider.terminateNetworkSliceInstance(new TerminateNsiRequest(networkSliceUuid), tenantId);
                //vsLocalEngine.terminateNs(networkSliceId);
            }
        } catch (Exception e) {
            manageVsError("Error while terminating VS " + vsiUuid + ": " + e.getMessage());
        }
    }

    void setInternalStatus(VerticalServiceStatus status) {
        this.internalStatus = status;
    }

    VerticalServiceStatus getInternalStatus() {
        return this.internalStatus;
    }



    private boolean isValidStatus(VerticalServiceStatus status){
        if (status == VerticalServiceStatus.INSTANTIATED && internalStatus == VerticalServiceStatus.INSTANTIATING){
            log.debug("Instantiation procedure completed.");
            return true;
        }
        if (status == VerticalServiceStatus.TERMINATED && internalStatus == VerticalServiceStatus.TERMINATING) {
            log.debug("Termination procedure completed.");
            return true;
        }
        if(status == VerticalServiceStatus.MODIFIED && internalStatus == VerticalServiceStatus.UNDER_MODIFICATION) {
            internalStatus = VerticalServiceStatus.INSTANTIATED;
            log.debug("VS Modification procedure completed.");
            return true;
        }
        return false;
    }


    private void nsStatusChangeOperations(VerticalServiceStatus status) throws NotExistingEntityException, Exception {
        if(isValidStatus(status)){
            this.internalStatus = status;
            vsRecordService.setVsStatus(vsiUuid, status);
        }
        else{
            manageVsError("Received notification about NSI creation in wrong status.");
            return;
        }
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
        vsRecordService.setVsFailureInfo(vsiUuid, errorMessage);
    }




private class NspNetworkSliceInteraction {
        private NsmfRestClient nsmfRestClient;
        private String nstId;
        private LocationInfo locationInfoVsi;

        public NspNetworkSliceInteraction(NsmfRestClient nsmfRestClient, String nstId, LocationInfo locationInfoVsi){
            this.nsmfRestClient=nsmfRestClient;
            this.nstId=nstId;
            this.locationInfoVsi= locationInfoVsi;
        }

    public NsmfRestClient getNsmfRestClient() {
        return nsmfRestClient;
    }

    public String getNstId() {
        return nstId;
    }

    public LocationInfo getLocationInfoVsi() {
        return locationInfoVsi;
    }

}

}
