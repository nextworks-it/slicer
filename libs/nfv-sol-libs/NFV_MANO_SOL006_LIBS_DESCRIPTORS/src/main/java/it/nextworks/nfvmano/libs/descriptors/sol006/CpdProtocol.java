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
 * CpdProtocol
 */

@Entity
public class CpdProtocol {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("address-data")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "cpd_protocol_fk", referencedColumnName = "uuid")
  private List<CpdAddressdata> addressData = null;

  @JsonProperty("associated-layer-protocol")
  private String associatedLayerProtocol = null;

  public CpdProtocol addressData(List<CpdAddressdata> addressData) {
    this.addressData = addressData;
    return this;
  }

  public CpdProtocol addAddressDataItem(CpdAddressdata addressDataItem) {
    if (this.addressData == null) {
      this.addressData = new ArrayList<>();
    }
    this.addressData.add(addressDataItem);
    return this;
  }

  /**
   * Provides information on the addresses to be assigned to the CP(s) instantiated from the CPD.
   * @return addressData
   **/

    public List<CpdAddressdata> getAddressData() {
    return addressData;
  }

  public void setAddressData(List<CpdAddressdata> addressData) {
    this.addressData = addressData;
  }

  public CpdProtocol associatedLayerProtocol(String associatedLayerProtocol) {
    this.associatedLayerProtocol = associatedLayerProtocol;
    return this;
  }

  /**
   * One of the values of the attribute layerProtocol of the Cpd IE.
   * @return associatedLayerProtocol
   **/

  
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
    CpdProtocol cpdProtocol = (CpdProtocol) o;
    return Objects.equals(this.addressData, cpdProtocol.addressData) &&
        Objects.equals(this.associatedLayerProtocol, cpdProtocol.associatedLayerProtocol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressData, associatedLayerProtocol);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CpdProtocol {\n");
    
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
