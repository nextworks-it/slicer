package it.nextworks.nfvmano.catalogue.blueprint.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.catalogue.blueprint.elements.VsComponent;

public interface VsComponentRepository extends JpaRepository<VsComponent, Long> {

}
