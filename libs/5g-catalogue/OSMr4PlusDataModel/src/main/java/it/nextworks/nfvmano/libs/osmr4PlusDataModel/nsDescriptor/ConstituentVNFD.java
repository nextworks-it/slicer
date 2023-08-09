package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class ConstituentVNFD {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vnf-index")
    private Integer memberVNFIndex;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-id-ref")
    private String vnfdIdentifierReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("start-by-default")
    private boolean startByDefault;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getMemberVNFIndex() {
        return memberVNFIndex;
    }

    public void setMemberVNFIndex(Integer memberVNFIndex) {
        this.memberVNFIndex = memberVNFIndex;
    }

    public String getVnfdIdentifierReference() {
        return vnfdIdentifierReference;
    }

    public void setVnfdIdentifierReference(String vnfdIdentifierReference) {
        this.vnfdIdentifierReference = vnfdIdentifierReference;
    }

    public boolean isStartByDefault() {
        return startByDefault;
    }

    public void setStartByDefault(boolean startByDefault) {
        this.startByDefault = startByDefault;
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
        return "ConstituentVNFD{" +
                "memberVNFIndex=" + memberVNFIndex +
                ", vnfdIdentifierReference='" + vnfdIdentifierReference + '\'' +
                ", startByDefault=" + startByDefault +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
