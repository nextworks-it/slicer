package it.nextworks.nfvmano.sebastian.record.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;

public interface VerticalServiceInstanceRepository extends JpaRepository<VerticalServiceInstance, Long> {

	Optional<VerticalServiceInstance> findByVsiId(String vsiId);
	List<VerticalServiceInstance> findByTenantId(String tenantId);
	List<VerticalServiceInstance> findByVsdId(String vsdId);
	List<VerticalServiceInstance> findByNetworkSliceId(String networkSliceId);
	
}
