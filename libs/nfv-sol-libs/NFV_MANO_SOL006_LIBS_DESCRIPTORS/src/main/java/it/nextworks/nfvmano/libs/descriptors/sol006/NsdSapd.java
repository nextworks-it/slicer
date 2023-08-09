package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * NsdSapd
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdSapd {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("address-assignment")
  private Boolean addressAssignment = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("virtual-link-desc")
  private String virtualLinkDesc = null;

  @JsonProperty("vnf")
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "extCpdId", column = @Column(name = "vnf_associated_ext_cpd_id")) })
  private VnfAssociatedCpdId vnf = null;

  @JsonProperty("pnf")
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "extCpdId", column = @Column(name = "pnf_associated_ext_cpd_id")) })
  private PnfAssociatedCpdId pnf = null;

  @JsonProperty("ns")
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "extCpdId", column = @Column(name = "ns_associated_ext_cpd_id")) })
  private NsAssociatedCpdId ns = null;

  @JsonProperty("floating-ip-required")
  private Boolean floatingIpRequired = null;

  public NsdSapd addressAssignment(Boolean addressAssignment) {
    this.addressAssignment = addressAssignment;
    return this;
  }

  /**
   * Specify whether the SAP address assignment is under the responsibility of management and orchestration functions or not. If it is set to True, management and orchestration functions are responsible for assigning addresses to the access points instantiated from this SAPD.
   * @return addressAssignment
   **/
  //@Schema(description = "Specify whether the SAP address assignment is under the responsibility of management and orchestration functions or not. If it is set to True, management and orchestration functions are responsible for assigning addresses to the access points instantiated from this SAPD.")
  
    public Boolean isAddressAssignment() {
    return addressAssignment;
  }

  public void setAddressAssignment(Boolean addressAssignment) {
    this.addressAssignment = addressAssignment;
  }

  public NsdSapd id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this Cpd information element.
   * @return id
   **/
  //@Schema(description = "Identifier of this Cpd information element.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public NsdSapd virtualLinkDesc(String virtualLinkDesc) {
    this.virtualLinkDesc = virtualLinkDesc;
    return this;
  }

  public String getVirtualLinkDesc() {
    return virtualLinkDesc;
  }

  public void setVirtualLinkDesc(String virtualLinkDesc) {
    this.virtualLinkDesc = virtualLinkDesc;
  }

  public NsdSapd vnf(VnfAssociatedCpdId vnf) {
    this.vnf = vnf;
    return this;
  }

  public VnfAssociatedCpdId getVnf() {
    return vnf;
  }

  public void setVnf(VnfAssociatedCpdId vnf) {
    this.vnf = vnf;
  }

  public NsdSapd pnf(PnfAssociatedCpdId pnf) {
    this.pnf = pnf;
    return this;
  }

  public PnfAssociatedCpdId getPnf() {
    return pnf;
  }

  public void setPnf(PnfAssociatedCpdId pnf) {
    this.pnf = pnf;
  }

  public NsdSapd ns(NsAssociatedCpdId ns) {
    this.ns = ns;
    return this;
  }

  public NsAssociatedCpdId getNs() {
    return ns;
  }

  public void setNs(NsAssociatedCpdId ns) {
    this.ns = ns;
  }

  public NsdSapd floatingIpRequired(Boolean floatingIpRequired) {
      this.floatingIpRequired = floatingIpRequired;
      return this;
  }

  public Boolean getFloatingIpRequired() { return floatingIpRequired; }

  public void setFloatingIpRequired(Boolean floatingIpRequired) { this.floatingIpRequired = floatingIpRequired; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdSapd nsdSapd = (NsdSapd) o;
    return Objects.equals(this.addressAssignment, nsdSapd.addressAssignment) &&
            Objects.equals(this.id, nsdSapd.id) &&
            Objects.equals(this.virtualLinkDesc, nsdSapd.virtualLinkDesc) &&
            Objects.equals(this.vnf, nsdSapd.vnf) &&
            Objects.equals(this.pnf, nsdSapd.pnf) &&
            Objects.equals(this.ns, nsdSapd.ns) &&
            Objects.equals(this.floatingIpRequired, nsdSapd.floatingIpRequired);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressAssignment, id, virtualLinkDesc, vnf, pnf, ns, floatingIpRequired);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("class NsdSapd {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    addressAssignment: ").append(toIndentedString(addressAssignment)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    virtualLinkDesc: ").append(toIndentedString(virtualLinkDesc)).append("\n");
    sb.append("    vnf: ").append(toIndentedString(vnf)).append("\n");
    sb.append("    pnf: ").append(toIndentedString(pnf)).append("\n");
    sb.append("    ns: ").append(toIndentedString(ns)).append("\n");
    sb.append("    floatingIpRequired: ").append(toIndentedString(floatingIpRequired)).append("\n");
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
