package it.nextworks.nfvmano.sebastian.arbitrator;

import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ArbitratorNotificationRestClient {

    private static final Logger log = LoggerFactory.getLogger(ArbitratorNotificationRestClient.class);

    private RestTemplate restTemplate;

    private String notificationBaseUrl;

    public ArbitratorNotificationRestClient(String baseUrl){
        this.notificationBaseUrl= baseUrl;
        this.restTemplate= new RestTemplate();

    }


    private ResponseEntity<String> performHTTPRequest(Object request, String url, HttpMethod httpMethod) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");


        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

        try {
            //log.info("URL performing the request to: "+url);
            ResponseEntity<String> httpResponse =
                    restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            HttpStatus code = httpResponse.getStatusCode();
            //log.info("Response code: " + httpResponse.getStatusCode().toString());
            return httpResponse;
        } catch (RestClientException e) {
            log.info("Message received: "+e.getMessage());
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

        if(httpResponse.getBody()==null) return null;

        log.info(("Body response: "+httpResponse.getBody().toString()));
        return httpResponse.getBody().toString();

    }

    public void sendArbtirationResponse(String operationId, ArbitratorResponse response){
        String url = notificationBaseUrl + "/arbitrator/notifyArbitrationResponse/"+operationId;
        ResponseEntity<String> httpResponse = performHTTPRequest(response, url, HttpMethod.POST);
        manageHTTPResponse(httpResponse, "Error while sending external arbitration", "External arbitration correctly sent", HttpStatus.CREATED);
    }
}
