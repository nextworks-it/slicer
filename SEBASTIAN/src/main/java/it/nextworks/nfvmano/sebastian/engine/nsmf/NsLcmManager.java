/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.sebastian.engine.nsmf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.nextworks.nfvmano.libs.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.descriptors.nsd.Sapd;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.elements.SapData;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.CreateNsIdentifierRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.InstantiateNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.TerminateNsRequest;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.engine.Engine;
import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.EngineMessageType;
import it.nextworks.nfvmano.sebastian.engine.messages.InstantiateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.engine.messages.NotifyNfvNsiStatusChange;
import it.nextworks.nfvmano.sebastian.engine.messages.NsStatusChange;
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
	private String nsdInfoId;
	private Engine engine;
	
	private NetworkSliceStatus internalStatus;
	
	public NsLcmManager(String networkSliceInstanceId,
			String name,
			String description,
			String tenantId,
			NfvoService nfvoService,
			VsRecordService vsRecordService,
			Engine engine) {
		this.networkSliceInstanceId = networkSliceInstanceId;
		this.name = name;
		this.description = description;
		this.tenantId = tenantId;
		this.nfvoService = nfvoService;
		this.vsRecordService = vsRecordService;
		this.internalStatus = NetworkSliceStatus.INSTANTIATING;
		this.engine = engine;
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
			
			case TERMINATE_NSI_REQUEST: {
				log.debug("Processing NSI termination request.");
				TerminateNsiRequestMessage terminateVsRequestMsg = (TerminateNsiRequestMessage)em;
				processTerminateRequest(terminateVsRequestMsg);
				break;
			}
			
			case NOTIFY_NFV_NSI_STATUS_CHANGE: {
				log.debug("Processing NFV NSI status change notification.");
				NotifyNfvNsiStatusChange nfvNotificationMsg = (NotifyNfvNsiStatusChange)em;
				processNfvNsChangeNotification(nfvNotificationMsg);
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
			log.debug("Retrieving NSD");
			NsdInfo nsdInfo = nfvoService.queryNsd(new GeneralizedQueryRequest(Utilities.buildNsdFilter(nsdId, nsdVersion), null)).getQueryResult().get(0);
			this.nsdInfoId = nsdInfo.getNsdInfoId();
			
			this.nsd = nsdInfo.getNsd();
			
			String nfvNsId = nfvoService.createNsIdentifier(new CreateNsIdentifierRequest(nsdInfoId, "NFV-NS-"+ name, description, tenantId));
			log.debug("Created NFV NS instance ID on NFVO: " + nfvNsId);
			this.nfvNsiInstanceId = nfvNsId;
			vsRecordService.setNfvNsiInNsi(networkSliceInstanceId, nfvNsId);
			
			log.debug("Building NFV NS instantiation request");
			
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
	
	private void processNfvNsChangeNotification(NotifyNfvNsiStatusChange msg) {
		if (! ((internalStatus == NetworkSliceStatus.INSTANTIATING) || (internalStatus == NetworkSliceStatus.TERMINATING))) {
			manageNsError("Received notification about NFV NS status change in wrong status.");
			return;
		}
		if (!(msg.getNfvNsiId().equals(nfvNsiInstanceId))) {
			manageNsError("Received notification about NFV NS not associated to network slice.");
			return;
		}
		if (msg.isSuccessful()) {
			switch (internalStatus) {
			case INSTANTIATING: {
				log.debug("Successful instantiation of NFV NS " + msg.getNfvNsiId() + " and network slice " + networkSliceInstanceId);
				this.internalStatus=NetworkSliceStatus.INSTANTIATED;
				vsRecordService.setNsStatus(networkSliceInstanceId, NetworkSliceStatus.INSTANTIATED);
				log.debug("Sending notification to engine.");
				engine.notifyNetworkSliceStatusChange(networkSliceInstanceId, NsStatusChange.NS_CREATED, true);
				return;
			}
			
			case TERMINATING: {
				log.debug("Successful termination of NFV NS " + msg.getNfvNsiId() + " and network slice " + networkSliceInstanceId);
				//TODO: should we also remove the NS instance ID from the NFVO?
				this.internalStatus=NetworkSliceStatus.TERMINATED;
				vsRecordService.setNsStatus(networkSliceInstanceId, NetworkSliceStatus.TERMINATED);
				log.debug("Sending notification to engine.");
				engine.notifyNetworkSliceStatusChange(networkSliceInstanceId, NsStatusChange.NS_TERMINATED, true);
				return;
			}

			default:
				break;
			}
		} else {
			log.error("The operation associated to NFV network service " + msg.getNfvNsiId() + " has failed.");
			manageNsError("The operation associated to NFV network service " + msg.getNfvNsiId() + " has failed.");
		}
	}
	
	private void processTerminateRequest(TerminateNsiRequestMessage msg) {
		if (internalStatus != NetworkSliceStatus.INSTANTIATED) {
			manageNsError("Received termination request in wrong status. Skipping message.");
			return;
		}
		log.debug("Terminating network slice " + networkSliceInstanceId);
		this.internalStatus = NetworkSliceStatus.TERMINATING;
		vsRecordService.setNsStatus(networkSliceInstanceId, NetworkSliceStatus.TERMINATING);
		log.debug("Sending request to terminate NFV network service " + nfvNsiInstanceId);
		try {
			String operationId = nfvoService.terminateNs(new TerminateNsRequest(nfvNsiInstanceId, null));
			log.debug("Sent request to NFVO service for terminating NFV NS " + nfvNsiInstanceId + ": operation ID " + operationId);
		} catch (Exception e) {
			manageNsError(e.getMessage());
		}
		
	}
	
	private void manageNsError(String error) {
		log.error(error);
		vsRecordService.setNsFailureInfo(networkSliceInstanceId, error);
	}

}
