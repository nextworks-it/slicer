package it.nextworks.nfvmano.sebastian.nstE2eComposer.repository;

import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.Bucket;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.BucketType;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
        List<Bucket> findByBucketType(BucketType bucketType);
        Optional<Bucket> findById(Long id);
}
