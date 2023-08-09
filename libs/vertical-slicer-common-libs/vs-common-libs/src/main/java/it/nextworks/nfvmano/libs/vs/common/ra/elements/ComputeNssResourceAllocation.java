package it.nextworks.nfvmano.libs.vs.common.ra.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComputeNssResourceAllocation extends NssResourceAllocation{

    private List<VirtualLinkResourceAllocation> vLinkResources=new ArrayList<VirtualLinkResourceAllocation>();


    private Map<String, String> vnfPlacement; //the key is the VNFD ID, the value is the node ID


    @JsonCreator
    public ComputeNssResourceAllocation(
            @JsonProperty("nsstId") String nsstId,

            @JsonProperty("vLinkResources") List<VirtualLinkResourceAllocation> vLinkResources,
            @JsonProperty("vnfPlacement") Map<String, String> vnfPlacement){

        super(nsstId);
        allocationType = NssResourceAllocationType.COMPUTE;
        if(vLinkResources!=null){
            this.vLinkResources=vLinkResources;
        }
        if(vnfPlacement!=null){
            this.vnfPlacement=vnfPlacement;
        }

    }

    public List<VirtualLinkResourceAllocation> getvLinkResources() {
        return vLinkResources;
    }

    public Map<String, String> getVnfPlacement() {
        return vnfPlacement;
    }
}
