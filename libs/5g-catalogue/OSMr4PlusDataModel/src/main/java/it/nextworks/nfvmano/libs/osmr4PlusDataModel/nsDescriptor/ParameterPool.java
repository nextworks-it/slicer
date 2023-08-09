package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterPool {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ParameterPoolRange> range;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParameterPoolRange> getRange() {
        return range;
    }

    public void setRange(List<ParameterPoolRange> range) {
        this.range = range;
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
        return "ParameterPool{" +
                "name='" + name + '\'' +
                ", range=" + range +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
