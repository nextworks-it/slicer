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
package it.nextworks.nfvmano.sebastian.nfvodriver.timeo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnBoardVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnBoardVnfPackageResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardAppPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardAppPackageResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardPnfdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryOnBoadedAppPkgInfoResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryPnfdResponse;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.CreateNsIdentifierRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.InstantiateNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.TerminateNsRequest;


/**
 * REST client to interact with TIMEO NFVO.
 * 
 * @author nextworks
 *
 */
public class TimeoRestClient {

	private static final Logger log = LoggerFactory.getLogger(TimeoRestClient.class);
	
	private RestTemplate restTemplate;
	
	//private String timeoUrl;
	
	private String nsdServiceUrl;
	
	private String vnfdServiceUrl;
	
	private String appdServiceUrl;
	
	private String nsLifecycleServiceUrl;
	
	/**
	 * Constructor
	 * 
	 * @param timeoUrl root URL to interact with TIMEO
	 * @param restTemplate REST template
	 */
	public TimeoRestClient(String timeoUrl,
			RestTemplate restTemplate) {
		//this.timeoUrl = timeoUrl;
		this.nsdServiceUrl = timeoUrl + "/nfvo/nsdManagement";
		this.vnfdServiceUrl = timeoUrl + "/nfvo/vnfdManagement";
		this.appdServiceUrl = timeoUrl + "/nfvo/appdManagement";
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
	
	//******************************** NSD methods ********************************//
	
	
	public String onboardNsd(OnboardNsdRequest request) throws MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		log.debug("Building HTTP request to onboard NSD.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = nsdServiceUrl + "/nsd";
		
		try {
			log.debug("Sending HTTP request to onboard NSD.");
			
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.CREATED)) {
				String nsdInfoId = httpResponse.getBody();
				log.debug("NSD correctly onboarded with Nsd info ID " + nsdInfoId);
				return nsdInfoId;
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NSD onboarding at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.CONFLICT)) {
				throw new AlreadyExistingEntityException("Error during NSD onboarding at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NSD onboarding");
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
		}
	}
	
	public String onboardPnfd(OnboardPnfdRequest request) throws MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		log.debug("Building HTTP request to onboard PNFD.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = nsdServiceUrl + "/pnfd";
		
		try {
			log.debug("Sending HTTP request to onboard PNFD.");
			
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.CREATED)) {
				String pnfdInfoId = httpResponse.getBody();
				log.debug("PNFD correctly onboarded with PNFD info ID " + pnfdInfoId);
				return pnfdInfoId;
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during PNFD onboarding at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.CONFLICT)) {
				throw new AlreadyExistingEntityException("Error during PNFD onboarding at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during PNFD onboarding");
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
		}
	}
	
	public QueryNsdResponse queryNsd(GeneralizedQueryRequest request) throws MalformattedElementException, NotExistingEntityException, FailedOperationException {
		
		log.debug("Building HTTP request to query NSD.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = nsdServiceUrl + "/nsd/query";
		
		try {
			log.debug("Sending HTTP request to retrieve NSD.");
			
			ResponseEntity<QueryNsdResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, QueryNsdResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("NSD correctly retrieved");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NSD retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NSD retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NSD retrieval");
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
		}
	}
	
	public QueryPnfdResponse queryPnfd(GeneralizedQueryRequest request) throws MalformattedElementException, NotExistingEntityException, FailedOperationException {
		log.debug("Building HTTP request to query PNFD.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = nsdServiceUrl + "/pnfd/query";
		
		try {
			log.debug("Sending HTTP request to retrieve PNFD.");
			
			ResponseEntity<QueryPnfdResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, QueryPnfdResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("PNFD correctly retrieved");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during PNFD retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during PNFD retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during PNFD retrieval");
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
		}
	}
	
	//******************************** VNF package methods ********************************//
	
	public OnBoardVnfPackageResponse onBoardVnfPackage(OnBoardVnfPackageRequest request)
			throws AlreadyExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Building HTTP request to onboard VNF package.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = vnfdServiceUrl + "/vnfPackage";
		
		try {
			log.debug("Sending HTTP request to onboard VNF package.");
			
			ResponseEntity<OnBoardVnfPackageResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, OnBoardVnfPackageResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.CREATED)) {
				log.debug("VNF package correctly onboarded");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during VNF package onboarding at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.CONFLICT)) {
				throw new AlreadyExistingEntityException("Error during VNF package onboarding at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during VNF package onboarding");
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO VNFD catalogue at url " + url);
		}
	}
	
	
	public QueryOnBoardedVnfPkgInfoResponse queryVnfPackageInfo(GeneralizedQueryRequest request)
			throws NotExistingEntityException, MalformattedElementException {
		log.debug("Building HTTP request to query VNF package.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = vnfdServiceUrl + "/vnfPackage/query";
		
		try {
			log.debug("Sending HTTP request to retrieve VNF Package.");
			
			ResponseEntity<QueryOnBoardedVnfPkgInfoResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, QueryOnBoardedVnfPkgInfoResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("VNF package correctly retrieved");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during VNF package retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during VNF package retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new NotExistingEntityException("Generic error during VNF package retrieval at NFVO: " + httpResponse.getBody());
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new NotExistingEntityException("Error while interacting with NFVO VNFD catalogue at url " + url);
		}
	}
	
	//******************************** MEC App package methods ********************************//
	
	public QueryOnBoadedAppPkgInfoResponse queryApplicationPackage(GeneralizedQueryRequest request)
			throws NotExistingEntityException, MalformattedElementException {
		log.debug("Building HTTP request to query MEC App package.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = appdServiceUrl + "/appd/query";
		
		try {
			log.debug("Sending HTTP request to retrieve MEC App Package.");
			
			ResponseEntity<QueryOnBoadedAppPkgInfoResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, QueryOnBoadedAppPkgInfoResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("MEC App package correctly retrieved");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during MEC App package retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during MEC App package retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new NotExistingEntityException("Generic error during MEC App package retrieval at NFVO: " + httpResponse.getBody());
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new NotExistingEntityException("Error while interacting with NFVO MEC App catalogue at url " + url);
		}
	}
	
	public OnboardAppPackageResponse onboardAppPackage(OnboardAppPackageRequest request)
			throws AlreadyExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Building HTTP request to onboard a MEC App package.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = appdServiceUrl + "/appd";
		
		try {
			log.debug("Sending HTTP request to onboard a MEC App Package.");
			
			ResponseEntity<OnboardAppPackageResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, OnboardAppPackageResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.CREATED)) {
				log.debug("MEC App package correctly onboarded");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.CONFLICT)) {
				throw new AlreadyExistingEntityException("Error during MEC App package onboarding at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during MEC App package onboarding at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error during MEC App package onboarding at NFVO: " + httpResponse.getBody());
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO MEC App catalogue at url " + url);
		}
	}

}
