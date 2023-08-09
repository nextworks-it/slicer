package it.nextworks.nfvmano.libs.osmr10DataModels.vnfd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.descriptors.sol006.Vnfd;

public class OsmVnfdSol006Wrapper {

    @JsonProperty("vnfd")
    private final Vnfd vnfd;

    @JsonCreator
    public OsmVnfdSol006Wrapper(@JsonProperty("vnfd") Vnfd vnfd) { this.vnfd = vnfd; }

    public Vnfd getVnfd() { return vnfd; }
}
