package it.nextworks.nfvmano.sebastian.nstE2eComposer.rest;

import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.nstE2Ecomposer.messages.NstAdvertisementRemoveRequest;
import it.nextworks.nfvmano.sebastian.nstE2Ecomposer.messages.NstAdvertisementRequest;
import it.nextworks.nfvmano.sebastian.nstE2eComposer.service.BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/vs/catalogue/")
public class NSTe2eComposerRestController {
    private static final Logger log = LoggerFactory.getLogger(NSTe2eComposerRestController.class);

    @Autowired
    BucketService bucketService;

    @Autowired
    private NsTemplateCatalogueService nsTemplateCatalogueService;

    @Value("${catalogue.admin}")
    private String adminTenant;

    public NSTe2eComposerRestController(){    }


    private static String getUserFromAuth(Authentication auth) {
        Object principal = auth.getPrincipal();
        if (!UserDetails.class.isAssignableFrom(principal.getClass())) {
            throw new IllegalArgumentException("Auth.getPrincipal() does not implement UserDetails");
        }
        return ((UserDetails) principal).getUsername();
    }

    @RequestMapping(value = "/nstAdvertising", method = RequestMethod.POST)
    public ResponseEntity<?> advertiseNst(@RequestBody NstAdvertisementRequest nstAdvertisementRequest, Authentication auth, HttpServletRequest request) {

        String user = getUserFromAuth(auth);

        if (user.equals(adminTenant)) { //Admin CANNOT advertise nst.
            log.warn("Request refused as tenant {} is admin.", user);
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        String ipAddress = request.getRemoteAddr();
        try {
            bucketService.bucketizeNst(nstAdvertisementRequest, ipAddress);
            return new ResponseEntity<String>("NST correctly advertised.", HttpStatus.CREATED);
        }
        catch (MalformattedElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (FailedOperationException e) {
            return new ResponseEntity<String>("NST performance requirements not satisfied.", HttpStatus.BAD_REQUEST);
        }
        catch (AlreadyExistingEntityException e) {
            return new ResponseEntity<String>("NST already present.", HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            log.error("Internal exception");
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/nstAdvertising/{nstUuid}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAdvertisedNst(@PathVariable String nstUuid, @RequestBody List<String> kpiList, Authentication auth) {

        /*String user = getUserFromAuth(auth);

        if (user.equals(adminTenant)) { //Admin CANNOT advertise nst.
            log.warn("Request refused as tenant {} is admin.", user);
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }*/
        try {
            bucketService.updateKpi(nstUuid, kpiList);
            return new ResponseEntity<String>("KPI of NST with UUID "+nstUuid+" correctly updated.", HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.error("Internal exception");
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/nstAdvertising", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeNSTadvertise(@RequestBody NstAdvertisementRemoveRequest nstAdvertisementRemoveRequest, Authentication auth, HttpServletRequest request) {
        String user = getUserFromAuth(auth);

        if (user.equals(adminTenant)) {//Admin CANNOT advertise nst.
            log.warn("Request refused as tenant {} is admin.", user);
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        String ipAddress = request.getRemoteAddr();
        try {
            bucketService.removeFromBucket(nstAdvertisementRemoveRequest, ipAddress);
           return new ResponseEntity<String>("Advertised NST with id "+nstAdvertisementRemoveRequest.getNstId()+" correctly removed.", HttpStatus.OK);
        }
        catch (NotExistingEntityException e) {
            log.error("NST with UUID "+nstAdvertisementRemoveRequest.getNstId()+" advertised by "+nstAdvertisementRemoveRequest.getDomainName()+" not found.");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (MalformattedElementException e) {
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error("Internal exception");
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
