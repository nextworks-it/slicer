package it.nextworks.nfvmano.libs.vs.common.nsmf.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.TransportFlowType;

import java.util.HashMap;
import java.util.Map;

public class TransportConfig {

    private TransportFlowType target;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String satelliteGw;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> acmConfig;

    @JsonCreator
    public TransportConfig(@JsonProperty("target") TransportFlowType target,
                           @JsonProperty("satelliteGw") String satelliteGw,
                           @JsonProperty("acmConfig") Map<String, String> acmConfig) {
        this.target = target;
        this.satelliteGw = satelliteGw;
        this.acmConfig = acmConfig;
    }

    public TransportFlowType getTarget() {
        return target;
    }

    public String getSatelliteGw() {
        return satelliteGw;
    }

    public Map<String, String> getAcmConfig() {
        return acmConfig;
    }
}
