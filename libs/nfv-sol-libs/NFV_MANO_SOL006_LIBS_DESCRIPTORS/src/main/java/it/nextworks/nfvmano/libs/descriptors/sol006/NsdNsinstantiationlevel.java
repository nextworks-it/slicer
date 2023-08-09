package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * NsdNsinstantiationlevel
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdNsinstantiationlevel {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("virtual-link-to-level-mapping")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_ns_instantiation_level_fk", referencedColumnName = "uuid")
  private List<VirtualLinkToLevelMappingItem> virtualLinkToLevelMapping = null;

  @JsonProperty("vnf-to-level-mapping")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<NsdVnftolevelmapping> vnfToLevelMapping = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("ns-to-level-mapping")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<NsdNstolevelmapping> nsToLevelMapping = null;

  @JsonProperty("description")
  private String description = null;

  public NsdNsinstantiationlevel virtualLinkToLevelMapping(List<VirtualLinkToLevelMappingItem> virtualLinkToLevelMapping) {
    this.virtualLinkToLevelMapping = virtualLinkToLevelMapping;
    return this;
  }

  public NsdNsinstantiationlevel addVirtualLinkToLevelMappingItem(VirtualLinkToLevelMappingItem virtualLinkToLevelMappingItem) {
    if (this.virtualLinkToLevelMapping == null) {
      this.virtualLinkToLevelMapping = new ArrayList<VirtualLinkToLevelMappingItem>();
    }
    this.virtualLinkToLevelMapping.add(virtualLinkToLevelMappingItem);
    return this;
  }

  /**
   * Specifies the profile of the VLs involved in this NS level and, for each of them, the needed bandwidth.
   * @return virtualLinkToLevelMapping
   **/
  //@Schema(description = "Specifies the profile of the VLs involved in this NS level and, for each of them, the needed bandwidth.")
  
    public List<VirtualLinkToLevelMappingItem> getVirtualLinkToLevelMapping() {
    return virtualLinkToLevelMapping;
  }

  public void setVirtualLinkToLevelMapping(List<VirtualLinkToLevelMappingItem> virtualLinkToLevelMapping) {
    this.virtualLinkToLevelMapping = virtualLinkToLevelMapping;
  }

  public NsdNsinstantiationlevel vnfToLevelMapping(List<NsdVnftolevelmapping> vnfToLevelMapping) {
    this.vnfToLevelMapping = vnfToLevelMapping;
    return this;
  }

  public NsdNsinstantiationlevel addVnfToLevelMappingItem(NsdVnftolevelmapping vnfToLevelMappingItem) {
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

  public NsdNsinstantiationlevel id(String id) {
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

  public NsdNsinstantiationlevel nsToLevelMapping(List<NsdNstolevelmapping> nsToLevelMapping) {
    this.nsToLevelMapping = nsToLevelMapping;
    return this;
  }

  public NsdNsinstantiationlevel addNsToLevelMappingItem(NsdNstolevelmapping nsToLevelMappingItem) {
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

  public NsdNsinstantiationlevel description(String description) {
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
    NsdNsinstantiationlevel nsdNsinstantiationlevel = (NsdNsinstantiationlevel) o;
    return Objects.equals(this.virtualLinkToLevelMapping, nsdNsinstantiationlevel.virtualLinkToLevelMapping) &&
        Objects.equals(this.vnfToLevelMapping, nsdNsinstantiationlevel.vnfToLevelMapping) &&
        Objects.equals(this.id, nsdNsinstantiationlevel.id) &&
        Objects.equals(this.nsToLevelMapping, nsdNsinstantiationlevel.nsToLevelMapping) &&
        Objects.equals(this.description, nsdNsinstantiationlevel.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLinkToLevelMapping, vnfToLevelMapping, id, nsToLevelMapping, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdNsinstantiationlevel {\n");
    
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
