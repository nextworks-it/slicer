package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.smfsm.repo;

import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements.SonataTranslationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SliceManagerFsmTranslationInformationRepository extends JpaRepository<SliceManagerFsmTranslationInformation, Long> {



    Optional<SliceManagerFsmTranslationInformation> findByNsiId(String nsiId);


}
