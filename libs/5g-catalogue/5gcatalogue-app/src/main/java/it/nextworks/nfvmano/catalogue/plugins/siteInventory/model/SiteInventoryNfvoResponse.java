package it.nextworks.nfvmano.catalogue.plugins.siteInventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteInventoryNfvoResponse {

    @JsonProperty("_embedded")
    private NfvoWrapperList wrapper;

    public NfvoWrapperList getWrapper() {
        return wrapper;
    }

    public void setWrapper(NfvoWrapperList wrapper) {
        this.wrapper = wrapper;
    }
}
