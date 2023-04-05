package it.nextworks.nfvmano.nsmf.record.repos;

import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceInstanceRecord;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceSubnetInstanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NetworkSliceSubnetInstanceRepo extends JpaRepository<NetworkSliceSubnetInstanceRecord, UUID> {
    Optional<NetworkSliceSubnetInstanceRecord> findByNssiIdentifier(UUID nsiId);
}
