package it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonitoringInfo {

    @JsonProperty("IP_address")
    private String ipAddress;
    @JsonProperty("Interface")
    private String interfaceName;
    @JsonProperty("VM_name")
    private String vmName;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }
}
