package it.nextworks.nfvmano.nsmf.sbi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces.NsmfLcmConfigInterface;
import it.nextworks.nfvmano.libs.vs.common.nsmf.interfaces.NsmfLcmProvisioningInterface;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.CreateNsiIdRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.InstantiateNsiRequest;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning.TerminateNsiRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.interfaces.NssmfLcmConfigInterface;
import it.nextworks.nfvmano.libs.vs.common.nssmf.interfaces.NssmfLcmProvisioningInterface;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration.RemoveConfigRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration.SetConfigRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.configuration.UpdateConfigRequest;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.query.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.nsmf.NsLcmService;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalInstantiateNssiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

public class NssmfRestClient implements NssmfLcmProvisioningInterface, NssmfLcmConfigInterface {

    private RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(NssmfRestClient.class);
    private String baseUrl;

    public NssmfRestClient(String url){
        restTemplate = new RestTemplate();
        this.baseUrl = url;

    }

    @Override
    public void setNetworkSubSliceConfiguration(SetConfigRequest request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, NotExistingEntityException, AlreadyExistingEntityException {

    }

    @Override
    public void updateNetworkSubSliceConfiguration(UpdateConfigRequest request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, NotExistingEntityException {

    }

    @Override
    public void removeNetworkSubSliceConfiguration(RemoveConfigRequest request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, NotExistingEntityException {

    }

    @Override
    public UUID createNetworkSubSliceIdentifier() throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        log.debug("Received request to create a network subslice identifier");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        HttpEntity<?> postEntity = new HttpEntity<>(header);

        String url = baseUrl + "/nss/createnssid";
        try {
            ResponseEntity<UUID> httpResponse =
                    restTemplate.exchange(url, HttpMethod.POST, postEntity, UUID.class);

            log.debug("Response code: " + httpResponse.getStatusCode().toString());
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.CREATED)) {
                log.debug("Network subslice identifier created");

                UUID nssi = httpResponse.getBody();
                log.debug("NSSI_ID: "+nssi.toString());

                return nssi;
            } else {
                throw new FailedOperationException("Generic error during NssmfRestClient interaction with NSSMF");
            }

        }catch (Exception e){
            log.error("Generic error during NssmfRestClient interaction with NSSMF",e);
            throw new FailedOperationException("Generic error during NssmfRestClient interaction with NSSMF");
        }

    }

    @Override
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, AlreadyExistingEntityException, NotExistingEntityException {


        log.debug("Received request to instantiate a network subslice identifier "+request.getNssiId());
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug(mapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.warn("error deserializing request:",e);
        }
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        HttpEntity<?> postEntity = new HttpEntity<>(request, header);

        String url = baseUrl + "/nss/"+request.getNssiId()+"/action/instantiate";
        try {
            ResponseEntity<UUID> httpResponse =
                    restTemplate.exchange(url, HttpMethod.PUT, postEntity, UUID.class);

            log.debug("Response code: " + httpResponse.getStatusCode().toString());
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.ACCEPTED)) {
                log.debug("Triggered network slice subnet instantiation");



            } else {
                throw new FailedOperationException("Generic error during NssmfRestClient interaction with NSSMF");
            }

        }catch (Exception e){
            log.error("Generic error during NssmfRestClient interaction with NSSMF",e);
            throw new FailedOperationException("Generic error during NssmfRestClient interaction with NSSMF");
        }

    }

    @Override
    public void modifyNetworkSlice(NssmfBaseProvisioningMessage request) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("Received request to modify a network subslice "+request.getNssiId());
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug(mapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.warn("error deserializing request:",e);
        }
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        HttpEntity<?> postEntity = new HttpEntity<>(request, header);

        String url = baseUrl + "/nss/"+request.getNssiId()+"/action/modify";
        try {
            ResponseEntity<UUID> httpResponse =
                    restTemplate.exchange(url, HttpMethod.PUT, postEntity, UUID.class);

            log.debug("Response code: " + httpResponse.getStatusCode().toString());
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.ACCEPTED)) {
                log.debug("Triggered network slice subnet modification");

            } else {
                throw new FailedOperationException("Generic error during NssmfRestClient interaction with NSSMF");
            }

        }catch (Exception e){
            log.error("Generic error during NssmfRestClient interaction with NSSMF",e);
            throw new FailedOperationException("Generic error during NssmfRestClient interaction with NSSMF");
        }

    }

    @Override
    public void terminateNetworkSliceInstance(NssmfBaseProvisioningMessage request) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("Received request to terminate a network subslice"+request.getNssiId());
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug(mapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.warn("error deserializing request:",e);
        }
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        HttpEntity<?> postEntity = new HttpEntity<>(request, header);

        String url = baseUrl + "/nss/"+request.getNssiId()+"/action/terminate";
        try {
            ResponseEntity<UUID> httpResponse =
                    restTemplate.exchange(url, HttpMethod.PUT, postEntity, UUID.class);

            log.debug("Response code: " + httpResponse.getStatusCode().toString());
            HttpStatus code = httpResponse.getStatusCode();

            if (code.equals(HttpStatus.ACCEPTED)) {
                log.debug("Triggered network slice subnet termination");

            } else {
                throw new FailedOperationException("Generic error during NssmfRestClient interaction with NSSMF");
            }

        }catch (Exception e){
            log.error("Generic error during NssmfRestClient interaction with NSSMF",e);
            throw new FailedOperationException("Generic error during NssmfRestClient interaction with NSSMF");
        }
    }
}
