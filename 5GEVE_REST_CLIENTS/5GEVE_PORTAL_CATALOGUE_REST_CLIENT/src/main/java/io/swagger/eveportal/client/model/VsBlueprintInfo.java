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
 * VsBlueprintInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class VsBlueprintInfo {
  @SerializedName("activeVsdId")
  private List<String> activeVsdId = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("onBoardedMecAppPackageInfoId")
  private List<String> onBoardedMecAppPackageInfoId = null;

  @SerializedName("onBoardedNsdInfoId")
  private List<String> onBoardedNsdInfoId = null;

  @SerializedName("onBoardedVnfPackageInfoId")
  private List<String> onBoardedVnfPackageInfoId = null;

  @SerializedName("vsBlueprint")
  private VsBlueprint vsBlueprint = null;

  @SerializedName("vsBlueprintId")
  private String vsBlueprintId = null;

  @SerializedName("vsBlueprintVersion")
  private String vsBlueprintVersion = null;

  public VsBlueprintInfo activeVsdId(List<String> activeVsdId) {
    this.activeVsdId = activeVsdId;
    return this;
  }

  public VsBlueprintInfo addActiveVsdIdItem(String activeVsdIdItem) {
    if (this.activeVsdId == null) {
      this.activeVsdId = new ArrayList<String>();
    }
    this.activeVsdId.add(activeVsdIdItem);
    return this;
  }

   /**
   * Get activeVsdId
   * @return activeVsdId
  **/
  @ApiModelProperty(value = "")
  public List<String> getActiveVsdId() {
    return activeVsdId;
  }

  public void setActiveVsdId(List<String> activeVsdId) {
    this.activeVsdId = activeVsdId;
  }

  public VsBlueprintInfo name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VsBlueprintInfo onBoardedMecAppPackageInfoId(List<String> onBoardedMecAppPackageInfoId) {
    this.onBoardedMecAppPackageInfoId = onBoardedMecAppPackageInfoId;
    return this;
  }

  public VsBlueprintInfo addOnBoardedMecAppPackageInfoIdItem(String onBoardedMecAppPackageInfoIdItem) {
    if (this.onBoardedMecAppPackageInfoId == null) {
      this.onBoardedMecAppPackageInfoId = new ArrayList<String>();
    }
    this.onBoardedMecAppPackageInfoId.add(onBoardedMecAppPackageInfoIdItem);
    return this;
  }

   /**
   * Get onBoardedMecAppPackageInfoId
   * @return onBoardedMecAppPackageInfoId
  **/
  @ApiModelProperty(value = "")
  public List<String> getOnBoardedMecAppPackageInfoId() {
    return onBoardedMecAppPackageInfoId;
  }

  public void setOnBoardedMecAppPackageInfoId(List<String> onBoardedMecAppPackageInfoId) {
    this.onBoardedMecAppPackageInfoId = onBoardedMecAppPackageInfoId;
  }

  public VsBlueprintInfo onBoardedNsdInfoId(List<String> onBoardedNsdInfoId) {
    this.onBoardedNsdInfoId = onBoardedNsdInfoId;
    return this;
  }

  public VsBlueprintInfo addOnBoardedNsdInfoIdItem(String onBoardedNsdInfoIdItem) {
    if (this.onBoardedNsdInfoId == null) {
      this.onBoardedNsdInfoId = new ArrayList<String>();
    }
    this.onBoardedNsdInfoId.add(onBoardedNsdInfoIdItem);
    return this;
  }

   /**
   * Get onBoardedNsdInfoId
   * @return onBoardedNsdInfoId
  **/
  @ApiModelProperty(value = "")
  public List<String> getOnBoardedNsdInfoId() {
    return onBoardedNsdInfoId;
  }

  public void setOnBoardedNsdInfoId(List<String> onBoardedNsdInfoId) {
    this.onBoardedNsdInfoId = onBoardedNsdInfoId;
  }

  public VsBlueprintInfo onBoardedVnfPackageInfoId(List<String> onBoardedVnfPackageInfoId) {
    this.onBoardedVnfPackageInfoId = onBoardedVnfPackageInfoId;
    return this;
  }

  public VsBlueprintInfo addOnBoardedVnfPackageInfoIdItem(String onBoardedVnfPackageInfoIdItem) {
    if (this.onBoardedVnfPackageInfoId == null) {
      this.onBoardedVnfPackageInfoId = new ArrayList<String>();
    }
    this.onBoardedVnfPackageInfoId.add(onBoardedVnfPackageInfoIdItem);
    return this;
  }

   /**
   * Get onBoardedVnfPackageInfoId
   * @return onBoardedVnfPackageInfoId
  **/
  @ApiModelProperty(value = "")
  public List<String> getOnBoardedVnfPackageInfoId() {
    return onBoardedVnfPackageInfoId;
  }

  public void setOnBoardedVnfPackageInfoId(List<String> onBoardedVnfPackageInfoId) {
    this.onBoardedVnfPackageInfoId = onBoardedVnfPackageInfoId;
  }

  public VsBlueprintInfo vsBlueprint(VsBlueprint vsBlueprint) {
    this.vsBlueprint = vsBlueprint;
    return this;
  }

   /**
   * Get vsBlueprint
   * @return vsBlueprint
  **/
  @ApiModelProperty(value = "")
  public VsBlueprint getVsBlueprint() {
    return vsBlueprint;
  }

  public void setVsBlueprint(VsBlueprint vsBlueprint) {
    this.vsBlueprint = vsBlueprint;
  }

  public VsBlueprintInfo vsBlueprintId(String vsBlueprintId) {
    this.vsBlueprintId = vsBlueprintId;
    return this;
  }

   /**
   * Get vsBlueprintId
   * @return vsBlueprintId
  **/
  @ApiModelProperty(value = "")
  public String getVsBlueprintId() {
    return vsBlueprintId;
  }

  public void setVsBlueprintId(String vsBlueprintId) {
    this.vsBlueprintId = vsBlueprintId;
  }

  public VsBlueprintInfo vsBlueprintVersion(String vsBlueprintVersion) {
    this.vsBlueprintVersion = vsBlueprintVersion;
    return this;
  }

   /**
   * Get vsBlueprintVersion
   * @return vsBlueprintVersion
  **/
  @ApiModelProperty(value = "")
  public String getVsBlueprintVersion() {
    return vsBlueprintVersion;
  }

  public void setVsBlueprintVersion(String vsBlueprintVersion) {
    this.vsBlueprintVersion = vsBlueprintVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VsBlueprintInfo vsBlueprintInfo = (VsBlueprintInfo) o;
    return Objects.equals(this.activeVsdId, vsBlueprintInfo.activeVsdId) &&
        Objects.equals(this.name, vsBlueprintInfo.name) &&
        Objects.equals(this.onBoardedMecAppPackageInfoId, vsBlueprintInfo.onBoardedMecAppPackageInfoId) &&
        Objects.equals(this.onBoardedNsdInfoId, vsBlueprintInfo.onBoardedNsdInfoId) &&
        Objects.equals(this.onBoardedVnfPackageInfoId, vsBlueprintInfo.onBoardedVnfPackageInfoId) &&
        Objects.equals(this.vsBlueprint, vsBlueprintInfo.vsBlueprint) &&
        Objects.equals(this.vsBlueprintId, vsBlueprintInfo.vsBlueprintId) &&
        Objects.equals(this.vsBlueprintVersion, vsBlueprintInfo.vsBlueprintVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(activeVsdId, name, onBoardedMecAppPackageInfoId, onBoardedNsdInfoId, onBoardedVnfPackageInfoId, vsBlueprint, vsBlueprintId, vsBlueprintVersion);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VsBlueprintInfo {\n");
    
    sb.append("    activeVsdId: ").append(toIndentedString(activeVsdId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    onBoardedMecAppPackageInfoId: ").append(toIndentedString(onBoardedMecAppPackageInfoId)).append("\n");
    sb.append("    onBoardedNsdInfoId: ").append(toIndentedString(onBoardedNsdInfoId)).append("\n");
    sb.append("    onBoardedVnfPackageInfoId: ").append(toIndentedString(onBoardedVnfPackageInfoId)).append("\n");
    sb.append("    vsBlueprint: ").append(toIndentedString(vsBlueprint)).append("\n");
    sb.append("    vsBlueprintId: ").append(toIndentedString(vsBlueprintId)).append("\n");
    sb.append("    vsBlueprintVersion: ").append(toIndentedString(vsBlueprintVersion)).append("\n");
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

