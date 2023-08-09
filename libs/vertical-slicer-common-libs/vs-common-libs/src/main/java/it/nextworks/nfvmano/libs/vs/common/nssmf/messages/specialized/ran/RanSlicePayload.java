package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.ran;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;

public class RanSlicePayload extends NssmfBaseProvisioningMessage {
    @JsonProperty("nsst")
    private NSST nsst;

    public RanSlicePayload() {
    }

    public NSST getNsst() {
        return this.nsst;
    }

    public void setNst(NSST nst) {
        this.nsst = nst;
    }
}