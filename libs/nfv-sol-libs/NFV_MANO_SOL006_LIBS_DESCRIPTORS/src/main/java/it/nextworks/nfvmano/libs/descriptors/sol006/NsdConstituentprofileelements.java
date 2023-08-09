package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdConstituentprofileelements
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdConstituentprofileelements {

  @JsonProperty("cpd-id")
  private String cpdId = null;

  @JsonProperty("id")
  private String id = null;

  public NsdConstituentprofileelements cpdId(String cpdId) {
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

  public NsdConstituentprofileelements id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Specifies the constituents of the CpProfile.
   * @return id
   **/
  //@Schema(description = "Specifies the constituents of the CpProfile.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdConstituentprofileelements nsdConstituentprofileelements = (NsdConstituentprofileelements) o;
    return Objects.equals(this.cpdId, nsdConstituentprofileelements.cpdId) &&
        Objects.equals(this.id, nsdConstituentprofileelements.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdId, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentprofileelements {\n");
    
    sb.append("    cpdId: ").append(toIndentedString(cpdId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
