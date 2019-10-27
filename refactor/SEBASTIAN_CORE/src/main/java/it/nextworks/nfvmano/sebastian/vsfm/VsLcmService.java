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
package it.nextworks.nfvmano.sebastian.vsfm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsBlueprintCatalogueService;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.vsmanagement.VsLocalEngine;
import it.nextworks.nfvmano.sebastian.vsfm.vsnbi.VsLcmProviderInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsBlueprint;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.messages.QueryVsBlueprintResponse;
import it.nextworks.nfvmano.sebastian.common.Utilities;

import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.vsfm.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.ModifyVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.PurgeVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.QueryVsResponse;
import it.nextworks.nfvmano.sebastian.vsfm.messages.TerminateVsRequest;

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
public class VsLcmService implements VsLcmProviderInterface {

	private static final Logger log = LoggerFactory.getLogger(VsLcmService.class);
	
	@Autowired
	private VsLocalEngine vsLocalEngine;
	
	@Autowired
	private VsDescriptorCatalogueService vsDescriptorCatalogueService;
	
	@Autowired
	private VsBlueprintCatalogueService vsBlueprintCatalogueService;
	
	@Autowired
	private VsRecordService vsRecordService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private NfvoCatalogueService nfvoCatalogueService;

	@Autowired
	private NfvoLcmService nfvoLcmService;
	
	@Value("${sebastian.admin}")
	private String adminTenant;
	
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
		
		LocationInfo locationConstraints = request.getLocationConstraints();
		String ranEndPointId = null;
		if (locationConstraints != null) {
			ranEndPointId = vsb.getRanEndPoint();
			if (ranEndPointId != null) 
				log.debug("Set location constraints and RAN endpoint for VS instance.");
			else log.warn("No RAN endpoint available. Unable to specify the location constraints for the service.");
		}
		
		log.debug("The VS instantion request is valid.");
		
		String vsiId = vsRecordService.createVsInstance(request.getName(), request.getDescription(), vsdId, tenantId, userData, locationConstraints, ranEndPointId);
		vsLocalEngine.initNewVsLcmManager(vsiId);
		if (!(tenantId.equals(adminTenant))) adminService.addVsiInTenant(vsiId, tenantId);
		try {
			vsLocalEngine.instantiateVs(vsiId, request);
			log.debug("Synchronous processing for VSI instantiation request completed for VSI ID " + vsiId);
			return vsiId;
		} catch (Exception e) {
			vsRecordService.setVsFailureInfo(vsiId, e.getMessage());
			throw new FailedOperationException(e.getMessage());
		}
	}

	@Override
	public QueryVsResponse queryVs(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
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
						NetworkSliceInstance nsi = vsRecordService.getNsInstance(nsiId);
						String nfvNsId = nsi.getNfvNsId();
						if (nfvNsId != null) {
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
	public List<String> queryAllVsIds(GeneralizedQueryRequest request)
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
	public void terminateVs(TerminateVsRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received request to terminate a Vertical Service instance.");
		request.isValid();
		
		String tenantId = request.getTenantId();
		String vsiId = request.getVsiId();
		
		VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiId);
		if (tenantId.equals(adminTenant) || vsi.getTenantId().equals(tenantId)) {
			log.debug("The termination request is valid.");
			vsLocalEngine.terminateVs(vsiId, request);
			log.debug("Synchronous processing for VSI termination request completed for VSI ID " + vsiId);
		} else {
			log.debug("Tenant " + tenantId + " is not allowed to terminate VS instance " + vsiId);
			throw new NotPermittedOperationException("Tenant " + tenantId + " is not allowed to terminate VS instance " + vsiId);
		}
	}
	
	@Override
	public void purgeVs(PurgeVsRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
		log.debug("Received request to purge a terminated Vertical Service instance.");
		request.isValid();
		
		String tenantId = request.getTenantId();
		String vsiId = request.getVsiId();
		
		VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiId);
		if (tenantId.equals(adminTenant) || vsi.getTenantId().equals(tenantId)) {
			String nsiId = vsi.getNetworkSliceId();

			//Remove also the SO-managed NSIs
			//Retrieve nsInstance -> get all nsSubnets
			//For nsSub : nsSubnets -> id nsSub.soManaged==true -> vsRecords.deleteNsInstance
			NetworkSliceInstance nsi = vsRecordService.getNsInstance(nsiId);
			for (String nsiSubnetId : nsi.getNetworkSliceSubnetInstances()){
				nsi = vsRecordService.getNsInstance(nsiSubnetId);
				if (nsi.getSoManaged()){
					vsRecordService.deleteNsInstance(nsiSubnetId);
				}
			}
			vsRecordService.deleteNsInstance(nsiId);
			vsRecordService.removeVsInstance(vsiId);
			vsLocalEngine.removeVerticalServiceLcmManager(vsiId);
			log.debug("VSI purge action completed for VSI ID " + vsiId);
		} else {
			log.debug("Tenant " + tenantId + " is not allowed to purge VS instance " + vsiId);
			throw new NotPermittedOperationException("Tenant " + tenantId + " is not allowed to purge VS instance " + vsiId);
		}
	}

	@Override
	public void modifyVs(ModifyVsRequest request) throws MethodNotImplementedException, NotExistingEntityException,
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
		vsLocalEngine.modifyVs(vsiId, request);
		log.debug("Synchronous processing for VSI modification request completed for VSI ID " + vsiId);
		//throw new MethodNotImplementedException("VS modification not yet supported.");
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

}
