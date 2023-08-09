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
 * NsdDf
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdDf {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("flavour-key")
  private String flavourKey = null;

  @JsonProperty("affinity-or-anti-affinity-group")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_df_fk", referencedColumnName = "uuid")
  private List<NsdAffinityOrAntiaffinityGroup> affinityOrAntiAffinityGroup = null;

  @JsonProperty("vnf-profile")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_df_fk", referencedColumnName = "uuid")
  private List<VnfProfileItem> vnfProfile = null;

  @JsonProperty("virtual-link-profile")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_df_fk", referencedColumnName = "uuid")
  private List<VirtualLinkProfileItem> virtualLinkProfile = null;

  @JsonProperty("ns-instantiation-level")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_df_fk", referencedColumnName = "uuid")
  private List<NsdNsinstantiationlevel> nsInstantiationLevel = null;

  @JsonProperty("dependencies")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_df_fk", referencedColumnName = "uuid")
  private List<NsdDependencies> dependencies = null;

  @JsonProperty("monitored-info")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_df_fk", referencedColumnName = "uuid")
  private List<NsdMonitoredinfo> monitoredInfo = null;

  @JsonProperty("scaling-aspect")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_df_fk", referencedColumnName = "uuid")
  private List<NsdScalingaspect> scalingAspect = null;

  @JsonProperty("pnf-profile")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_df_fk", referencedColumnName = "uuid")
  private List<NsdPnfprofile> pnfProfile = null;

  @JsonProperty("default-instantiation-level")
  private String defaultInstantiationLevel = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("ns-profile")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_df_fk", referencedColumnName = "uuid")
  private List<NsdNsprofile> nsProfile = null;

  public NsdDf flavourKey(String flavourKey) {
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

  public NsdDf affinityOrAntiAffinityGroup(List<NsdAffinityOrAntiaffinityGroup> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
    return this;
  }

  public NsdDf addAffinityOrAntiAffinityGroupItem(NsdAffinityOrAntiaffinityGroup affinityOrAntiAffinityGroupItem) {
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

  public NsdDf vnfProfile(List<VnfProfileItem> vnfProfile) {
    this.vnfProfile = vnfProfile;
    return this;
  }

  public NsdDf addVnfProfileItem(VnfProfileItem vnfProfileItem) {
    if (this.vnfProfile == null) {
      this.vnfProfile = new ArrayList<VnfProfileItem>();
    }
    this.vnfProfile.add(vnfProfileItem);
    return this;
  }

  /**
   * VNF profile to be used for the NS flavour.
   * @return vnfProfile
   **/
  //@Schema(description = "VNF profile to be used for the NS flavour.")
  
    public List<VnfProfileItem> getVnfProfile() {
    return vnfProfile;
  }

  public void setVnfProfile(List<VnfProfileItem> vnfProfile) {
    this.vnfProfile = vnfProfile;
  }

  public NsdDf virtualLinkProfile(List<VirtualLinkProfileItem> virtualLinkProfile) {
    this.virtualLinkProfile = virtualLinkProfile;
    return this;
  }

  public NsdDf addVirtualLinkProfileItem(VirtualLinkProfileItem virtualLinkProfileItem) {
    if (this.virtualLinkProfile == null) {
      this.virtualLinkProfile = new ArrayList<VirtualLinkProfileItem>();
    }
    this.virtualLinkProfile.add(virtualLinkProfileItem);
    return this;
  }

  /**
   * VL profile to be used for the NS flavour.
   * @return virtualLinkProfile
   **/
  //@Schema(description = "VL profile to be used for the NS flavour.")
  
    public List<VirtualLinkProfileItem> getVirtualLinkProfile() {
    return virtualLinkProfile;
  }

  public void setVirtualLinkProfile(List<VirtualLinkProfileItem> virtualLinkProfile) {
    this.virtualLinkProfile = virtualLinkProfile;
  }

  public NsdDf nsInstantiationLevel(List<NsdNsinstantiationlevel> nsInstantiationLevel) {
    this.nsInstantiationLevel = nsInstantiationLevel;
    return this;
  }

  public NsdDf addNsInstantiationLevelItem(NsdNsinstantiationlevel nsInstantiationLevelItem) {
    if (this.nsInstantiationLevel == null) {
      this.nsInstantiationLevel = new ArrayList<NsdNsinstantiationlevel>();
    }
    this.nsInstantiationLevel.add(nsInstantiationLevelItem);
    return this;
  }

  /**
   * Describes the details of an NS level.
   * @return nsInstantiationLevel
   **/
  //@Schema(description = "Describes the details of an NS level.")

    public List<NsdNsinstantiationlevel> getNsInstantiationLevel() {
    return nsInstantiationLevel;
  }

  public void setNsInstantiationLevel(List<NsdNsinstantiationlevel> nsInstantiationLevel) {
    this.nsInstantiationLevel = nsInstantiationLevel;
  }

  public NsdDf dependencies(List<NsdDependencies> dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  public NsdDf addDependenciesItem(NsdDependencies dependenciesItem) {
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

  public NsdDf monitoredInfo(List<NsdMonitoredinfo> monitoredInfo) {
    this.monitoredInfo = monitoredInfo;
    return this;
  }

  public NsdDf addMonitoredInfoItem(NsdMonitoredinfo monitoredInfoItem) {
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

  public NsdDf scalingAspect(List<NsdScalingaspect> scalingAspect) {
    this.scalingAspect = scalingAspect;
    return this;
  }

  public NsdDf addScalingAspectItem(NsdScalingaspect scalingAspectItem) {
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

  public NsdDf pnfProfile(List<NsdPnfprofile> pnfProfile) {
    this.pnfProfile = pnfProfile;
    return this;
  }

  public NsdDf addPnfProfileItem(NsdPnfprofile pnfProfileItem) {
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

  public NsdDf defaultInstantiationLevel(String defaultInstantiationLevel) {
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

  public NsdDf id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifies this NsDf information element. It identifies a NS DF within the NSD.
   * @return id
   **/
  //@Schema(description = "Identifies this NsDf information element. It identifies a NS DF within the NSD.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public NsdDf nsProfile(List<NsdNsprofile> nsProfile) {
    this.nsProfile = nsProfile;
    return this;
  }

  public NsdDf addNsProfileItem(NsdNsprofile nsProfileItem) {
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
    NsdDf nsdDf = (NsdDf) o;
    return Objects.equals(this.flavourKey, nsdDf.flavourKey) &&
        Objects.equals(this.affinityOrAntiAffinityGroup, nsdDf.affinityOrAntiAffinityGroup) &&
        Objects.equals(this.vnfProfile, nsdDf.vnfProfile) &&
        Objects.equals(this.virtualLinkProfile, nsdDf.virtualLinkProfile) &&
        Objects.equals(this.nsInstantiationLevel, nsdDf.nsInstantiationLevel) &&
        Objects.equals(this.dependencies, nsdDf.dependencies) &&
        Objects.equals(this.monitoredInfo, nsdDf.monitoredInfo) &&
        Objects.equals(this.scalingAspect, nsdDf.scalingAspect) &&
        Objects.equals(this.pnfProfile, nsdDf.pnfProfile) &&
        Objects.equals(this.defaultInstantiationLevel, nsdDf.defaultInstantiationLevel) &&
        Objects.equals(this.id, nsdDf.id) &&
        Objects.equals(this.nsProfile, nsdDf.nsProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flavourKey, affinityOrAntiAffinityGroup, vnfProfile, virtualLinkProfile, nsInstantiationLevel, dependencies, monitoredInfo, scalingAspect, pnfProfile, defaultInstantiationLevel, id, nsProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdDf {\n");
    
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
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
