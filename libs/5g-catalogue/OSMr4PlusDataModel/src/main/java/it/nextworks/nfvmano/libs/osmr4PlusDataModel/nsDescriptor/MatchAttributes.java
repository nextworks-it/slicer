package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class MatchAttributes {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ip-proto")
    private Integer ipProtocol;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("source-ip-address")
    private String sourceIPAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("destination-ip-address")
    private String destinationIPAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("source-port")
    private String sourcePort;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("destination-port")
    private String destinationPort;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIpProtocol() {
        return ipProtocol;
    }

    public void setIpProtocol(Integer ipProtocol) {
        this.ipProtocol = ipProtocol;
    }

    public String getSourceIPAddress() {
        return sourceIPAddress;
    }

    public void setSourceIPAddress(String sourceIPAddress) {
        this.sourceIPAddress = sourceIPAddress;
    }

    public String getDestinationIPAddress() {
        return destinationIPAddress;
    }

    public void setDestinationIPAddress(String destinationIPAddress) {
        this.destinationIPAddress = destinationIPAddress;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
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
        return "MatchAttributes{" +
                "id='" + id + '\'' +
                ", ipProtocol=" + ipProtocol +
                ", sourceIPAddress='" + sourceIPAddress + '\'' +
                ", destinationIPAddress='" + destinationIPAddress + '\'' +
                ", sourcePort='" + sourcePort + '\'' +
                ", destinationPort='" + destinationPort + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
