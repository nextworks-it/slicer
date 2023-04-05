package it.nextworks.nfvmano.nsmf.ra;

import it.nextworks.nfvmano.libs.vs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.query.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAAlgorithmType;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.ResourceAllocationPolicy;
import it.nextworks.nfvmano.libs.vs.common.ra.interfaces.ResourceAllocationPolicyMgmt;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.policy.OnboardRAPolicyRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.policy.RAPolicyMatchRequest;
import it.nextworks.nfvmano.nsmf.ra.record.elements.ResourceAllocationPolicyRecord;
import it.nextworks.nfvmano.nsmf.ra.record.repos.ResourceAllocationPolicyRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResourceAllocationPolicyService implements ResourceAllocationPolicyMgmt {

    private static final Logger log = LoggerFactory.getLogger(ResourceAllocationPolicyService.class);
    @Autowired
    private ResourceAllocationPolicyRepo resourceAllocationPolicyRepo;

    @Value("${resource_allocation.default_algorithm:STATIC}")
    private RAAlgorithmType defaultRaAlgorithm;

    @Override
    public UUID onboardResourceAllocationPolicy(OnboardRAPolicyRequest request) throws MalformattedElementException, AlreadyExistingEntityException {
        log.debug("Processing request to Onboard a new RA policy");
        request.isValid();
        ResourceAllocationPolicyRecord record = new ResourceAllocationPolicyRecord(request.getResourceAllocationPolicy());

        if(request.getResourceAllocationPolicy().isDefault()){
            log.debug("Request to onboard default policy");
            Optional<ResourceAllocationPolicyRecord> currentDefault = resourceAllocationPolicyRepo.findByIsDefault(true);
            if(currentDefault.isPresent()){
                log.debug("Removing previous default RA policy");
                resourceAllocationPolicyRepo.delete(currentDefault.get());
            }

        }else{
            if(resourceAllocationPolicyRepo.findByNstIdAndTenant(
                    request.getResourceAllocationPolicy().getNstId(),
                    request.getResourceAllocationPolicy().getTenant()
            ).isPresent())
                throw new AlreadyExistingEntityException(
                        "A policy with NST ID "+ request.getResourceAllocationPolicy().getNstId()
                                +" and tenant "+ request.getResourceAllocationPolicy().getTenant()
                                +" is already present, delete or modify policy with ID ");

            if(resourceAllocationPolicyRepo.findBySliceTypeAndTenant(
                    request.getResourceAllocationPolicy().getSliceType(),
                    request.getResourceAllocationPolicy().getTenant()).isPresent())
                throw new AlreadyExistingEntityException("A policy with slice type "+ request.getResourceAllocationPolicy().getSliceType()
                        +" and tenant "+ request.getResourceAllocationPolicy().getTenant()
                        +" is already present, delete or modify policy with ID");


        }
        log.debug("Creating new policy based on received request");

        //ResourceAllocationPolicyRecord policy=new ResourceAllocationPolicyRecord(request.getResourceAllocationPolicy());
        resourceAllocationPolicyRepo.saveAndFlush(record);
        log.debug("Created policy with ID "+record.getId());
        return record.getId();
    }


    @PostConstruct
    private void onboardDefaultPolicy(){
        log.debug("Onboarding default RA policy with default algorithm:"+ defaultRaAlgorithm);
        ResourceAllocationPolicy policy = new ResourceAllocationPolicy(null,
                null,
                null,
                null,
                null,
                null,
                defaultRaAlgorithm,
                null,
                true
        );
        OnboardRAPolicyRequest request = new OnboardRAPolicyRequest(policy);
        try {
            onboardResourceAllocationPolicy(request);
        } catch (MalformattedElementException e) {
           log.error("Failed to onboard default RA policy", e);
        } catch (AlreadyExistingEntityException e) {
            log.error("Failed to onboard default RA policy", e);
        }
    }

    @Override
    public void deleteResourceAllocationPolicy(UUID raPolicyId) throws NotExistingEntityException {
        log.debug("Processing request to delete a RA policy:"+raPolicyId);
        if(resourceAllocationPolicyRepo.findById(raPolicyId).isPresent()){
            resourceAllocationPolicyRepo.delete(raPolicyId);
            log.debug("RA policy deleted");
        }else throw new NotExistingEntityException("Resource Allocation policy with id:"+raPolicyId+" does not exist");


    }

    @Override
    public List<ResourceAllocationPolicy> getResourceAllocationPolicies(GeneralizedQueryRequest request) {
        log.debug("Processing query for Resource Allocation Policies");
        //TODO implement filters
        return resourceAllocationPolicyRepo.findAll().stream()
                .map(p -> p.getResourceAllocationPolicy())
                .collect(Collectors.toList());

    }

    @Override
    public Optional<ResourceAllocationPolicy> findMatchingPolicy(RAPolicyMatchRequest request) throws MalformattedElementException {
        log.debug("Receive request to determine the appropriate RA Algorithm");

        request.isValid();



        ResourceAllocationPolicyRecord matchPolicy=null;
        List<ResourceAllocationPolicyRecord> foundPolicies;
        Optional<ResourceAllocationPolicyRecord> policy =
                resourceAllocationPolicyRepo.findByNstIdAndTenant(request.getNstId(), request.getTenant());
        if(policy.isPresent()) {
            log.debug("Retrieving policies with NST ID {} and tenant {}", request.getNstId(), request.getTenant());

            if(policy.get().getExpirationTime()!=null){
                Date currentDate = new Date();
                if(currentDate.compareTo(policy.get().getExpirationTime())<0){
                    log.debug("Found policy:"+policy.get().getId());
                    matchPolicy=policy.get();
                }
            }
        } else {
            log.debug("No policy found by using NST ID {} and tenant {}", request.getNstId(), request.getTenant());
        }

        policy= resourceAllocationPolicyRepo.findBySliceTypeAndTenant(request.getSST(), request.getTenant());
        if(matchPolicy==null && policy.isPresent()){
            log.debug("Retrieving policies with slice type {} and tenant {}", request.getSST(), request.getTenant());

            if(policy.get().getExpirationTime()!=null){
                Date currentDate = new Date();
                if(currentDate.compareTo(policy.get().getExpirationTime())<0){
                    log.debug("Found policy:"+policy.get().getId());
                    return   Optional.of(policy.get().getResourceAllocationPolicy());
                }else log.debug("Ignoring expired policy:"+policy.get().getId());
            }else return Optional.of(policy.get().getResourceAllocationPolicy());
        } else{
            log.debug("No policy found by using slice type {} and tenant {}", request.getSST(), request.getTenant());
        }
        policy = resourceAllocationPolicyRepo.findBySliceType(request.getSST());
        if(policy.isPresent()){
            log.debug("Retrieving policies with slice type {}", request.getSST());
            if(policy.get().getExpirationTime()!=null){
                Date currentDate = new Date();
                if(currentDate.compareTo(policy.get().getExpirationTime())<0){
                    log.debug("Found policy:"+policy.get().getId());
                    return Optional.of(policy.get().getResourceAllocationPolicy());
                }else log.debug("Ignoring expired policy:"+policy.get().getId());
            }else return Optional.of(policy.get().getResourceAllocationPolicy());
        }
        log.debug("No policy found searching default");
        if(resourceAllocationPolicyRepo.findByIsDefault(true).isPresent())
            return Optional.of(resourceAllocationPolicyRepo.findByIsDefault(true).get().getResourceAllocationPolicy());
        return Optional.empty();
    }


}
