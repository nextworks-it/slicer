package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OsmNsdSol6Catalogue {
    @JsonProperty("nsd")
    private List<Nsd> nsds;


    public OsmNsdSol6Catalogue(){

    }
    public OsmNsdSol6Catalogue(List<Nsd> nsds) {
        this.nsds = nsds;
    }

    public List<Nsd> getNsds() {
        return nsds;
    }
}
