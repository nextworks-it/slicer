package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



/**
 * CpProfileIdSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class CpProfileIdSchema   {
  @JsonProperty("constituent-profile-elements")

  private List<NsdConstituentprofileelements> constituentProfileElements = null;

  @JsonProperty("cp-profile-id_id")
  private String cpProfileIdId = null;

  public CpProfileIdSchema constituentProfileElements(List<NsdConstituentprofileelements> constituentProfileElements) {
    this.constituentProfileElements = constituentProfileElements;
    return this;
  }

  public CpProfileIdSchema addConstituentProfileElementsItem(NsdConstituentprofileelements constituentProfileElementsItem) {
    if (this.constituentProfileElements == null) {
      this.constituentProfileElements = new ArrayList<NsdConstituentprofileelements>();
    }
    this.constituentProfileElements.add(constituentProfileElementsItem);
    return this;
  }

  /**
   * Specifies the constituents of the CpProfile.
   * @return constituentProfileElements
   **/
  //@Schema(description = "Specifies the constituents of the CpProfile.")

    public List<NsdConstituentprofileelements> getConstituentProfileElements() {
    return constituentProfileElements;
  }

  public void setConstituentProfileElements(List<NsdConstituentprofileelements> constituentProfileElements) {
    this.constituentProfileElements = constituentProfileElements;
  }

  public CpProfileIdSchema cpProfileIdId(String cpProfileIdId) {
    this.cpProfileIdId = cpProfileIdId;
    return this;
  }

  /**
   * Identifier of this CpProfile information element. It uniquely identifies a CpProfile.
   * @return cpProfileIdId
   **/
  //@Schema(description = "Identifier of this CpProfile information element. It uniquely identifies a CpProfile.")
  
    public String getCpProfileIdId() {
    return cpProfileIdId;
  }

  public void setCpProfileIdId(String cpProfileIdId) {
    this.cpProfileIdId = cpProfileIdId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CpProfileIdSchema cpProfileIdSchema = (CpProfileIdSchema) o;
    return Objects.equals(this.constituentProfileElements, cpProfileIdSchema.constituentProfileElements) &&
        Objects.equals(this.cpProfileIdId, cpProfileIdSchema.cpProfileIdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(constituentProfileElements, cpProfileIdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CpProfileIdSchema {\n");
    
    sb.append("    constituentProfileElements: ").append(toIndentedString(constituentProfileElements)).append("\n");
    sb.append("    cpProfileIdId: ").append(toIndentedString(cpProfileIdId)).append("\n");
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
