package it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NeutralHostNSInstantiationRequest {

    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("network_service_id")
    private String nsdId;
    @JsonProperty("ports")
    private List<Integer> ports;
    @JsonProperty("slic3_id")
    private String sliceId;
    @JsonProperty("trusted")
    private boolean trusted;
    @JsonProperty("user_id")
    private String adminId;

    public NeutralHostNSInstantiationRequest(String description, String name, String nsdId, List<Integer> ports, String sliceId, boolean trusted, String adminId) {
        this.description = description;
        this.name = name;
        this.nsdId = nsdId;
        this.ports = ports;
        this.sliceId = sliceId;
        this.trusted = trusted;
        this.adminId = adminId;
    }

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

    public String getNsdId() {
        return nsdId;
    }

    public void setNsdId(String nsdId) {
        this.nsdId = nsdId;
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
}
