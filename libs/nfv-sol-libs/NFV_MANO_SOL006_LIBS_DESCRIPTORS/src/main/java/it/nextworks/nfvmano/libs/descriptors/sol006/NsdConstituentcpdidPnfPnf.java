package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdConstituentcpdidPnfPnf
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdConstituentcpdidPnfPnf {

  @JsonProperty("pnf-cpd-id")
  private String pnfCpdId = null;

  @JsonProperty("pnfd-id")
  private String pnfdId = null;

  public NsdConstituentcpdidPnfPnf pnfCpdId(String pnfCpdId) {
    this.pnfCpdId = pnfCpdId;
    return this;
  }

  /**
   * Get pnfCpdId
   * @return pnfCpdId
   **/
  //@Schema(description = "")
  
    public String getPnfCpdId() {
    return pnfCpdId;
  }

  public void setPnfCpdId(String pnfCpdId) {
    this.pnfCpdId = pnfCpdId;
  }

  public NsdConstituentcpdidPnfPnf pnfdId(String pnfdId) {
    this.pnfdId = pnfdId;
    return this;
  }

  /**
   * Get pnfdId
   * @return pnfdId
   **/
  //@Schema(description = "")
  
    public String getPnfdId() {
    return pnfdId;
  }

  public void setPnfdId(String pnfdId) {
    this.pnfdId = pnfdId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdConstituentcpdidPnfPnf nsdConstituentcpdidPnfPnf = (NsdConstituentcpdidPnfPnf) o;
    return Objects.equals(this.pnfCpdId, nsdConstituentcpdidPnfPnf.pnfCpdId) &&
        Objects.equals(this.pnfdId, nsdConstituentcpdidPnfPnf.pnfdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfCpdId, pnfdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentcpdidPnfPnf {\n");
    
    sb.append("    pnfCpdId: ").append(toIndentedString(pnfCpdId)).append("\n");
    sb.append("    pnfdId: ").append(toIndentedString(pnfdId)).append("\n");
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
