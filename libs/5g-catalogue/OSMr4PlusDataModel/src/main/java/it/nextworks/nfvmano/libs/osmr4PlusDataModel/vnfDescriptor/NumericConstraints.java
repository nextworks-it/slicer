package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class NumericConstraints {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("min-value")
    private Integer minValue;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("max-value")
    private Integer maxValue;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
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
        return "NumericConstraints{" +
                "minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", otherProperties=" + otherProperties +
                '}';
    }
}