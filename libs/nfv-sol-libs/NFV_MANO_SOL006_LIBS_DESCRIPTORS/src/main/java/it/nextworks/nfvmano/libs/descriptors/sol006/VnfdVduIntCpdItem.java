package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.PortSecurityDisableStrategyEnum;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * AllOfvnfdVduIntCpdItems
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdVduIntCpdItem extends VirtualNetworkInterfaceRequirements {

  @JsonProperty("protocol")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_vdu_int_cpd_item_fk", referencedColumnName = "uuid")
  private List<CpdProtocol> protocol = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("layer-protocol")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> layerProtocol = null;

  @JsonProperty("trunk-mode")
  private Boolean trunkMode = null;

  @JsonProperty("role")
  private String role = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("int-virtual-link-desc")
  private String intVirtualLinkDesc = null;

  @JsonProperty("security-group-rule-id")
  private String securityGroupRuleId = null;

  @JsonProperty("nicio-requirements")
  private String nicioRequirements = null;

  @JsonProperty("bitrate-requirement")
  private String bitrateRequirement = null;

  @JsonProperty("order")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  @Column(name = "sort")
  private List<String> order = null;

  @JsonProperty("port-security-enabled")
  private Boolean portSecurityEnabled = null;

  @JsonProperty("port-security-disable-strategy")
  private PortSecurityDisableStrategyEnum portSecurityDisableStrategy = null;

  public VnfdVduIntCpdItem protocol(List<CpdProtocol> protocol) {
    this.protocol = protocol;
    return this;
  }

  public VnfdVduIntCpdItem addProtocolItem(CpdProtocol protocolItem) {
    if (this.protocol == null) {
      this.protocol = new ArrayList<CpdProtocol>();
    }
    this.protocol.add(protocolItem);
    return this;
  }

  /**
   * Identifies the protocol layering information the CP uses for connectivity purposes and associated information. There shall be one cpProtocol for each layer protocol as indicated by the attribute layerProtocol. When a PnfExtCpd as defined in ETSI GS NFV-IFA 014 [i.8] is inherited from this Cpd, the cardinality is set to 0.
   * @return protocol
   **/
      public List<CpdProtocol> getProtocol() {
    return protocol;
  }

  public void setProtocol(List<CpdProtocol> protocol) {
    this.protocol = protocol;
  }

  public VnfdVduIntCpdItem description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Provides human-readable information on the purpose of the connection point (e.g. connection point for control plane traffic).
   * @return description
   **/

  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public VnfdVduIntCpdItem layerProtocol(List<String> layerProtocol) {
    this.layerProtocol = layerProtocol;
    return this;
  }

  public VnfdVduIntCpdItem addLayerProtocolItem(String layerProtocolItem) {
    if (this.layerProtocol == null) {
      this.layerProtocol = new ArrayList<String>();
    }
    this.layerProtocol.add(layerProtocolItem);
    return this;
  }

  /**
   * Get layerProtocol
   * @return layerProtocol
   **/

  
    public List<String> getLayerProtocol() {
    return layerProtocol;
  }

  public void setLayerProtocol(List<String> layerProtocol) {
    this.layerProtocol = layerProtocol;
  }

  public VnfdVduIntCpdItem trunkMode(Boolean trunkMode) {
    this.trunkMode = trunkMode;
    return this;
  }

  /**
   * Information about whether the Cp instantiated from this CPD is in Trunk mode (802.1Q or other). When operating in 'trunk mode', the Cp is capable of carrying traffic for several VLANs. A cardinality of 0 implies that trunkMode is not configured for the Cp i.e. It is equivalent to Boolean value 'false'.
   * @return trunkMode
   **/

  
    public Boolean isTrunkMode() {
    return trunkMode;
  }

  public void setTrunkMode(Boolean trunkMode) {
    this.trunkMode = trunkMode;
  }

  public VnfdVduIntCpdItem role(String role) {
    this.role = role;
    return this;
  }

  /**
   * Identifies the role of the connection points corresponding to the CPD in the context of the traffic flow patterns in the VNF, PNF or NS. For example an NS with a tree flow pattern within the NS will have legal cpRoles of ROOT and LEAF.
   * @return role
   **/

  
    public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public VnfdVduIntCpdItem id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this Cpd information element.
   * @return id
   **/

  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfdVduIntCpdItem intVirtualLinkDesc(String intVirtualLinkDesc) {
    this.intVirtualLinkDesc = intVirtualLinkDesc;
    return this;
  }

  /**
   * Reference of the internal VLD which this internal CPD connects to.
   * @return intVirtualLinkDesc
   **/

  
    public String getIntVirtualLinkDesc() {
    return intVirtualLinkDesc;
  }

  public void setIntVirtualLinkDesc(String intVirtualLinkDesc) {
    this.intVirtualLinkDesc = intVirtualLinkDesc;
  }

  public VnfdVduIntCpdItem securityGroupRuleId(String securityGroupRuleId) {
    this.securityGroupRuleId = securityGroupRuleId;
    return this;
  }

  /**
   * Reference of the security group rules bound to this CPD.
   * @return securityGroupRuleId
   **/

  
    public String getSecurityGroupRuleId() {
    return securityGroupRuleId;
  }

  public void setSecurityGroupRuleId(String securityGroupRuleId) {
    this.securityGroupRuleId = securityGroupRuleId;
  }

  public VnfdVduIntCpdItem nicioRequirements(String nicioRequirements) {
    this.nicioRequirements = nicioRequirements;
    return this;
  }

  /**
   * This references (couples) the CPD with any logical node I/O requirements (for network devices) that may have been created. Linking these attributes is necessary so that so that I/O requirements that need to be articulated at the logical node level can be associated with the network interface requirements associated with the CPD.
   * @return nicioRequirements
   **/

  
    public String getNicioRequirements() {
    return nicioRequirements;
  }

  public void setNicioRequirements(String nicioRequirements) {
    this.nicioRequirements = nicioRequirements;
  }

  public VnfdVduIntCpdItem bitrateRequirement(String bitrateRequirement) {
    this.bitrateRequirement = bitrateRequirement;
    return this;
  }

  /**
   * Bitrate requirement on this CP.
   * @return bitrateRequirement
   **/

  
    public String getBitrateRequirement() {
    return bitrateRequirement;
  }

  public void setBitrateRequirement(String bitrateRequirement) {
    this.bitrateRequirement = bitrateRequirement;
  }

  public VnfdVduIntCpdItem order(List<String> order) {
    this.order = order;
    return this;
  }

  public VnfdVduIntCpdItem addOrderItem(String orderItem) {
    if (this.order == null) {
      this.order = new ArrayList<String>();
    }
    this.order.add(orderItem);
    return this;
  }

  /**
   * Get order
   * @return order
   **/

  
    public List<String> getOrder() {
    return order;
  }

  public void setOrder(List<String> order) {
    this.order = order;
  }

  public VnfdVduIntCpdItem portSecurityEnabled(Boolean portSecurityEnabled) {
      this.portSecurityEnabled = portSecurityEnabled;
      return this;
  }

  public Boolean getPortSecurityEnabled() { return portSecurityEnabled; }

  public void setPortSecurityEnabled(Boolean portSecurityEnabled) { this.portSecurityEnabled = portSecurityEnabled; }

  public VnfdVduIntCpdItem portSecurityDisableStrategy(PortSecurityDisableStrategyEnum portSecurityDisableStrategy) {
      this.portSecurityDisableStrategy = portSecurityDisableStrategy;
      return this;
  }

  public PortSecurityDisableStrategyEnum getPortSecurityDisableStrategy() { return portSecurityDisableStrategy; }

  public void setPortSecurityDisableStrategy(PortSecurityDisableStrategyEnum portSecurityDisableStrategy) {
    this.portSecurityDisableStrategy = portSecurityDisableStrategy;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdVduIntCpdItem allOfvnfdVduIntCpdItems = (VnfdVduIntCpdItem) o;
    return Objects.equals(this.protocol, allOfvnfdVduIntCpdItems.protocol) &&
            Objects.equals(this.description, allOfvnfdVduIntCpdItems.description) &&
            Objects.equals(this.layerProtocol, allOfvnfdVduIntCpdItems.layerProtocol) &&
            Objects.equals(this.trunkMode, allOfvnfdVduIntCpdItems.trunkMode) &&
            Objects.equals(this.role, allOfvnfdVduIntCpdItems.role) &&
            Objects.equals(this.id, allOfvnfdVduIntCpdItems.id) &&
            Objects.equals(this.intVirtualLinkDesc, allOfvnfdVduIntCpdItems.intVirtualLinkDesc) &&
            Objects.equals(this.securityGroupRuleId, allOfvnfdVduIntCpdItems.securityGroupRuleId) &&
            Objects.equals(this.nicioRequirements, allOfvnfdVduIntCpdItems.nicioRequirements) &&
            Objects.equals(this.bitrateRequirement, allOfvnfdVduIntCpdItems.bitrateRequirement) &&
            Objects.equals(this.order, allOfvnfdVduIntCpdItems.order) &&
            Objects.equals(this.portSecurityEnabled, allOfvnfdVduIntCpdItems.portSecurityEnabled) &&
            Objects.equals(this.portSecurityDisableStrategy, allOfvnfdVduIntCpdItems.portSecurityDisableStrategy) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(protocol, description, layerProtocol, trunkMode, role, id, intVirtualLinkDesc,
            securityGroupRuleId, nicioRequirements, bitrateRequirement, order, portSecurityEnabled,
            portSecurityDisableStrategy, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllOfvnfdVduIntCpdItems {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    layerProtocol: ").append(toIndentedString(layerProtocol)).append("\n");
    sb.append("    trunkMode: ").append(toIndentedString(trunkMode)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    intVirtualLinkDesc: ").append(toIndentedString(intVirtualLinkDesc)).append("\n");
    sb.append("    securityGroupRuleId: ").append(toIndentedString(securityGroupRuleId)).append("\n");
    sb.append("    nicioRequirements: ").append(toIndentedString(nicioRequirements)).append("\n");
    sb.append("    bitrateRequirement: ").append(toIndentedString(bitrateRequirement)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
    sb.append("    portSecurityEnabled: ").append(toIndentedString(portSecurityEnabled)).append("\n");
    sb.append("    portSecurityDisableStrategy: ").append(toIndentedString(portSecurityDisableStrategy)).append("\n");
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
