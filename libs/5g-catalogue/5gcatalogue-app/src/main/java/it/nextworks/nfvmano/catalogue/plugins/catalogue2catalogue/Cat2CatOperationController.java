package it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue;

import io.swagger.annotations.ApiParam;
import it.nextworks.nfvmano.catalogue.common.Utilities;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.ProblemDetails;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/catalogue/cat2catOperation")
public class Cat2CatOperationController {

    private static final Logger log = LoggerFactory.getLogger(Cat2CatManagementController.class);

    @Autowired
    Cat2CatOperationService cat2CatOperationService;

    public Cat2CatOperationController() {
    }

    @RequestMapping(value = "/exportNsd/{nsdInfoId}", method = RequestMethod.POST)
    public ResponseEntity<?> exportNsd(@ApiParam(value = "", required = true) @PathVariable("nsdInfoId") String nsdInfoId, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        log.debug("Received request for exporting NSD with nsdInfoId {} to public 5G Catalogues", nsdInfoId);

        try {
            cat2CatOperationService.exportNsd(nsdInfoId);
        } catch (FailedOperationException e) {
            log.error("Failure while exporting NSD: " + e.getMessage());
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failure while exporting NSD"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Export NSD request successfully submitted", HttpStatus.OK);
    }

    @RequestMapping(value = "/exportPnfd/{pnfdInfoId}", method = RequestMethod.POST)
    public ResponseEntity<?> exportPnfd(@ApiParam(value = "", required = true) @PathVariable("pnfdInfoId") String pnfdInfoId, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        log.debug("Received request for exporting PNFD with pnfdInfoId {} to public 5G Catalogues", pnfdInfoId);

        try {
            cat2CatOperationService.exportPnfd(pnfdInfoId);
        } catch (FailedOperationException e) {
            log.error("Failure while exporting PNFD info: " + e.getMessage());
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failure while exporting PNFD"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Export PNFD request successfully submitted", HttpStatus.OK);
    }

    @RequestMapping(value = "/exportVnfPkg/{vnfPkgInfoId}", method = RequestMethod.POST)
    public ResponseEntity<?> exportVnfPkg(@ApiParam(value = "", required = true) @PathVariable("vnfPkgInfoId") String vnfPkgInfoId, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        log.debug("Received request for exporting VNF Pkg with vnfPkgInfoId {} to public 5G Catalogues", vnfPkgInfoId);

        try {
            cat2CatOperationService.exportVnfPkg(vnfPkgInfoId);
        } catch (FailedOperationException e) {
            log.error("Failure while exporting VNF Pkg info: " + e.getMessage());
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failure while exporting VNF Pkg"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Export VNF Pkg request successfully submitted", HttpStatus.OK);
    }
}
