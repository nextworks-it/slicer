package it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.repos;

import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.OsmInfoObject;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.OsmObjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OsmInfoObjectRepository extends JpaRepository<OsmInfoObject, String> {

    Optional<OsmInfoObject> findById(String id);
    List<OsmInfoObject> findByOsmId(String osmId);
    List<OsmInfoObject> findByOsmIdAndType(String osmId, OsmObjectType type);
    Optional<OsmInfoObject> findByDescriptorIdAndVersionAndOsmId(String descriptorId, String version, String osmId);
    Optional<OsmInfoObject> findByDescriptorIdAndOsmId(String descriptorId, String osmId);
}
