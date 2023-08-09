package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ParameterPoolRange {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("start-value")
    private Integer startValue;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("end-value")
    private Integer endValue;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getStartValue() {
        return startValue;
    }

    public void setStartValue(Integer startValue) {
        this.startValue = startValue;
    }

    public Integer getEndValue() {
        return endValue;
    }

    public void setEndValue(Integer endValue) {
        this.endValue = endValue;
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
        return "ParameterPoolRange{" +
                "startValue=" + startValue +
                ", endValue=" + endValue +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
