package it.nextworks.nfvmano.sebastian.nsmf.nbi;

import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiUuidRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/vs/basic/nslcm")
public class NsmfRestController {

	private static final Logger log = LoggerFactory.getLogger(NsmfRestController.class);

	@Autowired
	private NsLcmService nsLcmService;

	@Value("${sebastian.admin}")
	private String adminTenant;

	private static String getUserFromAuth(Authentication auth) {
		Object principal = auth.getPrincipal();
		if (!UserDetails.class.isAssignableFrom(principal.getClass())) {
			throw new IllegalArgumentException("Auth.getPrincipal() does not implement UserDetails");
		}
		return ((UserDetails) principal).getUsername();
	}

	public NsmfRestController() {
	}

	@RequestMapping(value = "/ns", method = RequestMethod.POST)
	public ResponseEntity<?> createNsId(@RequestBody CreateNsiUuidRequest request, Authentication auth) {
		log.debug("Received request to create a new network slice instance ID.");
		try {
			String tenantId = getUserFromAuth(auth);
			String nsiUuid = nsLcmService.createNetworkSliceIdentifier(request, tenantId);
			if(nsiUuid==null){
				return new ResponseEntity<>("The nst with UUID "+request.getNstUuid()+" does not satisfies the arbitration request.", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(nsiUuid, HttpStatus.CREATED);
		} catch (NotExistingEntityException e) {
			log.error("NS ID creation failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/ns/{nsiUuid}", method = RequestMethod.GET)
	public ResponseEntity<?> getNsInstance(@PathVariable String nsiUuid, Authentication auth) {
		log.debug("Received query for network slice instance with UUID " + nsiUuid);
		try {
			String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("NSI_ID", nsiUuid);
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<NetworkSliceInstance> nsis = nsLcmService.queryNetworkSliceInstance(query, tenantId);
			if (nsis.isEmpty()) {
				log.error("Network slice instance with UUID " + nsiUuid + " not found");
				return new ResponseEntity<>("Network slice instance with UUID " + nsiUuid + " not found", HttpStatus.BAD_REQUEST);
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

	@RequestMapping(value = "/ns", method = RequestMethod.GET)
	public ResponseEntity<?> getNsInstance(Authentication auth) {
		log.debug("Received query for all network slice instances.");
		try {
			String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<NetworkSliceInstance> nsis = nsLcmService.queryNetworkSliceInstance(query, tenantId);
			return new ResponseEntity<List<NetworkSliceInstance>>(nsis, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/ns/{nsiUuid}/action/instantiate", method = RequestMethod.PUT)
	public ResponseEntity<?> instantiateNsi(@PathVariable String nsiUuid, @RequestBody InstantiateNsiRequest request, Authentication auth) {
		log.debug("Received request to instantiate network slice " + nsiUuid);
		try {

			String tenantId = getUserFromAuth(auth);
			nsLcmService.instantiateNetworkSlice(request, tenantId);
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

	@RequestMapping(value = "/ns/{nsiUuid}/action/modify", method = RequestMethod.PUT)
	public ResponseEntity<?> modifyNsi(@PathVariable String nsiUuid, @RequestBody ModifyNsiRequest request, Authentication auth) {
		log.debug("Received request to modify network slice " + nsiUuid);
		try {
			String tenantId = getUserFromAuth(auth);
			nsLcmService.modifyNetworkSlice(request, tenantId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (NotExistingEntityException e) {
			log.error("NS modification failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS modification failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS modification failed due to missing permission or conflicting state.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS modification failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/ns/{nsiUuid}/action/terminate", method = RequestMethod.PUT)
	public ResponseEntity<?> terminateNsi(@PathVariable String nsiUuid, @RequestBody TerminateNsiRequest request, Authentication auth) {
		log.debug("Received request to terminate network slice " + nsiUuid);
		try {
			String tenantId = getUserFromAuth(auth);
			nsLcmService.terminateNetworkSliceInstance(request, tenantId);
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
}
