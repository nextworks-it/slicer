package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

/**
 * NsSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class NsSchema   {
  @JsonProperty("nsd-id")
  private String nsdId = null;

  @JsonProperty("sap-cpd-id")
  private String sapCpdId = null;

  public NsSchema nsdId(String nsdId) {
    this.nsdId = nsdId;
    return this;
  }

  /**
   * Get nsdId
   * @return nsdId
   **/
  //@Schema(description = "")
  
    public String getNsdId() {
    return nsdId;
  }

  public void setNsdId(String nsdId) {
    this.nsdId = nsdId;
  }

  public NsSchema sapCpdId(String sapCpdId) {
    this.sapCpdId = sapCpdId;
    return this;
  }

  /**
   * Get sapCpdId
   * @return sapCpdId
   **/
  //@Schema(description = "")
  
    public String getSapCpdId() {
    return sapCpdId;
  }

  public void setSapCpdId(String sapCpdId) {
    this.sapCpdId = sapCpdId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsSchema nsSchema = (NsSchema) o;
    return Objects.equals(this.nsdId, nsSchema.nsdId) &&
        Objects.equals(this.sapCpdId, nsSchema.sapCpdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nsdId, sapCpdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsSchema {\n");
    
    sb.append("    nsdId: ").append(toIndentedString(nsdId)).append("\n");
    sb.append("    sapCpdId: ").append(toIndentedString(sapCpdId)).append("\n");
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
