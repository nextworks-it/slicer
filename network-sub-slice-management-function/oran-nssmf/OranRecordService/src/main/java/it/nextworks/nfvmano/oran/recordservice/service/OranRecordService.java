/*
 * Copyright (c) 2021 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.oran.recordservice.service;

import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;
import it.nextworks.nfvmano.oran.policies.enums.PolicyTypeIdEnum;
import it.nextworks.nfvmano.oran.recordservice.OranNetworkSliceSubnetInstance;
import it.nextworks.nfvmano.oran.recordservice.PolicyTypeJsonSchema;
import it.nextworks.nfvmano.oran.recordservice.repo.OranNssiRepository;
import it.nextworks.nfvmano.oran.recordservice.repo.PolicyTypeJsonSchemaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class OranRecordService {
    private static final Logger log = LoggerFactory.getLogger(OranRecordService.class);

    @Autowired
    private OranNssiRepository nssiRepository;

    @Autowired
    private PolicyTypeJsonSchemaRepository policyTypeJsonSchemaRepository;

    public synchronized void createOranNetworkSliceSubnetInstanceEntry(UUID nssiId){
        log.debug("Creating a new network slice subnet instance");

        OranNetworkSliceSubnetInstance nssi=new OranNetworkSliceSubnetInstance();
        nssi.setNssiId(nssiId);
        nssiRepository.saveAndFlush(nssi);
        log.debug("Created new network slice subnet instance  with ID {}", nssiId.toString());
    }

    /**
     * This method returns the NSSI stored in DB that matches a given ID
     *
     * @param nssiId ID of the Network Slice Subnet Instance to be returned
     * @return the Network Slice Subnet instance
     * @throws NotExistingEntityException if the NSSI with the given ID is not present in DB
     */
    public OranNetworkSliceSubnetInstance getNssInstance(UUID nssiId) throws NotExistingEntityException {
        log.debug("Retrieving nssi with ID {} from DB", nssiId.toString());
        Optional<OranNetworkSliceSubnetInstance> nssi=nssiRepository.findByNssiId(nssiId);
        if(!nssi.isPresent())
            throw new NotExistingEntityException("Nssi with ID "+nssiId+" is not present in DB");

        return nssi.get();
    }

    /**
     * This method adds a new entry to SliceProfileToPolicy map
     * that maps a slice profile ID with the policy ID generated from translation
     *
     * @param nssiId the ID of the network slice subnet instance stored in DB
     * @param entry the entry to add to SliceProfileToPolicy map
     */
    public synchronized void addSliceProfileToPolicyEntry(UUID nssiId, Map.Entry<String, Map.Entry<Integer, String>> entry){
        log.debug("Adding new entry into map SliceProfileToPolicy");
        try{
            OranNetworkSliceSubnetInstance nssi=getNssInstance(nssiId);
            nssi.getSliceProfileToPolicy().put(entry.getKey(), entry.getValue());
            nssiRepository.saveAndFlush(nssi);
            log.debug("Added new entry to map SliceProfileToPolicy");
        } catch (NotExistingEntityException e){
            log.error("NSSI not present in DB. Impossible to add entry to map SliceProfileToPolicy");
        }
    }

    public synchronized void createPolicyTypeJsonSchemaEntry(PolicyTypeIdEnum policyTypeId, String jsonSchema){
        log.debug("Creating new PolicyType JSON Schema entry for PolicyType with ID {}", policyTypeId);

        PolicyTypeJsonSchema policyType=new PolicyTypeJsonSchema(policyTypeId, jsonSchema);
        policyTypeJsonSchemaRepository.saveAndFlush(policyType);
        log.debug("PolicyType JSON Schema correctly created");
    }

    /**
     * This method returns a PolicyTypeJsonSchema in DB that matches with a given ID
     * @param policyTypeId the ID of the PolicyTypeJsonSchema that has to be returned
     * @return the PolicyTypeJsonSchema with the given ID
     * @throws NotExistingEntityException if the PolicyTypeJsonSchema with the given ID is not present in DB
     */
    public PolicyTypeJsonSchema getPolicyTypeJsonSchema(PolicyTypeIdEnum policyTypeId) throws NotExistingEntityException {
        log.debug("Retrieving PolicyType JSON Schema with ID {}", policyTypeId);
        Optional<PolicyTypeJsonSchema> policyTypeJsonSchema=policyTypeJsonSchemaRepository.findByPolicyTypeId(policyTypeId);
        if(!policyTypeJsonSchema.isPresent())
            throw new NotExistingEntityException("PolicyType JSON Schema with ID "+policyTypeId+" not found in DB");

        return policyTypeJsonSchema.get();
    }

    /**
     * This method sets a JSON Schema into a PolicyTypeJsonSchema with a given ID
     * @param policyTypeId the ID of the PolicyTypeJsonSchema to be modified
     * @param jsonSchema the JSON schema to set into the PolicyTypeJsonSchema with the given ID
     */
    public synchronized void setJsonSchema(PolicyTypeIdEnum policyTypeId,String jsonSchema){
        log.debug("Setting new JSON schema for PolicyType with ID {}", policyTypeId);
        try{
            PolicyTypeJsonSchema policyType=getPolicyTypeJsonSchema(policyTypeId);
            policyType.setJsonSchema(jsonSchema);
            policyTypeJsonSchemaRepository.saveAndFlush(policyType);
            log.debug("PolicyType JSON schema correctly set");
        }catch (NotExistingEntityException e){
            log.error("PolicyType not present in DB. Impossible to set new JSON schema");
        }
    }

    /**
     * This method deletes an NSSI stored in DB given its ID
     *
     * @param policyTypeId the ID of the NSSI to be removed
     * @throws NotExistingEntityException if the NSSI is not found into DB
     * @throws NotPermittedOperationException if the operation is not permitted
     */
    public synchronized void deletePolicyTypeJsonSchema(PolicyTypeIdEnum policyTypeId) throws NotExistingEntityException{
        log.debug("Removing PolicyType JSON Schema with ID {} from DB", policyTypeId.toString());

        PolicyTypeJsonSchema policyTypeJsonSchema=getPolicyTypeJsonSchema(policyTypeId);

        policyTypeJsonSchemaRepository.delete(policyTypeJsonSchema);
        log.debug("PolicyType JSON Schema with ID {} correctly deleted from DB", policyTypeId);
    }
}
