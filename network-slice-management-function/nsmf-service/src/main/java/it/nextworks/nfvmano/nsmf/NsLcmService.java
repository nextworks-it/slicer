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
import com.fasterxml.jackson.databind.SerializationFeature;

import it.nextworks.nfvmano.catalogue.template.elements.GsTemplateInfo;
import it.nextworks.nfvmano.catalogue.template.elements.NsTemplateInfo;
import it.nextworks.nfvmano.catalogue.template.interfaces.NestCatalogueInterface;
import it.nextworks.nfvmano.catalogue.template.interfaces.NsTemplateCatalogueInterface;
import it.nextworks.nfvmano.catalogue.template.messages.nest.QueryNesTemplateResponse;
import it.nextworks.nfvmano.catalogue.template.messages.nst.QueryNsTemplateResponse;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceSubnetType;

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



    /********************************************************************************/

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

            UUID networkSliceId = nsiRecordService.createNetworkSliceInstanceEntry (
                    nstId,
                    request.getVsInstanceId(),
                    tenantId,
                    request.getName(),
                    nsTemplate.getNsst().getSliceProfileList().get(0).geteMBBPerfReq().get(0).getExpDataRateDL(),
                    nsTemplate.getNsst().getSliceProfileList().get(0).geteMBBPerfReq().get(0).getExpDataRateUL()
            );
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
    		throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
    	log.debug("Processing request to instantiate a network slice instance");
    	request.isValid();
    	UUID nsiId = request.getNsiId();
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
