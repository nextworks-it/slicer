package it.nextworks.nfvmano.flexPhyMac.sbi;

import java.util.List;

public class FlexPhyMacResourceAllocation {
    private List<FlexPhyMacUeAllocation> Ues;

    public FlexPhyMacResourceAllocation(List<FlexPhyMacUeAllocation> Ues){
        this.Ues = Ues;
    }
    public List<FlexPhyMacUeAllocation> getUes() {
        return Ues;
    }

    public void setUes(List<FlexPhyMacUeAllocation> ues) {
        Ues = ues;
    }
}
