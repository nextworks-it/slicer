package it.nextworks.nfvmano.libs.vs.common.ra.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class TransportNssResourceAllocation extends NssResourceAllocation{
    private List<TransportFlowAllocation> transportAllocations = new ArrayList<>();

    @JsonCreator
    public TransportNssResourceAllocation(
            @JsonProperty("nsstId") String nsstId,
            @JsonProperty("transportAllocations") List<TransportFlowAllocation> transportAllocations) {
        super(nsstId);
        allocationType =NssResourceAllocationType.TRANSPORT;
        if(transportAllocations!=null)
            this.transportAllocations=transportAllocations;
    }

    public List<TransportFlowAllocation> getTransportAllocations() {
        return transportAllocations;
    }
}
