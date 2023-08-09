package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

/**
 * ScalingInfoSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class ScalingInfoSchema   {
  @JsonProperty("scale-level")
  private String scaleLevel = null;

  @JsonProperty("scaling-aspect-id")
  private String scalingAspectId = null;

  public ScalingInfoSchema scaleLevel(String scaleLevel) {
    this.scaleLevel = scaleLevel;
    return this;
  }

  /**
   * The scale level, greater than or equal to 0.
   * @return scaleLevel
   **/
  //@Schema(description = "The scale level, greater than or equal to 0.")
  
    public String getScaleLevel() {
    return scaleLevel;
  }

  public void setScaleLevel(String scaleLevel) {
    this.scaleLevel = scaleLevel;
  }

  public ScalingInfoSchema scalingAspectId(String scalingAspectId) {
    this.scalingAspectId = scalingAspectId;
    return this;
  }

  /**
   * Identifier of the scaling aspect.
   * @return scalingAspectId
   **/
  //@Schema(description = "Identifier of the scaling aspect.")
  
    public String getScalingAspectId() {
    return scalingAspectId;
  }

  public void setScalingAspectId(String scalingAspectId) {
    this.scalingAspectId = scalingAspectId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScalingInfoSchema scalingInfoSchema = (ScalingInfoSchema) o;
    return Objects.equals(this.scaleLevel, scalingInfoSchema.scaleLevel) &&
        Objects.equals(this.scalingAspectId, scalingInfoSchema.scalingAspectId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scaleLevel, scalingAspectId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScalingInfoSchema {\n");
    
    sb.append("    scaleLevel: ").append(toIndentedString(scaleLevel)).append("\n");
    sb.append("    scalingAspectId: ").append(toIndentedString(scalingAspectId)).append("\n");
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
