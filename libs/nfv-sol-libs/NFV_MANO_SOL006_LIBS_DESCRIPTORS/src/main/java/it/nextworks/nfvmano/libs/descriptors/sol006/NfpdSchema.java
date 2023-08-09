package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * NfpdSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class NfpdSchema   {
  @JsonProperty("position-desc-id")

  private List<NsdPositiondescid> positionDescId = null;

  @JsonProperty("rule")
  private String rule = null;

  @JsonProperty("nfpd_id")
  private String nfpdId = null;

  public NfpdSchema positionDescId(List<NsdPositiondescid> positionDescId) {
    this.positionDescId = positionDescId;
    return this;
  }

  public NfpdSchema addPositionDescIdItem(NsdPositiondescid positionDescIdItem) {
    if (this.positionDescId == null) {
      this.positionDescId = new ArrayList<NsdPositiondescid>();
    }
    this.positionDescId.add(positionDescIdItem);
    return this;
  }

  /**
   * Describes a position in the NFP in terms of one or more CP profiles and rules for distributing the traffic among CP and SAP instances created from the CPD or SAPD associated to these profiles. This shall be connection point profile, which is either a CPD associated with the VnfProfile of a constituent VNF, or a CPD associated with the PnfProfile of a constituent PNF, or a SAPD associated with the NsProfile of a nested NS. The related VnfProfile, PnfProfile and NsProfile shall be included in the parent VNFFGD.
   * @return positionDescId
   **/
  //@Schema(description = "Describes a position in the NFP in terms of one or more CP profiles and rules for distributing the traffic among CP and SAP instances created from the CPD or SAPD associated to these profiles. This shall be connection point profile, which is either a CPD associated with the VnfProfile of a constituent VNF, or a CPD associated with the PnfProfile of a constituent PNF, or a SAPD associated with the NsProfile of a nested NS. The related VnfProfile, PnfProfile and NsProfile shall be included in the parent VNFFGD.")

    public List<NsdPositiondescid> getPositionDescId() {
    return positionDescId;
  }

  public void setPositionDescId(List<NsdPositiondescid> positionDescId) {
    this.positionDescId = positionDescId;
  }

  public NfpdSchema rule(String rule) {
    this.rule = rule;
    return this;
  }

  /**
   * Provides an NFP classification and selection rule. The rule may be expressed as a criteria constructed out of atomic assertions linked by Boolean operators AND, OR and NOT.
   * @return rule
   **/
  //@Schema(description = "Provides an NFP classification and selection rule. The rule may be expressed as a criteria constructed out of atomic assertions linked by Boolean operators AND, OR and NOT.")
  
    public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public NfpdSchema nfpdId(String nfpdId) {
    this.nfpdId = nfpdId;
    return this;
  }

  /**
   * Identifies this nfpd information element within a VNFFGD.
   * @return nfpdId
   **/
  //@Schema(description = "Identifies this nfpd information element within a VNFFGD.")
  
    public String getNfpdId() {
    return nfpdId;
  }

  public void setNfpdId(String nfpdId) {
    this.nfpdId = nfpdId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfpdSchema nfpdSchema = (NfpdSchema) o;
    return Objects.equals(this.positionDescId, nfpdSchema.positionDescId) &&
        Objects.equals(this.rule, nfpdSchema.rule) &&
        Objects.equals(this.nfpdId, nfpdSchema.nfpdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(positionDescId, rule, nfpdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfpdSchema {\n");
    
    sb.append("    positionDescId: ").append(toIndentedString(positionDescId)).append("\n");
    sb.append("    rule: ").append(toIndentedString(rule)).append("\n");
    sb.append("    nfpdId: ").append(toIndentedString(nfpdId)).append("\n");
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
