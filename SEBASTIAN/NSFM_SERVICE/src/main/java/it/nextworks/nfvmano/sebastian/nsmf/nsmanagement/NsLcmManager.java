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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upc.gco.slcnt.orch.nmro.driver.lcm.NmroLcmDriver;
import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.enums.NsScaleType;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Sapd;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.ScaleNsToLevelData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.NsLcmProviderInterface;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.SapData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.ScaleNsData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.*;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.NsInfo;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.libs.ifa.templates.NstServiceProfile;
import it.nextworks.nfvmano.libs.ifa.templates.SliceType;
import it.nextworks.nfvmano.libs.ifa.templates.plugAndPlay.PpFunction;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmAbstractDriver;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmService;
import it.nextworks.nfvmano.nfvodriver.NsStatusChange;
import it.nextworks.nfvmano.sebastian.common.ActuationRequest;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.nsmf.NsmfUtils;
import it.nextworks.nfvmano.sebastian.nsmf.engine.messages.*;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChange;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.FlexRanService;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.PnPCommunicationService;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.RanQoSTranslator;
import it.nextworks.nfvmano.sebastian.record.NsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

/**
 * Entity in charge of managing the lifecycle
 * of a single network sub-slice instance
 * 
 * @author nextworks
 *
 */
public class NsLcmManager {

	private static final Logger log = LoggerFactory.getLogger(NsLcmManager.class);
	private String networkSliceInstanceUuid;
	private String name;
	private String description;
	private List<String> nestedNsiUuids = new ArrayList<>();			//network slice subnets explicitly handled by the VS
	private List<String> soNestedNsiUuids = new ArrayList<>();        //network slice subnets implicitly created by the SO, and so controlled by the SO only
	private String tenantId;
	private NfvoCatalogueService nfvoCatalogueService;
	private NfvoLcmService nfvoLcmService;
	private NsLcmProviderInterface nsLcmProviderInterface;
	private NsRecordService nsRecordService;
	private String nfvNsiInstanceId;
	private Nsd nsd;
	private String nsdInfoId;
	private NsLcmService nsLcmService;
	private NsmfLcmConsumerInterface notificationDispatcher;
	private UsageResourceUpdate usageResourceUpdate;

	private NetworkSliceStatus internalStatus;

	private String requestedInstantiationLevelId;
	
	private NST networkSliceTemplate;
	
	private NsmfUtils nsmfUtils;

	private FlexRanService flexRanService;
	private PnPCommunicationService pnPCommunicationService;

	private SliceType sliceType;
	private String nsDfId;

	private String instantationLevel;
	private ActuationLcmService actuationLcmService;
	private List<String> operationsId;

	public NsLcmManager(String networkSliceInstanceUuid,
						String name,
						String description,
						String tenantId,
						NfvoCatalogueService nfvoCatalogueService,
						NfvoLcmService nfvoLcmService,
						NsRecordService nsRecordService,
						NsmfLcmConsumerInterface notificationDispatcher,
						NsLcmService nsLcmService,

						NST networkSliceTemplate,
						FlexRanService flexRanService,
						PnPCommunicationService pnPCommunicationService,
						NsmfUtils nsmfUtils,
						UsageResourceUpdate usageResourceUpdate,
						ActuationLcmService actuationLcmService
						) {

		this.networkSliceInstanceUuid = networkSliceInstanceUuid;
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
		this.flexRanService = flexRanService;
		this.pnPCommunicationService = pnPCommunicationService;
		this.nsmfUtils = nsmfUtils;
		this.usageResourceUpdate= usageResourceUpdate;
		this.actuationLcmService = actuationLcmService;
		operationsId=new ArrayList<String>();
		pnPCommunicationService.setTargetUrl(nsmfUtils.getPlugAndPlayHostname());
	}
	
	
	
	public NetworkSliceStatus getInternalStatus() {
		return internalStatus;
	}

	private void instantiationRollback(){
		UUID sliceID = UUID.fromString(this.networkSliceInstanceUuid);
		// Check RAN Service
		Integer ranSliceId = this.flexRanService.getRanId(sliceID);

		/* RAN and RAN Adapter Rollback */
		//NOTE: Evaluate if try/catch should ber put here.
		if (ranSliceId != null) {
			this.flexRanService.terminateRanSlice(sliceID);
			//NOTE here we're assuming that both FlexRAN and RANAdapter are configured
			this.flexRanService.removeRemoteMapping(sliceID);
		}

		/* PnP Rollback */
		pnPCommunicationService.terminateSliceComponents(sliceID);
	}

	/**
	 * Method used to receive messages about NSI lifecycle from the Rabbit MQ
	 * 
	 * @param message received message
	 */
	public void receiveMessage(String message) {
		log.debug("Received message for NSI " + networkSliceInstanceUuid + "\n" + message);
		
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
				case ACTUATE_NSI_REQUEST: {
					log.debug("Processing NSI actuation request.");
					ActuateNsiRequestMessage actuateNsiRequestMessage = (ActuateNsiRequestMessage)em;
					processActuateRequest(actuateNsiRequestMessage);
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
		log.info("KPI:"+ Instant.now().toEpochMilli()+", Start processing instantiate Network Slice creation request.");
		if (internalStatus != NetworkSliceStatus.NOT_INSTANTIATED) {
			manageNsError("Received instantiation request in wrong status. Skipping message.");
			return;
		}
		internalStatus = NetworkSliceStatus.INSTANTIATING;

		Map<String, String> nsdToInstantiate = null;

		/* NST Analysis */
		//SliceType sliceType = networkSliceTemplate.getSliceType();
		// Step 1: Check NSSTs list
		List<String> nsstList = networkSliceTemplate.getNsstIds();
		for(String nsst_id: nsstList){
			// TODO nsst management here
		}

		try {
			//TODO get nsst RAN if any
			// Step 1: check for RAN

			NstServiceProfile nstServiceProfile = networkSliceTemplate.getNstServiceProfile();
			if(nstServiceProfile != null) {
				this.sliceType = nstServiceProfile.getsST();
				RanQoSTranslator ranQoSTranslator = new RanQoSTranslator();
				List<JSONObject> qosConstraints = ranQoSTranslator.ranProfileToQoSConstraints(networkSliceTemplate);
//				switch (sliceType) {
//					case URLLC:
//						//TODO: translate stype info of the NST in Flexran attributes
//						break;
//
//					case EMBB:
//						//TODO: translate stype info of the NST in Flexran attributes
//						break;
//
//					default:
//						//TODO invoke rollback!
//						break;
//				}
				// RAN-1 -> Slice creation on flexran
				this.flexRanService.createRanSlice(UUID.fromString(networkSliceInstanceUuid));
				// RAN-2 -> Map Slice UUID to FlexranID into the RANAdapter (it's crazy, I know)
				this.flexRanService.mapIdsRemotely(UUID.fromString(networkSliceInstanceUuid));
				// RAN-3 -> Apply QoS Constraints
				this.flexRanService.applyInitialQosConstraints(UUID.fromString(networkSliceInstanceUuid),qosConstraints);
			}

			//TODO P&P should be always available
			// Step 2: check for PnP functions
			List<PpFunction> ppFunctions = networkSliceTemplate.getPpFunctionList();
			if (ppFunctions != null) {
				// Do PnP stuff
				this.pnPCommunicationService.deploySliceComponents(UUID.fromString(networkSliceInstanceUuid), this.networkSliceTemplate);

			}

			//TODO get all nsst
			// Step 3: proceed in instantiating nsds, if any

			if (networkSliceTemplate.getNsdId() != null) {
				String nsdId = networkSliceTemplate.getNsdId();
				String nsdVersion = networkSliceTemplate.getNsdVersion();
				//Assumption: supposing having only one NsDfId and ILid for NsLcmManager
				String dfId = this.getNsDfId();
				String ilId = this.getInstantationLevel();
				log.debug("Creating NFV NSI ID for NFV NS with NSD ID " + nsdId);
				log.debug("Updating internal network slice record");
				nsRecordService.setNsiInstantiationInfo(networkSliceInstanceUuid, null, null, msg.getRequest().getNsSubnetIds());
				nsRecordService.setNsStatus(networkSliceInstanceUuid, NetworkSliceStatus.INSTANTIATING);

				log.debug("Retrieving NSD");
				NsdInfo nsdInfo;
				if(!nsmfUtils.isNmroNfvoCatalogueType()) {
					nsdInfo = nfvoCatalogueService.queryNsd(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildNsdFilter(nsdId, nsdVersion), null)).getQueryResult().get(0);
				}else {
					Map<String, String> filterParams = new HashMap();
					filterParams.put("NSD_ID", nsdId);
					nsdInfo = nfvoCatalogueService.queryNsd(new GeneralizedQueryRequest(new Filter(filterParams), null)).getQueryResult().get(0);
				}
				log.debug("NSD correctly retrieved");
				this.nsdInfoId = nsdInfo.getNsdInfoId();
				this.nsd = nsdInfo.getNsd();

				String tenantIdOsm="";
				if(nsmfUtils.isSsoNmroIntegrationScenario()) {

					tenantIdOsm=nsmfUtils.getNfvoCatalogueUsername();//TODO the mapping between the tenant on NSP and tenant on NFVO is missing. Get from config variable
					this.nsdInfoId = nsdInfo.getNsdId();// The NSD cannot be on boarded specifying its own ID, so the custom one is get from NSD
					log.info("Set the NFVO Catalogue username into request: "+tenantIdOsm);
				}
				log.info("KPI:"+ Instant.now().toEpochMilli()+", Sending request to create Ns Identifier.");
				String nfvNsId = nfvoLcmService.createNsIdentifier(new CreateNsIdentifierRequest(nsdInfoId, "NFV-NS-" + name, description, tenantIdOsm));
				log.info("KPI:"+ Instant.now().toEpochMilli()+", Received response about creation of Ns Identifier.");
				log.debug("Created NFV NS instance ID on NFVO: " + nfvNsId);
				this.nfvNsiInstanceId = nfvNsId;
				nsRecordService.setNfvNsiInNsi(networkSliceInstanceUuid, nfvNsId);

				log.debug("Building NFV NS instantiation request");

				String ranEndPointId = null;
				LocationInfo locationInfo = msg.getRequest().getLocationConstraints();

				if (locationInfo.isMeaningful()) {
					ranEndPointId = msg.getRequest().getRanEndPointId();
				}


				List<SapData> sapData = new ArrayList<>();
				if(nsd!=null) {//This case is NOT triggered when the nfvo catalogue type is NMRO: the related driver does not take the NSD but the NsdInfo only.
					List<Sapd> saps = nsd.getSapd();
					for (Sapd sap : saps) {
						SapData sData = null;
						log.info("sap.getCpdId() is null {}", sap.getCpdId() == null);
						if (sap.getCpdId().equals(ranEndPointId)) {
							sData = new SapData(sap.getCpdId(),                                //SAPD ID
									"SAP-" + name + "-" + sap.getCpdId(),                        //name
									"SAP " + sap.getCpdId() + " for Network Slice " + name,    //description
									null,                                                        //address
									locationInfo);                                                //locationInfo
							log.debug("Set location constraints for SAP " + sap.getCpdId());
						} else {
							sData = new SapData(sap.getCpdId(),                            //SAPD ID
									"SAP-" + name + "-" + sap.getCpdId(),                        //name
									"SAP " + sap.getCpdId() + " for Network Slice " + name,    //description
									null,                                                        //address
									null);                                                        //locationInfo
						}
						sapData.add(sData);
					}
				}
				log.debug("Completed SAP Data");
				//TODO: here manage service profile info

				//Read NFV_NS_IDs from nsSubnetIds and put in nestedNsInstanceId list
				//TODO: candidate for removal
				List<String> nestedNfvNsId = new ArrayList<>();
				for(String nsiUuid : msg.getRequest().getNsSubnetIds()){
					NetworkSliceInstance nsi = nsRecordService.getNsInstance(nsiUuid);
					nestedNfvNsId.add(nsi.getNfvNsId());
					this.nestedNsiUuids.add(nsi.getNsiId());
				}
				/*
				 * DON'T STORE this.nestedNsiIds on DB:
				 * The nested Nsi are found by the Arbitrator -> they are ALREADY on DB
				 */

				//Read configuration parameters
				Map<String, String> additionalParamForNs = msg.getRequest().getUserData();
				log.info("Nfv Ns id is: "+nfvNsId);
				log.info("KPI:"+ Instant.now().toEpochMilli()+", Sending request to instanciate network slice.");
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
				operationsId.add(operationId);
				log.debug("Sent request to NFVO service for instantiating NFV NS " + nfvNsId + ": operation ID " + operationId);
			}


		} catch (Exception e) {
			//instantiationRollback();
			log.info(e.getMessage());
			manageNsError(e.getMessage());
		}
	}

	private void processModifyRequest(ModifyNsiRequestMessage msg){

		if (internalStatus != NetworkSliceStatus.INSTANTIATED) {
			manageNsError("Received modification request in wrong status. Skipping message.");
			return;
		}

		else if (!msg.getRequest().getNsiId().equals(networkSliceInstanceUuid)){
			manageNsError("Received modification request with wrong nsiUuid " + msg.getRequest().getNsiId());
			return;
		}

		log.debug("Modifying network slice " + networkSliceInstanceUuid);

		this.internalStatus = NetworkSliceStatus.UNDER_MODIFICATION;
		nsRecordService.setNsStatus(networkSliceInstanceUuid, NetworkSliceStatus.UNDER_MODIFICATION);
		log.debug("Sending request to modify NFV network service " + nfvNsiInstanceId);
		try {
			String newInstantiationLevelId="newInstantiationLevelId";//TODO to be get in some way
			ScaleNsToLevelData scaleNsToLevelData = new ScaleNsToLevelData(newInstantiationLevelId, null);
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
			requestedInstantiationLevelId = newInstantiationLevelId;
		} catch (Exception e) {
			manageNsError(e.getMessage());
		}

	}

	//If the lcm driver type is NMRO, then the instantiation requires two checks:
    //the first one about the operational status
    //a second one (below implemented) about the config status
    //It has been done here and not in the nfvo polling manager because it would imply to modify the above interface and this could cause problems.

	private boolean pollConfigStatusNetworkServices() throws InterruptedException, FailedOperationException, MalformattedElementException, NotExistingEntityException, MethodNotImplementedException {
		if(nfvoLcmService.getNfvoLcmDriver() instanceof NmroLcmDriver) {
			int pollingTime = nsmfUtils.getNfvoLcmPolling();
			log.info("Polling in "+pollingTime+" seconds the config status of Network Service(s).");
			int configStatusSuccessfulCount = 0;
			while(configStatusSuccessfulCount<operationsId.size()) {
				Thread.sleep(nsmfUtils.getNfvoLcmPolling() * 1000);
				for (String operationId : operationsId) {
					NmroLcmDriver nmroLcmDriver = (NmroLcmDriver) nfvoLcmService.getNfvoLcmDriver();
					if (nmroLcmDriver.getConfigStatus(operationId) == OperationStatus.SUCCESSFULLY_DONE) {
						log.info("OperationId "+operationId +" is in successful status.");
						configStatusSuccessfulCount++;
					}
					if (nmroLcmDriver.getConfigStatus(operationId) == OperationStatus.FAILED) {
						log.error("Configuration status in failed status of operation ID "+operationId);
						return false;
					}
					log.info("Current number of config status in successful status: "+configStatusSuccessfulCount);
				}

			}
			operationsId.clear();
			return true;
		}
		else{
			return true;
		}
	}

	private void processNfvNsChangeNotification(NotifyNfvNsiStatusChange msg) {
		if (! ((internalStatus == NetworkSliceStatus.INSTANTIATING) || (internalStatus == NetworkSliceStatus.TERMINATING) || (internalStatus == NetworkSliceStatus.UNDER_MODIFICATION))) {
			log.info("Internal status is "+internalStatus);
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

						if(pollConfigStatusNetworkServices()==false){
							NotifyNfvNsiStatusChange newMsg = new NotifyNfvNsiStatusChange(
									msg.getNfvNsiId(),
									NsStatusChange.NS_FAILED,
									false);
							processNfvNsChangeNotification(newMsg);
						}

						log.info("KPI:"+ Instant.now().toEpochMilli()+", Successful instantiation of NFV NS and network slice.");
						log.debug("Successful instantiation of NFV NS " + msg.getNfvNsiId() + " and network slice " + networkSliceInstanceUuid);
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
								soNestedNsiUuids.add(nestedNsiId);
								nsRecordService.setNsStatus(nestedNsiId, NetworkSliceStatus.INSTANTIATED);
							}
						}
						if (soNestedNsiUuids.size() >0 )
							nsRecordService.addNsSubnetsInNetworkSliceInstance(networkSliceInstanceUuid, soNestedNsiUuids);

						nsRecordService.setNsStatus(networkSliceInstanceUuid, NetworkSliceStatus.INSTANTIATED);
						log.debug("Sending notification to engine.");
						usageResourceUpdate.addResourceUpdate(networkSliceInstanceUuid,tenantId);
						notificationDispatcher.notifyNetworkSliceStatusChange(new NetworkSliceStatusChangeNotification(networkSliceInstanceUuid, NetworkSliceStatusChange.NSI_CREATED, true,tenantId));
						break;
					}
					case UNDER_MODIFICATION: {
						log.debug("Successful modification of NFV NS " + msg.getNfvNsiId() + " and network slice " + networkSliceInstanceUuid);
						this.internalStatus=NetworkSliceStatus.INSTANTIATED;
						//TODO check on requestedInstantiationLevelId
						nsRecordService.updateNsInstantiationLevelAfterScaling(networkSliceInstanceUuid, requestedInstantiationLevelId);
						nsRecordService.setNsStatus(networkSliceInstanceUuid, NetworkSliceStatus.INSTANTIATED);
						usageResourceUpdate.modifyResourceUsageUpdate(networkSliceInstanceUuid, tenantId);
						notificationDispatcher.notifyNetworkSliceStatusChange(new NetworkSliceStatusChangeNotification(networkSliceInstanceUuid, NetworkSliceStatusChange.NSI_MODIFIED, true,tenantId));
						break;
					}

					case TERMINATING: {
						log.info("KPI:"+Instant.now().toEpochMilli()+", Successful termination of NFV NS.");
						log.debug("Successful termination of NFV NS " + msg.getNfvNsiId() + " and network slice " + networkSliceInstanceUuid);
						//TODO: should we also remove the NS instance ID from the NFVO?
						for (String soManagedNsiUuid : soNestedNsiUuids){
							nsRecordService.setNsStatus(soManagedNsiUuid, NetworkSliceStatus.TERMINATED);
						}
						this.internalStatus=NetworkSliceStatus.TERMINATED;
						nsRecordService.setNsStatus(networkSliceInstanceUuid, NetworkSliceStatus.TERMINATED);
						log.debug("Removing NSLCM Manager from engine for network slice " + networkSliceInstanceUuid);
						nsLcmService.removeNsLcmManager(networkSliceInstanceUuid);
						log.debug("Sending notification about network slice termination.");

						usageResourceUpdate.removeResourceUpdate(networkSliceInstanceUuid, tenantId);
						notificationDispatcher.notifyNetworkSliceStatusChange(new NetworkSliceStatusChangeNotification(networkSliceInstanceUuid, NetworkSliceStatusChange.NSI_TERMINATED, true,tenantId));

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

	private void processActuateRequest(ActuateNsiRequestMessage msg){
		if (internalStatus != NetworkSliceStatus.INSTANTIATED) {
			manageNsError("Received actuation request in wrong status. Skipping message.");
			return;
		}
		ActuationRequest request = msg.getRequest();
		boolean successful =actuationLcmService.processActuation(request);
		NetworkSliceStatusChangeNotification networkSliceStatusChangeNotification =
				new NetworkSliceStatusChangeNotification(request.getNsiId(),
					NetworkSliceStatusChange.NSI_ACTUATED, successful);
		networkSliceStatusChangeNotification.setTenantId(msg.getTenantId());
		notificationDispatcher.notifyNetworkSliceActuation(networkSliceStatusChangeNotification, request.getNotificationEndpoint());
	}

	private void processTerminateRequest(TerminateNsiRequestMessage msg) {
		if (internalStatus != NetworkSliceStatus.INSTANTIATED) {
			manageNsError("Received termination request in wrong status. Skipping message.");
			return;
		} else if (!msg.getRequest().getNsiId().equals(networkSliceInstanceUuid)){
            manageNsError("Received termination request with wrong nsiUuid " + msg.getRequest().getNsiId());
            return;
        }

		log.debug("Terminating network slice " + networkSliceInstanceUuid);
		log.info("KPI:"+Instant.now().toEpochMilli()+", Terminating network slice.");
		if (pnPCommunicationService.isPnP(UUID.fromString(networkSliceInstanceUuid))) {
			log.debug("Terminating P&P Functions for " + networkSliceInstanceUuid);
			pnPCommunicationService.terminateSliceComponents(UUID.fromString(networkSliceInstanceUuid));
		}
		if (flexRanService.isRan(UUID.fromString(networkSliceInstanceUuid))){
			log.debug("Terminating RAN Slice for  " + networkSliceInstanceUuid);
			flexRanService.terminateRanSlice(UUID.fromString(networkSliceInstanceUuid));
		}

		this.internalStatus = NetworkSliceStatus.TERMINATING;
		nsRecordService.setNsStatus(networkSliceInstanceUuid, NetworkSliceStatus.TERMINATING);
		log.debug("Sending request to terminate NFV network service " + nfvNsiInstanceId);
		try {
			String operationId = nfvoLcmService.terminateNs(new TerminateNsRequest(nfvNsiInstanceId, new Date(System.currentTimeMillis())));
			log.debug("Sent request to NFVO service for terminating NFV NS " + nfvNsiInstanceId + ": operation ID " + operationId);
		} catch (Exception e) {
			manageNsError(e.getMessage());
		}
		
	}

	public String getNsDfId() {
		return nsDfId;
	}

	public void setNsDfId(String nsDfId) {
		this.nsDfId = nsDfId;
	}

	public String getInstantationLevel() {
		return instantationLevel;
	}

	public void setInstantationLevel(String instantationLevel) {
		this.instantationLevel = instantationLevel;
	}

	private void manageNsError(String error) {
		nsmfUtils.manageNsError(nfvNsiInstanceId, error);
	}

}
