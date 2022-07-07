package it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.messages;

import java.util.List;
import java.util.Map;

public class NsmmAllocationResponse {

    private Integer nsmmAllocationId;

    private Map<String, String> allocatedVlSubnets;

    private Map<String, String> allocatedVlNetworks;

    private List<String> internalVpnNetworks;

    private String externalGwSubnet;

    public Integer getNsmmAllocationId() {
        return nsmmAllocationId;
    }

    public Map<String, String> getAllocatedVlSubnets() {
        return allocatedVlSubnets;
    }

    public String getExternalGwSubnet() {
        return externalGwSubnet;
    }

    public Map<String, String> getAllocatedVlNetworks() {
        return allocatedVlNetworks;
    }

    public List<String> getInternalVpnNetworks() {
        return internalVpnNetworks;
    }

    public NsmmAllocationResponse(Integer nsmAllocationId, Map<String, String> allocatedVlSubnets, Map<String, String> allocatedVlNetworks,
                                  String externalGwSubnet, List<String> internalVpnNetworks) {
        this.nsmmAllocationId = nsmAllocationId;
        this.allocatedVlSubnets = allocatedVlSubnets;
        this.externalGwSubnet = externalGwSubnet;
        this.allocatedVlNetworks = allocatedVlNetworks;
        this.internalVpnNetworks=internalVpnNetworks;
    }
}
