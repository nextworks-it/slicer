package it.nextworks.nfvmano.nsmf.record.elements;

import it.nextworks.nfvmano.libs.vs.common.ra.elements.*;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Embeddable
public class NssResourceAllocationRecord {


    @ElementCollection
    private List<VirtualLinkResourceAllocationRecord> vLinkResources=new ArrayList<VirtualLinkResourceAllocationRecord>();


    @ElementCollection
    private List<TransportFlowAllocationRecord> transportAllocations =new ArrayList<TransportFlowAllocationRecord>();

    private NssResourceAllocationType allocationType;

    @ElementCollection
    private Map<String, String> vnfPlacement; //the key is the VNFD ID, the value is the node ID

    public NssResourceAllocationRecord(){}

    public NssResourceAllocationRecord(
            NssResourceAllocationType allocationType,

                                 List<VirtualLinkResourceAllocationRecord> vLinkResources,
                                 Map<String, String> vnfPlacement,
                                List<TransportFlowAllocationRecord> transportAllocations){


        this.vLinkResources=vLinkResources;
        this.vnfPlacement=vnfPlacement;
        this.allocationType=allocationType;
        this.transportAllocations=transportAllocations;

    }


    public NssResourceAllocationType getAllocationType() {
        return allocationType;
    }

    public List<VirtualLinkResourceAllocationRecord> getvLinkResources() {
        return vLinkResources;
    }

    public void setvLinkResources(List<VirtualLinkResourceAllocationRecord> vLinkResources) {
        this.vLinkResources = vLinkResources;
    }

    public Map<String, String> getVnfPlacement() {
        return vnfPlacement;
    }

    public void setVnfPlacement(Map<String, String> vnfPlacement) {
        this.vnfPlacement = vnfPlacement;
    }


    public NssResourceAllocation getNssResourceAllocation(){
        if(allocationType.equals(NssResourceAllocationType.COMPUTE)){
            return new ComputeNssResourceAllocation(null, vLinkResources.stream().map(vl -> vl.getVirtualResourceAllocation()).collect(Collectors.toList()), vnfPlacement);
        }else if(allocationType.equals(NssResourceAllocationType.TRANSPORT)){
            return new TransportNssResourceAllocation(null, transportAllocations.stream().map(ta-> ta.getTransportSegmentAllocation()).collect(Collectors.toList()));
        }else return null;

    }
}
