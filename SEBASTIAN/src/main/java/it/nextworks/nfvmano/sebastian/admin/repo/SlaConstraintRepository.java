package it.nextworks.nfvmano.sebastian.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.admin.elements.SlaVirtualResourceConstraint;

public interface SlaConstraintRepository extends JpaRepository<SlaVirtualResourceConstraint, Long>{

}
