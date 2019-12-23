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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.enums.NsScaleType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.*;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.NsInfo;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmService;
import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.nsmf.NsmfUtils;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.InstantiateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.ModifyNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.NotifyNfvNsiStatusChange;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.NsmfEngineMessage;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.NsmfEngineMessageType;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.TerminateNsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.*;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Sapd;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.ScaleNsToLevelData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.SapData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.ScaleNsData;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.record.NsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;

/**
 * Entity in charge of managing the lifecycle
 * of a single network sub-slice instance
 * 
 * @author nextworks
 *
 */
public class NsLcmManager {

	private static final Logger log = LoggerFactory.getLogger(NsLcmManager.class);
	private String networkSliceInstanceId;
	private String name;
	private String description;
	private List<String> nestedNsiIds = new ArrayList<>();			//network slice subnets explicitly handled by the VS
	private List<String> soNestedNsiIds = new ArrayList<>();        //network slice subnets implicitly created by the SO, and so controlled by the SO only
	private String tenantId;
	private NfvoCatalogueService nfvoCatalogueService;
	private NfvoLcmService nfvoLcmService;
	private NsRecordService nsRecordService;
	private String nfvNsiInstanceId;
	private Nsd nsd;
	private String nsdInfoId;
	private NsLcmService nsLcmService;
	private NsmfLcmConsumerInterface notificationDispatcher;
	
	private NetworkSliceStatus internalStatus;

	private String requestedInstantiationLevelId;
	
	private NST networkSliceTemplate;
	
	private NsmfUtils nsmfUtils;
	
	public NsLcmManager(String networkSliceInstanceId,
						String name,
						String description,
						String tenantId,
						NfvoCatalogueService nfvoCatalogueService,
						NfvoLcmService nfvoLcmService,
						NsRecordService nsRecordService,
						NsmfLcmConsumerInterface notificationDispatcher,
						NsLcmService nsLcmService,
						NST networkSliceTemplate, 
						NsmfUtils nsmfUtils) {
		this.networkSliceInstanceId = networkSliceInstanceId;
		this.name = name;
		this.description = description;
		this.tenantId = tenantId;
		this.nfvoCatalogueService = nfvoCatalogueService;
		this.nfvoLcmService = nfvoLcmService;
		this.nsRecordService = nsRecordService;
		this.internalStatus = NetworkSliceStatus.NOT_INSTANTIATED;
		this.notificationDispatcher = notificationDispatcher;
		this.nsLcmService = nsLcmService;
		this.networkSliceTemplate = networkSliceTemplate;
		this.nsmfUtils = nsmfUtils;
	}
	
	
	
	public NetworkSliceStatus getInternalStatus() {
		return internalStatus;
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
			NsmfEngineMessage em = (NsmfEngineMessage) mapper.readValue(message, NsmfEngineMessage.class);
			NsmfEngineMessageType type = em.getType();
			
			switch (type) {
			case INSTANTIATE_NSI_REQUEST: {
				log.debug("Processing NSI instantiation request.");
				InstantiateNsiRequestMessage instantiateVsRequestMsg = (InstantiateNsiRequestMessage)em;
				processInstantiateRequest(instantiateVsRequestMsg);
				break;
			}

			case MODIFY_NSI_REQUEST: {
				log.debug("Processing NSI modification request.");
				ModifyNsiRequestMessage modifyNsiRequestMessage = (ModifyNsiRequestMessage)em;
                processModifyRequest(modifyNsiRequestMessage);
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
		if (internalStatus != NetworkSliceStatus.NOT_INSTANTIATED) {
			manageNsError("Received instantiation request in wrong status. Skipping message.");
			return;
		}
		internalStatus = NetworkSliceStatus.INSTANTIATING;
		String nsdId = networkSliceTemplate.getNsdId();
		String nsdVersion = networkSliceTemplate.getNsdVersion();
		String dfId = msg.getRequest().getDfId();
		String ilId = msg.getRequest().getIlId();
		log.debug("Creating NFV NSI ID for NFV NS with NSD ID " + nsdId);
		
		try {
			log.debug("Updating internal network slice record");
			nsRecordService.setNsiInstantiationInfo(networkSliceInstanceId, dfId, ilId, msg.getRequest().getNsSubnetIds());
			nsRecordService.setNsStatus(networkSliceInstanceId, NetworkSliceStatus.INSTANTIATING);
			
			log.debug("Retrieving NSD");
			NsdInfo nsdInfo = nfvoCatalogueService.queryNsd(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildNsdFilter(nsdId, nsdVersion), null)).getQueryResult().get(0);
			this.nsdInfoId = nsdInfo.getNsdInfoId();
			
			this.nsd = nsdInfo.getNsd();
			
			String nfvNsId = nfvoLcmService.createNsIdentifier(new CreateNsIdentifierRequest(nsdInfoId, "NFV-NS-"+ name, description, tenantId));
			log.debug("Created NFV NS instance ID on NFVO: " + nfvNsId);
			this.nfvNsiInstanceId = nfvNsId;
			nsRecordService.setNfvNsiInNsi(networkSliceInstanceId, nfvNsId);
			
			log.debug("Building NFV NS instantiation request");
			
			String ranEndPointId = null;
			LocationInfo locationInfo = msg.getRequest().getLocationConstraints();
			if (locationInfo.isMeaningful()) {
				ranEndPointId = msg.getRequest().getRanEndPointId();
			}
			
			List<Sapd> saps = nsd.getSapd();
			List<SapData> sapData = new ArrayList<>();
			for (Sapd sap : saps) {
				SapData sData = null;
				if (sap.getCpdId().equals(ranEndPointId)) {
					sData = new SapData(sap.getCpdId(), 								//SAPD ID
							"SAP-" + name + "-" + sap.getCpdId(),						//name 
							"SAP " + sap.getCpdId() + " for Network Slice " + name, 	//description
							null,														//address
							locationInfo);												//locationInfo
					log.debug("Set location constraints for SAP " + sap.getCpdId());
				} else {
					sData = new SapData(sap.getCpdId(), 							//SAPD ID
						"SAP-" + name + "-" + sap.getCpdId(),						//name 
						"SAP " + sap.getCpdId() + " for Network Slice " + name, 	//description
						null,														//address
						null);														//locationInfo
				}
				sapData.add(sData);
			}
			log.debug("Completed SAP Data");
			//TODO: here manage service profile info
			
			//Read NFV_NS_IDs from nsSubnetIds and put in nestedNsInstanceId list
			List<String> nestedNfvNsId = new ArrayList<>();
			for(String nsiId : msg.getRequest().getNsSubnetIds()){
				NetworkSliceInstance nsi = nsRecordService.getNsInstance(nsiId);
				nestedNfvNsId.add(nsi.getNfvNsId());
				this.nestedNsiIds.add(nsi.getNsiId());
			}
			/*
			 * DON'T STORE this.nestedNsiIds on DB:
			 * The nested Nsi are found by the Arbitrator -> they are ALREADY on DB
			 */

			//Read configuration parameters
			Map<String, String> additionalParamForNs = msg.getRequest().getUserData();
			
			String operationId = nfvoLcmService.instantiateNs(new InstantiateNsRequest(nfvNsId,
					dfId, 					//flavourId 
					sapData, 				//sapData
					null,					//pnfInfo
					null,					//vnfInstanceData
					nestedNfvNsId,			//nestedNsInstanceId 
					null,					//locationConstraints 
					additionalParamForNs,	//additionalParamForNs 
					null,					//additionalParamForVnf 
					null,					//startTime
					ilId,					//nsInstantiationLevelId
					null));					//additionalAffinityOrAntiAffinityRule
			
			log.debug("Sent request to NFVO service for instantiating NFV NS " + nfvNsId + ": operation ID " + operationId);
			
		} catch (Exception e) {
			manageNsError(e.getMessage());
		}
	}

	private void processModifyRequest(ModifyNsiRequestMessage msg){
		if (internalStatus != NetworkSliceStatus.INSTANTIATED) {
			manageNsError("Received modification request in wrong status. Skipping message.");
			return;
		}
		else if (!msg.getRequest().getNsiId().equals(networkSliceInstanceId)){
			manageNsError("Received modification request with wrong nsiId " + msg.getRequest().getNsiId());
			return;
		}
		log.debug("Modifying network slice " + networkSliceInstanceId);
		this.internalStatus = NetworkSliceStatus.UNDER_MODIFICATION;
		nsRecordService.setNsStatus(networkSliceInstanceId, NetworkSliceStatus.UNDER_MODIFICATION);
		log.debug("Sending request to modify NFV network service " + nfvNsiInstanceId);
		try {
			ScaleNsToLevelData scaleNsToLevelData = new ScaleNsToLevelData(msg.getRequest().getIlId(), null);
			ScaleNsData scaleNsData = new ScaleNsData(null, 
					null, 
					null, 
					scaleNsToLevelData, 
					null, 
					null, 
					null);
			ScaleNsRequest scaleReq = new ScaleNsRequest(nfvNsiInstanceId, 
					NsScaleType.SCALE_NS, 
					scaleNsData, 
					null, 
					null);
			String operationId = nfvoLcmService.scaleNs(scaleReq);
			log.debug("Sent request to NFVO service for modifying NFV NS " + nfvNsiInstanceId + ": operation ID " + operationId);
			//Save the requested instantiation level id in an auxiliary attribute
			requestedInstantiationLevelId = msg.getRequest().getIlId();
		} catch (Exception e) {
			manageNsError(e.getMessage());
		}

	}

	private void processNfvNsChangeNotification(NotifyNfvNsiStatusChange msg) {
		if (! ((internalStatus == NetworkSliceStatus.INSTANTIATING) || (internalStatus == NetworkSliceStatus.TERMINATING) || (internalStatus == NetworkSliceStatus.UNDER_MODIFICATION))) {
			manageNsError("Received notification about NFV NS status change in wrong status.");
			return;
		}
		if (!(msg.getNfvNsiId().equals(nfvNsiInstanceId))) {
			manageNsError("Received notification about NFV NS not associated to network slice.");
			return;
		}
		try{
			if (msg.isSuccessful()) {
				switch (internalStatus) {
					case INSTANTIATING: {
						log.debug("Successful instantiation of NFV NS " + msg.getNfvNsiId() + " and network slice " + networkSliceInstanceId);
						this.internalStatus=NetworkSliceStatus.INSTANTIATED;

						//If the network slice includes slice subnets, update or create the related entries
						//Note that some subnets can be explicit (i.e. VS-managed, so already available in db) or implicit (i.e. SO-managed, so you need to create a new entry in this phase if not yet present)
						//You need to read the NS info from the NFVO

						QueryNsResponse queryNs = nfvoLcmService.queryNs(new GeneralizedQueryRequest(Utilities.buildNfvNsiFilter(msg.getNfvNsiId()), null));
						NsInfo nsInfo = queryNs.getQueryNsResult().get(0);
						List<String> nfvNsIds = nsInfo.getNestedNsInfoId();
						for (String nfvNsId : nfvNsIds){
							try{
								nsRecordService.getNsInstanceFromNfvNsi(nfvNsId);
							} catch (NotExistingEntityException e){
								String nestedNsiId = nsRecordService.createNetworkSliceInstanceEntry(null,
										null, null, null, null, nfvNsId, null,
										null, null, null, true);
								soNestedNsiIds.add(nestedNsiId);
								nsRecordService.setNsStatus(nestedNsiId, NetworkSliceStatus.INSTANTIATED);
							}
						}
						if (soNestedNsiIds.size() >0 )
							nsRecordService.addNsSubnetsInNetworkSliceInstance(networkSliceInstanceId, soNestedNsiIds);

						nsRecordService.setNsStatus(networkSliceInstanceId, NetworkSliceStatus.INSTANTIATED);
						log.debug("Sending notification to engine.");
						notificationDispatcher.notifyNetworkSliceStatusChange(new NetworkSliceStatusChangeNotification(networkSliceInstanceId, NetworkSliceStatusChange.NSI_CREATED, true));
						break;
					}
					case UNDER_MODIFICATION: {
						log.debug("Successful modification of NFV NS " + msg.getNfvNsiId() + " and network slice " + networkSliceInstanceId);
						this.internalStatus=NetworkSliceStatus.INSTANTIATED;
						//TODO check on requestedInstantiationLevelId
						nsRecordService.updateNsInstantiationLevelAfterScaling(networkSliceInstanceId, requestedInstantiationLevelId);
						nsRecordService.setNsStatus(networkSliceInstanceId, NetworkSliceStatus.INSTANTIATED);
						notificationDispatcher.notifyNetworkSliceStatusChange(new NetworkSliceStatusChangeNotification(networkSliceInstanceId, NetworkSliceStatusChange.NSI_MODIFIED, true));
						break;
					}

					case TERMINATING: {
						log.debug("Successful termination of NFV NS " + msg.getNfvNsiId() + " and network slice " + networkSliceInstanceId);
						//TODO: should we also remove the NS instance ID from the NFVO?
						for (String soManagedNsiId : soNestedNsiIds){
							nsRecordService.setNsStatus(soManagedNsiId, NetworkSliceStatus.TERMINATED);
						}
						this.internalStatus=NetworkSliceStatus.TERMINATED;
						nsRecordService.setNsStatus(networkSliceInstanceId, NetworkSliceStatus.TERMINATED);
						log.debug("Removing NSLCM Manager from engine for network slice " + networkSliceInstanceId);
						nsLcmService.removeNsLcmManager(networkSliceInstanceId);
						log.debug("Sending notification about network slice termination.");
						notificationDispatcher.notifyNetworkSliceStatusChange(new NetworkSliceStatusChangeNotification(networkSliceInstanceId, NetworkSliceStatusChange.NSI_TERMINATED, true));
						break;
					}

					default:
						break;
				}
			} else {
				log.error("The operation associated to NFV network service " + msg.getNfvNsiId() + " has failed.");
				manageNsError("The operation associated to NFV network service " + msg.getNfvNsiId() + " has failed.");
			}
		}catch (Exception e){
			manageNsError(e.getMessage());
		}
	}
	
	private void processTerminateRequest(TerminateNsiRequestMessage msg) {
		if (internalStatus != NetworkSliceStatus.INSTANTIATED) {
			manageNsError("Received termination request in wrong status. Skipping message.");
			return;
		} else if (!msg.getRequest().getNsiId().equals(networkSliceInstanceId)){
            manageNsError("Received termination request with wrong nsiId " + msg.getRequest().getNsiId());
            return;
        }

		log.debug("Terminating network slice " + networkSliceInstanceId);
		this.internalStatus = NetworkSliceStatus.TERMINATING;
		nsRecordService.setNsStatus(networkSliceInstanceId, NetworkSliceStatus.TERMINATING);
		log.debug("Sending request to terminate NFV network service " + nfvNsiInstanceId);
		try {
			String operationId = nfvoLcmService.terminateNs(new TerminateNsRequest(nfvNsiInstanceId, null));
			log.debug("Sent request to NFVO service for terminating NFV NS " + nfvNsiInstanceId + ": operation ID " + operationId);
		} catch (Exception e) {
			manageNsError(e.getMessage());
		}
		
	}
	
	private void manageNsError(String error) {
		nsmfUtils.manageNsError(nfvNsiInstanceId, error);
	}

}
