package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

public class VLD {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("short-name")
    private String shortName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String vendor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String version;
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
    @JsonProperty("provider-network")
    private ProviderNetwork providerNetwork;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("mgmt-network")
    private boolean mgmtNetwork;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vim-network-name")
    private String vimNetworkName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ip-profile-ref")
    private String ipProfileRef;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("vnfd-connection-point-ref")
    private List<VNFDConnectionPointReference> vnfdConnectionPointReferences;

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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public ProviderNetwork getProviderNetwork() {
        return providerNetwork;
    }

    public void setProviderNetwork(ProviderNetwork providerNetwork) {
        this.providerNetwork = providerNetwork;
    }

    public boolean isMgmtNetwork() {
        return mgmtNetwork;
    }

    public void setMgmtNetwork(boolean mgmtNetwork) {
        this.mgmtNetwork = mgmtNetwork;
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

    public List<VNFDConnectionPointReference> getVnfdConnectionPointReferences() {
        return vnfdConnectionPointReferences;
    }

    public void setVnfdConnectionPointReferences(List<VNFDConnectionPointReference> vnfdConnectionPointReferences) {
        this.vnfdConnectionPointReferences = vnfdConnectionPointReferences;
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
        return "VLD{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", vendor='" + vendor + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", rootBandwidth=" + rootBandwidth +
                ", leafBandwidth=" + leafBandwidth +
                ", providerNetwork=" + providerNetwork +
                ", mgmtNetwork=" + mgmtNetwork +
                ", vimNetworkName='" + vimNetworkName + '\'' +
                ", ipProfileRef='" + ipProfileRef + '\'' +
                ", vnfdConnectionPointReferences=" + vnfdConnectionPointReferences +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
