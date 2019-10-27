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
package it.nextworks.nfvmano.nfvodriver.timeo;


import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnBoardVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnBoardVnfPackageResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardAppPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardAppPackageResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardNsdRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardPnfdRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryOnBoadedAppPkgInfoResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryPnfdResponse;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;


/**
 * REST client to interact with TIMEO NFVO.
 * 
 * @author nextworks
 *
 */
public class TimeoLcmRestClient {

	private static final Logger log = LoggerFactory.getLogger(TimeoLcmRestClient.class);
	
	private RestTemplate restTemplate;

	private String nsLifecycleServiceUrl;
	
	/**
	 * Constructor
	 * 
	 * @param timeoUrl root URL to interact with TIMEO
	 * @param restTemplate REST template
	 */
	public TimeoLcmRestClient(String timeoUrl,
							  RestTemplate restTemplate) {


		this.nsLifecycleServiceUrl = timeoUrl + "/nfvo/nsLifecycle";
		this.restTemplate = restTemplate;
	}
	
	
	//******************************** NS LCM methods ********************************//
	
	public String createNsIdentifier(CreateNsIdentifierRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {

		log.debug("Building HTTP request to create NS ID.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);

		String url = nsLifecycleServiceUrl + "/ns";

		try {
			log.debug("Sending HTTP request to create NS ID.");
			ResponseEntity<String> httpResponse = 
    				restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.CREATED)) {
				String nsId = httpResponse.getBody();
				log.debug("Created NS ID at NFVO: " + nsId);
				return nsId;
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS ID creation at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS ID creation at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS ID creation");
			}
				
		} catch (RestClientException ex) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}

	public String instantiateNs(InstantiateNsRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		String nsId = request.getNsInstanceId();
		log.debug("Building HTTP request to instantiate NS instance " + nsId);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> putEntity = new HttpEntity<>(request, header);

		String url = nsLifecycleServiceUrl + "/ns/" + nsId + "/instantiate";
		
		try {
			log.debug("Sending HTTP request to instantiate NS.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.PUT, putEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				String operationId = httpResponse.getBody();
				log.debug("Started NS instantiation at NFVO. Operation ID: " + operationId);
				return operationId;
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS instantiation at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS instantiation at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS instantiation");
			}
			
		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}

	public String scaleNs(ScaleNsRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		String nsId = request.getNsInstanceId();
		log.debug("Building HTTP request to scale NS instance " + nsId);

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> putEntity = new HttpEntity<>(request, header);

		String url = nsLifecycleServiceUrl + "/ns/" + nsId + "/scale";

		try {
			log.debug("Sending HTTP request to instantiate NS.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.PUT, putEntity, String.class);

			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();

			if (code.equals(HttpStatus.OK)) {
				String operationId = httpResponse.getBody();
				log.debug("Started NS instantiation at NFVO. Operation ID: " + operationId);
				return operationId;
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS instantiation at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS instantiation at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS instantiation");
			}

		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}
	
	public QueryNsResponse queryNs(String nsInstanceId) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Building HTTP request for querying NS instance with ID " + nsInstanceId);
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> getEntity = new HttpEntity<>(header);
		
		String url = nsLifecycleServiceUrl + "/ns/" + nsInstanceId;
		
		try {
			log.debug("Sending HTTP request to retrieve NS instance.");
			
			ResponseEntity<QueryNsResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.GET, getEntity, QueryNsResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("NS instance correctly retrieved");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS retrieval");
			}
			
		} catch (Exception e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}
	
	public String terminateNs(TerminateNsRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		String nsInstanceId = request.getNsInstanceId();
		log.debug("Building HTTP request for terminating NS instance " + nsInstanceId);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> putEntity = new HttpEntity<>(request, header);

		String url = nsLifecycleServiceUrl + "/ns/" + nsInstanceId + "/terminate";
		
		try {
			log.debug("Sending HTTP request to terminate NS.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.PUT, putEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				String operationId = httpResponse.getBody();
				log.debug("Started NS termination at NFVO. Operation ID: " + operationId);
				return operationId;
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS termination at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS termination at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS termination");
			}
			
		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}
	
	public void deleteNsIdentifier(String nsInstanceIdentifier)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException {
		log.debug("Building HTTP request for deleting NS instance identifier " + nsInstanceIdentifier);

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> deleteEntity = new HttpEntity<>(header);

		String url = nsLifecycleServiceUrl + "/ns/" + nsInstanceIdentifier;
		
		try {
			log.debug("Sending HTTP request to delete NS ID.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.DELETE, deleteEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("NS ID removed at NFVO.");
				return;
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS ID removal at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS ID removal");
			}
			
		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}
	
	public OperationStatus getOperationStatus(String operationId) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Building HTTP request to retrieve status for NS LCM operation " + operationId);

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> getEntity = new HttpEntity<>(header);

		String url = nsLifecycleServiceUrl + "/operation/" + operationId;

		try {
			log.debug("Sending HTTP request to retrieve operation status.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.GET, getEntity, String.class);

			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();

			if (code.equals(HttpStatus.OK)) {
				log.debug("Operation status correctly retrieved.");
				String operationStatus = httpResponse.getBody();
				
				if (operationStatus.equals("PROCESSING")) return OperationStatus.PROCESSING;
				else if (operationStatus.equals("SUCCESSFULLY_DONE")) return OperationStatus.SUCCESSFULLY_DONE;
				else if (operationStatus.equals("FAILED")) return OperationStatus.FAILED;
				else {
					log.error("Unknow operation status returned.");
					throw new FailedOperationException("Unknow operation status returned.");
				}
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during retrieval of operation status at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during retrieval of operation status at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during retrieval of operation status");
			}

		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}

	}
	


}
