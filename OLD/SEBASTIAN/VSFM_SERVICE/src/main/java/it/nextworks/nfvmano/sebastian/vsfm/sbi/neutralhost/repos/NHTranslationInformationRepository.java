package it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.repos;

import it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements.NHTranslationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NHTranslationInformationRepository extends JpaRepository<NHTranslationInformation, Long> {

    Optional<NHTranslationInformation> findById(String UUID);

    Optional<NHTranslationInformation> findByNsiId(String nsiId);

    Optional<NHTranslationInformation> findBySmNsInstanceId(String smNsInstanceId);
}
