package it.nextworks.nfvmano.sebastian.admin.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


import it.nextworks.nfvmano.sebastian.admin.elements.TenantGroup;

public interface GroupRepository extends JpaRepository<TenantGroup, Long> {

	Optional<TenantGroup> findByName(String name);
	Optional<TenantGroup> findById(Long id);
	
}
