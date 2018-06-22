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
package it.nextworks.nfvmano.sebastian.catalogue;

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

import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.catalogue.messages.OnboardVsDescriptorRequest;
import it.nextworks.nfvmano.sebastian.catalogue.messages.QueryVsDescriptorResponse;
import it.nextworks.nfvmano.sebastian.common.Utilities;

@RestController
@CrossOrigin
@RequestMapping("/vs/catalogue")
public class VsDescriptorCatalogueRestController {

	private static final Logger log = LoggerFactory.getLogger(VsDescriptorCatalogueRestController.class);
	
	@Autowired
	private VsDescriptorCatalogueService vsDescriptorCatalogueService;
	
	@Value("${sebastian.admin}")
	private String adminTenant;

	private static String getUserFromAuth(Authentication auth) {
		Object principal = auth.getPrincipal();
		if (!UserDetails.class.isAssignableFrom(principal.getClass())) {
			throw new IllegalArgumentException("Auth.getPrincipal() does not implement UserDetails");
		}
		return ((UserDetails) principal).getUsername();
	}
	
	public VsDescriptorCatalogueRestController() { } 
	
	@RequestMapping(value = "/vsdescriptor", method = RequestMethod.POST)
	public ResponseEntity<?> createVsDescriptor(@RequestBody OnboardVsDescriptorRequest request, Authentication auth) {
		log.debug("Received request to create a VS descriptor.");
		String user = getUserFromAuth(auth);
		if (!request.getTenantId().equals(user)) {
			return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		try {
			String vsDescriptorId = vsDescriptorCatalogueService.onBoardVsDescriptor(request);
			return new ResponseEntity<String>(vsDescriptorId, HttpStatus.CREATED);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (AlreadyExistingEntityException e) {
			log.error("VS Blueprint already existing");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/vsdescriptor", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVsDescriptors(Authentication auth) {
		log.debug("Received request to retrieve all the VS descriptors.");
		try {
			String user = getUserFromAuth(auth);
			QueryVsDescriptorResponse response = vsDescriptorCatalogueService.queryVsDescriptor(
					new GeneralizedQueryRequest(Utilities.buildTenantFilter(user), null)
			);
			return new ResponseEntity<List<VsDescriptor>>(response.getVsDescriptors(), HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS Blueprints not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/vsdescriptor/{vsdId}", method = RequestMethod.GET)
	public ResponseEntity<?> getVsDescriptor(@PathVariable String vsdId, Authentication auth) {
		log.debug("Received request to retrieve VS descriptor with ID " + vsdId);
		try {
			String user = getUserFromAuth(auth);
			QueryVsDescriptorResponse response = vsDescriptorCatalogueService.queryVsDescriptor(
					new GeneralizedQueryRequest(
							Utilities.buildVsDescriptorFilter(vsdId, user),
							null
					)
			);
			return new ResponseEntity<VsDescriptor>(response.getVsDescriptors().get(0), HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS Blueprints not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/vsdescriptor/{vsdId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteVsDescriptor(@PathVariable String vsdId, Authentication auth) {
		log.debug("Received request to delete VS descriptor with ID " + vsdId);
		try {
			String user = getUserFromAuth(auth);
			vsDescriptorCatalogueService.deleteVsDescriptor(vsdId, user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Malformatted request");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotExistingEntityException e) {
			log.error("VS Blueprints not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Internal exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
