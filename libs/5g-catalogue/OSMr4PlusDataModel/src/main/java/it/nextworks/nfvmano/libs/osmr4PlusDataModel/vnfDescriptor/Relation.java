package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

public class Relation {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requires;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String provides;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getRequires() {
        return requires;
    }

    public void setRequires(String requires) {
        this.requires = requires;
    }

    public String getProvides() {
        return provides;
    }

    public void setProvides(String provides) {
        this.provides = provides;
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
        return "Relation{" +
                "requires='" + requires + '\'' +
                ", provides='" + provides + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
