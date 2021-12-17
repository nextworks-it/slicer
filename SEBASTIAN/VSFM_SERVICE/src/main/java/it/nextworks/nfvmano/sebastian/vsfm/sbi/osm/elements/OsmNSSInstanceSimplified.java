package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmNSSInstanceSimplified {

    @JsonProperty("nsd")
    private OsmNSDescriptorSimplified nsd;

    @JsonProperty("constituent-vnfr-ref")
    private List<String> vnfrs;

    public OsmNSDescriptorSimplified getNsd() {
        return nsd;
    }

    public void setNsd(OsmNSDescriptorSimplified nsd) {
        this.nsd = nsd;
    }

    public List<String> getVnfrs() {
        return vnfrs;
    }

    public void setVnfrs(List<String> vnfrs) {
        this.vnfrs = vnfrs;
    }
}
