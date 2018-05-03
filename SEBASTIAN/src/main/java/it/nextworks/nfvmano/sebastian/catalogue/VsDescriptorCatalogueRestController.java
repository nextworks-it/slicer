package it.nextworks.nfvmano.sebastian.catalogue;

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
	
	public VsDescriptorCatalogueRestController() { } 
	
	@RequestMapping(value = "/vsdescriptor", method = RequestMethod.POST)
	public ResponseEntity<?> createVsDescriptor(@RequestBody OnboardVsDescriptorRequest request) {
		log.debug("Received request to create a VS descriptor.");
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
	public ResponseEntity<?> getAllVsDescriptors() {
		log.debug("Received request to retrieve all the VS descriptors.");
		try {
			//TODO: this is to be fixed when we will support the authentication. At the moment it returns all the VSDs, independently on the tenant.
			QueryVsDescriptorResponse response = vsDescriptorCatalogueService.queryVsDescriptor(new GeneralizedQueryRequest(Utilities.buildVsDescriptorFilter(adminTenant), null)); 
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
	public ResponseEntity<?> getVsDescriptor(@PathVariable String vsdId) {
		log.debug("Received request to retrieve VS descriptor with ID " + vsdId);
		try {
			//TODO: this is to be fixed when we will support the authentication. At the moment it returns all the VSDs, independently on the tenant.
			QueryVsDescriptorResponse response = vsDescriptorCatalogueService.queryVsDescriptor(new GeneralizedQueryRequest(Utilities.buildVsDescriptorFilter(vsdId, adminTenant), null)); 
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
	public ResponseEntity<?> deleteVsDescriptor(@PathVariable String vsdId) {
		log.debug("Received request to delete VS descriptor with ID " + vsdId);
		try {
			//TODO: this is to be fixed when we will support the authentication. At the moment it returns all the VSDs, independently on the tenant.
			vsDescriptorCatalogueService.deleteVsDescriptor(vsdId, adminTenant);
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
