package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ConnectionPoint {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("floating-ip-required")
    private boolean floatingIPRequired;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vld-id-ref")
    private String vldIdRef;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vnf-index-ref")
    private Integer memberVnfIndexRef;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-id-ref")
    private String vnfdIdRef;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-connection-point-ref")
    private String vnfdConnectionPointRef;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFloatingIPRequired() {
        return floatingIPRequired;
    }

    public void setFloatingIPRequired(boolean floatingIPRequired) {
        this.floatingIPRequired = floatingIPRequired;
    }

    public String getVldIdRef() {
        return vldIdRef;
    }

    public void setVldIdRef(String vldIdRef) {
        this.vldIdRef = vldIdRef;
    }

    public Integer getMemberVnfIndexRef() {
        return memberVnfIndexRef;
    }

    public void setMemberVnfIndexRef(Integer memberVnfIndexRef) {
        this.memberVnfIndexRef = memberVnfIndexRef;
    }

    public String getVnfdIdRef() {
        return vnfdIdRef;
    }

    public void setVnfdIdRef(String vnfdIdRef) {
        this.vnfdIdRef = vnfdIdRef;
    }

    public String getVnfdConnectionPointRef() {
        return vnfdConnectionPointRef;
    }

    public void setVnfdConnectionPointRef(String vnfdConnectionPointRef) {
        this.vnfdConnectionPointRef = vnfdConnectionPointRef;
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
        return "ConnectionPoint{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", floatingIPRequired=" + floatingIPRequired +
                ", vldIdRef='" + vldIdRef + '\'' +
                ", memberVnfIndexRef='" + memberVnfIndexRef + '\'' +
                ", vnfdIdRef='" + vnfdIdRef + '\'' +
                ", vnfdConnectionPointRef='" + vnfdConnectionPointRef + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
