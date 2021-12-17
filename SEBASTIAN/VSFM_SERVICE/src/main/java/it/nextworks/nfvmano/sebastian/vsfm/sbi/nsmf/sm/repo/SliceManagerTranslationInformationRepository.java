package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.sm.repo;

import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements.SonataTranslationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SliceManagerTranslationInformationRepository extends JpaRepository<SliceManagerTranslationInformation, Long> {

    Optional<SonataTranslationInformation> findById(String UUID);

    Optional<SliceManagerTranslationInformation> findByNsiId(String nsiId);


}
