package it.nextworks.nfvmano.sebastian.arbitrator;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;


import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/arbitrator")
public class ArbitratorAlgorithmRestController {

	private static final Logger log = LoggerFactory.getLogger(ArbitratorAlgorithmRestController.class);
	
	@Autowired
	private ArbitratorAlgorithmService algorithm;
	
	public ArbitratorAlgorithmRestController() {	}


	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", examples = @Example(@ExampleProperty(mediaType = "application/json",
					value = "{\"requestId\": \"operationId\"}")))})

	@RequestMapping(value = "/computeArbitration", method = RequestMethod.POST)

	public ResponseEntity<?> computeArbitration(@RequestBody List<ArbitratorRequest> request) {
		log.debug("Received request for Arbitration computation");
		try {


			Map<String, String> operationIds = algorithm.computeArbitratorSolution(request);
			String descriptorName  = request.get(0).getVsd().getName();
			algorithm.sendResponse(operationIds, descriptorName);
			log.debug("Continuing execution");
			return new ResponseEntity<>(operationIds, HttpStatus.CREATED);

		} catch (NotExistingEntityException e) {
			log.error("Error. Elements in the request not found." + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (FailedOperationException e) {
			log.error("Error. Solution not found. " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Error. Generic exception. "+ e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
