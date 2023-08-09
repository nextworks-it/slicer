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
 * ProtocolSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class ProtocolSchema {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("address-data")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "protocol_schema_fk", referencedColumnName = "uuid")
  private List<CpdAddressdata> addressData = null;

  @JsonProperty("associated-layer-protocol")
  private String associatedLayerProtocol = null;

  public ProtocolSchema addressData(List<CpdAddressdata> addressData) {
    this.addressData = addressData;
    return this;
  }

  public ProtocolSchema addAddressDataItem(CpdAddressdata addressDataItem) {
    if (this.addressData == null) {
      this.addressData = new ArrayList<CpdAddressdata>();
    }
    this.addressData.add(addressDataItem);
    return this;
  }

  /**
   * Provides information on the addresses to be assigned to the CP(s) instantiated from the CPD.
   * @return addressData
   **/
  //@Schema(description = "Provides information on the addresses to be assigned to the CP(s) instantiated from the CPD.")

    public List<CpdAddressdata> getAddressData() {
    return addressData;
  }

  public void setAddressData(List<CpdAddressdata> addressData) {
    this.addressData = addressData;
  }

  public ProtocolSchema associatedLayerProtocol(String associatedLayerProtocol) {
    this.associatedLayerProtocol = associatedLayerProtocol;
    return this;
  }

  /**
   * One of the values of the attribute layerProtocol of the Cpd IE.
   * @return associatedLayerProtocol
   **/
  //@Schema(description = "One of the values of the attribute layerProtocol of the Cpd IE.")
  
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
    ProtocolSchema protocolSchema = (ProtocolSchema) o;
    return Objects.equals(this.addressData, protocolSchema.addressData) &&
        Objects.equals(this.associatedLayerProtocol, protocolSchema.associatedLayerProtocol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressData, associatedLayerProtocol);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProtocolSchema {\n");
    
    sb.append("    addressData: ").append(toIndentedString(addressData)).append("\n");
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
