package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Uniquely identifies this VNF Indicator information element.
 */
//@Schema(description = "Uniquely identifies this VNF Indicator information element.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class VnfIndicatorInfoSchema   {
  @JsonProperty("vnf-indicator")
  private String vnfIndicator = null;

  @JsonProperty("vnfd-id")
  private String vnfdId = null;

  public VnfIndicatorInfoSchema vnfIndicator(String vnfIndicator) {
    this.vnfIndicator = vnfIndicator;
    return this;
  }

  /**
   * Identifies a VNF indicator within the VNFD.
   * @return vnfIndicator
   **/
  //@Schema(description = "Identifies a VNF indicator within the VNFD.")
  
    public String getVnfIndicator() {
    return vnfIndicator;
  }

  public void setVnfIndicator(String vnfIndicator) {
    this.vnfIndicator = vnfIndicator;
  }

  public VnfIndicatorInfoSchema vnfdId(String vnfdId) {
    this.vnfdId = vnfdId;
    return this;
  }

  /**
   * Identifies a VNFD.
   * @return vnfdId
   **/
  //@Schema(description = "Identifies a VNFD.")
  
    public String getVnfdId() {
    return vnfdId;
  }

  public void setVnfdId(String vnfdId) {
    this.vnfdId = vnfdId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfIndicatorInfoSchema vnfIndicatorInfoSchema = (VnfIndicatorInfoSchema) o;
    return Objects.equals(this.vnfIndicator, vnfIndicatorInfoSchema.vnfIndicator) &&
        Objects.equals(this.vnfdId, vnfIndicatorInfoSchema.vnfdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnfIndicator, vnfdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfIndicatorInfoSchema {\n");
    
    sb.append("    vnfIndicator: ").append(toIndentedString(vnfIndicator)).append("\n");
    sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
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
