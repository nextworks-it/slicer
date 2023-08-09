package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * ConstituentProfileElementsSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class ConstituentProfileElementsSchema   {
  @JsonProperty("cpd-id")
  private String cpdId = null;

  @JsonProperty("constituent-profile-elements_id")
  private String constituentProfileElementsId = null;

  public ConstituentProfileElementsSchema cpdId(String cpdId) {
    this.cpdId = cpdId;
    return this;
  }

  /**
   * References the VNF external CPD for a given VnfProfile, or the PNF external CPD for a given PnfProfile, or a NS SAPD for a give NsProfile identified by the constituentBasedElementId.
   * @return cpdId
   **/
  //@Schema(description = "References the VNF external CPD for a given VnfProfile, or the PNF external CPD for a given PnfProfile, or a NS SAPD for a give NsProfile identified by the constituentBasedElementId.")
  
    public String getCpdId() {
    return cpdId;
  }

  public void setCpdId(String cpdId) {
    this.cpdId = cpdId;
  }

  public ConstituentProfileElementsSchema constituentProfileElementsId(String constituentProfileElementsId) {
    this.constituentProfileElementsId = constituentProfileElementsId;
    return this;
  }

  /**
   * Specifies the constituents of the CpProfile.
   * @return constituentProfileElementsId
   **/
  //@Schema(description = "Specifies the constituents of the CpProfile.")
  
    public String getConstituentProfileElementsId() {
    return constituentProfileElementsId;
  }

  public void setConstituentProfileElementsId(String constituentProfileElementsId) {
    this.constituentProfileElementsId = constituentProfileElementsId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConstituentProfileElementsSchema constituentProfileElementsSchema = (ConstituentProfileElementsSchema) o;
    return Objects.equals(this.cpdId, constituentProfileElementsSchema.cpdId) &&
        Objects.equals(this.constituentProfileElementsId, constituentProfileElementsSchema.constituentProfileElementsId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdId, constituentProfileElementsId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConstituentProfileElementsSchema {\n");
    
    sb.append("    cpdId: ").append(toIndentedString(cpdId)).append("\n");
    sb.append("    constituentProfileElementsId: ").append(toIndentedString(constituentProfileElementsId)).append("\n");
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
