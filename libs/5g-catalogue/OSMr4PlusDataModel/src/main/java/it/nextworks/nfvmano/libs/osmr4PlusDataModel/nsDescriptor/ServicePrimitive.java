package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicePrimitive {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ServicePrimitiveParameter> parameter;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("parameter-group")
    private List<ServicePrimitiveParameterGroup> parameterGroups;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("vnf-primitive-group")
    private List<VNFPrimitiveGroup> vnfPrimitiveGroups;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user-defined-script")
    private String userDefinedScript;

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

    public List<ServicePrimitiveParameterGroup> getParameterGroups() {
        return parameterGroups;
    }

    public void setParameterGroups(List<ServicePrimitiveParameterGroup> parameterGroups) {
        this.parameterGroups = parameterGroups;
    }

    public List<VNFPrimitiveGroup> getVnfPrimitiveGroups() {
        return vnfPrimitiveGroups;
    }

    public void setVnfPrimitiveGroups(List<VNFPrimitiveGroup> vnfPrimitiveGroups) {
        this.vnfPrimitiveGroups = vnfPrimitiveGroups;
    }

    public String getUserDefinedScript() {
        return userDefinedScript;
    }

    public void setUserDefinedScript(String userDefinedScript) {
        this.userDefinedScript = userDefinedScript;
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
        return "ServicePrimitive{" +
                "name='" + name + '\'' +
                ", parameter=" + parameter +
                ", parameterGroups=" + parameterGroups +
                ", vnfPrimitiveGroups=" + vnfPrimitiveGroups +
                ", userDefinedScript='" + userDefinedScript + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}