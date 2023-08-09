package it.nextworks.nfvmano.sbi.nfvo.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VlAllocation {
    private String name;
    private Map<String, String> vimNetworkName = new HashMap<>();

    private List<VnfConnectionPointAllocation> vnfConnectionPointAllocations = new ArrayList<>();

    public VlAllocation() {
    }

    public VlAllocation(String name, Map<String, String> vimNetworkName, List<VnfConnectionPointAllocation> vnfConnectionPointAllocations) {
        this.name = name;
        this.vimNetworkName = vimNetworkName;
        this.vnfConnectionPointAllocations = vnfConnectionPointAllocations;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getVimNetworkName() {
        return vimNetworkName;
    }

    public List<VnfConnectionPointAllocation> getVnfConnectionPointAllocations() {
        return vnfConnectionPointAllocations;
    }

    public void addVimNetworkName(String vimId, String networkName){
        vimNetworkName.put(vimId, networkName);
    }

}
