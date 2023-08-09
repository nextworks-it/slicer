package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.repos;

import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common.ManoObjectType;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements.SoObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FivegrowthObjectRepository extends JpaRepository<SoObject, String> {

    Optional<SoObject> findById(String id);
    Optional<SoObject> findByDescriptorIdAndTypeAndSoId(String descriptorId, ManoObjectType type, String soId);
    List<SoObject> findBySoIdAndType(String soId, ManoObjectType type);
    List<SoObject> findBySoId(String onapId);
    Optional<SoObject> findByDescriptorIdAndVersionAndSoId(String descriptorId, String version, String soId);
    Optional<SoObject> findByCatalogueIdAndSoId(String catalogue, String soId);
}
