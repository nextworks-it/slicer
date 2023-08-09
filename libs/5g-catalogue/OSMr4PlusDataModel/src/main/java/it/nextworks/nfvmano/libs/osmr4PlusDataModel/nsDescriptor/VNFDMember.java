package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class VNFDMember {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vnf-index-ref")
    private Integer indexReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getIndexReference() {
        return indexReference;
    }

    public void setIndexReference(Integer indexReference) {
        this.indexReference = indexReference;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        return "VNFDMember{" +
                "indexReference=" + indexReference +
                ", count=" + count +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
