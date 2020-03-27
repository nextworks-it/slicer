package it.nextworks.nfvmano.sebastian.nsmf.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sebastian.common.ActuationRequest;

public class ActuateNsiRequestMessage extends NsmfEngineMessage{

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("request")
    private ActuationRequest request;


    /**
     * Constructor
     *
     * @param request termination request
     * @param tenantId ID of the tenant requesting the network slice termination
     */
    @JsonCreator
    public ActuateNsiRequestMessage(@JsonProperty("request") ActuationRequest request,
                                    @JsonProperty("tenantId") String tenantId) {
        this.type = NsmfEngineMessageType.ACTUATE_NSI_REQUEST;
        this.tenantId = tenantId;
        this.request = request;
    }


    public ActuationRequest getRequest() {
        return request;
    }

    public String getTenantId() {
        return tenantId;
    }
}
