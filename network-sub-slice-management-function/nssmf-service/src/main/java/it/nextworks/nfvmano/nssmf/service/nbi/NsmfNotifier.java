/*
 * Copyright (c) 2022 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.nssmf.service.nbi;

import it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces.NsmfNotificationInterface;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.NsmfNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Rest Client Service pushing Notifications to the NSMF
 *
 * @author Pietro G. Giardina
 */

@Service
public class NsmfNotifier implements NsmfNotificationInterface {
    private static final Logger log = LoggerFactory.getLogger(NsmfNotifier.class);

    @Value("${nssmf.nsmfnotifier.auth.user}")
    private String username;
    @Value("${nssmf.nsmfnotifier.auth.pwd}")
    private String password;
    @Value("${nssmf.nsmfnotifier.url}")
    private String baseurl;

    private NsmfRestClient nsmfRestClient;

    @Override
    public void notifyNsmf(NsmfNotificationMessage nsmfNotificationMessage) {
        log.debug("Sending NSSI Status change notification");
        log.debug("Requested by " + nsmfNotificationMessage.getNssiId().toString());
        if (nsmfRestClient.authenticate(username, password)){
            nsmfRestClient.notifyNsmf(nsmfNotificationMessage);
            log.debug("Notification Sent");
        }else
            log.debug("Error in sending notification to NSMF. Requester: " + nsmfNotificationMessage.getNssiId().toString());
    }

    @PostConstruct
    private void initRestClient(){
        nsmfRestClient = new NsmfRestClient(baseurl);
    }
}

class NsmfRestClient implements NsmfNotificationInterface{

    private static final Logger log = LoggerFactory.getLogger(NsmfRestClient.class);
    private RestTemplate restTemplate;
    private String baseUrl;
    private String authCookie;

    NsmfRestClient(String baseUrl) {
        restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    boolean authenticate(String username, String password){
        log.info("Building http request to login");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        try{
            ResponseEntity<String> httpResponse = restTemplate.exchange(baseUrl +"/login", HttpMethod.POST, request, String.class);
            HttpHeaders headersResp = httpResponse.getHeaders();
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.OK)) {
                authCookie = headersResp.getFirst(HttpHeaders.SET_COOKIE);
                log.info("Authentication performed on Nsmf. Cookie:  " + authCookie);
                return true;
            }

            log.debug("Error while sending notification - HTTP Code: "+code.toString());
            return false;

        }catch (RestClientException e) {
            log.error("Error during authentication process with the NSMF " + e.toString());
            return false;
        }

    }

    @Override
    public void notifyNsmf(NsmfNotificationMessage nsmfNotificationMessage) {

        if(this.authCookie!=null) {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");

            header.add("Cookie", this.authCookie);


            HttpEntity<?> postEntity = new HttpEntity<>(nsmfNotificationMessage, header);

            //String url = baseUrl + "/nssmf-notifications";
            String url = baseUrl + "/vs/basic/nslcm/nss/"+nsmfNotificationMessage.getNssiId().toString()+"/notify";

            try {
                log.debug("Sending HTTP message to notify network slice status change.");
                ResponseEntity<String> httpResponse =
                        restTemplate.exchange(url, HttpMethod.PUT, postEntity, String.class);

                log.debug("Response code: " + httpResponse.getStatusCode().toString());
                HttpStatus code = httpResponse.getStatusCode();

                if (code.equals(HttpStatus.ACCEPTED)) {
                    log.debug("Notification correctly dispatched.");
                } else {
                    log.debug("Error while sending notification");
                }

            } catch (RestClientException e) {
                log.debug("Error while sending notification");
                log.debug(e.toString());
                log.debug("RestClientException response: Message " + e.getMessage());
            }
        }else log.debug("Authentication Required!");

    }
}