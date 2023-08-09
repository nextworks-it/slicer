package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OsmVnfPackage {
    @JsonProperty("vnfd")
    private Vnfd vnfd;

    public OsmVnfPackage(){

    }
    public Vnfd getVnfd(){
        return vnfd;
    }
}
