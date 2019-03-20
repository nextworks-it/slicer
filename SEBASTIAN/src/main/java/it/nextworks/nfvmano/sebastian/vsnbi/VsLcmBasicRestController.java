/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.sebastian.vsnbi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
import it.nextworks.nfvmano.sebastian.vsnbi.messages.PurgeVsRequest;
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

	private static String getUserFromAuth(Authentication auth) {
		Object principal = auth.getPrincipal();
		if (!UserDetails.class.isAssignableFrom(principal.getClass())) {
			throw new IllegalArgumentException("Auth.getPrincipal() does not implement UserDetails");
		}
		return ((UserDetails) principal).getUsername();
	}
	
	@RequestMapping(value = "/vs", method = RequestMethod.POST)
	public ResponseEntity<?> instantiateVs(@RequestBody InstantiateVsRequest request, Authentication auth) {
		log.debug("Received request to instantiate a new Vertical Service.");
		try {
			String username = getUserFromAuth(auth);
			if (!request.getTenantId().equals(username)) {
				return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
			}
			String vsId = vsLcmService.instantiateVs(request);
			return new ResponseEntity<>(vsId, HttpStatus.CREATED);
		} catch (NotExistingEntityException e) {
			log.error("VS instantiation failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instantiation failed due to missing permission.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (MalformattedElementException e) {
			log.error("VS instantiation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("VS instantiation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/vs/{vsiId}", method = RequestMethod.GET)
	public ResponseEntity<?> getVsInstance(@PathVariable String vsiId, Authentication auth) {
		log.debug("Received request to retrieve VS instance with ID " + vsiId);
		try {
			String user = getUserFromAuth(auth);
			QueryVsResponse response = vsLcmService.queryVs(new GeneralizedQueryRequest(Utilities.buildVsInstanceFilter(vsiId, user), null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instance not visible for the given tenant.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Internal exception: {}", e.getClass().getSimpleName());
			log.debug("Details: ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/vsId", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVsInstancesId(Authentication auth) {
		log.debug("Received request to retrieve all the VS instances ID.");
		try {
			String user = getUserFromAuth(auth);
			List<String> response = vsLcmService.queryAllVsIds(new GeneralizedQueryRequest(Utilities.buildTenantFilter(user), null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/vs/{vsiId}/terminate", method = RequestMethod.POST)
	public ResponseEntity<?> terminateVsInstance(@PathVariable String vsiId, Authentication auth) {
		log.debug("Received request to terminate VS instance with ID " + vsiId);
		try {
			String user = getUserFromAuth(auth);
			vsLcmService.terminateVs(new TerminateVsRequest(vsiId, user));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instance not visible for the given tenant.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/vs/{vsiId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> purgeVsInstance(@PathVariable String vsiId, Authentication auth) {
		log.debug("Received request to purge VS instance with ID " + vsiId);
		try {
			String user = getUserFromAuth(auth);
			vsLcmService.purgeVs(new PurgeVsRequest(vsiId, user));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("Operation not permitted: " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/vs/{vsiId}", method = RequestMethod.PUT)
	public ResponseEntity<?> modifyVsInstance(@PathVariable String vsiId, @RequestBody ModifyVsRequest request, Authentication auth) {
		log.debug("Received request to modify VS instance with ID " + vsiId);
		try {
			String user = getUserFromAuth(auth);
			if (!request.getTenantId().equals(user)) {
				return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
			}
			vsLcmService.modifyVs(request); 
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instance not visible for the given tenant.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
}
