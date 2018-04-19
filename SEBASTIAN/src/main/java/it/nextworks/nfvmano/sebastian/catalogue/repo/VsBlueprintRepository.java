package it.nextworks.nfvmano.sebastian.catalogue.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.catalogue.elements.VsBlueprint;

public interface VsBlueprintRepository extends JpaRepository<VsBlueprint, Long> {

	Optional<VsBlueprint> findByVsBlueprintId(String id);
	Optional<VsBlueprint> findByNameAndVersion(String name, String version);
}
