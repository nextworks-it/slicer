package it.nextworks.nfvmano.sebastian.admin.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

	Optional<Tenant> findByUsername(String username);
	Optional<Tenant> findById(Long id);

	List<Tenant> findByGroupName(String groupname);
	
}
