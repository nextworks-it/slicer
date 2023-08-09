package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdConstituentcpdid2
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdConstituentcpdid2 {

  @JsonProperty("constituent-cpd-id")
  private String constituentCpdId = null;

  @JsonProperty("constituent-base-element-id")
  private String constituentBaseElementId = null;

  @JsonProperty("ip-address")
  private String ipAddress = null;

  public NsdConstituentcpdid2 constituentCpdId(String constituentCpdId) {
    this.constituentCpdId = constituentCpdId;
    return this;
  }

  /**
   * A reference to the descriptor of a connection point attached to one of the constituent VNFs and PNFs or to the descriptor of a NS SAP.
   * @return constituentCpdId
   **/
  //@Schema(description = "A reference to the descriptor of a connection point attached to one of the constituent VNFs and PNFs or to the descriptor of a NS SAP.")
  
    public String getConstituentCpdId() {
    return constituentCpdId;
  }

  public void setConstituentCpdId(String constituentCpdId) {
    this.constituentCpdId = constituentCpdId;
  }

  public NsdConstituentcpdid2 constituentBaseElementId(String constituentBaseElementId) {
    this.constituentBaseElementId = constituentBaseElementId;
    return this;
  }

  /**
   * Reference to the profile of an NS constituent.
   * @return constituentBaseElementId
   **/
  //@Schema(description = "Reference to the profile of an NS constituent.")
  
    public String getConstituentBaseElementId() {
    return constituentBaseElementId;
  }

  public void setConstituentBaseElementId(String constituentBaseElementId) {
    this.constituentBaseElementId = constituentBaseElementId;
  }

  public NsdConstituentcpdid2 ipAddress(String ipAddress) {
      this.ipAddress = ipAddress;
      return this;
  }

  public String getIpAddress() { return ipAddress; }

  public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdConstituentcpdid2 nsdConstituentcpdid2 = (NsdConstituentcpdid2) o;
    return Objects.equals(this.constituentCpdId, nsdConstituentcpdid2.constituentCpdId) &&
            Objects.equals(this.constituentBaseElementId, nsdConstituentcpdid2.constituentBaseElementId) &&
            Objects.equals(this.ipAddress, nsdConstituentcpdid2.ipAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(constituentCpdId, constituentBaseElementId, ipAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentcpdid2 {\n");
    
    sb.append("    constituentCpdId: ").append(toIndentedString(constituentCpdId)).append("\n");
    sb.append("    constituentBaseElementId: ").append(toIndentedString(constituentBaseElementId)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
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
