package it.nextworks.nfvmano.sbi.nfvo.messages;

import java.util.UUID;

public class VnfAllocation {

    private String memberVnfIndex;
    private UUID vimId;

    public VnfAllocation() {
    }

    public VnfAllocation(String memberVnfIndex, UUID vimId) {
        this.memberVnfIndex = memberVnfIndex;
        this.vimId = vimId;
    }

    public String getMemberVnfIndex() {
        return memberVnfIndex;
    }

    public UUID getVimId() {
        return vimId;
    }
}
