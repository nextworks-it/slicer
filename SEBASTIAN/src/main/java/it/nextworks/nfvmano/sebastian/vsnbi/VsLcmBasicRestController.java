package it.nextworks.nfvmano.sebastian.vsnbi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.ModifyVsRequest;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.QueryVsResponse;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.TerminateVsRequest;

@RestController
@CrossOrigin
@RequestMapping("/vs/basic/vslcm")
public class VsLcmBasicRestController {

	private static final Logger log = LoggerFactory.getLogger(VsLcmBasicRestController.class);
	
	@Autowired
	private VsLcmService vsLcmService;
	
	@Value("${sebastian.admin}")
	private String adminTenant;
	
	public VsLcmBasicRestController() {	}
	
	@RequestMapping(value = "/vs", method = RequestMethod.POST)
	public ResponseEntity<?> instantiateVs(@RequestBody InstantiateVsRequest request) {
		log.debug("Received request to instantiate a new Vertical Service.");
		try {
			String vsId = vsLcmService.instantiateVs(request);
			return new ResponseEntity<String>(vsId, HttpStatus.CREATED);
		} catch (NotExistingEntityException e) {
			log.error("VS instantiation failed due to missing elements in DB.");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instantiation failed due to missing permission.");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (MalformattedElementException e) {
			log.error("VS instantiation failed due to bad-formatted request.");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("VS instantiation failed due to internal errors.");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/vs", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVsInstances() {
		log.debug("Received request to retrieve all the VS instances.");
		try {
			//TODO: this is to be fixed when we will support the authentication. At the moment it returns all the VSIs, independently on the tenant.
			QueryVsResponse response = vsLcmService.queryVs(new GeneralizedQueryRequest(Utilities.buildTenantFilter(adminTenant), null)); 
			return new ResponseEntity<QueryVsResponse>(response, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instance not visible for the given tenant.");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/vs/{vsiId}", method = RequestMethod.GET)
	public ResponseEntity<?> getVsInstance(@PathVariable String vsiId) {
		log.debug("Received request to retrieve VS instance with ID " + vsiId);
		try {
			//TODO: this is to be fixed when we will support the authentication. At the moment it returns all the VSIs, independently on the tenant.
			QueryVsResponse response = vsLcmService.queryVs(new GeneralizedQueryRequest(Utilities.buildVsInstanceFilter(vsiId, adminTenant), null)); 
			return new ResponseEntity<QueryVsResponse>(response, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instance not visible for the given tenant.");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/vsId", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVsInstancesId() {
		log.debug("Received request to retrieve all the VS instances ID.");
		try {
			//TODO: this is to be fixed when we will support the authentication. At the moment it returns all the VSIs, independently on the tenant.
			List<String> response = vsLcmService.queryAllVsIds(new GeneralizedQueryRequest(Utilities.buildTenantFilter(adminTenant), null)); 
			return new ResponseEntity<List<String>>(response, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/vs/{vsiId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> terminateVsInstance(@PathVariable String vsiId) {
		log.debug("Received request to terminate VS instance with ID " + vsiId);
		try {
			//TODO: this is to be fixed when we will support the authentication. At the moment it returns all the VSIs, independently on the tenant.
			vsLcmService.terminateVs(new TerminateVsRequest(vsiId, adminTenant)); 
			return new ResponseEntity<QueryVsResponse>(HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instance not visible for the given tenant.");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/vs/{vsiId}", method = RequestMethod.PUT)
	public ResponseEntity<?> modifyVsInstance(@PathVariable String vsiId, @RequestBody ModifyVsRequest request) {
		log.debug("Received request to modify VS instance with ID " + vsiId);
		try {
			vsLcmService.modifyVs(request); 
			return new ResponseEntity<QueryVsResponse>(HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instance not visible for the given tenant.");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
}
