package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceType;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;

import java.util.UUID;

public class TransportInstantiatePayload extends NssmfBaseProvisioningMessage {

    @JsonProperty("payload")
    private NssResourceAllocation nssResourceAllocation;

    private NSST transportNsst;

    private SliceType sliceType;

    public TransportInstantiatePayload() {}

    public TransportInstantiatePayload(UUID nssiId, NssResourceAllocation nssResourceAllocation, NSST transportNsst, SliceType sliceType) {
        super(nssiId);
        this.nssResourceAllocation = nssResourceAllocation;
        this.transportNsst=transportNsst;
        this.sliceType=sliceType;
    }

    public void isValid()throws MalformattedElementException{
        if(nssResourceAllocation==null)
            throw new MalformattedElementException("NssResourceAllocation object cannot be null");

        if(transportNsst==null)
            throw new MalformattedElementException("Transport NSST is required");

        if(sliceType==null)
            throw new MalformattedElementException("Slice type is required");
    }

    public NssResourceAllocation getNssResourceAllocation() {
        return nssResourceAllocation;
    }

    public void setNssResourceAllocation(NssResourceAllocation nssResourceAllocation) {
        this.nssResourceAllocation = nssResourceAllocation;
    }

    public NSST getTransportNsst() {
        return transportNsst;
    }

    public void setTransportNsst(NSST transportNsst) {
        this.transportNsst = transportNsst;
    }

    public SliceType getSliceType() {
        return sliceType;
    }

    public void setSliceType(SliceType sliceType) {
        this.sliceType = sliceType;
    }
}
