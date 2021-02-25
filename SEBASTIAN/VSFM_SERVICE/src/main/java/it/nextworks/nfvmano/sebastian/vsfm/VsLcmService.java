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

import java.util.*;

import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsBlueprintCatalogueService;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.NsdManagementProviderInterface;
//import it.nextworks.nfvmano.nfvodriver.NfvoLcmService;
import it.nextworks.nfvmano.sebastian.common.*;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceSubnetInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.*;
import it.nextworks.nfvmano.sebastian.vsfm.interfaces.VsLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.vsfm.messages.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.VsmfInteractionHandler;
import it.nextworks.nfvmano.sebastian.vsfm.vscoordinator.VsiNsiCoordinator;
import it.nextworks.nfvmano.sebastian.vsfm.vsmanagement.VsLcmManager;
import it.nextworks.nfvmano.sebastian.vsfm.interfaces.VsLcmProviderInterface;
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

import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.NsInfo;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.SapInfo;
import it.nextworks.nfvmano.libs.ifa.records.vnfinfo.VnfExtCpInfo;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsBlueprint;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.messages.QueryVsBlueprintResponse;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceFailureNotification;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.vsfm.vscoordinator.VsCoordinator;

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
public class VsLcmService implements VsLcmProviderInterface, NsmfLcmConsumerInterface, VsLcmConsumerInterface {

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
	
	//@Autowired
	//private NfvoLcmService nfvoLcmService;

	@Autowired
    private VirtualResourceCalculatorService virtualResourceCalculatorService;
	
	@Value("${sebastian.admin}")
	private String adminTenant;
	
	private NsmfLcmProviderInterface nsmfLcmProvider;

	@Autowired
    private VsmfInteractionHandler vsmfInteractionHandler;

	private NsdManagementProviderInterface nsdManagementProvider;
	
	@Value("${spring.rabbitmq.host}")
    private String rabbitHost;

	@Value("${spring.rabbitmq.timout:10}")
	private int rabbitTimeout;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier(ConfigurationParameters.engineQueueExchange)
    TopicExchange messageExchange;
    
    @Autowired
    private VsmfUtils vsmfUtils;

    //internal map of VS LCM Managers
    //each VS LCM Manager is created when a new VSI ID is created and removed when the VSI ID is removed
    private Map<String, VsLcmManager> vsLcmManagers = new HashMap<>();

    //internal map of Vs NSI Coordinators
	//key: "nsiId_domain" or "nsiId", value coordinator
	private Map<String, VsiNsiCoordinator> vsNsiCoordinators = new HashMap<>();

    //internal map of VS Coordinators
    //each VS Coordinator is created on demand, as soon as a VSI asks for resources for being instantiated
    private Map<String, VsCoordinator> vsCoordinators = new HashMap<>();
	
	@Override
	public String instantiateVs(InstantiateVsRequest request, String domain) throws MethodNotImplementedException,
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
		if(domain==null){
		    return internalProcessInstantiateVs(vsd, request);
        }else{
		    log.debug("Triggering VSS instantiation on remote site");

		    InstantiateVsRequest rRequest = new InstantiateVsRequest(request.getName(),
					request.getDescription(),
					vsd.getVsDescriptorId(),
					request.getTenantId(),
					request.getNotificationUrl(),
					request.getUserData(),
					request.getLocationConstraints());
            String remoteId = vsmfInteractionHandler.instantiateVs(rRequest, domain);
		    String vsiId = vsRecordService.createVsInstance(request.getName(), request.getDescription(),
                    vsdId,
                    tenantId,
                    request.getUserData(),
                    request.getLocationConstraints(),
                    null,
                    domain,
                    remoteId);
		    return vsiId;
        }


	}

	private String internalProcessInstantiateVs(VsDescriptor vsd, InstantiateVsRequest request ) throws NotExistingEntityException, FailedOperationException, MalformattedElementException,NotPermittedOperationException {

        String tenantId = request.getTenantId();
        String vsdId = request.getVsdId();


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

        LocationInfo locationConstraints = request.getLocationConstraints();
        String ranEndPointId = null;
        if (locationConstraints != null) {
            ranEndPointId = vsb.getRanEndPoint();
            if (ranEndPointId != null)
                log.debug("Set location constraints and RAN endpoint for VS instance.");
            else log.warn("No RAN endpoint available. Unable to specify the location constraints for the service.");
        }

        log.debug("The VS instantiation request is valid.");

        String vsiId = vsRecordService.createVsInstance(request.getName(), request.getDescription(), vsdId, tenantId, userData, locationConstraints, ranEndPointId, null, null);
        initNewVsLcmManager(vsiId, request.getName());
        if (!(tenantId.equals(adminTenant))) adminService.addVsiInTenant(vsiId, tenantId);
        try {
            String topic = "lifecycle.instantiatevs." + vsiId;
            InstantiateVsiRequestMessage internalMessage = new InstantiateVsiRequestMessage(vsiId, request, ranEndPointId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal VS instantiation message in Json format.");
                vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal VS instantiation message in Json format.");
            }
            log.debug("Synchronous processing for VSI instantiation request completed for VSI ID " + vsiId);
            return vsiId;
        } catch (Exception e) {
            vsRecordService.setVsFailureInfo(vsiId, e.getMessage());
            throw new FailedOperationException(e.getMessage());
        }
    }

    private QueryVsResponse internalQueryVs(GeneralizedQueryRequest request) throws NotPermittedOperationException, MalformattedElementException, MethodNotImplementedException, NotExistingEntityException, FailedOperationException {
		log.debug("Received a query about a Vertical Service instance.");
		request.isValid();
		//At the moment the only filter accepted are:
		//1. VSI ID && TENANT ID
		//No attribute selector is supported at the moment
		Filter filter = request.getFilter();
		List<String> attributeSelector = request.getAttributeSelector();

		if ((attributeSelector == null) || (attributeSelector.isEmpty())) {
			Map<String,String> fp = filter.getParameters();
			if (fp.size()==2 && fp.containsKey("VSI_ID") && fp.containsKey("TENANT_ID")) {
				String vsiId = fp.get("VSI_ID");
				String tenantId = fp.get("TENANT_ID");
				log.debug("Received a query about VS instance with ID " + vsiId + " for tenant ID " + tenantId);
				VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiId);
				if (tenantId.equals(adminTenant) || tenantId.equals(vsi.getTenantId())) {
					List<SapInfo> externalInterconnections = new ArrayList<>();
					Map<String, List<VnfExtCpInfo>> internalInterconnections = new HashMap<>();
					String nsiId = vsi.getNetworkSliceId();
					String monitoringUrl = null;
					if (nsiId != null) {

						NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(nsiId, null, tenantId);
						String nfvNsId = nsi.getNfvNsId();
						if (nfvNsId != null) {
							//TODO: check with Pietro. This should be removed from here and embedded into the
							//network slice instance information handled at the NSMF -
							//e.g. through a new query that explicitely request NFV related info
							//maybe this could be embedded into the attribute field of the query request
							//QueryNsResponse queryNs = nfvoLcmService.queryNs(new GeneralizedQueryRequest(Utilities.buildNfvNsiFilter(nfvNsId), null));

							//TODO: to be done
							/*
							QueryNsResponse queryNs = nsmfLcmProvider.queryNetworkSliceInstance(new GeneralizedQueryRequest(Utilities.buildNfvNsiFilter(nfvNsId), null), null, null);
							NsInfo nsInfo = queryNs.getQueryNsResult().get(0);
							log.debug("Retrieved NS info from NFVO");
							externalInterconnections = nsInfo.getSapInfo();
							monitoringUrl = nsInfo.getMonitoringDashboardUrl();
							if ("".equals(monitoringUrl)) {
								monitoringUrl = null;
							}

							 */
							//TODO: in order to get VNF info we should interact with the VNFM... Still thinking about how to do that.
						} else log.debug("The Network Slice is not associated to any NFV Network Service. No interconnection info available.");
					} else log.debug("The VS is not associated to any Network Slice. No interconnection info available.");
					return new QueryVsResponse(
							vsiId,
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
					throw new NotPermittedOperationException("Tenant " + tenantId + " has not access to VSI with ID " + vsiId);
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
	public QueryVsResponse queryVs(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("queryvs request for domain:"+domainId);
		log.debug(request.toString());
		if(domainId==null){
			return internalQueryVs(request);
		}else{
			return vsmfInteractionHandler.queryVs(request, domainId);
		}
	}
	
	@Override
	public List<String> queryAllVsIds(GeneralizedQueryRequest request, String domainId)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Received request about all the IDs of Vertical Service instances.");
		request.isValid();
		
		Filter filter = request.getFilter();
        List<String> attributeSelector = request.getAttributeSelector();
        if ((attributeSelector == null) || (attributeSelector.isEmpty())) {
        	Map<String, String> fp = filter.getParameters();
            if (fp.size() == 1 && fp.containsKey("TENANT_ID")) {
            	List<String> vsIds = new ArrayList<>();
            	String tenantId = fp.get("TENANT_ID");
            	List<VerticalServiceInstance> vsInstances = new ArrayList<>();
            	if (tenantId.equals(adminTenant)) {
            		log.debug("VSI ID query for admin: returning all the VSIs");
            		vsInstances = vsRecordService.getAllVsInstances();
            	} else {
            		log.debug("VSI ID query for tenant " + tenantId);
            		vsInstances = vsRecordService.getAllVsInstances(tenantId);
            	}
            	for (VerticalServiceInstance vsi : vsInstances) vsIds.add(vsi.getVsiId());
            	return vsIds;
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
	public List<VerticalServiceInstance> queryAllVsInstances(GeneralizedQueryRequest request, String domainId)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Received request about all the IDs of Vertical Service instances.");
		request.isValid();
		Filter filter = request.getFilter();
		List<String> attributeSelector = request.getAttributeSelector();
		if ((attributeSelector == null) || (attributeSelector.isEmpty())) {
			Map<String, String> fp = filter.getParameters();
			if (fp.size() == 1 && fp.containsKey("TENANT_ID")) {
				List<String> vsIds = new ArrayList<>();
				String tenantId = fp.get("TENANT_ID");
				List<VerticalServiceInstance> vsInstances = new ArrayList<>();
				if (tenantId.equals(adminTenant)) {
					log.debug("VSI ID query for admin: returning all the VSIs");
					vsInstances = vsRecordService.getAllVsInstances();
				} else {
					log.debug("VSI ID query for tenant " + tenantId);
					vsInstances = vsRecordService.getAllVsInstances(tenantId);
				}
				return vsInstances;
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
	public void terminateVs(TerminateVsRequest request, String domainId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received request to terminate a Vertical Service instance.");
		request.isValid();
		
		String tenantId = request.getTenantId();
		String vsiId = request.getVsiId();
		
		VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiId);
		if (tenantId.equals(adminTenant) || vsi.getTenantId().equals(tenantId)) {
			log.debug("The termination request is valid.");
			if(domainId==null|| domainId.isEmpty()){
                log.debug("Synchronous processing for VSI termination request completed for VSI ID " + vsiId);
			    terminateVs(vsiId, request);
            }else{
			    vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.TERMINATING);
			    String mappedId = vsRecordService.getVsInstance(vsiId).getMappedInstanceId();
				TerminateVsRequest translatedReq = new TerminateVsRequest(mappedId, tenantId);
			    vsmfInteractionHandler.terminateVs(translatedReq, domainId);
            }
		} else {
			log.debug("Tenant " + tenantId + " is not allowed to terminate VS instance " + vsiId);
			throw new NotPermittedOperationException("Tenant " + tenantId + " is not allowed to terminate VS instance " + vsiId);
		}
	}
	
	@Override
	public void purgeVs(PurgeVsRequest request, String domainId)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received request to purge a terminated Vertical Service instance.");
		request.isValid();
		
		String tenantId = request.getTenantId();
		String vsiId = request.getVsiId();
		
		VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiId);
		if (tenantId.equals(adminTenant) || vsi.getTenantId().equals(tenantId)) {
			//TODO: at the moment the network slices are not purged, since assuming they are handled 
			//through another system
			
			vsRecordService.removeVsInstance(vsiId);
			removeVerticalServiceLcmManager(vsiId);
			log.debug("VSI purge action completed for VSI ID " + vsiId);
		} else {
			log.debug("Tenant " + tenantId + " is not allowed to purge VS instance " + vsiId);
			throw new NotPermittedOperationException("Tenant " + tenantId + " is not allowed to purge VS instance " + vsiId);
		}
	}

	@Override
	public void modifyVs(ModifyVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException,
			FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received request to modify a Vertical Service instance.");
		request.isValid();

		String tenantId = request.getTenantId();
		String vsiId = request.getVsiId();
		String vsdId = request.getVsdId();

		VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
		if ((!(vsd.getTenantId().equals(tenantId))) && (!(tenantId.equals(adminTenant)))) {
			log.debug("Tenant " + tenantId + " is not allowed to modify VS instance with VSD " + vsdId);
			throw new NotPermittedOperationException("Tenant " + tenantId + " is not allowed to modify VS instance with VSD " + vsdId);
		}
		modifyVs(vsiId, request);
		log.debug("Synchronous processing for VSI modification request completed for VSI ID " + vsiId);
	}
	

    /**
     *
     * @param invokerVsiId Is the id of the Vsi invoking the coordination. VsCoordinator uses it as Coordinator ID
     * @param candidateVsis List of VSIs candidate for being terminated or updated
     */
    public void requestVsiCoordination(String invokerVsiId, Map<String, VsAction> candidateVsis){
        log.debug("Processing new VSI coordination request from VSI " + invokerVsiId);
        if (!vsCoordinators.containsKey(invokerVsiId))
            initNewVsCoordinator(invokerVsiId);
        String topic = "coordlifecycle.coordinatevs." + invokerVsiId;
        CoordinateVsiRequest internalMessage = new CoordinateVsiRequest(invokerVsiId, candidateVsis);
        try {
            sendMessageToQueue(internalMessage, topic);
        } catch (JsonProcessingException e) {
            log.error("Error while translating internal VS coordination message in Json format.");
            vsRecordService.setVsFailureInfo(invokerVsiId, "Error while translating internal VS coordination message in Json format.");
        }
    }


    public void requestVsNsiCoordination(String invokerVsiId, VsNssiAction action, String tenantId ) throws FailedOperationException {
    	log.debug("Processing VS NSI coordination request");
    	String topicId = action.getNssiId();
    	if(action.getDomainId()!=null){
    		topicId+="_"+action.getDomainId();
		}



		List<VerticalServiceInstance> vsInstances = vsRecordService.getVsInstancesFromNetworkSliceSubnet(action.getNssiId());
    	if(vsInstances==null || vsInstances.isEmpty())
    		throw new FailedOperationException("Cannot find vertical services associated with the slice:"+action.getNssiId());

    	for(VerticalServiceInstance targetInstance : vsInstances){
    		NotifyVsiNssiCoordinationStart coordinationStart = new NotifyVsiNssiCoordinationStart(action.getNssiId(), action.getDomainId());
    		String vsiTopic = "lifecycle.notifynsicoordination." +targetInstance.getVsiId();
			try {
				sendMessageToQueue(coordinationStart, vsiTopic);
			} catch (JsonProcessingException e) {
				log.error("Error while translating internal VS coordination message in Json format.");
				vsRecordService.setVsFailureInfo(invokerVsiId, "Error while translating internal VS coordination message in Json format.");
			}
		}
		if(!vsNsiCoordinators.containsKey(topicId)){
			log.debug("retrieving nstid from the vertical service instance records");
    		initNewVsNsiCoordinator(action.getNssiId(), action.getDomainId(), vsInstances.get(0).getNssis().get(action.getNssiId()).getNsstId(), tenantId);

		}

		String topic = "coordnsilifecycle.coordinatevsnsi." + topicId;

		CoordinateVsiNssiRequest internalMessage = new CoordinateVsiNssiRequest(invokerVsiId, action);
		try {
			sendMessageToQueue(internalMessage, topic);
		} catch (JsonProcessingException e) {
			log.error("Error while translating internal VS coordination message in Json format.");
			vsRecordService.setVsFailureInfo(invokerVsiId, "Error while translating internal VS coordination message in Json format.");
		}

	}
	
	private void modifyVs(String vsiId, ModifyVsRequest request) throws NotExistingEntityException{
        log.debug("Processing new VSI modification request for VSI ID " + vsiId);
        if (vsLcmManagers.containsKey(vsiId)) {
            String topic = "lifecycle.modifyvs." + vsiId;
            ModifyVsiRequestMessage internalMessage = new ModifyVsiRequestMessage(vsiId, request);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal VS modification message in Json format.");
                vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal VS modification message in Json format.");
            }
        } else {
            log.error("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Unable to modify the VSI.");
            throw new NotExistingEntityException("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Unable to modify the VSI.");
        }
    }
    
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
     * This method is called by the VS Coordinator once it is notified for the termination of the last VSI in the
     * termination candidate list
     *
     * @param vsiId both Coordinator and VSI invoker ID
     * @throws NotExistingEntityException
     */
    public void notifyVsCoordinationEnd(String vsiId) throws NotExistingEntityException {
        log.debug("Processing end of Vs Coordination notification from Vs Coordinator ID " + vsiId);
        if(vsCoordinators.containsKey(vsiId)){
            // 1st step: destroy the coordinator
            vsCoordinators.remove(vsiId);
            // 2nd step: unlock the invoker VSI frozen in waiting_for_resource status
            String topic = "lifecycle.resourcesgranted." + vsiId;
            NotifyResourceGranted internalMessage = new NotifyResourceGranted(vsiId);
            try {
                sendMessageToQueue(internalMessage, topic);
            } catch (JsonProcessingException e) {
                log.error("Error while translating internal RESOURCES GRANTED message in Json format.");
                vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal RESOURCES GRANTED message in Json format.");
            }
        } else {
            log.error("Unable to find VS Coordinator for VSI ID " + vsiId + ". Unable to instantiate the invoker VSI");
            throw new NotExistingEntityException("Unable to find VS Coordinator for VSI ID " + vsiId + ". Unable to instantiate the invoker VSI");
        }
    }

    public void notifyVsNssiCoordinationEnd(String nsiId, String domain, boolean successful){
		log.debug("Processing end of Vs Nsi Coordination notification for slice:" + nsiId+ " domain: "+domain);
		List<VerticalServiceInstance> vsis = vsRecordService.getVsInstancesFromNetworkSliceSubnet(nsiId);
		for (VerticalServiceInstance vsi : vsis) {
			String vsiId = vsi.getVsiId();
			log.debug("Network Slice " + nsiId + " is associated to vertical service " + vsiId);
			if (vsLcmManagers.containsKey(vsiId)) {
				log.debug("Notifying coordination end to affected service:"+vsiId);
				String topic = "lifecycle.notifynscoordinationend." + vsiId;
				NotifyVsiNssiCoordinationEnd internalMessage = new NotifyVsiNssiCoordinationEnd(nsiId, domain, successful);
				try {
					sendMessageToQueue(internalMessage, topic);
				} catch (Exception e) {
					log.error("General exception while sending message to queue.");
				}
			} else {
				log.error("Unable to find Vertical Service LCM Manager for VSI ID " + vsiId + ". Unable to notify associated NS status change.");
			}
		}

		for(Map.Entry<String, VsLcmManager> managerEntry: vsLcmManagers.entrySet()){
			if(managerEntry.getValue().getPendingNsiModificationIds().contains(nsiId)){
				NotifyVsiNssiCoordinationEnd internalMessage = new NotifyVsiNssiCoordinationEnd(nsiId, domain, successful);
				try {
					log.debug("Notifying coordination end to requester:"+managerEntry.getKey());
					String topic = "lifecycle.notifynscoordinationend." + managerEntry.getKey();
					sendMessageToQueue(internalMessage, topic);
				} catch (Exception e) {
					log.error("General exception while sending message to queue.");
				}
			}
		}

	}

    /**
     * This methd is invoked by VsLcmManager as soon as its own termination process succeeded
     *
     * @param vsiId Id of terminated VSI
     * @throws NotExistingEntityException
     */
    public void notifyVsiTermination(String vsiId) throws NotExistingEntityException {
        log.debug("Processing VSI termination request for VSI ID " + vsiId);
        if (vsLcmManagers.containsKey(vsiId)) {
            log.debug("VSI " + vsiId + " TERMINATED");
            for (Map.Entry<String, VsCoordinator> coordEntry : vsCoordinators.entrySet()) {
                VsCoordinator coordinator = coordEntry.getValue();
                if (coordinator.getCandidateVsis().containsKey(vsiId)) {
                    String topic = "coordlifecycle.notifyterm." + coordEntry.getKey();
                    VsiTerminationNotificationMessage internalMessage = new VsiTerminationNotificationMessage(vsiId);
                    try {
                        sendMessageToQueue(internalMessage, topic);
                    } catch (JsonProcessingException e) {
                        log.error("Error while translating internal VSI TERMINATION notification message in Json format.");
                        vsRecordService.setVsFailureInfo(vsiId, "Error while translating internal VSI TERMINATION notification message in Json format.");
                    }
                }
            }

        }else{
                log.error("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Invalid Termination notification.");
                throw new NotExistingEntityException("Unable to find VS LCM Manager for VSI ID " + vsiId + ". Invalid Termination notification.");


        }
    }

    /**
     * This method removes a VS LC manager from the engine map
     *
     * @param vsiId ID of the VS whose VS LCM must be removed
     */
    public void removeVerticalServiceLcmManager(String vsiId) {
        log.debug("Vertical service " + vsiId + " has been terminated. Removing VS LCM from engine");
        this.vsLcmManagers.remove(vsiId);
        log.debug("VS LCM manager removed from engine.");
    }

	@Override
	public void notifyNetworkSliceStatusChange(NetworkSliceStatusChangeNotification notification) {
		String networkSliceId = notification.getNsiId();
		log.debug("Processing notification about status change for network slice " + networkSliceId);
        //TODO: why is the domain not included in the notification?
		if(this.vsNsiCoordinators.containsKey(networkSliceId)){
        	log.debug("notifying VSINSI coordinator");
			NotifyNsiStatusChange internalMessage = new NotifyNsiStatusChange(networkSliceId, notification.getStatusChange());
			String topic = "coordnsilifecycle.nsstatuschange."+networkSliceId;
			try {
				sendMessageToQueue(internalMessage, topic);
			} catch (Exception e) {
				log.error("General exception while sending message to queue.",e);
			}
		}
		List<VerticalServiceInstance> vsis = vsRecordService.getVsInstancesFromNetworkSlice(networkSliceId);
        String vsiId = null;
		for (VerticalServiceInstance vsi : vsis) {
            if(!vsi.getStatus().equals(VerticalServiceStatus.TERMINATED)) {
                if (vsi.getNetworkSliceId() != null && vsi.getNetworkSliceId().equals(networkSliceId)) {
                    vsiId = vsi.getVsiId();
                } else {
                    Map<String, NetworkSliceSubnetInstance> nssis = vsi.getNssis();
                    if (!nssis.isEmpty() && nssis.containsKey(networkSliceId)) {
                        vsiId = vsi.getVsiId();
                    }
                }
                log.debug("Network Slice " + networkSliceId + " is associated to vertical service " + vsiId);
                if (vsLcmManagers.containsKey(vsiId)) {
                    String topic = "lifecycle.notifyns." + vsiId;
                    NotifyNsiStatusChange internalMessage = new NotifyNsiStatusChange(networkSliceId, notification.getStatusChange());
                    try {
                        sendMessageToQueue(internalMessage, topic);
                    } catch (Exception e) {
                        log.error("General exception while sending message to queue.");
                    }
                } else {
                    log.error("Unable to find Vertical Service LCM Manager for VSI ID " + vsiId + ". Unable to notify associated NS status change.");
                }
            }
        }
		
	}
	
	@Override
	public void notifyNetworkSliceFailure(NetworkSliceFailureNotification notification) {
		//TODO:
		log.debug("Received network slice failure messages, but not able to process it at the moment. Skipped.");
	}

	public void setNsmfLcmProvider(NsmfLcmProviderInterface nsmfLcmProvider) {
		this.nsmfLcmProvider = nsmfLcmProvider;
		this.arbitratorService.setNsmfLcmProvider(nsmfLcmProvider);
	}

	public void setNsdManagementProvider(NsdManagementProviderInterface nsdManagementProvider){
    	this.nsdManagementProvider = nsdManagementProvider;
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
     * @param nsiId network slice instance id to be coordinated
	 * @param domain the domain of the network slice
	 * @param nstId the network slice template id
	 *
     */
	private void initNewVsNsiCoordinator(String nsiId, String domain, String nstId, String tenantId ) {
		String coordinatorId= nsiId;
		if(domain!=null)
			coordinatorId+="_"+domain;
		log.debug("Initializing new VS Nsi Coordinator with id " + coordinatorId);
		VsiNsiCoordinator vsNsiCoordinator = new VsiNsiCoordinator(nsiId, nstId, domain, nsmfLcmProvider, this, tenantId);
		createVsNsiCoordinationQueue(coordinatorId, vsNsiCoordinator);
		String topicId = nsiId;
		if(domain!=null)
			topicId+="_"+domain;


		vsNsiCoordinators.put(topicId, vsNsiCoordinator);
		log.debug("VS NSI Coordinator with id " + coordinatorId + " initialized and added to the engine.");
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
     * @param vsiId ID of the VS instance for which the VS LCM Manager must be initialized
     */
    private void initNewVsLcmManager(String vsiId, String vsiName) {
        log.debug("Initializing new VS LCM manager for VSI ID " + vsiId);
        VsLcmManager vsLcmManager = new VsLcmManager(vsiId,
        		vsiName,
        		vsRecordService, 
        		vsDescriptorCatalogueService,
        		vsBlueprintCatalogueService,
        		translatorService, 
        		arbitratorService, 
        		adminService, 
        		this, 
        		virtualResourceCalculatorService, 
        		nsmfLcmProvider,
        		vsmfUtils, this);
        createQueue(vsiId, vsLcmManager);
        vsLcmManagers.put(vsiId, vsLcmManager);
        log.debug("VS LCM manager for VSI ID " + vsiId + " initialized and added to the engine.");
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
        cf.setConnectionTimeout(rabbitTimeout);
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
	 *
	 * @param vsNsiCoordinatorId Id of the VsCoordinator (tentantId as candidate)
	 * @param vsCoordinator VSI coordinator in charge of processing messages
	 */
	private void createVsNsiCoordinationQueue(String vsNsiCoordinatorId, VsiNsiCoordinator vsCoordinator) {
		String queueName = ConfigurationParameters.engineQueueNamePrefix +"coordvsnsi"+ vsNsiCoordinatorId;
		log.debug("Creating new Queue " + queueName + " in rabbit host " + rabbitHost);
		CachingConnectionFactory cf = new CachingConnectionFactory();
		cf.setAddresses(rabbitHost);
		cf.setConnectionTimeout(rabbitTimeout);
		RabbitAdmin rabbitAdmin = new RabbitAdmin(cf);
		Queue queue = new Queue(queueName, false, false, true);
		rabbitAdmin.declareQueue(queue);
		rabbitAdmin.declareExchange(messageExchange);
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(messageExchange).with("coordnsilifecycle.*." + vsNsiCoordinatorId));
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
     * @param vsiId ID of the VSI for which the queue is created
     * @param vsiManager VSI Manager in charge of processing the queue messages
     */
    private void createQueue(String vsiId, VsLcmManager vsiManager) {
        String queueName = ConfigurationParameters.engineQueueNamePrefix + vsiId;
        log.debug("Creating new Queue " + queueName + " in rabbit host " + rabbitHost);
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setAddresses(rabbitHost);
        cf.setConnectionTimeout(rabbitTimeout);
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

	@Override
	public void notifyVerticalServiceStatusChange(VerticalServiceStatusChangeNotification notification, String domain) {

    	log.debug("Received notification fro Vertical Service status change: "+notification.getVsiId());
    	String vssiId = notification.getVsiId();
        VerticalServiceInstance nestedVsi;
    	if(domain==null){
    	    try{
                nestedVsi = vsRecordService.getVsInstance(vssiId);
            }catch (NotExistingEntityException e) {
    	        log.error("Couldnot find Vertical Service instance:"+vssiId);
                return;
            }

        } else {

    	    nestedVsi= vsRecordService.getVsInstanceFromMappedInstanceId(vssiId, domain);
			if(nestedVsi==null){
				log.error("Unable to find nested Vertical Service Instance");
				return;
			}
            try{

				VerticalServiceStatus nestedStatus = VerticalServiceStatus.INSTANTIATED;
				if(notification.getVsStatusChange().equals(VerticalServiceStatusChange.VSI_CREATED)){
					nestedStatus=VerticalServiceStatus.INSTANTIATED;
				}else if(notification.getVsStatusChange().equals(VerticalServiceStatusChange.VSI_TERMINATED)){
					nestedStatus=VerticalServiceStatus.TERMINATED;
				}
				vsRecordService.setVsStatus(nestedVsi.getVsiId(), nestedStatus );

			}catch(Exception e){
                log.error("error updating nested VSI status",e);
			}


		}

    	List<VerticalServiceInstance> vsis = vsRecordService.getVsInstancesFromVerticalSubService(nestedVsi.getVsiId());
		for (VerticalServiceInstance vsi : vsis) {
			String vsiId = vsi.getVsiId();
			log.debug("Vertical (sub)service " + nestedVsi.getVsiId() + " is associated to vertical service " + vsiId);
			if (vsLcmManagers.containsKey(vsiId)) {
				String topic = "lifecycle.notifyvs." + vsiId;
				NotifyVsiStatusChange internalMessage = new NotifyVsiStatusChange(nestedVsi.getVsiId(), notification.getVsStatusChange());
				try {
					sendMessageToQueue(internalMessage, topic);
				} catch (Exception e) {
					log.error("General exception while sending message to queue.");
				}
			}else{

			    log.error("Unable to find Vertical Service LCM Manager for VSI ID " + vsiId +
                        ". Unable to notify associated VS status change.");

			}
		}

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
