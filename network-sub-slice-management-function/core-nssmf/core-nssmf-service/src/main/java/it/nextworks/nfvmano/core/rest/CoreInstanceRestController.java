package it.nextworks.nfvmano.core.rest;

import it.nextworks.nfvmano.core.recordIM.CoreInstanceInfo;
import it.nextworks.nfvmano.core.service.*;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("${nssmf.provisioning.baseurl}")
public class CoreInstanceRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CoreInstanceRestController.class);

    @Autowired
    private CoreInstanceService coreInstanceService;

    public CoreInstanceRestController(){}


    @RequestMapping(value = "/nss/{coreInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCoreInstanceInfo(@PathVariable String coreInstanceId) {
        try {
            LOG.info("Received request to get information about core instance with ID  "+coreInstanceId);
            CoreInstanceInfo coreInstanceInfo = coreInstanceService.getCoreInstanceInfo(coreInstanceId);
            return new ResponseEntity<>(coreInstanceInfo, HttpStatus.OK);
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

    @RequestMapping(value = "/nss", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCoreInstanceInfo() {
        try {
            LOG.info("Received request to get information about all core instances ");
            List<CoreInstanceInfo> coreInstanceInfoList = coreInstanceService.getAllCoreInstanceInfo();
            for(CoreInstanceInfo coreInstanceInfo: coreInstanceInfoList){ //Removing the default slice information
                coreInstanceInfo.getCoreNetworkSliceId().remove("nsst_core");
            }
            return new ResponseEntity<>(coreInstanceInfoList, HttpStatus.OK);
        }

        catch(Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
