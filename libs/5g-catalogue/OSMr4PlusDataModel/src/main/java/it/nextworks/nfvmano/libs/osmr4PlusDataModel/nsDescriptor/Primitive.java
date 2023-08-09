package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

public class Primitive {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer index;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Primitive{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
