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

package it.nextworks.nfvmano.sebastian.nsmf.nsmanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmNotificationConsumerInterface;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmService;
import it.nextworks.nfvmano.nfvodriver.NsStatusChange;
import it.nextworks.nfvmano.sebastian.common.ConfigurationParameters;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.vncom.nsfm.nsnbi.N2VCommunicationService;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NotifyNfvNsiStatusChange;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.nsmf.messages.EngineMessage;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NsLocalEngine implements NfvoLcmNotificationConsumerInterface {

    private static final Logger log = LoggerFactory.getLogger(NsLocalEngine.class);

    @Autowired
    private N2VCommunicationService n2VCommunicationService;

    @Autowired
    private VsRecordService vsRecordService;

    @Autowired
    private NfvoCatalogueService nfvoCatalogueService;

    @Autowired
    private NfvoLcmService nfvoLcmService;

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier(ConfigurationParameters.engineQueueExchange)
    TopicExchange messageExchange;

    //internal map of VS LCM Managers
    //each VS LCM Manager is created when a new VSI ID is created and removed when the VSI ID is removed
    private Map<String, NsLcmManager> nsLcmManagers = new HashMap<>();

    public NsLocalEngine() { }

    /**
     * This method implements the NfvoLcmNotificationConsumerInterface allowing teh receiveng of
     * notifications from NFVO LCM Service
     *
     * @param nfvNsId
     * @param changeType
     * @param successful
     */
    @Override
    public void notifyNfvNsStatusChange(String nfvNsId, NsStatusChange changeType, boolean successful) {
        log.debug("Processing notification about status change for NFV NS " + nfvNsId);
        try {
            NetworkSliceInstance nsi = vsRecordService.getNsInstanceFromNfvNsi(nfvNsId);
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
    /**
     * NS LCM METHODS
     */

    /**
     * This method initializes a new NS LCM manager that will be in charge
     * of processing all the requests and events for that NSI.
     *
     * @param nsiId ID of the network slice instance for which the NS LCM Manager must be initialized
     */
    public void initNewNsLcmManager(String nsiId, String tenantId, String sliceName, String sliceDescription) {
        log.debug("Initializing new NSMF for NSI ID " + nsiId);
        NsLcmManager nsLcmManager = new NsLcmManager(nsiId, sliceName, sliceDescription, tenantId, nfvoCatalogueService, nfvoLcmService, vsRecordService, this);
        createQueue(nsiId, nsLcmManager);
        nsLcmManagers.put(nsiId, nsLcmManager);
        log.debug("NS LCM manager for Network Slice Instance ID " + nsiId + " initialized and added to the engine.");
    }

    /**
     * This method starts the procedures to instantiate a NSI, sending a message to
     * the related NS LCM Manager
     *
     * @param nsiId ID of the NS instance to be instantiated
     * @param tenantId tenant owning the NS instance
     * @param nsdId NSD ID of the NFV NS that implements the NS instance
     * @param nsdVersion NSD version of the NFV NS that implements the NS instance
     * @param dfId DF ID of the NFV NS that implements the NS instance
     * @param instantiationLevelId instantiation level ID of the NFV NS that implements the NS instance
     * @param vsiId ID of the Vertical Service instance associated to the network slice, if available
     * @throws NotExistingEntityException if the NS LCM manager is not found
     */
    public void instantiateNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId, List<String> nsSubnetIds) throws NotExistingEntityException {
        log.debug("Processing new NSI instantiation request for NSI ID " + nsiId);
        if (nsLcmManagers.containsKey(nsiId)) {
            String topic = "nslifecycle.instantiatens." + nsiId;
            Map<String, String> userData = new HashMap<>();
            LocationInfo locationConstraints = null;
            String ranEndPointId = null;
            if (vsiId != null) {
                VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiId);
                userData = vsi.getUserData();
                locationConstraints = vsi.getLocationConstraints();
                ranEndPointId = vsi.getRanEndPointId();
            }
            InstantiateNsiRequestMessage internalMessage = new InstantiateNsiRequestMessage(nsiId, nsdId, nsdVersion, dfId, instantiationLevelId, nsSubnetIds, userData, locationConstraints, ranEndPointId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal NS instantiation message in Json format.");
                if (vsiId != null) vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal NS instantiation message in Json format.");
            }
        } else {
            log.error("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Unable to instantiate the NSI.");
            throw new NotExistingEntityException("Unable to find NS LCM Manager for NSI ID " + nsiId + ". Unable to instantiate the NSI.");
        }
    }

    /**
     * This method starts the procedures to modify a NSI, sending a message to
     * the related NS LCM Manager
     *
     * @param nsiId ID of the NS instance to be instantiated
     * @param tenantId tenant owning the NS instance
     * @param nsdId NSD ID of the NFV NS that implements the NS instance
     * @param nsdVersion NSD version of the NFV NS that implements the NS instance
     * @param dfId DF ID of the NFV NS that implements the NS instance
     * @param instantiationLevelId instantiation level ID of the NFV NS that implements the NS instance
     * @param vsiId ID of the Vertical Service instance associated to the network slice, if available
     * @throws NotExistingEntityException if the NS LCM manager is not found
     */
    public void modifyNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId)
            throws NotExistingEntityException {
        log.debug("Processing new NSI modification request for NSI ID " + nsiId);
        if (nsLcmManagers.containsKey(nsiId)) {
            String topic = "nslifecycle.modifyns." + nsiId;
            ModifyNsiRequestMessage internalMessage = new ModifyNsiRequestMessage(nsiId, nsdId, nsdVersion, dfId, instantiationLevelId, vsiId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal NS modification message in Json format.");
                if (vsiId != null) vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal NS modification message in Json format.");
            }
        } else {
            log.error("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Unable to modify the NSI.");
            throw new NotExistingEntityException("Unable to find NS LCM Manager for NSI ID " + nsiId + ". Unable to modify the NSI.");
        }
    }

    /**
     * This method starts the procedures to terminate a NSI, sending a message to
     * the related NS LCM Manager
     *
     * @param nsiId ID of the NS instance to be terminated
     * @throws Exception
     */
    public void terminateNs(String nsiId) throws Exception {
        log.debug("Processing NSI termination request for NSI ID " + nsiId);
        if (nsLcmManagers.containsKey(nsiId)) {
            String topic = "nslifecycle.terminatens." + nsiId;
            TerminateNsiRequestMessage internalMessage = new TerminateNsiRequestMessage(nsiId);
            sendMessageToQueue(internalMessage, topic);
        } else {
            log.error("Unable to find Network Slice LCM Manager for NSI ID " + nsiId + ". Unable to terminate the NSI.");
            throw new NotExistingEntityException("Unable to find NS LCM Manager for NSI ID " + nsiId + ". Unable to terminate the NSI.");
        }
    }

    /**
     * This methdo is part of the NsLcMService Iface
     * @param networkSliceId
     * @param changeType
     * @param successful
     */
    public void notifyNetworkSliceStatusChange(String networkSliceId, NsStatusChange changeType, boolean successful) {
        // this if statement contains operation that can be executed locally only
        if (changeType == NsStatusChange.NS_TERMINATED) {
            log.debug("Network slice " + networkSliceId + " has been terminated. Removing NS LCM from engine");
            this.nsLcmManagers.remove(networkSliceId);
            log.debug("NS LCM removed from engine.");
        }

        n2VCommunicationService.notifyNetworkSliceStatusChange(networkSliceId, changeType, successful);
    }

    /**
     * SERVICE METHODS
     */
    private void sendMessageToQueue(EngineMessage msg, String topic) throws JsonProcessingException {
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
}
