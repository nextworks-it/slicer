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

import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
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
import it.nextworks.nfvmano.sebastian.engine.messages.NsStatusChange;
import it.nextworks.nfvmano.sebastian.engine.messages.TerminateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
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
	private AdminService adminService;
	private NfvoService nfvoService;
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
	 * @param adminService admin service
	 * @param nfvoService NFVO service
	 * @param engine engine
	 */
	public VsLcmManager(String vsiId,
			VsRecordService vsRecordService,
			VsDescriptorRepository vsDescriptorRepository,
			TranslatorService translatorService,
			ArbitratorService arbitratorService,
			AdminService adminService,
			NfvoService nfvoService,
			Engine engine) {
		this.vsiId = vsiId;
		this.vsRecordService = vsRecordService;
		this.vsDescriptorRepository = vsDescriptorRepository;
		this.translatorService = translatorService;
		this.arbitratorService = arbitratorService;
		this.adminService = adminService;
		this.nfvoService = nfvoService;
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
		if (internalStatus != VerticalServiceStatus.INSTANTIATED) {
			manageVsError("Received termination request in wrong status. Skipping message.");
			return;
		}
		//TODO: check if the network slices composing the VS are shared. At the moment slice sharing not supported.
		log.debug("Terminating Vertical Service " + vsiId);
		log.debug("Network slice " + networkSliceId + " must be terminated.");
		this.internalStatus = VerticalServiceStatus.TERMINATING;
		try {
			vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.TERMINATING);
			engine.terminateNs(networkSliceId);
		} catch (Exception e) {
			manageVsError("Error while terminating VS " + vsiId + ": " + e.getMessage());
		}
	}
	
	private void processNsiStatusChangeNotification(NotifyNsiStatusChange msg) {
		if (! ((internalStatus == VerticalServiceStatus.INSTANTIATING) || (internalStatus == VerticalServiceStatus.TERMINATING))) {
			manageVsError("Received NSI status change notification in wrong status. Skipping message.");
			return;
		}
		NsStatusChange nsStatusChange = msg.getStatusChange();
		try {
			switch (nsStatusChange) {
			case NS_CREATED: {
				if (internalStatus == VerticalServiceStatus.INSTANTIATING) {
					log.debug("The network slice " + msg.getNsiId() + " associated to vertical service " + vsiId + " has been successfully created. Vertical service established.");
					this.internalStatus = VerticalServiceStatus.INSTANTIATED;
					vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.INSTANTIATED);
					NetworkSliceInstance nsi = vsRecordService.getNsInstance(networkSliceId);
					VirtualResourceUsage resourceUsage = nfvoService.computeVirtualResourceUsage(new NfvNsInstantiationInfo(nsi.getNsdId(), 
							nsi.getNsdVersion(), 
							nsi.getDfId(),
							nsi.getInstantiationLevelId()));
					adminService.addUsedResourcesInTenant(tenantId, resourceUsage.getDiskStorage(), resourceUsage.getvCPU(), resourceUsage.getMemoryRAM());
					log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure completed.");
				} else {
					manageVsError("Received notification about NSI creation in wrong status.");
				}
				break;
			}

			case NS_TERMINATED: {
				if (internalStatus == VerticalServiceStatus.TERMINATING) {
					log.debug("The network slice " + msg.getNsiId() + " associated to vertical service " + vsiId + " has been successfully terminated. Vertical service terminated.");
					this.internalStatus = VerticalServiceStatus.TERMINATED;
					vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.TERMINATED);
					NetworkSliceInstance nsi = vsRecordService.getNsInstance(networkSliceId);
					VirtualResourceUsage resourceUsage = nfvoService.computeVirtualResourceUsage(new NfvNsInstantiationInfo(nsi.getNsdId(), 
							nsi.getNsdVersion(), 
							nsi.getDfId(),
							nsi.getInstantiationLevelId()));
					adminService.removeUsedResourcesInTenant(tenantId, resourceUsage.getDiskStorage(), resourceUsage.getvCPU(), resourceUsage.getMemoryRAM());
					log.debug("Updated resource usage for tenant " + tenantId + ". Termination procedure completed.");
				} else {
					manageVsError("Received notification about NSI termination in wrong status.");
				}
				break;
			}

			case NS_FAILED: {
				manageVsError("Received notification about network slice " + msg.getNsiId() + " failure");
				break;
			}

			default: {
				break;
			}
			}
		} catch (Exception e) {
			manageVsError("Error while processing NSI status change notification: " + e.getMessage());
		}
	}
	
	private void manageVsError(String errorMessage) {
		log.error(errorMessage);
		vsRecordService.setVsFailureInfo(vsiId, errorMessage);
	}
	
}
