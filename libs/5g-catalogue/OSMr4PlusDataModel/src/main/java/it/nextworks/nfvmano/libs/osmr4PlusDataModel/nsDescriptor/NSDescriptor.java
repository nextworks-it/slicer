package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

public class NSDescriptor {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String logo;
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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("constituent-vnfd")
    private List<ConstituentVNFD> constituentVNFDs;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("input-parameter-xpath")
    private List<XPathInputParameter> xpathInputParameters;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("ip-profiles")
    private List<IPProfile> ipProfiles;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("placement-groups")
    private List<PlacementGroup> placementGroups;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("vld")
    private List<VLD> vldList;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("connection-point")
    private List<ConnectionPoint> connectionPoints;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("scaling-group-descriptor")
    private List<ScalingGroupDescriptor> scalingGroupDescriptors;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Vnffgd> vnffgd;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("initial-service-primitive")
    private List<ConfigurationServicePrimitive> initialServicePrimitives;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("terminate-service-primitive")
    private List<ConfigurationServicePrimitive> terminateServicePrimitives;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("parameter-pool")
    private List<ParameterPool> parameterPools;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("key-pair")
    private List<KeyPair> keyPairs;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<User> user;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("vnf-dependency")
    private List<VNFDependency> vnfDependencies;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("monitoring-param")
    private List<MonitoringParam> monitoringParams;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("service-primitive")
    private List<ServicePrimitive> servicePrimitives;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public List<ConstituentVNFD> getConstituentVNFDs() {
        return constituentVNFDs;
    }

    public void setConstituentVNFDs(List<ConstituentVNFD> constituentVNFDs) {
        this.constituentVNFDs = constituentVNFDs;
    }

    public List<XPathInputParameter> getXpathInputParameters() {
        return xpathInputParameters;
    }

    public void setXpathInputParameters(List<XPathInputParameter> xpathInputParameters) {
        this.xpathInputParameters = xpathInputParameters;
    }

    public List<IPProfile> getIpProfiles() {
        return ipProfiles;
    }

    public void setIpProfiles(List<IPProfile> ipProfiles) {
        this.ipProfiles = ipProfiles;
    }

    public List<PlacementGroup> getPlacementGroups() {
        return placementGroups;
    }

    public void setPlacementGroups(List<PlacementGroup> placementGroups) {
        this.placementGroups = placementGroups;
    }

    public List<VLD> getVldList() {
        return vldList;
    }

    public void setVldList(List<VLD> vldList) {
        this.vldList = vldList;
    }

    public List<ConnectionPoint> getConnectionPoints() {
        return connectionPoints;
    }

    public void setConnectionPoints(List<ConnectionPoint> connectionPoints) {
        this.connectionPoints = connectionPoints;
    }

    public List<ScalingGroupDescriptor> getScalingGroupDescriptors() {
        return scalingGroupDescriptors;
    }

    public void setScalingGroupDescriptors(List<ScalingGroupDescriptor> scalingGroupDescriptors) {
        this.scalingGroupDescriptors = scalingGroupDescriptors;
    }

    public List<Vnffgd> getVnffgd() {
        return vnffgd;
    }

    public void setVnffgd(List<Vnffgd> vnffgd) {
        this.vnffgd = vnffgd;
    }

    public List<ConfigurationServicePrimitive> getInitialServicePrimitives() {
        return initialServicePrimitives;
    }

    public void setInitialServicePrimitives(List<ConfigurationServicePrimitive> initialServicePrimitives) {
        this.initialServicePrimitives = initialServicePrimitives;
    }

    public List<ConfigurationServicePrimitive> getTerminateServicePrimitives() {
        return terminateServicePrimitives;
    }

    public void setTerminateServicePrimitives(List<ConfigurationServicePrimitive> terminateServicePrimitives) {
        this.terminateServicePrimitives = terminateServicePrimitives;
    }

    public List<ParameterPool> getParameterPools() {
        return parameterPools;
    }

    public void setParameterPools(List<ParameterPool> parameterPools) {
        this.parameterPools = parameterPools;
    }

    public List<KeyPair> getKeyPairs() {
        return keyPairs;
    }

    public void setKeyPairs(List<KeyPair> keyPairs) {
        this.keyPairs = keyPairs;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<VNFDependency> getVnfDependencies() {
        return vnfDependencies;
    }

    public void setVnfDependencies(List<VNFDependency> vnfDependencies) {
        this.vnfDependencies = vnfDependencies;
    }

    public List<MonitoringParam> getMonitoringParams() {
        return monitoringParams;
    }

    public void setMonitoringParams(List<MonitoringParam> monitoringParams) {
        this.monitoringParams = monitoringParams;
    }

    public List<ServicePrimitive> getServicePrimitives() {
        return servicePrimitives;
    }

    public void setServicePrimitives(List<ServicePrimitive> servicePrimitives) {
        this.servicePrimitives = servicePrimitives;
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
        return "NSDescriptor{" +
                "id='" + id + '\'' +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", vendor='" + vendor + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", constituentVNFDs=" + constituentVNFDs +
                ", xpathInputParameters=" + xpathInputParameters +
                ", ipProfiles=" + ipProfiles +
                ", placementGroups=" + placementGroups +
                ", vldList=" + vldList +
                ", connectionPoints=" + connectionPoints +
                ", scalingGroupDescriptors=" + scalingGroupDescriptors +
                ", vnffgd=" + vnffgd +
                ", initialServicePrimitives=" + initialServicePrimitives +
                ", terminateServicePrimitives=" + terminateServicePrimitives +
                ", parameterPools=" + parameterPools +
                ", keyPairs=" + keyPairs +
                ", user=" + user +
                ", vnfDependencies=" + vnfDependencies +
                ", monitoringParams=" + monitoringParams +
                ", servicePrimitives=" + servicePrimitives +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
