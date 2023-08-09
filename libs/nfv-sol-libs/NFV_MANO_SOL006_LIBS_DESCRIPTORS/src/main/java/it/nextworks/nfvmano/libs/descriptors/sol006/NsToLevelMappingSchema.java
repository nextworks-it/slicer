package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

/**
 * NsToLevelMappingSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class NsToLevelMappingSchema   {
  @JsonProperty("number-of-instances")
  private String numberOfInstances = null;

  @JsonProperty("ns-profile-id")
  private String nsProfileId = null;

  public NsToLevelMappingSchema numberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
    return this;
  }

  /**
   * Specifies the number of nested NS instances required for the NS scale level.
   * @return numberOfInstances
   **/
  //@Schema(description = "Specifies the number of nested NS instances required for the NS scale level.")
  
    public String getNumberOfInstances() {
    return numberOfInstances;
  }

  public void setNumberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
  }

  public NsToLevelMappingSchema nsProfileId(String nsProfileId) {
    this.nsProfileId = nsProfileId;
    return this;
  }

  /**
   * Identifies the profile to be used for a nested NS involved in the NS level.
   * @return nsProfileId
   **/
  //@Schema(description = "Identifies the profile to be used for a nested NS involved in the NS level.")
  
    public String getNsProfileId() {
    return nsProfileId;
  }

  public void setNsProfileId(String nsProfileId) {
    this.nsProfileId = nsProfileId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsToLevelMappingSchema nsToLevelMappingSchema = (NsToLevelMappingSchema) o;
    return Objects.equals(this.numberOfInstances, nsToLevelMappingSchema.numberOfInstances) &&
        Objects.equals(this.nsProfileId, nsToLevelMappingSchema.nsProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfInstances, nsProfileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsToLevelMappingSchema {\n");
    
    sb.append("    numberOfInstances: ").append(toIndentedString(numberOfInstances)).append("\n");
    sb.append("    nsProfileId: ").append(toIndentedString(nsProfileId)).append("\n");
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
