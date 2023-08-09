package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.ArrayList;
import java.util.List;



/**
 * NsProfileSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class NsProfileSchema   {
  @JsonProperty("instantiation-level-id")
  private String instantiationLevelId = null;

  @JsonProperty("ns-profile_id")
  private String nsProfileId = null;

  @JsonProperty("min-number-of-instances")
  private String minNumberOfInstances = null;

  @JsonProperty("max-number-of-instances")
  private String maxNumberOfInstances = null;

  @JsonProperty("ns-df-id")
  private String nsDfId = null;

  @JsonProperty("nsd-id")
  private String nsdId = null;

  @JsonProperty("affinity-or-anti-affinity-group-id")

  private List<NsdAffinityOrAntiaffinityGroupId> affinityOrAntiAffinityGroupId = null;

  @JsonProperty("virtual-link-connectivity")

  private List<VirtualLinkConnectivitySchema> virtualLinkConnectivity = null;

  public NsProfileSchema instantiationLevelId(String instantiationLevelId) {
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

  public NsProfileSchema nsProfileId(String nsProfileId) {
    this.nsProfileId = nsProfileId;
    return this;
  }

  /**
   * Identifies an NS profile.
   * @return nsProfileId
   **/
  //@Schema(description = "Identifies an NS profile.")
  
    public String getNsProfileId() {
    return nsProfileId;
  }

  public void setNsProfileId(String nsProfileId) {
    this.nsProfileId = nsProfileId;
  }

  public NsProfileSchema minNumberOfInstances(String minNumberOfInstances) {
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

  public NsProfileSchema maxNumberOfInstances(String maxNumberOfInstances) {
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

  public NsProfileSchema nsDfId(String nsDfId) {
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

  public NsProfileSchema nsdId(String nsdId) {
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

  public NsProfileSchema affinityOrAntiAffinityGroupId(List<NsdAffinityOrAntiaffinityGroupId> affinityOrAntiAffinityGroupId) {
    this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
    return this;
  }

  public NsProfileSchema addAffinityOrAntiAffinityGroupIdItem(NsdAffinityOrAntiaffinityGroupId affinityOrAntiAffinityGroupIdItem) {
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

  public NsProfileSchema virtualLinkConnectivity(List<VirtualLinkConnectivitySchema> virtualLinkConnectivity) {
    this.virtualLinkConnectivity = virtualLinkConnectivity;
    return this;
  }

  public NsProfileSchema addVirtualLinkConnectivityItem(VirtualLinkConnectivitySchema virtualLinkConnectivityItem) {
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
    NsProfileSchema nsProfileSchema = (NsProfileSchema) o;
    return Objects.equals(this.instantiationLevelId, nsProfileSchema.instantiationLevelId) &&
        Objects.equals(this.nsProfileId, nsProfileSchema.nsProfileId) &&
        Objects.equals(this.minNumberOfInstances, nsProfileSchema.minNumberOfInstances) &&
        Objects.equals(this.maxNumberOfInstances, nsProfileSchema.maxNumberOfInstances) &&
        Objects.equals(this.nsDfId, nsProfileSchema.nsDfId) &&
        Objects.equals(this.nsdId, nsProfileSchema.nsdId) &&
        Objects.equals(this.affinityOrAntiAffinityGroupId, nsProfileSchema.affinityOrAntiAffinityGroupId) &&
        Objects.equals(this.virtualLinkConnectivity, nsProfileSchema.virtualLinkConnectivity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instantiationLevelId, nsProfileId, minNumberOfInstances, maxNumberOfInstances, nsDfId, nsdId, affinityOrAntiAffinityGroupId, virtualLinkConnectivity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsProfileSchema {\n");
    
    sb.append("    instantiationLevelId: ").append(toIndentedString(instantiationLevelId)).append("\n");
    sb.append("    nsProfileId: ").append(toIndentedString(nsProfileId)).append("\n");
    sb.append("    minNumberOfInstances: ").append(toIndentedString(minNumberOfInstances)).append("\n");
    sb.append("    maxNumberOfInstances: ").append(toIndentedString(maxNumberOfInstances)).append("\n");
    sb.append("    nsDfId: ").append(toIndentedString(nsDfId)).append("\n");
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
