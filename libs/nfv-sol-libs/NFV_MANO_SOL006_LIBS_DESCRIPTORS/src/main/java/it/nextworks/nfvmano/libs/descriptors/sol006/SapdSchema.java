package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.model.NsdCpdorvirtuallink;

import org.springframework.validation.annotation.Validated;

/**
 * SapdSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class SapdSchema   {
  @JsonProperty("address-assignment")
  private Boolean addressAssignment = null;

  @JsonProperty("sapd_id")
  private String sapdId = null;


  @JsonProperty("virtual-link-desc")
  private String virtualLinkDesc = null;

  public String getVirtualLinkDesc() {
    return virtualLinkDesc;
  }

  public void setVirtualLinkDesc(String virtualLinkDesc) {
    this.virtualLinkDesc = virtualLinkDesc;
  }

  @JsonProperty("vnf")
  private VnfAssociatedCpdId vnf = null;

  @JsonProperty("ns")
  private NsAssociatedCpdId ns = null;

  @JsonProperty("pnf")
  private PnfAssociatedCpdId pnf = null;

  public PnfAssociatedCpdId getPnf() {
    return pnf;
  }

  public SapdSchema addressAssignment(Boolean addressAssignment) {
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

  public SapdSchema sapdId(String sapdId) {
    this.sapdId = sapdId;
    return this;
  }

  /**
   * Identifier of this Cpd information element.
   * @return sapdId
   **/
  //@Schema(description = "Identifier of this Cpd information element.")
  
    public String getSapdId() {
    return sapdId;
  }

  public void setSapdId(String sapdId) {
    this.sapdId = sapdId;
  }



  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SapdSchema sapdSchema = (SapdSchema) o;
    return Objects.equals(this.addressAssignment, sapdSchema.addressAssignment) &&
        Objects.equals(this.sapdId, sapdSchema.sapdId);
   //     Objects.equals(this.cpdOrVirtualLink, sapdSchema.cpdOrVirtualLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressAssignment, sapdId);
    //return Objects.hash(addressAssignment, sapdId, cpdOrVirtualLink);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SapdSchema {\n");
    
    sb.append("    addressAssignment: ").append(toIndentedString(addressAssignment)).append("\n");
    sb.append("    sapdId: ").append(toIndentedString(sapdId)).append("\n");
    //sb.append("    cpdOrVirtualLink: ").append(toIndentedString(cpdOrVirtualLink)).append("\n");
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
