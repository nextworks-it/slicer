package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.NameEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * VnfdSupportedvnfinterfaces
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdSupportedvnfinterfaces {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("cpd-id")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> cpdId = null;

  @JsonProperty("name")
  private NameEnum name = null;

  @JsonProperty("interface-details")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<VnfdVdustoragerequirements> interfaceDetails = null;

  public VnfdSupportedvnfinterfaces cpdId(List<String> cpdId) {
    this.cpdId = cpdId;
    return this;
  }

  public VnfdSupportedvnfinterfaces addCpdIdItem(String cpdIdItem) {
    if (this.cpdId == null) {
      this.cpdId = new ArrayList<String>();
    }
    this.cpdId.add(cpdIdItem);
    return this;
  }

  /**
   * Get cpdId
   * @return cpdId
   **/
  //@Schema(description = "")
  
    public List<String> getCpdId() {
    return cpdId;
  }

  public void setCpdId(List<String> cpdId) {
    this.cpdId = cpdId;
  }

  public VnfdSupportedvnfinterfaces name(NameEnum name) {
    this.name = name;
    return this;
  }

  /**
   * Identifies an interface produced by the VNF. Valid values: - VNF_CONFIGURATION - VNF_INDICATOR
   * @return name
   **/
  //@Schema(description = "Identifies an interface produced by the VNF. Valid values: - VNF_CONFIGURATION - VNF_INDICATOR")
  
    public NameEnum getName() {
    return name;
  }

  public void setName(NameEnum name) {
    this.name = name;
  }

  public VnfdSupportedvnfinterfaces interfaceDetails(List<VnfdVdustoragerequirements> interfaceDetails) {
    this.interfaceDetails = interfaceDetails;
    return this;
  }

  public VnfdSupportedvnfinterfaces addInterfaceDetailsItem(VnfdVdustoragerequirements interfaceDetailsItem) {
    if (this.interfaceDetails == null) {
      this.interfaceDetails = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.interfaceDetails.add(interfaceDetailsItem);
    return this;
  }

  /**
   * Get interfaceDetails
   * @return interfaceDetails
   **/
  //@Schema(description = "")

    public List<VnfdVdustoragerequirements> getInterfaceDetails() {
    return interfaceDetails;
  }

  public void setInterfaceDetails(List<VnfdVdustoragerequirements> interfaceDetails) {
    this.interfaceDetails = interfaceDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdSupportedvnfinterfaces vnfdSupportedvnfinterfaces = (VnfdSupportedvnfinterfaces) o;
    return Objects.equals(this.cpdId, vnfdSupportedvnfinterfaces.cpdId) &&
        Objects.equals(this.name, vnfdSupportedvnfinterfaces.name) &&
        Objects.equals(this.interfaceDetails, vnfdSupportedvnfinterfaces.interfaceDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdId, name, interfaceDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdSupportedvnfinterfaces {\n");
    
    sb.append("    cpdId: ").append(toIndentedString(cpdId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    interfaceDetails: ").append(toIndentedString(interfaceDetails)).append("\n");
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
