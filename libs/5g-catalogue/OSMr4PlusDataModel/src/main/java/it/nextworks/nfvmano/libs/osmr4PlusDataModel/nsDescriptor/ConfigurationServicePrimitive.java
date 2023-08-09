package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationServicePrimitive {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer seq;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user-defined-script")
    private String userDefinedScript;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("parameter")
    private List<ConfigurationServicePrimitiveParameter> parameterList;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserDefinedScript() {
        return userDefinedScript;
    }

    public void setUserDefinedScript(String userDefinedScript) {
        this.userDefinedScript = userDefinedScript;
    }

    public List<ConfigurationServicePrimitiveParameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ConfigurationServicePrimitiveParameter> parameterList) {
        this.parameterList = parameterList;
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
        return "ConfigurationServicePrimitive{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", userDefinedScript='" + userDefinedScript + '\'' +
                ", parameterList=" + parameterList +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
