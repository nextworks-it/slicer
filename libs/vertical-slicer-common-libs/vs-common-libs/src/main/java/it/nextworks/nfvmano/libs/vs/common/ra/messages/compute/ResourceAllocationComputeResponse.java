package it.nextworks.nfvmano.libs.vs.common.ra.messages.compute;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.interfaces.InterfaceMessage;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.NsResourceAllocation;

public class ResourceAllocationComputeResponse implements InterfaceMessage {

    private String responseId;
    private NsResourceAllocation nsResourceAllocation;
    private  boolean successful;

    public ResourceAllocationComputeResponse() {
    }

    public ResourceAllocationComputeResponse(String responseId, NsResourceAllocation nsResourceAllocation, boolean successful) {
        this.responseId=responseId;
        this.nsResourceAllocation = nsResourceAllocation;
        this.successful = successful;
    }

    public NsResourceAllocation getNsResourceAllocation() {
        return nsResourceAllocation;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public void setNsResourceAllocation(NsResourceAllocation nsResourceAllocation) {
        this.nsResourceAllocation = nsResourceAllocation;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if(nsResourceAllocation==null)
            throw new MalformattedElementException("RA response without response");

        nsResourceAllocation.isValid();
    }
}
