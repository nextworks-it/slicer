package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * VduDeltaSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class VduDeltaSchema   {
  @JsonProperty("number-of-instances")
  private String numberOfInstances = null;

  @JsonProperty("vdu-delta_id")
  private String vduDeltaId = null;

  public VduDeltaSchema numberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
    return this;
  }

  /**
   * Number of instances of VNFC based on this VDU to deploy for an instantiation level or for a scaling delta. Shall be zero or greater.
   * @return numberOfInstances
   **/
  //@Schema(description = "Number of instances of VNFC based on this VDU to deploy for an instantiation level or for a scaling delta. Shall be zero or greater.")
  
    public String getNumberOfInstances() {
    return numberOfInstances;
  }

  public void setNumberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
  }

  public VduDeltaSchema vduDeltaId(String vduDeltaId) {
    this.vduDeltaId = vduDeltaId;
    return this;
  }

  /**
   * Uniquely identifies a VDU.
   * @return vduDeltaId
   **/
  //@Schema(description = "Uniquely identifies a VDU.")
  
    public String getVduDeltaId() {
    return vduDeltaId;
  }

  public void setVduDeltaId(String vduDeltaId) {
    this.vduDeltaId = vduDeltaId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VduDeltaSchema vduDeltaSchema = (VduDeltaSchema) o;
    return Objects.equals(this.numberOfInstances, vduDeltaSchema.numberOfInstances) &&
        Objects.equals(this.vduDeltaId, vduDeltaSchema.vduDeltaId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfInstances, vduDeltaId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VduDeltaSchema {\n");
    
    sb.append("    numberOfInstances: ").append(toIndentedString(numberOfInstances)).append("\n");
    sb.append("    vduDeltaId: ").append(toIndentedString(vduDeltaId)).append("\n");
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
