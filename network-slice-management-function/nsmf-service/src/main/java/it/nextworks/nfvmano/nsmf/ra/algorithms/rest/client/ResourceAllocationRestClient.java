package it.nextworks.nfvmano.nsmf.ra.algorithms.rest.client;

import it.nextworks.nfvmano.libs.vs.common.ra.elements.ExternalAlgorithmRequest;
import it.nextworks.nfvmano.libs.vs.common.utils.BaseRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

public class ResourceAllocationRestClient extends BaseRestClient {
    private static final Logger log = LoggerFactory.getLogger(ResourceAllocationRestClient.class);

    private String externalAlgorithmBaseUrl;
    private String cookies;

    public ResourceAllocationRestClient(){}

    public ResourceAllocationRestClient(String externalAlgorithmBaseUrl){
        this.externalAlgorithmBaseUrl=externalAlgorithmBaseUrl;
    }

    public String computeResourceAllocation(ExternalAlgorithmRequest request){
        log.debug("Sending request to compute resource allocation to external algorithm");
        String url=externalAlgorithmBaseUrl+"/computeResourceAllocation";
        ResponseEntity<String> httpResponse= performHTTPRequest(request, url, HttpMethod.POST);
        return manageHTTPResponse(httpResponse, "Error while computing resource allocation", "Resource allocation computation started", HttpStatus.ACCEPTED);
    }
}
