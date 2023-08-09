package it.nextworks.nfvmano.core.repo;

import it.nextworks.nfvmano.core.recordIM.UpfInstanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpfInstanceInfoRepository extends JpaRepository<UpfInstanceInfo, Long> {
    Optional<UpfInstanceInfo> findByUpfInstanceId(String upfInstanceId);
}
