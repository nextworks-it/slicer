package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * DfSchemaNsinstantiationlevel
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class DfSchemaNsinstantiationlevel   {
  @JsonProperty("virtual-link-to-level-mapping")

  private List<LinkBitrateRequirements> virtualLinkToLevelMapping = null;

  @JsonProperty("vnf-to-level-mapping")

  private List<NsdVnftolevelmapping> vnfToLevelMapping = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("ns-to-level-mapping")

  private List<NsdNstolevelmapping> nsToLevelMapping = null;

  @JsonProperty("description")
  private String description = null;

  public DfSchemaNsinstantiationlevel virtualLinkToLevelMapping(List<LinkBitrateRequirements> virtualLinkToLevelMapping) {
    this.virtualLinkToLevelMapping = virtualLinkToLevelMapping;
    return this;
  }

  public DfSchemaNsinstantiationlevel addVirtualLinkToLevelMappingItem(LinkBitrateRequirements virtualLinkToLevelMappingItem) {
    if (this.virtualLinkToLevelMapping == null) {
      this.virtualLinkToLevelMapping = new ArrayList<LinkBitrateRequirements>();
    }
    this.virtualLinkToLevelMapping.add(virtualLinkToLevelMappingItem);
    return this;
  }

  /**
   * Specifies the profile of the VLs involved in this NS level and, for each of them, the needed bandwidth.
   * @return virtualLinkToLevelMapping
   **/
  //@Schema(description = "Specifies the profile of the VLs involved in this NS level and, for each of them, the needed bandwidth.")

    public List<LinkBitrateRequirements> getVirtualLinkToLevelMapping() {
    return virtualLinkToLevelMapping;
  }

  public void setVirtualLinkToLevelMapping(List<LinkBitrateRequirements> virtualLinkToLevelMapping) {
    this.virtualLinkToLevelMapping = virtualLinkToLevelMapping;
  }

  public DfSchemaNsinstantiationlevel vnfToLevelMapping(List<NsdVnftolevelmapping> vnfToLevelMapping) {
    this.vnfToLevelMapping = vnfToLevelMapping;
    return this;
  }

  public DfSchemaNsinstantiationlevel addVnfToLevelMappingItem(NsdVnftolevelmapping vnfToLevelMappingItem) {
    if (this.vnfToLevelMapping == null) {
      this.vnfToLevelMapping = new ArrayList<NsdVnftolevelmapping>();
    }
    this.vnfToLevelMapping.add(vnfToLevelMappingItem);
    return this;
  }

  /**
   * Specifies the profile of the VNFs involved in this NS level and, for each of them, the required number of instances.
   * @return vnfToLevelMapping
   **/
  //@Schema(description = "Specifies the profile of the VNFs involved in this NS level and, for each of them, the required number of instances.")

    public List<NsdVnftolevelmapping> getVnfToLevelMapping() {
    return vnfToLevelMapping;
  }

  public void setVnfToLevelMapping(List<NsdVnftolevelmapping> vnfToLevelMapping) {
    this.vnfToLevelMapping = vnfToLevelMapping;
  }

  public DfSchemaNsinstantiationlevel id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this NsLevel information element. It uniquely identifies an NS level within the DF.
   * @return id
   **/
  //@Schema(description = "Identifier of this NsLevel information element. It uniquely identifies an NS level within the DF.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DfSchemaNsinstantiationlevel nsToLevelMapping(List<NsdNstolevelmapping> nsToLevelMapping) {
    this.nsToLevelMapping = nsToLevelMapping;
    return this;
  }

  public DfSchemaNsinstantiationlevel addNsToLevelMappingItem(NsdNstolevelmapping nsToLevelMappingItem) {
    if (this.nsToLevelMapping == null) {
      this.nsToLevelMapping = new ArrayList<NsdNstolevelmapping>();
    }
    this.nsToLevelMapping.add(nsToLevelMappingItem);
    return this;
  }

  /**
   * Specifies the profile of the nested NSs involved in this NS level and, for each of them, the required number of instances.
   * @return nsToLevelMapping
   **/
  //@Schema(description = "Specifies the profile of the nested NSs involved in this NS level and, for each of them, the required number of instances.")

    public List<NsdNstolevelmapping> getNsToLevelMapping() {
    return nsToLevelMapping;
  }

  public void setNsToLevelMapping(List<NsdNstolevelmapping> nsToLevelMapping) {
    this.nsToLevelMapping = nsToLevelMapping;
  }

  public DfSchemaNsinstantiationlevel description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human readable description of the NS level.
   * @return description
   **/
  //@Schema(description = "Human readable description of the NS level.")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DfSchemaNsinstantiationlevel dfSchemaNsinstantiationlevel = (DfSchemaNsinstantiationlevel) o;
    return Objects.equals(this.virtualLinkToLevelMapping, dfSchemaNsinstantiationlevel.virtualLinkToLevelMapping) &&
        Objects.equals(this.vnfToLevelMapping, dfSchemaNsinstantiationlevel.vnfToLevelMapping) &&
        Objects.equals(this.id, dfSchemaNsinstantiationlevel.id) &&
        Objects.equals(this.nsToLevelMapping, dfSchemaNsinstantiationlevel.nsToLevelMapping) &&
        Objects.equals(this.description, dfSchemaNsinstantiationlevel.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLinkToLevelMapping, vnfToLevelMapping, id, nsToLevelMapping, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DfSchemaNsinstantiationlevel {\n");
    
    sb.append("    virtualLinkToLevelMapping: ").append(toIndentedString(virtualLinkToLevelMapping)).append("\n");
    sb.append("    vnfToLevelMapping: ").append(toIndentedString(vnfToLevelMapping)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nsToLevelMapping: ").append(toIndentedString(nsToLevelMapping)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
