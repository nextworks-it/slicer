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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.TenantGroup;
import it.nextworks.nfvmano.sebastian.admin.repo.GroupRepository;
import it.nextworks.nfvmano.sebastian.admin.repo.SlaConstraintRepository;
import it.nextworks.nfvmano.sebastian.admin.repo.SlaRepository;
import it.nextworks.nfvmano.sebastian.admin.repo.TenantRepository;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;


/**
 * The Admin service handles the management requests for creation of tenants, groups and SLAs.
 * It is invoked by the AdminRestController.
 *  
 * @author nextworks
 *
 */
@Service
public class AdminService {
	
	private static final Logger log = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private TenantRepository tenantRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private SlaRepository slaRepository;
	
	@Autowired
	private SlaConstraintRepository slaConstraintRepository;
	
	@Autowired
	private VsRecordService vsRecordService;

	@Autowired
	@Qualifier("sPasswordEncoder")
	private PasswordEncoder passwordEncoder;
	
	public AdminService() {	}

	public synchronized void createGroup(String name) throws AlreadyExistingEntityException {
		log.debug("Processing request to create a new group " + name);
		Optional<TenantGroup> groupOpt = groupRepository.findByName(name);
		if (groupOpt.isPresent()) throw new AlreadyExistingEntityException("Group " + name + " already present in DB.");
		TenantGroup group = new TenantGroup(name);
		groupRepository.saveAndFlush(group);
		log.debug("Group " + name + " added in internal DB.");
	}
	
	public TenantGroup getGroup(String name) throws NotExistingEntityException {
		log.debug("Processing request to get group " + name);
		Optional<TenantGroup> groupOpt = groupRepository.findByName(name);
		if (groupOpt.isPresent()) return groupOpt.get();
		else throw new NotExistingEntityException("Group " + name + " not existing in DB.");	
	}
	
	public synchronized void deleteGroup(String name) throws NotExistingEntityException, FailedOperationException {
		log.debug("Processing request to remove group " + name);
		TenantGroup group = getGroup(name);
		if (group.getTenants().isEmpty()) {
			groupRepository.delete(group);
			log.debug("Group " + name + " removed from internal DB.");
		} else {
			log.error("The group includes some tenants. Impossible to remove it.");
			throw new FailedOperationException("The group includes some tenants. Impossible to remove it.");
		}
	}
	
	public synchronized void createTenant(Tenant tenant, String groupName) throws AlreadyExistingEntityException, NotExistingEntityException {
		log.debug("Processing request to create a new tenant");
		TenantGroup tg = getGroup(groupName);
		Optional<Tenant> tenantOpt = tenantRepository.findByUsername(tenant.getUsername());
		if (tenantOpt.isPresent()) throw new AlreadyExistingEntityException("Tenant " + tenant.getUsername() + " already present in DB.");
		Tenant target = new Tenant(
				tg,
				tenant.getUsername(),
				passwordEncoder.encode(tenant.getPassword())
		);
		tenantRepository.saveAndFlush(target);
		log.debug("Tenant " + tenant.getUsername() + " added in internal DB.");
	}
	
	public Tenant getTenant(String name) throws NotExistingEntityException {
		log.debug("Processing request to get tenant " + name);
		Optional<Tenant> tenantOpt = tenantRepository.findByUsername(name);
		if (tenantOpt.isPresent()) return tenantOpt.get();
		else throw new NotExistingEntityException("Tenant " + name + " not existing in DB.");
	}
	
	public synchronized void deleteTenant(String username) throws NotExistingEntityException, FailedOperationException {
		log.debug("Processing request to remove tenant " + username);
		Tenant tenant = getTenant(username);
		if (tenant.getVsdId().isEmpty() && tenant.getVsiId().isEmpty()) {
			TenantGroup group = tenant.getGroup();
			group.getTenants().remove(tenant);
			groupRepository.save(group);
			log.debug("Tenant " + username + " removed from internal DB.");
		} else {
			log.error("The tenant cannot be removed since it has active VSIs or VSDs.");
			throw new FailedOperationException("The tenant cannot be removed since it has active VSIs or VSDs.");
		}
	}
	
	public synchronized Long createSla(String tenantUserName, Sla sla) throws NotExistingEntityException, MalformattedElementException {
		log.debug("Processing request to create a new SLA for tenant " + tenantUserName);
		Tenant tenant = getTenant(tenantUserName);
		sla.isValid();
		Sla target = new Sla(tenant, sla.getSlaStatus());
		slaRepository.saveAndFlush(target);
		Long id = target.getId();
		log.debug("Created SLA with ID " + id + " for tenant " + tenantUserName);
		List<SlaVirtualResourceConstraint> constraints = sla.getSlaConstraints();
		for (SlaVirtualResourceConstraint src : constraints) {
			SlaVirtualResourceConstraint targetSrc = new SlaVirtualResourceConstraint(target, src.getMaxResourceLimit(), src.getScope(), src.getLocation());
			slaConstraintRepository.saveAndFlush(targetSrc);
		}
		return id;
	}
	
	public Set<Sla> getSlaByTenant(String username) throws NotExistingEntityException {
		log.debug("Processing request to retrieve SLA associated to tenant " + username);
		Set<Sla> sla = slaRepository.findByTenantUsername(username);
		if (sla.isEmpty()) throw new NotExistingEntityException("SLA not found");
		return sla;
	}
	
	public Sla getSlaById(String id, String username) throws NotExistingEntityException, MalformattedElementException {
		log.debug("Processing request to retrieve SLA with ID " + id + " and associated to tenant " + username);
		Long idLong = Long.valueOf(id);
		Optional<Sla> slaOpt = slaRepository.findById(idLong);
		if (slaOpt.isPresent()) {
			Sla sla = slaOpt.get();
			if (sla.getTenant().getUsername().equals(username)) return sla;
			else throw new NotExistingEntityException("Not found SLA for the given tenant.");
		} else throw new NotExistingEntityException("SLA with ID " + id + " not found in DB.");
	}
	
	public synchronized void deleteSla(String id, String username) throws NotExistingEntityException, MalformattedElementException {
		log.debug("Processing request to remove SLA with ID " + id + " and associated to tenant " + username);
		Sla sla = getSlaById(id, username);
		Tenant tenant = sla.getTenant();
		tenant.getSla().remove(sla);
		tenantRepository.save(tenant);
		log.debug("SLA removed from internal DB.");
	}
	
	public List<TenantGroup> getAllGroups() {
		log.debug("Processing request to retrieve all the tenant groups");
		List<TenantGroup> groups = groupRepository.findAll();
		return groups;
	}
	
	public List<Tenant> getAllTenants() {
		log.debug("Processing request to retrieve all the tenants");
		List<Tenant> tenants = tenantRepository.findAll();
		return tenants;
	}

	public List<Tenant> getAllTenantsByGroupName(String groupName) {
		log.debug("Processing request to retrieve all the tenants belonging to group " + groupName);
		List<Tenant> tenants = tenantRepository.findByGroupName(groupName);
		return tenants;
	}

	public List<Sla> getAllSlas() {
		log.debug("Processing request to retrieve all the SLAs");
		List<Sla> slas = slaRepository.findAll();
		return slas;
	}
	
	public List<String> getAllNetworkSliceInstancesId() {
		log.debug("Retrieving the IDs of all the network slice instances.");
		List<String> nsIds = new ArrayList<>();
		List<NetworkSliceInstance> nss = vsRecordService.getAllNetworkSliceInstance();
		for (NetworkSliceInstance ns : nss) {
			nsIds.add(ns.getNsiId());
		}
		return nsIds;
	}
	
	public List<NetworkSliceInstance> getAllNetworkSliceInstances() {
		log.debug("Retrieving all the network slice instances.");
		return vsRecordService.getAllNetworkSliceInstance();
	}
	
	public NetworkSliceInstance getNetworkSliceInstance(String nsiId) throws NotExistingEntityException {
		log.debug("Retrieving network slice instance with ID " + nsiId);
		return vsRecordService.getNsInstance(nsiId);
	}
	
	public synchronized void addVsdInTenant(String vsdId, String tenantId) 
			throws NotExistingEntityException {
		log.debug("Adding VSD " + vsdId + " to tenant " + tenantId);
		Tenant tenant = getTenant(tenantId);
		tenant.addVsd(vsdId);
		tenantRepository.saveAndFlush(tenant);
		log.debug("Added VSD " + vsdId + " to tenant " + tenantId);
	}
	
	public synchronized void removeVsdFromTenant(String vsdId, String tenantId) 
			throws NotExistingEntityException {
		log.debug("Removing VSD " + vsdId + " from tenant " + tenantId);
		Tenant tenant = getTenant(tenantId);
		tenant.removeVsd(vsdId);
		tenantRepository.saveAndFlush(tenant);
		log.debug("Removed VSD " + vsdId + " from tenant " + tenantId);
	}
	
	public synchronized void addVsiInTenant(String vsiId, String tenantId) 
			throws NotExistingEntityException {
		log.debug("Adding VSI " + vsiId + " to tenant " + tenantId);
		Tenant tenant = getTenant(tenantId);
		tenant.addVsi(vsiId);
		tenantRepository.saveAndFlush(tenant);
		log.debug("Added VSI " + vsiId + " to tenant " + tenantId);
	}
	
	public synchronized void removeVsiFromTenant(String vsiId, String tenantId) 
			throws NotExistingEntityException {
		log.debug("Removing VSI " + vsiId + " from tenant " + tenantId);
		Tenant tenant = getTenant(tenantId);
		tenant.removeVsi(vsiId);
		tenantRepository.saveAndFlush(tenant);
		log.debug("Removed VSI " + vsiId + " from tenant " + tenantId);
	}

	public synchronized void addUsedResourcesInTenant(String tenantId, VirtualResourceUsage vru)
			throws NotExistingEntityException {
		log.debug("Adding consumed resources to tenant " + tenantId);
		Tenant tenant = getTenant(tenantId);
		tenant.addUsedResources(vru);
		tenantRepository.saveAndFlush(tenant);
		log.debug("Added consumed resources to tenant " + tenantId);
	}

	public synchronized void removeUsedResourcesInTenant(String tenantId, VirtualResourceUsage vru)
			throws NotExistingEntityException {
		log.debug("Removing consumed resources to tenant " + tenantId);
		Tenant tenant = getTenant(tenantId);
		tenant.removeUsedResources(vru);
		tenantRepository.saveAndFlush(tenant);
		log.debug("Removed consumed resources to tenant " + tenantId);
	}
	}
