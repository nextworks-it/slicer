package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VDU {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("pdu-type")
    private String pduType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("mgmt-vpci")
    private String mgmtVpci;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vm-flavor")
    private VMFlavor vmFlavor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("guest-epa")
    private GuestEpa guestEpa;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vswitch-epa")
    private VSwitchEpa vswitchEpa;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("hypervisor-epa")
    private HypervisorEpa hypervisorEpa;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("host-epa")
    private HostEpa hostEpa;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Alarm> alarm;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String image;
    @JsonProperty("image-checksum")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageChecksum;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("alternative-images")
    private List<AlternativeImages> alternativeImages;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vdu-configuration")
    private VNFConfiguration vduConfiguration;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("monitoring-param")
    private List<MonitoringParamVdu> monitoringParam;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cloud-init")
    private String cloudInit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cloud-init-file")
    private String cloudInitFile;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("supplemental-boot-data")
    private SupplementalBootData supplementalBootData;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("internal-connection-point")
    private List<InternalConnectionPoint> internalConnectionPoints;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("interface")
    private List<Interface> interfaces;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Volumes> volumes;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPduType() {
        return pduType;
    }

    public void setPduType(String pduType) {
        this.pduType = pduType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMgmtVpci() {
        return mgmtVpci;
    }

    public void setMgmtVpci(String mgmtVpci) {
        this.mgmtVpci = mgmtVpci;
    }

    public VMFlavor getVmFlavor() {
        return vmFlavor;
    }

    public void setVmFlavor(VMFlavor vmFlavor) {
        this.vmFlavor = vmFlavor;
    }

    public GuestEpa getGuestEpa() {
        return guestEpa;
    }

    public void setGuestEpa(GuestEpa guestEpa) {
        this.guestEpa = guestEpa;
    }

    public VSwitchEpa getVswitchEpa() {
        return vswitchEpa;
    }

    public void setVswitchEpa(VSwitchEpa vswitchEpa) {
        this.vswitchEpa = vswitchEpa;
    }

    public HypervisorEpa getHypervisorEpa() {
        return hypervisorEpa;
    }

    public void setHypervisorEpa(HypervisorEpa hypervisorEpa) {
        this.hypervisorEpa = hypervisorEpa;
    }

    public HostEpa getHostEpa() {
        return hostEpa;
    }

    public void setHostEpa(HostEpa hostEpa) {
        this.hostEpa = hostEpa;
    }

    public List<Alarm> getAlarm() {
        return alarm;
    }

    public void setAlarm(List<Alarm> alarm) {
        this.alarm = alarm;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageChecksum() {
        return imageChecksum;
    }

    public void setImageChecksum(String imageChecksum) {
        this.imageChecksum = imageChecksum;
    }

    public List<AlternativeImages> getAlternativeImages() {
        return alternativeImages;
    }

    public void setAlternativeImages(List<AlternativeImages> alternativeImages) {
        this.alternativeImages = alternativeImages;
    }

    public VNFConfiguration getVduConfiguration() {
        return vduConfiguration;
    }

    public void setVduConfiguration(VNFConfiguration vduConfiguration) {
        this.vduConfiguration = vduConfiguration;
    }

    public List<MonitoringParamVdu> getMonitoringParam() {
        return monitoringParam;
    }

    public void setMonitoringParam(List<MonitoringParamVdu> monitoringParam) {
        this.monitoringParam = monitoringParam;
    }

    public String getCloudInit() {
        return cloudInit;
    }

    public void setCloudInit(String cloudInit) {
        this.cloudInit = cloudInit;
    }

    public String getCloudInitFile() {
        return cloudInitFile;
    }

    public void setCloudInitFile(String cloudInitFile) {
        this.cloudInitFile = cloudInitFile;
    }

    public SupplementalBootData getSupplementalBootData() {
        return supplementalBootData;
    }

    public void setSupplementalBootData(SupplementalBootData supplementalBootData) {
        this.supplementalBootData = supplementalBootData;
    }

    public List<InternalConnectionPoint> getInternalConnectionPoints() {
        return internalConnectionPoints;
    }

    public void setInternalConnectionPoints(List<InternalConnectionPoint> internalConnectionPoints) {
        this.internalConnectionPoints = internalConnectionPoints;
    }

    public List<Interface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Interface> interfaces) {
        this.interfaces = interfaces;
    }

    public List<Volumes> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volumes> volumes) {
        this.volumes = volumes;
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
        return "VDU{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pduType='" + pduType + '\'' +
                ", count=" + count +
                ", mgmtVpci='" + mgmtVpci + '\'' +
                ", vmFlavor=" + vmFlavor +
                ", guestEpa=" + guestEpa +
                ", vswitchEpa=" + vswitchEpa +
                ", hypervisorEpa=" + hypervisorEpa +
                ", hostEpa=" + hostEpa +
                ", alarm=" + alarm +
                ", image='" + image + '\'' +
                ", imageChecksum='" + imageChecksum + '\'' +
                ", alternativeImages=" + alternativeImages +
                ", vduConfiguration=" + vduConfiguration +
                ", monitoringParam=" + monitoringParam +
                ", cloudInit='" + cloudInit + '\'' +
                ", cloudInitFile='" + cloudInitFile + '\'' +
                ", supplementalBootData=" + supplementalBootData +
                ", internalConnectionPoints=" + internalConnectionPoints +
                ", interfaces=" + interfaces +
                ", volumes=" + volumes +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
