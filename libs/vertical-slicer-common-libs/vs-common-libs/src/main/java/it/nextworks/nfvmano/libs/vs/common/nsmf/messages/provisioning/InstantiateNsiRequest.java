package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning;

import it.nextworks.nfvmano.libs.vs.common.nsmf.messages.NsmfGenericNsiMessage;

import java.util.UUID;

public class InstantiateNsiRequest extends NsmfGenericNsiMessage {
    private boolean newUpf;
    private String upfName;


    public InstantiateNsiRequest(){};
    public InstantiateNsiRequest(UUID nsiId){
        super(nsiId);
    }

    public boolean isNewUpf() {
        return newUpf;
    }

    public void setNewUpf(boolean newUpf) {
        this.newUpf = newUpf;
    }

    public String getUpfName() {
        return upfName;
    }

    public void setUpfName(String upfName) {
        this.upfName = upfName;
    }

}
