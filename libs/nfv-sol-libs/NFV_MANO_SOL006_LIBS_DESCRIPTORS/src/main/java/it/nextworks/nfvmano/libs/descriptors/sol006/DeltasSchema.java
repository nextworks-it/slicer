package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



/**
 * DeltasSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class DeltasSchema   {
  @JsonProperty("virtual-link-bit-rate-delta")

  private List<VnfdAspectdeltadetailsVirtuallinkbitratedelta> virtualLinkBitRateDelta = null;

  @JsonProperty("vdu-delta")

  private List<VnfdAspectdeltadetailsVdudelta> vduDelta = null;

  @JsonProperty("deltas_id")
  private String deltasId = null;

  public DeltasSchema virtualLinkBitRateDelta(List<VnfdAspectdeltadetailsVirtuallinkbitratedelta> virtualLinkBitRateDelta) {
    this.virtualLinkBitRateDelta = virtualLinkBitRateDelta;
    return this;
  }

  public DeltasSchema addVirtualLinkBitRateDeltaItem(VnfdAspectdeltadetailsVirtuallinkbitratedelta virtualLinkBitRateDeltaItem) {
    if (this.virtualLinkBitRateDelta == null) {
      this.virtualLinkBitRateDelta = new ArrayList<VnfdAspectdeltadetailsVirtuallinkbitratedelta>();
    }
    this.virtualLinkBitRateDelta.add(virtualLinkBitRateDeltaItem);
    return this;
  }

  /**
   * The bitrate to be added or removed to virtual links created from particular virtual link descriptors.
   * @return virtualLinkBitRateDelta
   **/
  //@Schema(description = "The bitrate to be added or removed to virtual links created from particular virtual link descriptors.")

    public List<VnfdAspectdeltadetailsVirtuallinkbitratedelta> getVirtualLinkBitRateDelta() {
    return virtualLinkBitRateDelta;
  }

  public void setVirtualLinkBitRateDelta(List<VnfdAspectdeltadetailsVirtuallinkbitratedelta> virtualLinkBitRateDelta) {
    this.virtualLinkBitRateDelta = virtualLinkBitRateDelta;
  }

  public DeltasSchema vduDelta(List<VnfdAspectdeltadetailsVdudelta> vduDelta) {
    this.vduDelta = vduDelta;
    return this;
  }

  public DeltasSchema addVduDeltaItem(VnfdAspectdeltadetailsVdudelta vduDeltaItem) {
    if (this.vduDelta == null) {
      this.vduDelta = new ArrayList<VnfdAspectdeltadetailsVdudelta>();
    }
    this.vduDelta.add(vduDeltaItem);
    return this;
  }

  /**
   * The number of VNFC instances based on particular VDUs to be created or removed.
   * @return vduDelta
   **/
  //@Schema(description = "The number of VNFC instances based on particular VDUs to be created or removed.")

    public List<VnfdAspectdeltadetailsVdudelta> getVduDelta() {
    return vduDelta;
  }

  public void setVduDelta(List<VnfdAspectdeltadetailsVdudelta> vduDelta) {
    this.vduDelta = vduDelta;
  }

  public DeltasSchema deltasId(String deltasId) {
    this.deltasId = deltasId;
    return this;
  }

  /**
   * Identifier of this scaling delta.
   * @return deltasId
   **/
  //@Schema(description = "Identifier of this scaling delta.")
  
    public String getDeltasId() {
    return deltasId;
  }

  public void setDeltasId(String deltasId) {
    this.deltasId = deltasId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeltasSchema deltasSchema = (DeltasSchema) o;
    return Objects.equals(this.virtualLinkBitRateDelta, deltasSchema.virtualLinkBitRateDelta) &&
        Objects.equals(this.vduDelta, deltasSchema.vduDelta) &&
        Objects.equals(this.deltasId, deltasSchema.deltasId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLinkBitRateDelta, vduDelta, deltasId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeltasSchema {\n");
    
    sb.append("    virtualLinkBitRateDelta: ").append(toIndentedString(virtualLinkBitRateDelta)).append("\n");
    sb.append("    vduDelta: ").append(toIndentedString(vduDelta)).append("\n");
    sb.append("    deltasId: ").append(toIndentedString(deltasId)).append("\n");
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
