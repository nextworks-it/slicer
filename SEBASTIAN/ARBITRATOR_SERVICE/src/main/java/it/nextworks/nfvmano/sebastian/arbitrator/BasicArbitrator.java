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
package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifasol.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.ifasol.catalogues.interfaces.messages.QueryNsdIfaResponse;

import it.nextworks.nfvmano.libs.ifasol.catalogues.interfaces.enums.NsdFormat;

import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.blueprint.elements.ServiceConstraints;
import it.nextworks.nfvmano.catalogue.blueprint.elements.ServicePriorityLevel;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.common.VsAction;
import it.nextworks.nfvmano.sebastian.common.VsActionType;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;

/**
 * This is a basic arbitrator that only checks if the request VSI is compliant with 
 * the SLA of the tenant and does not support network slice sharing.
 * 
 * @author nextworks
 *
 */
public class BasicArbitrator extends AbstractArbitrator {

	private static final Logger log = LoggerFactory.getLogger(BasicArbitrator.class);

	

    public BasicArbitrator(AdminService adminService,
                           VsRecordService vsRecordService,
                           VsDescriptorCatalogueService vsDescriptorCatalogueService,
                           TranslatorService translatorService,
                           NfvoCatalogueService nfvoService,
                           NsTemplateCatalogueService nsTemplateCatalogueService,
                           VirtualResourceCalculatorService vsc,
                           NsmfLcmProviderInterface nsmfLcmProvider) {
        super(adminService,
                vsRecordService,
                vsDescriptorCatalogueService,
                translatorService,
                nfvoService,
                nsTemplateCatalogueService,
                ArbitratorType.BASIC_ARBITRATOR,
                vsc,
                nsmfLcmProvider);
    }

	/**
	 *
	 * @param tenantId
	 * @return impactedVerticalServiceInstances of impacted VSIs and associated actions
	 * @throws NotExistingEntityException
	 */
	private Map<String, VsAction> generateImpactedVsList(String tenantId) throws NotExistingEntityException, FailedOperationException {
		// search VS to be updated/terminated
		List<VerticalServiceInstance> candidateVsiList = vsRecordService.getAllVsInstances(tenantId);
		Map<String, VsAction> impactedVerticalServiceInstances = new HashMap<>();
		for (VerticalServiceInstance vsi : candidateVsiList) {
			// check vsd priority
			VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsi.getVsdId());
			List<ServiceConstraints> serviceConstraints = vsd.getServiceConstraints();
			if (!(vsi.getStatus()==VerticalServiceStatus.INSTANTIATED)) break;
			if(serviceConstraints.isEmpty() || serviceConstraints.get(0).getPriority() == ServicePriorityLevel.LOW){
				//retrive NSInfo
				//NetworkSliceInstance nsi = vsRecordService.getNsInstance(vsi.getNetworkSliceId());
				NetworkSliceInstance nsi = readNetworkSliceInstanceInformation(vsi.getNetworkSliceId(), vsi.getTenantId());
				impactedVerticalServiceInstances.put(vsi.getVsiId(), new VsAction(
						vsi.getVsiId(),
						VsActionType.TERMINATE,
						nsi.getNsInstantiationInfo(true)
				));
			}else if(serviceConstraints.get(0).getPriority() == ServicePriorityLevel.MEDIUM){
				/*NetworkSliceInstance nsi = vsRecordService.getNsInstance(vsi.getNetworkSliceId());
				impactedVerticalServiceInstances.put(vsi.getVsiId(), new VsAction(
					vsi.getVsiId(),
					VsActionType.UPDATE,
					nsi.getNsInstantiationInfo(true)
				));*/
                log.debug("UPDATE VS Action is not supported yet");
            }
        }
        return impactedVerticalServiceInstances;
    }

	@Override
	public List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests)
			throws FailedOperationException, NotExistingEntityException {
		log.debug("Received request at the arbitrator.");
		
		//TODO: At the moment we process only the first request and the first ns init info
		ArbitratorRequest req = requests.get(0);
		String tenantId = req.getTenantId();
		NfvNsInstantiationInfo nsInitInfo = null;
		Map<String, VsAction> impactedVerticalServiceInstances = null;
		Map<String, NfvNsInstantiationInfo> nsInitInfos = req.getInstantiationNsd();
		for (Map.Entry<String, NfvNsInstantiationInfo> e : nsInitInfos.entrySet()) {
			nsInitInfo = e.getValue();
		}

		log.debug("The request is for tenant " + tenantId + " and for NSD " + nsInitInfo.getNfvNsdId() + " with DF " + nsInitInfo.getDeploymentFlavourId() + " and instantiation level " + nsInitInfo.getInstantiationLevelId());

		
		try {

			//Retrieve NSD info
			String nfvNsId = nsInitInfo.getNfvNsdId();
			String nsdVersion = nsInitInfo.getNsdVersion();

			//Nsd nsd = nfvoCatalogueService.queryNsdAssumingOne(nfvNsId, nsdVersion);
			GeneralizedQueryRequest queryRequest = new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildNsdFilter(nfvNsId, nsdVersion), null);
			QueryNsdResponse nsdResponse = nfvoCatalogueService.queryNsd(queryRequest);
			if (nsdResponse.getNsdFormat() == NsdFormat.IFA) {
				Nsd nsd = ((QueryNsdIfaResponse)nsdResponse).getQueryResult().get(0).getNsd();
				List<String> nestedNsdIds = nsd.getNestedNsdId();
				Map<String, Boolean> existingNsiIds = null;
				if (!nestedNsdIds.isEmpty()) {

					//Retrieve <DF, IL> from nsInitInfo
					String instantiationLevelId = nsInitInfo.getInstantiationLevelId();
					String deploymentFlavourID = nsInitInfo.getDeploymentFlavourId();
					//Create NSIid sublist
					existingNsiIds = new HashMap<>();
					for (String nestedNsdId : nestedNsdIds) {
						//Check existing NSI per id, tenant, IL, DF
						List<NetworkSliceInstance> nsis = getUsableSlices(tenantId, nestedNsdId, nsdVersion, deploymentFlavourID, instantiationLevelId);

						for (NetworkSliceInstance nsi : nsis) {
							existingNsiIds.put(nsi.getNsiId(), false);
							log.debug("Existing NSI found found: {}", nsi.getNsiId());
						}
					}
				}

				VirtualResourceUsage requiredRes = virtualResourceCalculatorService.computeVirtualResourceUsage(nsInitInfo);
				log.debug("The total amount of required resources for the service is the following: " + requiredRes.toString());

				log.debug("Reading info about active SLA and used resources for the given tenant.");

				Tenant tenant = adminService.getTenant(tenantId);
				Sla tenantSla = tenant.getActiveSla();
				//TODO: At the moment we are considering only the SLA about global resource usage. MEC versus cloud still to be managed.
				SlaVirtualResourceConstraint sc = tenantSla.getGlobalConstraint();
				VirtualResourceUsage maxRes = sc.getMaxResourceLimit();
				log.debug("The maximum amount of global virtual resources allowed for the tenant is the following: " + maxRes.toString());

				VirtualResourceUsage usedRes = tenant.getAllocatedResources();
				log.debug("The current resource usage for the tenant is the following: " + usedRes.toString());

				boolean acceptableRequest = true;
				if ((requiredRes.getDiskStorage() + usedRes.getDiskStorage()) > maxRes.getDiskStorage())
					acceptableRequest = false;
				if ((requiredRes.getMemoryRAM() + usedRes.getMemoryRAM()) > maxRes.getMemoryRAM())
					acceptableRequest = false;
				if ((requiredRes.getvCPU() + usedRes.getvCPU()) > maxRes.getvCPU()) acceptableRequest = false;

				if (!acceptableRequest) impactedVerticalServiceInstances = generateImpactedVsList(tenantId);

				ArbitratorResponse response = new ArbitratorResponse(requests.get(0).getRequestId(),
						acceptableRequest,                    //acceptableRequest
						true,                                //newSliceRequired,
						null,                                //existingCompositeSlice,
						false,                                //existingCompositeSliceToUpdate,
						existingNsiIds,
						impactedVerticalServiceInstances);
				List<ArbitratorResponse> responses = new ArrayList<>();
				responses.add(response);
				return responses;
			}else throw new FailedOperationException("NSD format not supported");

			} catch(NotExistingEntityException e){
				log.error("Info not found from NFVO or DB: " + e.getMessage());
				throw new NotExistingEntityException("Error retrieving info at the arbitrator: " + e.getMessage());
			} catch(Exception e){
				log.error("Failure at the arbitrator: " + e.getMessage());
				throw new FailedOperationException(e.getMessage());
			}

	}

	@Override
	public List<ArbitratorResponse> arbitrateVsScaling(List<ArbitratorRequest> requests)
		throws FailedOperationException, NotExistingEntityException {
		log.debug("Received VS Scaling request at the arbitrator.");

		//TODO: At the moment we process only the first request and the first ns init info
		ArbitratorRequest req = requests.get(0);
		String tenantId = req.getTenantId();
		String nsiId = null;
		NfvNsInstantiationInfo nsInitInfo = null;
		Map<String, NfvNsInstantiationInfo> nsInitInfos = req.getInstantiationNsd();
		for (Map.Entry<String, NfvNsInstantiationInfo> e : nsInitInfos.entrySet()) {
			nsInitInfo = e.getValue();
			nsiId = e.getKey();
		}
		log.debug("The request is for tenant " + tenantId + " and for NSD " + nsInitInfo.getNfvNsdId() + " with DF " + nsInitInfo.getDeploymentFlavourId() + " and instantiation level " + nsInitInfo.getInstantiationLevelId());
		//Retrieving NSI for the current NS
		try {
			//NetworkSliceInstance nsi = vsRecordService.getNsInstance(nsiId);
			NetworkSliceInstance nsi = readNetworkSliceInstanceInformation(nsiId, tenantId);

			//Compute resource usage for nsi
			VirtualResourceUsage currentNsRes = virtualResourceCalculatorService.computeVirtualResourceUsage(nsi, true);
			//Compute resoruce required for the scaled ns
			VirtualResourceUsage requiredRes = virtualResourceCalculatorService.computeVirtualResourceUsage(nsInitInfo);

			//Retrieving SLA constraints and current resource usage for the tenant?
			Tenant tenant = adminService.getTenant(tenantId);
			Sla tenantSla = tenant.getActiveSla();
			//TODO: At the moment we are considering only the SLA about global resource usage. MEC versus cloud still to be managed.
			SlaVirtualResourceConstraint sc = tenantSla.getGlobalConstraint();
			VirtualResourceUsage maxRes = sc.getMaxResourceLimit();
			log.debug("The maximum amount of global virtual resources allowed for the tenant is the following: " + maxRes.toString());

			VirtualResourceUsage usedRes = tenant.getAllocatedResources();
			log.debug("The current resource usage for the tenant is the following: " + usedRes.toString());

			//Compute resource
			boolean acceptableRequest = true;
			if ((requiredRes.getDiskStorage() + usedRes.getDiskStorage()) - currentNsRes.getDiskStorage() > maxRes.getDiskStorage()) acceptableRequest = false;
			if ((requiredRes.getMemoryRAM() + usedRes.getMemoryRAM()) -currentNsRes.getMemoryRAM() > maxRes.getMemoryRAM()) acceptableRequest = false;
			if ((requiredRes.getvCPU() + usedRes.getvCPU()) - currentNsRes.getvCPU() > maxRes.getvCPU()) acceptableRequest = false;

			ArbitratorResponse response = new ArbitratorResponse(requests.get(0).getRequestId(),
					acceptableRequest,					//acceptableRequest
					false, 								//newSliceRequired,
					null, 								//existingCompositeSlice,
					false, 								//existingCompositeSliceToUpdate,
					null,
					null);
			List<ArbitratorResponse> responses = new ArrayList<>();
			responses.add(response);
			return responses;

		} catch (NotExistingEntityException e) {
			log.error("Info not found from NFVO or DB: " + e.getMessage());
			throw new NotExistingEntityException("Error retrieving info at the arbitrator: " + e.getMessage());

		} catch (Exception e) {
			log.error("Failure at the arbitrator: " + e.getMessage());
			throw new FailedOperationException(e.getMessage());
		}

	}

	
	private NetworkSliceInstance readNetworkSliceInstanceInformation (String nsiId, String tenantId) 
    		throws FailedOperationException, NotExistingEntityException{
    	log.debug("Interacting with NSMF service to get information about network slice with ID " + nsiId);
    	Map<String, String> parameters = new HashMap<String, String>();
    	parameters.put("NSI_ID", nsiId);
    	Filter filter = new Filter(parameters);
    	GeneralizedQueryRequest request = new GeneralizedQueryRequest(filter, new ArrayList<String>());
    	try {
    		List<NetworkSliceInstance> nsis = nsmfLcmProvider.queryNetworkSliceInstance(request, null, tenantId);
    		if (nsis.isEmpty()) {
    			log.error("Network Slice " + nsiId + " not found in NSMF service");
    			throw new NotExistingEntityException("Network Slice " + nsiId + " not found in NSMF service");
    		}
    		return nsis.get(0);
    	} catch (Exception e) {
			log.error("Error while getting network slice instance " + nsiId + ": " + e.getMessage());
			throw new FailedOperationException("Error while getting network slice instance " + nsiId + ": " + e.getMessage());
		}
    }

	private List<NetworkSliceInstance> getUsableSlices(String tenantId, String nestedNsdId, 
			String nsdVersion, String deploymentFlavourID, String instantiationLevelId) {
		//TODO: find a better way to query this. Maybe with ad hoc filter supported on NSMF side.
		List<NetworkSliceInstance> target = new ArrayList<NetworkSliceInstance>();
		log.debug("Interacting with NSMF service to get information about all network slices");
    	GeneralizedQueryRequest request = new GeneralizedQueryRequest(new Filter(new HashMap<String, String>()), 
    			new ArrayList<String>());
    	try {
    		List<NetworkSliceInstance> nsis = nsmfLcmProvider.queryNetworkSliceInstance(request,null,  tenantId);
    		for (NetworkSliceInstance nsi : nsis) {
    			if ((nsi.getNsdId().equals(nestedNsdId)) &&
    					(nsi.getNsdVersion().equals(nsdVersion)) &&
    					(nsi.getDfId().equals(deploymentFlavourID)) &&
    					(nsi.getInstantiationLevelId().contentEquals(instantiationLevelId))) {
    				log.debug("Found usable network slice " + nsi.getNsiId());
    				target.add(nsi);
    			}
    		}
    	} catch (Exception e) {
			log.debug("Error while getting network slice instances. Returning empty array");
		}
    	return target;
	}

}

