package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



/**
 * InstantiationLevelSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class InstantiationLevelSchema   {
  @JsonProperty("scaling-info")

  private List<VnfdScalinginfo> scalingInfo = null;

  @JsonProperty("vdu-level")

  private List<VnfdVdulevel> vduLevel = null;

  @JsonProperty("instantiation-level_id")
  private String instantiationLevelId = null;

  @JsonProperty("description")
  private String description = null;

  public InstantiationLevelSchema scalingInfo(List<VnfdScalinginfo> scalingInfo) {
    this.scalingInfo = scalingInfo;
    return this;
  }

  public InstantiationLevelSchema addScalingInfoItem(VnfdScalinginfo scalingInfoItem) {
    if (this.scalingInfo == null) {
      this.scalingInfo = new ArrayList<VnfdScalinginfo>();
    }
    this.scalingInfo.add(scalingInfoItem);
    return this;
  }

  /**
   * The InstantiationLevel information element describes a given level of resources to be instantiated within a DF in term of the number of VNFC instances to be created from each VDU.
   * @return scalingInfo
   **/
  //@Schema(description = "The InstantiationLevel information element describes a given level of resources to be instantiated within a DF in term of the number of VNFC instances to be created from each VDU.")

    public List<VnfdScalinginfo> getScalingInfo() {
    return scalingInfo;
  }

  public void setScalingInfo(List<VnfdScalinginfo> scalingInfo) {
    this.scalingInfo = scalingInfo;
  }

  public InstantiationLevelSchema vduLevel(List<VnfdVdulevel> vduLevel) {
    this.vduLevel = vduLevel;
    return this;
  }

  public InstantiationLevelSchema addVduLevelItem(VnfdVdulevel vduLevelItem) {
    if (this.vduLevel == null) {
      this.vduLevel = new ArrayList<VnfdVdulevel>();
    }
    this.vduLevel.add(vduLevelItem);
    return this;
  }

  /**
   * Sets the number of instances for the VDU in this instantiation level.
   * @return vduLevel
   **/
  //@Schema(description = "Sets the number of instances for the VDU in this instantiation level.")

    public List<VnfdVdulevel> getVduLevel() {
    return vduLevel;
  }

  public void setVduLevel(List<VnfdVdulevel> vduLevel) {
    this.vduLevel = vduLevel;
  }

  public InstantiationLevelSchema instantiationLevelId(String instantiationLevelId) {
    this.instantiationLevelId = instantiationLevelId;
    return this;
  }

  /**
   * Uniquely identifies a level with the DF.
   * @return instantiationLevelId
   **/
  //@Schema(description = "Uniquely identifies a level with the DF.")
  
    public String getInstantiationLevelId() {
    return instantiationLevelId;
  }

  public void setInstantiationLevelId(String instantiationLevelId) {
    this.instantiationLevelId = instantiationLevelId;
  }

  public InstantiationLevelSchema description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human readable description of the instantiation level
   * @return description
   **/
  //@Schema(description = "Human readable description of the instantiation level")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstantiationLevelSchema instantiationLevelSchema = (InstantiationLevelSchema) o;
    return Objects.equals(this.scalingInfo, instantiationLevelSchema.scalingInfo) &&
        Objects.equals(this.vduLevel, instantiationLevelSchema.vduLevel) &&
        Objects.equals(this.instantiationLevelId, instantiationLevelSchema.instantiationLevelId) &&
        Objects.equals(this.description, instantiationLevelSchema.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scalingInfo, vduLevel, instantiationLevelId, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstantiationLevelSchema {\n");
    
    sb.append("    scalingInfo: ").append(toIndentedString(scalingInfo)).append("\n");
    sb.append("    vduLevel: ").append(toIndentedString(vduLevel)).append("\n");
    sb.append("    instantiationLevelId: ").append(toIndentedString(instantiationLevelId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
