package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class VSwitchEpa {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ovs-acceleration")
    private String ovsAcceleration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ovs-offload")
    private String ovsOffload;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getOvsAcceleration() {
        return ovsAcceleration;
    }

    public void setOvsAcceleration(String ovsAcceleration) {
        this.ovsAcceleration = ovsAcceleration;
    }

    public String getOvsOffload() {
        return ovsOffload;
    }

    public void setOvsOffload(String ovsOffload) {
        this.ovsOffload = ovsOffload;
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
        return "VSwitchEpa{" +
                "ovsAcceleration='" + ovsAcceleration + '\'' +
                ", ovsOffload='" + ovsOffload + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
