package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.IpAddressTypeEnum;

import javax.persistence.Embeddable;

/**
 * CpdL3addressdata
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class CpdL3addressdata {

  @JsonProperty("ip-address-assignment")
  private Boolean ipAddressAssignment = null;

  @JsonProperty("floating-ip-activated")
  private Boolean floatingIpActivated = null;

  @JsonProperty("number-of-ip-addresses")
  private String numberOfIpAddresses = null;

  @JsonProperty("ip-address-type")
  private IpAddressTypeEnum ipAddressType = null;

  public CpdL3addressdata ipAddressAssignment(Boolean ipAddressAssignment) {
    this.ipAddressAssignment = ipAddressAssignment;
    return this;
  }

  /**
   * Specify if the address assignment is the responsibility of management and orchestration function or not. If it is set to True, it is the management and orchestration function responsibility. 
   * @return ipAddressAssignment
   **/
  //@Schema(description = "Specify if the address assignment is the responsibility of management and orchestration function or not. If it is set to True, it is the management and orchestration function responsibility. ")
  
    public Boolean isIpAddressAssignment() {
    return ipAddressAssignment;
  }

  public void setIpAddressAssignment(Boolean ipAddressAssignment) {
    this.ipAddressAssignment = ipAddressAssignment;
  }

  public CpdL3addressdata floatingIpActivated(Boolean floatingIpActivated) {
    this.floatingIpActivated = floatingIpActivated;
    return this;
  }

  /**
   * Specify if the floating IP scheme is activated on the CP or not.
   * @return floatingIpActivated
   **/
  //@Schema(description = "Specify if the floating IP scheme is activated on the CP or not.")
  
    public Boolean isFloatingIpActivated() {
    return floatingIpActivated;
  }

  public void setFloatingIpActivated(Boolean floatingIpActivated) {
    this.floatingIpActivated = floatingIpActivated;
  }

  public CpdL3addressdata numberOfIpAddresses(String numberOfIpAddresses) {
    this.numberOfIpAddresses = numberOfIpAddresses;
    return this;
  }

  /**
   * Minimum number of IP addresses to be assigned based on this L3AddressData information element.
   * @return numberOfIpAddresses
   **/
  //@Schema(description = "Minimum number of IP addresses to be assigned based on this L3AddressData information element.")
  
    public String getNumberOfIpAddresses() {
    return numberOfIpAddresses;
  }

  public void setNumberOfIpAddresses(String numberOfIpAddresses) {
    this.numberOfIpAddresses = numberOfIpAddresses;
  }

  public CpdL3addressdata ipAddressType(IpAddressTypeEnum ipAddressType) {
    this.ipAddressType = ipAddressType;
    return this;
  }

  /**
   * Define address type. The address type should be aligned with the address type supported by the layerProtocol attribute of the parent VnfExtCpd.
   * @return ipAddressType
   **/
  //@Schema(description = "Define address type. The address type should be aligned with the address type supported by the layerProtocol attribute of the parent VnfExtCpd.")
  
    public IpAddressTypeEnum getIpAddressType() {
    return ipAddressType;
  }

  public void setIpAddressType(IpAddressTypeEnum ipAddressType) {
    this.ipAddressType = ipAddressType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CpdL3addressdata cpdL3addressdata = (CpdL3addressdata) o;
    return Objects.equals(this.ipAddressAssignment, cpdL3addressdata.ipAddressAssignment) &&
        Objects.equals(this.floatingIpActivated, cpdL3addressdata.floatingIpActivated) &&
        Objects.equals(this.numberOfIpAddresses, cpdL3addressdata.numberOfIpAddresses) &&
        Objects.equals(this.ipAddressType, cpdL3addressdata.ipAddressType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddressAssignment, floatingIpActivated, numberOfIpAddresses, ipAddressType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CpdL3addressdata {\n");
    
    sb.append("    ipAddressAssignment: ").append(toIndentedString(ipAddressAssignment)).append("\n");
    sb.append("    floatingIpActivated: ").append(toIndentedString(floatingIpActivated)).append("\n");
    sb.append("    numberOfIpAddresses: ").append(toIndentedString(numberOfIpAddresses)).append("\n");
    sb.append("    ipAddressType: ").append(toIndentedString(ipAddressType)).append("\n");
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
