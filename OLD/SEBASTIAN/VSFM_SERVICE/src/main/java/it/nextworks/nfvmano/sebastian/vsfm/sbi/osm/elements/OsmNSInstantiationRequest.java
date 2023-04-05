package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OsmNSInstantiationRequest {
    @JsonProperty("vimAccountId")
    private String vimAccountId;
    @JsonProperty("nsiName")
    private String nsiName;
    @JsonProperty("nstId")
    private String nstInfoId;

    public OsmNSInstantiationRequest(String nsiName, String nstInfoId, String vimAccountId) {
        this.vimAccountId = vimAccountId;
        this.nsiName = nsiName;
        this.nstInfoId = nstInfoId;
    }

    public String getVimAccountId() {
        return vimAccountId;
    }

    public void setVimAccountId(String vimAccountId) {
        this.vimAccountId = vimAccountId;
    }

    public String getNsiName() {
        return nsiName;
    }

    public void setNsiName(String nsiName) {
        this.nsiName = nsiName;
    }

    public String getNstInfoId() {
        return nstInfoId;
    }

    public void setNstInfoId(String nstInfoId) {
        this.nstInfoId = nstInfoId;
    }
}
