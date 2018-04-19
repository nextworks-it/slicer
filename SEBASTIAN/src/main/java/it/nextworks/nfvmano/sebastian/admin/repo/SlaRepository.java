package it.nextworks.nfvmano.sebastian.admin.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.admin.elements.Sla;

public interface SlaRepository extends JpaRepository<Sla, Long> {

	Optional<Sla> findById(Long id);
	Set<Sla> findByTenantUsername(String tenantUsername);
	
}
