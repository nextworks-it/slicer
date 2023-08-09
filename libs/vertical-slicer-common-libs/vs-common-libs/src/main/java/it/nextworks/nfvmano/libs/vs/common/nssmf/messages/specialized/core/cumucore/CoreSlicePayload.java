package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;


public class CoreSlicePayload extends NssmfBaseProvisioningMessage {
    @JsonProperty("nst")
    private NST nst;

    private String upfName;
    public NST getNst() {
        return nst;
    }

    public void setNst(NST nst) {
        this.nst = nst;
    }

    public String getUpfName() {
        return upfName;
    }

    public void setUpfName(String upfName) {
        this.upfName = upfName;
    }

}
