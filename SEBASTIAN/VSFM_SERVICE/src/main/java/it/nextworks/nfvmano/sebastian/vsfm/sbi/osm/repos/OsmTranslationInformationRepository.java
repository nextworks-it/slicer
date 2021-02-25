package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.repos;

import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements.OsmTranslationInformation;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements.SonataTranslationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OsmTranslationInformationRepository extends JpaRepository<OsmTranslationInformation, Long> {

    Optional<OsmTranslationInformation> findById(String UUID);

    Optional<OsmTranslationInformation> findByNsiId(String nsiId);

    Optional<OsmTranslationInformation> findByOsmInstanceId(String osmInstanceId);
}
