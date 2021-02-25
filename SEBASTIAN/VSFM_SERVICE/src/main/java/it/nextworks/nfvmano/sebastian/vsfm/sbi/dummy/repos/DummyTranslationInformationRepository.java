package it.nextworks.nfvmano.sebastian.vsfm.sbi.dummy.repos;

import it.nextworks.nfvmano.sebastian.vsfm.sbi.dummy.elements.DummyTranslationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DummyTranslationInformationRepository extends JpaRepository<DummyTranslationInformation, Long> {

    Optional<DummyTranslationInformation> findById(String UUID);

    Optional<DummyTranslationInformation> findByNsiId(String nsiId);
}
