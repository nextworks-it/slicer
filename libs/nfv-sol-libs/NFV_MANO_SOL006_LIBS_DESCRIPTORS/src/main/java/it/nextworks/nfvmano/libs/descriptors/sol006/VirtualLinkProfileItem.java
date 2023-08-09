package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * AllOfnsdDfVirtualLinkProfileItems
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VirtualLinkProfileItem extends LocalAffinityOrAntiAffinityRule  {

  @JsonProperty("flavour-id")
  private String flavourId = null;

  @JsonProperty("min-bitrate-requirements")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "min_bitrate_requirements_fk", referencedColumnName = "uuid")
  private LinkBitrateRequirements minBitrateRequirements = null;

  @JsonProperty("affinity-or-anti-affinity-group")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "virtual_link_profile_item_fk", referencedColumnName = "uuid")
  private List<AffinityOrAntiAffinityGroupIdSchema> affinityOrAntiAffinityGroup = null;

  @JsonProperty("virtual-link-desc-id")
  private String virtualLinkDescId = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("max-bitrate-requirements")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "max_bitrate_requirements_fk", referencedColumnName = "uuid")
  private LinkBitrateRequirements maxBitrateRequirements = null;

  @JsonProperty("virtual-link-protocol-data")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "virtual_link_protocol_data_fk", referencedColumnName = "uuid")
  private VirtualLinkProtocolDataSchema virtualLinkProtocolData = null;

  public VirtualLinkProfileItem flavourId(String flavourId) {
    this.flavourId = flavourId;
    return this;
  }

  /**
   * Identifies a flavour within the VLD.
   * @return flavourId
   **/
  //@Schema(description = "Identifies a flavour within the VLD.")
  
    public String getFlavourId() {
    return flavourId;
  }

  public void setFlavourId(String flavourId) {
    this.flavourId = flavourId;
  }

  public VirtualLinkProfileItem minBitrateRequirements(LinkBitrateRequirements minBitrateRequirements) {
    this.minBitrateRequirements = minBitrateRequirements;
    return this;
  }

  /**
   * Get minBitrateRequirements
   * @return minBitrateRequirements
   **/
  //@Schema(description = "")
  

    public LinkBitrateRequirements getMinBitrateRequirements() {
    return minBitrateRequirements;
  }

  public void setMinBitrateRequirements(LinkBitrateRequirements minBitrateRequirements) {
    this.minBitrateRequirements = minBitrateRequirements;
  }

  public VirtualLinkProfileItem
  affinityOrAntiAffinityGroup(List<AffinityOrAntiAffinityGroupIdSchema> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
    return this;
  }

  public VirtualLinkProfileItem
  addAffinityOrAntiAffinityGroupItem(AffinityOrAntiAffinityGroupIdSchema affinityOrAntiAffinityGroupItem) {
    if (this.affinityOrAntiAffinityGroup == null) {
      this.affinityOrAntiAffinityGroup = new ArrayList<AffinityOrAntiAffinityGroupIdSchema>();
    }
    this.affinityOrAntiAffinityGroup.add(affinityOrAntiAffinityGroupItem);
    return this;
  }

  /**
   * Identifies an affinity or anti-affinity group the VLs instantiated according to the VlProfile belong to.
   * @return affinityOrAntiAffinityGroup
   **/
  //@Schema(description = "Identifies an affinity or anti-affinity group the VLs instantiated according to the VlProfile belong to.")
  
    public List<AffinityOrAntiAffinityGroupIdSchema> getAffinityOrAntiAffinityGroup() {
    return affinityOrAntiAffinityGroup;
  }

  public void setAffinityOrAntiAffinityGroup(List<AffinityOrAntiAffinityGroupIdSchema> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
  }

  public VirtualLinkProfileItem virtualLinkDescId(String virtualLinkDescId) {
    this.virtualLinkDescId = virtualLinkDescId;
    return this;
  }

  /**
   * Uniquely references a VLD.
   * @return virtualLinkDescId
   **/
  //@Schema(description = "Uniquely references a VLD.")
  
    public String getVirtualLinkDescId() {
    return virtualLinkDescId;
  }

  public void setVirtualLinkDescId(String virtualLinkDescId) {
    this.virtualLinkDescId = virtualLinkDescId;
  }

  public VirtualLinkProfileItem id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Uniquely identifies this VirtualLinkProfile information element.
   * @return id
   **/
  //@Schema(description = "Uniquely identifies this VirtualLinkProfile information element.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VirtualLinkProfileItem maxBitrateRequirements(LinkBitrateRequirements maxBitrateRequirements) {
    this.maxBitrateRequirements = maxBitrateRequirements;
    return this;
  }

  /**
   * Get maxBitrateRequirements
   * @return maxBitrateRequirements
   **/
  //@Schema(description = "")


    public LinkBitrateRequirements getMaxBitrateRequirements() {
    return maxBitrateRequirements;
  }

  public void setMaxBitrateRequirements(LinkBitrateRequirements maxBitrateRequirements) {
    this.maxBitrateRequirements = maxBitrateRequirements;
  }

  public VirtualLinkProfileItem virtualLinkProtocolData(VirtualLinkProtocolDataSchema virtualLinkProtocolData) {
      this.virtualLinkProtocolData = virtualLinkProtocolData;
      return this;
  }

  public VirtualLinkProtocolDataSchema getVirtualLinkProtocolData() { return virtualLinkProtocolData; }

  public void setVirtualLinkProtocolData(VirtualLinkProtocolDataSchema virtualLinkProtocolData) {
    this.virtualLinkProtocolData = virtualLinkProtocolData;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualLinkProfileItem allOfnsdDfVirtualLinkProfileItems = (VirtualLinkProfileItem) o;
    return Objects.equals(this.flavourId, allOfnsdDfVirtualLinkProfileItems.flavourId) &&
        Objects.equals(this.minBitrateRequirements, allOfnsdDfVirtualLinkProfileItems.minBitrateRequirements) &&
        Objects.equals(this.affinityOrAntiAffinityGroup, allOfnsdDfVirtualLinkProfileItems.affinityOrAntiAffinityGroup) &&
        Objects.equals(this.virtualLinkDescId, allOfnsdDfVirtualLinkProfileItems.virtualLinkDescId) &&
        Objects.equals(this.id, allOfnsdDfVirtualLinkProfileItems.id) &&
        Objects.equals(this.maxBitrateRequirements, allOfnsdDfVirtualLinkProfileItems.maxBitrateRequirements) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flavourId, minBitrateRequirements, affinityOrAntiAffinityGroup, virtualLinkDescId, id, maxBitrateRequirements, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllOfnsdDfVirtualLinkProfileItems {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n");
    sb.append("    minBitrateRequirements: ").append(toIndentedString(minBitrateRequirements)).append("\n");
    sb.append("    affinityOrAntiAffinityGroup: ").append(toIndentedString(affinityOrAntiAffinityGroup)).append("\n");
    sb.append("    virtualLinkDescId: ").append(toIndentedString(virtualLinkDescId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    maxBitrateRequirements: ").append(toIndentedString(maxBitrateRequirements)).append("\n");
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
