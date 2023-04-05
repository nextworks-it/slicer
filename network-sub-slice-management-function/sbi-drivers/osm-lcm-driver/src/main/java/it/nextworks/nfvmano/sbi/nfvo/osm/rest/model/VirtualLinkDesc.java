package it.nextworks.nfvmano.sbi.nfvo.osm.rest.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Virutal llink desc main attributes")
public class VirtualLinkDesc {
    @SerializedName("id")
    private String id;
    @SerializedName("mgmt-network")
    private boolean mgmtNetwork;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMgmtNetwork() {
        return mgmtNetwork;
    }

    public void setMgmtNetwork(boolean mgmtNetwork) {
        this.mgmtNetwork = mgmtNetwork;
    }
}
