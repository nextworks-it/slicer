/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.vsfm.nbi;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.nextworks.nfvmano.sebastian.admin.MgmtCatalogueUtilities;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.repo.VSICatalogueUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.messages.InstantiateVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.ModifyVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.PurgeVsRequest;
import it.nextworks.nfvmano.sebastian.vsfm.messages.QueryVsResponse;
import it.nextworks.nfvmano.sebastian.vsfm.messages.TerminateVsRequest;
@Api(tags = "Vertical Service LCM Management API")
@RestController
@CrossOrigin
@RequestMapping("/vs/basic/vslcm")
public class VsLcmBasicRestController {

	private static final Logger log = LoggerFactory.getLogger(VsLcmBasicRestController.class);
	
	@Autowired
	private VsLcmService vsLcmService;
	
	@Value("${sebastian.admin}")
	private String adminTenant;
	
	public VsLcmBasicRestController() {
	}

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
			String vsId = vsLcmService.instantiateVs(request, null);
			return new ResponseEntity<>(vsId, HttpStatus.CREATED);
		} catch (NotExistingEntityException e) {
			log.error("VS instantiation failed due to missing elements in DB.",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instantiation failed due to missing permission.",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (MalformattedElementException e) {
			log.error("VS instantiation failed due to bad-formatted request.",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("VS instantiation failed due to internal errors.",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get the Vertical Service Instance with the specified ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The Vertical Service Instance", response = VerticalServiceInstance.class),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)

	@RequestMapping(value = "/vs/{vsiId}", method = RequestMethod.GET)
	public ResponseEntity<?> getVsInstance(@PathVariable String vsiId, Authentication auth) {
		log.debug("Received request to retrieve VS instance with ID " + vsiId);
		try {
			String user = getUserFromAuth(auth);
			QueryVsResponse response = vsLcmService.queryVs(new GeneralizedQueryRequest(VSICatalogueUtilities.buildVsInstanceFilter(vsiId, user), null), null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotPermittedOperationException e) {
			log.error("VS instance not visible for the given tenant.",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Internal exception: {}", e.getClass().getSimpleName());
			log.debug("Details: ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@ApiOperation(value = "Get the list of Vertical Service Instance IDs")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The Vertical Service Instance", response = String.class, responseContainer = "Set"),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/vsId", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVsInstancesId(Authentication auth) {
		log.debug("Received request to retrieve all the VS instances ID.");
		try {
			String user = getUserFromAuth(auth);
			List<String> response = vsLcmService.queryAllVsIds(new GeneralizedQueryRequest(MgmtCatalogueUtilities.buildTenantFilter(user), null), null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS instance not found",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Internal exception",e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/vs/{vsiId}/terminate", method = RequestMethod.POST)
	public ResponseEntity<?> terminateVsInstance(@PathVariable String vsiId, Authentication auth) {
		log.debug("Received request to terminate VS instance with ID " + vsiId);
		try {
			String user = getUserFromAuth(auth);
			vsLcmService.terminateVs(new TerminateVsRequest(vsiId, user), null);
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
			vsLcmService.purgeVs(new PurgeVsRequest(vsiId, user), null);
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
			vsLcmService.modifyVs(request, null);
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



	@ApiOperation(value = "Get the list of Vertical Service Instance matching the specified filter")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The Vertical Service Instances matching the filter params", response = VerticalServiceInstance.class, responseContainer = "Set"),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/vsInstances", method = RequestMethod.POST)
	public ResponseEntity<?> getAllVsInstances( @RequestBody GeneralizedQueryRequest request, Authentication auth) {
		log.debug("Received request to retrieve all the VS instances ID.");
		try {
			String user = getUserFromAuth(auth);

			return new ResponseEntity<>(vsLcmService.queryAllVsInstances(request, null), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
}
