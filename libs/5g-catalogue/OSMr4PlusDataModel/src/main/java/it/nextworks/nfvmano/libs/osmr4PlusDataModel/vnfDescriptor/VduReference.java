package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class VduReference {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vdu-id-ref")
    private String vduIdRef;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getVduIdRef() {
        return vduIdRef;
    }

    public void setVduIdRef(String vduIdRef) {
        this.vduIdRef = vduIdRef;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        return "Vdu{" +
                "vduIdRef='" + vduIdRef + '\'' +
                ", count=" + count +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
