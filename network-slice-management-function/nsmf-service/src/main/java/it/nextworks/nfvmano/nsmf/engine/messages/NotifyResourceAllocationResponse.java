package it.nextworks.nfvmano.nsmf.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;

public class NotifyResourceAllocationResponse extends NsmfEngineMessage{

    private ResourceAllocationComputeResponse response;

    public ResourceAllocationComputeResponse getResponse() {
        return response;
    }



    @JsonCreator
    public NotifyResourceAllocationResponse(@JsonProperty("response")ResourceAllocationComputeResponse response) {
        this.type=NsmfEngineMessageType.NOTIFY_RESOURCE_ALLOCATION_RESPONSE;
        this.response = response;
    }
}
