package it.nextworks.nfvmano.flexPhyMac.sbi;

public class FlexPhyMacUeAllocation {
    private String ueId;
    private String allocation;

    public FlexPhyMacUeAllocation(String ueId, String allocation) {
        this.ueId = ueId;
        this.allocation = allocation;
    }

    public String getUeId() {
        return ueId;
    }

    public void setUeId(String ueId) {
        this.ueId = ueId;
    }

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }
}
