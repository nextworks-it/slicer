package it.nextworks.nfvmano.sebastian.pp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


public class SbRestClient {
    private static final Logger log = LoggerFactory.getLogger(SbRestClient.class);

    private RestTemplate restTemplate;
    private String targetUrl;


    public SbRestClient() {
        this.restTemplate = new RestTemplate();

    }


    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public ResponseEntity<String> performHTTPRequest(Object request, String url, HttpMethod httpMethod){
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");  //TODO: put header values as parameters
        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

        try {
            log.info("URL performing the request to: "+url);
            ResponseEntity<String> httpResponse = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            HttpStatus code = httpResponse.getStatusCode();
            log.info("Response code: " + httpResponse.getStatusCode().toString());
            return httpResponse;
        } catch (RestClientResponseException e) {
            log.info("Message received: "+e.getResponseBodyAsString());
            throw e;
        } catch (RestClientException e){
            log.info("Message received: "+e.getMessage());
            throw e;
        }
    }

    public String manageHTTPResponse(ResponseEntity<?> httpResponse, String errMsg, String okCodeMsg, HttpStatus httpStatusExpected) {
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
