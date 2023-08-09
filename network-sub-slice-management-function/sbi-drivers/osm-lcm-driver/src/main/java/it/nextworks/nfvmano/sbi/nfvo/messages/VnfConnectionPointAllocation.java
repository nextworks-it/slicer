package it.nextworks.nfvmano.sbi.nfvo.messages;

public class VnfConnectionPointAllocation {

    private String memberVnfIndex;
    private String vnfdConnectionPoint;

    private String ipAddress;

    public VnfConnectionPointAllocation() {
    }

    public VnfConnectionPointAllocation(String memberVnfIndex, String vnfdConnectionPoint, String ipAddress) {
        this.memberVnfIndex = memberVnfIndex;
        this.vnfdConnectionPoint = vnfdConnectionPoint;
        this.ipAddress = ipAddress;
    }

    public String getMemberVnfIndex() {
        return memberVnfIndex;
    }

    public String getVnfdConnectionPoint() {
        return vnfdConnectionPoint;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
