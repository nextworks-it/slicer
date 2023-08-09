package it.nextworks.nfvmano.libs.osmr10DataModels.nsd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.descriptors.sol006.Nsd;

import java.util.ArrayList;
import java.util.List;

public class OsmNsdsSol006Wrapper {

    @JsonProperty("nsd")
    private List<Nsd> nsds;

    @JsonCreator
    public OsmNsdsSol006Wrapper(@JsonProperty("nsd") List<Nsd> nsds) { this.nsds = nsds; }

    public void addNsd(Nsd nsd) {
        if(nsds == null)
            nsds = new ArrayList<>();

        nsds.add(nsd);
    }

    public List<Nsd> getNsds() { return nsds; }
}
