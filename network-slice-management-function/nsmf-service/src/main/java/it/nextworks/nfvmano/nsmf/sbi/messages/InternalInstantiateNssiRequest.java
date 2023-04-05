package it.nextworks.nfvmano.nsmf.sbi.messages;

import it.nextworks.nfvmano.libs.ifa.templates.nst.NSST;
import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.nsmf.sbi.generic.GenericInstantiateNssiRequest;

import java.util.UUID;

public class InternalInstantiateNssiRequest extends GenericInstantiateNssiRequest {

    private UUID parentNsiId;

    private NST parentNst;
    private NSST nsst;
    private ResourceAllocationComputeResponse resourceAllocationComputeResponse;


    public InternalInstantiateNssiRequest(UUID nssiId, UUID parentNsiId, NSST nsst, ResourceAllocationComputeResponse response, NST parentNST){
        super(nssiId);
        this.nsst=nsst;
        this.resourceAllocationComputeResponse = response;
        this.parentNst=parentNST;
        this.parentNsiId=parentNsiId;
    }

    public UUID getParentNsiId() {
        return parentNsiId;
    }

    public NSST getNsst() {
        return nsst;
    }

    public ResourceAllocationComputeResponse getResourceAllocationComputeResponse() {
        return resourceAllocationComputeResponse;
    }

    public NST getParentNst() {
        return parentNst;
    }
}
