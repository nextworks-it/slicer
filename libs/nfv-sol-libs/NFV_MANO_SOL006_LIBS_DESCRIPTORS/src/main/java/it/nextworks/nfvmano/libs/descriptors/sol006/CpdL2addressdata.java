package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * Provides the information on the MAC addresses to be assigned to the CP(s) instantiated from the parent CPD. Shall be present when the addressType is MAC address.
 */
//@Schema(description = "Provides the information on the MAC addresses to be assigned to the CP(s) instantiated from the parent CPD. Shall be present when the addressType is MAC address.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class CpdL2addressdata {

  @JsonProperty("mac-address-assignment")
  private Boolean macAddressAssignment = null;

  public CpdL2addressdata macAddressAssignment(Boolean macAddressAssignment) {
    this.macAddressAssignment = macAddressAssignment;
    return this;
  }

  /**
   * Specify if the MAC address assignment is the responsibility of management and orchestration function or not. If it is set to True, it is the management and orchestration function responsibility. If it is set to False, it will be provided by an external entity, e.g. OSS/BSS.
   * @return macAddressAssignment
   **/
  //@Schema(description = "Specify if the MAC address assignment is the responsibility of management and orchestration function or not. If it is set to True, it is the management and orchestration function responsibility. If it is set to False, it will be provided by an external entity, e.g. OSS/BSS.")
  
    public Boolean isMacAddressAssignment() {
    return macAddressAssignment;
  }

  public void setMacAddressAssignment(Boolean macAddressAssignment) {
    this.macAddressAssignment = macAddressAssignment;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CpdL2addressdata cpdL2addressdata = (CpdL2addressdata) o;
    return Objects.equals(this.macAddressAssignment, cpdL2addressdata.macAddressAssignment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(macAddressAssignment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CpdL2addressdata {\n");
    
    sb.append("    macAddressAssignment: ").append(toIndentedString(macAddressAssignment)).append("\n");
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
