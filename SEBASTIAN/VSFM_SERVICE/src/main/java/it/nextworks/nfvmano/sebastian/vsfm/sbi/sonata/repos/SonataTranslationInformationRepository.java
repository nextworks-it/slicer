package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.repos;

import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements.SonataTranslationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SonataTranslationInformationRepository extends JpaRepository<SonataTranslationInformation, Long> {

    Optional<SonataTranslationInformation> findById(String UUID);

    Optional<SonataTranslationInformation> findByNsiId(String nsiId);

    Optional<SonataTranslationInformation> findBySonataInstanceId(String sonataInstanceId);
}
