package it.nextworks.nfvmano.flexPhyMac.rest;

import it.nextworks.nfvmano.flexPhyMac.rest.record.FlexPhyMacSubscriber;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.flexPhyMac.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ran/subscribers-management")
public class SubscriberManagementRestController {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriberManagementRestController.class);

    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private RanNetworkSubSliceService ranNetworkSubSliceService;

    public SubscriberManagementRestController(){}


    @RequestMapping(value = "/{ranNetworkSubSliceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getSubscribersForSlice(@PathVariable String ranNetworkSubSliceId) {
        try {
            LOG.info("Received request to get subscribers from network slice whose id is "+ranNetworkSubSliceId);
            List<FlexPhyMacSubscriber> flexPhyMacSubscriberList = subscriberService.getSubscriberForSlice(ranNetworkSubSliceId);
            return new ResponseEntity<>(flexPhyMacSubscriberList, HttpStatus.OK);
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


    @RequestMapping(value = "/{ranNetworkSubSliceId}/{imsiSubscriber}", method = RequestMethod.POST)
    public ResponseEntity<?> registerSubscribers(@PathVariable String ranNetworkSubSliceId, @PathVariable String imsiSubscriber) {
        try {
            LOG.info("Received request to associate a new subscriber for slice "+ranNetworkSubSliceId);
            subscriberService.associateSubscriberWithSlice(ranNetworkSubSliceId, imsiSubscriber, true, null);
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


    @RequestMapping(value = "/{ranNetworkSubSliceId}/{imsiSubscriber}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeSubscriber(@PathVariable String ranNetworkSubSliceId, @PathVariable String imsiSubscriber) {
        try {
            LOG.info("Received request to remove a subscriber with IMSI "+imsiSubscriber+ "from RAN subslice with ID "+ranNetworkSubSliceId);
            subscriberService.removeSubscriber(ranNetworkSubSliceId, imsiSubscriber);
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
