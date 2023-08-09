package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.Vnfd;

import java.util.HashMap;
import java.util.Map;

public class SoVnfInfoObject {

    private boolean deletionPending;
    private String operationalState;
    private String usageState;

    private Vnfd vnfd;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public boolean isDeletionPending() {
        return deletionPending;
    }

    public void setDeletionPending(boolean deletionPending) {
        this.deletionPending = deletionPending;
    }

    public String getOperationalState() {
        return operationalState;
    }

    public void setOperationalState(String operationalState) {
        this.operationalState = operationalState;
    }

    public String getUsageState() {
        return usageState;
    }

    public void setUsageState(String usageState) {
        this.usageState = usageState;
    }

    public Vnfd getVnfd() {
        return vnfd;
    }

    public void setVnfd(Vnfd vnfd) {
        this.vnfd = vnfd;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }
}
