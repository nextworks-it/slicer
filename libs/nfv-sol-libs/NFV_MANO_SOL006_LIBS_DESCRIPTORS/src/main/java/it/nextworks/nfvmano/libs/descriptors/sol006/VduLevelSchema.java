package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * VduLevelSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class VduLevelSchema   {
  @JsonProperty("vdu-id")
  private String vduId = null;

  @JsonProperty("number-of-instances")
  private String numberOfInstances = null;

  public VduLevelSchema vduId(String vduId) {
    this.vduId = vduId;
    return this;
  }

  /**
   * Uniquely identifies a VDU.
   * @return vduId
   **/
  //@Schema(description = "Uniquely identifies a VDU.")
  
    public String getVduId() {
    return vduId;
  }

  public void setVduId(String vduId) {
    this.vduId = vduId;
  }

  public VduLevelSchema numberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
    return this;
  }

  /**
   * Number of instances of VNFC based on this VDU to deploy for this level.
   * @return numberOfInstances
   **/
  //@Schema(description = "Number of instances of VNFC based on this VDU to deploy for this level.")
  
    public String getNumberOfInstances() {
    return numberOfInstances;
  }

  public void setNumberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VduLevelSchema vduLevelSchema = (VduLevelSchema) o;
    return Objects.equals(this.vduId, vduLevelSchema.vduId) &&
        Objects.equals(this.numberOfInstances, vduLevelSchema.numberOfInstances);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vduId, numberOfInstances);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VduLevelSchema {\n");
    
    sb.append("    vduId: ").append(toIndentedString(vduId)).append("\n");
    sb.append("    numberOfInstances: ").append(toIndentedString(numberOfInstances)).append("\n");
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
