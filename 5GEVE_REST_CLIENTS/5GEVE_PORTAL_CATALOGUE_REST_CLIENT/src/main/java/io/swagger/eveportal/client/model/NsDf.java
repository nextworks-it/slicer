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
 * NsDf
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class NsDf {
  @SerializedName("affinityOrAntiAffinityGroup")
  private List<AffinityOrAntiAffinityGroup> affinityOrAntiAffinityGroup = null;

  @SerializedName("defaultNsInstantiationLevelId")
  private String defaultNsInstantiationLevelId = null;

  @SerializedName("dependencies")
  private List<Dependencies> dependencies = null;

  @SerializedName("flavourKey")
  private String flavourKey = null;

  @SerializedName("nsDfId")
  private String nsDfId = null;

  @SerializedName("nsInstantiationLevel")
  private List<NsLevel> nsInstantiationLevel = null;

  @SerializedName("nsProfile")
  private List<NsProfile> nsProfile = null;

  @SerializedName("pnfProfile")
  private List<PnfProfile> pnfProfile = null;

  @SerializedName("scalingAspect")
  private List<NsScalingAspect> scalingAspect = null;

  @SerializedName("virtualLinkProfile")
  private List<VirtualLinkProfile> virtualLinkProfile = null;

  @SerializedName("vnfProfile")
  private List<VnfProfile> vnfProfile = null;

  public NsDf affinityOrAntiAffinityGroup(List<AffinityOrAntiAffinityGroup> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
    return this;
  }

  public NsDf addAffinityOrAntiAffinityGroupItem(AffinityOrAntiAffinityGroup affinityOrAntiAffinityGroupItem) {
    if (this.affinityOrAntiAffinityGroup == null) {
      this.affinityOrAntiAffinityGroup = new ArrayList<AffinityOrAntiAffinityGroup>();
    }
    this.affinityOrAntiAffinityGroup.add(affinityOrAntiAffinityGroupItem);
    return this;
  }

   /**
   * Get affinityOrAntiAffinityGroup
   * @return affinityOrAntiAffinityGroup
  **/
  @ApiModelProperty(value = "")
  public List<AffinityOrAntiAffinityGroup> getAffinityOrAntiAffinityGroup() {
    return affinityOrAntiAffinityGroup;
  }

  public void setAffinityOrAntiAffinityGroup(List<AffinityOrAntiAffinityGroup> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
  }

  public NsDf defaultNsInstantiationLevelId(String defaultNsInstantiationLevelId) {
    this.defaultNsInstantiationLevelId = defaultNsInstantiationLevelId;
    return this;
  }

   /**
   * Get defaultNsInstantiationLevelId
   * @return defaultNsInstantiationLevelId
  **/
  @ApiModelProperty(value = "")
  public String getDefaultNsInstantiationLevelId() {
    return defaultNsInstantiationLevelId;
  }

  public void setDefaultNsInstantiationLevelId(String defaultNsInstantiationLevelId) {
    this.defaultNsInstantiationLevelId = defaultNsInstantiationLevelId;
  }

  public NsDf dependencies(List<Dependencies> dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  public NsDf addDependenciesItem(Dependencies dependenciesItem) {
    if (this.dependencies == null) {
      this.dependencies = new ArrayList<Dependencies>();
    }
    this.dependencies.add(dependenciesItem);
    return this;
  }

   /**
   * Get dependencies
   * @return dependencies
  **/
  @ApiModelProperty(value = "")
  public List<Dependencies> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<Dependencies> dependencies) {
    this.dependencies = dependencies;
  }

  public NsDf flavourKey(String flavourKey) {
    this.flavourKey = flavourKey;
    return this;
  }

   /**
   * Get flavourKey
   * @return flavourKey
  **/
  @ApiModelProperty(value = "")
  public String getFlavourKey() {
    return flavourKey;
  }

  public void setFlavourKey(String flavourKey) {
    this.flavourKey = flavourKey;
  }

  public NsDf nsDfId(String nsDfId) {
    this.nsDfId = nsDfId;
    return this;
  }

   /**
   * Get nsDfId
   * @return nsDfId
  **/
  @ApiModelProperty(value = "")
  public String getNsDfId() {
    return nsDfId;
  }

  public void setNsDfId(String nsDfId) {
    this.nsDfId = nsDfId;
  }

  public NsDf nsInstantiationLevel(List<NsLevel> nsInstantiationLevel) {
    this.nsInstantiationLevel = nsInstantiationLevel;
    return this;
  }

  public NsDf addNsInstantiationLevelItem(NsLevel nsInstantiationLevelItem) {
    if (this.nsInstantiationLevel == null) {
      this.nsInstantiationLevel = new ArrayList<NsLevel>();
    }
    this.nsInstantiationLevel.add(nsInstantiationLevelItem);
    return this;
  }

   /**
   * Get nsInstantiationLevel
   * @return nsInstantiationLevel
  **/
  @ApiModelProperty(value = "")
  public List<NsLevel> getNsInstantiationLevel() {
    return nsInstantiationLevel;
  }

  public void setNsInstantiationLevel(List<NsLevel> nsInstantiationLevel) {
    this.nsInstantiationLevel = nsInstantiationLevel;
  }

  public NsDf nsProfile(List<NsProfile> nsProfile) {
    this.nsProfile = nsProfile;
    return this;
  }

  public NsDf addNsProfileItem(NsProfile nsProfileItem) {
    if (this.nsProfile == null) {
      this.nsProfile = new ArrayList<NsProfile>();
    }
    this.nsProfile.add(nsProfileItem);
    return this;
  }

   /**
   * Get nsProfile
   * @return nsProfile
  **/
  @ApiModelProperty(value = "")
  public List<NsProfile> getNsProfile() {
    return nsProfile;
  }

  public void setNsProfile(List<NsProfile> nsProfile) {
    this.nsProfile = nsProfile;
  }

  public NsDf pnfProfile(List<PnfProfile> pnfProfile) {
    this.pnfProfile = pnfProfile;
    return this;
  }

  public NsDf addPnfProfileItem(PnfProfile pnfProfileItem) {
    if (this.pnfProfile == null) {
      this.pnfProfile = new ArrayList<PnfProfile>();
    }
    this.pnfProfile.add(pnfProfileItem);
    return this;
  }

   /**
   * Get pnfProfile
   * @return pnfProfile
  **/
  @ApiModelProperty(value = "")
  public List<PnfProfile> getPnfProfile() {
    return pnfProfile;
  }

  public void setPnfProfile(List<PnfProfile> pnfProfile) {
    this.pnfProfile = pnfProfile;
  }

  public NsDf scalingAspect(List<NsScalingAspect> scalingAspect) {
    this.scalingAspect = scalingAspect;
    return this;
  }

  public NsDf addScalingAspectItem(NsScalingAspect scalingAspectItem) {
    if (this.scalingAspect == null) {
      this.scalingAspect = new ArrayList<NsScalingAspect>();
    }
    this.scalingAspect.add(scalingAspectItem);
    return this;
  }

   /**
   * Get scalingAspect
   * @return scalingAspect
  **/
  @ApiModelProperty(value = "")
  public List<NsScalingAspect> getScalingAspect() {
    return scalingAspect;
  }

  public void setScalingAspect(List<NsScalingAspect> scalingAspect) {
    this.scalingAspect = scalingAspect;
  }

  public NsDf virtualLinkProfile(List<VirtualLinkProfile> virtualLinkProfile) {
    this.virtualLinkProfile = virtualLinkProfile;
    return this;
  }

  public NsDf addVirtualLinkProfileItem(VirtualLinkProfile virtualLinkProfileItem) {
    if (this.virtualLinkProfile == null) {
      this.virtualLinkProfile = new ArrayList<VirtualLinkProfile>();
    }
    this.virtualLinkProfile.add(virtualLinkProfileItem);
    return this;
  }

   /**
   * Get virtualLinkProfile
   * @return virtualLinkProfile
  **/
  @ApiModelProperty(value = "")
  public List<VirtualLinkProfile> getVirtualLinkProfile() {
    return virtualLinkProfile;
  }

  public void setVirtualLinkProfile(List<VirtualLinkProfile> virtualLinkProfile) {
    this.virtualLinkProfile = virtualLinkProfile;
  }

  public NsDf vnfProfile(List<VnfProfile> vnfProfile) {
    this.vnfProfile = vnfProfile;
    return this;
  }

  public NsDf addVnfProfileItem(VnfProfile vnfProfileItem) {
    if (this.vnfProfile == null) {
      this.vnfProfile = new ArrayList<VnfProfile>();
    }
    this.vnfProfile.add(vnfProfileItem);
    return this;
  }

   /**
   * Get vnfProfile
   * @return vnfProfile
  **/
  @ApiModelProperty(value = "")
  public List<VnfProfile> getVnfProfile() {
    return vnfProfile;
  }

  public void setVnfProfile(List<VnfProfile> vnfProfile) {
    this.vnfProfile = vnfProfile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsDf nsDf = (NsDf) o;
    return Objects.equals(this.affinityOrAntiAffinityGroup, nsDf.affinityOrAntiAffinityGroup) &&
        Objects.equals(this.defaultNsInstantiationLevelId, nsDf.defaultNsInstantiationLevelId) &&
        Objects.equals(this.dependencies, nsDf.dependencies) &&
        Objects.equals(this.flavourKey, nsDf.flavourKey) &&
        Objects.equals(this.nsDfId, nsDf.nsDfId) &&
        Objects.equals(this.nsInstantiationLevel, nsDf.nsInstantiationLevel) &&
        Objects.equals(this.nsProfile, nsDf.nsProfile) &&
        Objects.equals(this.pnfProfile, nsDf.pnfProfile) &&
        Objects.equals(this.scalingAspect, nsDf.scalingAspect) &&
        Objects.equals(this.virtualLinkProfile, nsDf.virtualLinkProfile) &&
        Objects.equals(this.vnfProfile, nsDf.vnfProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityOrAntiAffinityGroup, defaultNsInstantiationLevelId, dependencies, flavourKey, nsDfId, nsInstantiationLevel, nsProfile, pnfProfile, scalingAspect, virtualLinkProfile, vnfProfile);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsDf {\n");
    
    sb.append("    affinityOrAntiAffinityGroup: ").append(toIndentedString(affinityOrAntiAffinityGroup)).append("\n");
    sb.append("    defaultNsInstantiationLevelId: ").append(toIndentedString(defaultNsInstantiationLevelId)).append("\n");
    sb.append("    dependencies: ").append(toIndentedString(dependencies)).append("\n");
    sb.append("    flavourKey: ").append(toIndentedString(flavourKey)).append("\n");
    sb.append("    nsDfId: ").append(toIndentedString(nsDfId)).append("\n");
    sb.append("    nsInstantiationLevel: ").append(toIndentedString(nsInstantiationLevel)).append("\n");
    sb.append("    nsProfile: ").append(toIndentedString(nsProfile)).append("\n");
    sb.append("    pnfProfile: ").append(toIndentedString(pnfProfile)).append("\n");
    sb.append("    scalingAspect: ").append(toIndentedString(scalingAspect)).append("\n");
    sb.append("    virtualLinkProfile: ").append(toIndentedString(virtualLinkProfile)).append("\n");
    sb.append("    vnfProfile: ").append(toIndentedString(vnfProfile)).append("\n");
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
