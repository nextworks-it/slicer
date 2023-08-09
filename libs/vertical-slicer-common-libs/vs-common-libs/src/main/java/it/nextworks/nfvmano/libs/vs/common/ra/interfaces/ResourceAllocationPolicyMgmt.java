package it.nextworks.nfvmano.libs.vs.common.ra.interfaces;

import it.nextworks.nfvmano.libs.vs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.query.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.ResourceAllocationPolicy;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.policy.OnboardRAPolicyRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.policy.RAPolicyMatchRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResourceAllocationPolicyMgmt {


    UUID onboardResourceAllocationPolicy(OnboardRAPolicyRequest request) throws MalformattedElementException, AlreadyExistingEntityException;

    void deleteResourceAllocationPolicy(UUID raPolicyId) throws NotExistingEntityException;

    List<ResourceAllocationPolicy> getResourceAllocationPolicies(GeneralizedQueryRequest request);

    Optional<ResourceAllocationPolicy> findMatchingPolicy(RAPolicyMatchRequest request) throws MalformattedElementException;
}
