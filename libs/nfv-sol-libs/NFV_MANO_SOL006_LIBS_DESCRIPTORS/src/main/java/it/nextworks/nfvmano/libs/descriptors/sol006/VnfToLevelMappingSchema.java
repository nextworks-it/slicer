package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * VnfToLevelMappingSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class VnfToLevelMappingSchema   {
  @JsonProperty("number-of-instances")
  private String numberOfInstances = null;

  @JsonProperty("vnf-profile-id")
  private String vnfProfileId = null;

  public VnfToLevelMappingSchema numberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
    return this;
  }

  /**
   * Specifies the number of VNF instances required for an NS level.
   * @return numberOfInstances
   **/
  //@Schema(description = "Specifies the number of VNF instances required for an NS level.")
  
    public String getNumberOfInstances() {
    return numberOfInstances;
  }

  public void setNumberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
  }

  public VnfToLevelMappingSchema vnfProfileId(String vnfProfileId) {
    this.vnfProfileId = vnfProfileId;
    return this;
  }

  /**
   * Identifies the profile to be used for a VNF involved in an NS level.
   * @return vnfProfileId
   **/
  //@Schema(description = "Identifies the profile to be used for a VNF involved in an NS level.")
  
    public String getVnfProfileId() {
    return vnfProfileId;
  }

  public void setVnfProfileId(String vnfProfileId) {
    this.vnfProfileId = vnfProfileId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfToLevelMappingSchema vnfToLevelMappingSchema = (VnfToLevelMappingSchema) o;
    return Objects.equals(this.numberOfInstances, vnfToLevelMappingSchema.numberOfInstances) &&
        Objects.equals(this.vnfProfileId, vnfToLevelMappingSchema.vnfProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfInstances, vnfProfileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfToLevelMappingSchema {\n");
    
    sb.append("    numberOfInstances: ").append(toIndentedString(numberOfInstances)).append("\n");
    sb.append("    vnfProfileId: ").append(toIndentedString(vnfProfileId)).append("\n");
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
