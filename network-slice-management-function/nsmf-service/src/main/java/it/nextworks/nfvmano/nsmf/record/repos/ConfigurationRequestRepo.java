package it.nextworks.nfvmano.nsmf.record.repos;

import it.nextworks.nfvmano.nsmf.record.elements.ConfigurationRequestRecord;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceInstanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConfigurationRequestRepo extends JpaRepository<ConfigurationRequestRecord, Long> {
    Optional<ConfigurationRequestRecord> findById(UUID nsiId);
}
