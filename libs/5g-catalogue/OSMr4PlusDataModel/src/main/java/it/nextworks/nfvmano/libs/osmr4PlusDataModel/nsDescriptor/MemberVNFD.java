package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class MemberVNFD {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vnf-index-ref")
    private Integer indexReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-id-ref")
    private String reference;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getIndexReference() {
        return indexReference;
    }

    public void setIndexReference(Integer indexReference) {
        this.indexReference = indexReference;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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
        return "MemberVNFD{" +
                "indexReference=" + indexReference +
                ", reference='" + reference + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
