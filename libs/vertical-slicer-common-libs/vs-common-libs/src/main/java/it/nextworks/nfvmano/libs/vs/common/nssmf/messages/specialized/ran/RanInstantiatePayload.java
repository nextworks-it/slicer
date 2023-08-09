package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.ran;

import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;

import java.util.UUID;

public class RanInstantiatePayload extends NssmfBaseProvisioningMessage {

    private NSST nsst;
    private NssResourceAllocation nssResourceAllocation;

    public RanInstantiatePayload(){}

    public RanInstantiatePayload(UUID nssiId, NSST nsst, NssResourceAllocation nssResourceAllocation){
        super(nssiId);
        this.nsst=nsst;
        this.nssResourceAllocation=nssResourceAllocation;
    }

    public NSST getNsst() {
        return nsst;
    }

    public void setNsst(NSST nsst) {
        this.nsst = nsst;
    }

    public NssResourceAllocation getNssResourceAllocation() {
        return nssResourceAllocation;
    }

    public void setNssResourceAllocation(NssResourceAllocation nssResourceAllocation) {
        this.nssResourceAllocation = nssResourceAllocation;
    }
}
