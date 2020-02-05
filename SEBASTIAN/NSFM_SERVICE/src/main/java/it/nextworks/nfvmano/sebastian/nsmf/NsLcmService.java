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

package it.nextworks.nfvmano.sebastian.nsmf;


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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmNotificationConsumerInterface;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmService;
import it.nextworks.nfvmano.nfvodriver.NsStatusChange;
import it.nextworks.nfvmano.catalogue.template.elements.NsTemplateInfo;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.common.ConfigurationParameters;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.InstantiateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.ModifyNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.NotifyNfvNsiStatusChange;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.NsmfEngineMessage;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.TerminateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.nsmanagement.NsLcmManager;
import it.nextworks.nfvmano.sebastian.record.NsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class NsLcmService implements NsmfLcmProviderInterface, NfvoLcmNotificationConsumerInterface {

    private static final Logger log = LoggerFactory.getLogger(NsLcmService.class);

    @Autowired
    private NsRecordService nsRecordService;
    
    @Autowired
    private NsmfUtils nsmfUtils; 
    
    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier(ConfigurationParameters.engineQueueExchange)
    TopicExchange messageExchange;
    
    @Autowired
    private NfvoCatalogueService nfvoCatalogueService;

    @Autowired
    private NfvoLcmService nfvoLcmService;

    //internal map of VS LCM Managers
    //each VS LCM Manager is created when a new VSI ID is created and removed when the VSI ID is removed
    private Map<String, NsLcmManager> nsLcmManagers = new HashMap<>();
    
    private NsmfLcmConsumerInterface notificationDispatcher;

    /********************************************************************************/

    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String tenantId)
    		throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {


    	log.debug("Processing request to create a new network slicer identifier");
    	request.isValid();
    	String nstId = request.getNstId();
    	
    	NsTemplateInfo nstInfo = nsmfUtils.getNsTemplateInfoFromCatalogue(nstId);
    	NST nsTemplate = nstInfo.getNST();
    	if (nsTemplate == null) {
    		log.error("Null NS template retrieved from the catalogue");
    		throw new NotExistingEntityException("Null NS template retrieved from the catalogue");
    	}
    	log.debug("Network Slice Template retrieved from catalogue");
    	
    	String nsdId = nsTemplate.getNsdId(); 
    	String nsdVersion = nsTemplate.getNsdVersion();
    	
    	String networkSliceId = nsRecordService.createNetworkSliceInstanceEntry (
    			nstId,
    			nsdId,
    			nsdVersion,
    			null,							//DF ID
    			null,							//IL ID
    			null, 							//nfvNsId,
    			new ArrayList<String>(),		// networkSliceSubnetInstances,
    			tenantId,
    			request.getName(),
    			request.getDescription(),
    			false							//SO managed
    	);
    	initNewNsLcmManager(networkSliceId, tenantId, request.getName(), request.getDescription(), nsTemplate);
    	return networkSliceId;
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String tenantId)
    		throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
    	log.debug("Processing request to instantiate a network slice instance");
    	request.isValid();
    	String nsiId = request.getNsiId();
    	log.debug("Processing NSI instantiation request for NSI ID " + nsiId);
        if (nsLcmManagers.containsKey(nsiId)) {
        	NetworkSliceStatus sliceStatus = nsLcmManagers.get(nsiId).getInternalStatus();
        	if (sliceStatus != NetworkSliceStatus.NOT_INSTANTIATED) {
        		log.error("Network slice " + nsiId + " not in NOT INSTANTIATED state. Cannot instantiate it. Skipping message.");
        		throw new NotPermittedOperationException("Network slice " + nsiId + " not in NOT INSTANTIATED state.");
        	}
            String topic = "nslifecycle.instantiatens." + nsiId;
            InstantiateNsiRequestMessage internalMessage = new InstantiateNsiRequestMessage(request, tenantId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
            	nsmfUtils.manageNsError(nsiId, "Error while translating internal NS instantiation message in Json format.");
            }
        } else {
            log.error("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Unable to instantiate the NSI.");
            throw new NotExistingEntityException("Unable to find NS LCM Manager for NSI ID " + nsiId + ". Unable to instantiate the NSI.");
        }
    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest request, String tenantId)
    		throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
    	log.debug("Processing request to modify a network slice instance");
    	request.isValid();
    	String nsiId = request.getNsiId();
    	log.debug("Processing new NSI modification request for NSI ID " + nsiId);
        if (nsLcmManagers.containsKey(nsiId)) {
        	NetworkSliceStatus sliceStatus = nsLcmManagers.get(nsiId).getInternalStatus();
        	if (sliceStatus != NetworkSliceStatus.INSTANTIATED) {
        		log.error("Network slice " + nsiId + " not in INSTANTIATED state. Cannot modify it. Skipping message.");
        		throw new NotPermittedOperationException("Network slice " + nsiId + " not in INSTANTIATED state.");
        	}
            String topic = "nslifecycle.modifyns." + nsiId;
            ModifyNsiRequestMessage internalMessage = new ModifyNsiRequestMessage(request, tenantId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
            	nsmfUtils.manageNsError(nsiId, "Error while translating internal NS modification message in Json format.");
            }
        } else {
            log.error("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Unable to modify the NSI.");
            throw new NotExistingEntityException("Unable to find NS LCM Manager for NSI ID " + nsiId + ". Unable to modify the NSI.");
        }
    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest request, String tenantId)
    		throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
    	log.debug("Processing request to terminate a network slice instance");
    	request.isValid();
    	String nsiId = request.getNsiId();
    	log.debug("Processing NSI termination request for NSI ID " + nsiId);
        if (nsLcmManagers.containsKey(nsiId)) {
        	NetworkSliceStatus sliceStatus = nsLcmManagers.get(nsiId).getInternalStatus();
        	if (sliceStatus != NetworkSliceStatus.INSTANTIATED) {
        		log.error("Network slice " + nsiId + " not in INSTANTIATED state. Cannot terminate it. Skipping message.");
        		throw new NotPermittedOperationException("Network slice " + nsiId + " not in INSTANTIATED state.");
        	}
            String topic = "nslifecycle.terminatens." + nsiId;
            TerminateNsiRequestMessage internalMessage = new TerminateNsiRequestMessage(request, tenantId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
            	nsmfUtils.manageNsError(nsiId, "Error while translating internal NS termination message in Json format.");
            }
        } else {
            log.error("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Unable to terminate the NSI.");
            throw new NotExistingEntityException("Unable to find NS LCM Manager for NSI ID " + nsiId + ". Unable to terminate the NSI.");
        }
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String tenantId) 
    		throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
    	log.debug("Processing query network slice request");
    	request.isValid();
    	
    	//TODO: process tenant ID
    	
    	List<NetworkSliceInstance> nsis = new ArrayList<NetworkSliceInstance>();
    	Filter filter = request.getFilter();
    	Map<String, String> fParams = filter.getParameters();
    	if (fParams.isEmpty()) {
    		log.debug("Query all the network slices");
    		nsis.addAll(nsRecordService.getAllNetworkSliceInstance());
    	} else if ( (fParams.size()==1) && (fParams.containsKey("NSI_ID"))) {
    		String nsiId = fParams.get("NSI_ID");
    		try {
    			NetworkSliceInstance nsi = nsRecordService.getNsInstance(nsiId);
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

    /**
     * This method implements the NfvoLcmNotificationConsumerInterface allowing the reception of
     * notifications from NFVO LCM Service
     *
     * @param nfvNsId ID of the NFV Network service this notification refers to
     * @param changeType type of LCM change
     * @param successful if the change has been successful
     */
    @Override
    public void notifyNfvNsStatusChange(String nfvNsId, NsStatusChange changeType, boolean successful) {
        log.debug("Processing notification about status change for NFV NS " + nfvNsId);
        try {
            NetworkSliceInstance nsi = nsRecordService.getNsInstanceFromNfvNsi(nfvNsId);
            String nsiId = nsi.getNsiId();
            log.debug("NFV NS " + nfvNsId + " is associated to network slice " + nsiId);
            String topic = "nslifecycle.notifynfvns." + nsiId;
            NotifyNfvNsiStatusChange internalMessage = new NotifyNfvNsiStatusChange(nfvNsId, changeType, successful);
            sendMessageToQueue(internalMessage, topic);
        } catch (NotExistingEntityException e) {
            log.error("Unable to process the notification: " + e.getMessage() + ". Skipping message.");
        } catch (Exception e) {
            log.error("General exception while processing notification: " + e.getMessage());
        }
    }

    
    
    public void removeNsLcmManager(String nsiId) {
    	this.nsLcmManagers.remove(nsiId);
        log.debug("NS LCM removed from engine.");
    }
    
    /**
     * This method initializes a new NS LCM manager that will be in charge
     * of processing all the requests and events for that NSI.
     *
     * @param nsiId ID of the network slice instance for which the NS LCM Manager must be initialized
     */
    private void initNewNsLcmManager(String nsiId, String tenantId, String sliceName, String sliceDescription, NST networkSliceTemplate) {
        log.debug("Initializing new NSMF for NSI ID " + nsiId);
        NsLcmManager nsLcmManager = new NsLcmManager(nsiId, sliceName, sliceDescription, tenantId, nfvoCatalogueService, nfvoLcmService, nsRecordService, notificationDispatcher, this, networkSliceTemplate, nsmfUtils);
        createQueue(nsiId, nsLcmManager);
        nsLcmManagers.put(nsiId, nsLcmManager);
        log.debug("NS LCM manager for Network Slice Instance ID " + nsiId + " initialized and added to the engine.");
    }

    private void sendMessageToQueue(NsmfEngineMessage msg, String topic) throws JsonProcessingException {
        ObjectMapper mapper = Utilities.buildObjectMapper();
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
    private void createQueue(String nsiId, NsLcmManager nsiManager) {
        String queueName = ConfigurationParameters.engineQueueNamePrefix + nsiId;
        log.debug("Creating new Queue " + queueName + " in rabbit host " + rabbitHost);
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setAddresses(rabbitHost);
        cf.setConnectionTimeout(5);
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

	public void setNotificationDispatcher(NsmfLcmConsumerInterface notificationDispatcher) {
		this.notificationDispatcher = notificationDispatcher;
		nsmfUtils.setNotificationDispatcher(notificationDispatcher);
	}

    
}
