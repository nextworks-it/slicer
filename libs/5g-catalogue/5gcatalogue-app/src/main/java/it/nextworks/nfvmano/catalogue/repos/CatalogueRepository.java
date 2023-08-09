package it.nextworks.nfvmano.catalogue.repos;

import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {

    Optional<Catalogue> findByCatalogueId(String catalogueId);

}
