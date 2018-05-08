package it.nextworks.nfvmano.sebastian.engine;

import java.util.HashMap;
import java.util.Map;

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

import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.common.ConfigurationParameters;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.TerminateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.vsmanagement.VsLcmManager;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.TerminateVsRequest;


/**
 * Service which manages all the VS and NS LCM Managers
 * 
 * @author nextworks
 *
 */
@Service
public class Engine {

	private static final Logger log = LoggerFactory.getLogger(Engine.class);
	
	@Autowired
	private VsRecordService vsRecordService;
	
	@Value("${spring.rabbitmq.host}")
	private String rabbitHost;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	@Qualifier(ConfigurationParameters.engineQueueExchange)
	TopicExchange messageExchange;
	
	@Autowired
	private TranslatorService translatorService;
	
	//internal map of VS LCM Managers
	//each VS LCM Manager is created when a new VSI ID is created and removed when the VSI ID is removed
	private Map<String, VsLcmManager> vsLcmManagers = new HashMap<>(); 
	
	public Engine() { }
	
	/**
	 * This method initializes a new VS LCM manager that will be in charge 
	 * of processing all the requests and event for that VSI.
	 * 
	 * @param vsiId ID of the VS instance for which the VS LCM Manager must be initialized
	 */
	public void initNewVsLcmManager(String vsiId) {
		log.debug("Initializing new VS LCM manager for VSI ID " + vsiId);
		VsLcmManager vsLcmManager = new VsLcmManager(vsiId, vsRecordService, translatorService);
		createQueue(vsiId, vsLcmManager);
		vsLcmManagers.put(vsiId, vsLcmManager);
		log.debug("VS LCM manager for VSI ID " + vsiId + " initialized and added to the engine.");
	}
	
	/**
	 * This method starts the procedures to instantiate a VSI, sending a message to 
	 * the related VS LCM Manager
	 * 
	 * @param vsiId ID of the VSI to be instantiated
	 * @param request request with the instantiation details
	 * @throws NotExistingEntityException if the VS LCM manager is not found
	 */
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
