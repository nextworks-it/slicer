package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class IPProfile {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ip-profile-params")
    private IPProfileParameters ipProfileParameters;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IPProfileParameters getIpProfileParameters() {
        return ipProfileParameters;
    }

    public void setIpProfileParameters(IPProfileParameters ipProfileParameters) {
        this.ipProfileParameters = ipProfileParameters;
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
        return "IPProfile{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ipProfileParameters=" + ipProfileParameters +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
