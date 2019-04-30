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
package it.nextworks.nfvmano.sebastian.catalogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.elements.Filter;
import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.catalogue.elements.ServiceConstraints;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.catalogue.messages.OnboardVsDescriptorRequest;
import it.nextworks.nfvmano.sebastian.catalogue.messages.QueryVsDescriptorResponse;
import it.nextworks.nfvmano.sebastian.catalogue.repo.ServiceConstraintsRepository;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;

@Service
public class VsDescriptorCatalogueService implements VsDescriptorCatalogueInterface {

	private static final Logger log = LoggerFactory.getLogger(VsDescriptorCatalogueService.class);
	
	@Autowired
	private VsDescriptorRepository vsDescriptorRepository;
	
	@Autowired
	private ServiceConstraintsRepository serviceConstraintsRepository;
	
	@Autowired
	private VsBlueprintCatalogueService vsBlueprintCatalogueService;
	
	@Value("${sebastian.admin}")
	private String adminTenant;
	
	public VsDescriptorCatalogueService() { }
	
	@Override
	public synchronized String onBoardVsDescriptor(OnboardVsDescriptorRequest request)
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		log.debug("Processing request to on-board a new VS descriptor");
		request.isValid();
		VsDescriptor vsd = new VsDescriptor(request.getVsd().getName(), request.getVsd().getVersion(), request.getVsd().getVsBlueprintId(), 
				request.getVsd().getSst(), request.getVsd().getManagementType(), request.getVsd().getQosParameters(), request.getVsd().getSla(), request.isPublic(), request.getTenantId());
		String vsdId = storeVsd(vsd, request.getVsd().getServiceConstraints());
		try {
			vsBlueprintCatalogueService.addVsdInBlueprint(vsd.getVsBlueprintId(), vsdId);
		} catch (NotExistingEntityException e) {
			throw new FailedOperationException(e.getMessage());
		}
		return vsdId;
	}
	
	@Override
	public QueryVsDescriptorResponse queryVsDescriptor(GeneralizedQueryRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
		log.debug("Processing a query for a VS descriptor");
		request.isValid();

		//At the moment the only filters accepted are:
		//1. VS Descriptor ID and tenant ID
		//VSD_ID & TENANT_ID
		//2. Tenant ID
		//TENANT_ID
		//No attribute selector is supported at the moment
		
		List<VsDescriptor> vsDescriptors = new ArrayList<>();
		Filter filter = request.getFilter();
        List<String> attributeSelector = request.getAttributeSelector();
        if ((attributeSelector == null) || (attributeSelector.isEmpty())) {
        	Map<String, String> fp = filter.getParameters();
            if (fp.size() == 1 && fp.containsKey("TENANT_ID")) {
            	String tenantId = fp.get("TENANT_ID");
            	if (tenantId.equals(adminTenant)) {
            		log.debug("VSD query for admin: returning all the VSDs");
            		vsDescriptors = vsDescriptorRepository.findAll();
            		log.debug("Retrieved all the VSDs");
            	} else {
            		vsDescriptors = vsDescriptorRepository.findByTenantId(tenantId);
            		log.debug("Added VSD for tenant " + tenantId);
            		List<VsDescriptor> tmpPublicVsds = vsDescriptorRepository.findByIsPublic(true);
            		for (VsDescriptor vsd : tmpPublicVsds) {
            			if (!(vsd.getTenantId().equals(tenantId))) {
            				vsDescriptors.add(vsd);
            				log.debug("Added public VSD " + vsd.getName());
            			}
            		}
            	}
            } else if (fp.size() == 2 && fp.containsKey("VSD_ID") && fp.containsKey("TENANT_ID")) {
            	String vsdId = fp.get("VSD_ID");
            	String tenantId = fp.get("TENANT_ID");
            	Optional<VsDescriptor> vsd = null;
            	if (tenantId.equals(adminTenant)) {
            		vsd = vsDescriptorRepository.findByVsDescriptorId(vsdId);
            	} else {
            		vsd = vsDescriptorRepository.findByVsDescriptorIdAndTenantId(vsdId, tenantId);
            	}
            	if (vsd.isPresent()) {
            		vsDescriptors.add(vsd.get());
            		log.debug("Added VSD with VSD ID " + vsdId + " for tenant " + tenantId);
            	} else throw new NotExistingEntityException("VSD with VSD ID " + vsdId + " for tenant " + tenantId + " not found");
            } else if (fp.isEmpty()) {
            	vsDescriptors = vsDescriptorRepository.findByIsPublic(true);
            	log.debug("Added all the public VSD available in DB.");
            }
            return new QueryVsDescriptorResponse(vsDescriptors);
        } else {
            log.error("Received query VS Descriptor with attribute selector. Not supported at the moment.");
            throw new MethodNotImplementedException("Received query VS Descriptor with attribute selector. Not supported at the moment.");
        }
	}
	
	@Override
	public synchronized void deleteVsDescriptor(String vsDescriptorId, String tenantId)
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
		log.debug("Processing request to delete a VS descriptor");
		if  (vsDescriptorId == null) throw new MalformattedElementException("VSD ID is null");
		
		Optional<VsDescriptor> vsdOpt = vsDescriptorRepository.findByVsDescriptorId(vsDescriptorId);
		if (vsdOpt.isPresent()) {
			VsDescriptor vsd = vsdOpt.get();
			if ( (vsd.getTenantId().equals(tenantId)) || (tenantId.equals(adminTenant)) ) {
				String vsbId = vsd.getVsBlueprintId();
				vsDescriptorRepository.delete(vsd);
				vsBlueprintCatalogueService.removeVsdInBlueprint(vsbId, vsDescriptorId);
				log.debug("VSD " + vsDescriptorId + " removed from the internal DB.");
			} else {
				log.error("Tenant " + tenantId + " does not have the right to remove the VSD " + vsDescriptorId);
				throw new FailedOperationException("Tenant " + tenantId + " does not have the right to remove the VSD " + vsDescriptorId);
			}
		} else {
			log.error("VSD " + vsDescriptorId + " not found");
			throw new NotExistingEntityException("VSD " + vsDescriptorId + " not found");
		}
	}
	
	public VsDescriptor getVsd(String vsdId) throws NotExistingEntityException {
		log.debug("Internal request to retrieve VSD with ID " + vsdId);
		Optional<VsDescriptor> vsdOpt = vsDescriptorRepository.findByVsDescriptorId(vsdId);
		if (vsdOpt.isPresent()) return vsdOpt.get();
		else throw new NotExistingEntityException("VSD with ID " + vsdId + " not found");
	}
	
	private String storeVsd(VsDescriptor vsd, List<ServiceConstraints> serviceConstraints) throws AlreadyExistingEntityException, FailedOperationException {
		log.debug("On boarding VSD with name " + vsd.getName() + " and version " + vsd.getVersion() + " for tenant " + vsd.getTenantId());
		if (vsDescriptorRepository.findByNameAndVersionAndTenantId(vsd.getName(), vsd.getVersion(), vsd.getTenantId()).isPresent()) {
			log.error("The VSD is already present");
			throw new AlreadyExistingEntityException("VSD with name " + vsd.getName() + " and version " + vsd.getVersion() + " for tenant " + vsd.getTenantId() + " already present");
		}
		vsDescriptorRepository.saveAndFlush(vsd);
		String vsdId = String.valueOf(vsd.getId());
		vsd.setVsDescriptorId(vsdId);
		vsDescriptorRepository.saveAndFlush(vsd);
		log.debug("Added VS Descriptor with ID " + vsdId);
		if (serviceConstraints != null) {
			for (ServiceConstraints sc : serviceConstraints) {
				ServiceConstraints targetServiceConstraints = new ServiceConstraints(vsd, sc.isSharable(), 
						sc.isCanIncludeSharedElements(), sc.getPriority(), sc.getPreferredProviders(), sc.getNonPreferredProviders(),
						sc.getProhibitedProviders(), sc.getAtomicComponentId());
				serviceConstraintsRepository.saveAndFlush(targetServiceConstraints);
			}
			log.debug("Added service constraints for VS descriptor with ID " + vsdId);
		}
		return vsdId;
	}

}
