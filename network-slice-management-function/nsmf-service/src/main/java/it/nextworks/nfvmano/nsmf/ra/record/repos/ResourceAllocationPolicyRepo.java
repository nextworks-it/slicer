package it.nextworks.nfvmano.nsmf.ra.record.repos;

import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAPolicySliceType;
import it.nextworks.nfvmano.nsmf.ra.record.elements.ResourceAllocationPolicyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResourceAllocationPolicyRepo  extends JpaRepository<ResourceAllocationPolicyRecord, UUID> {

    Optional<ResourceAllocationPolicyRecord> findById(UUID id);

    Optional<ResourceAllocationPolicyRecord> findByNstIdAndTenant(String nstId, String tenant);

    Optional<ResourceAllocationPolicyRecord> findBySliceTypeAndTenant(RAPolicySliceType sliceType, String tenant);

    Optional<ResourceAllocationPolicyRecord> findBySliceType(RAPolicySliceType sst);

    Optional<ResourceAllocationPolicyRecord> findByIsDefault(boolean b);
}
