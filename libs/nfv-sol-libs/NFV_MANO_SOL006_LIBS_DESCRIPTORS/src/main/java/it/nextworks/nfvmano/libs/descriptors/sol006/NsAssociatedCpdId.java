package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidNsNs
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsAssociatedCpdId {

  @JsonProperty("nsd-id")
  private String nsdId = null;

  @JsonProperty("ext-cpd-id")
  private String extCpdId = null;

  public NsAssociatedCpdId nsdId(String nsdId) {
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

  public NsAssociatedCpdId extCpdId(String extCpdId) {
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
    NsAssociatedCpdId nsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidNsNs = (NsAssociatedCpdId) o;
    return Objects.equals(this.nsdId, nsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidNsNs.nsdId) &&
        Objects.equals(this.extCpdId, nsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidNsNs.extCpdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nsdId, extCpdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdCpdorvirtuallinkAssociatedcpdidAssociatedcpdidNsNs {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    nsdId: ").append(toIndentedString(nsdId)).append("\n");
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
