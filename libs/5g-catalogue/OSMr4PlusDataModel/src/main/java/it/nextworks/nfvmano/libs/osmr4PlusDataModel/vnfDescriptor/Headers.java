package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

public class Headers {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String key;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        return "Headers{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
