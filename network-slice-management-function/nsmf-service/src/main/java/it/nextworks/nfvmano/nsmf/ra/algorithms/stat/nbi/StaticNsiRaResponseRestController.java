package it.nextworks.nfvmano.nsmf.ra.algorithms.stat.nbi;

import it.nextworks.nfvmano.libs.vs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.query.elements.Filter;
import it.nextworks.nfvmano.libs.vs.common.query.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.StaticAlgorithmResponseManager;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.messages.OnboardStaticRaResponseRequest;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record.elements.StaticRaResponseRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticNsiRaResponseRestController {
    private static final Logger log = LoggerFactory.getLogger(StaticNsiRaResponseRestController.class);

    @Autowired
    private StaticAlgorithmResponseManager allocationService;

    public StaticNsiRaResponseRestController(){}

    @RequestMapping(value = "/staticNsiRa", method= RequestMethod.POST)
    public ResponseEntity<?> createStaticNsiRaResponse(@RequestBody OnboardStaticRaResponseRequest request) {
        log.debug("Received request to create new static nsi response");

        try{
            String staticNsiResponseId=allocationService.createStaticNsiRaResponse(request);
            return new ResponseEntity<>(staticNsiResponseId, HttpStatus.OK);
        } catch (AlreadyExistingEntityException e){
            log.error("Static NSI Response already exist");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e){
            log.debug("Internal error");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/staticNsiRa/{responseId}", method = RequestMethod.GET)
    public ResponseEntity<?> getStaticNsiRaResponse(@PathVariable String responseId){
        log.debug("Received request to get static NsiRA response with ID {}", responseId);
        try{
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("RESPONSE_ID", responseId);
            Filter filter = new Filter(parameters);
            GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
            StaticRaResponseRecord staticNsiRaResponse=allocationService.queryStaticNsiResponse(query).get(0);
            return new ResponseEntity<>(staticNsiRaResponse, HttpStatus.OK);
        } catch (NotExistingEntityException e){
            log.error("Static NsiRa response with ID {} does not exist", responseId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.error("Internal error");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/staticNsiRa", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStaticNsiRaResponses(){
        log.debug("Received request to get all static NsiRA responses");
        try{
            Map<String, String> parameters = new HashMap<String, String>();
            Filter filter = new Filter(parameters);
            GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
            List<StaticRaResponseRecord> staticNsiRaResponses=allocationService.queryStaticNsiResponse(query);
            return new ResponseEntity<>(staticNsiRaResponses, HttpStatus.OK);
        } catch (NotExistingEntityException e){
            log.error("No static NsiRa response found");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.error("Internal error");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/staticNsiRa/{responseId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStaticNsiRaResponse(@PathVariable String responseId){
        log.debug("Received request to delete static NsiRa response with ID {}", responseId);

        try{
            allocationService.deleteStaticNsiResponse(responseId);
            String response="Policy correctly deleted";
            return new ResponseEntity<String>(response, HttpStatus.OK);
        }  catch (NotExistingEntityException e){
            log.error("Static NsiRa response with ID {} does not exist", responseId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal error");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
