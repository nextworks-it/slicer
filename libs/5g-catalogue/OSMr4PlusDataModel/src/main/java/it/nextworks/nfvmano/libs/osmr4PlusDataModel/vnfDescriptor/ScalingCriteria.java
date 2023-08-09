package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ScalingCriteria {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("scale-in-threshold")
    private Integer scaleInThreshold;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("scale-in-relational-operation")
    private String scaleInRelationalOperation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("scale-out-threshold")
    private Integer scaleOutThreshold;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("scale-out-relational-operation")
    private String scaleOutRelationalOperation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnf-monitoring-param-ref")
    private String vnfMonitoringParamRef;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScaleInThreshold() {
        return scaleInThreshold;
    }

    public void setScaleInThreshold(Integer scaleInThreshold) {
        this.scaleInThreshold = scaleInThreshold;
    }

    public String getScaleInRelationalOperation() {
        return scaleInRelationalOperation;
    }

    public void setScaleInRelationalOperation(String scaleInRelationalOperation) {
        this.scaleInRelationalOperation = scaleInRelationalOperation;
    }

    public Integer getScaleOutThreshold() {
        return scaleOutThreshold;
    }

    public void setScaleOutThreshold(Integer scaleOutThreshold) {
        this.scaleOutThreshold = scaleOutThreshold;
    }

    public String getScaleOutRelationalOperation() {
        return scaleOutRelationalOperation;
    }

    public void setScaleOutRelationalOperation(String scaleOutRelationalOperation) {
        this.scaleOutRelationalOperation = scaleOutRelationalOperation;
    }

    public String getVnfMonitoringParamRef() {
        return vnfMonitoringParamRef;
    }

    public void setVnfMonitoringParamRef(String vnfMonitoringParamRef) {
        this.vnfMonitoringParamRef = vnfMonitoringParamRef;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "ScalingCriteria{" +
                "name='" + name + '\'' +
                ", scaleInThreshold=" + scaleInThreshold +
                ", scaleInRelationalOperation='" + scaleInRelationalOperation + '\'' +
                ", scaleOutThreshold=" + scaleOutThreshold +
                ", scaleOutRelationalOperation='" + scaleOutRelationalOperation + '\'' +
                ", vnfMonitoringParamRef='" + vnfMonitoringParamRef + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
