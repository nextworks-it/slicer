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
package it.nextworks.nfvmano.sebastian.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.DeletePolicyResponse;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.GenericPolicyListRequest;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.QueryPolicyResponse;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.TransferPolicyRequest;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.TenantGroup;


@RestController
@CrossOrigin
@RequestMapping("/vs/admin")
public class AdminRestController {

	private static final Logger log = LoggerFactory.getLogger(AdminRestController.class);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PolicyManagementService policyManagementService;
	
	public AdminRestController() {	}
	
	@RequestMapping(value = "/policy", method = RequestMethod.POST)
	public ResponseEntity<?> createPolicy(@RequestBody TransferPolicyRequest request) {
		log.debug("Received request to create policy");
		try {
			String policyId = policyManagementService.transferPolicy(request);
			return new ResponseEntity<String>(policyId, HttpStatus.CREATED);
		} catch (MalformattedElementException e) {
			log.debug("Malformatted request: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.debug("General exception: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/policy/{policyid}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePolicy(@PathVariable String policyid) {
		log.debug("Received request to delete policy " + policyid);
		try {
			List<String> policyInfoId = new ArrayList<>();
			policyInfoId.add(policyid);
			GenericPolicyListRequest request = new GenericPolicyListRequest(policyInfoId);
			DeletePolicyResponse response = policyManagementService.deletePolicy(request);
			if (response.getDeletedPolicyInfoId().get(0).equals(policyid)) return new ResponseEntity<>(HttpStatus.OK);
			else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (MalformattedElementException e) {
			log.debug("Malformatted request: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.debug("General exception: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/policy", method = RequestMethod.GET)
	public ResponseEntity<?> getPolicies() {
		log.debug("Received request to get all policies");
		Filter filter = new Filter(new HashMap<>());
		GeneralizedQueryRequest request = new GeneralizedQueryRequest(filter, null);
		try {
			QueryPolicyResponse response = policyManagementService.queryPolicy(request);
			return new ResponseEntity<QueryPolicyResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.debug("General exception: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/policy/{policyid}", method = RequestMethod.GET)
	public ResponseEntity<?> getPolicy(@PathVariable String policyid) {
		log.debug("Received request to get policy " + policyid);
		Map<String, String> filterMap = new HashMap<>();
		filterMap.put("POLICY_ID", policyid);
		Filter filter = new Filter(filterMap);
		GeneralizedQueryRequest request = new GeneralizedQueryRequest(filter, null);
		try {
			QueryPolicyResponse response = policyManagementService.queryPolicy(request);
			return new ResponseEntity<QueryPolicyResponse>(response, HttpStatus.OK);
		} catch (NotExistingEntityException e) {
			log.debug("Not found exception: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.debug("General exception: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/group/{name}", method = RequestMethod.POST)
	public ResponseEntity<?> createGroup(@PathVariable String name) {
		log.debug("Received request to create a new group with name " + name);
		try {
			adminService.createGroup(name);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (AlreadyExistingEntityException e) {
			log.debug("Group already existing");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "/group/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getGroup(@PathVariable String name) {
		log.debug("Received request to get info about group with name " + name);
		try {
			TenantGroup group = adminService.getGroup(name);
			return new ResponseEntity<TenantGroup>(group, HttpStatus.OK);
		} catch (NotExistingEntityException e) {
			log.debug("Group not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/group", method = RequestMethod.GET)
	public ResponseEntity<?> getTenantGroups() {
		log.debug("Received request to get info about all tenant groups");
		List<TenantGroup> tenantGroups = adminService.getAllGroups();
		return new ResponseEntity<List<TenantGroup>>(tenantGroups, HttpStatus.OK);
	}

	@RequestMapping(value = "/group/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteGroup(@PathVariable String name) {
		log.debug("Received request to remove group with name " + name);
		try {
			adminService.deleteGroup(name);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NotExistingEntityException e) {
			log.debug("Group not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (FailedOperationException e) {
			log.debug("Group deletion failed");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "/group/{groupName}/tenant", method = RequestMethod.POST)
	public ResponseEntity<?> createTenant(@PathVariable String groupName, @RequestBody Tenant tenant) {
		log.debug("Received request to create a tenant with name " + tenant.getUsername());
		try {
			tenant.isValid();
			adminService.createTenant(tenant, groupName);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (MalformattedElementException e) {
			log.error("Tenant without username");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("Tenant group not existing");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (AlreadyExistingEntityException e) {
			log.error("Tenant already existing");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "/group/{groupname}/tenant/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> getTenant(@PathVariable String username) {
		log.debug("Received request to get info about tenant with name " + username);
		try {
			Tenant tenant = adminService.getTenant(username);
			return new ResponseEntity<Tenant>(tenant, HttpStatus.OK);
		} catch (NotExistingEntityException e) {
			log.debug("Tenant not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/groups/tenants", method = RequestMethod.GET)
	public ResponseEntity<?> getTenants() {
		log.debug("Received request to get info about all tenants");
		List<Tenant> tenants = adminService.getAllTenants();
		return new ResponseEntity<List<Tenant>>(tenants, HttpStatus.OK);
	}

	@RequestMapping(value = "/group/{groupname}/tenant", method = RequestMethod.GET)
	public ResponseEntity<?> getTenantsInGroup(@PathVariable String groupname) {
		log.debug("Received request to get info about all tenants in group " + groupname);
		List<Tenant> tenants = adminService.getAllTenantsByGroupName(groupname);
		return new ResponseEntity<List<Tenant>>(tenants, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/group/{groupname}/tenant/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTenant(@PathVariable String username) {
		log.debug("Received request to remove tenant with username " + username);
		try {
			adminService.deleteTenant(username);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NotExistingEntityException e) {
			log.debug("Tenant not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (FailedOperationException e) {
			log.debug("Tenant deletion failed");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "/group/{groupName}/tenant/{userName}/sla", method = RequestMethod.POST)
	public ResponseEntity<?> createTenant(@PathVariable String userName, @RequestBody Sla sla) {
		log.debug("Received request to create a new SLA for tenant with name " + userName);
		try {
			Long slaId = adminService.createSla(userName, sla);
			return new ResponseEntity<Long>(slaId, HttpStatus.CREATED);
		} catch (MalformattedElementException e) {
			log.error("Malformatted SLA");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("Tenant not existing");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} 
	}
	
	@RequestMapping(value = "/group/{groupname}/tenant/{username}/sla", method = RequestMethod.GET)
	public ResponseEntity<?> getTenantSlas(@PathVariable String username) {
		log.debug("Received request to get all the SLAs associated to the tenant with name " + username);
		try {
			Set<Sla> sla = adminService.getSlaByTenant(username); 
			return new ResponseEntity<Set<Sla>>(sla, HttpStatus.OK);
		} catch (NotExistingEntityException e) {
			log.debug("SLA not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/group/{groupname}/tenant/{username}/sla/{slaId}", method = RequestMethod.GET)
	public ResponseEntity<?> getSla(@PathVariable String username, @PathVariable String slaId) {
		log.debug("Received request to get the SLA with ID "+ slaId + " associated to the tenant with name " + username);
		try {
			Sla sla = adminService.getSlaById(slaId, username); 
			return new ResponseEntity<>(sla, HttpStatus.OK);
		} catch (NotExistingEntityException e) {
			log.debug("SLA not found");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.debug("Malformatted SLA request");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/group/{groupname}/tenant/{username}/sla/{slaId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSla(@PathVariable String username, @PathVariable String slaId) {
		log.debug("Received request to remove SLA with ID " + slaId + " for tenant with username " + username);
		try {
			adminService.deleteSla(slaId, username);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NotExistingEntityException e) {
			log.debug("Tenant not found");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.debug("Malformatted request");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	

}
