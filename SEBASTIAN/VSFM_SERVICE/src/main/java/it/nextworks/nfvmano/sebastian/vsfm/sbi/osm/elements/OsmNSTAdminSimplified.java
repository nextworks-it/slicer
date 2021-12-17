package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmNSTAdminSimplified {

    @JsonProperty("nsrs-detailed-list")
    private List<OsmNSSInformationSimplified> nsrs;

    public List<OsmNSSInformationSimplified> getNsrs() {
        return nsrs;
    }

    public void setNsrs(List<OsmNSSInformationSimplified> nsrs) {
        this.nsrs = nsrs;
    }
}
