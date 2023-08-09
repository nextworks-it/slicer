package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AllOfvnfdDfVirtualLinkProfileItems
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdDfVirtualLinkProfileItem extends LocalAffinityOrAntiAffinityRule {

  @JsonProperty("flavour")
  private String flavour = null;

  @JsonProperty("max-bit-rate-requirements")
  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "leaf", column = @Column(name = "max_bit_rate_requirements_leaf")),
          @AttributeOverride(name = "root", column = @Column(name = "max_bit_rate_requirements_root"))
  })
  private MaxBitRateRequirementsSchema maxBitRateRequirements = null;

  @JsonProperty("affinity-or-anti-affinity-group")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_virtual_link_profile_item_fk", referencedColumnName = "uuid")
  private List<AffinityOrAntiAffinityGroupIdSchema> affinityOrAntiAffinityGroup = null;

  @JsonProperty("virtual-link-protocol-data")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "virtual_link_protocol_data_schema_fk", referencedColumnName = "uuid")
  private VirtualLinkProtocolDataSchema virtualLinkProtocolData = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("min-bit-rate-requirements")
  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "leaf", column = @Column(name = "min_bit_rate_requirements_leaf")),
          @AttributeOverride(name = "root", column = @Column(name = "min_bit_rate_requirements_root"))
  })
  private MinBitRateRequirementsSchema minBitRateRequirements = null;

  public VnfdDfVirtualLinkProfileItem flavour(String flavour) {
    this.flavour = flavour;
    return this;
  }

  /**
   * Identifies a flavour within the VnfVirtualLinkDesc.
   * @return flavour
   **/
  //@Schema(description = "Identifies a flavour within the VnfVirtualLinkDesc.")
  
    public String getFlavour() {
    return flavour;
  }

  public void setFlavour(String flavour) {
    this.flavour = flavour;
  }

  public VnfdDfVirtualLinkProfileItem maxBitRateRequirements(MaxBitRateRequirementsSchema maxBitRateRequirements) {
    this.maxBitRateRequirements = maxBitRateRequirements;
    return this;
  }

  /**
   * Specifies the maximum bitrate requirements for a VL instantiated according to this profile.
   * @return maxBitRateRequirements
   **/
  //@Schema(description = "Specifies the maximum bitrate requirements for a VL instantiated according to this profile.")
  
    public Object getMaxBitRateRequirements() {
    return maxBitRateRequirements;
  }

  public void setMaxBitRateRequirements(MaxBitRateRequirementsSchema maxBitRateRequirements) {
    this.maxBitRateRequirements = maxBitRateRequirements;
  }

  public VnfdDfVirtualLinkProfileItem affinityOrAntiAffinityGroup(List<AffinityOrAntiAffinityGroupIdSchema>
                                                                          affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
    return this;
  }

  public VnfdDfVirtualLinkProfileItem addAffinityOrAntiAffinityGroupItem(AffinityOrAntiAffinityGroupIdSchema
                                                                                 affinityOrAntiAffinityGroupItem) {
    if (this.affinityOrAntiAffinityGroup == null) {
      this.affinityOrAntiAffinityGroup = new ArrayList<AffinityOrAntiAffinityGroupIdSchema>();
    }
    this.affinityOrAntiAffinityGroup.add(affinityOrAntiAffinityGroupItem);
    return this;
  }

  /**
   * Identifier(s) of the affinity or anti-affinity group(s) the VnfVirtualLinkDesc belongs to.
   * @return affinityOrAntiAffinityGroup
   **/
  //@Schema(description = "Identifier(s) of the affinity or anti-affinity group(s) the VnfVirtualLinkDesc belongs to.")
  
    public List<AffinityOrAntiAffinityGroupIdSchema> getAffinityOrAntiAffinityGroup() {
    return affinityOrAntiAffinityGroup;
  }

  public void setAffinityOrAntiAffinityGroup(List<AffinityOrAntiAffinityGroupIdSchema> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
  }

  public VnfdDfVirtualLinkProfileItem virtualLinkProtocolData(VirtualLinkProtocolDataSchema virtualLinkProtocolData) {
    this.virtualLinkProtocolData = virtualLinkProtocolData;
    return this;
  }

  /**
   * Specifies the protocol data for a VL instantiated according to this profile. Cardinality 0 is used when no protocol data needs to be specified.
   * @return virtualLinkProtocolData
   **/
  //@Schema(description = "Specifies the protocol data for a VL instantiated according to this profile. Cardinality 0 is used when no protocol data needs to be specified.")
  
    public Object getVirtualLinkProtocolData() {
    return virtualLinkProtocolData;
  }

  public void setVirtualLinkProtocolData(VirtualLinkProtocolDataSchema virtualLinkProtocolData) {
    this.virtualLinkProtocolData = virtualLinkProtocolData;
  }

  public VnfdDfVirtualLinkProfileItem id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Uniquely identifies a Vnf VLD.
   * @return id
   **/
  //@Schema(description = "Uniquely identifies a Vnf VLD.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfdDfVirtualLinkProfileItem minBitRateRequirements(MinBitRateRequirementsSchema minBitRateRequirements) {
    this.minBitRateRequirements = minBitRateRequirements;
    return this;
  }

  /**
   * Specifies the minimum bitrate requirements for a VL instantiated according to this profile.
   * @return minBitRateRequirements
   **/
  //@Schema(description = "Specifies the minimum bitrate requirements for a VL instantiated according to this profile.")
  
    public Object getMinBitRateRequirements() {
    return minBitRateRequirements;
  }

  public void setMinBitRateRequirements(MinBitRateRequirementsSchema minBitRateRequirements) {
    this.minBitRateRequirements = minBitRateRequirements;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdDfVirtualLinkProfileItem allOfvnfdDfVirtualLinkProfileItems = (VnfdDfVirtualLinkProfileItem) o;
    return Objects.equals(this.flavour, allOfvnfdDfVirtualLinkProfileItems.flavour) &&
        Objects.equals(this.maxBitRateRequirements, allOfvnfdDfVirtualLinkProfileItems.maxBitRateRequirements) &&
        Objects.equals(this.affinityOrAntiAffinityGroup, allOfvnfdDfVirtualLinkProfileItems.affinityOrAntiAffinityGroup) &&
        Objects.equals(this.virtualLinkProtocolData, allOfvnfdDfVirtualLinkProfileItems.virtualLinkProtocolData) &&
        Objects.equals(this.id, allOfvnfdDfVirtualLinkProfileItems.id) &&
        Objects.equals(this.minBitRateRequirements, allOfvnfdDfVirtualLinkProfileItems.minBitRateRequirements) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flavour, maxBitRateRequirements, affinityOrAntiAffinityGroup, virtualLinkProtocolData, id, minBitRateRequirements, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllOfvnfdDfVirtualLinkProfileItems {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    flavour: ").append(toIndentedString(flavour)).append("\n");
    sb.append("    maxBitRateRequirements: ").append(toIndentedString(maxBitRateRequirements)).append("\n");
    sb.append("    affinityOrAntiAffinityGroup: ").append(toIndentedString(affinityOrAntiAffinityGroup)).append("\n");
    sb.append("    virtualLinkProtocolData: ").append(toIndentedString(virtualLinkProtocolData)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    minBitRateRequirements: ").append(toIndentedString(minBitRateRequirements)).append("\n");
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
