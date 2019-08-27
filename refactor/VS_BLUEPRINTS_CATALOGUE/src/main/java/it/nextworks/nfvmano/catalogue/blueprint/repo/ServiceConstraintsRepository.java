package it.nextworks.nfvmano.catalogue.blueprint.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.catalogue.blueprint.elements.ServiceConstraints;

public interface ServiceConstraintsRepository extends JpaRepository<ServiceConstraints, Long> {

}
