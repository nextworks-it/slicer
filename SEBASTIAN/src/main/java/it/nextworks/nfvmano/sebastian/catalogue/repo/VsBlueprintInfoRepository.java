package it.nextworks.nfvmano.sebastian.catalogue.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.catalogue.elements.VsBlueprintInfo;

public interface VsBlueprintInfoRepository extends JpaRepository<VsBlueprintInfo, Long> {

	Optional<VsBlueprintInfo> findByVsBlueprintId(String id);
	Optional<VsBlueprintInfo> findByNameAndVsBlueprintVersion(String name, String version);
	
}
