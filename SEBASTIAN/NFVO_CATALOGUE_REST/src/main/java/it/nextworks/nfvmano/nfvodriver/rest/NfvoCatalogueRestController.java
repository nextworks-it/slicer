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
package it.nextworks.nfvmano.nfvodriver.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.nextworks.nfvmano.libs.ifasol.catalogues.interfaces.messages.*;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



/**
 * This class implements the REST APIs for the NSD, VNFD queries at the VS level
 * The internal implementation of the service is implemented through
 * the NfvoCatalogueService and the specific drivers.
 * 
 * @author nextworks
 *
 */
@Api(tags = "NSD and VNFD query API")
@RestController
@CrossOrigin
@RequestMapping("/nfvo")
public class NfvoCatalogueRestController {

	private static final Logger log = LoggerFactory.getLogger(NfvoCatalogueRestController.class);
	
	@Autowired
	NfvoCatalogueService nfvoCatalogueService;
	
	public NfvoCatalogueRestController() { }


	@ApiOperation(value = "Get the available NSDs")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "", response = QueryNsdResponse.class),
			//@ApiResponse(code = 400, message = "The supplied element contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 404, message = "The element with the supplied id was not found", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@RequestMapping(value = "/queryNsd", method = RequestMethod.POST)
	public ResponseEntity<?> queryNsd( @RequestBody GeneralizedQueryRequest request, Authentication auth) {
		log.debug("Received query NSD request");
		try {
			QueryNsdResponse response = nfvoCatalogueService.queryNsd(request);
			return new ResponseEntity<QueryNsdResponse>(response, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("Not existing entities: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MethodNotImplementedException e) {
			log.error("Method not implemented. " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FailedOperationException e) {
			log.error("Operation failed: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@ApiOperation(value = "Get the available VNFDs")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "", response = QueryOnBoardedVnfPkgInfoResponse.class),
			//@ApiResponse(code = 400, message = "The supplied element contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 404, message = "The element with the supplied id was not found", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})

	@RequestMapping(value = "/queryVnfp", method = RequestMethod.POST)
	public ResponseEntity<?> queryVnfPackage(  @RequestBody GeneralizedQueryRequest request, Authentication auth) {
		log.debug("Received query VNF package request");
		try {
			request.isValid();
		} catch (MalformattedElementException e) {
			log.error("Malformatted request: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		try {
			QueryOnBoardedVnfPkgInfoResponse response = nfvoCatalogueService.queryVnfPackageInfo(request);
			return new ResponseEntity<QueryOnBoardedVnfPkgInfoResponse>(response, HttpStatus.OK);
		} catch (MethodNotImplementedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotExistingEntityException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}



}
