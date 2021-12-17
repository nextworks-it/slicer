package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmVDURecordSimplified {

    @JsonProperty("interfaces")
    private List<OsmVDUInterfaceSimplified> interfaces;

    public List<OsmVDUInterfaceSimplified> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<OsmVDUInterfaceSimplified> interfaces) {
        this.interfaces = interfaces;
    }
}
