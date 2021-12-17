package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmNSDescriptorSimplified {

    @JsonProperty("constituent-vnfd")
    private List<OsmConstituentVnfSimplified> constituentVnfs;

    public List<OsmConstituentVnfSimplified> getConstituentVnfs() {
        return constituentVnfs;
    }

    public void setConstituentVnfs(List<OsmConstituentVnfSimplified> constituentVnfs) {
        this.constituentVnfs = constituentVnfs;
    }
}
