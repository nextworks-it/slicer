package it.nextworks.nfvmano.flexPhyMac.repo;

import it.nextworks.nfvmano.flexPhyMac.rest.record.RanSubSlice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RanSubSliceRepository extends JpaRepository<RanSubSlice, Long> {
    Optional<RanSubSlice> findBySliceId(String sliceId);
}
