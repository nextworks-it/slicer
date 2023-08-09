package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements.SoNsQueryResponse;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements.SoVnfQueryResponse;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.*;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.*;

import java.io.IOException;
import java.util.Map;

public class SODriver {

    private static final Logger log = LoggerFactory.getLogger(SODriver.class);

    private RestTemplate restTemplate;

    private String smUrl;

    public SODriver(String ipAddress, String port) {
        this.smUrl = String.format("http://%s:%s/5gt/so/v1", ipAddress, port);
        this.restTemplate= new RestTemplate(new BufferingClientHttpRequestFactory(
                new SimpleClientHttpRequestFactory()
        ));
    }

    //********************** NSD methods ********************************//

    public String onboardNsd(OnboardNsdRequest request) throws MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
        log.debug("Building HTTP request to onboard NSD.");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        HttpEntity<?> postEntity = new HttpEntity<>(request, header);

        String url = this.smUrl + "/ns/nsdManagement/nsd";

        try {
            log.debug("Sending HTTP request to onboard NSD.");

            ResponseEntity<String> httpResponse =
                    restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);

            log.debug("Response code: " + httpResponse.getStatusCode().toString());
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.OK)) {
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

    public QueryNsdResponse queryNsd(String urlTemplate, GeneralizedQueryRequest request) throws MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.debug("Building HTTP request to query NSD.");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        HttpEntity<?> getEntity = new HttpEntity<>(request, header);
        String nsdId = request.getFilter().getParameters().get("NSD_ID");
        String nsdVersion = request.getFilter().getParameters().get("NSD_VERSION");

        String url = this.smUrl + urlTemplate.replaceAll("\\{nsdid\\}", nsdId);
        url = url.replaceAll("\\{nsdversion\\}", nsdVersion);

        try {
            log.debug("Sending HTTP request to retrieve NSD.");
            log.debug("NSD_ID:"+  nsdId + "NSD_VERSION:" + nsdVersion + "GET: " + url);

			/*ResponseEntity<QueryNsdIdVersionResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.GET, getEntity, QueryNsdIdVersionResponse.class);
			*/
            ResponseEntity<QueryNsdResponse> httpResponse =
                    restTemplate.exchange(url, HttpMethod.GET, getEntity, QueryNsdResponse.class);

            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.OK)) {
                log.debug("HttpStatus.OK!");
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode  = (ObjectNode) mapper.valueToTree(httpResponse.getBody());
                ((ObjectNode)rootNode.path("queryResult").get(0)).put("nsdInfoId", nsdId);
                QueryNsdResponse finalResponse = mapper.treeToValue(rootNode, QueryNsdResponse.class);
                return finalResponse;

            } else if (code.equals(HttpStatus.NOT_FOUND)) {
                throw new NotExistingEntityException("Error during NSD retrieval at NFVO: " + httpResponse.getBody());
            } else if (code.equals(HttpStatus.BAD_REQUEST)) {
                throw new MalformattedElementException("Error during NSD retrieval at NFVO: " + httpResponse.getBody());
            } else {
                throw new FailedOperationException("Generic error on NFVO during NSD retrieval");
            }
        } catch (IOException | RestClientException e3) {
            log.debug("error while interacting with NFVO.");
            throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);

        }

    }

    public void deleteNsd(String nsdId, String nsdVersion) throws FailedOperationException {
        log.debug("Building HTTP request to delete NSD.");
        HttpHeaders header = new HttpHeaders();
        header.add("Accept", "application/json");
        HttpEntity<?> getEntity = new HttpEntity<>(null, header);

        String url = this.smUrl + "/ns/nsd/" + nsdId + "/" + nsdVersion;

        try {
            log.debug("Sending HTTP request to delete NSD.");

            ResponseEntity<QueryNsdResponse> httpResponse =
                    restTemplate.exchange(url, HttpMethod.DELETE, getEntity, QueryNsdResponse.class);

            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.OK)) {
                log.debug("HttpStatus.OK!");
            } else if (code.equals(HttpStatus.NOT_FOUND)) {
                throw new FailedOperationException("Error during NSD retrieval at NFVO: " + httpResponse.getBody());
            } else if (code.equals(HttpStatus.BAD_REQUEST)) {
                throw new FailedOperationException("Error during NSD retrieval at NFVO: " + httpResponse.getBody());
            } else {
                throw new FailedOperationException("Generic error on NFVO during NSD retrieval");
            }
        } catch (RestClientException e3) {
            log.debug("Error while interacting with NFVO.");
            throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
        }
    }

    public SoNsQueryResponse queryNsds() throws FailedOperationException{
        log.debug("Building HTTP request to query NSDs.");
        HttpHeaders header = new HttpHeaders();
        header.add("Accept", "application/json");
        HttpEntity<?> getEntity = new HttpEntity<>(null, header);

        String url = this.smUrl + "/ns/nsd";

        try {
            log.debug("Sending HTTP request to retrieve NSDs.");

            ResponseEntity<SoNsQueryResponse> httpResponse =
                    restTemplate.exchange(url, HttpMethod.GET, getEntity, SoNsQueryResponse.class);

            log.debug("Response code: " + httpResponse.getStatusCode().toString());
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.OK)) {
                log.debug("NSDs correctly retrieved");
                return httpResponse.getBody();
            } else {
                throw new FailedOperationException("Generic error during NSDs retrieval at NFVO: " + httpResponse.getBody());
            }
        } catch (RestClientException e) {
            log.debug("Error while interacting with NFVO.");
            throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
        }
    }

    //********************** VNF package methods ********************************//

    public OnBoardVnfPackageResponse onBoardVnfPackage(String urlTemplate, OnBoardVnfPackageRequest request)
            throws AlreadyExistingEntityException, FailedOperationException, MalformattedElementException {
        log.debug("Building HTTP request to onboard VNF package.");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        HttpEntity<?> postEntity = new HttpEntity<>(request, header);

        String url = this.smUrl + urlTemplate;

        try {
            log.debug("Sending HTTP request to onboard VNF package.");

            ResponseEntity<OnBoardVnfPackageResponse> httpResponse =
                    restTemplate.exchange(url, HttpMethod.POST, postEntity, OnBoardVnfPackageResponse.class);

            log.debug("Response code: " + httpResponse.getStatusCode().toString());
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.OK)) {
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

    public QueryOnBoardedVnfPkgInfoResponse queryVnfPackageInfo(String urlTemplate, GeneralizedQueryRequest request)
            throws NotExistingEntityException, MalformattedElementException {
        log.debug("Building HTTP request to query VNF package.");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        HttpEntity<?> getEntity = new HttpEntity<>(request, header);
        String vnfdId = request.getFilter().getParameters().get("VNFD_ID");
        Map<String, String> parameters = request.getFilter().getParameters();
        String vnfdVersion = "NONE";
        if( parameters.containsKey("VNFD_VERSION")){
            vnfdVersion = parameters.get("VNFD_VERSION");
        }

        String url = this.smUrl + urlTemplate.replaceAll("\\{vnfdid\\}", vnfdId);
        url = url.replaceAll("\\{vnfdversion\\}", vnfdVersion);

        try {
            log.debug("Sending HTTP request to retrieve VNF Package.");

            log.debug("VNFD_ID:" + vnfdId + " VNFD_VERSION:" + vnfdVersion + " GET: "+url);
            ResponseEntity<QueryOnBoardedVnfPkgInfoResponse> httpResponse =
                    restTemplate.exchange(url, HttpMethod.GET, getEntity, QueryOnBoardedVnfPkgInfoResponse.class);

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

    public SoVnfQueryResponse queryVnfPackagesInfo() throws FailedOperationException{
        log.debug("Building HTTP request to query VNF packages.");
        HttpHeaders header = new HttpHeaders();
        header.add("Accept", "application/json");
        HttpEntity<?> getEntity = new HttpEntity<>(null, header);

        String url = this.smUrl + "/ns/vnfd";

        try {
            log.debug("Sending HTTP request to retrieve VNF Packages.");

            ResponseEntity<SoVnfQueryResponse> httpResponse =
                    restTemplate.exchange(url, HttpMethod.GET, getEntity, SoVnfQueryResponse.class);

            log.debug("Response code: " + httpResponse.getStatusCode().toString());
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.OK)) {
                log.debug("VNF packages correctly retrieved");
                return httpResponse.getBody();
            } else {
                throw new FailedOperationException("Generic error during VNF packages retrieval at NFVO: " + httpResponse.getBody());
            }
        } catch (RestClientException e) {
            log.debug("Error while interacting with NFVO.");
            throw new FailedOperationException("Error while interacting with NFVO VNFD catalogue at url " + url);
        }
    }
}
