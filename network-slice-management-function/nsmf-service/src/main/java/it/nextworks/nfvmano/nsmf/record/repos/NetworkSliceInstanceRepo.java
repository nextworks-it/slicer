package it.nextworks.nfvmano.nsmf.record.repos;

import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceInstanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NetworkSliceInstanceRepo extends JpaRepository<NetworkSliceInstanceRecord, Long> {
    Optional<NetworkSliceInstanceRecord> findById(UUID nsiId);
}
