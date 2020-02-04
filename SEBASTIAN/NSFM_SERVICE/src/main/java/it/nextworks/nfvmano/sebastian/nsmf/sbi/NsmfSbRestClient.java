/*
 * Copyright (c) 2020 Nextworks s.r.l
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

package it.nextworks.nfvmano.sebastian.nsmf.sbi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

class NsmfSbRestClient {
    private static final Logger log = LoggerFactory.getLogger(NsmfSbRestClient.class);

    private RestTemplate restTemplate;
    private String targetUrl;


    NsmfSbRestClient() {
        this.restTemplate = new RestTemplate();

    }

    String getTargetUrl() {
        return targetUrl;
    }

    void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    ResponseEntity<String> performHTTPRequest(Object request, String url, HttpMethod httpMethod) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");  //TODO: put header values as parameters
        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

        try {
            log.info("URL performing the request to: "+url);
            ResponseEntity<String> httpResponse = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            HttpStatus code = httpResponse.getStatusCode();
            log.info("Response code: " + httpResponse.getStatusCode().toString());
            return httpResponse;
        } catch (RestClientException e) {
            log.info("Message received: "+e.getMessage());
            return null;
        }
    }

    String manageHTTPResponse(ResponseEntity<?> httpResponse, String errMsg, String okCodeMsg, HttpStatus httpStatusExpected) {
        if (httpResponse == null) {
            log.info(errMsg);
            return null;
        }

        if (httpResponse.getStatusCode().equals(httpStatusExpected)) log.info(okCodeMsg);
        else log.info(errMsg);

        log.info("Response code: " + httpResponse.getStatusCode().toString());

        if(httpResponse.getBody()==null) return null;

        log.info(("Body response: "+httpResponse.getBody().toString()));
        return httpResponse.getBody().toString();

    }

}
