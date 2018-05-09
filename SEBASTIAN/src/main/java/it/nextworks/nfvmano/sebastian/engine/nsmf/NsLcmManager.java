package it.nextworks.nfvmano.sebastian.engine.nsmf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.descriptors.nsd.Sapd;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.elements.SapData;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.CreateNsIdentifierRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.InstantiateNsRequest;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessageType;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.TerminateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;

/**
 * Entity in charge of managing the lifecycle
 * of a single network slice instance
 * 
 * @author nextworks
 *
 */
public class NsLcmManager {

	private static final Logger log = LoggerFactory.getLogger(NsLcmManager.class);
	private String networkSliceInstanceId;
	private String name;
	private String description;
	private String tenantId;
	private NfvoService nfvoService;
	private VsRecordService vsRecordService;
	private String nfvNsiInstanceId;
	private Nsd nsd;
	
	private NetworkSliceStatus internalStatus;
	
	public NsLcmManager(String networkSliceInstanceId,
			String name,
			String description,
			String tenantId,
			NfvoService nfvoService,
			VsRecordService vsRecordService) {
		this.networkSliceInstanceId = networkSliceInstanceId;
		this.name = name;
		this.description = description;
		this.tenantId = tenantId;
		this.nfvoService = nfvoService;
		this.vsRecordService = vsRecordService;
		this.internalStatus = NetworkSliceStatus.INSTANTIATING;
	}
	
	/**
	 * Method used to receive messages about NSI lifecycle from the Rabbit MQ
	 * 
	 * @param message received message
	 */
	public void receiveMessage(String message) {
		log.debug("Received message for NSI " + networkSliceInstanceId + "\n" + message);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			EngineMessage em = (EngineMessage) mapper.readValue(message, EngineMessage.class);
			EngineMessageType type = em.getType();
			
			switch (type) {
			case INSTANTIATE_NSI_REQUEST: {
				log.debug("Processing NSI instantiation request.");
				InstantiateNsiRequestMessage instantiateVsRequestMsg = (InstantiateNsiRequestMessage)em;
				processInstantiateRequest(instantiateVsRequestMsg);
				break;
			}
			
			case TERMINATE_VSI_REQUEST: {
				log.debug("Processing NSI termination request.");
				TerminateNsiRequestMessage terminateVsRequestMsg = (TerminateNsiRequestMessage)em;
				processTerminateRequest(terminateVsRequestMsg);
				break;
			}
			
			default: {
				log.error("Received message with not supported type. Skipping.");
				break;
			}
			}
			
		} catch(JsonParseException e) {
			manageNsError("Error while parsing message: " + e.getMessage());
		} catch(JsonMappingException e) {
			manageNsError("Error in Json mapping: " + e.getMessage());
		} catch(IOException e) {
			manageNsError("IO error when receiving json message: " + e.getMessage());
		}
	}
	
	private void processInstantiateRequest(InstantiateNsiRequestMessage msg) {
		if (internalStatus != NetworkSliceStatus.INSTANTIATING) {
			manageNsError("Received instantiation request in wrong status. Skipping message.");
			return;
		}
		String nsdId = msg.getNfvNsdId();
		String nsdVersion = msg.getNfvNsdVersion();
		String dfId = msg.getDfId();
		String ilId = msg.getIlId();
		log.debug("Creating NSI ID for NFV NS with NSD ID " + nsdId);
		
		try {
			String nfvNsId = nfvoService.createNsIdentifier(new CreateNsIdentifierRequest(nsdId, "NFV-NS-"+ name, description, tenantId));
			log.debug("Created NFV NS instance ID on NFVO: " + nfvNsId);
			this.nfvNsiInstanceId = nfvNsId;
			vsRecordService.setNfvNsiInNsi(networkSliceInstanceId, nfvNsId);
			
			log.debug("Building NFV NS instantiation request");
			
			log.debug("Retrieving NSD");
			this.nsd = nfvoService.queryNsd(new GeneralizedQueryRequest(Utilities.buildNsdFilter(nsdId, nsdVersion), null)).getQueryResult().get(0).getNsd();
			
			List<Sapd> saps = nsd.getSapd();
			List<SapData> sapData = new ArrayList<>();
			for (Sapd sap : saps) {
				SapData sData = new SapData(sap.getCpdId(), 							//SAPD ID
						"SAP-" + name + "-" + sap.getCpdId(),							//name 
						"SAP " + sap.getCpdId() + " for Network Slice " + name, 		//description
						null);															//address
				sapData.add(sData);
			}
			log.debug("Completed SAP Data");
			
			String operationId = nfvoService.instantiateNs(new InstantiateNsRequest(nfvNsId, 
					dfId, 					//flavourId 
					sapData, 				//sapData
					null,					//pnfInfo
					null,					//vnfInstanceData
					null,					//nestedNsInstanceId 
					null,					//locationConstraints 
					null,					//additionalParamForNs 
					null,					//additionalParamForVnf 
					null,					//startTime
					ilId,					//nsInstantiationLevelId
					null));					//additionalAffinityOrAntiAffinityRule
			
			log.debug("Sent request to NFVO service for instantiating NFV NS " + nfvNsId + ": operation ID " + operationId);
			
		} catch (Exception e) {
			manageNsError(e.getMessage());
		}
		
	}
	
	private void processTerminateRequest(TerminateNsiRequestMessage msg) {
		//TODO:
	}
	
	private void manageNsError(String error) {
		log.error(error);
		vsRecordService.setNsFailureInfo(networkSliceInstanceId, error);
	}

}
