package it.nextworks.nfvmano.libs.vs.common.ra.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SdnNssResourceAllocation extends NssResourceAllocation{

    private List<ConnectivityServiceResourceAllocation> csResources=new ArrayList<ConnectivityServiceResourceAllocation>();

    @JsonCreator
    public SdnNssResourceAllocation(@JsonProperty("nsstId") String nsstId,
                                    @JsonProperty("vLinkResources") List<ConnectivityServiceResourceAllocation> csResources) {
        super(nsstId);
        this.allocationType=NssResourceAllocationType.SDN;
        this.csResources=csResources;
    }

    public List<ConnectivityServiceResourceAllocation> getCsResources() {
        return csResources;
    }

    public void setCsResources(List<ConnectivityServiceResourceAllocation> csResources) {
        this.csResources = csResources;
    }
}
