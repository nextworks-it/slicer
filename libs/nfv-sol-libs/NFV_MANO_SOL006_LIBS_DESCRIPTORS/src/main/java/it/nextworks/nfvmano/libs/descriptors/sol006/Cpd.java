package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * A Cpd information element describes network connectivity to a compute resource or a VL.
 */
//@Schema(description = "A Cpd information element describes network connectivity to a compute resource or a VL.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class Cpd {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("protocol")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "cpd_fk", referencedColumnName = "uuid")
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

  public Cpd protocol(List<CpdProtocol> protocol) {
    this.protocol = protocol;
    return this;
  }

  public Cpd addProtocolItem(CpdProtocol protocolItem) {
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
  //@Schema(description = "Identifies the protocol layering information the CP uses for connectivity purposes and associated information. There shall be one cpProtocol for each layer protocol as indicated by the attribute layerProtocol. When a PnfExtCpd as defined in ETSI GS NFV-IFA 014 [i.8] is inherited from this Cpd, the cardinality is set to 0.")

    public List<CpdProtocol> getProtocol() {
    return protocol;
  }

  public void setProtocol(List<CpdProtocol> protocol) {
    this.protocol = protocol;
  }

  public Cpd description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Provides human-readable information on the purpose of the connection point (e.g. connection point for control plane traffic).
   * @return description
   **/
  //@Schema(description = "Provides human-readable information on the purpose of the connection point (e.g. connection point for control plane traffic).")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Cpd layerProtocol(List<String> layerProtocol) {
    this.layerProtocol = layerProtocol;
    return this;
  }

  public Cpd addLayerProtocolItem(String layerProtocolItem) {
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
  //@Schema(description = "")
  
    public List<String> getLayerProtocol() {
    return layerProtocol;
  }

  public void setLayerProtocol(List<String> layerProtocol) {
    this.layerProtocol = layerProtocol;
  }

  public Cpd trunkMode(Boolean trunkMode) {
    this.trunkMode = trunkMode;
    return this;
  }

  /**
   * Information about whether the Cp instantiated from this CPD is in Trunk mode (802.1Q or other). When operating in 'trunk mode', the Cp is capable of carrying traffic for several VLANs. A cardinality of 0 implies that trunkMode is not configured for the Cp i.e. It is equivalent to Boolean value 'false'.
   * @return trunkMode
   **/
  //@Schema(description = "Information about whether the Cp instantiated from this CPD is in Trunk mode (802.1Q or other). When operating in 'trunk mode', the Cp is capable of carrying traffic for several VLANs. A cardinality of 0 implies that trunkMode is not configured for the Cp i.e. It is equivalent to Boolean value 'false'.")
  
    public Boolean isTrunkMode() {
    return trunkMode;
  }

  public void setTrunkMode(Boolean trunkMode) {
    this.trunkMode = trunkMode;
  }

  public Cpd role(String role) {
    this.role = role;
    return this;
  }

  /**
   * Identifies the role of the connection points corresponding to the CPD in the context of the traffic flow patterns in the VNF, PNF or NS. For example an NS with a tree flow pattern within the NS will have legal cpRoles of ROOT and LEAF.
   * @return role
   **/
  //@Schema(description = "Identifies the role of the connection points corresponding to the CPD in the context of the traffic flow patterns in the VNF, PNF or NS. For example an NS with a tree flow pattern within the NS will have legal cpRoles of ROOT and LEAF.")
  
    public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Cpd id(String id) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cpd cpd = (Cpd) o;
    return Objects.equals(this.protocol, cpd.protocol) &&
        Objects.equals(this.description, cpd.description) &&
        Objects.equals(this.layerProtocol, cpd.layerProtocol) &&
        Objects.equals(this.trunkMode, cpd.trunkMode) &&
        Objects.equals(this.role, cpd.role) &&
        Objects.equals(this.id, cpd.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(protocol, description, layerProtocol, trunkMode, role, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cpd {\n");
    
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    layerProtocol: ").append(toIndentedString(layerProtocol)).append("\n");
    sb.append("    trunkMode: ").append(toIndentedString(trunkMode)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
