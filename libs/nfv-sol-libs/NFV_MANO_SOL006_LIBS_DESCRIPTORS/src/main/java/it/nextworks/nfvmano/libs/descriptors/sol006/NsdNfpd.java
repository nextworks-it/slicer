package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NsdNfpd
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdNfpd {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("rule")
  private String rule = null;

  @JsonProperty("position-desc-id")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_nfpd_fk", referencedColumnName = "uuid")
  private List<NsdPositiondescid> positionDescId = null;

  public NsdNfpd id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifies this nfpd information element within a VNFFGD.
   * @return id
   **/
  //@Schema(description = "Identifies this nfpd information element within a VNFFGD.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public NsdNfpd rule(String rule) {
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

  public NsdNfpd positionDescId(List<NsdPositiondescid> positionDescId) {
    this.positionDescId = positionDescId;
    return this;
  }

  public NsdNfpd addPositionDescIdItem(NsdPositiondescid positionDescIdItem) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdNfpd nsdNfpd = (NsdNfpd) o;
    return Objects.equals(this.id, nsdNfpd.id) &&
        Objects.equals(this.rule, nsdNfpd.rule) &&
        Objects.equals(this.positionDescId, nsdNfpd.positionDescId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, rule, positionDescId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdNfpd {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    rule: ").append(toIndentedString(rule)).append("\n");
    sb.append("    positionDescId: ").append(toIndentedString(positionDescId)).append("\n");
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
