/*
 * Copyright (c) 2022 Nextworks s.r.l.
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

package it.nextworks.nfvmano.nssmf.service.nbi;

import io.swagger.annotations.Api;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotPermittedOperationException;

import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.utils.DynamicClassManager;
import it.nextworks.nfvmano.nssmf.service.NssmfLcmService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import java.util.Map;
import java.util.UUID;

/**
 * Class implementing REST Controller exposing Provisioning API (CRUD).
 * Monitor interface has not yet been defined (12 Nov 2021)
 *
 * @author Pietro G. Giardina
 */

@Api(tags = "Network Sub-Slice Provisioning APIs")
@RestController
@CrossOrigin
@RequestMapping("${nssmf.provisionig.baseurl}")
public class ProvisioningRestController {

    private static final Logger log = LoggerFactory.getLogger(ProvisioningRestController.class);
    @Value("${nssmf.provisioning.message.class}")
    private String provisioningMessageClass;
    private Class messageClass;
    private ObjectMapper map;

    @Autowired
    private NssmfLcmService nssmfLcmService;

    public ProvisioningRestController()  {
        this.map = new ObjectMapper();
    }

    @PostConstruct
    private void loadConfig(){
        log.info("Startup Configuration loading");
        try{
            if(!DynamicClassManager.checkClassType(this.provisioningMessageClass, NssmfBaseProvisioningMessage.class)){
                log.error("Error in loading class "+ this.provisioningMessageClass + ": NOT compatible with " +
                        NssmfBaseProvisioningMessage.class.getName() + "\n NSSMF is shutting down!");
                System.exit(0);
            }
            log.debug("Class "+ this.provisioningMessageClass + ": is compatible with " + NssmfBaseProvisioningMessage.class.getName());
            this.messageClass = Class.forName(this.provisioningMessageClass);

        } catch (ClassNotFoundException e) {
            log.error("Error in loading class "+ this.provisioningMessageClass + ": class NOT Found!\nNSSMF is shutting down");
            System.exit(0);
        }

    }

    @RequestMapping(value = "/nss/createnssid", method = RequestMethod.POST)
    public ResponseEntity<?> createNsId() {
        log.debug("Received request to create a new network slice instance ID.");
        try {

            UUID nssiId = nssmfLcmService.createNetworkSubSliceIdentifier();
            return new ResponseEntity<>(nssiId, HttpStatus.CREATED);

        } catch (MalformattedElementException e) {
            log.error("NS ID creation failed due to bad-formatted request.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotPermittedOperationException e) {
            log.error("NS ID creation failed due to missing permission.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("NS ID creation failed due to internal errors.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/nss/{nsiId}/action/instantiate", method = RequestMethod.PUT)
    public ResponseEntity<?> instantiateNsi(@PathVariable String nsiId, @RequestBody Map<String, Object> request ) {
        log.debug("Received request to instantiate network slice " + nsiId);
        try {

            nssmfLcmService.instantiateNetworkSubSlice((NssmfBaseProvisioningMessage)map.convertValue(request, this.messageClass));
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NotExistingEntityException e) {
            log.error("NS instantiation failed due to missing elements in DB.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MalformattedElementException e) {
            log.error("NS instantiation failed due to bad-formatted request.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotPermittedOperationException e) {
            log.error("NS instantiation failed due to missing permission or conflicting state.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("NS instantiation failed due to internal errors.");
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/nss/{nsiId}/action/modify", method = RequestMethod.PUT)
    public ResponseEntity<?> modifyNsi(@PathVariable String nsiId, @RequestBody Map<String, Object> request) {
        log.debug("Received request to modify network slice " + nsiId);
        try {

            nssmfLcmService.modifyNetworkSlice((NssmfBaseProvisioningMessage)map.convertValue(request, this.messageClass));
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NotExistingEntityException e) {
            log.error("NS modification failed due to missing elements in DB.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MalformattedElementException e) {
            log.error("NS modification failed due to bad-formatted request.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotPermittedOperationException e) {
            log.error("NS modification failed due to missing permission or conflicting state.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("NS modification failed due to internal errors.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/nss/{nsiId}/action/terminate", method = RequestMethod.PUT)
    public ResponseEntity<?> terminateNsi(@PathVariable String nsiId, @RequestBody Map<String, Object> request) {
        log.debug("Received request to terminate network slice " + nsiId);
        try {
            nssmfLcmService.terminateNetworkSliceInstance((NssmfBaseProvisioningMessage)map.convertValue(request, this.messageClass));
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NotExistingEntityException e) {
            log.error("NS termination failed due to missing elements in DB.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MalformattedElementException e) {
            log.error("NS termination failed due to bad-formatted request.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotPermittedOperationException e) {
            log.error("NS termination failed due to missing permission or conflicting state.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("NS termination failed due to internal errors.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
