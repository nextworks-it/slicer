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
package it.nextworks.nfvmano.sebastian.vsnbi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.elements.Filter;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
import it.nextworks.nfvmano.libs.records.nsinfo.NsInfo;
import it.nextworks.nfvmano.libs.records.nsinfo.SapInfo;
import it.nextworks.nfvmano.libs.records.vnfinfo.VnfExtCpInfo;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.catalogue.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.engine.Engine;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.ModifyVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.PurgeVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.QueryVsResponse;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.TerminateVsRequest;

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

	private static final Logger log = LoggerFactory.getLogger(VsLcmProviderInterface.class);
	
	@Autowired
	private Engine engine;
	
	@Autowired
	private VsDescriptorCatalogueService vsDescriptorCatalogueService;
	
	@Autowired
	private VsRecordService vsRecordService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private NfvoService nfvoService;
	
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
		
		log.debug("The VS instantion request is valid.");
		
		String vsiId = vsRecordService.createVsInstance(request.getName(), request.getDescription(), vsdId, tenantId);
		engine.initNewVsLcmManager(vsiId);
		if (!(tenantId.equals(adminTenant))) adminService.addVsiInTenant(vsiId, tenantId);
		try {
			engine.instantiateVs(vsiId, request);
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
					if (nsiId != null) {
						NetworkSliceInstance nsi = vsRecordService.getNsInstance(nsiId);
						String nfvNsId = nsi.getNfvNsId();
						if (nfvNsId != null) {
							QueryNsResponse queryNs = nfvoService.queryNs(new GeneralizedQueryRequest(Utilities.buildNfvNsiFilter(nfvNsId), null));
							NsInfo nsInfo = queryNs.getQueryNsResult().get(0);							
							log.debug("Retrieved NS info from NFVO");
							externalInterconnections = nsInfo.getSapInfo();
							//TODO: in order to get VNF info we should interact with the VNFM... Still thinking about how to do that.
						} else log.debug("The Network Slice is not associated to any NFV Network Service. No interconnection info available.");
					} else log.debug("The VS is not associated to any Network Slice. No interconnection info available.");
					return new QueryVsResponse(vsiId, vsi.getName(), vsi.getDescription(), vsi.getVsdId(), vsi.getStatus(),
							externalInterconnections, internalInterconnections, vsi.getErrorMessage());
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
			engine.terminateVs(vsiId, request);
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
			vsRecordService.removeVsInstance(vsiId);
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
		throw new MethodNotImplementedException("VS modification not yet supported.");
	}

}
