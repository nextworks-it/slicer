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
package it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import it.nextworks.nfvmano.catalogue.common.Utilities;
import it.nextworks.nfvmano.catalogue.engine.VnfPackageManagementInterface;
import it.nextworks.nfvmano.catalogue.engine.elements.ContentType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.ProblemDetails;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.*;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-05T11:50:31.473+02:00")

@CrossOrigin
@Controller
public class VnfpkgmApiController implements VnfpkgmApi {

    private static final Logger log = LoggerFactory.getLogger(VnfpkgmApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    VnfPackageManagementInterface vnfPackageManagementInterface;

    @Value("${catalogue.default.project:admin}")
    private String defaultProject;

    @Autowired
    public VnfpkgmApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> createVNFPkgInfo(@RequestParam(required = false) String project,
                                              @ApiParam(value = "", required = true) @Valid @RequestBody CreateVnfPkgInfoRequest body,
                                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.debug("Processing REST request to create a VNF Pkg info");
            try {
                VnfPkgInfo vnfPkgInfo = vnfPackageManagementInterface.createVnfPkgInfo(body, project, false);
                return new ResponseEntity<VnfPkgInfo>(vnfPkgInfo, HttpStatus.CREATED);
            } catch (MalformattedElementException | FailedOperationException e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()), HttpStatus.BAD_REQUEST);
            } catch (NotPermittedOperationException e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()), HttpStatus.BAD_REQUEST);
            } catch (NotAuthorizedOperationException e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.FORBIDDEN.value(),
                        e.getMessage()), HttpStatus.FORBIDDEN);
            } catch (Exception e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.PRECONDITION_FAILED.value(),
                    "Accept header null or different from application/json"), HttpStatus.PRECONDITION_FAILED);
    }

    public ResponseEntity<?> getVNFPkgsInfo(@RequestParam(required = false) String project,
                                            @RequestParam(required = false) UUID vnfdId,
                                            @RequestParam(required = false) String extraData,
                                            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        String accept = request.getHeader("Accept");
        // TODO: process URI parameters for filters and attributes. At the moment it returns all the VNF Pkgs info
        if (accept != null && accept.contains("application/json")) {
            try {
                List<VnfPkgInfo> vnfPkgInfos = vnfPackageManagementInterface.getAllVnfPkgInfos(project, extraData, vnfdId);
                log.debug("VNF Pkg infos retrieved");
                return new ResponseEntity<List<VnfPkgInfo>>(vnfPkgInfos, HttpStatus.OK);
            } catch (NotPermittedOperationException | FailedOperationException e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()), HttpStatus.BAD_REQUEST);
            } catch (NotAuthorizedOperationException e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.FORBIDDEN.value(),
                        e.getMessage()), HttpStatus.FORBIDDEN);
            } catch (Exception e) {
                return new ResponseEntity<ProblemDetails>(
                        Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.PRECONDITION_FAILED.value(),
                    "Accept header null or different from application/json"), HttpStatus.PRECONDITION_FAILED);
    }

    public ResponseEntity<?> queryVNFPkgInfo(@RequestParam(required = false) String project,
                                             @ApiParam(value = "", required = true) @PathVariable("vnfPkgId") String vnfPkgId,
                                             @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        String accept = request.getHeader("Accept");
        log.debug("Processing REST request to retrieve VNF Pkg info " + vnfPkgId);
        if (accept != null && accept.contains("application/json")) {
            try {
                VnfPkgInfo vnfPkgInfo = vnfPackageManagementInterface.getVnfPkgInfo(vnfPkgId, project);
                log.debug("NSD info retrieved");
                return new ResponseEntity<VnfPkgInfo>(vnfPkgInfo, HttpStatus.OK);
            } catch (NotExistingEntityException e) {
                log.error("VNF Pkg info " + vnfPkgId + " not found");
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.NOT_FOUND.value(),
                        e.getMessage()), HttpStatus.NOT_FOUND);
            } catch (MalformattedElementException | FailedOperationException e) {
                log.error("VNF Pkg info " + vnfPkgId + " cannot be found: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(
                        Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(),
                                e.getMessage()),
                        HttpStatus.BAD_REQUEST);
            } catch (NotPermittedOperationException e) {
                return new ResponseEntity<ProblemDetails>(
                        Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(),
                                e.getMessage()),
                        HttpStatus.BAD_REQUEST);
            } catch (NotAuthorizedOperationException e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.FORBIDDEN.value(),
                        e.getMessage()), HttpStatus.FORBIDDEN);
            } catch (Exception e) {
                log.error("VNF Pkg info " + vnfPkgId + " cannot be retrieved: general internal error");
                return new ResponseEntity<ProblemDetails>(
                        Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                e.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.PRECONDITION_FAILED.value(),
                    "Accept header null or different from application/json"), HttpStatus.PRECONDITION_FAILED);
        }
    }

    public ResponseEntity<?> updateVNFPkgInfo(@RequestParam(required = false) String project,
                                              @ApiParam(value = "", required = true) @PathVariable("vnfPkgId") String vnfPkgId,
                                              @ApiParam(value = "", required = true) @Valid @RequestBody VnfPkgInfoModifications body,
                                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        String accept = request.getHeader("Accept");
        log.debug("Processing REST request for Updating VNF Pkg info " + vnfPkgId);
        if (body == null) {
            return new ResponseEntity<String>("Error message: Body is empty!", HttpStatus.BAD_REQUEST);
        }

        if (accept != null && accept.contains("application/json")) {
            try {
                VnfPkgInfoModifications vnfPkgInfoModifications = vnfPackageManagementInterface.updateVnfPkgInfo(body, vnfPkgId, project, false);
                return new ResponseEntity<VnfPkgInfoModifications>(vnfPkgInfoModifications, HttpStatus.OK);
            } catch (NotExistingEntityException e) {
                log.error("Impossible to update VNF Pkg info: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.NOT_FOUND.value(),
                        e.getMessage()), HttpStatus.NOT_FOUND);
            } catch (MalformattedElementException | FailedOperationException e) {
                log.error("Impossible to update VNF Pkg info: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()), HttpStatus.BAD_REQUEST);
            } catch (NotPermittedOperationException e) {
                log.error("Impossible to update VNF Pkg info: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.CONFLICT.value(),
                        e.getMessage()), HttpStatus.CONFLICT);
            } catch (NotAuthorizedOperationException e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.FORBIDDEN.value(),
                        e.getMessage()), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.PRECONDITION_FAILED.value(),
                    "Accept header null or different from application/json"), HttpStatus.PRECONDITION_FAILED);
        }
    }

    public ResponseEntity<?> deleteVNFPkgInfo(@RequestParam(required = false) String project,
                                              @ApiParam(value = "", required = true) @PathVariable("vnfPkgId") String vnfPkgId,
                                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        String accept = request.getHeader("Accept");
        log.debug("Processing REST request to delete VNF Pkg info " + vnfPkgId);
        try {
            vnfPackageManagementInterface.deleteVnfPkgInfo(vnfPkgId, project, false);
            log.debug("VNF Pkg info removed");
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (NotExistingEntityException e) {
            log.error("VNF Pkg info " + vnfPkgId + " not found");
            return new ResponseEntity<ProblemDetails>(
                    Utilities.buildProblemDetails(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (NotPermittedOperationException e) {
            log.error("VNF Pkg info " + vnfPkgId + " cannot be removed: " + e.getMessage());
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.CONFLICT.value(),
                    e.getMessage()), HttpStatus.CONFLICT);
        } catch (MalformattedElementException | FailedOperationException e) {
            log.error("VNF Pkg info " + vnfPkgId + " cannot be removed: " + e.getMessage());
            return new ResponseEntity<ProblemDetails>(
                    Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (NotAuthorizedOperationException e) {
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.FORBIDDEN.value(),
                    e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error("VNF Pkg info " + vnfPkgId + " cannot be removed: general internal error");
            return new ResponseEntity<ProblemDetails>(
                    Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getVNFD(@RequestParam(required = false) String project,
                                     @ApiParam(value = "", required = true) @PathVariable("vnfPkgId") String vnfPkgId,
                                     @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        String accept = request.getHeader("Accept");
        // TODO: consistency between accept values and input format when onboarding
        // should be better checked.
        // TODO: probably we should select the format based on the accept values. At the
        // at the moment the format is selected based on the original input type,
        // that is maintained in the DB.
        log.debug("Processing REST request to retrieve VNFD for VNF Pkg info ID " + vnfPkgId);

        try {
            Object vnfd = vnfPackageManagementInterface.getVnfd(vnfPkgId, false, project);
            // TODO: here it needs to check the type of entity that is returned
            return new ResponseEntity<Resource>((Resource) vnfd, HttpStatus.OK);
        } catch (NotExistingEntityException e) {
            log.error("VNFD for VNF Pkg info ID " + vnfPkgId + " not found");
            return new ResponseEntity<ProblemDetails>(
                    Utilities.buildProblemDetails(HttpStatus.NOT_FOUND.value(),
                            e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (NotPermittedOperationException e) {
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.CONFLICT.value(),
                    e.getMessage()), HttpStatus.CONFLICT);
        } catch (NotAuthorizedOperationException e) {
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.FORBIDDEN.value(),
                    e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (FailedOperationException e) {
            return new ResponseEntity<ProblemDetails>(
                    Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ProblemDetails>(
                    Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getVNFPkg(@RequestParam(required = false) String project,
                                       @ApiParam(value = "", required = true) @PathVariable("vnfPkgId") String vnfPkgId,
                                       @ApiParam(value = "") @RequestHeader(value = "Range", required = false) String range,
                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        String accept = request.getHeader("Accept");
        // TODO: consistency between accept values and input format when onboarding
        // should be better checked.
        // TODO: probably we should select the format based on the accept values. At the
        // moment the format is selected based on the original input type,
        // that is maintained in the DB.
        log.debug("Processing REST request to retrieve VNF Pkg for VNF Pkg info ID " + vnfPkgId);

        try {
            Object vnfPkg = vnfPackageManagementInterface.getVnfPkg(vnfPkgId, false, project);
            // TODO: here it needs to check the type of entity that is returned
            return new ResponseEntity<Resource>((Resource) vnfPkg, HttpStatus.OK);
        } catch (NotExistingEntityException e) {
            log.error("VNF Pkg for VNF Pkg info ID " + vnfPkgId + " not found");
            return new ResponseEntity<ProblemDetails>(
                    Utilities.buildProblemDetails(HttpStatus.NOT_FOUND.value(),
                            e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (NotPermittedOperationException e) {
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.CONFLICT.value(),
                    e.getMessage()), HttpStatus.CONFLICT);
        } catch (FailedOperationException e) {
            return new ResponseEntity<ProblemDetails>(
                    Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (NotAuthorizedOperationException e) {
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.FORBIDDEN.value(),
                    e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<ProblemDetails>(
                    Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> uploadVNFPkg(@RequestParam(required = false) String project,
                                          @ApiParam(value = "", required = true) @PathVariable("vnfPkgId") String vnfPkgId,
                                          @ApiParam(value = "", required = true) @RequestParam("file") MultipartFile body,
                                          @ApiParam(value = "The payload body contains a VNF Package ZIP file. The request shall set the \"Content-Type\" HTTP header as defined above") @RequestHeader(value = "Content-Type", required = false) String contentType,
                                          @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if(project == null)
            project = defaultProject;

        log.debug("Processing REST request for Uploading VNF Pkg content in VNF Pkg info " + vnfPkgId);

        String accept = request.getHeader("Accept");
        //if (accept != null && accept.contains("application/json")) {
        if (body.isEmpty()) {
            return new ResponseEntity<String>("Error message: File is empty!", HttpStatus.BAD_REQUEST);
        }

        if (!contentType.startsWith("multipart/form-data")) {
            // TODO: to be implemented later on
            return new ResponseEntity<String>("Unable to parse content " + contentType, HttpStatus.NOT_IMPLEMENTED);
        } else {
            try {
                ContentType type = null;
                log.debug("VNF Pkg content file name is: " + body.getOriginalFilename());
                if (body.getOriginalFilename().endsWith("zip")) {
                    type = ContentType.ZIP;
                } else {
                    // TODO: to be implemented later on
                    return new ResponseEntity<String>("Unable to parse file type that is not .zip",
                            HttpStatus.NOT_IMPLEMENTED);
                }
                vnfPackageManagementInterface.uploadVnfPkg(vnfPkgId, body, type, false, project);
                log.debug("Upload processing done");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                // TODO: check if we need to introduce the asynchronous mode
            } catch (NotPermittedOperationException | AlreadyExistingEntityException e) {
                log.error("Impossible to upload VNF Pkg: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.CONFLICT.value(),
                        e.getMessage()), HttpStatus.CONFLICT);
            } catch (MalformattedElementException | FailedOperationException e) {
                log.error("Impossible to upload VNF Pkg: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()), HttpStatus.BAD_REQUEST);
            } catch (NotExistingEntityException e) {
                log.error("Impossible to upload VNF PkgD: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.NOT_FOUND.value(),
                        e.getMessage()), HttpStatus.NOT_FOUND);
            } catch (NotAuthorizedOperationException e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.FORBIDDEN.value(),
                        e.getMessage()), HttpStatus.FORBIDDEN);
            } catch (Exception e) {
                log.error("General exception while uploading VNF Pkg content: " + e.getMessage());
                log.error("Details: ", e);
                return new ResponseEntity<ProblemDetails>(
                        Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                e.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        /*} else {
            return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.PRECONDITION_FAILED.value(),
                    "Accept header null or different from application/json"), HttpStatus.PRECONDITION_FAILED);
        }*/
    }

    @Override
    public ResponseEntity<?> updateVNFPkg(@RequestParam(required = false) String project,
                                          @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
                                          @ApiParam(value = "", required = true) @PathVariable("vnfPkgId") String vnfPkgId,
                                          @ApiParam(value = "", required = true) @RequestParam("file") MultipartFile body,
                                          @ApiParam(value = "The payload body contains a VNF Package ZIP file. The request shall set the \"Content-Type\" HTTP header as defined above.") @RequestHeader(value = "Content-Type", required = false) String contentType){
        if(project == null)
            project = defaultProject;

        log.debug("Processing REST request for Updating VNF Package content in VNF Pkg info " + vnfPkgId);
        String accept = request.getHeader("Accept");
        //if (accept != null && accept.contains("application/json")) {
        if (body.isEmpty()) {
            return new ResponseEntity<String>("Error message: File is empty!", HttpStatus.BAD_REQUEST);
        }

        if (!contentType.startsWith("multipart/form-data")) {
            // TODO: to be implemented later on
            return new ResponseEntity<String>("Unable to parse content " + contentType, HttpStatus.NOT_IMPLEMENTED);
        } else {
            try {
                ContentType type = null;
                log.debug("VNF Pkg content file name is: " + body.getOriginalFilename());
                if (body.getOriginalFilename().endsWith("zip")) {
                    type = ContentType.ZIP;
                } else {
                    // TODO: to be implemented later on
                    return new ResponseEntity<String>("Unable to parse file type that is not .zip",
                            HttpStatus.NOT_IMPLEMENTED);
                }
                vnfPackageManagementInterface.updateVnfPkg(vnfPkgId, body, type, false, null, project);
                log.debug("Update processing done");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                // TODO: check if we need to introduce the asynchronous mode
            } catch (NotPermittedOperationException | AlreadyExistingEntityException e) {
                log.error("Impossible to update VNF Pkg: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.CONFLICT.value(),
                        e.getMessage()), HttpStatus.CONFLICT);
            } catch (MalformattedElementException | FailedOperationException e) {
                log.error("Impossible to update VNF Pkg: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()), HttpStatus.BAD_REQUEST);
            } catch (NotExistingEntityException e) {
                log.error("Impossible to update VNF Pkg: " + e.getMessage());
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.NOT_FOUND.value(),
                        e.getMessage()), HttpStatus.NOT_FOUND);
            } catch (NotAuthorizedOperationException e) {
                return new ResponseEntity<ProblemDetails>(Utilities.buildProblemDetails(HttpStatus.FORBIDDEN.value(),
                        e.getMessage()), HttpStatus.FORBIDDEN);
            } catch (Exception e) {
                log.error("General exception while updating VNF Pkg content: " + e.getMessage());
                log.error("Details: ", e);
                return new ResponseEntity<ProblemDetails>(
                        Utilities.buildProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                e.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    public ResponseEntity<?> uploadVNFPkgFromURI(@RequestParam(required = false) String project,
                                                 @ApiParam(value = "", required = true) @PathVariable("vnfPkgId") String vnfPkgId,
                                                 @ApiParam(value = "", required = true) @Valid @RequestBody UploadVnfPackageFromUriRequest body,
                                                 @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> queryVNFPkgArtifact(@RequestParam(required = false) String project,
                                                 @ApiParam(value = "", required = true) @PathVariable("vnfPkgId") String vnfPkgId,
                                                 @ApiParam(value = "", required = true) @PathVariable("artifactPath") String artifactPath,
                                                 @ApiParam(value = "") @RequestHeader(value = "Range", required = false) String range,
                                                 @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type ", e);
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> createSubscription(@ApiParam(value = "", required = true) @Valid @RequestBody PkgmSubscriptionRequest body,
                                                @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<PkgmSubscription>(objectMapper.readValue("{  \"filter\" : {    \"vnfProducts\" : [ \"vnfProducts\", \"vnfProducts\" ],    \"usageState\" : { },    \"versions\" : [ \"versions\", \"versions\" ],    \"vnfdId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"vnfProductsFromProvider\" : [ \"vnfProductsFromProvider\", \"vnfProductsFromProvider\" ],    \"notificationTypes\" : { },    \"vnfpkgId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"operationalState\" : { },    \"vnfprovider\" : \"vnfprovider\",    \"vfnProductName\" : \"vfnProductName\",    \"vnfSoftwareVersion\" : \"vnfSoftwareVersion\",    \"vnfdVersions\" : [ \"vnfdVersions\", \"vnfdVersions\" ]  },  \"_links\" : \"http://example.com/aeiou\",  \"callbackUri\" : \"http://example.com/aeiou\",  \"self\" : \"http://example.com/aeiou\",  \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"}", PkgmSubscription.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<PkgmSubscription>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> getSubscriptions(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<PkgmSubscription>>(objectMapper.readValue("[ {  \"filter\" : {    \"vnfProducts\" : [ \"vnfProducts\", \"vnfProducts\" ],    \"usageState\" : { },    \"versions\" : [ \"versions\", \"versions\" ],    \"vnfdId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"vnfProductsFromProvider\" : [ \"vnfProductsFromProvider\", \"vnfProductsFromProvider\" ],    \"notificationTypes\" : { },    \"vnfpkgId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"operationalState\" : { },    \"vnfprovider\" : \"vnfprovider\",    \"vfnProductName\" : \"vfnProductName\",    \"vnfSoftwareVersion\" : \"vnfSoftwareVersion\",    \"vnfdVersions\" : [ \"vnfdVersions\", \"vnfdVersions\" ]  },  \"_links\" : \"http://example.com/aeiou\",  \"callbackUri\" : \"http://example.com/aeiou\",  \"self\" : \"http://example.com/aeiou\",  \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"}, {  \"filter\" : {    \"vnfProducts\" : [ \"vnfProducts\", \"vnfProducts\" ],    \"usageState\" : { },    \"versions\" : [ \"versions\", \"versions\" ],    \"vnfdId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"vnfProductsFromProvider\" : [ \"vnfProductsFromProvider\", \"vnfProductsFromProvider\" ],    \"notificationTypes\" : { },    \"vnfpkgId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"operationalState\" : { },    \"vnfprovider\" : \"vnfprovider\",    \"vfnProductName\" : \"vfnProductName\",    \"vnfSoftwareVersion\" : \"vnfSoftwareVersion\",    \"vnfdVersions\" : [ \"vnfdVersions\", \"vnfdVersions\" ]  },  \"_links\" : \"http://example.com/aeiou\",  \"callbackUri\" : \"http://example.com/aeiou\",  \"self\" : \"http://example.com/aeiou\",  \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<PkgmSubscription>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> getSubscription(@ApiParam(value = "", required = true) @PathVariable("subscriptionId") String subscriptionId,
                                             @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<PkgmSubscription>(objectMapper.readValue("{  \"filter\" : {    \"vnfProducts\" : [ \"vnfProducts\", \"vnfProducts\" ],    \"usageState\" : { },    \"versions\" : [ \"versions\", \"versions\" ],    \"vnfdId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"vnfProductsFromProvider\" : [ \"vnfProductsFromProvider\", \"vnfProductsFromProvider\" ],    \"notificationTypes\" : { },    \"vnfpkgId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"operationalState\" : { },    \"vnfprovider\" : \"vnfprovider\",    \"vfnProductName\" : \"vfnProductName\",    \"vnfSoftwareVersion\" : \"vnfSoftwareVersion\",    \"vnfdVersions\" : [ \"vnfdVersions\", \"vnfdVersions\" ]  },  \"_links\" : \"http://example.com/aeiou\",  \"callbackUri\" : \"http://example.com/aeiou\",  \"self\" : \"http://example.com/aeiou\",  \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"}", PkgmSubscription.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<PkgmSubscription>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> deleteSubscription(@ApiParam(value = "", required = true) @PathVariable("subscriptionId") String subscriptionId,
                                                @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
}
