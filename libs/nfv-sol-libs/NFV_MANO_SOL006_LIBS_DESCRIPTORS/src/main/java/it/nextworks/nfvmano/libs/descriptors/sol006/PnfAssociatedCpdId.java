package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidPnfPnf
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class PnfAssociatedCpdId {

  @JsonProperty("pnfd-id")
  private String pnfdId = null;

  @JsonProperty("ext-cpd-id")
  private String extCpdId = null;

  public PnfAssociatedCpdId pnfdId(String pnfdId) {
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

  public PnfAssociatedCpdId extCpdId(String extCpdId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PnfAssociatedCpdId nsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidPnfPnf = (PnfAssociatedCpdId) o;
    return Objects.equals(this.pnfdId, nsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidPnfPnf.pnfdId) &&
        Objects.equals(this.extCpdId, nsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidPnfPnf.extCpdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfdId, extCpdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidPnfPnf {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    pnfdId: ").append(toIndentedString(pnfdId)).append("\n");
    sb.append("    extCpdId: ").append(toIndentedString(extCpdId)).append("\n");
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
