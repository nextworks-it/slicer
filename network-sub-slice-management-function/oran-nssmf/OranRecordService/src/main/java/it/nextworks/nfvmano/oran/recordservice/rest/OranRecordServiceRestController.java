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
package it.nextworks.nfvmano.oran.recordservice.rest;

import com.fasterxml.jackson.databind.util.JSONPObject;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.oran.policies.enums.PolicyTypeIdEnum;
import it.nextworks.nfvmano.oran.recordservice.OranNetworkSliceSubnetInstance;
import it.nextworks.nfvmano.oran.recordservice.PolicyTypeJsonSchema;
import it.nextworks.nfvmano.oran.recordservice.service.OranRecordService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/oran/recordservice")
public class OranRecordServiceRestController {

    private static final Logger log = LoggerFactory.getLogger(OranRecordServiceRestController.class);
    @Autowired
    private OranRecordService recordService;

    public OranRecordServiceRestController(){}

    @RequestMapping(value="/nssi/{nssiId}/createnssientry", method = RequestMethod.POST)
    public ResponseEntity<?> createNssiEntry(@PathVariable String nssiId) {
        log.debug("Received request to create new NSSI entry with ID {}", nssiId);
        try{
            recordService.createOranNetworkSliceSubnetInstanceEntry(UUID.fromString(nssiId));
            return new ResponseEntity<>("NSSI with ID "+nssiId+" created", HttpStatus.OK);
        } catch (Exception e) {
            log.error("NSSI creation failed due to internal errors.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/nssi/{nssiId}", method = RequestMethod.GET)
    public ResponseEntity<?> queryNssi(@PathVariable String nssiId){
        log.debug("Received request to query NSSI with ID {}", nssiId);
        try{
            OranNetworkSliceSubnetInstance nssi=recordService.getNssInstance(UUID.fromString(nssiId));
            return new ResponseEntity<>(nssi, HttpStatus.OK);
        } catch (NotExistingEntityException e) {
            log.error("NSSI query failed due to missing elements in DB.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("NSSI query failed due to internal errors.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/policytypeschema/{policyTypeId}/createpolicytypeentry", method = RequestMethod.POST)
    public ResponseEntity<?> createPolicyTypeJsonSchema(@PathVariable PolicyTypeIdEnum policyTypeId, @RequestBody String body){
        log.debug("Received request to create new PolicyType JSON Schema with ID {}", policyTypeId);
        try{
            JSONObject jsonSchema=new JSONObject(body);
            log.debug("This is the JSON \n {}", jsonSchema);
            recordService.createPolicyTypeJsonSchemaEntry(policyTypeId, body);
            return new ResponseEntity<>("PolicyType JSON Schema correctly created", HttpStatus.OK);
        } catch (Exception e) {
            log.error("PolicyType JSON Schema creation failed due to internal errors.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/policytypeschema/{policyTypeId}", method = RequestMethod.GET)
    public ResponseEntity<?> queryPolicyTypeJsonSchema(@PathVariable PolicyTypeIdEnum policyTypeId){
        log.debug("Received request to query new PolicyType JSON Schema with ID {}", policyTypeId);
        try{
            PolicyTypeJsonSchema policyTypeJsonSchema=recordService.getPolicyTypeJsonSchema(policyTypeId);
            return new ResponseEntity<>(policyTypeJsonSchema, HttpStatus.OK);
        } catch (NotExistingEntityException e) {
            log.error("PolicyType JSON Schema query failed due to missing elements in DB.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("PolicyType JSON Schema creation failed due to internal errors.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
