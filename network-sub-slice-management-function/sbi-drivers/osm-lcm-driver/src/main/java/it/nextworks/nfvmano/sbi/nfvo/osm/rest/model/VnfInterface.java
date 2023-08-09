package it.nextworks.nfvmano.sbi.nfvo.osm.rest.model; 
import com.google.gson.annotations.SerializedName;

public class VnfInterface{
   
    @SerializedName("external-connection-point-ref") 
    public String externalConnectionPointRef;
    @SerializedName("internal-connection-point-ref") 
    public String internalConnectionPointRef;
    @SerializedName("mgmt-interface") 
    public boolean mgmtInterface;
    @SerializedName("mgmt-vnf") 
    public boolean mgmtVnf;
    public String name;
    @SerializedName("ns-vld-id") 
    public String nsVldId;
    public String type;
    public boolean port_security;
    public String compute_node;
    @SerializedName("ip-address") 
    public String ipAddress;
    @SerializedName("mac-address") 
    public String macAddress;
    public Object pci;
    public int vlan;
    @SerializedName("fixed-ip")
    public boolean fixedIp;
    public String vim_info;
    public String mac_address;
    public String vim_net_id;
    public String vim_interface_id;
    public String ip_address;
    public boolean mgmt_vnf_interface;
    public boolean mgmt_vdu_interface;

    public String getExternalConnectionPointRef() {
        return externalConnectionPointRef;
    }

    public String getInternalConnectionPointRef() {
        return internalConnectionPointRef;
    }

    public boolean isMgmtInterface() {
        return mgmtInterface;
    }

    public boolean isMgmtVnf() {
        return mgmtVnf;
    }

    public String getName() {
        return name;
    }

    public String getNsVldId() {
        return nsVldId;
    }

    public String getType() {
        return type;
    }

    public boolean isPort_security() {
        return port_security;
    }

    public String getCompute_node() {
        return compute_node;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public Object getPci() {
        return pci;
    }

    public int getVlan() {
        return vlan;
    }

    public boolean isFixedIp() {
        return fixedIp;
    }

    public String getVim_info() {
        return vim_info;
    }

    public String getMac_address() {
        return mac_address;
    }

    public String getVim_net_id() {
        return vim_net_id;
    }

    public String getVim_interface_id() {
        return vim_interface_id;
    }

    public String getIp_address() {
        return ip_address;
    }

    public boolean isMgmt_vnf_interface() {
        return mgmt_vnf_interface;
    }

    public boolean isMgmt_vdu_interface() {
        return mgmt_vdu_interface;
    }
}
