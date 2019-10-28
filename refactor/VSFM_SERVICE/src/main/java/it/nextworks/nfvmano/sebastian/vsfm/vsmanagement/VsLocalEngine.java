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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.nfvodriver.NsStatusChange;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.sebastian.common.ConfigurationParameters;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.common.VsAction;
import it.nextworks.nfvmano.sebastian.vncom.vsfm.vssbi.N2VCommunicationInterface;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;
import it.nextworks.nfvmano.sebastian.vsfm.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.ModifyVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.TerminateVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.engine.*;
import it.nextworks.nfvmano.sebastian.vsfm.vscoordinator.VsCoordinator;
import it.nextworks.nfvmano.sebastian.vncom.vsfm.vssbi.VsSBIService;
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
import org.springframework.stereotype.Component;
;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VsLocalEngine implements N2VCommunicationInterface {

    private static final Logger log = LoggerFactory.getLogger(VsLocalEngine.class);
    /*NOTE: this enables the communication between the VSLCMs and the NSLCMs*/
    @Autowired
    private VsSBIService vsSBIService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private VsRecordService vsRecordService;

    @Autowired
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;

    @Autowired
    private TranslatorService translatorService;

    @Autowired
    private ArbitratorService arbitratorService;

    @Autowired
    private NfvoCatalogueService nfvoCatalogueService;

    @Autowired
    private VirtualResourceCalculatorService virtualResourceCalculatorService;

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier(ConfigurationParameters.engineQueueExchange)
    TopicExchange messageExchange;

    //internal map of VS LCM Managers
    //each VS LCM Manager is created when a new VSI ID is created and removed when the VSI ID is removed
    private Map<String, VsLcmManager> vsLcmManagers = new HashMap<>();

    //internal map of VS Coordinators
    //each VS Coordinator is created on demand, as soon as a VSI asks for resources for being instantiated
    private Map<String, VsCoordinator> vsCoordinators = new HashMap<>();

    public VsLocalEngine() { }


    /**
     * VS LCM METHODS
     */

    /**
     * This method initializes a new VS Coordinator that will be in charge of Terminate or Update VSIs
     * @param vsCoordinatorId Id of VS Coordinator instance. It will be the id of the VsLcm invoking it
     */
    public void initNewVsCoordinator(String vsCoordinatorId) {
        log.debug("Initializing new VS Coordinator with id " + vsCoordinatorId);
        VsCoordinator vsCoordinator = new VsCoordinator(vsCoordinatorId, this);
        createQueue(vsCoordinatorId, vsCoordinator);
        vsCoordinators.put(vsCoordinatorId, vsCoordinator);
        log.debug("VS Coordinator with id " + vsCoordinatorId + " initialized and added to the engine.");
    }

    /**
     * This method initializes a new VS LCM manager that will be in charge
     * of processing all the requests and events for that VSI.
     *
     * @param vsiId ID of the VS instance for which the VS LCM Manager must be initialized
     */
    public void initNewVsLcmManager(String vsiId) {
        log.debug("Initializing new VS LCM manager for VSI ID " + vsiId);
        VsLcmManager vsLcmManager = new VsLcmManager(vsiId, vsRecordService, vsDescriptorCatalogueService, translatorService, arbitratorService, adminService, nfvoCatalogueService, this, virtualResourceCalculatorService);
        createQueue(vsiId, vsLcmManager);
        vsLcmManagers.put(vsiId, vsLcmManager);
        log.debug("VS LCM manager for VSI ID " + vsiId + " initialized and added to the engine.");
    }

    public void instantiateVs(String vsiId, InstantiateVsRequest request) throws NotExistingEntityException {
        log.debug("Processing new VSI instantiation request for VSI ID " + vsiId);
        if (vsLcmManagers.containsKey(vsiId)) {
            String topic = "lifecycle.instantiatevs." + vsiId;
            InstantiateVsiRequestMessage internalMessage = new InstantiateVsiRequestMessage(vsiId, request);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal VS instantiation message in Json format.");
                vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal VS instantiation message in Json format.");
            }
        } else {
            log.error("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Unable to instantiate the VSI.");
            throw new NotExistingEntityException("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Unable to instantiate the VSI.");
        }
    }

    /**
     * This method starts the procedures to modify an existing VS by sending a message to
     * the proper VS LCM Manager
     *
     * @param vsiId ID of the VSI to be instantiated
     * @param request request with the modification details
     * @throws NotExistingEntityException
     */
    public void modifyVs(String vsiId, ModifyVsRequest request) throws NotExistingEntityException{
        log.debug("Processing new VSI modification request for VSI ID " + vsiId);
        if (vsLcmManagers.containsKey(vsiId)) {
            String topic = "lifecycle.modifyvs." + vsiId;
            ModifyVsiRequestMessage internalMessage = new ModifyVsiRequestMessage(vsiId, request);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal VS modification message in Json format.");
                vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal VS modification message in Json format.");
            }
        } else {
            log.error("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Unable to modify the VSI.");
            throw new NotExistingEntityException("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Unable to modify the VSI.");
        }
    }

    /**
     *
     * @param invokerVsiId Is the id of the Vsi invoking the coordination. VsCoordinator uses it as Coordinator ID
     * @param candidateVsis List of VSIs candidate for being terminated or updated
     */
    public void requestVsiCoordination(String invokerVsiId, Map<String, VsAction> candidateVsis){
        log.debug("Processing new VSI coordination request from VSI " + invokerVsiId);
        if (!vsCoordinators.containsKey(invokerVsiId))
            initNewVsCoordinator(invokerVsiId);
        String topic = "coordlifecycle.coordinatevs." + invokerVsiId;
        CoordinateVsiRequest internalMessage = new CoordinateVsiRequest(invokerVsiId, candidateVsis);
        try {
            sendMessageToQueue(internalMessage, topic);
        } catch (JsonProcessingException e) {
            log.error("Error while translating internal VS coordination message in Json format.");
            vsRecordService.setVsFailureInfo(invokerVsiId, "Error while translating internal VS coordination message in Json format.");
        }
    }

    /**
     * This method starts the procedures to terminate a VSI, sending a message to
     * the related VS LCM Manager
     *
     * @param vsiId ID of the VSI to be terminated
     * @param request request with the termination details
     * @throws NotExistingEntityException if the VS LCM manager is not found
     */
    public void terminateVs(String vsiId, TerminateVsRequest request) throws NotExistingEntityException {
        log.debug("Processing VSI termination request for VSI ID " + vsiId);
        if (vsLcmManagers.containsKey(vsiId)) {
            String topic = "lifecycle.terminatevs." + vsiId;
            TerminateVsiRequestMessage internalMessage = new TerminateVsiRequestMessage(vsiId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal VS termination message in Json format.");
                vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal VS termination message in Json format.");
            }
        } else {
            log.error("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Unable to terminate the VSI.");
            throw new NotExistingEntityException("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Unable to terminate the VSI.");
        }
    }

    /**
     * This method is called by the VS Coordinator once it is notified for the termination of the last VSI in the
     * termination candidate list
     *
     * @param vsiId both Coordinator and VSI invoker ID
     * @throws NotExistingEntityException
     */
    public void notifyVsCoordinationEnd(String vsiId) throws NotExistingEntityException {
        log.debug("Processing end of Vs Coordination notification from Vs Coordinator ID " + vsiId);
        if(vsCoordinators.containsKey(vsiId)){
            // 1st step: destroy the coordinator
            vsCoordinators.remove(vsiId);
            // 2nd step: unlock the invoker VSI frozen in waiting_for_resource status
            String topic = "lifecycle.resourcesgranted." + vsiId;
            NotifyResourceGranted internalMessage = new NotifyResourceGranted(vsiId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal RESOURCES GRANTED message in Json format.");
                vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal RESOURCES GRANTED message in Json format.");
            }
        } else {
            log.error("Unable to find VS Coordinator for VSI ID " + vsiId + ". Unable to instantiate the invoker VSI");
            throw new NotExistingEntityException("Unable to find VS Coordinator for VSI ID " + vsiId + ". Unable to instantiate the invoker VSI");
        }
    }

    /**
     * This methd is invoked by VsLcmManager as soon as its own termination process succeeded
     *
     * @param vsiId Id of terminated VSI
     * @throws NotExistingEntityException
     */
    public void notifyVsiTermination(String vsiId) throws NotExistingEntityException {
        log.debug("Processing VSI termination request for VSI ID " + vsiId);
        if (vsLcmManagers.containsKey(vsiId)) {
            log.debug("VSI " + vsiId + " TERMINATED");
            for (Map.Entry<String, VsCoordinator> coordEntry: vsCoordinators.entrySet()){
                VsCoordinator coordinator = coordEntry.getValue();
                if(coordinator.getCandidateVsis().containsKey(vsiId)){
                    String topic = "coordlifecycle.notifyterm." + coordEntry.getKey();
                    VsiTerminationNotificationMessage internalMessage = new VsiTerminationNotificationMessage(vsiId);
                    try {
                        sendMessageToQueue(internalMessage, topic);
                    } catch (JsonProcessingException e) {
                        log.error("Error while translating internal VSI TERMINATION notification message in Json format.");
                        vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal VSI TERMINATION notification message in Json format.");
                    }
                }
            }
        } else {
            log.error("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Invalid Termination notification.");
            throw new NotExistingEntityException("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Invalid Termination notification.");
        }
    }

    /**
     * This method removes a VS LC manager from the engine map
     *
     * @param vsiId ID of the VS whose VS LCM must be removed
     */
    public void removeVerticalServiceLcmManager(String vsiId) {
        log.debug("Vertical service " + vsiId + " has been terminated. Removing VS LCM from engine");
        this.vsLcmManagers.remove(vsiId);
        log.debug("VS LCM manager removed from engine.");
    }

    /**
     * METHOD impementing the N2VCommunicationInterface
     */
    @Override
    public void notifyNetworkSliceStatusChange(String networkSliceId, NsStatusChange changeType, boolean successful) {
        log.debug("Processing notification about status change for network slice " + networkSliceId);
        List<VerticalServiceInstance> vsis = vsRecordService.getVsInstancesFromNetworkSlice(networkSliceId);
        for (VerticalServiceInstance vsi : vsis) {
            String vsiId = vsi.getVsiId();
            log.debug("Network Slice " + networkSliceId + " is associated to vertical service " + vsiId);
            if (vsLcmManagers.containsKey(vsiId)) {
                String topic = "lifecycle.notifyns." + vsiId;
                NotifyNsiStatusChange internalMessage = new NotifyNsiStatusChange(networkSliceId, changeType);
                try {
                    sendMessageToQueue(internalMessage, topic);
                } catch (Exception e) {
                    log.error("General exception while sending message to queue.");
                }
            } else {
                log.error("Unable to find Vertical Service LCM Manager for VSI ID " + vsiId + ". Unable to notify associated NS status change.");
            }
        }
//        if (changeType == NsStatusChange.NS_TERMINATED) {
//            log.debug("Network slice " + networkSliceId + " has been terminated. Removing NS LCM from engine");
//            this.nsLcmManagers.remove(networkSliceId);
//            log.debug("NS LCM removed from engine.");
//        }
    }

    /**
     * METHODS for communicatig with NS Management
     */

    public void initNewNsLcmManager(String nsiId, String tenantId, String sliceName, String sliceDescription) {
        vsSBIService.initNewNsLcmManager(nsiId, tenantId, sliceName, sliceDescription);
    }

    public void instantiateNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId, List<String> nsSubnetIds) throws NotExistingEntityException {
        vsSBIService.instantiateNs(nsiId, tenantId, nsdId, nsdVersion, dfId, instantiationLevelId, vsiId, nsSubnetIds);
    }

    public void modifyNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId) throws NotExistingEntityException {
        vsSBIService.modifyNs(nsiId, tenantId, nsdId, nsdVersion, dfId, instantiationLevelId, vsiId);
    }

    public void terminateNs(String nsiId) throws Exception {
        vsSBIService.terminateNs(nsiId);
    }

    /**
     * SERVICE METHODS
     */

    /**
     * This internal method sends a message to the internal queue, using a specific topic
     *
     * @param msg
     * @param topic
     * @throws JsonProcessingException
     */
    private void sendMessageToQueue(EngineMessage msg, String topic) throws JsonProcessingException {
        ObjectMapper mapper = Utilities.buildObjectMapper();
        String json = mapper.writeValueAsString(msg);
        rabbitTemplate.convertAndSend(messageExchange.getName(), topic, json);
    }

    /**
     *
     * @param vsCoordinatorId Id of the VsCoordinator (tentantId as candidate)
     * @param vsCoordinator VSI coordinator in charge of processing messages
     */
    private void createQueue(String vsCoordinatorId, VsCoordinator vsCoordinator) {
        String queueName = ConfigurationParameters.engineQueueNamePrefix +"coord"+ vsCoordinatorId;
        log.debug("Creating new Queue " + queueName + " in rabbit host " + rabbitHost);
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setAddresses(rabbitHost);
        cf.setConnectionTimeout(5);
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cf);
        Queue queue = new Queue(queueName, false, false, true);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(messageExchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(messageExchange).with("coordlifecycle.*." + vsCoordinatorId));
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
        MessageListenerAdapter adapter = new MessageListenerAdapter(vsCoordinator, "receiveMessage");
        container.setMessageListener(adapter);
        container.setQueueNames(queueName);
        container.start();
        log.debug("Queue created");
    }

    /**
     * This internal method creates a queue for the exchange of asynchronous messages
     * related to a given VSI.
     *
     * @param vsiId ID of the VSI for which the queue is created
     * @param vsiManager VSI Manager in charge of processing the queue messages
     */
    private void createQueue(String vsiId, VsLcmManager vsiManager) {
        String queueName = ConfigurationParameters.engineQueueNamePrefix + vsiId;
        log.debug("Creating new Queue " + queueName + " in rabbit host " + rabbitHost);
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setAddresses(rabbitHost);
        cf.setConnectionTimeout(5);
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cf);
        Queue queue = new Queue(queueName, false, false, true);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(messageExchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(messageExchange).with("lifecycle.*." + vsiId));
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
        MessageListenerAdapter adapter = new MessageListenerAdapter(vsiManager, "receiveMessage");
        container.setMessageListener(adapter);
        container.setQueueNames(queueName);
        container.start();
        log.debug("Queue created");
    }
}
