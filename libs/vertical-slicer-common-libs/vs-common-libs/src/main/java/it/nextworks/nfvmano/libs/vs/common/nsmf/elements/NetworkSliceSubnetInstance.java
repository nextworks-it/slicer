package it.nextworks.nfvmano.libs.vs.common.nsmf.elements;

import it.nextworks.nfvmano.libs.vs.common.ra.elements.NssResourceAllocation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NetworkSliceSubnetInstance {

    private UUID networkSliceSubnetInstanceId;
    private NetworkSliceInstanceStatus status;
    private String nsstId;
    private String nsstType;
    private NssResourceAllocation currentAllocation;


    public NetworkSliceSubnetInstance(UUID networkSliceSubnetInstanceId,
                                      NetworkSliceInstanceStatus status,
                                      String nsstId,
                                      String nsstType,
                                      NssResourceAllocation currentAllocation) {
        this.networkSliceSubnetInstanceId = networkSliceSubnetInstanceId;
        this.currentAllocation=currentAllocation;

        this.status = status;
        this.nsstId = nsstId;
        this.nsstType =nsstType;
    }

    public UUID getNetworkSliceSubnetInstanceId() {
        return networkSliceSubnetInstanceId;
    }

    public NetworkSliceInstanceStatus getStatus() {
        return status;
    }

    public String getNsstId() {
        return nsstId;
    }

    public String getNsstType() {
        return nsstType;
    }

    public NssResourceAllocation getCurrentAllocation() {
        return currentAllocation;
    }
}
