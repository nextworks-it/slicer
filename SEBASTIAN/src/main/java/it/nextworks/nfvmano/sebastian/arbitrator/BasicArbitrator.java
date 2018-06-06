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
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;

/**
 * This is a basic arbitrator that only checks if the request VSI is compliant with 
 * the SLA of the tenant and does not support network slice sharing.
 * 
 * @author nextworks
 *
 */
public class BasicArbitrator extends AbstractArbitrator {

	private static final Logger log = LoggerFactory.getLogger(BasicArbitrator.class);
	
	public BasicArbitrator(AdminService adminService, VsRecordService vsRecordService,
			TranslatorService translatorService, NfvoService nfvoService) {
		super(adminService, vsRecordService, translatorService, nfvoService, ArbitratorType.BASIC_ARBITRATOR);
	}

	@Override
	public List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests) 
			throws FailedOperationException, NotExistingEntityException {
		log.debug("Received request at the arbitrator. At the moment dummy reply.");
		
		//TODO: At the moment we process only the first request and the first ns init info
		ArbitratorRequest req = requests.get(0);
		String tenantId = req.getTenantId();
		NfvNsInstantiationInfo nsInitInfo = null;
		Map<String, NfvNsInstantiationInfo> nsInitInfos = req.getInstantiationNsd();
		for (Map.Entry<String, NfvNsInstantiationInfo> e : nsInitInfos.entrySet()) {
			nsInitInfo = e.getValue();
		}
		
		log.debug("The request is for tenant " + tenantId + " and for NSD " + nsInitInfo.getNfvNsdId() + " with DF " + nsInitInfo.getDeploymentFlavourId() + " and instantiation level " + nsInitInfo.getInstantiationLevelId());
		
		try {
			VirtualResourceUsage requiredRes = nfvoService.computeVirtualResourceUsage(nsInitInfo);
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
			if ((requiredRes.getDiskStorage() + usedRes.getDiskStorage()) > maxRes.getDiskStorage()) acceptableRequest = false;
			if ((requiredRes.getMemoryRAM() + usedRes.getMemoryRAM()) > maxRes.getMemoryRAM()) acceptableRequest = false;
			if ((requiredRes.getvCPU() + usedRes.getvCPU()) > maxRes.getvCPU()) acceptableRequest = false;
			
			ArbitratorResponse response = new ArbitratorResponse(requests.get(0).getRequestId(), 
					acceptableRequest,					//acceptableRequest 
					true, 								//newSliceRequired, 
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
	
	

}
