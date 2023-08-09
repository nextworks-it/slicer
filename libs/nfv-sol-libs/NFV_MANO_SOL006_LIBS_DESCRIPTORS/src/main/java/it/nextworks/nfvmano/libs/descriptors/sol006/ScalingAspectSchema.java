package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * ScalingAspectSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class ScalingAspectSchema   {
  @JsonProperty("scaling-aspect_id")
  private String scalingAspectId = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("scaling-level")
  private String scalingLevel = null;

  public ScalingAspectSchema scalingAspectId(String scalingAspectId) {
    this.scalingAspectId = scalingAspectId;
    return this;
  }

  /**
   * Identifier of this NsScalingAspect information element. It uniquely identifies the NS scaling aspect in an NSD.
   * @return scalingAspectId
   **/
  //@Schema(description = "Identifier of this NsScalingAspect information element. It uniquely identifies the NS scaling aspect in an NSD.")
  
    public String getScalingAspectId() {
    return scalingAspectId;
  }

  public void setScalingAspectId(String scalingAspectId) {
    this.scalingAspectId = scalingAspectId;
  }

  public ScalingAspectSchema description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Provides a human readable description of the NS scaling aspect.
   * @return description
   **/
  //@Schema(description = "Provides a human readable description of the NS scaling aspect.")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ScalingAspectSchema name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Provides a human readable name of the NS scaling aspect.
   * @return name
   **/
  //@Schema(description = "Provides a human readable name of the NS scaling aspect.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ScalingAspectSchema scalingLevel(String scalingLevel) {
    this.scalingLevel = scalingLevel;
    return this;
  }

  /**
   * Describes the details of an NS level.
   * @return scalingLevel
   **/
  //@Schema(description = "Describes the details of an NS level.")
  
    public String getScalingLevel() {
    return scalingLevel;
  }

  public void setScalingLevel(String scalingLevel) {
    this.scalingLevel = scalingLevel;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScalingAspectSchema scalingAspectSchema = (ScalingAspectSchema) o;
    return Objects.equals(this.scalingAspectId, scalingAspectSchema.scalingAspectId) &&
        Objects.equals(this.description, scalingAspectSchema.description) &&
        Objects.equals(this.name, scalingAspectSchema.name) &&
        Objects.equals(this.scalingLevel, scalingAspectSchema.scalingLevel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scalingAspectId, description, name, scalingLevel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScalingAspectSchema {\n");
    
    sb.append("    scalingAspectId: ").append(toIndentedString(scalingAspectId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    scalingLevel: ").append(toIndentedString(scalingLevel)).append("\n");
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
