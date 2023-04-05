package it.nextworks.nfvmano.core.repo;

import it.nextworks.nfvmano.core.recordIM.CoreInstanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoreInstanceInfoRepository extends JpaRepository<CoreInstanceInfo, Long> {
    Optional<CoreInstanceInfo> findByCoreInstanceId(String coreInstanceId);
}
