package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class InternalVld {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String shortName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("root-bandwidth")
    private Integer rootBandwidth;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("leaf-bandwidth")
    private Integer leafBandwidth;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("internal-connection-point")
    private InternalConnectionPointVld internalConnectionPoint;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("provider-network")
    private ProviderNetwork providerNetwork;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vim-network-name")
    private String vimNetworkName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ip-profile-ref")
    private String ipProfileRef;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRootBandwidth() {
        return rootBandwidth;
    }

    public void setRootBandwidth(Integer rootBandwidth) {
        this.rootBandwidth = rootBandwidth;
    }

    public Integer getLeafBandwidth() {
        return leafBandwidth;
    }

    public void setLeafBandwidth(Integer leafBandwidth) {
        this.leafBandwidth = leafBandwidth;
    }

    public InternalConnectionPointVld getInternalConnectionPoint() {
        return internalConnectionPoint;
    }

    public void setInternalConnectionPoint(InternalConnectionPointVld internalConnectionPoint) {
        this.internalConnectionPoint = internalConnectionPoint;
    }

    public ProviderNetwork getProviderNetwork() {
        return providerNetwork;
    }

    public void setProviderNetwork(ProviderNetwork providerNetwork) {
        this.providerNetwork = providerNetwork;
    }

    public String getVimNetworkName() {
        return vimNetworkName;
    }

    public void setVimNetworkName(String vimNetworkName) {
        this.vimNetworkName = vimNetworkName;
    }

    public String getIpProfileRef() {
        return ipProfileRef;
    }

    public void setIpProfileRef(String ipProfileRef) {
        this.ipProfileRef = ipProfileRef;
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
        return "InternalVld{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", rootBandwidth=" + rootBandwidth +
                ", leafBandwidth=" + leafBandwidth +
                ", internalConnectionPoint=" + internalConnectionPoint +
                ", providerNetwork=" + providerNetwork +
                ", vimNetworkName='" + vimNetworkName + '\'' +
                ", ipProfileRef='" + ipProfileRef + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
