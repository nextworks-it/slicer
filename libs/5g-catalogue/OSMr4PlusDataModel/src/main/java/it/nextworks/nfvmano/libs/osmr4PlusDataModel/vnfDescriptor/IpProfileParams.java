package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IpProfileParams {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ip-version")
    private String ipVersion;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("subnet-address")
    private String subnetAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("gateway-address")
    private String gatewayAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("security-group")
    private String securityGroup;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("dns-server")
    private List<DnsServer> dnsServers;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dhcp-params")
    private DhcpParams dhcpParams;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("subnet-prefix-pool")
    private String subnetPrefixPool;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

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

    public String getGatewayAddress() {
        return gatewayAddress;
    }

    public void setGatewayAddress(String gatewayAddress) {
        this.gatewayAddress = gatewayAddress;
    }

    public String getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }

    public List<DnsServer> getDnsServers() {
        return dnsServers;
    }

    public void setDnsServers(List<DnsServer> dnsServers) {
        this.dnsServers = dnsServers;
    }

    public DhcpParams getDhcpParams() {
        return dhcpParams;
    }

    public void setDhcpParams(DhcpParams dhcpParams) {
        this.dhcpParams = dhcpParams;
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
        return "IPProfileParams{" +
                "ipVersion='" + ipVersion + '\'' +
                ", subnetAddress='" + subnetAddress + '\'' +
                ", gatewayAddress='" + gatewayAddress + '\'' +
                ", securityGroup='" + securityGroup + '\'' +
                ", dnsServers=" + dnsServers +
                ", dhcpParams=" + dhcpParams +
                ", subnetPrefixPool='" + subnetPrefixPool + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
