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
 * NsdNsprofile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdNsprofile {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("instantiation-level-id")
  private String instantiationLevelId = null;

  @JsonProperty("ns-df-id")
  private String nsDfId = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("min-number-of-instances")
  private String minNumberOfInstances = null;

  @JsonProperty("max-number-of-instances")
  private String maxNumberOfInstances = null;

  @JsonProperty("nsd-id")
  private String nsdId = null;

  @JsonProperty("affinity-or-anti-affinity-group-id")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_ns_profile_fk", referencedColumnName = "uuid")
  private List<NsdAffinityOrAntiaffinityGroupId> affinityOrAntiAffinityGroupId = null;

  @JsonProperty("virtual-link-connectivity")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_ns_profile_fk", referencedColumnName = "uuid")
  private List<VirtualLinkConnectivitySchema> virtualLinkConnectivity = null;

  public NsdNsprofile instantiationLevelId(String instantiationLevelId) {
    this.instantiationLevelId = instantiationLevelId;
    return this;
  }

  /**
   * Identifies the NS level within the referenced NS DF to be used in the context of the parent NS instantiation. If not present, the default NS instantiation level as declared in the referenced NSD shall be used.
   * @return instantiationLevelId
   **/
  //@Schema(description = "Identifies the NS level within the referenced NS DF to be used in the context of the parent NS instantiation. If not present, the default NS instantiation level as declared in the referenced NSD shall be used.")
  
    public String getInstantiationLevelId() {
    return instantiationLevelId;
  }

  public void setInstantiationLevelId(String instantiationLevelId) {
    this.instantiationLevelId = instantiationLevelId;
  }

  public NsdNsprofile nsDfId(String nsDfId) {
    this.nsDfId = nsDfId;
    return this;
  }

  /**
   * Identifies the applicable network service DF within the scope of the NSD.
   * @return nsDfId
   **/
  //@Schema(description = "Identifies the applicable network service DF within the scope of the NSD.")
  
    public String getNsDfId() {
    return nsDfId;
  }

  public void setNsDfId(String nsDfId) {
    this.nsDfId = nsDfId;
  }

  public NsdNsprofile id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifies an NS profile.
   * @return id
   **/
  //@Schema(description = "Identifies an NS profile.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public NsdNsprofile minNumberOfInstances(String minNumberOfInstances) {
    this.minNumberOfInstances = minNumberOfInstances;
    return this;
  }

  /**
   * Minimum number of nested NS instances based on the referenced NSD that is permitted to exist for this NsProfile.
   * @return minNumberOfInstances
   **/
  //@Schema(description = "Minimum number of nested NS instances based on the referenced NSD that is permitted to exist for this NsProfile.")
  
    public String getMinNumberOfInstances() {
    return minNumberOfInstances;
  }

  public void setMinNumberOfInstances(String minNumberOfInstances) {
    this.minNumberOfInstances = minNumberOfInstances;
  }

  public NsdNsprofile maxNumberOfInstances(String maxNumberOfInstances) {
    this.maxNumberOfInstances = maxNumberOfInstances;
    return this;
  }

  /**
   * Maximum number of nested NS instances based on the referenced NSD that is permitted to exist for this NsProfile.
   * @return maxNumberOfInstances
   **/
  //@Schema(description = "Maximum number of nested NS instances based on the referenced NSD that is permitted to exist for this NsProfile.")
  
    public String getMaxNumberOfInstances() {
    return maxNumberOfInstances;
  }

  public void setMaxNumberOfInstances(String maxNumberOfInstances) {
    this.maxNumberOfInstances = maxNumberOfInstances;
  }

  public NsdNsprofile nsdId(String nsdId) {
    this.nsdId = nsdId;
    return this;
  }

  /**
   * Identifies the NSD applicable to NS instantiated according to this profile.
   * @return nsdId
   **/
  //@Schema(description = "Identifies the NSD applicable to NS instantiated according to this profile.")
  
    public String getNsdId() {
    return nsdId;
  }

  public void setNsdId(String nsdId) {
    this.nsdId = nsdId;
  }

  public NsdNsprofile affinityOrAntiAffinityGroupId(List<NsdAffinityOrAntiaffinityGroupId> affinityOrAntiAffinityGroupId) {
    this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
    return this;
  }

  public NsdNsprofile addAffinityOrAntiAffinityGroupIdItem(NsdAffinityOrAntiaffinityGroupId affinityOrAntiAffinityGroupIdItem) {
    if (this.affinityOrAntiAffinityGroupId == null) {
      this.affinityOrAntiAffinityGroupId = new ArrayList<NsdAffinityOrAntiaffinityGroupId>();
    }
    this.affinityOrAntiAffinityGroupId.add(affinityOrAntiAffinityGroupIdItem);
    return this;
  }

  /**
   * Identifies an affinity or anti-affinity group the NSs created according to this NsProfile belongs to.
   * @return affinityOrAntiAffinityGroupId
   **/
  //@Schema(description = "Identifies an affinity or anti-affinity group the NSs created according to this NsProfile belongs to.")

    public List<NsdAffinityOrAntiaffinityGroupId> getAffinityOrAntiAffinityGroupId() {
    return affinityOrAntiAffinityGroupId;
  }

  public void setAffinityOrAntiAffinityGroupId(List<NsdAffinityOrAntiaffinityGroupId> affinityOrAntiAffinityGroupId) {
    this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
  }

  public NsdNsprofile virtualLinkConnectivity(List<VirtualLinkConnectivitySchema> virtualLinkConnectivity) {
    this.virtualLinkConnectivity = virtualLinkConnectivity;
    return this;
  }

  public NsdNsprofile addVirtualLinkConnectivityItem(VirtualLinkConnectivitySchema virtualLinkConnectivityItem) {
    if (this.virtualLinkConnectivity == null) {
      this.virtualLinkConnectivity = new ArrayList<VirtualLinkConnectivitySchema>();
    }
    this.virtualLinkConnectivity.add(virtualLinkConnectivityItem);
    return this;
  }

  /**
   * Get virtualLinkConnectivity
   * @return virtualLinkConnectivity
   **/
  //@Schema(description = "")

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
    NsdNsprofile nsdNsprofile = (NsdNsprofile) o;
    return Objects.equals(this.instantiationLevelId, nsdNsprofile.instantiationLevelId) &&
        Objects.equals(this.nsDfId, nsdNsprofile.nsDfId) &&
        Objects.equals(this.id, nsdNsprofile.id) &&
        Objects.equals(this.minNumberOfInstances, nsdNsprofile.minNumberOfInstances) &&
        Objects.equals(this.maxNumberOfInstances, nsdNsprofile.maxNumberOfInstances) &&
        Objects.equals(this.nsdId, nsdNsprofile.nsdId) &&
        Objects.equals(this.affinityOrAntiAffinityGroupId, nsdNsprofile.affinityOrAntiAffinityGroupId) &&
        Objects.equals(this.virtualLinkConnectivity, nsdNsprofile.virtualLinkConnectivity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instantiationLevelId, nsDfId, id, minNumberOfInstances, maxNumberOfInstances, nsdId, affinityOrAntiAffinityGroupId, virtualLinkConnectivity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdNsprofile {\n");
    
    sb.append("    instantiationLevelId: ").append(toIndentedString(instantiationLevelId)).append("\n");
    sb.append("    nsDfId: ").append(toIndentedString(nsDfId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    minNumberOfInstances: ").append(toIndentedString(minNumberOfInstances)).append("\n");
    sb.append("    maxNumberOfInstances: ").append(toIndentedString(maxNumberOfInstances)).append("\n");
    sb.append("    nsdId: ").append(toIndentedString(nsdId)).append("\n");
    sb.append("    affinityOrAntiAffinityGroupId: ").append(toIndentedString(affinityOrAntiAffinityGroupId)).append("\n");
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
