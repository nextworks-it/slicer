package it.nextworks.nfvmano.sebastian.catalogue;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.nextworks.nfvmano.libs.common.elements.Filter;
import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsBlueprintInfo;
import it.nextworks.nfvmano.sebastian.catalogue.messages.OnBoardVsBlueprintRequest;
import it.nextworks.nfvmano.sebastian.catalogue.messages.QueryVsBlueprintResponse;
import it.nextworks.nfvmano.sebastian.common.Utilities;

@RestController
@CrossOrigin
@RequestMapping("/vs/catalogue")
public class VsBlueprintCatalogueRestController {
	
	private static final Logger log = LoggerFactory.getLogger(VsBlueprintCatalogueRestController.class);
	
	@Autowired
	private VsBlueprintCatalogueService vsBlueprintCatalogueService;

	public VsBlueprintCatalogueRestController() { }
	
	@RequestMapping(value = "/vsblueprint", method = RequestMethod.POST)
	public ResponseEntity<?> createVsBlueprint(@RequestBody OnBoardVsBlueprintRequest request) {
		log.debug("Received request to create a VS blueprint.");
		try {
			String vsBlueprintId = vsBlueprintCatalogueService.onBoardVsBlueprint(request);
			return new ResponseEntity<String>(vsBlueprintId, HttpStatus.CREATED);
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
	
	@RequestMapping(value = "/vsblueprint", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVsBlueprints() {
		log.debug("Received request to retrieve all the VS blueprints.");
		try {
			QueryVsBlueprintResponse response = vsBlueprintCatalogueService.queryVsBlueprint(new GeneralizedQueryRequest(new Filter(), null)); 
			return new ResponseEntity<List<VsBlueprintInfo>>(response.getVsBlueprintInfo(), HttpStatus.CREATED);
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
	
	@RequestMapping(value = "/vsblueprint/{vsbId}", method = RequestMethod.GET)
	public ResponseEntity<?> getVsBlueprint(@PathVariable String vsbId) {
		log.debug("Received request to retrieve VS blueprint with ID " + vsbId);
		try {
			QueryVsBlueprintResponse response = vsBlueprintCatalogueService.queryVsBlueprint(new GeneralizedQueryRequest(Utilities.buildVsBlueprintFilter(vsbId), null)); 
			return new ResponseEntity<VsBlueprintInfo>(response.getVsBlueprintInfo().get(0), HttpStatus.OK);
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
	
	@RequestMapping(value = "/vsblueprint/{vsbId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteVsBlueprint(@PathVariable String vsbId) {
		log.debug("Received request to delete VS blueprint with ID " + vsbId);
		try {
			vsBlueprintCatalogueService.deleteVsBlueprint(vsbId); 
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
