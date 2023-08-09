package it.nextworks.nfvmano.libs.vs.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class BaseRestClient {
    private static final Logger log = LoggerFactory.getLogger(BaseRestClient.class);

    private RestTemplate restTemplate;
    private String cookies;

    public BaseRestClient(){
        this.restTemplate=new RestTemplate();
    }

    public BaseRestClient(String cookies){
        this.restTemplate=new RestTemplate();
        this.cookies=cookies;
    }

    public BaseRestClient(String cookies, RestTemplate restTemplate){
        this.restTemplate=restTemplate;
        this.cookies=cookies;
    }


    public ResponseEntity<String> performHTTPRequest(Object request, String url, HttpMethod httpMethod) {

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        if (this.cookies != null) {
            header.add("Cookie", this.cookies);
        }

        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

        try {
            ResponseEntity<String> httpResponse =
                    restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            HttpStatus code = httpResponse.getStatusCode();
            return httpResponse;
        } catch (RestClientException e) {
            log.info("Message received: " + e.getMessage());
            return null;
        }
    }


    public ResponseEntity<String>  performHTTPRequestWithFormData(MultiValueMap<String, String> formDataMap, String url, HttpMethod httpMethod){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","multipart/form-data");
        headers.add("Accept","*/*");
        headers.add("Accept-encoding","gzip, deflate, br");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formDataMap, headers);

        return
                restTemplate.exchange(url,
                        httpMethod,
                        entity,
                        String.class);
    }

    public String manageHTTPResponse(ResponseEntity<?> httpResponse, String errMsg, String okCodeMsg, HttpStatus httpStatusExpected) {
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

}
