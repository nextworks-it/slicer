package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmKDURecordSimplified {

    @JsonProperty("services")
    List<OsmKDUServiceSimplified> services;

    public List<OsmKDUServiceSimplified> getServices() {
        return services;
    }

    public void setServices(List<OsmKDUServiceSimplified> services) {
        this.services = services;
    }
}
