package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class TextConstraints {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("min-length")
    private Integer minLength;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("max-length")
    private Integer maxLength;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
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
        return "TextConstraints{" +
                "minLength=" + minLength +
                ", maxLength=" + maxLength +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
