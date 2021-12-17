package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class OsmAdditionalParamForKdu {

    @JsonProperty("kdu_name")
    private String kduName;
    @JsonProperty("additionalParams")
    private Map<String, String> additionalParams;

    public OsmAdditionalParamForKdu(String kduName, Map<String, String> additionalParams) {
        this.kduName = kduName;
        this.additionalParams = additionalParams;
    }

    public String getKduName() {
        return kduName;
    }

    public void setKduName(String kduName) {
        this.kduName = kduName;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }
}
