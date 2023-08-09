package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;

import java.util.HashMap;
import java.util.Map;

public class SoNsInfoObject {

    private boolean deletionPending;
    private String operationalState;
    private String usageState;

    private Nsd nsd;

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

    public Nsd getNsd() {
        return nsd;
    }

    public void setNsd(Nsd nsd) {
        this.nsd = nsd;
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
