package it.nextworks.nfvmano.catalogue.plugins.siteInventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NfvoWrapperList {

    @JsonProperty("nfvOrchestrators")
    List<NfvOrchestrator> nfvOrchestratorList;

    public List<NfvOrchestrator> getNfvOrchestratorList() {
        return nfvOrchestratorList;
    }

    public void setNfvOrchestratorList(List<NfvOrchestrator> nfvOrchestratorList) {
        this.nfvOrchestratorList = nfvOrchestratorList;
    }
}
