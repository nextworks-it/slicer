package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * Specifies the protocol data for a VL instantiated according to this profile. Cardinality 0 is used when no protocol data needs to be specified.
 */
//@Schema(description = "Specifies the protocol data for a VL instantiated according to this profile. Cardinality 0 is used when no protocol data needs to be specified.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VirtualLinkProtocolDataSchema {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("l2-protocol-data")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "virtual_link_protocol_data_schema_l2_protocol_data_fk", referencedColumnName = "uuid")
  private VirtuallinkprotocoldataSchemaL2protocoldata l2ProtocolData = null;

  @JsonProperty("l3-protocol-data")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "virtual_link_protocol_data_schema_l3_protocol_data_fk", referencedColumnName = "uuid")
  private VirtuallinkprotocoldataSchemaL3protocoldata l3ProtocolData = null;

  @JsonProperty("associated-layer-protocol")
  private String associatedLayerProtocol = null;

  public VirtualLinkProtocolDataSchema l2ProtocolData(VirtuallinkprotocoldataSchemaL2protocoldata l2ProtocolData) {
    this.l2ProtocolData = l2ProtocolData;
    return this;
  }

  /**
   * Get l2ProtocolData
   * @return l2ProtocolData
   **/
  //@Schema(description = "")
  

    public VirtuallinkprotocoldataSchemaL2protocoldata getL2ProtocolData() {
    return l2ProtocolData;
  }

  public void setL2ProtocolData(VirtuallinkprotocoldataSchemaL2protocoldata l2ProtocolData) {
    this.l2ProtocolData = l2ProtocolData;
  }

  public VirtualLinkProtocolDataSchema l3ProtocolData(VirtuallinkprotocoldataSchemaL3protocoldata l3ProtocolData) {
    this.l3ProtocolData = l3ProtocolData;
    return this;
  }

  /**
   * Get l3ProtocolData
   * @return l3ProtocolData
   **/
  //@Schema(description = "")
  

    public VirtuallinkprotocoldataSchemaL3protocoldata getL3ProtocolData() {
    return l3ProtocolData;
  }

  public void setL3ProtocolData(VirtuallinkprotocoldataSchemaL3protocoldata l3ProtocolData) {
    this.l3ProtocolData = l3ProtocolData;
  }

  public VirtualLinkProtocolDataSchema associatedLayerProtocol(String associatedLayerProtocol) {
    this.associatedLayerProtocol = associatedLayerProtocol;
    return this;
  }

  /**
   * One of the values of the attribute layerProtocol of the ConnectivityType IE.
   * @return associatedLayerProtocol
   **/
  //@Schema(description = "One of the values of the attribute layerProtocol of the ConnectivityType IE.")
  
    public String getAssociatedLayerProtocol() {
    return associatedLayerProtocol;
  }

  public void setAssociatedLayerProtocol(String associatedLayerProtocol) {
    this.associatedLayerProtocol = associatedLayerProtocol;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualLinkProtocolDataSchema virtualLinkProtocolDataSchema = (VirtualLinkProtocolDataSchema) o;
    return Objects.equals(this.l2ProtocolData, virtualLinkProtocolDataSchema.l2ProtocolData) &&
        Objects.equals(this.l3ProtocolData, virtualLinkProtocolDataSchema.l3ProtocolData) &&
        Objects.equals(this.associatedLayerProtocol, virtualLinkProtocolDataSchema.associatedLayerProtocol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(l2ProtocolData, l3ProtocolData, associatedLayerProtocol);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualLinkProtocolDataSchema {\n");
    
    sb.append("    l2ProtocolData: ").append(toIndentedString(l2ProtocolData)).append("\n");
    sb.append("    l3ProtocolData: ").append(toIndentedString(l3ProtocolData)).append("\n");
    sb.append("    associatedLayerProtocol: ").append(toIndentedString(associatedLayerProtocol)).append("\n");
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
