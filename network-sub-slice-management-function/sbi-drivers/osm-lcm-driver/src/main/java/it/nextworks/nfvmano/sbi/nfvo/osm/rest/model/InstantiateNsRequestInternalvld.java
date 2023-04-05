/*
 * OSM NB API featuring ETSI NFV SOL005
 * This is Open Source MANO Northbound API featuring ETSI NFV SOL005. For more information on OSM, you can visit [http://osm.etsi.org](http://osm.etsi.org). You can send us your comments and questions to OSM_TECH@list.etsi.org or join the [OpenSourceMANO Slack Workplace](https://join.slack.com/t/opensourcemano/shared_invite/enQtMzQ3MzYzNTQ0NDIyLWVkNTE4ZjZjNWI0ZTQyN2VhOTI1MjViMzU1NWYwMWM3ODI4NTQyY2VlODA2ZjczMWIyYTFkZWNiZmFkM2M2ZDk) 
 *
 * OpenAPI spec version: 1.0.0
 * Contact: OSM_TECH@list.etsi.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package it.nextworks.nfvmano.sbi.nfvo.osm.rest.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * InstantiateNsRequestInternalvld
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-03-04T13:48:18.253Z[GMT]")
public class InstantiateNsRequestInternalvld {
  @SerializedName("name")
  private String name = null;

  @SerializedName("vim-network-name")
  private String vimNetworkName = null;

  @SerializedName("vim-network-id")
  private String vimNetworkId = null;

  @SerializedName("ip-profile")
  private InstantiateNsRequestIpprofile ipProfile = null;

  @SerializedName("provider-network")
  private InstantiateNsRequestProvidernetwork providerNetwork = null;

  @SerializedName("internal-connection-point")
  private List<InstantiateNsRequestInternalconnectionpoint> internalConnectionPoint = null;

  public InstantiateNsRequestInternalvld name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @Schema(required = true, description = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public InstantiateNsRequestInternalvld vimNetworkName(String vimNetworkName) {
    this.vimNetworkName = vimNetworkName;
    return this;
  }

   /**
   * Get vimNetworkName
   * @return vimNetworkName
  **/
  @Schema(description = "")
  public String getVimNetworkName() {
    return vimNetworkName;
  }

  public void setVimNetworkName(String vimNetworkName) {
    this.vimNetworkName = vimNetworkName;
  }

  public InstantiateNsRequestInternalvld vimNetworkId(String vimNetworkId) {
    this.vimNetworkId = vimNetworkId;
    return this;
  }

   /**
   * Get vimNetworkId
   * @return vimNetworkId
  **/
  @Schema(description = "")
  public String getVimNetworkId() {
    return vimNetworkId;
  }

  public void setVimNetworkId(String vimNetworkId) {
    this.vimNetworkId = vimNetworkId;
  }

  public InstantiateNsRequestInternalvld ipProfile(InstantiateNsRequestIpprofile ipProfile) {
    this.ipProfile = ipProfile;
    return this;
  }

   /**
   * Get ipProfile
   * @return ipProfile
  **/
  @Schema(description = "")
  public InstantiateNsRequestIpprofile getIpProfile() {
    return ipProfile;
  }

  public void setIpProfile(InstantiateNsRequestIpprofile ipProfile) {
    this.ipProfile = ipProfile;
  }

  public InstantiateNsRequestInternalvld providerNetwork(InstantiateNsRequestProvidernetwork providerNetwork) {
    this.providerNetwork = providerNetwork;
    return this;
  }

   /**
   * Get providerNetwork
   * @return providerNetwork
  **/
  @Schema(description = "")
  public InstantiateNsRequestProvidernetwork getProviderNetwork() {
    return providerNetwork;
  }

  public void setProviderNetwork(InstantiateNsRequestProvidernetwork providerNetwork) {
    this.providerNetwork = providerNetwork;
  }

  public InstantiateNsRequestInternalvld internalConnectionPoint(List<InstantiateNsRequestInternalconnectionpoint> internalConnectionPoint) {
    this.internalConnectionPoint = internalConnectionPoint;
    return this;
  }

  public InstantiateNsRequestInternalvld addInternalConnectionPointItem(InstantiateNsRequestInternalconnectionpoint internalConnectionPointItem) {
    if (this.internalConnectionPoint == null) {
      this.internalConnectionPoint = new ArrayList<InstantiateNsRequestInternalconnectionpoint>();
    }
    this.internalConnectionPoint.add(internalConnectionPointItem);
    return this;
  }

   /**
   * Get internalConnectionPoint
   * @return internalConnectionPoint
  **/
  @Schema(description = "")
  public List<InstantiateNsRequestInternalconnectionpoint> getInternalConnectionPoint() {
    return internalConnectionPoint;
  }

  public void setInternalConnectionPoint(List<InstantiateNsRequestInternalconnectionpoint> internalConnectionPoint) {
    this.internalConnectionPoint = internalConnectionPoint;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstantiateNsRequestInternalvld instantiateNsRequestInternalvld = (InstantiateNsRequestInternalvld) o;
    return Objects.equals(this.name, instantiateNsRequestInternalvld.name) &&
        Objects.equals(this.vimNetworkName, instantiateNsRequestInternalvld.vimNetworkName) &&
        Objects.equals(this.vimNetworkId, instantiateNsRequestInternalvld.vimNetworkId) &&
        Objects.equals(this.ipProfile, instantiateNsRequestInternalvld.ipProfile) &&
        Objects.equals(this.providerNetwork, instantiateNsRequestInternalvld.providerNetwork) &&
        Objects.equals(this.internalConnectionPoint, instantiateNsRequestInternalvld.internalConnectionPoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, vimNetworkName, vimNetworkId, ipProfile, providerNetwork, internalConnectionPoint);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstantiateNsRequestInternalvld {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    vimNetworkName: ").append(toIndentedString(vimNetworkName)).append("\n");
    sb.append("    vimNetworkId: ").append(toIndentedString(vimNetworkId)).append("\n");
    sb.append("    ipProfile: ").append(toIndentedString(ipProfile)).append("\n");
    sb.append("    providerNetwork: ").append(toIndentedString(providerNetwork)).append("\n");
    sb.append("    internalConnectionPoint: ").append(toIndentedString(internalConnectionPoint)).append("\n");
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
