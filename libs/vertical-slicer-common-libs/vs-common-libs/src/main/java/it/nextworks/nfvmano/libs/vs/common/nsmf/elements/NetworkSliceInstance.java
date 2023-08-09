package it.nextworks.nfvmano.libs.vs.common.nsmf.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NetworkSliceInstance {

    private UUID networkSliceInstanceId;
    private List<UUID> networkSliceSubnetIds = new ArrayList<>();
    private String vsInstanceId;
    private NetworkSliceInstanceStatus status;
    private String internalStatus;

    public void setUlDataRate(int ulDataRate) {
        this.ulDataRate = ulDataRate;
    }

    private List<String> imsiAssiocatedWith = new ArrayList<>();
    private int dlDataRate;
    private int ulDataRate;

    private String upfName;

    public NetworkSliceInstance(UUID networkSliceInstanceId, List<UUID> networkSliceSubnetIds, String vsInstanceId, NetworkSliceInstanceStatus status, String internalStatus) {
        this.networkSliceInstanceId = networkSliceInstanceId;
        this.networkSliceSubnetIds = networkSliceSubnetIds;
        this.vsInstanceId = vsInstanceId;
        this.status = status;
        this.internalStatus= internalStatus;
    }

    public NetworkSliceInstance(UUID networkSliceInstanceId, List<UUID> networkSliceSubnetIds,
                                String vsInstanceId, NetworkSliceInstanceStatus status,
                                String internalStatus, List<String> imsiAssiocatedWith,
                                int dlDataRate, int ulDataRate, String upfName) {
        this.networkSliceInstanceId = networkSliceInstanceId;
        this.networkSliceSubnetIds = networkSliceSubnetIds;
        this.vsInstanceId = vsInstanceId;
        this.status = status;
        this.internalStatus= internalStatus;
        this.imsiAssiocatedWith = imsiAssiocatedWith;
        this.dlDataRate = dlDataRate;
        this.ulDataRate = ulDataRate;
        this.upfName = upfName;
    }


    public UUID getNetworkSliceInstanceId() {
        return networkSliceInstanceId;
    }

    public List<UUID> getNetworkSliceSubnetIds() {
        return networkSliceSubnetIds;
    }

    public String getVsInstanceId() {
        return vsInstanceId;
    }

    public NetworkSliceInstanceStatus getStatus() {
        return status;
    }

    public List<String> getImsiAssiocatedWith() {
        return imsiAssiocatedWith;
    }

    public void setImsiAssiocatedWith(List<String> imsiAssiocatedWith) {
        this.imsiAssiocatedWith = imsiAssiocatedWith;
    }

    public int getDlDataRate() {
        return dlDataRate;
    }

    public void setDlDataRate(int dlDataRate) {
        this.dlDataRate = dlDataRate;
    }

    public int getUlDataRate() {
        return ulDataRate;
    }

    public String getUpfName() {
        return upfName;
    }

    public void setUpfName(String upfName) {
        this.upfName = upfName;
    }

}
