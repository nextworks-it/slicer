package it.nextworks.nfvmano.core.rest;

import it.nextworks.nfvmano.core.recordIM.UpfInstanceInfo;
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
public class UpfInstanceRestController {

    private static final Logger LOG = LoggerFactory.getLogger(UpfInstanceRestController.class);

    @Autowired
    private UpfInstanceService upfInstanceService;

    public UpfInstanceRestController(){}


    @RequestMapping(value = "/nss/{upfInstanceName}", method = RequestMethod.GET)
    public ResponseEntity<?> getCoreInstanceInfo(@PathVariable String upfInstanceName) {
        try {
            LOG.info("Received request to get information about UPF instance with name  "+upfInstanceName);
            UpfInstanceInfo upfInstanceInfo = upfInstanceService.getUpfInstanceInfoByName(upfInstanceName);
            return new ResponseEntity<>(upfInstanceInfo, HttpStatus.OK);
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
            LOG.info("Received request to get information about all UPF instances ");
            List<UpfInstanceInfo> upfInstanceInfoList = upfInstanceService.getAllUpfInstanceInfo();
            for(UpfInstanceInfo upfInstanceInfo : upfInstanceInfoList){ //Removing the default slice information
                upfInstanceInfo.getUpfNetworkSliceId().remove("nsst_core");
            }
            return new ResponseEntity<>(upfInstanceInfoList, HttpStatus.OK);
        }

        catch(Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
