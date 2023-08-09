package it.nextworks.nfvmano.libs.vs.common.nsmf.elements;

import java.util.Map;

public class PnfInstance {

    private String pnfInstanceId;
    private String pnfdId;
    private String pnfdVersion;
    private String description;
    private Map<String, String> capabilities;

    public PnfInstance(){}

    public PnfInstance(String pnfInstanceId, String pnfdId, String pnfdVersion, String description, Map<String, String> capabilities) {
        this.pnfInstanceId = pnfInstanceId;
        this.pnfdId = pnfdId;
        this.pnfdVersion = pnfdVersion;
        this.description = description;
        this.capabilities = capabilities;
    }

    public String getPnfInstanceId() {
        return pnfInstanceId;
    }

    public void setPnfInstanceId(String pnfInstanceId) {
        this.pnfInstanceId = pnfInstanceId;
    }

    public String getPnfdId() {
        return pnfdId;
    }

    public void setPnfdId(String pnfdId) {
        this.pnfdId = pnfdId;
    }

    public String getPnfdVersion() {
        return pnfdVersion;
    }

    public void setPnfdVersion(String pnfdVersion) {
        this.pnfdVersion = pnfdVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Map<String, String> capabilities) {
        this.capabilities = capabilities;
    }
}
