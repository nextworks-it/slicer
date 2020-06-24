package it.nextworks.nfvmano.sebastian.vsfm.sbi;


import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class implements a REST controller to receive notifications from
 * the NSMF about actuation results about Network Slice Instances
 *
 *
 * @author nextworks
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/vs/notifications")
public class ActuationNotificationsRestController {

    private static final Logger log = LoggerFactory.getLogger(ActuationNotificationsRestController.class);

    public ActuationNotificationsRestController(){}

    @RequestMapping(value = "/actuation/{nsiId}", method = RequestMethod.POST)
    public ResponseEntity<?> notifyNsiLcmChange(@PathVariable String nsiId,
                                                @RequestBody NetworkSliceStatusChangeNotification networkSliceStatusChangeNotification) {
        log.debug("Received notification about NSI actuation about NSI with ID "+nsiId);
        //log.info("The actuation request is successful "+ networkSliceStatusChangeNotification.isSuccessful());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
