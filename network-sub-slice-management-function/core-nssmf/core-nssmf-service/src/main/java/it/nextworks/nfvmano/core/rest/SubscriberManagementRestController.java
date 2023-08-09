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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/core/subscribers-management")
public class SubscriberManagementRestController {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriberManagementRestController.class);

    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private CoreNetworkSliceService coreNetworkSliceService;

    public SubscriberManagementRestController(){}

    @RequestMapping(value = "/{upfInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllSubscribersInfo(@PathVariable String upfInstanceId) {
        try {
            LOG.info("Received request for getting all subscribers information from core instance "+upfInstanceId);
            List<SubscriberListForSlice> networkSlices = subscriberService.getAllSubscribers(upfInstanceId);
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


    @RequestMapping(value = "/subscriber/{imsi}/{groupName}", method = RequestMethod.POST)
    public ResponseEntity<?> registerSubscriberToGroup(@PathVariable String imsi, @PathVariable String groupName) {
            LOG.info("Received request to register a new subscriber with IMSI "+imsi+ "at the group name "+groupName);
            if(subscriberService.registerSubscriber(imsi, groupName)==null){
                return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/associate-subscriber-to-slice/{imsi}/{sliceId}", method = RequestMethod.POST)
    public ResponseEntity<?> registerSubscriberToSlice(@PathVariable String imsi, @PathVariable String sliceId) {
        LOG.info("Received request to register a new subscriber with IMSI "+imsi+ "and associate to slice with ID "+sliceId);

        try {
            boolean success = subscriberService.associateUeToSlice(imsi, sliceId);
            if(!success){
                LOG.error("Error during UE to slice association");
                return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
            }
        } catch (NotExistingEntityException e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
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


    @RequestMapping(value = "/subscriberProfile/{subProfileId}", method = RequestMethod.POST)
    public ResponseEntity<?> createSubscriberProfileId(@PathVariable String subProfileId) {
            LOG.info("Received request for creating subscription profile with id "+subProfileId);

            String response = subscriberService.createSubscriberProfile(subProfileId);
            if(response==null)
                return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/subscriberGroup/{subscriberGroupId}/{subscriberProfileId}/{sliceId}", method = RequestMethod.POST)
    public ResponseEntity<?> createSubProfileId(@PathVariable String subscriberGroupId, @PathVariable String subscriberProfileId, @PathVariable String sliceId) {
        LOG.info("Received request for creating sub group with ID "+subscriberGroupId+ " The sub profile Id is "+subscriberProfileId+ " and the slice id is "+sliceId);
        String response = subscriberService.createSubscriberGroup(subscriberProfileId, subscriberGroupId, sliceId);

        if(response==null)
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
