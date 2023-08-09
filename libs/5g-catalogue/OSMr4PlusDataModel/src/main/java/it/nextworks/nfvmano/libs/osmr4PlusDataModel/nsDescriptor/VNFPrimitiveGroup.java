package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNFPrimitiveGroup {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vnf-index-ref")
    private Integer indexReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-id-ref")
    private String reference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-name")
    private String vnfdName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Primitive> primitive;

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

    public String getVnfdName() {
        return vnfdName;
    }

    public void setVnfdName(String vnfdName) {
        this.vnfdName = vnfdName;
    }

    public List<Primitive> getPrimitive() {
        return primitive;
    }

    public void setPrimitive(List<Primitive> primitive) {
        this.primitive = primitive;
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
        return "VNFPrimitiveGroup{" +
                "indexReference=" + indexReference +
                ", reference='" + reference + '\'' +
                ", vnfdName='" + vnfdName + '\'' +
                ", primitive=" + primitive +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
