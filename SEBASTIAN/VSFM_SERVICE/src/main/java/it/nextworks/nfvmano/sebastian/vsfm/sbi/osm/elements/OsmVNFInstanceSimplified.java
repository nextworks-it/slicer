package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmVNFInstanceSimplified {

    @JsonProperty("vdur")
    private List<OsmVDURecordSimplified> vdur;

    @JsonProperty("kdur")
    private List<OsmKDURecordSimplified> kdur;

    public List<OsmVDURecordSimplified> getVdur() {
        return vdur;
    }

    public void setVdur(List<OsmVDURecordSimplified> vdur) {
        this.vdur = vdur;
    }

    public List<OsmKDURecordSimplified> getKdur() {
        return kdur;
    }

    public void setKdur(List<OsmKDURecordSimplified> kdur) {
        this.kdur = kdur;
    }
}
