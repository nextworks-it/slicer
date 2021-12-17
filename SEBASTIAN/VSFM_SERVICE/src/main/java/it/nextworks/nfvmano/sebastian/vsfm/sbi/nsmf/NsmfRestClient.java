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
package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.common.Authenticator;
import it.nextworks.nfvmano.sebastian.nsmf.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.AbstractNsmfDriver;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfType;

/**
 * REST client to interact with a REST based NSMF exposing 3GPP inspired interface.
 *
 * @author nextworks
 */
public class NsmfRestClient extends AbstractNsmfDriver {

    private static final Logger log = LoggerFactory.getLogger(NsmfRestClient.class);

    private RestTemplate restTemplate;
    private String cookies;

    private String nsmfUrl;
    private Authenticator authenticator;

    public NsmfRestClient(String domainId, String baseUrl, AdminService adminService) {
        super(NsmfType.NSMF_3GPP_LIKE, domainId);
        this.nsmfUrl = baseUrl + "/vs/basic/nslcm";
        this.restTemplate = new RestTemplate();
        this.authenticator = new Authenticator(baseUrl, adminService);
    }

    private void performAuth(String tenantId) {
        authenticator.authenticate(tenantId);
        if (authenticator.isAuthenticated()) {
            cookies = authenticator.getCookie();
        }
    }

    //In order to test the NSMF client
    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    private ResponseEntity<String> performHTTPRequest(Object request, String url, HttpMethod httpMethod, String tenantID) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        performAuth(tenantID);
        if (this.cookies != null) {
            header.add("Cookie", this.cookies);
        }

        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

        try {
            //log.info("URL performing the request to: "+url);
            ResponseEntity<String> httpResponse =
                    restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            HttpStatus code = httpResponse.getStatusCode();
            //log.info("Response code: " + httpResponse.getStatusCode().toString());
            return httpResponse;
        } catch (RestClientException e) {
            log.info("Message received: " + e.getMessage());
            return null;
        }
    }

    private String manageHTTPResponse(ResponseEntity<?> httpResponse, String errMsg, String okCodeMsg, HttpStatus httpStatusExpected) {
        if (httpResponse == null) {
            log.info(errMsg);
            return null;
        }

        if (httpResponse.getStatusCode().equals(httpStatusExpected)) log.info(okCodeMsg);
        else log.info(errMsg);

        log.info("Response code: " + httpResponse.getStatusCode().toString());

        if (httpResponse.getBody() == null) return null;

        log.info(("Body response: " + httpResponse.getBody().toString()));
        return httpResponse.getBody().toString();

    }


    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
        String url = nsmfUrl + "/ns";
        ResponseEntity<String> httpResponse = performHTTPRequest(request, url, HttpMethod.POST, tenantId);
        return manageHTTPResponse(httpResponse, "Error while creating network slice identifier", "Network slice identifier correctly created", HttpStatus.CREATED);
    }


    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
        log.info("Sending request to instantiate network Slice");
        String url = nsmfUrl + "/ns/" + request.getNsiId() + "/action/instantiate";
        ResponseEntity<String> httpResponse = performHTTPRequest(request, url, HttpMethod.PUT, tenantId);
        String bodyResponse = manageHTTPResponse(httpResponse, "Error while instantiating network slice", "Network slice instantiation correctly performed", HttpStatus.ACCEPTED);
    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
        log.info("Sending request to modify network slice");
        String url = nsmfUrl + "/ns/" + request.getNsiId() + "/action/modify";
        ResponseEntity<String> httpResponse = performHTTPRequest(request, url, HttpMethod.PUT, tenantId);
        String bodyResponse = manageHTTPResponse(httpResponse, "Error while modifying network slice", "Network slice modification correctly performed", HttpStatus.ACCEPTED);
    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
        log.info("Sending request to terminate network slice");
        String url = nsmfUrl + "/ns/" + request.getNsiId() + "/action/terminate";
        ResponseEntity<String> httpResponse = performHTTPRequest(request, url, HttpMethod.PUT, tenantId);
        String bodyResponse = manageHTTPResponse(httpResponse, "Error while terminating network slice", "Network slice termination correctly performed", HttpStatus.ACCEPTED);
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domainId, String tenantId)
            throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
        log.info("Sending request to query network slice instance");
        String url = nsmfUrl + "/ns/";
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        performAuth(tenantId);
        if (this.cookies != null) {
            header.add("Cookie", this.cookies);
        }

        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

        try {
            //If the nsiID is specified a NetworkSliceInstance is sent, viceversa a List<NetworkSliceInstance> .
            if (request.getFilter() != null) {
                String nsiID = request.getFilter().getParameters().getOrDefault("NSI_ID", "");
                url += nsiID;
                ResponseEntity<NetworkSliceInstance> httpResponseAtMostOneNSI =
                        restTemplate.exchange(url, HttpMethod.GET, httpEntity, NetworkSliceInstance.class);

                List<NetworkSliceInstance> nsii = new ArrayList<NetworkSliceInstance>();
                nsii.add(httpResponseAtMostOneNSI.getBody());
                log.info("Response code: " + httpResponseAtMostOneNSI.getStatusCode().toString());
                manageHTTPResponse(httpResponseAtMostOneNSI, "Error querying network slice instance", "Query to network slice instance performed correctly", HttpStatus.OK);
                return nsii;
            } else {
                ResponseEntity<List<NetworkSliceInstance>> httpResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<NetworkSliceInstance>>() {
                });
                log.info("Response code: " + httpResponse.getStatusCode().toString());
                manageHTTPResponse(httpResponse, "Error querying network slice instance", "Query to network slice instance performed correctly", HttpStatus.OK);
                return httpResponse.getBody();
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void configureNetworkSliceInstance(ConfigureNsiRequest request, String domainId, String tenantId) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException{
        throw new MethodNotImplementedException("Day1 configuration currently not supported");
    }
}