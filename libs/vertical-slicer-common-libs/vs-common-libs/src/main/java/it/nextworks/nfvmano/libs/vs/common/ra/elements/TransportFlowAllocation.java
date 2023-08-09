package it.nextworks.nfvmano.libs.vs.common.ra.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransportFlowAllocation {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TransportFlowType transportFlowType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String defaultGw;

    private boolean active;


    @JsonCreator
    public TransportFlowAllocation(
            @JsonProperty("transportFlowType") TransportFlowType transportFlowType,
            @JsonProperty("defaultGw") String defaultGw,
            @JsonProperty("active") boolean active) {
        this.transportFlowType = transportFlowType;
        this.active = active;
        this.defaultGw = defaultGw;
    }

    public boolean isActive() {
        return active;
    }

    public TransportFlowType getTransportFlowType() {
        return transportFlowType;
    }

    public String getDefaultGw() {
        return defaultGw;
    }
}
