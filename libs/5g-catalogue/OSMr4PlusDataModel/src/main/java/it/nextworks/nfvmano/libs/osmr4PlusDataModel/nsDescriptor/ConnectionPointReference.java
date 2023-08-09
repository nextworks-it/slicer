package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ConnectionPointReference {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vnf-index-ref")
    private Integer indexReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-ingress-connection-point-ref")
    private String vnfdIngressConnectionPointReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-egress-connection-point-ref")
    private String vnfdEgressConnectionPointReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-id-ref")
    private String vnfdIdReference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer order;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getIndexReference() {
        return indexReference;
    }

    public void setIndexReference(Integer indexReference) {
        this.indexReference = indexReference;
    }

    public String getVnfdIngressConnectionPointReference() {
        return vnfdIngressConnectionPointReference;
    }

    public void setVnfdIngressConnectionPointReference(String vnfdIngressConnectionPointReference) {
        this.vnfdIngressConnectionPointReference = vnfdIngressConnectionPointReference;
    }

    public String getVnfdEgressConnectionPointReference() {
        return vnfdEgressConnectionPointReference;
    }

    public void setVnfdEgressConnectionPointReference(String vnfdEgressConnectionPointReference) {
        this.vnfdEgressConnectionPointReference = vnfdEgressConnectionPointReference;
    }

    public String getVnfdIdReference() {
        return vnfdIdReference;
    }

    public void setVnfdIdReference(String vnfdIdReference) {
        this.vnfdIdReference = vnfdIdReference;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
        return "ConnectionPointReference{" +
                "indexReference=" + indexReference +
                ", vnfdIngressConnectionPointReference='" + vnfdIngressConnectionPointReference + '\'' +
                ", vnfdEgressConnectionPointReference='" + vnfdEgressConnectionPointReference + '\'' +
                ", vnfdIdReference='" + vnfdIdReference + '\'' +
                ", order=" + order +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
