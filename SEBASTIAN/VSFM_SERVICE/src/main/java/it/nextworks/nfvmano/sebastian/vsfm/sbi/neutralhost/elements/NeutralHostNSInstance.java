package it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NeutralHostNSInstance {

    @JsonProperty("id")
    private String id;
    @JsonProperty("monitoring_info")
    private List<MonitoringInfo> monitoringInfo;
    @JsonProperty("osm_instance_id")
    private String osmInstanceId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("network_service_id")
    private String nsiId;
    @JsonProperty("ports")
    private List<Integer> ports;
    @JsonProperty("slic3_id")
    private String sliceId;
    @JsonProperty("slic3_name")
    private String sliceName;
    @JsonProperty("trusted")
    private boolean trusted;
    @JsonProperty("user_id")
    private String adminId;
    @JsonProperty("swam_service_id")
    private String swamServiceId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNsiId() {
        return nsiId;
    }

    public void setNsiId(String nsiId) {
        this.nsiId = nsiId;
    }

    public List<Integer> getPorts() {
        return ports;
    }

    public void setPorts(List<Integer> ports) {
        this.ports = ports;
    }

    public String getSliceId() {
        return sliceId;
    }

    public void setSliceId(String sliceId) {
        this.sliceId = sliceId;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MonitoringInfo> getMonitoringInfo() {
        return monitoringInfo;
    }

    public void setMonitoringInfo(List<MonitoringInfo> monitoringInfo) {
        this.monitoringInfo = monitoringInfo;
    }

    public String getOsmInstanceId() {
        return osmInstanceId;
    }

    public void setOsmInstanceId(String osmInstanceId) {
        this.osmInstanceId = osmInstanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSliceName() {
        return sliceName;
    }

    public void setSliceName(String sliceName) {
        this.sliceName = sliceName;
    }

    public String getSwamServiceId() {
        return swamServiceId;
    }

    public void setSwamServiceId(String swamServiceId) {
        this.swamServiceId = swamServiceId;
    }
}
