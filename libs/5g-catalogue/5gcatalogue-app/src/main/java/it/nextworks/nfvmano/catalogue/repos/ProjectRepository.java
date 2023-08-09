package it.nextworks.nfvmano.catalogue.repos;

import it.nextworks.nfvmano.catalogue.auth.projectmanagement.ProjectResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectResource, UUID> {

    Optional<ProjectResource> findById(UUID id);

    Optional<ProjectResource> findByProjectId(String projectId);
}
