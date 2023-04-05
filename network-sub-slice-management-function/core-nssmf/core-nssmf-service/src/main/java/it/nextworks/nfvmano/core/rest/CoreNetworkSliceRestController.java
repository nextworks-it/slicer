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
package it.nextworks.nfvmano.core.rest;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.core.service.*;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.CoreSlicePayload;
import it.nextworks.nfvmano.sbi.cnc.messages.NetworkSliceCNC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/core/slice-management")
public class CoreNetworkSliceRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CoreNetworkSliceRestController.class);

    @Autowired
    private CoreNetworkSliceService coreNetworkSliceService;

    public CoreNetworkSliceRestController(){}


    @RequestMapping(value = "/{coreInstanceId}", method = RequestMethod.POST)
    public ResponseEntity<?> createNetworkSliceCore(@PathVariable String coreInstanceId, @RequestBody CoreSlicePayload coreSlicePayload) {
        try {
            LOG.info("Received request to create a core network slice");
            String networkCoreSliceId = coreNetworkSliceService.createCoreNetworkSlice(coreInstanceId, coreSlicePayload);
            return new ResponseEntity<>(networkCoreSliceId, HttpStatus.OK);
        }
        catch(MalformattedElementException e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(NotExistingEntityException e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{coreInstanceId}/{coreNetworkSliceId}", method = RequestMethod.PUT)
    public ResponseEntity<?> modifyNetworkSliceCore(@PathVariable String coreInstanceId, @PathVariable String coreNetworkSliceId, @RequestBody CoreSlicePayload coreSlicePayload) {
        try {
            LOG.info("Received request to update core network slice information");
            coreNetworkSliceService.updateCoreNetworkSlice(coreInstanceId, coreNetworkSliceId, coreSlicePayload);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch(NotExistingEntityException e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{coreInstanceId}/{coreNetworkSliceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getNetworkSliceCore(@PathVariable String coreInstanceId, @PathVariable String coreNetworkSliceId) {
        try {
            LOG.info("Received request to get core network slice information");
            NetworkSliceCNC networkSliceCNC = coreNetworkSliceService.getCoreNetworkSliceInformation(coreInstanceId, coreNetworkSliceId);
            return new ResponseEntity<>(networkSliceCNC, HttpStatus.OK);
        }
        catch(NotExistingEntityException e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{coreInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllNetworkSliceCore(@PathVariable String coreInstanceId) {
        try {
            LOG.info("Received request to get all core network slice information");
            List<NetworkSliceCNC> coreNetworkSlices = coreNetworkSliceService.getAllCoreNetworkSliceInformation(coreInstanceId);
            return new ResponseEntity<>(coreNetworkSlices, HttpStatus.OK);
        }
        catch(NotExistingEntityException e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{coreInstanceId}/{coreNetworkSliceId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteNetworkSliceCore(@PathVariable String coreInstanceId, @PathVariable String coreNetworkSliceId) {
        try {
            LOG.info("Received request to remove a core network slice with identifier: "+coreNetworkSliceId);
            coreNetworkSliceService.deleteCoreNetworkSlice(coreInstanceId, coreNetworkSliceId);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
        catch(NotExistingEntityException e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
