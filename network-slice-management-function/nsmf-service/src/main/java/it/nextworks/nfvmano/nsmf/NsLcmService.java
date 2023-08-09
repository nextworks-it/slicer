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

package it.nextworks.nfvmano.nsmf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.nextworks.nfvmano.catalogue.template.elements.GsTemplateInfo;
import it.nextworks.nfvmano.catalogue.template.elements.NsTemplateInfo;
import it.nextworks.nfvmano.catalogue.template.interfaces.NestCatalogueInterface;
import it.nextworks.nfvmano.catalogue.template.interfaces.NsTemplateCatalogueInterface;
import it.nextworks.nfvmano.catalogue.template.messages.nest.QueryNesTemplateResponse;
import it.nextworks.nfvmano.catalogue.template.messages.nst.OnBoardNsTemplateRequest;
import it.nextworks.nfvmano.catalogue.template.messages.nst.QueryNsTemplateResponse;
import it.nextworks.nfvmano.catalogues.template.TemplateCatalogueUtilities;
import it.nextworks.nfvmano.libs.ifa.templates.nst.*;

import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces.NsiLcmNotificationConsumerInterface;
import it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces.NsmfLcmConfigInterface;
import it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces.NsmfLcmProvisioningInterface;
import it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces.NssiLcmNotificationConsumerInterface;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.NsmfNotificationMessage;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.UpdateConfigurationRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.CreateNsiIdFromNestRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.CreateNsiIdRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.InstantiateNsiRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.TerminateNsiRequest;
import it.nextworks.nfvmano.libs.vs.common.query.elements.Filter;
import it.nextworks.nfvmano.libs.vs.common.query.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;

import it.nextworks.nfvmano.nsmf.engine.messages.*;
import it.nextworks.nfvmano.nsmf.manager.NsLcmManager;
import it.nextworks.nfvmano.nsmf.nbi.VsmfNotifier;
import it.nextworks.nfvmano.nsmf.ra.ResourceAllocationComputeService;
import it.nextworks.nfvmano.nsmf.record.NsiRecordService;
import it.nextworks.nfvmano.nsmf.record.elements.ConfigurationRequestRecord;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceInstanceRecord;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceInstanceRecordStatus;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceSubnetInstanceRecord;
import it.nextworks.nfvmano.nsmf.record.repos.ConfigurationRequestRepo;
import it.nextworks.nfvmano.nsmf.sbi.NssmfDriverRegistry;
import it.nextworks.nfvmano.nsmf.topology.InfrastructureTopologyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class NsLcmService implements NsmfLcmProvisioningInterface, NsmfLcmConfigInterface, NssiLcmNotificationConsumerInterface {

    private static final Logger log = LoggerFactory.getLogger(NsLcmService.class);

    @Autowired
    private NsiRecordService nsiRecordService;

    @Autowired
    private ConfigurationRequestRepo configurationRequestRepo;
    
    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("engine-queue-exchange")
    TopicExchange messageExchange;

    @Autowired
    private ResourceAllocationComputeService resourceAllocationProvider;

    @Value("${spring.rabbitmq.queue_name_prefix:engine-in-}")
    private String queueNamePrefix;


    @Autowired
    private NsTemplateCatalogueInterface nsTemplateCatalogueInterface;

    @Autowired
    private NestCatalogueInterface nestCatalogueInterface;

    @Autowired
    private NssmfDriverRegistry driverRegistry;

    @Autowired
    private InfrastructureTopologyService infrastructureTopologyService;

    @Autowired
    private VsmfNotifier vsmfNotifier;

    @Value("${nsmf.vsmfnotifier.notifyVsmf:false}")
    private boolean notifyVsmf;
    //internal map of VS LCM Managers
    //each VS LCM Manager is created when a new VSI ID is created and removed when the VSI ID is removed
    private Map<UUID, NsLcmManager> nsLcmManagers = new HashMap<>();
    
    private NsiLcmNotificationConsumerInterface notificationDispatcher;



    public void scaleSliceOnNewUpf(UpdateConfigurationRequest request) throws NotExistingEntityException, FailedOperationException, MethodNotImplementedException, MalformattedElementException, NotPermittedOperationException {
        final String NSD_ID_NEW_UPF = "fff391fe-53e8-4124-ad10-92c33f3e4373";
        String nsiId = request.getNsiId().toString();
        NetworkSliceInstanceRecord networkSliceInstanceRecord = nsiRecordService.getNetworkSliceInstanceRecord(UUID.fromString(nsiId));
        String nssiCoreId = null;
        List<NetworkSliceSubnetInstanceRecord> networkSliceInstanceRecordList = networkSliceInstanceRecord.getNetworkSliceSubnetInstanceIds();
        for(NetworkSliceSubnetInstanceRecord networkSliceSubnetInstanceRecord: networkSliceInstanceRecordList){
            if(networkSliceSubnetInstanceRecord.getNetworkSliceSubnetInstance().getNsstType().equals("CORE")){
                nssiCoreId = networkSliceSubnetInstanceRecord.getNetworkSliceSubnetInstance().getNetworkSliceSubnetInstanceId().toString();
                break;
            }
        }
        if(nssiCoreId==null)
            throw new NotExistingEntityException("NSSI Core not found within end-to-end network slice with ID "+nsiId);

        configureNetworkSlice(request, "admin");

    }

    public void scaleUpfOld(String nsiId, String upfName, String nsdIdNewUpf) throws NotExistingEntityException, it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException, it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException, it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException, it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException, FailedOperationException, MethodNotImplementedException, MalformattedElementException, NotPermittedOperationException, it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException, JsonProcessingException {
        log.info("Getting NST identifier for NSI whose id is "+nsiId);
        log.info("UPF name is "+upfName);
        NetworkSliceInstanceRecord networkSliceInstanceRecord = nsiRecordService.getNetworkSliceInstanceRecord(UUID.fromString(nsiId));
        String nstId = networkSliceInstanceRecord.getNstId();
        log.info("NST id found is "+nstId);

        QueryNsTemplateResponse queryNsTemplateResponse = nsTemplateCatalogueInterface.queryNsTemplate(new it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest(TemplateCatalogueUtilities.buildNsTemplateFilter(nstId), null));

        NST nst=null;

        for(NsTemplateInfo nsTemplateInfo: queryNsTemplateResponse.getNsTemplateInfos()){
            log.info(nsTemplateInfo.getNST().getNstId());
            if(nsTemplateInfo.getNST().getNstId().equals(nstId)){
                nst = nsTemplateInfo.getNST();

                break;
            }
        }
        if(nst==null){
            log.error("NST not found");
            return;
        }


        NST newNST = null;
        String newNstId = newNST.getNstId()+"_"+upfName;
        for(NsTemplateInfo nsTemplateInfo: queryNsTemplateResponse.getNsTemplateInfos()){
            log.info(nsTemplateInfo.getNST().getNstId());
            if(nsTemplateInfo.getNST().getNstId().equals(newNstId)){
                newNST = nsTemplateInfo.getNST();
                break;
            }
        }

        if(newNST==null){
            log.info("New NST not available. Going to on boarding it");
            newNST = new NST(nst);
            NSST firstNSSTchild = newNST.getNsst().getNsstList().get(0);

            List<URLLCPerfReq> urllcPerfReqs = new ArrayList<>();
            if(firstNSSTchild.getSliceProfileList().get(0).getuRLLCPerfReq().size()>0) {
                URLLCPerfReq urllcPerfReq = new URLLCPerfReq(firstNSSTchild.getSliceProfileList().get(0).getuRLLCPerfReq().get(0));
                urllcPerfReqs.add(urllcPerfReq);
            }

            List<EMBBPerfReq> embbPerfReqs = new ArrayList<>();

            if(firstNSSTchild.getSliceProfileList().get(0).geteMBBPerfReq().size()>0) {
                EMBBPerfReq embbPerfReq = new EMBBPerfReq(firstNSSTchild.getSliceProfileList().get(0).geteMBBPerfReq().get(0));
                embbPerfReqs.add(embbPerfReq);
            }


            SliceProfile sliceProfile = new SliceProfile();
            sliceProfile.setMaxNumberofUEs(firstNSSTchild.getSliceProfileList().get(0).getMaxNumberofUEs());
            sliceProfile.setLatency(firstNSSTchild.getSliceProfileList().get(0).getLatency());
            sliceProfile.setResourceSharingLevel(firstNSSTchild.getSliceProfileList().get(0).getResourceSharingLevel());
            sliceProfile.setuRLLCPerfReq(urllcPerfReqs);
            sliceProfile.seteMBBPerfReq(embbPerfReqs);
            List<String> coverageAreaTAList = firstNSSTchild.getSliceProfileList().get(0).getCoverageAreaTAList();
            List<String> newCoverageAreaTAList = new ArrayList<>();
            if(coverageAreaTAList!=null && coverageAreaTAList.size()>0) {
                for (String coverageAreaTa : coverageAreaTAList) {
                    newCoverageAreaTAList.add(coverageAreaTa);
                }
            }
            sliceProfile.setCoverageAreaTAList(newCoverageAreaTAList);
            List<SliceProfile> sliceProfileList = new ArrayList();
            sliceProfileList.add(sliceProfile);

            NsdInfo nsdInfo = new NsdInfo(nsdIdNewUpf, "nsd_new_UPF","nsd_new_UPF", "v01");


            NSST nsst = new NSST(firstNSSTchild.getNsstId()+"_"+upfName,
                    firstNSSTchild.isOperationalState(),
                    firstNSSTchild.getAdministrativeState(),
                    new NsInfo(firstNSSTchild.getNsInfo()),
                    sliceProfileList,
            new ArrayList<>(),
                    nsdInfo,
                    firstNSSTchild.getType());
            firstNSSTchild.setNsstName(firstNSSTchild.getNsstId()+"_"+upfName);
            firstNSSTchild.setNsstVersion(firstNSSTchild.getNsstId()+"_"+upfName);
            List<NSST> nsstList = new ArrayList<>();
            nsstList.add(nsst);


            NSST nsstParent = newNST.getNsst();
            List<URLLCPerfReq> urllcPerfReqsNsstParent = new ArrayList<>();
            if(nsstParent.getSliceProfileList().get(0).getuRLLCPerfReq().size()>0) {
                URLLCPerfReq urllcPerfReqNsstParent = new URLLCPerfReq(nsstParent.getSliceProfileList().get(0).getuRLLCPerfReq().get(0));
                urllcPerfReqsNsstParent.add(urllcPerfReqNsstParent);
            }

            List<EMBBPerfReq> embbPerfReqsNsstParent = new ArrayList<>();
            if(nsstParent.getSliceProfileList().get(0).geteMBBPerfReq().size()>0) {
                EMBBPerfReq embbPerfReqNsstParent = new EMBBPerfReq(nsstParent.getSliceProfileList().get(0).geteMBBPerfReq().get(0));
                embbPerfReqsNsstParent.add(embbPerfReqNsstParent);
            }

            SliceProfile sliceProfileNsstParent = new SliceProfile();
            sliceProfileNsstParent.setMaxNumberofUEs(nsstParent.getSliceProfileList().get(0).getMaxNumberofUEs());
            sliceProfileNsstParent.setLatency(nsstParent.getSliceProfileList().get(0).getLatency());
            sliceProfileNsstParent.setResourceSharingLevel(nsstParent.getSliceProfileList().get(0).getResourceSharingLevel());
            sliceProfileNsstParent.setuRLLCPerfReq(urllcPerfReqsNsstParent);
            sliceProfileNsstParent.seteMBBPerfReq(embbPerfReqsNsstParent);

            List<SliceProfile> sliceProfileListNsstParent = new ArrayList();
            sliceProfileListNsstParent.add(sliceProfileNsstParent);



            NSST nsstParentNew = new NSST(nsstParent.getNsstId()+"_"+upfName,
                    nsstParent.isOperationalState(),
                    nsstParent.getAdministrativeState(),
                    new NsInfo(nsstParent.getNsInfo()),
                    sliceProfileListNsstParent,
                    nsstList,
                    null,
                    nsstParent.getType());

            nsstParentNew.setNsstName(newNST.getNsst().getNsstName()+"_"+upfName);
            nsstParentNew.setNsstName(newNST.getNsst().getNsstVersion()+"_"+upfName);

            NstServiceProfile nstServiceProfile = new NstServiceProfile(newNST.getNstServiceProfileList().get(0));
            List<NstServiceProfile> nstServiceProfileList = new ArrayList<>();
            nstServiceProfileList.add(nstServiceProfile);



            NST myNST = new NST(
                    newNST.getNstId()+"_"+upfName,
                    newNST.getNstName()+"_"+upfName,
                    newNST.getNstVersion()+"_"+upfName,
                    newNST.getNstProvider(),
                    newNST.getAdministrativeState(),
                    nstServiceProfileList,
                    nsstParentNew
            );

            OnBoardNsTemplateRequest onBoardNsTemplateRequest = new OnBoardNsTemplateRequest();
            log.info("New NST ID is "+myNST.getNstId());
            onBoardNsTemplateRequest.setNst(myNST);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(onBoardNsTemplateRequest);
            log.info(json);

            log.info("On boarding New NST");
            nsTemplateCatalogueInterface.onBoardNsTemplate(onBoardNsTemplateRequest);
            String tenantId = "admin";

            log.info("Creating NSI");
            CreateNsiIdRequest createNsiIdRequest = new CreateNsiIdRequest(myNST.getNstId(), myNST.getNstId(), myNST.getNstId(), null);
            UUID nsi = createNetworkSliceIdentifierFromNst(createNsiIdRequest, tenantId);

            log.info("Instantiating NSI");
            InstantiateNsiRequest instantiateNsiRequest = new InstantiateNsiRequest(nsi);
            instantiateNsiRequest.setNewUpf(true);
            instantiateNsiRequest.setUpfName(upfName);
            instantiateNetworkSlice(instantiateNsiRequest, tenantId);
        }
        else{
            log.info("NST already onboarded, skipping this step");
            String tenantId = "admin";

            log.info("Creating NSI");
            CreateNsiIdRequest createNsiIdRequest = new CreateNsiIdRequest(newNST.getNstId(), newNST.getNstId(), newNST.getNstId(), null);
            UUID nsi = createNetworkSliceIdentifierFromNst(createNsiIdRequest, tenantId);

            log.info("Instantiating NSI");
            InstantiateNsiRequest instantiateNsiRequest = new InstantiateNsiRequest(nsi);
            instantiateNsiRequest.setNewUpf(true);
            instantiateNsiRequest.setUpfName(upfName);
            instantiateNetworkSlice(instantiateNsiRequest, tenantId);
        }


    }

    @Override
    public UUID createNetworkSliceIdentifierFromNst(CreateNsiIdRequest request, String tenantId)
    		throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
    	
    	log.debug("Processing request to create a new network slicer identifier");
    	request.isValid();

    	String nstId = request.getNstId();
        //TODO: Resolve NST Catalogue query interfaces
        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("NST_ID", nstId);
        NsTemplateInfo nstInfo = null;
        try {
            log.debug("Retrieving NST");
            QueryNsTemplateResponse nsTemplateResponse =nsTemplateCatalogueInterface.queryNsTemplate(
                    new it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest(new it.nextworks.nfvmano.libs.ifa.common.elements.Filter(filterParams), null));

            nstInfo = nsTemplateResponse.getNsTemplateInfos().get(0);
            log.debug("Network Slice Template retrieved from catalogue");
            NST nsTemplate = nstInfo.getNST();
            if (nsTemplate == null) {
                log.error("Null NS template retrieved from the catalogue");
                throw new NotExistingEntityException("Null NS template retrieved from the catalogue");
            }
            if(nsTemplate.getNsst()==null || nsTemplate.getNsst().getType()!= SliceSubnetType.E2E){
                log.error("Retrieved NST with NSST not of type E2E or null ");
                throw new MalformattedElementException("Retrieved NST with NSST not of type E2E or null");
            }

            if(nsTemplate.getNsst().getSliceProfileList().get(0).geteMBBPerfReq()==null || nsTemplate.getNsst().getSliceProfileList().get(0).geteMBBPerfReq().size()==0){
                final String ERROR = "EmBB performance requirements not available into slice profile list";
                log.error(ERROR);
                throw new MalformattedElementException(ERROR);
            }

            log.info("Creating nsi record service");
            final String DEFAULT_UPF_NAME ="UPF-default";
            UUID networkSliceId = nsiRecordService.createNetworkSliceInstanceEntry (
                    nstId,
                    request.getVsInstanceId(),
                    tenantId,
                    request.getName(),
                    nsTemplate.getNsst().getSliceProfileList().get(0).geteMBBPerfReq().get(0).getExpDataRateDL(),
                    nsTemplate.getNsst().getSliceProfileList().get(0).geteMBBPerfReq().get(0).getExpDataRateUL(),
                    DEFAULT_UPF_NAME
            );
            log.info("Initing New NS LCM Manager");
            initNewNsLcmManager(networkSliceId, nsTemplate);
            return networkSliceId;

        } catch (it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException e) {
            throw new MethodNotImplementedException(e);
        } catch (it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException e) {
            throw new MalformattedElementException(e);
        } catch (it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException e) {
            throw new NotExistingEntityException("NST with ID: "+nstId+" not found in the catalogue");
        } catch (it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException e) {
            throw new FailedOperationException(e);
        }


    }

    public UUID createNetworkSliceIdentifierFromNest(CreateNsiIdFromNestRequest request, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {

        log.debug("Processing request to create a new network slicer identifier from NEST");
        request.isValid();
        String nestId= request.getNestId();

        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("NST_ID", nestId);
        GsTemplateInfo nestInfo=null;
        String nstId=null;
        try{
            log.debug("Retrieving NEST INFO");
            QueryNesTemplateResponse nesTemplateResponse =nestCatalogueInterface.queryNesTemplate(
                    new it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest(new it.nextworks.nfvmano.libs.ifa.common.elements.Filter(filterParams), null));
            nestInfo=nesTemplateResponse.getGsTemplateInfos().get(0);
            nstId=nestInfo.getReferenceNstId();
        } catch (it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException e) {
            throw new MethodNotImplementedException(e);
        } catch (it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException e) {
            throw new MalformattedElementException(e);
        } catch (it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException e) {
            throw new NotExistingEntityException("NEST with ID:"+nestId+" not found in the catalogue");
        } catch (it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException e) {
            throw new FailedOperationException(e);
        }
        if(nstId==null) {
            log.error("Request received for a NEST does not translated from an NST");
            throw new FailedOperationException("Request received for a NEST does not translated from an NST");
        }

        CreateNsiIdRequest nsiIdRequest=new CreateNsiIdRequest(nstId, request.getName(), request.getDescription(), request.getVsInstanceId());

        return createNetworkSliceIdentifierFromNst(nsiIdRequest, tenantId);
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request,  String tenantId)
    		throws NotExistingEntityException, MalformattedElementException, NotPermittedOperationException {
    	log.debug("Processing request to instantiate a network slice instance");
    	request.isValid();
    	UUID nsiId = request.getNsiId();
        if(request.getUpfName()==null){
            int countInstantiatedEndToEndSlices = 0;
            List<NetworkSliceInstance> networkSliceInstances = nsiRecordService.getAllNetworkSliceInstance();
            for(NetworkSliceInstance networkSliceInstance: networkSliceInstances){
                if(networkSliceInstance.getStatus()==NetworkSliceInstanceStatus.INSTANTIATED){
                    countInstantiatedEndToEndSlices++;
                }
            }
            log.info("The count of INSTANTIATED slices is "+countInstantiatedEndToEndSlices);
            if(countInstantiatedEndToEndSlices==0)
                request.setUpfName("UPF");
            else {
                countInstantiatedEndToEndSlices++;
                request.setUpfName("UPF-"+countInstantiatedEndToEndSlices);
            }
        }
    	log.debug("Processing NSI instantiation request for NSI ID " + nsiId);
        if (nsLcmManagers.containsKey(nsiId)) {
            NetworkSliceInstanceRecord record = nsiRecordService.getNetworkSliceInstanceRecord(nsiId);
        	if (record.getStatus() != NetworkSliceInstanceRecordStatus.CREATED) {
        		log.error("Network slice " + nsiId + " not in CREATED state. Cannot instantiate it. Skipping message.");
        		throw new NotPermittedOperationException("Network slice " + nsiId + " not in CREATED state. Current status:"+record.getStatus());
        	}
            String topic = "nslifecycle.instantiatens." + nsiId;
            InstantiateNsiRequestMessage internalMessage = new InstantiateNsiRequestMessage(request, tenantId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
            	this.manageNsError(nsiId, "Error while translating internal NS instantiation message in Json format.");
            }
        } else {
            log.error("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Unable to instantiate the NSI.");
            throw new NotExistingEntityException("Unable to find NS LCM Manager for NSI ID " + nsiId + ". Unable to instantiate the NSI.");
        }
    }




    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest request,  String tenantId)
    		throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
    	log.debug("Processing request to terminate a network slice instance");
    	request.isValid();
    	UUID nsiId = request.getNsiId();
    	log.debug("Processing NSI termination request for NSI ID " + nsiId);
        if (nsLcmManagers.containsKey(nsiId)) {
            NetworkSliceInstanceRecord record = nsiRecordService.getNetworkSliceInstanceRecord(nsiId);
            if (record.getStatus() != NetworkSliceInstanceRecordStatus.INSTANTIATED) {
                log.error("Network slice " + nsiId + " not in INSTANTIATED state. Cannot terminate it. Skipping message.\"");
                throw new NotPermittedOperationException("Network slice " + nsiId + " not in CREATED state. Current status:"+record.getStatus());
            }

            String topic = "nslifecycle.terminatens." + nsiId;
            TerminateNsiRequestMessage internalMessage = new TerminateNsiRequestMessage(request, tenantId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
            	this.manageNsError(nsiId, "Error while translating internal NS termination message in Json format.");
            }
        } else {
            log.error("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Unable to terminate the NSI.");
            throw new NotExistingEntityException("Unable to find NS LCM Manager for NSI ID " + nsiId + ". Unable to terminate the NSI.");
        }
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String tenantId)
    		throws MalformattedElementException {
    	log.debug("Processing query network slice request");
    	request.isValid();
    	
    	//TODO: process tenant ID
    	
    	List<NetworkSliceInstance> nsis = new ArrayList<NetworkSliceInstance>();
    	Filter filter = request.getFilter();
    	Map<String, String> fParams = filter.getParameters();
    	if (fParams.isEmpty()) {
    		log.debug("Query all the network slices");
    		nsis.addAll(nsiRecordService.getAllNetworkSliceInstance());

    	} else if ( (fParams.size()==1) && (fParams.containsKey("NSI_ID"))) {
    		String nsiId = fParams.get("NSI_ID");
    		try {

    			NetworkSliceInstance nsi = nsiRecordService.getNetworkSliceInstance(nsiId);
    			nsis.add(nsi);
    		} catch (NotExistingEntityException e) {
    			log.error("Network slice instance not found. Returning empty list.");
    		}
    	} else {
    		log.error("Query filter not supported.");
    		throw new MalformattedElementException("Query filter not supported.");
    	}
    	return nsis;
    }

    @Override
    public List<NetworkSliceSubnetInstance> queryNetworkSliceSubnetInstance(GeneralizedQueryRequest request, String tenantId)
            throws MalformattedElementException {
        log.debug("Processing query network slice request");
        request.isValid();

        //TODO: process tenant ID

        List<NetworkSliceSubnetInstance> nsis = new ArrayList<NetworkSliceSubnetInstance>();
        Filter filter = request.getFilter();
        Map<String, String> fParams = filter.getParameters();
        if (fParams.isEmpty()) {
            log.debug("Query all the network slice subnets");
            nsis.addAll(nsiRecordService.getAllNetworkSliceSubnetInstance());
        } else {
            log.error("Query filter not supported.");
            throw new MalformattedElementException("Query filter not supported.");
        }
        return nsis;
    }

    @Override
    public UUID configureNetworkSlice(UpdateConfigurationRequest request, String tenantId) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("Processing network slice configuration request");
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug(mapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.warn("Could not deserialize request");
        }

        request.isValid();
        UUID nsiId = request.getNsiId();

        ConfigurationRequestRecord configurationRequestRecord = new ConfigurationRequestRecord(ConfigurationRequestStatus.IN_PROGRESS, nsiId,
                request.getActionType());
        configurationRequestRepo.saveAndFlush(configurationRequestRecord);
        if (nsLcmManagers.containsKey(nsiId)) {
            NetworkSliceInstanceRecord record = nsiRecordService.getNetworkSliceInstanceRecord(nsiId);
            if (record.getStatus() != NetworkSliceInstanceRecordStatus.INSTANTIATED) {
                log.error("Network slice " + nsiId + " not in INSTANTIATED  state. Cannot CONFIGURE it. Skipping message.");
                throw new NotPermittedOperationException("Network slice " + nsiId + " not in INSTANTIATED state. Current status:"+record.getStatus());
            }
            String topic = "nslifecycle.configurens." + nsiId;
            EngineUpdateNsiRequest internalMessage = new EngineUpdateNsiRequest(configurationRequestRecord.getId(),
                    request.getNstId(),
                    request.getNssiId(),
                    request, request.getSliceSubnetType());
            try {
                sendMessageToQueue(internalMessage, topic);
                return configurationRequestRecord.getId();

            } catch (JsonProcessingException e) {
                this.manageNsError(nsiId, "Error while translating internal NS configuration message in Json format.");
                throw  new FailedOperationException("Error while translating internal NS configuration message in Json format.");
            }
        } else {
            log.error("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Unable to instantiate the NSI.");
            throw new NotExistingEntityException("Unable to find NS LCM Manager for NSI ID " + nsiId + ". Unable to instantiate the NSI.");
        }



    }


    /**
     * This method implements the NssLcmNotificationConsumerInterface allowing the reception of
     * notifications from NSS LCM Service
     *
     * @param 
     */
    @Override
    public void notifyNssStatusChange(NsmfNotificationMessage nssiStatusChange) throws NotExistingEntityException, MalformattedElementException {
        String nssiId = nssiStatusChange.getNssiId().toString();
        log.debug("Processing notification about status change for NFV NS " + nssiStatusChange.getNssiId());
        nssiStatusChange.isValid();
        NetworkSliceSubnetInstanceRecord nssiRecord = nsiRecordService.getNetworkSliceSubnetInstanceRecord(nssiStatusChange.getNssiId());
        try {
                NetworkSliceInstanceRecord nsiRecord = nsiRecordService.getNetworkSliceInstanceRecord(nssiRecord.getNsiId());

                log.debug("NSS " + nssiId + " is associated to network slice " + nsiRecord.getId()+". Sending message to queue");
                EngineNotifyNssiStatusChange internalMessage = null;
                String topic = "nslifecycle.notifynss." + nsiRecord.getId();
                if(true){
                    internalMessage = new EngineNotifyNssiStatusChange(nssiStatusChange.getNssiId(),
                            nssiStatusChange.getNssiNotifType(),
                            !nssiStatusChange.getNssiNotifType().equals(NssiNotifType.ERROR),
                            nssiStatusChange.getNssiStatus());
                }

                sendMessageToQueue(internalMessage, topic);




        } catch (Exception e) {
            log.error("General exception while processing notification: " + e.getMessage());
        }
    }

    
    
    public void removeNsLcmManager(String nsiId) {
    	this.nsLcmManagers.remove(UUID.fromString(nsiId));
        log.debug("NS LCM removed from engine.");
    }



    
    /**
     * This method initializes a new NS LCM manager that will be in charge
     * of processing all the requests and events for that NSI.
     *
     * @param nsiId ID of the network slice instance for which the NS LCM Manager must be initialized
     */
    private void initNewNsLcmManager(UUID nsiId, NST networkSliceTemplate) {
        log.debug("Initializing new NSMF for NSI ID " + nsiId);
        NsLcmManager nsLcmManager = new NsLcmManager(nsiId,
                networkSliceTemplate,
                nsiRecordService,
                this,
                notificationDispatcher,
                resourceAllocationProvider,
                driverRegistry,
                configurationRequestRepo,
                infrastructureTopologyService,
                vsmfNotifier,
                notifyVsmf
                );
        createQueue(nsiId, nsLcmManager);
        nsLcmManagers.put(nsiId, nsLcmManager);
        log.debug("NS LCM manager for Network Slice Instance ID " + nsiId + " initialized and added to the engine.");
    }
    
    private void sendMessageToQueue(NsmfEngineMessage msg, String topic) throws JsonProcessingException {
        ObjectMapper mapper = buildObjectMapper();
        String json = mapper.writeValueAsString(msg);
        rabbitTemplate.convertAndSend(messageExchange.getName(), topic, json);
    }
    
    /**
     * This internal method creates a queue for the exchange of asynchronous messages
     * related to a given NSI.
     *
     * @param nsiId ID of the NSI for which the queue is created
     * @param nsiManager NSMF in charge of processing the queue messages
     */
    private void createQueue(UUID nsiId, NsLcmManager nsiManager) {

        String queueName = this.queueNamePrefix + nsiId;
        log.debug("Creating new Queue " + queueName + " in rabbit host " + rabbitHost);
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setAddresses(rabbitHost);
        cf.setConnectionTimeout(30000);

        RabbitAdmin rabbitAdmin = new RabbitAdmin(cf);
        Queue queue = new Queue(queueName, false, false, true);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(messageExchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(messageExchange).with("nslifecycle.*." + nsiId));
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
        MessageListenerAdapter adapter = new MessageListenerAdapter(nsiManager, "receiveMessage");
        container.setMessageListener(adapter);
        container.setQueueNames(queueName);
        container.start();
        log.debug("Queue created");
    }

    private void manageNsError(UUID nsiId, String s) {
        log.error("Error processing LCM action for NSI:"+nsiId);
        log.error(s);
    }

    private ObjectMapper buildObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper;
    }


    public void processResoureAllocationResponse(ResourceAllocationComputeResponse response){
        log.debug("Processing Resource Allocation Response");
        UUID nsiId = UUID.fromString(response.getNsResourceAllocation().getNsiId());
        log.debug("Processing NSI instantiation request for NSI ID " + nsiId);
        if (nsLcmManagers.containsKey(nsiId)) {

            String topic = "nslifecycle.notifyra." + nsiId;
            NotifyResourceAllocationResponse internalMessage = new NotifyResourceAllocationResponse(response);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                this.manageNsError(nsiId, "Error while translating internal NS instantiation message in Json format.");
            }
        } else {
            log.warn("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Ignoring message.");

        }
    }


    public List<ConfigurationOperation> queryConfigurationOperation() {
        log.debug("Received query for the Configuration Operations");
        return configurationRequestRepo.findAll().stream().map(cr -> cr.getConfigurationOperation()).collect(Collectors.toList());

    }
}
