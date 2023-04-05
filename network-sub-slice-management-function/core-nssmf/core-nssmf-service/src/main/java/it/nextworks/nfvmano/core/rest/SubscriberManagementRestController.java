package it.nextworks.nfvmano.core.rest;

import it.nextworks.nfvmano.core.recordIM.SubscriberListForSlice;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.core.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/core/subscribers-management")
public class SubscriberManagementRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CoreNetworkSliceRestController.class);

    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private CoreNetworkSliceService coreNetworkSliceService;

    public SubscriberManagementRestController(){}

    @RequestMapping(value = "/{coreInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllSubscribersInfo(@PathVariable String coreInstanceId) {
        try {
            LOG.info("Received request for getting all subscribers information from core instance "+coreInstanceId);
            List<SubscriberListForSlice> networkSlices = subscriberService.getAllSubscribers(coreInstanceId);
            return new ResponseEntity<>(networkSlices, HttpStatus.OK);
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
    public ResponseEntity<?> getSubscribersForSlice(@PathVariable String coreInstanceId, @PathVariable String coreNetworkSliceId) {
        try {
            LOG.info("Received request to get subscribers from network slice whose id is "+coreNetworkSliceId);
            SubscriberListForSlice subscriberListForSlice = coreNetworkSliceService.getSubscribersForSlice(coreInstanceId, coreNetworkSliceId);
            return new ResponseEntity<>(subscriberListForSlice, HttpStatus.OK);
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


    @RequestMapping(value = "/{coreInstanceId}/{coreNetworkSliceId}", method = RequestMethod.POST)
    public ResponseEntity<?> registerSubscribers(@PathVariable String coreInstanceId, @PathVariable String coreNetworkSliceId, @RequestBody SubscriberListForSlice subscriberListForSlice) {
        try {
            LOG.info("Received request to register a new subscriber list for slice "+coreNetworkSliceId);
            subscriberService.registerSubscribersForSlice(coreInstanceId, coreNetworkSliceId, subscriberListForSlice);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch(MalformattedElementException e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{coreInstanceId}/{imsiSubscriber}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeSubscriber(@PathVariable String coreInstanceId, @PathVariable String imsiSubscriber) {
        try {
            LOG.info("Received request to remove a subscriber with IMSI "+imsiSubscriber+" from core instance "+coreInstanceId);
            subscriberService.removeSubscriber(coreInstanceId, imsiSubscriber);
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
