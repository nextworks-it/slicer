package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * DfSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class DfSchema   {
  @JsonProperty("flavour-key")
  private String flavourKey = null;

  @JsonProperty("affinity-or-anti-affinity-group")

  private List<NsdAffinityOrAntiaffinityGroup> affinityOrAntiAffinityGroup = null;

  @JsonProperty("vnf-profile")

  private List<LocalAffinityOrAntiAffinityRule> vnfProfile = null;

  @JsonProperty("virtual-link-profile")

  private List<LocalAffinityOrAntiAffinityRule> virtualLinkProfile = null;

  @JsonProperty("ns-instantiation-level")

  private List<DfSchemaNsinstantiationlevel> nsInstantiationLevel = null;

  @JsonProperty("dependencies")

  private List<NsdDependencies> dependencies = null;

  @JsonProperty("monitored-info")

  private List<NsdMonitoredinfo> monitoredInfo = null;

  @JsonProperty("scaling-aspect")

  private List<NsdScalingaspect> scalingAspect = null;

  @JsonProperty("pnf-profile")

  private List<NsdPnfprofile> pnfProfile = null;

  @JsonProperty("default-instantiation-level")
  private String defaultInstantiationLevel = null;

  @JsonProperty("df_id")
  private String dfId = null;

  @JsonProperty("ns-profile")

  private List<NsdNsprofile> nsProfile = null;

  public DfSchema flavourKey(String flavourKey) {
    this.flavourKey = flavourKey;
    return this;
  }

  /**
   * Assurance parameter against which this flavour is being described.  The key can be a combination of multiple assurance parameters with a logical relationship between them. The parameters should be present as a monitoredInfo attribute in the NSD.
   * @return flavourKey
   **/
  //@Schema(description = "Assurance parameter against which this flavour is being described.  The key can be a combination of multiple assurance parameters with a logical relationship between them. The parameters should be present as a monitoredInfo attribute in the NSD.")

    public String getFlavourKey() {
    return flavourKey;
  }

  public void setFlavourKey(String flavourKey) {
    this.flavourKey = flavourKey;
  }

  public DfSchema affinityOrAntiAffinityGroup(List<NsdAffinityOrAntiaffinityGroup> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
    return this;
  }

  public DfSchema addAffinityOrAntiAffinityGroupItem(NsdAffinityOrAntiaffinityGroup affinityOrAntiAffinityGroupItem) {
    if (this.affinityOrAntiAffinityGroup == null) {
      this.affinityOrAntiAffinityGroup = new ArrayList<NsdAffinityOrAntiaffinityGroup>();
    }
    this.affinityOrAntiAffinityGroup.add(affinityOrAntiAffinityGroupItem);
    return this;
  }

  /**
   * Specifies affinity or anti-affinity relationship applicable between the VNF instances created using different VNFDs, the Virtual Link instances created using different NsVirtualLinkDescs or the nested NS instances created using different NSDs in the same affinity or anti-affinity group.
   * @return affinityOrAntiAffinityGroup
   **/
  //@Schema(description = "Specifies affinity or anti-affinity relationship applicable between the VNF instances created using different VNFDs, the Virtual Link instances created using different NsVirtualLinkDescs or the nested NS instances created using different NSDs in the same affinity or anti-affinity group.")

    public List<NsdAffinityOrAntiaffinityGroup> getAffinityOrAntiAffinityGroup() {
    return affinityOrAntiAffinityGroup;
  }

  public void setAffinityOrAntiAffinityGroup(List<NsdAffinityOrAntiaffinityGroup> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
  }

  public DfSchema vnfProfile(List<LocalAffinityOrAntiAffinityRule> vnfProfile) {
    this.vnfProfile = vnfProfile;
    return this;
  }

  public DfSchema addVnfProfileItem(LocalAffinityOrAntiAffinityRule vnfProfileItem) {
    if (this.vnfProfile == null) {
      this.vnfProfile = new ArrayList<LocalAffinityOrAntiAffinityRule>();
    }
    this.vnfProfile.add(vnfProfileItem);
    return this;
  }

  /**
   * VNF profile to be used for the NS flavour.
   * @return vnfProfile
   **/
  //@Schema(description = "VNF profile to be used for the NS flavour.")

    public List<LocalAffinityOrAntiAffinityRule> getVnfProfile() {
    return vnfProfile;
  }

  public void setVnfProfile(List<LocalAffinityOrAntiAffinityRule> vnfProfile) {
    this.vnfProfile = vnfProfile;
  }

  public DfSchema virtualLinkProfile(List<LocalAffinityOrAntiAffinityRule> virtualLinkProfile) {
    this.virtualLinkProfile = virtualLinkProfile;
    return this;
  }

  public DfSchema addVirtualLinkProfileItem(LocalAffinityOrAntiAffinityRule virtualLinkProfileItem) {
    if (this.virtualLinkProfile == null) {
      this.virtualLinkProfile = new ArrayList<LocalAffinityOrAntiAffinityRule>();
    }
    this.virtualLinkProfile.add(virtualLinkProfileItem);
    return this;
  }

  /**
   * VL profile to be used for the NS flavour.
   * @return virtualLinkProfile
   **/
  //@Schema(description = "VL profile to be used for the NS flavour.")

    public List<LocalAffinityOrAntiAffinityRule> getVirtualLinkProfile() {
    return virtualLinkProfile;
  }

  public void setVirtualLinkProfile(List<LocalAffinityOrAntiAffinityRule> virtualLinkProfile) {
    this.virtualLinkProfile = virtualLinkProfile;
  }

  public DfSchema nsInstantiationLevel(List<DfSchemaNsinstantiationlevel> nsInstantiationLevel) {
    this.nsInstantiationLevel = nsInstantiationLevel;
    return this;
  }

  public DfSchema addNsInstantiationLevelItem(DfSchemaNsinstantiationlevel nsInstantiationLevelItem) {
    if (this.nsInstantiationLevel == null) {
      this.nsInstantiationLevel = new ArrayList<DfSchemaNsinstantiationlevel>();
    }
    this.nsInstantiationLevel.add(nsInstantiationLevelItem);
    return this;
  }

  /**
   * Describes the details of an NS level.
   * @return nsInstantiationLevel
   **/
  //@Schema(description = "Describes the details of an NS level.")

    public List<DfSchemaNsinstantiationlevel> getNsInstantiationLevel() {
    return nsInstantiationLevel;
  }

  public void setNsInstantiationLevel(List<DfSchemaNsinstantiationlevel> nsInstantiationLevel) {
    this.nsInstantiationLevel = nsInstantiationLevel;
  }

  public DfSchema dependencies(List<NsdDependencies> dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  public DfSchema addDependenciesItem(NsdDependencies dependenciesItem) {
    if (this.dependencies == null) {
      this.dependencies = new ArrayList<NsdDependencies>();
    }
    this.dependencies.add(dependenciesItem);
    return this;
  }

  /**
   * Specifies the order in which instances of the VNFs and/or nested NSs have to be created.
   * @return dependencies
   **/
  //@Schema(description = "Specifies the order in which instances of the VNFs and/or nested NSs have to be created.")

    public List<NsdDependencies> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<NsdDependencies> dependencies) {
    this.dependencies = dependencies;
  }

  public DfSchema monitoredInfo(List<NsdMonitoredinfo> monitoredInfo) {
    this.monitoredInfo = monitoredInfo;
    return this;
  }

  public DfSchema addMonitoredInfoItem(NsdMonitoredinfo monitoredInfoItem) {
    if (this.monitoredInfo == null) {
      this.monitoredInfo = new ArrayList<NsdMonitoredinfo>();
    }
    this.monitoredInfo.add(monitoredInfoItem);
    return this;
  }

  /**
   * Get monitoredInfo
   * @return monitoredInfo
   **/
  //@Schema(description = "")

    public List<NsdMonitoredinfo> getMonitoredInfo() {
    return monitoredInfo;
  }

  public void setMonitoredInfo(List<NsdMonitoredinfo> monitoredInfo) {
    this.monitoredInfo = monitoredInfo;
  }

  public DfSchema scalingAspect(List<NsdScalingaspect> scalingAspect) {
    this.scalingAspect = scalingAspect;
    return this;
  }

  public DfSchema addScalingAspectItem(NsdScalingaspect scalingAspectItem) {
    if (this.scalingAspect == null) {
      this.scalingAspect = new ArrayList<NsdScalingaspect>();
    }
    this.scalingAspect.add(scalingAspectItem);
    return this;
  }

  /**
   * The scaling aspects supported by this DF of the NS.
   * @return scalingAspect
   **/
  //@Schema(description = "The scaling aspects supported by this DF of the NS.")

    public List<NsdScalingaspect> getScalingAspect() {
    return scalingAspect;
  }

  public void setScalingAspect(List<NsdScalingaspect> scalingAspect) {
    this.scalingAspect = scalingAspect;
  }

  public DfSchema pnfProfile(List<NsdPnfprofile> pnfProfile) {
    this.pnfProfile = pnfProfile;
    return this;
  }

  public DfSchema addPnfProfileItem(NsdPnfprofile pnfProfileItem) {
    if (this.pnfProfile == null) {
      this.pnfProfile = new ArrayList<NsdPnfprofile>();
    }
    this.pnfProfile.add(pnfProfileItem);
    return this;
  }

  /**
   * Get pnfProfile
   * @return pnfProfile
   **/
  //@Schema(description = "")

    public List<NsdPnfprofile> getPnfProfile() {
    return pnfProfile;
  }

  public void setPnfProfile(List<NsdPnfprofile> pnfProfile) {
    this.pnfProfile = pnfProfile;
  }

  public DfSchema defaultInstantiationLevel(String defaultInstantiationLevel) {
    this.defaultInstantiationLevel = defaultInstantiationLevel;
    return this;
  }

  /**
   * Identifies the NS level which represents the default NS instantiation level for this DF. It shall be present if there are multiple 'nsIinstantiationLevel' entries.
   * @return defaultInstantiationLevel
   **/
  //@Schema(description = "Identifies the NS level which represents the default NS instantiation level for this DF. It shall be present if there are multiple 'nsIinstantiationLevel' entries.")

    public String getDefaultInstantiationLevel() {
    return defaultInstantiationLevel;
  }

  public void setDefaultInstantiationLevel(String defaultInstantiationLevel) {
    this.defaultInstantiationLevel = defaultInstantiationLevel;
  }

  public DfSchema dfId(String dfId) {
    this.dfId = dfId;
    return this;
  }

  /**
   * Identifies this NsDf information element. It identifies a NS DF within the NSD.
   * @return dfId
   **/
  //@Schema(description = "Identifies this NsDf information element. It identifies a NS DF within the NSD.")

    public String getDfId() {
    return dfId;
  }

  public void setDfId(String dfId) {
    this.dfId = dfId;
  }

  public DfSchema nsProfile(List<NsdNsprofile> nsProfile) {
    this.nsProfile = nsProfile;
    return this;
  }

  public DfSchema addNsProfileItem(NsdNsprofile nsProfileItem) {
    if (this.nsProfile == null) {
      this.nsProfile = new ArrayList<NsdNsprofile>();
    }
    this.nsProfile.add(nsProfileItem);
    return this;
  }

  /**
   * Specifies a NS Profile supported by this NS DF.
   * @return nsProfile
   **/
  //@Schema(description = "Specifies a NS Profile supported by this NS DF.")

    public List<NsdNsprofile> getNsProfile() {
    return nsProfile;
  }

  public void setNsProfile(List<NsdNsprofile> nsProfile) {
    this.nsProfile = nsProfile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DfSchema dfSchema = (DfSchema) o;
    return Objects.equals(this.flavourKey, dfSchema.flavourKey) &&
        Objects.equals(this.affinityOrAntiAffinityGroup, dfSchema.affinityOrAntiAffinityGroup) &&
        Objects.equals(this.vnfProfile, dfSchema.vnfProfile) &&
        Objects.equals(this.virtualLinkProfile, dfSchema.virtualLinkProfile) &&
        Objects.equals(this.nsInstantiationLevel, dfSchema.nsInstantiationLevel) &&
        Objects.equals(this.dependencies, dfSchema.dependencies) &&
        Objects.equals(this.monitoredInfo, dfSchema.monitoredInfo) &&
        Objects.equals(this.scalingAspect, dfSchema.scalingAspect) &&
        Objects.equals(this.pnfProfile, dfSchema.pnfProfile) &&
        Objects.equals(this.defaultInstantiationLevel, dfSchema.defaultInstantiationLevel) &&
        Objects.equals(this.dfId, dfSchema.dfId) &&
        Objects.equals(this.nsProfile, dfSchema.nsProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flavourKey, affinityOrAntiAffinityGroup, vnfProfile, virtualLinkProfile, nsInstantiationLevel, dependencies, monitoredInfo, scalingAspect, pnfProfile, defaultInstantiationLevel, dfId, nsProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DfSchema {\n");

    sb.append("    flavourKey: ").append(toIndentedString(flavourKey)).append("\n");
    sb.append("    affinityOrAntiAffinityGroup: ").append(toIndentedString(affinityOrAntiAffinityGroup)).append("\n");
    sb.append("    vnfProfile: ").append(toIndentedString(vnfProfile)).append("\n");
    sb.append("    virtualLinkProfile: ").append(toIndentedString(virtualLinkProfile)).append("\n");
    sb.append("    nsInstantiationLevel: ").append(toIndentedString(nsInstantiationLevel)).append("\n");
    sb.append("    dependencies: ").append(toIndentedString(dependencies)).append("\n");
    sb.append("    monitoredInfo: ").append(toIndentedString(monitoredInfo)).append("\n");
    sb.append("    scalingAspect: ").append(toIndentedString(scalingAspect)).append("\n");
    sb.append("    pnfProfile: ").append(toIndentedString(pnfProfile)).append("\n");
    sb.append("    defaultInstantiationLevel: ").append(toIndentedString(defaultInstantiationLevel)).append("\n");
    sb.append("    dfId: ").append(toIndentedString(dfId)).append("\n");
    sb.append("    nsProfile: ").append(toIndentedString(nsProfile)).append("\n");
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
