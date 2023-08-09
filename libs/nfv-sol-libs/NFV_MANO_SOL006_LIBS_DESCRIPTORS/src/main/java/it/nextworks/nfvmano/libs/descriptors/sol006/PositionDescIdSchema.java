package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * PositionDescIdSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class PositionDescIdSchema   {
  @JsonProperty("position-desc-id_id")
  private String positionDescIdId = null;

  @JsonProperty("cp-profile-id")

  private List<NsdCpprofileid> cpProfileId = null;

  public PositionDescIdSchema positionDescIdId(String positionDescIdId) {
    this.positionDescIdId = positionDescIdId;
    return this;
  }

  /**
   * Identifier of this NfpPositionDesc element.
   * @return positionDescIdId
   **/
  //@Schema(description = "Identifier of this NfpPositionDesc element.")
  
    public String getPositionDescIdId() {
    return positionDescIdId;
  }

  public void setPositionDescIdId(String positionDescIdId) {
    this.positionDescIdId = positionDescIdId;
  }

  public PositionDescIdSchema cpProfileId(List<NsdCpprofileid> cpProfileId) {
    this.cpProfileId = cpProfileId;
    return this;
  }

  public PositionDescIdSchema addCpProfileIdItem(NsdCpprofileid cpProfileIdItem) {
    if (this.cpProfileId == null) {
      this.cpProfileId = new ArrayList<NsdCpprofileid>();
    }
    this.cpProfileId.add(cpProfileIdItem);
    return this;
  }

  /**
   * References the profile of a connection point to be traversed by the traffic flows matching the criteria. This shall be a connection point attached to one of the constituent VNFs and PNFs of the parent VNFFG, or a SAP of one of the constituent nested NSs of the parent VNFFG.
   * @return cpProfileId
   **/
  //@Schema(description = "References the profile of a connection point to be traversed by the traffic flows matching the criteria. This shall be a connection point attached to one of the constituent VNFs and PNFs of the parent VNFFG, or a SAP of one of the constituent nested NSs of the parent VNFFG.")

    public List<NsdCpprofileid> getCpProfileId() {
    return cpProfileId;
  }

  public void setCpProfileId(List<NsdCpprofileid> cpProfileId) {
    this.cpProfileId = cpProfileId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PositionDescIdSchema positionDescIdSchema = (PositionDescIdSchema) o;
    return Objects.equals(this.positionDescIdId, positionDescIdSchema.positionDescIdId) &&
        Objects.equals(this.cpProfileId, positionDescIdSchema.cpProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(positionDescIdId, cpProfileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PositionDescIdSchema {\n");
    
    sb.append("    positionDescIdId: ").append(toIndentedString(positionDescIdId)).append("\n");
    sb.append("    cpProfileId: ").append(toIndentedString(cpProfileId)).append("\n");
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
