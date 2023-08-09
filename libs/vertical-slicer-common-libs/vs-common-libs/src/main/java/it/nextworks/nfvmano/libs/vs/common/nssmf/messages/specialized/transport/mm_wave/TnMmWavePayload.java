package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.transport.mm_wave;

import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;

import java.util.List;

public class TnMmWavePayload extends NssmfBaseProvisioningMessage {

    private String networkName;
    private List<DeviceParams> deviceParamsList;


    public TnMmWavePayload(){

    }
    public TnMmWavePayload(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public List<DeviceParams> getDeviceParamsList() {
        return deviceParamsList;
    }

    public void setDeviceParamsList(List<DeviceParams> deviceParamsList) {
        this.deviceParamsList = deviceParamsList;
    }

}
