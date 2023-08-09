package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * AddressDataSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

public class AddressDataSchema {

  @JsonProperty("l2-address-data")
  @Embedded
  private CpdL2addressdata l2AddressData = null;

  @JsonProperty("l3-address-data")
  @Embedded
  private CpdL3addressdata l3AddressData = null;

  @JsonProperty("type")
  private String type = null;

  public AddressDataSchema l2AddressData(CpdL2addressdata l2AddressData) {
    this.l2AddressData = l2AddressData;
    return this;
  }

  /**
   * Get l2AddressData
   * @return l2AddressData
   **/
  //@Schema(description = "")
  

    public CpdL2addressdata getL2AddressData() {
    return l2AddressData;
  }

  public void setL2AddressData(CpdL2addressdata l2AddressData) {
    this.l2AddressData = l2AddressData;
  }

  public AddressDataSchema l3AddressData(CpdL3addressdata l3AddressData) {
    this.l3AddressData = l3AddressData;
    return this;
  }

  /**
   * Get l3AddressData
   * @return l3AddressData
   **/
  //@Schema(description = "")
  

    public CpdL3addressdata getL3AddressData() {
    return l3AddressData;
  }

  public void setL3AddressData(CpdL3addressdata l3AddressData) {
    this.l3AddressData = l3AddressData;
  }

  public AddressDataSchema type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Describes the type of the address to be assigned to the CP instantiated from the parent CPD. Value:   • MAC address.   • IP address.   • Etc. The content type shall be aligned with the address type supported by the layerProtocol attribute of the parent CPD.
   * @return type
   **/
  //@Schema(description = "Describes the type of the address to be assigned to the CP instantiated from the parent CPD. Value:   • MAC address.   • IP address.   • Etc. The content type shall be aligned with the address type supported by the layerProtocol attribute of the parent CPD.")
  
    public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressDataSchema addressDataSchema = (AddressDataSchema) o;
    return Objects.equals(this.l2AddressData, addressDataSchema.l2AddressData) &&
        Objects.equals(this.l3AddressData, addressDataSchema.l3AddressData) &&
        Objects.equals(this.type, addressDataSchema.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(l2AddressData, l3AddressData, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressDataSchema {\n");
    
    sb.append("    l2AddressData: ").append(toIndentedString(l2AddressData)).append("\n");
    sb.append("    l3AddressData: ").append(toIndentedString(l3AddressData)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
