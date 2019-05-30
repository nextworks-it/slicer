package it.nextworks.nfvmano.sebastian.admin.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.libs.policy.elements.PolicyInfo;
import it.nextworks.nfvmano.libs.policy.enums.ActivationStatus;

public interface PolicyRepository extends JpaRepository<PolicyInfo, Long> {

	Optional<PolicyInfo> findById(Long id);
	Optional<PolicyInfo> findByPolicyInfoId(String policyInfoId);
	List<PolicyInfo> findByActivationStatus(ActivationStatus activationStatus);
	
}
