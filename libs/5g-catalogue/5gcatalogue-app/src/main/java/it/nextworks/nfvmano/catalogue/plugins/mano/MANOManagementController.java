/*
 * Copyright 2018 Nextworks s.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.catalogue.plugins.mano;

import io.swagger.annotations.ApiParam;
import it.nextworks.nfvmano.catalogue.plugins.PluginsManager;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANO;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/catalogue/manoManagement")
public class MANOManagementController {

    private static final Logger log = LoggerFactory.getLogger(MANOManagementController.class);

    @Autowired
    private PluginsManager pluginsManager;

    public MANOManagementController() {

    }

    @RequestMapping(value = "/manos", method = RequestMethod.POST)
    public ResponseEntity<?> createMANO(@ApiParam(value = "", required = true) @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization, @RequestBody MANO mano) throws MethodNotImplementedException {

        log.debug("Received request for new MANO loading");
        if ((mano == null) || (mano.getManoId() == null)) {
            log.error("Malformatted MANO - Not acceptable");
            return new ResponseEntity<String>("MANO or MANO ID null", HttpStatus.BAD_REQUEST);
        } else {
            if (mano.getManoType() == null) {
                log.error("Malformatted MANO - Not acceptable");
                return new ResponseEntity<String>("MANO TYPE null", HttpStatus.BAD_REQUEST);
            }
        }

        String createdManoId;

        try {
            createdManoId = pluginsManager.createMANOPlugin(mano, false);
        } catch (AlreadyExistingEntityException e) {
            return new ResponseEntity<String>("MANO already present in DB", HttpStatus.CONFLICT);
        } catch (MethodNotImplementedException e) {
            return new ResponseEntity<String>("Unsupported MANO type", HttpStatus.BAD_REQUEST);
        } catch (MalformattedElementException e) {
            return new ResponseEntity<String>("Malformatted MANO: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(createdManoId, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/manos", method = RequestMethod.GET)
    public ResponseEntity<List<MANO>> getMANOs(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) throws MethodNotImplementedException {

        log.debug("Received request for getting MANO plugins");

        List<MANO> manos = pluginsManager.getAllMANOPlugins();

        return new ResponseEntity<List<MANO>>(manos, HttpStatus.OK);
    }

    @RequestMapping(value = "/manos/{manoId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMANO(@ApiParam(value = "", required = true) @PathVariable("manoId") String manoId, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) throws MethodNotImplementedException {

        log.debug("Received request for getting MANO plugin with manoId {}", manoId);

        MANO mano = null;

        try {
            mano = pluginsManager.getMANOPlugin(manoId);
        } catch (NotExistingEntityException e) {
            return new ResponseEntity<String>("MANO Plugin with manoId " + manoId + " not present in DB", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<MANO>(mano, HttpStatus.OK);
    }

    @RequestMapping(value = "/manos/{manoId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateMANO(@ApiParam(value = "", required = true) @PathVariable("manoId") String manoId, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization, @RequestBody MANO mano) throws MethodNotImplementedException {

        log.debug("Received request for updating MANO plugin with manoId {}", manoId);

        MANO newMano;

        try {
            newMano = pluginsManager.updateMANOPlugin(manoId, mano);
        } catch (NotExistingEntityException e) {
            return new ResponseEntity<String>("MANO Plugin with manoId " + manoId + " not present in DB", HttpStatus.BAD_REQUEST);
        } catch (FailedOperationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<MANO>(newMano, HttpStatus.OK);
    }
}
