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
package it.nextworks.nfvmano.sebastian.nsmf.nbi;

import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.common.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceFailureNotification;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;

public class VsmfRestClient implements NsmfLcmConsumerInterface {

	private static final Logger log = LoggerFactory.getLogger(VsmfRestClient.class);
	
	private RestTemplate restTemplate;
	private String vsmfUrl;
	private String cookies;
	private Authenticator authenticator;
	private String baseUrl;

	private void performAuth(String tenantId){
		authenticator.authenticate(tenantId);
		if(authenticator.isAuthenticated()){
			cookies=authenticator.getCookie();
		}
	}

	public VsmfRestClient(String baseUrl, AdminService adminService) {
		this.vsmfUrl = baseUrl + "/vs/notifications";
		this.baseUrl = baseUrl;
		this.restTemplate = new RestTemplate();
		this.authenticator = new Authenticator(baseUrl,adminService);
	}


	@Override
	public void notifyNetworkSliceStatusChange(NetworkSliceStatusChangeNotification notification) {
		log.debug("Sending notification about NSI status change");
		performAuth(notification.getTenantId());
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		if(this.cookies!=null){
			header.add("Cookie", this.cookies);
		}

		HttpEntity<?> postEntity = new HttpEntity<>(notification, header);

		String url = vsmfUrl + "/nsilcmchange";

		try {
			log.debug("Sending HTTP message to notify network slice status change.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);

			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();

			if (code.equals(HttpStatus.OK)) {
				log.debug("Notification correctly dispatched.");
			} else {
				log.debug("Error while sending notification");
			}

		} catch (RestClientException e) {
			log.debug("Error while sending notification");
			log.debug(e.toString());
			log.debug("RestClientException response: Message "+e.getMessage());
		}
	}
	
	@Override
	public void notifyNetworkSliceFailure(NetworkSliceFailureNotification notification) {
		/*This function is never triggered because the JsonProcessingException in never caught: the JSON validation is performed on the REST controller,
		//thus a valid JSON is always available.
		*/

		log.debug("Sending notification about NSI failure");
		HttpHeaders header = new HttpHeaders();
		//This function is never triggered because the
		header.add("Content-Type", "application/json");
		if(this.cookies!=null){
			header.add("Cookie", this.cookies);
		}
		HttpEntity<?> postEntity = new HttpEntity<>(notification, header);

		String url = vsmfUrl + "/nsifailure";

		try {
			log.debug("Sending HTTP message to notify network slice failure.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);

			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();

			if (code.equals(HttpStatus.OK)) {
				log.debug("Notification correctly dispatched.");
			} else {
				log.debug("Error while sending notification");
			}

		} catch (RestClientException e) {
			log.debug("Error while sending notification");
		}
	}

	@Override
	public void notifyNetworkSliceActuation(NetworkSliceStatusChangeNotification notification, String endpoint) {
		log.debug("Sending notification about actuation result.");
		performAuth(notification.getTenantId());
		final String url = baseUrl +endpoint;
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		if(this.cookies!=null){
			header.add("Cookie", this.cookies);
		}
		log.info("Url to send the request to: "+url);
		HttpEntity<?> postEntity = new HttpEntity<>(notification, header);
		try{
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);

			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();

			if (code.equals(HttpStatus.OK)) {
				log.debug("Notification correctly dispatched.");
			} else {
				log.debug("Error while sending notification");
			}

		} catch (RestClientException e) {
			log.info(e.getMessage());
			log.debug("Error while sending notification");
		}
	}

}
