package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class VNFDependency {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnf-source-ref")
    private String vnfSourceRef;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnf-depends-on-ref")
    private String vnfDependsOnRef;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getVnfSourceRef() {
        return vnfSourceRef;
    }

    public void setVnfSourceRef(String vnfSourceRef) {
        this.vnfSourceRef = vnfSourceRef;
    }

    public String getVnfDependsOnRef() {
        return vnfDependsOnRef;
    }

    public void setVnfDependsOnRef(String vnfDependsOnRef) {
        this.vnfDependsOnRef = vnfDependsOnRef;
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
        return "VNFDependency{" +
                "vnfSourceRef='" + vnfSourceRef + '\'' +
                ", vnfDependsOnRef='" + vnfDependsOnRef + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
