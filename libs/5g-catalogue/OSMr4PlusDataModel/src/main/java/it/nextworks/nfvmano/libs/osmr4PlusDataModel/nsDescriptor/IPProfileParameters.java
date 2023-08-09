package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPProfileParameters {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("gateway-address")
    private String gatewayAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ip-version")
    private String ipVersion;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("subnet-address")
    private String subnetAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("security-group")
    private String securityGroup;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("dns-server")
    private List<DNSServer> dnsServers;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dhcp-params")
    private DHCPParameters dhcpParameters;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("subnet-prefix-pool")
    private String subnetPrefixPool;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getGatewayAddress() {
        return gatewayAddress;
    }

    public void setGatewayAddress(String gatewayAddress) {
        this.gatewayAddress = gatewayAddress;
    }

    public String getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(String ipVersion) {
        this.ipVersion = ipVersion;
    }

    public String getSubnetAddress() {
        return subnetAddress;
    }

    public void setSubnetAddress(String subnetAddress) {
        this.subnetAddress = subnetAddress;
    }

    public String getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }

    public List<DNSServer> getDnsServers() {
        return dnsServers;
    }

    public void setDnsServers(List<DNSServer> dnsServers) {
        this.dnsServers = dnsServers;
    }

    public DHCPParameters getDhcpParameters() {
        return dhcpParameters;
    }

    public void setDhcpParameters(DHCPParameters dhcpParameters) {
        this.dhcpParameters = dhcpParameters;
    }

    public String getSubnetPrefixPool() {
        return subnetPrefixPool;
    }

    public void setSubnetPrefixPool(String subnetPrefixPool) {
        this.subnetPrefixPool = subnetPrefixPool;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "IPProfileParameters{" +
                "gatewayAddress='" + gatewayAddress + '\'' +
                ", ipVersion='" + ipVersion + '\'' +
                ", subnetAddress='" + subnetAddress + '\'' +
                ", securityGroup='" + securityGroup + '\'' +
                ", dnsServers=" + dnsServers +
                ", dhcpParameters=" + dhcpParameters +
                ", subnetPrefixPool='" + subnetPrefixPool + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
