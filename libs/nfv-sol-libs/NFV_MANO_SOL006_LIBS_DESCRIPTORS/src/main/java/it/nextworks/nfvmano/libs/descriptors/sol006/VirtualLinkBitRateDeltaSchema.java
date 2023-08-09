package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;




/**
 * VirtualLinkBitRateDeltaSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class VirtualLinkBitRateDeltaSchema   {
  @JsonProperty("virtual-link-bit-rate-delta_id")
  private String virtualLinkBitRateDeltaId = null;

  @JsonProperty("bit-rate-requirements")
  private VnfdAspectdeltadetailsBitraterequirements bitRateRequirements = null;

  public VirtualLinkBitRateDeltaSchema virtualLinkBitRateDeltaId(String virtualLinkBitRateDeltaId) {
    this.virtualLinkBitRateDeltaId = virtualLinkBitRateDeltaId;
    return this;
  }

  /**
   * Uniquely identifies a VnfVirtualLinkDesc.
   * @return virtualLinkBitRateDeltaId
   **/
  //@Schema(description = "Uniquely identifies a VnfVirtualLinkDesc.")
  
    public String getVirtualLinkBitRateDeltaId() {
    return virtualLinkBitRateDeltaId;
  }

  public void setVirtualLinkBitRateDeltaId(String virtualLinkBitRateDeltaId) {
    this.virtualLinkBitRateDeltaId = virtualLinkBitRateDeltaId;
  }

  public VirtualLinkBitRateDeltaSchema bitRateRequirements(VnfdAspectdeltadetailsBitraterequirements bitRateRequirements) {
    this.bitRateRequirements = bitRateRequirements;
    return this;
  }

  /**
   * Get bitRateRequirements
   * @return bitRateRequirements
   **/
  //@Schema(description = "")
  

    public VnfdAspectdeltadetailsBitraterequirements getBitRateRequirements() {
    return bitRateRequirements;
  }

  public void setBitRateRequirements(VnfdAspectdeltadetailsBitraterequirements bitRateRequirements) {
    this.bitRateRequirements = bitRateRequirements;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualLinkBitRateDeltaSchema virtualLinkBitRateDeltaSchema = (VirtualLinkBitRateDeltaSchema) o;
    return Objects.equals(this.virtualLinkBitRateDeltaId, virtualLinkBitRateDeltaSchema.virtualLinkBitRateDeltaId) &&
        Objects.equals(this.bitRateRequirements, virtualLinkBitRateDeltaSchema.bitRateRequirements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLinkBitRateDeltaId, bitRateRequirements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualLinkBitRateDeltaSchema {\n");
    
    sb.append("    virtualLinkBitRateDeltaId: ").append(toIndentedString(virtualLinkBitRateDeltaId)).append("\n");
    sb.append("    bitRateRequirements: ").append(toIndentedString(bitRateRequirements)).append("\n");
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
