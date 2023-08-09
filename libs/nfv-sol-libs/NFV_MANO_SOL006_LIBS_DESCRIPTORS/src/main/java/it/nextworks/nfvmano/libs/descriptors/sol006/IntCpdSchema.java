package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * IntCpdSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class IntCpdSchema {

  @JsonProperty("vdu-id")
  private String vduId = null;

  @JsonProperty("cpd")
  private String cpd = null;

  public IntCpdSchema vduId(String vduId) {
    this.vduId = vduId;
    return this;
  }

  /**
   * Get vduId
   * @return vduId
   **/
  //@Schema(description = "")
  
    public String getVduId() {
    return vduId;
  }

  public void setVduId(String vduId) {
    this.vduId = vduId;
  }

  public IntCpdSchema cpd(String cpd) {
    this.cpd = cpd;
    return this;
  }

  /**
   * Get cpd
   * @return cpd
   **/
  //@Schema(description = "")
  
    public String getCpd() {
    return cpd;
  }

  public void setCpd(String cpd) {
    this.cpd = cpd;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IntCpdSchema intCpdSchema = (IntCpdSchema) o;
    return Objects.equals(this.vduId, intCpdSchema.vduId) &&
        Objects.equals(this.cpd, intCpdSchema.cpd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vduId, cpd);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IntCpdSchema {\n");
    
    sb.append("    vduId: ").append(toIndentedString(vduId)).append("\n");
    sb.append("    cpd: ").append(toIndentedString(cpd)).append("\n");
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
