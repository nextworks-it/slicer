/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.eveportal.client.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * VirtualLinkProfile
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class VirtualLinkProfile {
  @SerializedName("affinityOrAntiAffinityGroupId")
  private List<String> affinityOrAntiAffinityGroupId = null;

  @SerializedName("flavourId")
  private String flavourId = null;

  @SerializedName("localAffinityOrAntiAffinityRule")
  private List<AffinityRule> localAffinityOrAntiAffinityRule = null;

  @SerializedName("maxBitrateRequirements")
  private LinkBitrateRequirements maxBitrateRequirements = null;

  @SerializedName("minBitrateRequirements")
  private LinkBitrateRequirements minBitrateRequirements = null;

  @SerializedName("virtualLinkDescId")
  private String virtualLinkDescId = null;

  @SerializedName("virtualLinkProfileId")
  private String virtualLinkProfileId = null;

  public VirtualLinkProfile affinityOrAntiAffinityGroupId(List<String> affinityOrAntiAffinityGroupId) {
    this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
    return this;
  }

  public VirtualLinkProfile addAffinityOrAntiAffinityGroupIdItem(String affinityOrAntiAffinityGroupIdItem) {
    if (this.affinityOrAntiAffinityGroupId == null) {
      this.affinityOrAntiAffinityGroupId = new ArrayList<String>();
    }
    this.affinityOrAntiAffinityGroupId.add(affinityOrAntiAffinityGroupIdItem);
    return this;
  }

   /**
   * Get affinityOrAntiAffinityGroupId
   * @return affinityOrAntiAffinityGroupId
  **/
  @ApiModelProperty(value = "")
  public List<String> getAffinityOrAntiAffinityGroupId() {
    return affinityOrAntiAffinityGroupId;
  }

  public void setAffinityOrAntiAffinityGroupId(List<String> affinityOrAntiAffinityGroupId) {
    this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
  }

  public VirtualLinkProfile flavourId(String flavourId) {
    this.flavourId = flavourId;
    return this;
  }

   /**
   * Get flavourId
   * @return flavourId
  **/
  @ApiModelProperty(value = "")
  public String getFlavourId() {
    return flavourId;
  }

  public void setFlavourId(String flavourId) {
    this.flavourId = flavourId;
  }

  public VirtualLinkProfile localAffinityOrAntiAffinityRule(List<AffinityRule> localAffinityOrAntiAffinityRule) {
    this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
    return this;
  }

  public VirtualLinkProfile addLocalAffinityOrAntiAffinityRuleItem(AffinityRule localAffinityOrAntiAffinityRuleItem) {
    if (this.localAffinityOrAntiAffinityRule == null) {
      this.localAffinityOrAntiAffinityRule = new ArrayList<AffinityRule>();
    }
    this.localAffinityOrAntiAffinityRule.add(localAffinityOrAntiAffinityRuleItem);
    return this;
  }

   /**
   * Get localAffinityOrAntiAffinityRule
   * @return localAffinityOrAntiAffinityRule
  **/
  @ApiModelProperty(value = "")
  public List<AffinityRule> getLocalAffinityOrAntiAffinityRule() {
    return localAffinityOrAntiAffinityRule;
  }

  public void setLocalAffinityOrAntiAffinityRule(List<AffinityRule> localAffinityOrAntiAffinityRule) {
    this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
  }

  public VirtualLinkProfile maxBitrateRequirements(LinkBitrateRequirements maxBitrateRequirements) {
    this.maxBitrateRequirements = maxBitrateRequirements;
    return this;
  }

   /**
   * Get maxBitrateRequirements
   * @return maxBitrateRequirements
  **/
  @ApiModelProperty(value = "")
  public LinkBitrateRequirements getMaxBitrateRequirements() {
    return maxBitrateRequirements;
  }

  public void setMaxBitrateRequirements(LinkBitrateRequirements maxBitrateRequirements) {
    this.maxBitrateRequirements = maxBitrateRequirements;
  }

  public VirtualLinkProfile minBitrateRequirements(LinkBitrateRequirements minBitrateRequirements) {
    this.minBitrateRequirements = minBitrateRequirements;
    return this;
  }

   /**
   * Get minBitrateRequirements
   * @return minBitrateRequirements
  **/
  @ApiModelProperty(value = "")
  public LinkBitrateRequirements getMinBitrateRequirements() {
    return minBitrateRequirements;
  }

  public void setMinBitrateRequirements(LinkBitrateRequirements minBitrateRequirements) {
    this.minBitrateRequirements = minBitrateRequirements;
  }

  public VirtualLinkProfile virtualLinkDescId(String virtualLinkDescId) {
    this.virtualLinkDescId = virtualLinkDescId;
    return this;
  }

   /**
   * Get virtualLinkDescId
   * @return virtualLinkDescId
  **/
  @ApiModelProperty(value = "")
  public String getVirtualLinkDescId() {
    return virtualLinkDescId;
  }

  public void setVirtualLinkDescId(String virtualLinkDescId) {
    this.virtualLinkDescId = virtualLinkDescId;
  }

  public VirtualLinkProfile virtualLinkProfileId(String virtualLinkProfileId) {
    this.virtualLinkProfileId = virtualLinkProfileId;
    return this;
  }

   /**
   * Get virtualLinkProfileId
   * @return virtualLinkProfileId
  **/
  @ApiModelProperty(value = "")
  public String getVirtualLinkProfileId() {
    return virtualLinkProfileId;
  }

  public void setVirtualLinkProfileId(String virtualLinkProfileId) {
    this.virtualLinkProfileId = virtualLinkProfileId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualLinkProfile virtualLinkProfile = (VirtualLinkProfile) o;
    return Objects.equals(this.affinityOrAntiAffinityGroupId, virtualLinkProfile.affinityOrAntiAffinityGroupId) &&
        Objects.equals(this.flavourId, virtualLinkProfile.flavourId) &&
        Objects.equals(this.localAffinityOrAntiAffinityRule, virtualLinkProfile.localAffinityOrAntiAffinityRule) &&
        Objects.equals(this.maxBitrateRequirements, virtualLinkProfile.maxBitrateRequirements) &&
        Objects.equals(this.minBitrateRequirements, virtualLinkProfile.minBitrateRequirements) &&
        Objects.equals(this.virtualLinkDescId, virtualLinkProfile.virtualLinkDescId) &&
        Objects.equals(this.virtualLinkProfileId, virtualLinkProfile.virtualLinkProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityOrAntiAffinityGroupId, flavourId, localAffinityOrAntiAffinityRule, maxBitrateRequirements, minBitrateRequirements, virtualLinkDescId, virtualLinkProfileId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualLinkProfile {\n");
    
    sb.append("    affinityOrAntiAffinityGroupId: ").append(toIndentedString(affinityOrAntiAffinityGroupId)).append("\n");
    sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n");
    sb.append("    localAffinityOrAntiAffinityRule: ").append(toIndentedString(localAffinityOrAntiAffinityRule)).append("\n");
    sb.append("    maxBitrateRequirements: ").append(toIndentedString(maxBitrateRequirements)).append("\n");
    sb.append("    minBitrateRequirements: ").append(toIndentedString(minBitrateRequirements)).append("\n");
    sb.append("    virtualLinkDescId: ").append(toIndentedString(virtualLinkDescId)).append("\n");
    sb.append("    virtualLinkProfileId: ").append(toIndentedString(virtualLinkProfileId)).append("\n");
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
