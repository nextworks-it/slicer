package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * SupportedVnfInterfacesSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class SupportedVnfInterfacesSchema   {
  @JsonProperty("cpd-id")

  private List<String> cpdId = null;

  /**
   * Identifies an interface produced by the VNF. Valid values: - VNF_CONFIGURATION - VNF_INDICATOR
   */
  public enum NameEnum {
    CONFIGURATION("vnf-configuration"),
    
    INDICATOR("vnf-indicator");

    private String value;

    NameEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static NameEnum fromValue(String text) {
      for (NameEnum b : NameEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("name")
  private NameEnum name = null;

  @JsonProperty("interface-details")

  private List<VnfdVdustoragerequirements> interfaceDetails = null;

  public SupportedVnfInterfacesSchema cpdId(List<String> cpdId) {
    this.cpdId = cpdId;
    return this;
  }

  public SupportedVnfInterfacesSchema addCpdIdItem(String cpdIdItem) {
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

  public SupportedVnfInterfacesSchema name(NameEnum name) {
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

  public SupportedVnfInterfacesSchema interfaceDetails(List<VnfdVdustoragerequirements> interfaceDetails) {
    this.interfaceDetails = interfaceDetails;
    return this;
  }

  public SupportedVnfInterfacesSchema addInterfaceDetailsItem(VnfdVdustoragerequirements interfaceDetailsItem) {
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
    SupportedVnfInterfacesSchema supportedVnfInterfacesSchema = (SupportedVnfInterfacesSchema) o;
    return Objects.equals(this.cpdId, supportedVnfInterfacesSchema.cpdId) &&
        Objects.equals(this.name, supportedVnfInterfacesSchema.name) &&
        Objects.equals(this.interfaceDetails, supportedVnfInterfacesSchema.interfaceDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdId, name, interfaceDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SupportedVnfInterfacesSchema {\n");
    
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
