package it.nextworks.nfvmano.nsmf.sbi.dummy;

import it.nextworks.nfvmano.libs.vs.common.exceptions.*;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NssiNotifType;
import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.NsmfNotificationMessage;
import it.nextworks.nfvmano.libs.vs.common.nssmf.elements.NssiStatus;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.ComputeNssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.nsmf.sbi.NssmfRestClient;
import it.nextworks.nfvmano.nsmf.sbi.messages.InternalInstantiateNssiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class DummyNssmfClient extends NssmfRestClient {
    private final static Logger log= LoggerFactory.getLogger(DummyNssmfClient.class);

    private String baseUrl;

    private RestTemplate restTemplate;

    private String authCookie;

    public DummyNssmfClient(String url) {
        super(url);
        this.baseUrl=url;
        this.restTemplate=new RestTemplate();
    }

    @Override
    public UUID createNetworkSubSliceIdentifier() throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        return UUID.randomUUID();
    }

    @Override
    public void instantiateNetworkSubSlice(NssmfBaseProvisioningMessage request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException, AlreadyExistingEntityException, NotExistingEntityException {
        if(request instanceof InternalInstantiateNssiRequest) {
            InternalInstantiateNssiRequest internalRequest = (InternalInstantiateNssiRequest) request;
            ResourceAllocationComputeResponse raResponse = internalRequest.getResourceAllocationComputeResponse();
            Optional<NssResourceAllocation> allocation = raResponse.getNsResourceAllocation().getNssResourceAllocations().stream()
                    .filter(nssA -> nssA.getNsstId().equals(internalRequest.getNsst().getNsstId()))
                    .findFirst();
            if (allocation.isPresent()) {
                log.debug("Received request to instantiate nssi with ID {}", request.getNssiId());
                log.debug("Creation of NS with ID {}", internalRequest.getNsst().getNsdInfo().getNsdId());
                Map<String, String> vnfPlacement=((ComputeNssResourceAllocation) allocation.get()).getVnfPlacement();

                for(String vnfdId: vnfPlacement.keySet()){
                    log.debug("VNF with ID {} placed on node {}", vnfdId, vnfPlacement.get(vnfdId));
                }

                NsmfNotificationMessage notificationMessage=new NsmfNotificationMessage(request.getNssiId(), NssiNotifType.STATUS_CHANGED, NssiStatus.INSTANTIATED);
                notifyNsmf(notificationMessage, request.getNssiId().toString());
            } else
                throw new FailedOperationException("Could not find allocation for NSST:" + internalRequest.getNsst().getNsstId());
        } else
            throw  new MethodNotImplementedException("Instantiate network sub slice method not implemented for generic message");
    }

    @Override
    public void terminateNetworkSliceInstance(NssmfBaseProvisioningMessage request) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("Receive request to terminate NSSI with ID {}", request.getNssiId().toString());
        log.debug("Deleted NSSI with ID {}", request.getNssiId().toString());
        NsmfNotificationMessage notificationMessage=new NsmfNotificationMessage(request.getNssiId(), NssiNotifType.STATUS_CHANGED, NssiStatus.TERMINATED);
        notifyNsmf(notificationMessage, request.getNssiId().toString());
    }

    private void notifyNsmf(NsmfNotificationMessage notificationMessage, String nssiId){
        boolean auth=true; //authenticate("admin", "admin");
        log.debug("Authenticated "+auth);
        if(auth) {
            //if (this.authCookie != null) {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            header.add("Cookie", this.authCookie);

            HttpEntity<?> postEntity = new HttpEntity<>(notificationMessage, header);

            String url = baseUrl + "/vs/basic/nslcm/nss/"+nssiId+"/notify";

            log.debug("URL where notify {}", url);
            log.debug(postEntity.getBody().toString());
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
                log.debug("RestClientException response: Message " + e.getMessage());
            }
            //} else log.debug("Authentication Required!");
        }
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
                this.authCookie = headersResp.getFirst(HttpHeaders.SET_COOKIE);
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
}
