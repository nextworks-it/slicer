package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.NetworkTypeEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Specifies the L2 protocol data for this virtual link. Shall be present when the associatedLayerProtocol attribute indicates a L2 protocol and shall be absent otherwise.
 */
//@Schema(description = "Specifies the L2 protocol data for this virtual link. Shall be present when the associatedLayerProtocol attribute indicates a L2 protocol and shall be absent otherwise.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VirtuallinkprotocoldataSchemaL2protocoldata {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("mtu")
  private String mtu = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("vlan-transparent")
  private Boolean vlanTransparent = null;

  @JsonProperty("network-type")
  private NetworkTypeEnum networkType = null;

  public VirtuallinkprotocoldataSchemaL2protocoldata mtu(String mtu) {
    this.mtu = mtu;
    return this;
  }

  /**
   * Specifies the maximum transmission unit (MTU) value for this L2 protocol.
   * @return mtu
   **/
  //@Schema(description = "Specifies the maximum transmission unit (MTU) value for this L2 protocol.")
  
    public String getMtu() {
    return mtu;
  }

  public void setMtu(String mtu) {
    this.mtu = mtu;
  }

  public VirtuallinkprotocoldataSchemaL2protocoldata name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Network name associated with this L2 protocol.
   * @return name
   **/
  //@Schema(description = "Network name associated with this L2 protocol.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VirtuallinkprotocoldataSchemaL2protocoldata vlanTransparent(Boolean vlanTransparent) {
    this.vlanTransparent = vlanTransparent;
    return this;
  }

  /**
   * Specifies whether to support VLAN transparency for this L2 protocol or not.
   * @return vlanTransparent
   **/
  //@Schema(description = "Specifies whether to support VLAN transparency for this L2 protocol or not.")
  
    public Boolean isVlanTransparent() {
    return vlanTransparent;
  }

  public void setVlanTransparent(Boolean vlanTransparent) {
    this.vlanTransparent = vlanTransparent;
  }

  public VirtuallinkprotocoldataSchemaL2protocoldata networkType(NetworkTypeEnum networkType) {
    this.networkType = networkType;
    return this;
  }

  /**
   * Specifies the network type for this L2 protocol. Possible values: FLAT, VLAN, VXLAN, GRE.
   * @return networkType
   **/
  //@Schema(description = "Specifies the network type for this L2 protocol. Possible values: FLAT, VLAN, VXLAN, GRE.")
  
    public NetworkTypeEnum getNetworkType() {
    return networkType;
  }

  public void setNetworkType(NetworkTypeEnum networkType) {
    this.networkType = networkType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtuallinkprotocoldataSchemaL2protocoldata virtuallinkprotocoldataSchemaL2protocoldata = (VirtuallinkprotocoldataSchemaL2protocoldata) o;
    return Objects.equals(this.mtu, virtuallinkprotocoldataSchemaL2protocoldata.mtu) &&
        Objects.equals(this.name, virtuallinkprotocoldataSchemaL2protocoldata.name) &&
        Objects.equals(this.vlanTransparent, virtuallinkprotocoldataSchemaL2protocoldata.vlanTransparent) &&
        Objects.equals(this.networkType, virtuallinkprotocoldataSchemaL2protocoldata.networkType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mtu, name, vlanTransparent, networkType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtuallinkprotocoldataSchemaL2protocoldata {\n");
    
    sb.append("    mtu: ").append(toIndentedString(mtu)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    vlanTransparent: ").append(toIndentedString(vlanTransparent)).append("\n");
    sb.append("    networkType: ").append(toIndentedString(networkType)).append("\n");
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
