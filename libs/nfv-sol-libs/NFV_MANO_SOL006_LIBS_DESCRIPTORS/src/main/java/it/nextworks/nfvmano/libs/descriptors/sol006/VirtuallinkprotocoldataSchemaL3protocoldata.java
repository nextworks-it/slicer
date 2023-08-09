package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.common.enums.IpVersionEnum;
import it.nextworks.nfvmano.libs.common.enums.Ipv6AddressModeEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * Specifies the L3 protocol data for this virtual link. Shall be present when the associatedLayerProtocol attribute indicates a L3 protocol and shall be absent otherwise.
 */
//@Schema(description = "Specifies the L3 protocol data for this virtual link. Shall be present when the associatedLayerProtocol attribute indicates a L3 protocol and shall be absent otherwise.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VirtuallinkprotocoldataSchemaL3protocoldata {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("ip-allocation-pools")
  @ElementCollection
  @CollectionTable(name = "virtual_link_protocol_data_schema_l3_pdata_ip_alloc_pools")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> ipAllocationPools = null;

  @JsonProperty("ip-version")
  private IpVersionEnum ipVersion = null;

  @JsonProperty("dhcp-enabled")
  private Boolean dhcpEnabled = null;

  @JsonProperty("cidr")
  private String cidr = null;

  @JsonProperty("gateway-ip")
  private String gatewayIp = null;

  @JsonProperty("ipv6-address-mode")
  private Ipv6AddressModeEnum ipv6AddressMode = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("security-group")
  private String securityGroup = null;

  public VirtuallinkprotocoldataSchemaL3protocoldata name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Network name associated with this L3 protocol.
   * @return name
   **/
  //@Schema(description = "Network name associated with this L3 protocol.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VirtuallinkprotocoldataSchemaL3protocoldata ipAllocationPools(List<String> ipAllocationPools) {
    this.ipAllocationPools = ipAllocationPools;
    return this;
  }

  public VirtuallinkprotocoldataSchemaL3protocoldata addIpAllocationPoolsItem(String ipAllocationPoolsItem) {
    if (this.ipAllocationPools == null) {
      this.ipAllocationPools = new ArrayList<String>();
    }
    this.ipAllocationPools.add(ipAllocationPoolsItem);
    return this;
  }

  /**
   * Get ipAllocationPools
   * @return ipAllocationPools
   **/
  //@Schema(description = "")
  
    public List<String> getIpAllocationPools() {
    return ipAllocationPools;
  }

  public void setIpAllocationPools(List<String> ipAllocationPools) {
    this.ipAllocationPools = ipAllocationPools;
  }

  public VirtuallinkprotocoldataSchemaL3protocoldata ipVersion(IpVersionEnum ipVersion) {
    this.ipVersion = ipVersion;
    return this;
  }

  /**
   * Specifies IP version of this L3 protocol. Value: • IPV4. • IPV6.
   * @return ipVersion
   **/
  //@Schema(description = "Specifies IP version of this L3 protocol. Value: • IPV4. • IPV6.")
  
    public IpVersionEnum getIpVersion() {
    return ipVersion;
  }

  public void setIpVersion(IpVersionEnum ipVersion) {
    this.ipVersion = ipVersion;
  }

  public VirtuallinkprotocoldataSchemaL3protocoldata dhcpEnabled(Boolean dhcpEnabled) {
    this.dhcpEnabled = dhcpEnabled;
    return this;
  }

  /**
   * Indicates whether DHCP (Dynamic Host Configuration Protocol) is enabled or disabled for this L3 protocol.
   * @return dhcpEnabled
   **/
  //@Schema(description = "Indicates whether DHCP (Dynamic Host Configuration Protocol) is enabled or disabled for this L3 protocol.")
  
    public Boolean isDhcpEnabled() {
    return dhcpEnabled;
  }

  public void setDhcpEnabled(Boolean dhcpEnabled) {
    this.dhcpEnabled = dhcpEnabled;
  }

  public VirtuallinkprotocoldataSchemaL3protocoldata cidr(String cidr) {
    this.cidr = cidr;
    return this;
  }

  /**
   * Specifies the CIDR (Classless InterDomain Routing) of this L3 protocol.
   * @return cidr
   **/
  //@Schema(description = "Specifies the CIDR (Classless InterDomain Routing) of this L3 protocol.")
  
    public String getCidr() {
    return cidr;
  }

  public void setCidr(String cidr) {
    this.cidr = cidr;
  }

  public VirtuallinkprotocoldataSchemaL3protocoldata gatewayIp(String gatewayIp) {
    this.gatewayIp = gatewayIp;
    return this;
  }

  /**
   * Specifies the gateway IP address for this L3 protocol.
   * @return gatewayIp
   **/
  //@Schema(description = "Specifies the gateway IP address for this L3 protocol.")
  
    public String getGatewayIp() {
    return gatewayIp;
  }

  public void setGatewayIp(String gatewayIp) {
    this.gatewayIp = gatewayIp;
  }

  public VirtuallinkprotocoldataSchemaL3protocoldata ipv6AddressMode(Ipv6AddressModeEnum ipv6AddressMode) {
    this.ipv6AddressMode = ipv6AddressMode;
    return this;
  }

  /**
   * Specifies IPv6 address mode. Possible values: • SLAAC. • DHCPV6-STATEFUL. • DHCPV6-STATELESS. May be present when the value of the ipVersion attribute is 'IPV6' and shall be absent otherwise.
   * @return ipv6AddressMode
   **/
  //@Schema(description = "Specifies IPv6 address mode. Possible values: • SLAAC. • DHCPV6-STATEFUL. • DHCPV6-STATELESS. May be present when the value of the ipVersion attribute is 'IPV6' and shall be absent otherwise.")
  
    public Ipv6AddressModeEnum getIpv6AddressMode() {
    return ipv6AddressMode;
  }

  public void setIpv6AddressMode(Ipv6AddressModeEnum ipv6AddressMode) {
    this.ipv6AddressMode = ipv6AddressMode;
  }

  public VirtuallinkprotocoldataSchemaL3protocoldata description(String description) {
      this.description = description;
      return this;
  }

  public String getDescription() { return description; }

  public void setDescription(String description) { this.description = description; }

  public VirtuallinkprotocoldataSchemaL3protocoldata securityGroup(String securityGroup) {
      this.securityGroup = securityGroup;
      return this;
  }

  public String getSecurityGroup() { return securityGroup; }

  public void setSecurityGroup(String securityGroup) { this.securityGroup = securityGroup; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtuallinkprotocoldataSchemaL3protocoldata virtuallinkprotocoldataSchemaL3protocoldata = (VirtuallinkprotocoldataSchemaL3protocoldata) o;
    return Objects.equals(this.name, virtuallinkprotocoldataSchemaL3protocoldata.name) &&
            Objects.equals(this.ipAllocationPools, virtuallinkprotocoldataSchemaL3protocoldata.ipAllocationPools) &&
            Objects.equals(this.ipVersion, virtuallinkprotocoldataSchemaL3protocoldata.ipVersion) &&
            Objects.equals(this.dhcpEnabled, virtuallinkprotocoldataSchemaL3protocoldata.dhcpEnabled) &&
            Objects.equals(this.cidr, virtuallinkprotocoldataSchemaL3protocoldata.cidr) &&
            Objects.equals(this.gatewayIp, virtuallinkprotocoldataSchemaL3protocoldata.gatewayIp) &&
            Objects.equals(this.ipv6AddressMode, virtuallinkprotocoldataSchemaL3protocoldata.ipv6AddressMode) &&
            Objects.equals(this.description, virtuallinkprotocoldataSchemaL3protocoldata.description) &&
            Objects.equals(this.securityGroup, virtuallinkprotocoldataSchemaL3protocoldata.securityGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, ipAllocationPools, ipVersion, dhcpEnabled, cidr, gatewayIp,
            ipv6AddressMode, description, securityGroup);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtuallinkprotocoldataSchemaL3protocoldata {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    ipAllocationPools: ").append(toIndentedString(ipAllocationPools)).append("\n");
    sb.append("    ipVersion: ").append(toIndentedString(ipVersion)).append("\n");
    sb.append("    dhcpEnabled: ").append(toIndentedString(dhcpEnabled)).append("\n");
    sb.append("    cidr: ").append(toIndentedString(cidr)).append("\n");
    sb.append("    gatewayIp: ").append(toIndentedString(gatewayIp)).append("\n");
    sb.append("    ipv6AddressMode: ").append(toIndentedString(ipv6AddressMode)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    securityGroup: ").append(toIndentedString(securityGroup)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
