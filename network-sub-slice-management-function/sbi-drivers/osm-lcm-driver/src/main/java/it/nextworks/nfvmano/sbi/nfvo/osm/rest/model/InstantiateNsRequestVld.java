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

import java.util.*;

/**
 * InstantiateNsRequestVld
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-03-04T13:48:18.253Z[GMT]")
public class InstantiateNsRequestVld {
  @SerializedName("name")
  private String name = null;

  @SerializedName("vim-network-name")
  private Map<String,String> vimNetworkName = null;

  @SerializedName("vim-network-id")
  private OneOfInstantiateNsRequestVldVimNetworkId vimNetworkId = null;

  @SerializedName("ns-net")
  private Map<String, Object> nsNet = null;

  @SerializedName("wimAccountId")
  private OneOfInstantiateNsRequestVldWimAccountId wimAccountId = null;

  @SerializedName("ip-profile")
  private Map<String, Object> ipProfile = null;

  @SerializedName("provider-network")
  private InstantiateNsRequestProvidernetwork1 providerNetwork = null;

  @SerializedName("vnfd-connection-point-ref")
  private List<InstantiateNsRequestVnfdconnectionpointref> vnfdConnectionPointRef = null;

  public InstantiateNsRequestVld name(String name) {
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

  public InstantiateNsRequestVld vimNetworkName(Map<String,String> vimNetworkName) {
    this.vimNetworkName = vimNetworkName;
    return this;
  }

   /**
   * Get vimNetworkName
   * @return vimNetworkName
  **/
  @Schema(description = "")
  public Map<String, String> getVimNetworkName() {
    return vimNetworkName;
  }

  public void setVimNetworkName(Map<String, String> vimNetworkName) {
    this.vimNetworkName = vimNetworkName;
  }

  public InstantiateNsRequestVld vimNetworkId(OneOfInstantiateNsRequestVldVimNetworkId vimNetworkId) {
    this.vimNetworkId = vimNetworkId;
    return this;
  }

   /**
   * Get vimNetworkId
   * @return vimNetworkId
  **/
  @Schema(description = "")
  public OneOfInstantiateNsRequestVldVimNetworkId getVimNetworkId() {
    return vimNetworkId;
  }

  public void setVimNetworkId(OneOfInstantiateNsRequestVldVimNetworkId vimNetworkId) {
    this.vimNetworkId = vimNetworkId;
  }

  public InstantiateNsRequestVld nsNet(Map<String, Object> nsNet) {
    this.nsNet = nsNet;
    return this;
  }

  public InstantiateNsRequestVld putNsNetItem(String key, Object nsNetItem) {
    if (this.nsNet == null) {
      this.nsNet = new HashMap<String, Object>();
    }
    this.nsNet.put(key, nsNetItem);
    return this;
  }

   /**
   * Get nsNet
   * @return nsNet
  **/
  @Schema(description = "")
  public Map<String, Object> getNsNet() {
    return nsNet;
  }

  public void setNsNet(Map<String, Object> nsNet) {
    this.nsNet = nsNet;
  }

  public InstantiateNsRequestVld wimAccountId(OneOfInstantiateNsRequestVldWimAccountId wimAccountId) {
    this.wimAccountId = wimAccountId;
    return this;
  }

   /**
   * Get wimAccountId
   * @return wimAccountId
  **/
  @Schema(description = "")
  public OneOfInstantiateNsRequestVldWimAccountId getWimAccountId() {
    return wimAccountId;
  }

  public void setWimAccountId(OneOfInstantiateNsRequestVldWimAccountId wimAccountId) {
    this.wimAccountId = wimAccountId;
  }

  public InstantiateNsRequestVld ipProfile(Map<String, Object> ipProfile) {
    this.ipProfile = ipProfile;
    return this;
  }

  public InstantiateNsRequestVld putIpProfileItem(String key, Object ipProfileItem) {
    if (this.ipProfile == null) {
      this.ipProfile = new HashMap<String, Object>();
    }
    this.ipProfile.put(key, ipProfileItem);
    return this;
  }

   /**
   * Get ipProfile
   * @return ipProfile
  **/
  @Schema(description = "")
  public Map<String, Object> getIpProfile() {
    return ipProfile;
  }

  public void setIpProfile(Map<String, Object> ipProfile) {
    this.ipProfile = ipProfile;
  }

  public InstantiateNsRequestVld providerNetwork(InstantiateNsRequestProvidernetwork1 providerNetwork) {
    this.providerNetwork = providerNetwork;
    return this;
  }

   /**
   * Get providerNetwork
   * @return providerNetwork
  **/
  @Schema(description = "")
  public InstantiateNsRequestProvidernetwork1 getProviderNetwork() {
    return providerNetwork;
  }

  public void setProviderNetwork(InstantiateNsRequestProvidernetwork1 providerNetwork) {
    this.providerNetwork = providerNetwork;
  }

  public InstantiateNsRequestVld vnfdConnectionPointRef(List<InstantiateNsRequestVnfdconnectionpointref> vnfdConnectionPointRef) {
    this.vnfdConnectionPointRef = vnfdConnectionPointRef;
    return this;
  }

  public InstantiateNsRequestVld addVnfdConnectionPointRefItem(InstantiateNsRequestVnfdconnectionpointref vnfdConnectionPointRefItem) {
    if (this.vnfdConnectionPointRef == null) {
      this.vnfdConnectionPointRef = new ArrayList<InstantiateNsRequestVnfdconnectionpointref>();
    }
    this.vnfdConnectionPointRef.add(vnfdConnectionPointRefItem);
    return this;
  }

   /**
   * Get vnfdConnectionPointRef
   * @return vnfdConnectionPointRef
  **/
  @Schema(description = "")
  public List<InstantiateNsRequestVnfdconnectionpointref> getVnfdConnectionPointRef() {
    return vnfdConnectionPointRef;
  }

  public void setVnfdConnectionPointRef(List<InstantiateNsRequestVnfdconnectionpointref> vnfdConnectionPointRef) {
    this.vnfdConnectionPointRef = vnfdConnectionPointRef;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstantiateNsRequestVld instantiateNsRequestVld = (InstantiateNsRequestVld) o;
    return Objects.equals(this.name, instantiateNsRequestVld.name) &&
        Objects.equals(this.vimNetworkName, instantiateNsRequestVld.vimNetworkName) &&
        Objects.equals(this.vimNetworkId, instantiateNsRequestVld.vimNetworkId) &&
        Objects.equals(this.nsNet, instantiateNsRequestVld.nsNet) &&
        Objects.equals(this.wimAccountId, instantiateNsRequestVld.wimAccountId) &&
        Objects.equals(this.ipProfile, instantiateNsRequestVld.ipProfile) &&
        Objects.equals(this.providerNetwork, instantiateNsRequestVld.providerNetwork) &&
        Objects.equals(this.vnfdConnectionPointRef, instantiateNsRequestVld.vnfdConnectionPointRef);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, vimNetworkName, vimNetworkId, nsNet, wimAccountId, ipProfile, providerNetwork, vnfdConnectionPointRef);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstantiateNsRequestVld {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    vimNetworkName: ").append(toIndentedString(vimNetworkName)).append("\n");
    sb.append("    vimNetworkId: ").append(toIndentedString(vimNetworkId)).append("\n");
    sb.append("    nsNet: ").append(toIndentedString(nsNet)).append("\n");
    sb.append("    wimAccountId: ").append(toIndentedString(wimAccountId)).append("\n");
    sb.append("    ipProfile: ").append(toIndentedString(ipProfile)).append("\n");
    sb.append("    providerNetwork: ").append(toIndentedString(providerNetwork)).append("\n");
    sb.append("    vnfdConnectionPointRef: ").append(toIndentedString(vnfdConnectionPointRef)).append("\n");
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
