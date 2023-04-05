package it.nextworks.nfvmano.nsmf.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceSubnetType;
import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstanceStatus;
import it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces.NsiLcmNotificationConsumerInterface;
import it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces.NsmfLcmProvisioningInterface;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.ConfigurationRequestStatus;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.AssociateSubscriber;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ConfigurationActionType;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.ScaleNetworkSlice;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.UpdateConfigurationRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.interfaces.NssmfLcmProvisioningInterface;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.ran.RanSlicePayload;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.interfaces.ResourceAllocationProvider;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.libs.vs.common.topology.NetworkTopology;
import it.nextworks.nfvmano.libs.vs.common.vsmf.elements.NsiNotifType;
import it.nextworks.nfvmano.libs.vs.common.vsmf.message.VsmfNotificationMessage;
import it.nextworks.nfvmano.nsmf.engine.messages.*;
import it.nextworks.nfvmano.nsmf.nbi.VsmfNotifier;
import it.nextworks.nfvmano.nsmf.record.NsiRecordService;
import it.nextworks.nfvmano.nsmf.record.elements.ConfigurationRequestRecord;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceInstanceRecord;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceInstanceRecordStatus;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceSubnetRecordStatus;
import it.nextworks.nfvmano.nsmf.record.repos.ConfigurationRequestRepo;
import it.nextworks.nfvmano.nsmf.sbi.NssmfDriverRegistry;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalInstantiateNssiRequest;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalModifyNssiRequest;
import it.nextworks.nfvmano.nsmf.topology.InfrastructureTopologyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class NsLcmManager {

    private static final Logger log = LoggerFactory.getLogger(NsLcmManager.class);
    private UUID networkSliceInstanceId;


    private NST nst;

    private NsiRecordService nsiRecordService;
    private ConfigurationRequestRepo configurationRequestRepo;
    private NsmfLcmProvisioningInterface nsmfLcmProvisioningInterface;
    private InfrastructureTopologyService infrastructureTopologyService;
    private NsiLcmNotificationConsumerInterface nsiLcmNotificationConsumerInterface;
    private ResourceAllocationProvider resourceAllocationProvider;
    private NssmfDriverRegistry driverRegistry;
    private ResourceAllocationComputeResponse resourceAllocationComputeResponse;
    private ArrayList<NSST> nsstToInstantiate = new ArrayList<>();

    private Map<UUID, NssmfLcmProvisioningInterface> nssiDrivers = new HashMap<>();
    private Map<UUID, NSST> nssiNsst = new HashMap<>();
    private UUID lastConfigurationRequestId;

    private VsmfNotifier vsmfNotifier;

    private boolean notifyVsmf;

    public NsLcmManager(UUID networkSliceInstanceId, NST nst, NsiRecordService nsiRecordService,
                        NsmfLcmProvisioningInterface nsmfLcmProvisioningInterface,
                        NsiLcmNotificationConsumerInterface nsiLcmNotificationConsumerInterface,
                        ResourceAllocationProvider resourceAllocationProvider,
                        NssmfDriverRegistry driverRegistry,
                        ConfigurationRequestRepo configurationRequestRepo,
                        InfrastructureTopologyService infrastructureTopologyService,
                        VsmfNotifier vsmfNotifier,
                        boolean notifyVsmf){
        this.networkSliceInstanceId = networkSliceInstanceId;
        this.nst = nst;
        this.nsiRecordService = nsiRecordService;
        this.nsmfLcmProvisioningInterface = nsmfLcmProvisioningInterface;
        this.nsiLcmNotificationConsumerInterface= nsiLcmNotificationConsumerInterface;
        this.resourceAllocationProvider=resourceAllocationProvider;
        this.driverRegistry= driverRegistry;
        this.configurationRequestRepo=configurationRequestRepo;
        this.infrastructureTopologyService=infrastructureTopologyService;
        this.vsmfNotifier=vsmfNotifier;
        this.notifyVsmf=notifyVsmf;
    }



    public void receiveMessage(String message) {
        log.debug("Received message for NSI " + networkSliceInstanceId + "\n" + message);

        ObjectMapper mapper = new ObjectMapper();
        NsmfEngineMessage em = null;
        try {
            em = (NsmfEngineMessage) mapper.readValue(message, NsmfEngineMessage.class);

            NsmfEngineMessageType type = em.getType();
            log.debug("Processing internal "+ type+ " message");
            switch (type){
                case INSTANTIATE_NSI_REQUEST:
                    processInstantiateNsiRequest((InstantiateNsiRequestMessage)em);
                    break;
                case MODIFY_NSI_REQUEST:
                    //TODO: implements modify NSI
                    break;
                case NOTIFY_RESOURCE_ALLOCATION_RESPONSE:
                    processResourceAllocationNotification((NotifyResourceAllocationResponse)em);
                    break;
                case NOTIFY_NSSI_STATUS_CHANGE:
                    processNotifyNssiStatusChange((EngineNotifyNssiStatusChange)em);
                    break;
                case TERMINATE_NSI_REQUEST:
                    processTerminateNsiRequest((TerminateNsiRequestMessage) em);
                    break;
                case UPDATE_NSI_REQUEST:
                    processUpdateNsiRequest((EngineUpdateNsiRequest)em);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
           logMessageError(e);
        }

    }

    private void sentNotificationToVsmf(VsmfNotificationMessage message){
        if(notifyVsmf)
            vsmfNotifier.notifyVsmf(message);
    }


    private void internalInstantiateFsmUpdate(){
        log.debug("Updating instance FSM");
        try {
            NetworkSliceInstanceRecord record = nsiRecordService.getNetworkSliceInstanceRecord(this.networkSliceInstanceId);
            if(record.getStatus().equals(NetworkSliceInstanceRecordStatus.COMPUTING_RESOURCE_ALLOCATION)  ||
                    record.getStatus().equals(NetworkSliceInstanceRecordStatus.INSTANTIATING_CORE_SUBNET) ||
                    record.getStatus().equals(NetworkSliceInstanceRecordStatus.INSTANTIATING_TRANSPORT_SUBNET) ||
                    record.getStatus().equals(NetworkSliceInstanceRecordStatus.INSTANTIATING_RAN_SUBNET) ||
                    record.getStatus().equals(NetworkSliceInstanceRecordStatus.INSTANTIATING_APP_SUBNET))
            {

                if(nsstToInstantiate.isEmpty()){
                    log.debug("Completed NSST instantiation. Setting NSI to INSTANTIATED");
                    nsiRecordService.updateNsInstanceStatus(this.networkSliceInstanceId, NetworkSliceInstanceRecordStatus.INSTANTIATED, "");

                    sentNotificationToVsmf(new VsmfNotificationMessage(networkSliceInstanceId, NsiNotifType.STATUS_CHANGED, NetworkSliceInstanceStatus.INSTANTIATED));
                }else{

                    NSST targetNsst = nsstToInstantiate.get(0);
                    log.debug("Instantiating NSST with id:"+targetNsst.getNsstId());

                    NetworkSliceInstanceRecordStatus status=null;
                    SliceSubnetType targetNsstType=targetNsst.getType();
                    switch (targetNsstType) {
                        case RAN:
                            status = NetworkSliceInstanceRecordStatus.INSTANTIATING_RAN_SUBNET;
                            break;
                        case CORE:
                            status = NetworkSliceInstanceRecordStatus.INSTANTIATING_CORE_SUBNET;
                            break;
                        case TRANSPORT:
                            status = NetworkSliceInstanceRecordStatus.INSTANTIATING_TRANSPORT_SUBNET;
                            break;
                        case VAPP:
                            status = NetworkSliceInstanceRecordStatus.INSTANTIATING_APP_SUBNET;
                            break;
                        case E2E:
                        default:
                            break;
                    }

                    log.debug("Updating NSI status to: "+status);
                    nsiRecordService.updateNsInstanceStatus(this.networkSliceInstanceId, status, "");
                    NssmfLcmProvisioningInterface driver = getNssmfLcmDriver(this.resourceAllocationComputeResponse, targetNsst);

                    try {
                        UUID nssiId = driver.createNetworkSubSliceIdentifier();
                        log.debug("created NSSI ID:"+nssiId);
                        nssiDrivers.put(nssiId, driver);
                        nssiNsst.put(nssiId, targetNsst);
                        Optional<NssResourceAllocation> allocation = resourceAllocationComputeResponse.getNsResourceAllocation().getNssResourceAllocations().stream()
                                .filter(nssA-> nssA.getNsstId().equals(targetNsst.getNsstId()))
                                .findFirst();
                        if(allocation.isPresent())
                            nsiRecordService.createNetworkSliceSubnetInstanceEntry(targetNsst.getNsstId(),
                                nssiId, this.networkSliceInstanceId,
                                targetNsst.getType(),
                                allocation.get()
                            );
                        else
                            nsiRecordService.createNetworkSliceSubnetInstanceEntry(targetNsst.getNsstId(),
                                nssiId, this.networkSliceInstanceId,
                                targetNsst.getType(),
                                null
                            );
                        log.debug("created NSSI RECORD:"+nssiId+" nsstId:"+targetNsst.getNsstId()+" type:"+targetNsst.getType());
                        driver.instantiateNetworkSubSlice(new InternalInstantiateNssiRequest(nssiId,
                                this.networkSliceInstanceId,
                                targetNsst,
                                resourceAllocationComputeResponse, this.nst ));
                    } catch (MethodNotImplementedException e) {
                        failInstance(e.getMessage());
                    } catch (FailedOperationException e) {
                        failInstance(e.getMessage());
                    } catch (MalformattedElementException e) {
                        failInstance(e.getMessage());
                    } catch (NotPermittedOperationException e) {
                        failInstance(e.getMessage());
                    } catch (IllegalAccessException e) {
                        failInstance(e.getMessage());
                    } catch (ClassNotFoundException e) {
                        failInstance(e.getMessage());
                    } catch (InstantiationException e) {
                        failInstance(e.getMessage());
                    } catch (AlreadyExistingEntityException e) {
                        failInstance(e.getMessage());
                    }
                }
            }
        } catch (NotExistingEntityException e) {
            failInstance(e.getMessage());
        }

    }

    private void processResourceAllocationNotification(NotifyResourceAllocationResponse em) {
        log.debug("Processing resource allocation notification");
        NetworkSliceInstanceRecord record = null;
        try {
            record = nsiRecordService.getNetworkSliceInstanceRecord(this.networkSliceInstanceId);
            if(!record.getStatus().equals(NetworkSliceInstanceRecordStatus.COMPUTING_RESOURCE_ALLOCATION)){
                log.warn("Received Resource Allocation notification in wrong status. IGNORING");
                return;
            }

            if(!em.getResponse().isSuccessful()) {
                failInstance("Could not find Resource Allocation Solution");
                return;
            }
            this.resourceAllocationComputeResponse= em.getResponse();


            Optional<NSST> coreNsst = nst.getNsst().getNsstList().stream().filter(nsst -> nsst.getType().equals(SliceSubnetType.CORE)).findFirst();
            if(coreNsst.isPresent()){
                log.debug("Found CORE NSST: "+coreNsst.get().getNsstId());
                nsstToInstantiate.add(coreNsst.get());

            }
            Optional<NSST> transportNsst = nst.getNsst().getNsstList().stream().filter(nsst -> nsst.getType().equals(SliceSubnetType.TRANSPORT)).findFirst();
            if(transportNsst.isPresent()) {
                  log.debug("Found TRANSPORT NSST: " + transportNsst.get().getNsstId());
                  nsstToInstantiate.add(transportNsst.get());
            }

            Optional<NSST> ranNsst = nst.getNsst().getNsstList().stream().filter(nsst -> nsst.getType().equals(SliceSubnetType.RAN)).findFirst();
            if(ranNsst.isPresent()) {
                log.debug("Found RAN NSST: " + ranNsst.get().getNsstId());
                nsstToInstantiate.add(ranNsst.get());
            }

            Optional<NSST> appNsst = nst.getNsst().getNsstList().stream().filter(nsst -> nsst.getType().equals(SliceSubnetType.VAPP)).findFirst();
            if(appNsst.isPresent()) {
                log.debug("Found VAPP NSST: " + appNsst.get().getNsstId());
                nsstToInstantiate.add(appNsst.get());
            }

            if(nsstToInstantiate.isEmpty()){
                failInstance("No CORE/TRANSPORT/EDGE/RAN/APP NSST to instantiate");
                return;
            }

            log.debug("Computed NSST instantiation order:"+nsstToInstantiate.stream().map(nsst -> nsst.getNsstId()).collect(Collectors.toList()));
            internalInstantiateFsmUpdate();

        } catch (NotExistingEntityException e) {
            failInstance(e.getMessage());
        }

    }

    private void processTerminateNsiRequest(TerminateNsiRequestMessage em) {
        log.debug("Processing Terminate NSI request");
        try{
            NetworkSliceInstanceRecord nsiRecord=nsiRecordService.getNetworkSliceInstanceRecord(this.networkSliceInstanceId);
            if(nsiRecord.getStatus().equals(NetworkSliceInstanceRecordStatus.INSTANTIATED)) {
                for(UUID nssiId: nssiNsst.keySet()){
                    NSST nsst=nssiNsst.get(nssiId);
                    switch (nsst.getType()){
                        case RAN:
                            nsiRecordService.updateNsInstanceStatus(networkSliceInstanceId, NetworkSliceInstanceRecordStatus.TERMINATING_RAN_SUBNET, "");
                            break;
                        case CORE:
                            nsiRecordService.updateNsInstanceStatus(networkSliceInstanceId, NetworkSliceInstanceRecordStatus.TERMINATING_CORE_SUBNET, "");
                            break;
                        case TRANSPORT:
                            nsiRecordService.updateNsInstanceStatus(networkSliceInstanceId, NetworkSliceInstanceRecordStatus.TERMINATING_TRANSPORT_SUBNET, "");
                            break;
                        case VAPP:
                            nsiRecordService.updateNsInstanceStatus(networkSliceInstanceId, NetworkSliceInstanceRecordStatus.TERMINATING_APP_SUBNET, "");
                            break;
                        default:
                            log.error("Network Slice Subnet not permitted");
                            break;
                    }
                    NssmfLcmProvisioningInterface driver=nssiDrivers.get(nssiId);
                    driver.terminateNetworkSliceInstance(new NssmfBaseProvisioningMessage(nssiId));
                }
            }
        }catch (NotExistingEntityException e){
            failInstance(e.getMessage());
        } catch (FailedOperationException e) {
            failInstance(e.getMessage());
        } catch (MethodNotImplementedException e) {
            failInstance(e.getMessage());
        } catch (MalformattedElementException e) {
            failInstance(e.getMessage());
        } catch (NotPermittedOperationException e) {
            failInstance(e.getMessage());
        }
    }



    private void processNotifyNssiStatusChange(EngineNotifyNssiStatusChange em)
    {
        log.debug("Processing NSSI Status Change notification for NSSI:"+em.getNssiId());
        try {
            NetworkSliceInstanceRecord record = nsiRecordService.getNetworkSliceInstanceRecord(this.networkSliceInstanceId);
            if (record.getStatus() == NetworkSliceInstanceRecordStatus.INSTANTIATING_CORE_SUBNET
                    || record.getStatus() == NetworkSliceInstanceRecordStatus.INSTANTIATING_TRANSPORT_SUBNET
                    || record.getStatus() == NetworkSliceInstanceRecordStatus.INSTANTIATING_RAN_SUBNET
                    || record.getStatus() == NetworkSliceInstanceRecordStatus.INSTANTIATING_APP_SUBNET) {
                if (em.isSuccessful()) {
                    //TODO: validate incoming notification
                    log.debug("NSSI sucessfully instantiated");
                    log.debug("Removing NSST from pending instantiation list:" + nsstToInstantiate.get(0).getNsstId());
                    nsstToInstantiate.remove(0);
                    nsiRecordService.updateNetworkSliceSubnetStatus(em.getNssiId(), NetworkSliceSubnetRecordStatus.INSTANTIATED);
                    internalInstantiateFsmUpdate();
                } else {
                    failInstance("Failed NSSI status change:" + em.getNssiId());
                }
            } else if (record.getStatus()==NetworkSliceInstanceRecordStatus.CONFIGURING){
                //TODO: Fix this, shortcut because the NSSMF do not support other types of notifications
                log.debug("Received NSSMF configuration notification");
                UUID configActionId = lastConfigurationRequestId;
                Optional<ConfigurationRequestRecord> configurationRequestRecord = configurationRequestRepo.findById(configActionId);
                if(configurationRequestRecord.isPresent()){
                    ConfigurationRequestRecord ncrRecord = configurationRequestRecord.get();
                    ncrRecord.setStatus(ConfigurationRequestStatus.SUCCESS);
                    nsiRecordService.updateNsInstanceStatus(this.networkSliceInstanceId, NetworkSliceInstanceRecordStatus.INSTANTIATED,"");
                    configurationRequestRepo.saveAndFlush(ncrRecord);
                    sentNotificationToVsmf(new VsmfNotificationMessage(networkSliceInstanceId, NsiNotifType.STATUS_CHANGED, NetworkSliceInstanceStatus.INSTANTIATED));
                    log.debug("Updating configuration request action and NSI status");
                } else {
                    log.warn("Could not found configuration request with specified id:" + configActionId + ". IGNORING");
                }
            } else if(record.getStatus()==NetworkSliceInstanceRecordStatus.TERMINATING_RAN_SUBNET
                    || record.getStatus()==NetworkSliceInstanceRecordStatus.TERMINATING_CORE_SUBNET
                    || record.getStatus()==NetworkSliceInstanceRecordStatus.TERMINATING_TRANSPORT_SUBNET
                    || record.getStatus()==NetworkSliceInstanceRecordStatus.TERMINATING_APP_SUBNET){
                nssiNsst.remove(em.getNssiId());
                nsiRecordService.updateNetworkSliceSubnetStatus(em.getNssiId(), NetworkSliceSubnetRecordStatus.TERMINATED);
                if(nssiNsst.isEmpty()) {
                    nsiRecordService.updateNsInstanceStatus(networkSliceInstanceId, NetworkSliceInstanceRecordStatus.TERMINATED, "");
                    sentNotificationToVsmf(new VsmfNotificationMessage(networkSliceInstanceId, NsiNotifType.STATUS_CHANGED, NetworkSliceInstanceStatus.TERMINATED));
                }
            } else {
                log.warn("Received NOTIFY NSSI STATUS Change in wrong status: " + record.getStatus() + ". IGNORING");
            }
        } catch (NotExistingEntityException e) {
            failInstance("Failed to retrieve NS Instance Record from DB:"+this.networkSliceInstanceId);
        }
    }

    private void processInstantiateNsiRequest(InstantiateNsiRequestMessage em){
        log.debug("Processing Instantiate NSI Request");
        try {
            NetworkSliceInstanceRecord record = nsiRecordService.getNetworkSliceInstanceRecord(em.getRequest().getNsiId());
            if(record.getStatus()==NetworkSliceInstanceRecordStatus.CREATED){

               nsiRecordService.updateNsInstanceStatus(networkSliceInstanceId,
                       NetworkSliceInstanceRecordStatus.COMPUTING_RESOURCE_ALLOCATION,
                       null);
                resourceAllocationProvider.computeResources(composeRAComputeRequest(em.getTenantId()));
            }else{
                log.warn("Received Instantiate NSI request in wrong status:"+ record.getStatus()+". Ignoring");
            }



        } catch (NotExistingEntityException | FailedOperationException | MalformattedElementException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
           failInstance(e.getMessage());
        }

    }


    private List<UUID> getNssiUuids() {
        String currentNsi = this.networkSliceInstanceId.toString();
        NetworkSliceInstance networkSliceInstance = null;
        try {
            networkSliceInstance = nsiRecordService.getNetworkSliceInstance(currentNsi);
        } catch (NotExistingEntityException e) {
            throw new RuntimeException(e);
        }
        List<UUID> nssiUUID = networkSliceInstance.getNetworkSliceSubnetIds();
        return nssiUUID;
    }

    private void processUpdateNsiRequest(EngineUpdateNsiRequest em)  {
        log.debug("Received request to update NSI");

        try {
            NetworkSliceInstanceRecord record = nsiRecordService.getNetworkSliceInstanceRecord(this.networkSliceInstanceId);
            if(!record.getStatus().equals(NetworkSliceInstanceRecordStatus.INSTANTIATED)){
                log.warn("Received update NSI request in wrong status:"+record.getStatus()+" . IGNORING");
            }
            List<UUID> targetNssiIds = new ArrayList<>();
            if(em.getNssiId()!=null){
                targetNssiIds.add(em.getNssiId());
            }else if(em.getNstId()!=null){
                targetNssiIds= record.getNetworkSliceSubnetInstanceIds().stream()
                        .filter(nssi -> nssi.getNsstId().equals(em.getNstId()))
                        .map(nssi-> nssi.getNssiIdentifier())
                        .collect(Collectors.toList());
            }else if(em.getSliceSubnetType()!=null){

                targetNssiIds= record.getNetworkSliceSubnetInstanceIds().stream()
                        .filter(nssi -> nssi.getSliceSubnetType().equals(SliceSubnetType.valueOf(em.getSliceSubnetType().toString())))
                        .map(nssi-> nssi.getNssiIdentifier())
                        .collect(Collectors.toList());
            }
            targetNssiIds = getNssiUuids(); //////TODO to discuss about

            if(targetNssiIds.isEmpty())
                log.warn("No NSSIs found to be configured. SKIPPING");
            //TODO update status
            this.lastConfigurationRequestId = em.getConfigurationRequestId();
            log.debug("Updating NSI status to CONFIGURING");
            nsiRecordService.updateNsInstanceStatus(this.networkSliceInstanceId, NetworkSliceInstanceRecordStatus.CONFIGURING, "");
            Optional<ConfigurationRequestRecord> configurationRequestRecord=configurationRequestRepo.findById(lastConfigurationRequestId);
            ConfigurationRequestRecord crrRecord;
            if(configurationRequestRecord.isPresent()) {
                crrRecord = configurationRequestRecord.get();
                crrRecord.setNetworkSliceSubnetInstanceId(targetNssiIds);
                configurationRequestRepo.saveAndFlush(crrRecord);
            }

            ConfigurationActionType configurationActionType = em.getUpdateConfigurationRequest().getActionType();



            for(UUID nssiId: targetNssiIds){

                InternalModifyNssiRequest internalModifyNssiRequest = new InternalModifyNssiRequest(nssiId,
                        this.networkSliceInstanceId,
                        nssiNsst.get(nssiId),
                        em.getUpdateConfigurationRequest(),
                        this.nst,
                        lastConfigurationRequestId

                );
                nssiDrivers.get(nssiId).modifyNetworkSlice(internalModifyNssiRequest);

            }
                setNsiToInstantiated(targetNssiIds, em.getUpdateConfigurationRequest());

        } catch (NotExistingEntityException e) {
            failInstance(e.getMessage());
        } catch (FailedOperationException e) {
            failInstance(e.getMessage());
        } catch (MethodNotImplementedException e) {
            failInstance(e.getMessage());
        } catch (MalformattedElementException e) {
            failInstance(e.getMessage());
        } catch (NotPermittedOperationException e) {
            failInstance(e.getMessage());
        }
    }


    private void setNsiToInstantiated(List<UUID> targetNssiIds, UpdateConfigurationRequest updateConfigurationRequest) throws NotExistingEntityException {
        log.debug("Updating NSI status to INSTANTIATE");
        ConfigurationActionType configurationActionType = updateConfigurationRequest.getActionType();
        boolean isImsiAssociation = configurationActionType == ConfigurationActionType.ASSOCIATE_SUBSCRIBER;
        boolean isSliceScaling = configurationActionType == ConfigurationActionType.SLICE_SCALING;
        boolean nsiRecordUpdated = false;

        if (isImsiAssociation && updateConfigurationRequest instanceof AssociateSubscriber) {
            String imsi = ((AssociateSubscriber) updateConfigurationRequest).getImsi();
            nsiRecordService.updateNsInstanceStatus(this.networkSliceInstanceId, NetworkSliceInstanceRecordStatus.INSTANTIATED, imsi, "");
            nsiRecordUpdated = true;
        }
        if (isSliceScaling && updateConfigurationRequest instanceof ScaleNetworkSlice) {
                ScaleNetworkSlice scaleNetworkSlice = (ScaleNetworkSlice) updateConfigurationRequest;
                int newDataRate = scaleNetworkSlice.getNewDataRate();
                nsiRecordService.updateNsInstanceStatus(this.networkSliceInstanceId, NetworkSliceInstanceRecordStatus.INSTANTIATED, scaleNetworkSlice.getScalingOption(), newDataRate, "");
                nsiRecordUpdated = true;
        }
        if(!nsiRecordUpdated)
            nsiRecordService.updateNsInstanceStatus(this.networkSliceInstanceId, NetworkSliceInstanceRecordStatus.INSTANTIATED,"");


            Optional<ConfigurationRequestRecord> configurationRequestRecord = configurationRequestRepo.findById(lastConfigurationRequestId);
            ConfigurationRequestRecord crrRecord;
            if (configurationRequestRecord.isPresent()) {
                crrRecord = configurationRequestRecord.get();
                crrRecord.setNetworkSliceSubnetInstanceId(targetNssiIds);
                configurationRequestRepo.saveAndFlush(crrRecord);
            }
        }

    private void failInstance(String message){
        try {
            log.error("Error during LCM operation:"+ message);
            nsiRecordService.updateNsInstanceStatus(networkSliceInstanceId, NetworkSliceInstanceRecordStatus.FAILED, message);
            sentNotificationToVsmf(new VsmfNotificationMessage(networkSliceInstanceId, NsiNotifType.ERROR, NetworkSliceInstanceStatus.FAILED));
        } catch (NotExistingEntityException e) {
            log.error("Error while retrieving instance record from DB");
        }
    }

    private void logMessageError(Exception e){
        log.error("Exception during message exchange. Skipping message");
        log.error("Error message:", e);

    }

    private ResourceAllocationComputeRequest composeRAComputeRequest(String tenantId){
        String requestId=UUID.randomUUID().toString();
        NetworkTopology topology=infrastructureTopologyService.getInfrastructureTopology();

        //TO-DO: implements the mechanism to retrieve sharable slices

        return new ResourceAllocationComputeRequest(requestId, networkSliceInstanceId.toString(), nst, tenantId, null, topology);
    }


    private NssmfLcmProvisioningInterface getNssmfLcmDriver(ResourceAllocationComputeResponse response, NSST targetNsst){
        return driverRegistry.getNssmfLcmDriver(response, targetNsst);
    }
}
