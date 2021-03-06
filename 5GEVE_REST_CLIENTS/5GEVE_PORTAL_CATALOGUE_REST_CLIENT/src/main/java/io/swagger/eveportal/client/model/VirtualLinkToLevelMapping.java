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

/**
 * VirtualLinkToLevelMapping
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class VirtualLinkToLevelMapping {
  @SerializedName("bitRateRequirements")
  private LinkBitrateRequirements bitRateRequirements = null;

  @SerializedName("virtualLinkProfileId")
  private String virtualLinkProfileId = null;

  public VirtualLinkToLevelMapping bitRateRequirements(LinkBitrateRequirements bitRateRequirements) {
    this.bitRateRequirements = bitRateRequirements;
    return this;
  }

   /**
   * Get bitRateRequirements
   * @return bitRateRequirements
  **/
  @ApiModelProperty(value = "")
  public LinkBitrateRequirements getBitRateRequirements() {
    return bitRateRequirements;
  }

  public void setBitRateRequirements(LinkBitrateRequirements bitRateRequirements) {
    this.bitRateRequirements = bitRateRequirements;
  }

  public VirtualLinkToLevelMapping virtualLinkProfileId(String virtualLinkProfileId) {
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
    VirtualLinkToLevelMapping virtualLinkToLevelMapping = (VirtualLinkToLevelMapping) o;
    return Objects.equals(this.bitRateRequirements, virtualLinkToLevelMapping.bitRateRequirements) &&
        Objects.equals(this.virtualLinkProfileId, virtualLinkToLevelMapping.virtualLinkProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bitRateRequirements, virtualLinkProfileId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualLinkToLevelMapping {\n");
    
    sb.append("    bitRateRequirements: ").append(toIndentedString(bitRateRequirements)).append("\n");
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

