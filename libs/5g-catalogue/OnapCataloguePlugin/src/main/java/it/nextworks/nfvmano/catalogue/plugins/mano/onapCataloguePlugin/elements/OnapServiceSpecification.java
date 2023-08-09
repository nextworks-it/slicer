package it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OnapServiceSpecification {

    @JsonProperty("id")
    private String nsId;
    @JsonProperty("name")
    private String nsName;

    public String getNsId() {
        return nsId;
    }

    public void setNsId(String nsId) {
        this.nsId = nsId;
    }

    public String getNsName() {
        return nsName;
    }

    public void setNsName(String nsName) {
        this.nsName = nsName;
    }
}
