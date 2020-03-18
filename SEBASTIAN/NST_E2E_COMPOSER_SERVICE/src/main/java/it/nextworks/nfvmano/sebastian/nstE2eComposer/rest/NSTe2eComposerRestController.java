package it.nextworks.nfvmano.sebastian.nstE2eComposer.rest;

import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
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
    public ResponseEntity<?> advertiseNST(@RequestBody NST nst, Authentication auth,  HttpServletRequest request) {


        String user = getUserFromAuth(auth);
        //Admin CANNOT advertise nst.
        if (user.equals(adminTenant)) {
            log.warn("Request refused as tenant {} is admin.", user);
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        String ipAddress = request.getRemoteAddr();
        try {
        bucketService.bucketizeNst(nst, ipAddress);
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
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/nstAdvertising/{nstId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeNSTadvertise(@PathVariable String nstId, Authentication auth,  HttpServletRequest request) {
        String user = getUserFromAuth(auth);
        //Admin CANNOT advertise nst.
        if (user.equals(adminTenant)) {
            log.warn("Request refused as tenant {} is admin.", user);
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        String ipAddress = request.getRemoteAddr();
        try {
            bucketService.removeFromBucket(nstId, ipAddress);
           return new ResponseEntity<String>("Advertised NST with id "+nstId+" correctly removed.", HttpStatus.OK);
        }
        catch (NotExistingEntityException e) {
            log.error("NST with UUID "+nstId+" advertised by "+ipAddress+" not found.");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        catch (Exception e) {
            log.error("Internal exception");
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
