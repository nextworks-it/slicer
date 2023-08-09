package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

public class CPUFeature {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String feature;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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
        return "CPUFeature{" +
                "feature='" + feature + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
