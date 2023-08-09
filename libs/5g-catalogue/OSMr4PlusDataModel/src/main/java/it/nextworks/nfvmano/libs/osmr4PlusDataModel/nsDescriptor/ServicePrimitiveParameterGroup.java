package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicePrimitiveParameterGroup {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ServicePrimitiveParameter> parameter;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean mandatory;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServicePrimitiveParameter> getParameter() {
        return parameter;
    }

    public void setParameter(List<ServicePrimitiveParameter> parameter) {
        this.parameter = parameter;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
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
        return "ServicePrimitiveParameterGroup{" +
                "name='" + name + '\'' +
                ", parameter=" + parameter +
                ", mandatory=" + mandatory +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
