package it.nextworks.nfvmano.sebastian.record.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;

public interface NetworkSliceInstanceRepository extends JpaRepository<NetworkSliceInstance, Long> {

	Optional<NetworkSliceInstance> findByNsiId(String nsiId);
	Optional<NetworkSliceInstance> findByNfvNsId(String nfvNsId);
	List<NetworkSliceInstance> findByTenantId(String tenantId);
	
}
