package it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record;

import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceType;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record.elements.StaticRaResponseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StaticRaResponseRepository extends JpaRepository<StaticRaResponseRecord, UUID> {

    Optional<StaticRaResponseRecord> findBySliceType(SliceType sliceType);

    Optional<StaticRaResponseRecord> findByResponseId(UUID responseId);
}
