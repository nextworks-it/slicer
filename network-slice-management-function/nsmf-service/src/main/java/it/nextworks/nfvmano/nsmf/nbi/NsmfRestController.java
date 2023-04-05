package it.nextworks.nfvmano.nsmf.nbi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.ConfigurationOperation;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceSubnetInstance;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.NsmfNotificationMessage;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.configuration.UpdateConfigurationRequest;
import it.nextworks.nfvmano.nsmf.NsLcmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import it.nextworks.nfvmano.libs.vs.common.query.elements.Filter;
import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.query.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.*;


import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstance;
@Api(tags = "Network Slice LCM API")
@RestController
@CrossOrigin
@RequestMapping("/vs/basic/nslcm")
public class NsmfRestController {

	private static final Logger log = LoggerFactory.getLogger(NsmfRestController.class);
	
	@Autowired
	private NsLcmService nsLcmService;
	
	@Value("${sebastian.admin}")
	private String adminTenant;
	
	/*private static String getUserFromAuth(Authentication auth) {
		Object principal = auth.getPrincipal();
		if (!UserDetails.class.isAssignableFrom(principal.getClass())) {
			throw new IllegalArgumentException("Auth.getPrincipal() does not implement UserDetails");
		}
		return ((UserDetails) principal).getUsername();
	}*/
	
	public NsmfRestController() { }
	
	@RequestMapping(value = "/ns/nst", method = RequestMethod.POST)
	public ResponseEntity<?> createNsIdFromNst(@RequestBody CreateNsiIdRequest request, Authentication auth) {
		log.debug("Received request to create a new network slice instance ID.");
		try {
			//String tenantId =  getUserFromAuth(auth);
			UUID nsiId = nsLcmService.createNetworkSliceIdentifierFromNst(request, adminTenant);
			return new ResponseEntity<>(nsiId, HttpStatus.CREATED);	
		} catch (NotExistingEntityException e) {
			log.error("NS ID creation failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS ID creation failed due to missing permission.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/ns/nest", method = RequestMethod.POST)
	public ResponseEntity<?> createNsIdFromNest(@RequestBody CreateNsiIdFromNestRequest request, Authentication auth){
		log.debug("Received request to create a new network slice instance ID from NEST.");
		try {
			//String tenantId = getUserFromAuth(auth);
			UUID nsiId = nsLcmService.createNetworkSliceIdentifierFromNest(request, adminTenant);
			return new ResponseEntity<>(nsiId, HttpStatus.CREATED);
		} catch (NotExistingEntityException e) {
			log.error("NS ID creation failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS ID creation failed due to missing permission.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@ApiOperation(value = "Get the Network Slice Instance with the specified ID ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The Network Slice Instance.", response = NetworkSliceInstance.class),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/ns/{nsiId}", method = RequestMethod.GET)
	public ResponseEntity<?> getNsInstance(@PathVariable String nsiId, Authentication auth) {
		log.debug("Received query for network slice instance with ID " + nsiId);
		try {
			//String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("NSI_ID", nsiId);
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<NetworkSliceInstance> nsis = nsLcmService.queryNetworkSliceInstance(query,  adminTenant);
			if (nsis.isEmpty()) {
				log.error("Network slice instance with ID " + nsiId + " not found");
				return new ResponseEntity<>("Network slice instance with ID " + nsiId + " not found", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<NetworkSliceInstance>(nsis.get(0), HttpStatus.OK);	
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all the Network Slice Instances")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The list of Network Slice Instances.", response = NetworkSliceInstance.class, responseContainer = "Set"),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/ns", method = RequestMethod.GET)
	public ResponseEntity<?> getNsInstance(Authentication auth) {
		log.debug("Received query for all network slice instances.");
		try {
			//String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<NetworkSliceInstance> nsis = nsLcmService.queryNetworkSliceInstance(query,  adminTenant);
			return new ResponseEntity<List<NetworkSliceInstance>>(nsis, HttpStatus.OK);	
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@ApiOperation(value = "Get all the Network Slice Instances")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The list of Network Slice Instances.", response = NetworkSliceInstance.class, responseContainer = "Set"),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/nss", method = RequestMethod.GET)
	public ResponseEntity<?> getNssInstance(Authentication auth) {
		log.debug("Received query for all network slice subnet instances.");
		try {
			//String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<NetworkSliceSubnetInstance> nsis = nsLcmService.queryNetworkSliceSubnetInstance(query,  adminTenant);
			return new ResponseEntity<List<NetworkSliceSubnetInstance>>(nsis, HttpStatus.OK);
		} catch (MalformattedElementException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all the Network Slice Instance IDSs")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The list of Network Slice Instances IDS.", response = String.class, responseContainer = "Set"),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/networksliceids", method = RequestMethod.GET)
	public ResponseEntity<?> getNsInstanceIds(Authentication auth) {
		log.debug("Received query for all network slice instances.");
		try {
			//String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<String> nsis = nsLcmService.queryNetworkSliceInstance(query, adminTenant).stream()
					.map(nsInstance -> nsInstance.getNetworkSliceInstanceId().toString())
					.collect(Collectors.toList());

			return new ResponseEntity<List<String>>(nsis, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/operations", method = RequestMethod.GET)
	public ResponseEntity<?> geOperations(Authentication auth) {
		log.debug("Received query for all operations.");
		try {
			//String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<ConfigurationOperation> ops = nsLcmService.queryConfigurationOperation();

			return new ResponseEntity<List<ConfigurationOperation>>(ops, HttpStatus.OK);

		} catch (Exception e) {
			log.error("query configuration operations failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/ns/{nsiId}/action/instantiate", method = RequestMethod.PUT)
	public ResponseEntity<?> instantiateNsi(@PathVariable String nsiId, @RequestBody InstantiateNsiRequest request, Authentication auth) {
		log.debug("Received request to instantiate network slice " + nsiId);
		try {
			if(!nsiId.equals(request.getNsiId().toString()))
				throw new MalformattedElementException("NSI ID within path variable differs from request body ones");

			//String tenantId = getUserFromAuth(auth);
			nsLcmService.instantiateNetworkSlice(request, adminTenant);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);	
		} catch (NotExistingEntityException e) {
			log.error("NS instantiation failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS instantiation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS instantiation failed due to missing permission or conflicting state.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS instantiation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@RequestMapping(value = "/ns/{nsiId}/action/configure", method = RequestMethod.PUT)
	public ResponseEntity<?> configureNsi(@PathVariable String nsiId, @RequestBody UpdateConfigurationRequest request, Authentication auth) {
		log.debug("Received request to configure network slice " + nsiId);
		try {
			if(!nsiId.equals(request.getNsiId().toString()))
				throw new MalformattedElementException("NSI ID within path variable differs from request body ones");

			//String tenantId = getUserFromAuth(auth);
			UUID operationId= nsLcmService.configureNetworkSlice(request, adminTenant);
			return new ResponseEntity<>(operationId.toString(), HttpStatus.ACCEPTED);
		} catch (NotExistingEntityException e) {
			log.error("NS configuration failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS configuration failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS configuration failed due to missing permission or conflicting state.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS configuration failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	
	@RequestMapping(value = "/ns/{nsiId}/action/terminate", method = RequestMethod.PUT)
	public ResponseEntity<?> terminateNsi(@PathVariable String nsiId, @RequestBody TerminateNsiRequest request, Authentication auth) {
		log.debug("Received request to terminate network slice " + nsiId);
		try {
			if(!nsiId.equals(request.getNsiId().toString()))
				throw new MalformattedElementException("NSI ID within path differs from request body ones");
			//String tenantId = getUserFromAuth(auth);
			nsLcmService.terminateNetworkSliceInstance(request, adminTenant);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);	
		} catch (NotExistingEntityException e) {
			log.error("NS termination failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS termination failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS termination failed due to missing permission or conflicting state.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS termination failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@RequestMapping(value = "/nss/{nssiId}/notify", method = RequestMethod.POST)
	public ResponseEntity<?> notifyNssiStatusChange(@PathVariable String nssiId, @RequestBody NsmfNotificationMessage request, Authentication auth) {

		log.debug("Received request to update status of network sub slice " + nssiId);
		try {
			if (!nssiId.equals(request.getNssiId().toString()))
				throw new MalformattedElementException("NSSI ID within path differs from request body ones");
			nsLcmService.notifyNssStatusChange(request);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (NotExistingEntityException e) {
			log.error("NSS Status Change Notification failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NSS Status Change Notification failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			log.error("NS instantiation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
