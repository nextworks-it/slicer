package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * PnfSchema
 */


public class PnfSchema   {
  @JsonProperty("pnf-cpd-id")
  private String pnfCpdId = null;

  @JsonProperty("pnfd-id")
  private String pnfdId = null;

  public PnfSchema pnfCpdId(String pnfCpdId) {
    this.pnfCpdId = pnfCpdId;
    return this;
  }

  /**
   * Get pnfCpdId
   * @return pnfCpdId
   **/

  
    public String getPnfCpdId() {
    return pnfCpdId;
  }

  public void setPnfCpdId(String pnfCpdId) {
    this.pnfCpdId = pnfCpdId;
  }

  public PnfSchema pnfdId(String pnfdId) {
    this.pnfdId = pnfdId;
    return this;
  }

  /**
   * Get pnfdId
   * @return pnfdId
   **/

  
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
    PnfSchema pnfSchema = (PnfSchema) o;
    return Objects.equals(this.pnfCpdId, pnfSchema.pnfCpdId) &&
        Objects.equals(this.pnfdId, pnfSchema.pnfdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfCpdId, pnfdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PnfSchema {\n");
    
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
