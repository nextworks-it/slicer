package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmNSInstanceSimplified {

    @JsonProperty("id")
    private String id;

    @JsonProperty("_admin")
    private OsmNSTAdminSimplified admin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OsmNSTAdminSimplified getAdmin() {
        return admin;
    }

    public void setAdmin(OsmNSTAdminSimplified nst) {
        this.admin = nst;
    }
}
