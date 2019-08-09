package it.nextworks.nfvmano.sebastian.catalogue.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.catalogue.elements.ServiceConstraints;

public interface ServiceConstraintsRepository extends JpaRepository<ServiceConstraints, Long> {

}
