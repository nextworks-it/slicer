package it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.repos;

import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common.ManoObjectType;
import it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.elements.OnapObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OnapObjectRepository extends JpaRepository<OnapObject, String> {

    Optional<OnapObject> findById(String id);
    Optional<OnapObject> findByDescriptorIdAndTypeAndOnapId(String descriptorId, ManoObjectType type, String onapId);
    List<OnapObject> findByOnapIdAndType(String onapId, ManoObjectType type);
    List<OnapObject> findByOnapId(String onapId);
    Optional<OnapObject> findByDescriptorIdAndVersionAndOnapId(String descriptorId, String version, String onapId);
    Optional<OnapObject> findByCatalogueIdAndOnapId(String catalogue, String onapId);

}
