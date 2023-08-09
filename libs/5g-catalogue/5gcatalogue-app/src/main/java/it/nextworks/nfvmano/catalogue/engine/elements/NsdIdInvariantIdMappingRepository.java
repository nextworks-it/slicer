package it.nextworks.nfvmano.catalogue.engine.elements;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NsdIdInvariantIdMappingRepository extends JpaRepository<NsdIdInvariantIdMapping, Long> {

    Optional<NsdIdInvariantIdMapping> findByNsdId(String nsdId);
    List<NsdIdInvariantIdMapping> findByInvariantId(String invariantId);
}
