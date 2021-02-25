package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SonataNSInstantiationRequest extends SonataRequest {

    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nst_id")
    private String nstId;

    public SonataNSInstantiationRequest(String nstId, String name, String description) {
        super(SonataRequestType.CREATE_SLICE);
        this.description = description;
        this.name = name;
        this.nstId = nstId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNstId() {
        return nstId;
    }

    public void setNstId(String nstId) {
        this.nstId = nstId;
    }

}
