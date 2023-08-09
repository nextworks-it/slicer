package it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.repos;

import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.elements.OsmTranslationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TranslationInformationRepository extends JpaRepository<OsmTranslationInformation, UUID> {

    Optional<OsmTranslationInformation> findById(String UUID);

    Optional<OsmTranslationInformation> findByCatInfoIdAndOsmManoId(String catInfoId, String osmManoId);
    List<OsmTranslationInformation> findByOsmManoId(String osmManoId);
    List<OsmTranslationInformation> findByOsmInfoIdAndOsmManoId(String osmInfoId, String osmManoId);
    List<OsmTranslationInformation> findByOsmDescriptorIdAndDescriptorVersionAndOsmManoId(String osmDescriptorId, String descriptorVersion, String osmManoId);
    List<OsmTranslationInformation> findByCatDescriptorIdAndDescriptorVersionAndOsmManoId(String catDescriptorId, String descriptorVersion, String osmManoId);
    List<OsmTranslationInformation> findByCatDescriptorIdAndOsmManoId(String catDescriptorId, String osmManoId);
}
