package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class VNFDConnectionPointReference {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vnf-index-ref")
    private Integer indexReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-connection-point-ref")
    private String vnfdConnectionPointReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-id-ref")
    private String vnfdIdReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ip-address")
    private String ipAddres;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

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

    public String getIpAddres() {
        return ipAddres;
    }

    public void setIpAddres(String ipAddres) {
        this.ipAddres = ipAddres;
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
        return "VNFDConnectionPointReference{" +
                "indexReference=" + indexReference +
                ", vnfdConnectionPointReference='" + vnfdConnectionPointReference + '\'' +
                ", vnfdIdReference='" + vnfdIdReference + '\'' +
                ", ipAddres='" + ipAddres + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
