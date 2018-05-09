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
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;
import it.nextworks.nfvmano.sebastian.common.ConfigurationParameters;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.TerminateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.TerminateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.nsmf.NsLcmManager;
import it.nextworks.nfvmano.sebastian.engine.vsmanagement.VsLcmManager;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
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
	
	@Autowired
	private VsDescriptorRepository vsDescriptorRepository;
	
	@Value("${spring.rabbitmq.host}")
	private String rabbitHost;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	@Qualifier(ConfigurationParameters.engineQueueExchange)
	TopicExchange messageExchange;
	
	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private ArbitratorService arbitratorService;
	
	@Autowired
	private NfvoService nfvoService;
	
	//internal map of VS LCM Managers
	//each VS LCM Manager is created when a new VSI ID is created and removed when the VSI ID is removed
	private Map<String, VsLcmManager> vsLcmManagers = new HashMap<>();
	
	//internal map of VS LCM Managers
	//each VS LCM Manager is created when a new VSI ID is created and removed when the VSI ID is removed
	private Map<String, NsLcmManager> nsLcmManagers = new HashMap<>();
	
	public Engine() { }
	
	/**
	 * This method initializes a new VS LCM manager that will be in charge 
	 * of processing all the requests and events for that VSI.
	 * 
	 * @param vsiId ID of the VS instance for which the VS LCM Manager must be initialized
	 */
	public void initNewVsLcmManager(String vsiId) {
		log.debug("Initializing new VS LCM manager for VSI ID " + vsiId);
		VsLcmManager vsLcmManager = new VsLcmManager(vsiId, vsRecordService, vsDescriptorRepository, translatorService, arbitratorService, this);
		createQueue(vsiId, vsLcmManager);
		vsLcmManagers.put(vsiId, vsLcmManager);
		log.debug("VS LCM manager for VSI ID " + vsiId + " initialized and added to the engine.");
	}
	
	/**
	 * This method initializes a new NS LCM manager that will be in charge
	 * of processing all the requests and events for that NSI.
	 * 
	 * @param nsiId ID of the network slice instance for which the NS LCM Manager must be initialized
	 */
	public void initNewNsLcmManager(String nsiId, String tenantId, String sliceName, String sliceDescription) {
		log.debug("Initializing new NSMF for NSI ID " + nsiId);
		NsLcmManager nsLcmManager = new NsLcmManager(nsiId, sliceName, sliceDescription, tenantId, nfvoService, vsRecordService);
		createQueue(nsiId, nsLcmManager);
		nsLcmManagers.put(nsiId, nsLcmManager);
		log.debug("NS LCM manager for Network Slice Instance ID " + nsiId + " initialized and added to the engine.");
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
	public void instantiateNs(String nsiId, String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String vsiId) throws NotExistingEntityException {
		log.debug("Processing new NSI instantiation request for NSI ID " + nsiId);
		if (nsLcmManagers.containsKey(nsiId)) {
			String topic = "nslifecycle.instantiatens." + nsiId;
			InstantiateNsiRequestMessage internalMessage = new InstantiateNsiRequestMessage(nsiId, nsdId, nsdVersion, dfId, instantiationLevelId);
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
