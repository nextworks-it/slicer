package it.nextworks.nfvmano.core.rest;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.sbi.cnc.GNbConfiguration;
import it.nextworks.nfvmano.core.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/core/configuration")
public class CoreConfigurationController {

    private static final Logger LOG = LoggerFactory.getLogger(CoreConfigurationController.class);

    @Autowired
    private GnbService gnbService;



    @RequestMapping(value = "/gnb", method = RequestMethod.POST)
    public ResponseEntity<?> addGNb(@RequestBody GNbConfiguration gNbConfiguration) {
        try {
            LOG.info("Received request to add gNB to a core instance. For simplicity is assumed to configure the first instance ");
            gnbService.storegNBInfo(gNbConfiguration);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (MalformattedElementException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
