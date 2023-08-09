package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

public class VirtualInterface {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String vpci;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer bandwidth;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVpci() {
        return vpci;
    }

    public void setVpci(String vpci) {
        this.vpci = vpci;
    }

    public Integer getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Integer bandwidth) {
        this.bandwidth = bandwidth;
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
        return "VirtualInterface{" +
                "type='" + type + '\'' +
                ", vpci='" + vpci + '\'' +
                ", bandwidth=" + bandwidth +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
