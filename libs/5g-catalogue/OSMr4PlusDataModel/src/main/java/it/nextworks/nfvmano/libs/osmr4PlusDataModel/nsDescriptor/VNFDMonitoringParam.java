package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class VNFDMonitoringParam {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vnf-index-ref")
    private Integer indexReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-id-ref")
    private String vnfdIdRef;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-monitoring-param-ref")
    private String vnfdMonitoringParamRef;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getIndexReference() {
        return indexReference;
    }

    public void setIndexReference(Integer indexReference) {
        this.indexReference = indexReference;
    }

    public String getVnfdIdRef() {
        return vnfdIdRef;
    }

    public void setVnfdIdRef(String vnfdIdRef) {
        this.vnfdIdRef = vnfdIdRef;
    }

    public String getVnfdMonitoringParamRef() {
        return vnfdMonitoringParamRef;
    }

    public void setVnfdMonitoringParamRef(String vnfdMonitoringParamRef) {
        this.vnfdMonitoringParamRef = vnfdMonitoringParamRef;
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
        return "VNFDMonitoringParam{" +
                "indexReference=" + indexReference +
                ", vnfdIdRef='" + vnfdIdRef + '\'' +
                ", vnfdMonitoringParamRef='" + vnfdMonitoringParamRef + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
