package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Classifier {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("rsp-id-ref")
    private String rspIdRef;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("match-attributes")
    private List<MatchAttributes> matchAttributesList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vnf-index-ref")
    private Integer indexReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-connection-point-ref")
    private String vnfdConnectionPointReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-id-ref")
    private String vnfdIdReference;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRspIdRef() {
        return rspIdRef;
    }

    public void setRspIdRef(String rspIdRef) {
        this.rspIdRef = rspIdRef;
    }

    public List<MatchAttributes> getMatchAttributesList() {
        return matchAttributesList;
    }

    public void setMatchAttributesList(List<MatchAttributes> matchAttributesList) {
        this.matchAttributesList = matchAttributesList;
    }

    public Integer getIndexReference() {
        return indexReference;
    }

    public void setIndexReference(Integer indexReference) {
        this.indexReference = indexReference;
    }

    public String getVnfdConnectionPointReference() {
        return vnfdConnectionPointReference;
    }

    public void setVnfdConnectionPointReference(String vnfdConnectionPointReference) {
        this.vnfdConnectionPointReference = vnfdConnectionPointReference;
    }

    public String getVnfdIdReference() {
        return vnfdIdReference;
    }

    public void setVnfdIdReference(String vnfdIdReference) {
        this.vnfdIdReference = vnfdIdReference;
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
        return "Classifier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rspIdRef='" + rspIdRef + '\'' +
                ", matchAttributesList=" + matchAttributesList +
                ", indexReference=" + indexReference +
                ", vnfdConnectionPointReference='" + vnfdConnectionPointReference + '\'' +
                ", vnfdIdReference='" + vnfdIdReference + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
