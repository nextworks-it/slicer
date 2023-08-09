package it.nextworks.nfvmano.catalogue.repos;

import it.nextworks.nfvmano.catalogue.auth.usermanagement.UserResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserResource, UUID> {

    Optional<UserResource> findById(UUID id);

    Optional<UserResource> findByUserName(String userName);
}
