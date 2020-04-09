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
package it.nextworks.nfvmano.sebastian.vsfm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsBlueprint;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.messages.QueryVsBlueprintResponse;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsBlueprintCatalogueService;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueService;
import it.nextworks.nfvmano.catalogues.template.repo.NsTemplateRepository;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.NsInfo;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.SapInfo;
import it.nextworks.nfvmano.libs.ifa.records.vnfinfo.VnfExtCpInfo;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmService;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.sebastian.common.*;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceFailureNotification;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;
import it.nextworks.nfvmano.sebastian.nstE2eComposer.repository.BucketRepository;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.*;
import it.nextworks.nfvmano.sebastian.vsfm.interfaces.VsLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.vsfm.messages.*;
import it.nextworks.nfvmano.sebastian.vsfm.vscoordinator.VsCoordinator;
import it.nextworks.nfvmano.sebastian.vsfm.vsmanagement.VsLcmManager;
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

import java.util.*;

/**
 * Front-end service for managing the incoming requests 
 * about creation, termination, etc. of Vertical Service instances.
 * 
 * It is invoked by the related REST controller and dispatches requests
 * to the centralized engine. 
 * 
 * @author nextworks
 *
 */
@Service
public class VsLcmService implements VsLcmProviderInterface, NsmfLcmConsumerInterface {

	private static final Logger log = LoggerFactory.getLogger(VsLcmService.class);
	
	@Autowired
	private VsDescriptorCatalogueService vsDescriptorCatalogueService;
	
	@Autowired
	private VsBlueprintCatalogueService vsBlueprintCatalogueService;
	
	@Autowired
	private VsRecordService vsRecordService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
    private TranslatorService translatorService;

    @Autowired
    private ArbitratorService arbitratorService;
	
	@Autowired
	private NfvoLcmService nfvoLcmService;

	@Autowired
    private VirtualResourceCalculatorService virtualResourceCalculatorService;
	
	@Value("${sebastian.admin}")
	private String adminTenant;
	
	private NsmfLcmProviderInterface nsmfLcmProvider;
	
	@Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier(ConfigurationParameters.engineQueueExchange)
    TopicExchange messageExchange;
    
    @Autowired
    private VsmfUtils vsmfUtils;

    @Autowired
	private BucketRepository bucketRepository;

    @Autowired
	private DomainCatalogueService domainCatalogueService;

    //internal map of VS LCM Managers
    //each VS LCM Manager is created when a new VSI UUID is created and removed when the VSI UUID is removed
    private Map<String, VsLcmManager> vsLcmManagers = new HashMap<>();

    //internal map of VS Coordinators
    //each VS Coordinator is created on demand, as soon as a VSI asks for resources for being instantiated
    private Map<String, VsCoordinator> vsCoordinators = new HashMap<>();
	
	@Override
	public String instantiateVs(InstantiateVsRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received request to instantiate a new Vertical Service instance.");
		request.isValid();

		String tenantId = request.getTenantId();
		String vsdId = request.getVsdId();

		VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
		if ((!(vsd.getTenantId().equals(tenantId))) && (!(tenantId.equals(adminTenant)))) {
			log.debug("Tenant " + tenantId + " is not allowed to create VS instance with VSD " + vsdId);
			throw new NotPermittedOperationException("Tenant " + tenantId + " is not allowed to create VS instance with VSD " + vsdId);
		}

		String vsbId = vsd.getVsBlueprintId();
		VsBlueprint vsb = retrieveVsb(vsbId);
		log.debug("Retrieved VSB for the requested VSI.");

		//checking configuration parameters
		Map<String, String> userData = request.getUserData();
		Set<String> providedConfigParameters = userData.keySet();
		if (!(providedConfigParameters.isEmpty())) {
			List<String> acceptableConfigParameters = vsb.getConfigurableParameters();
			for (String cp : providedConfigParameters) {
				if (!(acceptableConfigParameters.contains(cp))) {
					log.error("The request includes a configuration parameter " + cp + " which is not present in the VSB. Not acceptable.");
					throw new MalformattedElementException("The request includes a configuration parameter " + cp + " which is not present in the VSB. Not acceptable.");
				}
			}
			log.debug("Set user configuration parameters for VS instance.");
		}

		List<LocationInfo> locationsConstraints = request.getLocationsConstraints();
		String ranEndPointId = null;
		if (locationsConstraints != null && locationsConstraints.size()>0) {
			ranEndPointId = vsb.getRanEndPoint();
			if (ranEndPointId != null)
				log.debug("Set location constraints and RAN endpoint for VS instance.");
			else log.warn("No RAN endpoint available. Unable to specify the location constraints for the service.");
		}

		log.debug("The VS instantiation request is valid.");
		String vsiUuid = vsRecordService.createVsInstance(request.getName(), request.getDescription(), vsdId, tenantId, userData, locationsConstraints, ranEndPointId);

		RuntimeTranslator runtimeTranslator = new RuntimeTranslator(bucketRepository,vsDescriptorCatalogueService);
		log.info("Filtering Vertical Service Instantiation request against the NSTs buckets.");
		ArrayList<Long> suitableBuckets = runtimeTranslator.translate(vsdId);
		HashMap<String,NetworkSliceInternalInfo> domainIdNetworkSliceInternalInfoMap=new HashMap<String,NetworkSliceInternalInfo>();

		if (locationsConstraints != null) {
			log.info("Filtering NSTs by geographical constraints in the Vertical Service Instance.");
			for (LocationInfo locationConstraints : locationsConstraints) {
				domainIdNetworkSliceInternalInfoMap = runtimeTranslator.filterByLocationConstraints(suitableBuckets, locationConstraints, domainIdNetworkSliceInternalInfoMap);

			}
		}
		log.info("The Geographical filtering results in "+domainIdNetworkSliceInternalInfoMap.size()+" NST(s)");

		initNewVsLcmManager(vsiUuid, request.getName(), domainIdNetworkSliceInternalInfoMap);

		if (!(tenantId.equals(adminTenant))) adminService.addVsiInTenant(vsiUuid, tenantId);
		try {
			String topic = "lifecycle.instantiatevs." + vsiUuid;
            InstantiateVsiRequestMessage internalMessage = new InstantiateVsiRequestMessage(vsiUuid, request, ranEndPointId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal VS instantiation message in Json format.");
                vsRecordService.setVsFailureInfo(vsiUuid, "Error while translating internal VS instantiation message in Json format.");
            }
			log.debug("Synchronous processing for VSI instantiation request completed for VSI UUID " + vsiUuid);
			return vsiUuid;
		} catch (Exception e) {
			vsRecordService.setVsFailureInfo(vsiUuid, e.getMessage());
			throw new FailedOperationException(e.getMessage());
		}
	}

	@Override
	public QueryVsResponse queryVs(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received a query about a Vertical Service instance.");
		request.isValid();
		
		//At the moment the only filter accepted are:
		//1. VSI UUID && TENANT ID
		//No attribute selector is supported at the moment
		
		Filter filter = request.getFilter();
		List<String> attributeSelector = request.getAttributeSelector();
		
		if ((attributeSelector == null) || (attributeSelector.isEmpty())) {
			Map<String,String> fp = filter.getParameters();
			if (fp.size()==2 && fp.containsKey("VSI_ID") && fp.containsKey("TENANT_ID")) {
				String vsiUuid = fp.get("VSI_ID");
				String tenantId = fp.get("TENANT_ID");
				log.debug("Received a query about VS instance with UUID " + vsiUuid + " for tenant ID " + tenantId);
				VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiUuid);

				if (tenantId.equals(adminTenant) || tenantId.equals(vsi.getTenantId())) {
					List<SapInfo> externalInterconnections = new ArrayList<>();
					Map<String, List<VnfExtCpInfo>> internalInterconnections = new HashMap<>();
					String nsiId = vsi.getNetworkSliceId();
					String monitoringUrl = null;
					if (nsiId != null) {
						NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(nsiId, tenantId);
						String nfvNsId = nsi.getNfvNsId();
						if (nfvNsId != null) {
							//TODO: check with Pietro. This should be removed from here and embedded into the 
							//network slice instance information handled at the NSMF -
							//e.g. through a new query that explicitely request NFV related info
							//maybe this could be embedded into the attribute field of the query request
							QueryNsResponse queryNs = nfvoLcmService.queryNs(new GeneralizedQueryRequest(Utilities.buildNfvNsiFilter(nfvNsId), null));
							NsInfo nsInfo = queryNs.getQueryNsResult().get(0);
							log.debug("Retrieved NS info from NFVO");
							externalInterconnections = nsInfo.getSapInfo();
							monitoringUrl = nsInfo.getMonitoringDashboardUrl();
							if ("".equals(monitoringUrl)) {
								monitoringUrl = null;
							}
							//TODO: in order to get VNF info we should interact with the VNFM... Still thinking about how to do that.
						} else log.debug("The Network Slice is not associated to any NFV Network Service. No interconnection info available.");
					} else log.debug("The VS is not associated to any Network Slice. No interconnection info available.");
					return new QueryVsResponse(
							vsiUuid,
							vsi.getName(),
							vsi.getDescription(),
							vsi.getVsdId(),
							vsi.getStatus(),
							externalInterconnections,
							internalInterconnections,
							vsi.getErrorMessage(),
							monitoringUrl
					);
				} else {
					log.error("The tenant has not access to the given VSI");
					throw new NotPermittedOperationException("Tenant " + tenantId + " has not access to VSI with ID " + vsiUuid);
				}
			} else {
				log.error("Received VSI query with not supported filter.");
				throw new MalformattedElementException("Received VSI query with not supported filter.");
			}
		} else {
			log.error("Received VSI query with attribute selector. Not supported at the moment.");
			throw new MethodNotImplementedException("Received VSI query with attribute selector. Not supported at the moment.");
		}
	}
	
	@Override
	public List<String> queryAllVsIds(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Received request about all the IDs of Vertical Service instances.");
		request.isValid();
		
		Filter filter = request.getFilter();
        List<String> attributeSelector = request.getAttributeSelector();
        if ((attributeSelector == null) || (attributeSelector.isEmpty())) {
        	Map<String, String> fp = filter.getParameters();
            if (fp.size() == 1 && fp.containsKey("TENANT_ID")) {
            	List<String> vsUuid = new ArrayList<>();
            	String tenantId = fp.get("TENANT_ID");
            	List<VerticalServiceInstance> vsInstances = new ArrayList<>();
            	if (tenantId.equals(adminTenant)) {
            		log.debug("VSI ID query for admin: returning all the VSIs");
            		vsInstances = vsRecordService.getAllVsInstances();
            	} else {
            		log.debug("VSI ID query for tenant " + tenantId);
            		vsInstances = vsRecordService.getAllVsInstances(tenantId);
            	}
            	for (VerticalServiceInstance vsi : vsInstances) vsUuid.add(vsi.getVsiId());
            	return vsUuid;
            } else {
				log.error("Received all VSI ID query with not supported filter.");
				throw new MalformattedElementException("Received VSI query with not supported filter.");
			}
        } else {
			log.error("Received VSI query with attribute selector. Not supported at the moment.");
			throw new MethodNotImplementedException("Received VSI query with attribute selector. Not supported at the moment.");
		}
	}
		

	@Override
	public void terminateVs(TerminateVsRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received request to terminate a Vertical Service instance.");
		request.isValid();
		
		String tenantId = request.getTenantId();
		String vsiUuid = request.getVsiId();
		
		VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiUuid);
		if (tenantId.equals(adminTenant) || vsi.getTenantId().equals(tenantId)) {
			log.debug("The termination request is valid.");
			terminateVs(vsiUuid, request);
			log.debug("Synchronous processing for VSI termination request completed for VSI UUID " + vsiUuid);
		} else {
			log.debug("Tenant " + tenantId + " is not allowed to terminate VS instance " + vsiUuid);
			throw new NotPermittedOperationException("Tenant " + tenantId + " is not allowed to terminate VS instance " + vsiUuid);
		}
	}
	
	@Override
	public void purgeVs(PurgeVsRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received request to purge a terminated Vertical Service instance.");
		request.isValid();
		
		String tenantId = request.getTenantId();
		String vsiUuid = request.getVsiId();
		
		VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiUuid);
		if (tenantId.equals(adminTenant) || vsi.getTenantId().equals(tenantId)) {
			//TODO: at the moment the network slices are not purged, since assuming they are handled 
			//through another system
			
			vsRecordService.removeVsInstance(vsiUuid);
			removeVerticalServiceLcmManager(vsiUuid);
			log.debug("VSI purge action completed for VSI UUID " + vsiUuid);
		} else {
			log.debug("Tenant " + tenantId + " is not allowed to purge VS instance " + vsiUuid);
			throw new NotPermittedOperationException("Tenant " + tenantId + " is not allowed to purge VS instance " + vsiUuid);
		}
	}

	@Override
	public void modifyVs(ModifyVsRequest request) throws MethodNotImplementedException, NotExistingEntityException,
			FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received request to modify a Vertical Service instance.");
		request.isValid();

		String tenantId = request.getTenantId();
		String vsiUuid = request.getVsiId();
		String vsdId = request.getVsdId();

		VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
		if ((!(vsd.getTenantId().equals(tenantId))) && (!(tenantId.equals(adminTenant)))) {
			log.debug("Tenant " + tenantId + " is not allowed to modify VS instance with VSD " + vsdId);
			throw new NotPermittedOperationException("Tenant " + tenantId + " is not allowed to modify VS instance with VSD " + vsdId);
		}
		modifyVs(vsiUuid, request);
		log.debug("Synchronous processing for VSI modification request completed for VSI UUID " + vsiUuid);
	}
	

	public void actuateNsiE2E(ActuationRequest request, String e2eNsi) throws MalformattedElementException {
		log.debug("Received request to actuate an NSI E2E.");
		request.isValid();
		//TODO check on e2e nsi existence. Skipped for now
		//TODO check if the requesting tenant is the same that has previously instantiated the e2e nsi
		//Suppose vsiId is equal to E2ENsi
		String topic = "lifecycle.actuatevs." + e2eNsi;
		ActauteNsiMessage message = new ActauteNsiMessage(request);
		try {
			sendMessageToQueue(message, topic);
		} catch (JsonProcessingException e) {
			log.error("Error while translating internal VS modification message in Json format.");
			e.printStackTrace();
		}
	}
    /**
     *
     * @param invokerVsiUuid Is the UUID of the Vsi invoking the coordination. VsCoordinator uses it as Coordinator ID
     * @param candidateVsis List of VSIs candidate for being terminated or updated
     */
    public void requestVsiCoordination(String invokerVsiUuid, Map<String, VsAction> candidateVsis){
        log.debug("Processing new VSI coordination request from VSI " + invokerVsiUuid);
        if (!vsCoordinators.containsKey(invokerVsiUuid))
            initNewVsCoordinator(invokerVsiUuid);
        String topic = "coordlifecycle.coordinatevs." + invokerVsiUuid;
        CoordinateVsiRequest internalMessage = new CoordinateVsiRequest(invokerVsiUuid, candidateVsis);
        try {
            sendMessageToQueue(internalMessage, topic);
        } catch (JsonProcessingException e) {
            log.error("Error while translating internal VS coordination message in Json format.");
            vsRecordService.setVsFailureInfo(invokerVsiUuid, "Error while translating internal VS coordination message in Json format.");
        }
    }

    private void modifyVs(String vsiUuid, ModifyVsRequest request) throws NotExistingEntityException{
        log.debug("Processing new VSI modification request for VSI UUID " + vsiUuid);
        if (vsLcmManagers.containsKey(vsiUuid)) {
            String topic = "lifecycle.modifyvs." + vsiUuid;
            ModifyVsiRequestMessage internalMessage = new ModifyVsiRequestMessage(vsiUuid, request);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal VS modification message in Json format.");
                vsRecordService.setVsFailureInfo(vsiUuid, "Error while translating internal VS modification message in Json format.");
            }
        } else {
            log.error("Unable to find VS LCM Manager for VSI UUID " + vsiUuid + ". Unable to modify the VSI.");
            throw new NotExistingEntityException("Unable to find VS LCM Manager for VSI UUID " + vsiUuid + ". Unable to modify the VSI.");
        }
    }
    
    public void terminateVs(String vsiUuid, TerminateVsRequest request) throws NotExistingEntityException {
        log.debug("Processing VSI termination request for VSI UUID " + vsiUuid);
        if (vsLcmManagers.containsKey(vsiUuid)) {
            String topic = "lifecycle.terminatevs." + vsiUuid;
            TerminateVsiRequestMessage internalMessage = new TerminateVsiRequestMessage(vsiUuid);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal VS termination message in Json format.");
                vsRecordService.setVsFailureInfo(vsiUuid, "Error while translating internal VS termination message in Json format.");
            }
        } else {
            log.error("Unable to find VS LCM Manager for VSI UUID " + vsiUuid + ". Unable to terminate the VSI.");
            throw new NotExistingEntityException("Unable to find VS LCM Manager for VSI UUID " + vsiUuid + ". Unable to terminate the VSI.");
        }
    }

    /**
     * This method is called by the VS Coordinator once it is notified for the termination of the last VSI in the
     * termination candidate list
     *
     * @param vsiUuid both Coordinator and VSI invoker UUID
     * @throws NotExistingEntityException
     */
    public void notifyVsCoordinationEnd(String vsiUuid) throws NotExistingEntityException {
        log.debug("Processing end of Vs Coordination notification from Vs Coordinator UUID " + vsiUuid);
        if(vsCoordinators.containsKey(vsiUuid)){
            // 1st step: destroy the coordinator
            vsCoordinators.remove(vsiUuid);
            // 2nd step: unlock the invoker VSI frozen in waiting_for_resource status
            String topic = "lifecycle.resourcesgranted." + vsiUuid;
            NotifyResourceGranted internalMessage = new NotifyResourceGranted(vsiUuid);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal RESOURCES GRANTED message in Json format.");
                vsRecordService.setVsFailureInfo(vsiUuid, "Error while translating internal RESOURCES GRANTED message in Json format.");
            }
        } else {
            log.error("Unable to find VS Coordinator for VSI UUID " + vsiUuid + ". Unable to instantiate the invoker VSI");
            throw new NotExistingEntityException("Unable to find VS Coordinator for VSI UUID " + vsiUuid + ". Unable to instantiate the invoker VSI");
        }
    }

    /**
     * This methd is invoked by VsLcmManager as soon as its own termination process succeeded
     *
     * @param vsiUuid Id of terminated VSI
     * @throws NotExistingEntityException
     */
    public void notifyVsiTermination(String vsiUuid) throws NotExistingEntityException {
        log.debug("Processing VSI termination request for VSI UUID " + vsiUuid);
        if (vsLcmManagers.containsKey(vsiUuid)) {
            log.debug("VSI " + vsiUuid + " TERMINATED");
            for (Map.Entry<String, VsCoordinator> coordEntry: vsCoordinators.entrySet()){
                VsCoordinator coordinator = coordEntry.getValue();
                if(coordinator.getCandidateVsis().containsKey(vsiUuid)){
                    String topic = "coordlifecycle.notifyterm." + coordEntry.getKey();
                    VsiTerminationNotificationMessage internalMessage = new VsiTerminationNotificationMessage(vsiUuid);
                    try {
                        sendMessageToQueue(internalMessage, topic);
                    } catch (JsonProcessingException e) {
                        log.error("Error while translating internal VSI TERMINATION notification message in Json format.");
                        vsRecordService.setVsFailureInfo(vsiUuid, "Error while translating internal VSI TERMINATION notification message in Json format.");
                    }
                }
            }
        } else {
            log.error("Unable to find VS LCM Manager for VSI UUID " + vsiUuid + ". Invalid Termination notification.");
            throw new NotExistingEntityException("Unable to find VS LCM Manager for VSI UUID " + vsiUuid + ". Invalid Termination notification.");
        }
    }

    /**
     * This method removes a VS LC manager from the engine map
     *
     * @param vsiUuid ID of the VS whose VS LCM must be removed
     */
    public void removeVerticalServiceLcmManager(String vsiUuid) {
        log.debug("Vertical service " + vsiUuid + " has been terminated. Removing VS LCM from engine");
        this.vsLcmManagers.remove(vsiUuid);
        log.debug("VS LCM manager removed from engine.");
    }

	@Override
	public void notifyNetworkSliceStatusChange(NetworkSliceStatusChangeNotification notification) {
		String networkSliceUuid = notification.getNsiId();
		log.debug("Processing notification about status change for network slice " + networkSliceUuid);
        List<VerticalServiceInstance> vsis = vsRecordService.getVsInstancesFromNetworkSlice(networkSliceUuid);
        for (VerticalServiceInstance vsi : vsis) {
            String vsiUuid = vsi.getVsiId();
            log.debug("Network Slice " + networkSliceUuid + " is associated to vertical service " + vsiUuid);
            if (vsLcmManagers.containsKey(vsiUuid)) {
                String topic = "lifecycle.notifyns." + vsiUuid;
                NotifyNsiStatusChange internalMessage = new NotifyNsiStatusChange(networkSliceUuid, notification.getStatusChange());
                try {
                    sendMessageToQueue(internalMessage, topic);
                } catch (Exception e) {
                    log.error("General exception while sending message to queue.");
                }
            } else {
                log.error("Unable to find Vertical Service LCM Manager for VSI UUID " + vsiUuid + ". Unable to notify associated NS status change.");
            }
        }
		
	}
	
	@Override
	public void notifyNetworkSliceFailure(NetworkSliceFailureNotification notification) {
		//TODO:
		log.debug("Received network slice failure messages, but not able to process it at the moment. Skipped.");
	}

    @Override
    public void notifyNetworkSliceActuation(NetworkSliceStatusChangeNotification networkSliceStatusChangeNotification, String s) {

    }


    public void setNsmfLcmProvider(NsmfLcmProviderInterface nsmfLcmProvider) {
		this.nsmfLcmProvider = nsmfLcmProvider;
		this.arbitratorService.setNsmfLcmProvider(nsmfLcmProvider);
	}

	private VsBlueprint retrieveVsb(String vsbId) throws NotExistingEntityException, FailedOperationException { 
		log.debug("Retrieving VSB with ID " + vsbId);
		try {
			QueryVsBlueprintResponse response = vsBlueprintCatalogueService.queryVsBlueprint(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVsBlueprintFilter(vsbId), null));
			return response.getVsBlueprintInfo().get(0).getVsBlueprint();
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return null;
		} catch (MethodNotImplementedException e) {
			log.error("Method not implemented.");
			return null;
		}
	}
	
    /**
     * This method initializes a new VS Coordinator that will be in charge of Terminate or Update VSIs
     * @param vsCoordinatorId Id of VS Coordinator instance. It will be the id of the VsLcm invoking it
     */
    private void initNewVsCoordinator(String vsCoordinatorId) {
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
     * @param vsiUuid ID of the VS instance for which the VS LCM Manager must be initialized
     */
    private void initNewVsLcmManager(String vsiUuid, String vsiName, HashMap<String,NetworkSliceInternalInfo> domainIdNetworkSliceInternalInfoMap) {
        log.debug("Initializing new VS LCM manager for VSI UUID " + vsiUuid);
        VsLcmManager vsLcmManager = new VsLcmManager(vsiUuid,
        		vsiName,
        		vsRecordService, 
        		vsDescriptorCatalogueService, 
        		translatorService, 
        		arbitratorService, 
        		adminService, 
        		this, 
        		virtualResourceCalculatorService,
        		domainCatalogueService,
        		nsmfLcmProvider,
        		vsmfUtils,
				domainIdNetworkSliceInternalInfoMap);
        createQueue(vsiUuid, vsLcmManager);
        vsLcmManagers.put(vsiUuid, vsLcmManager);
        log.debug("VS LCM manager for VSI UUID " + vsiUuid + " initialized and added to the engine.");
    }
    
    /**
     * This internal method sends a message to the internal queue, using a specific topic
     *
     * @param msg
     * @param topic
     * @throws JsonProcessingException
     */
    private void sendMessageToQueue(VsmfEngineMessage msg, String topic) throws JsonProcessingException {
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
     * @param vsiUuid ID of the VSI for which the queue is created
     * @param vsiManager VSI Manager in charge of processing the queue messages
     */
    private void createQueue(String vsiUuid, VsLcmManager vsiManager) {
        String queueName = ConfigurationParameters.engineQueueNamePrefix + vsiUuid;
        log.debug("Creating new Queue " + queueName + " in rabbit host " + rabbitHost);
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setAddresses(rabbitHost);
        cf.setConnectionTimeout(5);
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cf);
        Queue queue = new Queue(queueName, false, false, true);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(messageExchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(messageExchange).with("lifecycle.*." + vsiUuid));
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
        MessageListenerAdapter adapter = new MessageListenerAdapter(vsiManager, "receiveMessage");
        container.setMessageListener(adapter);
        container.setQueueNames(queueName);
        container.start();
        log.debug("Queue created");
    }

//    private NetworkSliceInstance readNetworkSliceInstanceInformation (String nsiId, String tenantId) 
//    		throws FailedOperationException, NotExistingEntityException{
//    	log.debug("Interacting with NSMF service to get information about network slice with ID " + nsiId);
//    	Map<String, String> parameters = new HashMap<String, String>();
//    	parameters.put("NSI_ID", nsiId);
//    	Filter filter = new Filter(parameters);
//    	GeneralizedQueryRequest request = new GeneralizedQueryRequest(filter, new ArrayList<String>());
//    	try {
//    		List<NetworkSliceInstance> nsis = nsmfLcmProvider.queryNetworkSliceInstance(request, tenantId);
//    		if (nsis.isEmpty()) {
//    			log.error("Network Slice " + nsiId + " not found in NSMF service");
//    			throw new NotExistingEntityException("Network Slice " + nsiId + " not found in NSMF service");
//    		}
//    		return nsis.get(0);
//    	} catch (Exception e) {
//			log.error("Error while getting network slice instance " + nsiId + ": " + e.getMessage());
//			throw new FailedOperationException("Error while getting network slice instance " + nsiId + ": " + e.getMessage());
//		}
//    }

}
