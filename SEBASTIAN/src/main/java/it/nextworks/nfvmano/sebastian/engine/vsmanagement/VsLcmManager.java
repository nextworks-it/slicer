package it.nextworks.nfvmano.sebastian.engine.vsmanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;
import it.nextworks.nfvmano.sebastian.engine.Engine;
import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessageType;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.NotifyNsiStatusChange;
import it.nextworks.nfvmano.sebastian.engine.messages.TerminateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;

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
	private VsRecordService vsRecordService;
	private TranslatorService translatorService;
	private ArbitratorService arbitratorService;
	private VsDescriptorRepository vsDescriptorRepository;
	private Engine engine;
	
	private VerticalServiceStatus internalStatus;
	
	private String networkSliceId;
	
	//Key: VSD ID; Value: VSD
	private Map<String, VsDescriptor> vsDescriptors = new HashMap<>();
	private String tenantId;
	
	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the vertical service instance
	 * @param vsRecordService wrapper of VSI record
	 * @param vsDescriptorRepository repo of VSDs
	 * @param translatorService translator service
	 * @param arbitratorService arbitrator service
	 * @param engine engine
	 */
	public VsLcmManager(String vsiId,
			VsRecordService vsRecordService,
			VsDescriptorRepository vsDescriptorRepository,
			TranslatorService translatorService,
			ArbitratorService arbitratorService,
			Engine engine) {
		this.vsiId = vsiId;
		this.vsRecordService = vsRecordService;
		this.vsDescriptorRepository = vsDescriptorRepository;
		this.translatorService = translatorService;
		this.arbitratorService = arbitratorService;
		this.internalStatus = VerticalServiceStatus.INSTANTIATING;
		this.engine = engine;
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
			EngineMessage em = (EngineMessage) mapper.readValue(message, EngineMessage.class);
			EngineMessageType type = em.getType();
			
			switch (type) {
			case INSTANTIATE_VSI_REQUEST: {
				log.debug("Processing VSI instantiation request.");
				InstantiateVsiRequestMessage instantiateVsRequestMsg = (InstantiateVsiRequestMessage)em;
				processInstantiateRequest(instantiateVsRequestMsg);
				break;
			}
			
			case TERMINATE_VSI_REQUEST: {
				log.debug("Processing VSI termination request.");
				TerminateVsiRequestMessage terminateVsRequestMsg = (TerminateVsiRequestMessage)em;
				processTerminateRequest(terminateVsRequestMsg);
				break;
			}
			
			case NOTIFY_NSI_STATUS_CHANGE: {
				log.debug("Processing NSI status change notification.");
				NotifyNsiStatusChange notifyNsiStatusChangeMsg = (NotifyNsiStatusChange)em;
				processNsiStatusChangeNotification(notifyNsiStatusChangeMsg);
				break;
			}

			default:
				log.error("Received message with not supported type. Skipping.");
				break;
			}
					
		} catch(JsonParseException e) {
			manageVsError("Error while parsing message: " + e.getMessage());
		} catch(JsonMappingException e) {
			manageVsError("Error in Json mapping: " + e.getMessage());
		} catch(IOException e) {
			manageVsError("IO error when receiving json message: " + e.getMessage());
		}
	}
	
	private void processInstantiateRequest(InstantiateVsiRequestMessage msg) {
		if (internalStatus != VerticalServiceStatus.INSTANTIATING) {
			manageVsError("Received instantiation request in wrong status. Skipping message.");
			return;
		}
		String vsdId = msg.getRequest().getVsdId();
		log.debug("Instanting Vertical Service " + vsiId + " with VSD " + vsdId);
		VsDescriptor vsd = vsDescriptorRepository.findByVsDescriptorId(vsdId).get();
		this.vsDescriptors.put(vsdId, vsd);
		this.tenantId = msg.getRequest().getTenantId();
		
		List<String> vsdIds = new ArrayList<>();
		vsdIds.add(vsdId);
		try {
			Map<String, NfvNsInstantiationInfo> nsInfo = translatorService.translateVsd(vsdIds);
			log.debug("The VSD has been translated in the required network slice characteristics.");
			
			List<ArbitratorRequest> arbitratorRequests = new ArrayList<>();
			//only a single request is supported at the moment
			ArbitratorRequest arbitratorRequest = new ArbitratorRequest("requestId", tenantId, vsd, nsInfo);
			arbitratorRequests.add(arbitratorRequest);
			ArbitratorResponse arbitratorResponse = arbitratorService.computeArbitratorSolution(arbitratorRequests).get(0);
			if (!(arbitratorResponse.isAcceptableRequest())) {
				manageVsError("Error while instantiating VS " + vsiId + ": no solution returned from the arbitrator");
				return;
			}
			if (arbitratorResponse.isNewSliceRequired()) {
				log.debug("A new network slice should be instantiated for the Vertical Service instance " + vsiId);
				NfvNsInstantiationInfo nsiInfo = nsInfo.get(vsdId);
				//TODO: to be extended for composite VSDs
				String nsiId = vsRecordService.createNetworkSliceForVsi(vsiId, nsiInfo.getNfvNsdId(), nsiInfo.getNsdVersion(), nsiInfo.getDeploymentFlavourId(),
						nsiInfo.getInstantiationLevelId(), tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
				log.debug("Network Slice ID " + nsiId + " created for VSI " + vsiId);
				this.networkSliceId = nsiId;
				vsRecordService.setNsiInVsi(vsiId, nsiId);
				log.debug("Record updated with info about NSI and VSI association.");
				engine.initNewNsLcmManager(networkSliceId, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
				engine.instantiateNs(nsiId, tenantId, nsiInfo.getNfvNsdId(), nsiInfo.getNsdVersion(), 
						nsiInfo.getDeploymentFlavourId(), nsiInfo.getInstantiationLevelId(), vsiId);
			} else {
				//slice to be shared, not supported at the moment
				manageVsError("Error while instantiating VS " + vsiId + ": solution with slice sharing returned from the arbitrator. Not supported at the moment.");
			}
		} catch (Exception e) {
			manageVsError("Error while instantiating VS " + vsiId + ": " + e.getMessage());
		}
	}
	
	private void processTerminateRequest(TerminateVsiRequestMessage msg) {
		//TODO:
	}
	
	private void processNsiStatusChangeNotification(NotifyNsiStatusChange msg) {
		//TODO:
	}
	
	private void manageVsError(String errorMessage) {
		log.error(errorMessage);
		vsRecordService.setVsFailureInfo(vsiId, errorMessage);
	}
	
}
