package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OsmNsSol6Package {
    @JsonProperty("nsd")
    private OsmNsdSol6Catalogue  osmNsdCatalogue;

    public OsmNsSol6Package(){

    }
    public OsmNsSol6Package(OsmNsdSol6Catalogue osmNsdCatalogue) {
        this.osmNsdCatalogue = osmNsdCatalogue;
    }

    public OsmNsdSol6Catalogue getOsmNsdCatalogue() {
        return osmNsdCatalogue;
    }
}
