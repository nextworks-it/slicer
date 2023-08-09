package it.nextworks.nfvmano.catalogue.nbi.mec0102.appdmanagement;

import it.nextworks.nfvmano.catalogue.engine.AppdManagementService;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd.Appd;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages.OnboardAppPackageRequest;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages.OnboardAppPackageResponse;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages.QueryOnBoadedAppPkgInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@Controller
public class AppdApiController {

    private static final Logger log = LoggerFactory.getLogger(AppdApiController.class);

    @Autowired
    AppdManagementService appdManagement;

    @Value("${catalogue.default.project:admin}")
    private String defaultProject;

    public AppdApiController() {	}

    @RequestMapping(value = "/appd/query", method = RequestMethod.POST)
    public ResponseEntity<?> queryApplicationPackage(@RequestParam(required = false) String project,
                                                     @RequestBody GeneralizedQueryRequest request,
                                                     @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        log.debug("Received query Application Package request");
        try {
            QueryOnBoadedAppPkgInfoResponse response = appdManagement.queryApplicationPackage(request, project);
            return new ResponseEntity<QueryOnBoadedAppPkgInfoResponse>(response, HttpStatus.OK);
        } catch (MalformattedElementException e) {
            log.error("Malformatted request: " + e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotExistingEntityException e) {
            log.error("Not existing entities: " + e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MethodNotImplementedException e) {
            log.error("Method not implemented. " + e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotAuthorizedOperationException e) {
            log.error("Forbidden. " + e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /*
    @RequestMapping(value = "/appd/subscription", method = RequestMethod.POST)
    public ResponseEntity<?> subscribe(@RequestBody SubscribeRequest request) {
        log.debug("Received appd subscribe request");
        try {
            String response = appdManagement.subscribeMecAppPackageInfo(request, new AppdManagementRestConsumer(request.getCallbackUri(), null));
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        } catch (MethodNotImplementedException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FailedOperationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MalformattedElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/appd/subscription/{subscriptionId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> unsubscribe(@PathVariable String subscriptionId) {
        log.debug("Received appd unsubscribe request for subscription " + subscriptionId);
        try {
            appdManagement.unsubscribeMecAppPackageInfo(subscriptionId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MethodNotImplementedException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotExistingEntityException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MalformattedElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    */

    @RequestMapping(value = "/appd", method = RequestMethod.POST)
    public ResponseEntity<?> onboardAppPackage(@RequestParam(required = false) String project,
                                               @RequestBody OnboardAppPackageRequest request,
                                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        log.debug("Received on board App package request");
        try {
            OnboardAppPackageResponse response = appdManagement.onboardAppPackage(request, project);
            return new ResponseEntity<OnboardAppPackageResponse>(response, HttpStatus.CREATED);
        } catch (MethodNotImplementedException | FailedOperationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (AlreadyExistingEntityException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (MalformattedElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotAuthorizedOperationException e) {
            log.error("Forbidden. " + e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/appd/{appPackageId}/enable", method = RequestMethod.PUT)
    public ResponseEntity<?> enableAppPackage(@RequestParam(required = false) String project,
                                              @PathVariable String appPackageId,
                                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        log.debug("Received request to enable App package " + appPackageId);
        try {
            appdManagement.enableAppPackage(appPackageId, project);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MethodNotImplementedException | FailedOperationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotExistingEntityException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MalformattedElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotAuthorizedOperationException e) {
            log.error("Forbidden. " + e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/appd/{appPackageId}/disable", method = RequestMethod.PUT)
    public ResponseEntity<?> disableAppPackage(@RequestParam(required = false) String project,
                                               @PathVariable String appPackageId,
                                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        log.debug("Received request to disable App package " + appPackageId);
        try {
            appdManagement.disableAppPackage(appPackageId, project);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MethodNotImplementedException | FailedOperationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotExistingEntityException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MalformattedElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotAuthorizedOperationException e) {
            log.error("Forbidden. " + e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/appd/{appPackageId}/delete", method = RequestMethod.PUT)
    public ResponseEntity<?> deleteAppPackage(@RequestParam(required = false) String project,
                                              @PathVariable String appPackageId,
                                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        log.debug("Received request to delete App package " + appPackageId);
        try {
            appdManagement.deleteAppPackage(appPackageId, project, false);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MethodNotImplementedException | FailedOperationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotExistingEntityException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MalformattedElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotAuthorizedOperationException e) {
            log.error("Forbidden. " + e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/appd/{nsdInfoId}", method = RequestMethod.GET)
    public ResponseEntity<?> associatedAppd(@PathVariable String nsdInfoId,
                                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        log.debug("Received request to get Appd associated to Ns Info Resource with Id " + nsdInfoId);
        try {
            List<Appd> appdList = appdManagement.getAssociatedAppD(UUID.fromString(nsdInfoId));
            return new ResponseEntity<List<Appd>>(appdList, HttpStatus.OK);
        } catch (NotExistingEntityException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
