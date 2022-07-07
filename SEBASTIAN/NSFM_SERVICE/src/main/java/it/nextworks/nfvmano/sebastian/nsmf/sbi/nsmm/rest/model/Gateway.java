/*
 * nsmm
 * NorthBound Interface of Network Service Mesh Manager for 5GZORRO project. The NSMM provides API to manage resouces on the VIMs in order to establish secure intra-domain connections between services. In details: - a set of endpoints, called network-resources, is used to manage network resources on the selected vim to provide an external point of connectivity with a VPN server (wireguard). These network-resources considering OpenStack as a VIM include:   - networks and subnets   - routers and interfaces toward a floating network, to allow the creation of service-access-points   - configuration of the gateway VM included in the NSD, which provides the VPN service - a set of endpoints, called vpn-connections, is design to manage the VPN connections between remote peers.  The NSMM manages resources on a single domain and it is invoked by the slicer of the same domain to create all the network resources before the network service instantiation. After the creation of the network service, it is invoked to configure the gateway. Finally, the ISSM request to the slicer of each domain the creation of a secure channel which is forwarded to the NSMM that creates the VPN connection between the two gateways
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Actual gateway configuration of a VPN server gateway
 */@ApiModel(description = "Actual gateway configuration of a VPN server gateway")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2022-03-17T09:30:29.316+01:00[Europe/Rome]")
public class Gateway {

  @SerializedName("mgmt-ip")
  private String mgmtIp = null;
  
  @SerializedName("mgmt-port")
  private String mgmtPort = null;
  
  @SerializedName("private-vpn-range")
  private String privateVpnRange = null;
  
  @SerializedName("exposed-subnets")
  private List<String> exposedSubnets = new ArrayList<String>();
  
  public Gateway mgmtIp(String mgmtIp) {
    this.mgmtIp = mgmtIp;
    return this;
  }

  
  /**
  * Gateway VM management IP
  * @return mgmtIp
  **/
  @ApiModelProperty(required = true, value = "Gateway VM management IP")
  public String getMgmtIp() {
    return mgmtIp;
  }
  public void setMgmtIp(String mgmtIp) {
    this.mgmtIp = mgmtIp;
  }
  
  public Gateway mgmtPort(String mgmtPort) {
    this.mgmtPort = mgmtPort;
    return this;
  }

  
  /**
  * Gateway VM management port
  * @return mgmtPort
  **/
  @ApiModelProperty(example = "8080 ", required = true, value = "Gateway VM management port")
  public String getMgmtPort() {
    return mgmtPort;
  }
  public void setMgmtPort(String mgmtPort) {
    this.mgmtPort = mgmtPort;
  }
  
  public Gateway privateVpnRange(String privateVpnRange) {
    this.privateVpnRange = privateVpnRange;
    return this;
  }

  
  /**
  * Private VPN subnet
  * @return privateVpnRange
  **/
  @ApiModelProperty(example = "10.0.0.0/24", required = true, value = "Private VPN subnet")
  public String getPrivateVpnRange() {
    return privateVpnRange;
  }
  public void setPrivateVpnRange(String privateVpnRange) {
    this.privateVpnRange = privateVpnRange;
  }
  
  public Gateway exposedSubnets(List<String> exposedSubnets) {
    this.exposedSubnets = exposedSubnets;
    return this;
  }

  public Gateway addExposedSubnetsItem(String exposedSubnetsItem) {
    
    this.exposedSubnets.add(exposedSubnetsItem);
    return this;
  }
  
  /**
  * Subnet to expose
  * @return exposedSubnets
  **/
  @ApiModelProperty(example = "172.24.0.0/24", required = true, value = "Subnet to expose")
  public List<String> getExposedSubnets() {
    return exposedSubnets;
  }
  public void setExposedSubnets(List<String> exposedSubnets) {
    this.exposedSubnets = exposedSubnets;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Gateway gateway = (Gateway) o;
    return Objects.equals(this.mgmtIp, gateway.mgmtIp) &&
        Objects.equals(this.mgmtPort, gateway.mgmtPort) &&
        Objects.equals(this.privateVpnRange, gateway.privateVpnRange) &&
        Objects.equals(this.exposedSubnets, gateway.exposedSubnets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mgmtIp, mgmtPort, privateVpnRange, exposedSubnets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Gateway {\n");

    sb.append("    mgmtIp: ").append(toIndentedString(mgmtIp)).append("\n");
    sb.append("    mgmtPort: ").append(toIndentedString(mgmtPort)).append("\n");
    sb.append("    privateVpnRange: ").append(toIndentedString(privateVpnRange)).append("\n");
    sb.append("    exposedSubnets: ").append(toIndentedString(exposedSubnets)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  
}



