package it.nextworks.nfvmano.sebastian.catalogue.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;

public interface VsDescriptorRepository extends JpaRepository<VsDescriptor, Long> {

	List<VsDescriptor> findByTenantId(String tenantId);
	Optional<VsDescriptor> findByVsDescriptorId(String vsdId);
	Optional<VsDescriptor> findByVsDescriptorIdAndTenantId(String vsdId, String tenantId);
	List<VsDescriptor> findByIsPublic(boolean isPublic);
	Optional<VsDescriptor> findByNameAndVersionAndTenantId(String name, String version, String tenantId);
	
}
