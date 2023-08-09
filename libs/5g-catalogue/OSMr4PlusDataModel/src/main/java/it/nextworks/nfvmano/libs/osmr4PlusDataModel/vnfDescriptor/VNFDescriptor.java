package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNFDescriptor {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("short-name")
    private String shortName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String logo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String vendor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String version;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnf-configuration")
    private VNFConfiguration vnfConfiguration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("operational-status")
    private String operationalStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("mgmt-interface")
    private ManagementInterface managementInterface;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("internal-vld")
    private List<InternalVld> internalVld;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("ip-profiles")
    private List<IpProfiles> ipProfiles;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("connection-point")
    private List<ConnectionPoint> connectionPoints;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("vdu")
    private List<VDU> vduList;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("vdu-dependency")
    private List<VDUDependency> vduDependecies;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("service-function-chain")
    private String serviceFunctionChain;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("service-function-type")
    private String serviceFunctionType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("http-endpoint")
    private List<HTTPEndpoint> httpEndpoints;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("scaling-group-descriptor")
    private List<ScalingGroupDescriptor> scalingGroupDescriptor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("monitoring-param")
    private MonitoringParam monitoringParam;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("placement-groups")
    private List<PlacementGroup> placementGroups;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public VNFConfiguration getVnfConfiguration() {
        return vnfConfiguration;
    }

    public void setVnfConfiguration(VNFConfiguration vnfConfiguration) {
        this.vnfConfiguration = vnfConfiguration;
    }

    public String getOperationalStatus() {
        return operationalStatus;
    }

    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    public ManagementInterface getManagementInterface() {
        return managementInterface;
    }

    public void setManagementInterface(ManagementInterface managementInterface) {
        this.managementInterface = managementInterface;
    }

    public List<InternalVld> getInternalVld() {
        return internalVld;
    }

    public void setInternalVld(List<InternalVld> internalVld) {
        this.internalVld = internalVld;
    }

    public List<IpProfiles> getIpProfiles() {
        return ipProfiles;
    }

    public void setIpProfiles(List<IpProfiles> ipProfiles) {
        this.ipProfiles = ipProfiles;
    }

    public List<ConnectionPoint> getConnectionPoints() {
        return connectionPoints;
    }

    public void setConnectionPoints(List<ConnectionPoint> connectionPoints) {
        this.connectionPoints = connectionPoints;
    }

    public List<VDU> getVduList() {
        return vduList;
    }

    public void setVduList(List<VDU> vduList) {
        this.vduList = vduList;
    }

    public List<VDUDependency> getVduDependecies() {
        return vduDependecies;
    }

    public void setVduDependecies(List<VDUDependency> vduDependecies) {
        this.vduDependecies = vduDependecies;
    }

    public String getServiceFunctionChain() {
        return serviceFunctionChain;
    }

    public void setServiceFunctionChain(String serviceFunctionChain) {
        this.serviceFunctionChain = serviceFunctionChain;
    }

    public String getServiceFunctionType() {
        return serviceFunctionType;
    }

    public void setServiceFunctionType(String serviceFunctionType) {
        this.serviceFunctionType = serviceFunctionType;
    }

    public List<HTTPEndpoint> getHttpEndpoints() {
        return httpEndpoints;
    }

    public void setHttpEndpoints(List<HTTPEndpoint> httpEndpoints) {
        this.httpEndpoints = httpEndpoints;
    }

    public List<ScalingGroupDescriptor> getScalingGroupDescriptor() {
        return scalingGroupDescriptor;
    }

    public void setScalingGroupDescriptor(List<ScalingGroupDescriptor> scalingGroupDescriptor) {
        this.scalingGroupDescriptor = scalingGroupDescriptor;
    }

    public MonitoringParam getMonitoringParam() {
        return monitoringParam;
    }

    public void setMonitoringParam(MonitoringParam monitoringParam) {
        this.monitoringParam = monitoringParam;
    }

    public List<PlacementGroup> getPlacementGroups() {
        return placementGroups;
    }

    public void setPlacementGroups(List<PlacementGroup> placementGroups) {
        this.placementGroups = placementGroups;
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
        return "VNFDescriptor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", logo='" + logo + '\'' +
                ", vendor='" + vendor + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", vnfConfiguration=" + vnfConfiguration +
                ", operationalStatus='" + operationalStatus + '\'' +
                ", managementInterface=" + managementInterface +
                ", internalVld=" + internalVld +
                ", ipProfiles=" + ipProfiles +
                ", connectionPoints=" + connectionPoints +
                ", vduList=" + vduList +
                ", vduDependecies=" + vduDependecies +
                ", serviceFunctionChain='" + serviceFunctionChain + '\'' +
                ", serviceFunctionType='" + serviceFunctionType + '\'' +
                ", httpEndpoints=" + httpEndpoints +
                ", scalingGroupDescriptor=" + scalingGroupDescriptor +
                ", monitoringParam=" + monitoringParam +
                ", placementGroups=" + placementGroups +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
