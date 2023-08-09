package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * AllOfnsdDfVnfProfileItems
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfProfileItem extends LocalAffinityOrAntiAffinityRule {

  @JsonProperty("flavour-id")
  private String flavourId = null;

  @JsonProperty("affinity-or-anti-affinity-group")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnf_profile_item_fk", referencedColumnName = "uuid")
  private List<AffinityOrAntiAffinityGroupIdSchema> affinityOrAntiAffinityGroup = null;

  @JsonProperty("min-number-of-instances")
  private String minNumberOfInstances = null;

  @JsonProperty("instantiation-level")
  private String instantiationLevel = null;

  @JsonProperty("max-number-of-instances")
  private String maxNumberOfInstances = null;

  @JsonProperty("vnfd-id")
  private String vnfdId = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("virtual-link-connectivity")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnf_profile_item_fk", referencedColumnName = "uuid")
  private List<VirtualLinkConnectivitySchema> virtualLinkConnectivity = null;

  public VnfProfileItem flavourId(String flavourId) {
    this.flavourId = flavourId;
    return this;
  }

  /**
   * Identifies a flavour within the VNFD.
   * @return flavourId
   **/
  //@Schema(description = "Identifies a flavour within the VNFD.")
  
    public String getFlavourId() {
    return flavourId;
  }

  public void setFlavourId(String flavourId) {
    this.flavourId = flavourId;
  }

  public
  VnfProfileItem affinityOrAntiAffinityGroup(List<AffinityOrAntiAffinityGroupIdSchema> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
    return this;
  }

  public
  VnfProfileItem addAffinityOrAntiAffinityGroupItem(AffinityOrAntiAffinityGroupIdSchema affinityOrAntiAffinityGroupItem) {
    if (this.affinityOrAntiAffinityGroup == null) {
      this.affinityOrAntiAffinityGroup = new ArrayList<AffinityOrAntiAffinityGroupIdSchema>();
    }
    this.affinityOrAntiAffinityGroup.add(affinityOrAntiAffinityGroupItem);
    return this;
  }

  /**
   * Identifier(s) of the affinity or anti-affinity group(s) the VnfProfile belongs to.
   * @return affinityOrAntiAffinityGroup
   **/
  //@Schema(description = "Identifier(s) of the affinity or anti-affinity group(s) the VnfProfile belongs to.")
  
    public List<AffinityOrAntiAffinityGroupIdSchema> getAffinityOrAntiAffinityGroup() {
    return affinityOrAntiAffinityGroup;
  }

  public void setAffinityOrAntiAffinityGroup(List<AffinityOrAntiAffinityGroupIdSchema> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
  }

  public VnfProfileItem minNumberOfInstances(String minNumberOfInstances) {
    this.minNumberOfInstances = minNumberOfInstances;
    return this;
  }

  /**
   * Minimum number of instances of the VNF based on this VNFD that is permitted to exist for this VnfProfile.
   * @return minNumberOfInstances
   **/
  //@Schema(description = "Minimum number of instances of the VNF based on this VNFD that is permitted to exist for this VnfProfile.")
  
    public String getMinNumberOfInstances() {
    return minNumberOfInstances;
  }

  public void setMinNumberOfInstances(String minNumberOfInstances) {
    this.minNumberOfInstances = minNumberOfInstances;
  }

  public VnfProfileItem instantiationLevel(String instantiationLevel) {
    this.instantiationLevel = instantiationLevel;
    return this;
  }

  /**
   * Identifier of the instantiation level of the VNF DF to be used for instantiation. If not present, the default instantiation level as declared in the VNFD shall be used.
   * @return instantiationLevel
   **/
  //@Schema(description = "Identifier of the instantiation level of the VNF DF to be used for instantiation. If not present, the default instantiation level as declared in the VNFD shall be used.")
  
    public String getInstantiationLevel() {
    return instantiationLevel;
  }

  public void setInstantiationLevel(String instantiationLevel) {
    this.instantiationLevel = instantiationLevel;
  }

  public VnfProfileItem maxNumberOfInstances(String maxNumberOfInstances) {
    this.maxNumberOfInstances = maxNumberOfInstances;
    return this;
  }

  /**
   * Maximum number of instances of the VNF based on this VNFD that is permitted to exist for this VnfProfile.
   * @return maxNumberOfInstances
   **/
  //@Schema(description = "Maximum number of instances of the VNF based on this VNFD that is permitted to exist for this VnfProfile.")
  
    public String getMaxNumberOfInstances() {
    return maxNumberOfInstances;
  }

  public void setMaxNumberOfInstances(String maxNumberOfInstances) {
    this.maxNumberOfInstances = maxNumberOfInstances;
  }

  public VnfProfileItem vnfdId(String vnfdId) {
    this.vnfdId = vnfdId;
    return this;
  }

  /**
   * References a VNFD.
   * @return vnfdId
   **/
  //@Schema(description = "References a VNFD.")
  
    public String getVnfdId() {
    return vnfdId;
  }

  public void setVnfdId(String vnfdId) {
    this.vnfdId = vnfdId;
  }

  public VnfProfileItem id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this vnfProfile information element. It uniquely identifies a VnfProfile.
   * @return id
   **/
  //@Schema(description = "Identifier of this vnfProfile information element. It uniquely identifies a VnfProfile.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfProfileItem virtualLinkConnectivity(List<VirtualLinkConnectivitySchema> virtualLinkConnectivity) {
    this.virtualLinkConnectivity = virtualLinkConnectivity;
    return this;
  }

  public VnfProfileItem addVirtualLinkConnectivityItem(VirtualLinkConnectivitySchema virtualLinkConnectivityItem) {
    if (this.virtualLinkConnectivity == null) {
      this.virtualLinkConnectivity = new ArrayList<VirtualLinkConnectivitySchema>();
    }
    this.virtualLinkConnectivity.add(virtualLinkConnectivityItem);
    return this;
  }

  /**
   * Defines the connection information of the VNF, it contains connection relationship between a VNF connection point and a NS Virtual Link.
   * @return virtualLinkConnectivity
   **/
  //@Schema(description = "Defines the connection information of the VNF, it contains connection relationship between a VNF connection point and a NS Virtual Link.")
  
    public List<VirtualLinkConnectivitySchema> getVirtualLinkConnectivity() {
    return virtualLinkConnectivity;
  }

  public void setVirtualLinkConnectivity(List<VirtualLinkConnectivitySchema> virtualLinkConnectivity) {
    this.virtualLinkConnectivity = virtualLinkConnectivity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfProfileItem allOfnsdDfVnfProfileItems = (VnfProfileItem) o;
    return Objects.equals(this.flavourId, allOfnsdDfVnfProfileItems.flavourId) &&
        Objects.equals(this.affinityOrAntiAffinityGroup, allOfnsdDfVnfProfileItems.affinityOrAntiAffinityGroup) &&
        Objects.equals(this.minNumberOfInstances, allOfnsdDfVnfProfileItems.minNumberOfInstances) &&
        Objects.equals(this.instantiationLevel, allOfnsdDfVnfProfileItems.instantiationLevel) &&
        Objects.equals(this.maxNumberOfInstances, allOfnsdDfVnfProfileItems.maxNumberOfInstances) &&
        Objects.equals(this.vnfdId, allOfnsdDfVnfProfileItems.vnfdId) &&
        Objects.equals(this.id, allOfnsdDfVnfProfileItems.id) &&
        Objects.equals(this.virtualLinkConnectivity, allOfnsdDfVnfProfileItems.virtualLinkConnectivity) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flavourId, affinityOrAntiAffinityGroup, minNumberOfInstances, instantiationLevel, maxNumberOfInstances, vnfdId, id, virtualLinkConnectivity, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllOfnsdDfVnfProfileItems {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n");
    sb.append("    affinityOrAntiAffinityGroup: ").append(toIndentedString(affinityOrAntiAffinityGroup)).append("\n");
    sb.append("    minNumberOfInstances: ").append(toIndentedString(minNumberOfInstances)).append("\n");
    sb.append("    instantiationLevel: ").append(toIndentedString(instantiationLevel)).append("\n");
    sb.append("    maxNumberOfInstances: ").append(toIndentedString(maxNumberOfInstances)).append("\n");
    sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    virtualLinkConnectivity: ").append(toIndentedString(virtualLinkConnectivity)).append("\n");
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
