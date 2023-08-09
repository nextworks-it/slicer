package it.nextworks.nfvmano.libs.osmr10DataModels.nsd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OsmNsWrapper {

    @JsonProperty("nsd")
    private final OsmNsdsSol006Wrapper nsd;

    @JsonCreator
    public OsmNsWrapper(@JsonProperty("nsd") OsmNsdsSol006Wrapper nsd) { this.nsd = nsd; }

    public OsmNsdsSol006Wrapper getNsd() { return nsd; }
}
