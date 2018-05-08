package it.nextworks.nfvmano.sebastian.engine.vsmanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessageType;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.NotifyNsiStatusChange;
import it.nextworks.nfvmano.sebastian.engine.messages.TerminateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
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
	
	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the vertical service instance
	 * @param vsRecordService wrapper of VSI record
	 * @param translatorService translator service
	 */
	public VsLcmManager(String vsiId,
			VsRecordService vsRecordService,
			TranslatorService translatorService) {
		this.vsiId = vsiId;
		this.vsRecordService = vsRecordService;
		this.translatorService = translatorService;
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
		String vsdId = msg.getRequest().getVsdId();
		log.debug("Instanting Vertical Service " + vsiId + " with VSD " + vsdId);
		List<String> vsdIds = new ArrayList<>();
		vsdIds.add(vsdId);
		try {
			List<NfvNsInstantiationInfo> nsInfo = translatorService.translateVsd(vsdIds);
			//TODO:
			
			
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
