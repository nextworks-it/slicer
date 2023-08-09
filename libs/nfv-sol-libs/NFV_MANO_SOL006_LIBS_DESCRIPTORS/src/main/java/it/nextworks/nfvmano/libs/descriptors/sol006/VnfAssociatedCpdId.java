package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidVnfVnf
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class VnfAssociatedCpdId {

  @JsonProperty("ext-cpd-id")
  private String extCpdId = null;

  @JsonProperty("vnfd-id")
  private String vnfdId = null;

  public VnfAssociatedCpdId extCpdId(String extCpdId) {
    this.extCpdId = extCpdId;
    return this;
  }

  /**
   * Get extCpdId
   * @return extCpdId
   **/
  //@Schema(description = "")
  
    public String getExtCpdId() {
    return extCpdId;
  }

  public void setExtCpdId(String extCpdId) {
    this.extCpdId = extCpdId;
  }

  public VnfAssociatedCpdId vnfdId(String vnfdId) {
    this.vnfdId = vnfdId;
    return this;
  }

  /**
   * Get vnfdId
   * @return vnfdId
   **/
  //@Schema(description = "")
  
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
    VnfAssociatedCpdId nsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidVnfVnf = (VnfAssociatedCpdId) o;
    return Objects.equals(this.extCpdId, nsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidVnfVnf.extCpdId) &&
        Objects.equals(this.vnfdId, nsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidVnfVnf.vnfdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(extCpdId, vnfdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidVnfVnf {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    extCpdId: ").append(toIndentedString(extCpdId)).append("\n");
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
