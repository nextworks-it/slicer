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
 * VnfdDf
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdDf {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("supported-vnf-interfaces")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_fk", referencedColumnName = "uuid")
  private List<VnfdSupportedvnfinterfaces> supportedVnfInterfaces = null;

  @JsonProperty("indicator")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_fk", referencedColumnName = "uuid")
  private List<VnfdIndicator> indicator = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("affinity-or-anti-affinity-group")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_fk", referencedColumnName = "uuid")
  private List<VnfdAffinityorantiaffinitygroup> affinityOrAntiAffinityGroup = null;

  @JsonProperty("lcm-operations-configuration")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_lcm_operations_configuration_fk", referencedColumnName = "uuid")
  private VnfdLcmoperationsconfiguration lcmOperationsConfiguration = null;

  @JsonProperty("virtual-link-profile")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_fk", referencedColumnName = "uuid")
  private List<VnfdDfVirtualLinkProfileItem> virtualLinkProfile = null;

  @JsonProperty("instantiation-level")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_fk", referencedColumnName = "uuid")
  private List<VnfdInstantiationlevel> instantiationLevel = null;

  @JsonProperty("vdu-profile")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_fk", referencedColumnName = "uuid")
  private List<VnfdDfVduProfileItem> vduProfile = null;

  @JsonProperty("supported-operation")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> supportedOperation = null;

  @JsonProperty("scaling-aspect")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_fk", referencedColumnName = "uuid")
  private List<VnfdScalingaspect> scalingAspect = null;

  @JsonProperty("default-instantiation-level")
  private String defaultInstantiationLevel = null;

  @JsonProperty("monitoring-parameter")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_fk", referencedColumnName = "uuid")
  private List<VnfdDfMonitoringParameterItem> monitoringParameter = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("kdu-resource-profile")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_df_fk", referencedColumnName = "uuid")
  private List<KduResourceProfile> kduResourceProfile = null;

  public VnfdDf supportedVnfInterfaces(List<VnfdSupportedvnfinterfaces> supportedVnfInterfaces) {
    this.supportedVnfInterfaces = supportedVnfInterfaces;
    return this;
  }

  public VnfdDf addSupportedVnfInterfacesItem(VnfdSupportedvnfinterfaces supportedVnfInterfacesItem) {
    if (this.supportedVnfInterfaces == null) {
      this.supportedVnfInterfaces = new ArrayList<VnfdSupportedvnfinterfaces>();
    }
    this.supportedVnfInterfaces.add(supportedVnfInterfacesItem);
    return this;
  }

  /**
   * Indicates which interfaces the VNF produces and provides additional details on how to access the interface endpoints.
   * @return supportedVnfInterfaces
   **/
  //@Schema(description = "Indicates which interfaces the VNF produces and provides additional details on how to access the interface endpoints.")

    public List<VnfdSupportedvnfinterfaces> getSupportedVnfInterfaces() {
    return supportedVnfInterfaces;
  }

  public void setSupportedVnfInterfaces(List<VnfdSupportedvnfinterfaces> supportedVnfInterfaces) {
    this.supportedVnfInterfaces = supportedVnfInterfaces;
  }

  public VnfdDf indicator(List<VnfdIndicator> indicator) {
    this.indicator = indicator;
    return this;
  }

  public VnfdDf addIndicatorItem(VnfdIndicator indicatorItem) {
    if (this.indicator == null) {
      this.indicator = new ArrayList<VnfdIndicator>();
    }
    this.indicator.add(indicatorItem);
    return this;
  }

  /**
   * Declares the VNF indicators that are supported by this VNF (specific to this DF).
   * @return indicator
   **/
  //@Schema(description = "Declares the VNF indicators that are supported by this VNF (specific to this DF).")

    public List<VnfdIndicator> getIndicator() {
    return indicator;
  }

  public void setIndicator(List<VnfdIndicator> indicator) {
    this.indicator = indicator;
  }

  public VnfdDf description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human readable description of the deployment flavour
   * @return description
   **/
  //@Schema(description = "Human readable description of the deployment flavour")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public VnfdDf affinityOrAntiAffinityGroup(List<VnfdAffinityorantiaffinitygroup> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
    return this;
  }

  public VnfdDf addAffinityOrAntiAffinityGroupItem(VnfdAffinityorantiaffinitygroup affinityOrAntiAffinityGroupItem) {
    if (this.affinityOrAntiAffinityGroup == null) {
      this.affinityOrAntiAffinityGroup = new ArrayList<VnfdAffinityorantiaffinitygroup>();
    }
    this.affinityOrAntiAffinityGroup.add(affinityOrAntiAffinityGroupItem);
    return this;
  }

  /**
   * The AffinityOrAntiAffinityGroup describes the affinity or anti-affinity relationship applicable between the virtualization containers to be created based on different VDUs, or between internal VLs to be created based on different VnfVirtualLinkDesc(s).  Per VNF, the affinity/anti-affinity rules defined using this information element, using the LocalAffinityOrAntiAffinityRule information element, and using the placement constraints in the GrantLifecycleOperation as defined in ETSI GS NFV-IFA 007 [i.3] should be conflict-free. In case of conflicts, the placement constraints in the GrantLifecycleOperation shall take precedence.
   * @return affinityOrAntiAffinityGroup
   **/
  //@Schema(description = "The AffinityOrAntiAffinityGroup describes the affinity or anti-affinity relationship applicable between the virtualization containers to be created based on different VDUs, or between internal VLs to be created based on different VnfVirtualLinkDesc(s).  Per VNF, the affinity/anti-affinity rules defined using this information element, using the LocalAffinityOrAntiAffinityRule information element, and using the placement constraints in the GrantLifecycleOperation as defined in ETSI GS NFV-IFA 007 [i.3] should be conflict-free. In case of conflicts, the placement constraints in the GrantLifecycleOperation shall take precedence.")

    public List<VnfdAffinityorantiaffinitygroup> getAffinityOrAntiAffinityGroup() {
    return affinityOrAntiAffinityGroup;
  }

  public void setAffinityOrAntiAffinityGroup(List<VnfdAffinityorantiaffinitygroup> affinityOrAntiAffinityGroup) {
    this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
  }

  public VnfdDf lcmOperationsConfiguration(VnfdLcmoperationsconfiguration lcmOperationsConfiguration) {
    this.lcmOperationsConfiguration = lcmOperationsConfiguration;
    return this;
  }

  /**
   * Get lcmOperationsConfiguration
   * @return lcmOperationsConfiguration
   **/
  //@Schema(description = "")
  

    public VnfdLcmoperationsconfiguration getLcmOperationsConfiguration() {
    return lcmOperationsConfiguration;
  }

  public void setLcmOperationsConfiguration(VnfdLcmoperationsconfiguration lcmOperationsConfiguration) {
    this.lcmOperationsConfiguration = lcmOperationsConfiguration;
  }

  public VnfdDf virtualLinkProfile(List<VnfdDfVirtualLinkProfileItem> virtualLinkProfile) {
    this.virtualLinkProfile = virtualLinkProfile;
    return this;
  }

  public VnfdDf addVirtualLinkProfileItem(VnfdDfVirtualLinkProfileItem virtualLinkProfileItem) {
    if (this.virtualLinkProfile == null) {
      this.virtualLinkProfile = new ArrayList<VnfdDfVirtualLinkProfileItem>();
    }
    this.virtualLinkProfile.add(virtualLinkProfileItem);
    return this;
  }

  /**
   * Defines the internal VLD along with additional data which is used in this DF.
   * @return virtualLinkProfile
   **/
  //@Schema(description = "Defines the internal VLD along with additional data which is used in this DF.")
  
    public List<VnfdDfVirtualLinkProfileItem> getVirtualLinkProfile() {
    return virtualLinkProfile;
  }

  public void setVirtualLinkProfile(List<VnfdDfVirtualLinkProfileItem> virtualLinkProfile) {
    this.virtualLinkProfile = virtualLinkProfile;
  }

  public VnfdDf instantiationLevel(List<VnfdInstantiationlevel> instantiationLevel) {
    this.instantiationLevel = instantiationLevel;
    return this;
  }

  public VnfdDf addInstantiationLevelItem(VnfdInstantiationlevel instantiationLevelItem) {
    if (this.instantiationLevel == null) {
      this.instantiationLevel = new ArrayList<VnfdInstantiationlevel>();
    }
    this.instantiationLevel.add(instantiationLevelItem);
    return this;
  }

  /**
   * Describes the various levels of resources that can be used to instantiate the VNF using this flavour. Examples: Small, Medium, Large. If there is only one 'instantiationLevel' entry, it shall be treated as the default instantiation level for this DF.  The InstantiationLevel information element describes a given level of resources to be instantiated within a deployment flavour in term of the number of VNFC instances to be created from each VDU. All the VDUs referenced in the level shall be part of the corresponding deployment flavour and their number shall be within the range (min/max) for this deployment flavour.
   * @return instantiationLevel
   **/
  //@Schema(description = "Describes the various levels of resources that can be used to instantiate the VNF using this flavour. Examples: Small, Medium, Large. If there is only one 'instantiationLevel' entry, it shall be treated as the default instantiation level for this DF.  The InstantiationLevel information element describes a given level of resources to be instantiated within a deployment flavour in term of the number of VNFC instances to be created from each VDU. All the VDUs referenced in the level shall be part of the corresponding deployment flavour and their number shall be within the range (min/max) for this deployment flavour.")

    public List<VnfdInstantiationlevel> getInstantiationLevel() {
    return instantiationLevel;
  }

  public void setInstantiationLevel(List<VnfdInstantiationlevel> instantiationLevel) {
    this.instantiationLevel = instantiationLevel;
  }

  public VnfdDf vduProfile(List<VnfdDfVduProfileItem> vduProfile) {
    this.vduProfile = vduProfile;
    return this;
  }

  public VnfdDf addVduProfileItem(VnfdDfVduProfileItem vduProfileItem) {
    if (this.vduProfile == null) {
      this.vduProfile = new ArrayList<VnfdDfVduProfileItem>();
    }
    this.vduProfile.add(vduProfileItem);
    return this;
  }

  /**
   * The Vduprofile describes additional instantiation data for a given VDU used in a deployment flavour.
   * @return vduProfile
   **/
  //@Schema(description = "The Vduprofile describes additional instantiation data for a given VDU used in a deployment flavour.")
  
    public List<VnfdDfVduProfileItem> getVduProfile() {
    return vduProfile;
  }

  public void setVduProfile(List<VnfdDfVduProfileItem> vduProfile) {
    this.vduProfile = vduProfile;
  }

  public VnfdDf supportedOperation(List<String> supportedOperation) {
    this.supportedOperation = supportedOperation;
    return this;
  }

  public VnfdDf addSupportedOperationItem(String supportedOperationItem) {
    if (this.supportedOperation == null) {
      this.supportedOperation = new ArrayList<String>();
    }
    this.supportedOperation.add(supportedOperationItem);
    return this;
  }

  /**
   * Get supportedOperation
   * @return supportedOperation
   **/
  //@Schema(description = "")
  
    public List<String> getSupportedOperation() {
    return supportedOperation;
  }

  public void setSupportedOperation(List<String> supportedOperation) {
    this.supportedOperation = supportedOperation;
  }

  public VnfdDf scalingAspect(List<VnfdScalingaspect> scalingAspect) {
    this.scalingAspect = scalingAspect;
    return this;
  }

  public VnfdDf addScalingAspectItem(VnfdScalingaspect scalingAspectItem) {
    if (this.scalingAspect == null) {
      this.scalingAspect = new ArrayList<VnfdScalingaspect>();
    }
    this.scalingAspect.add(scalingAspectItem);
    return this;
  }

  /**
   * The scaling aspects supported by this DF of the VNF. scalingAspect shall be present if the VNF supports scaling.
   * @return scalingAspect
   **/
  //@Schema(description = "The scaling aspects supported by this DF of the VNF. scalingAspect shall be present if the VNF supports scaling.")

    public List<VnfdScalingaspect> getScalingAspect() {
    return scalingAspect;
  }

  public void setScalingAspect(List<VnfdScalingaspect> scalingAspect) {
    this.scalingAspect = scalingAspect;
  }

  public VnfdDf defaultInstantiationLevel(String defaultInstantiationLevel) {
    this.defaultInstantiationLevel = defaultInstantiationLevel;
    return this;
  }

  /**
   * This attribute references the 'instantiationLevel' entry which defines the default instantiation level for this DF. It shall be present if there are multiple 'instantiationLevel' entries.
   * @return defaultInstantiationLevel
   **/
  //@Schema(description = "This attribute references the 'instantiationLevel' entry which defines the default instantiation level for this DF. It shall be present if there are multiple 'instantiationLevel' entries.")
  
    public String getDefaultInstantiationLevel() {
    return defaultInstantiationLevel;
  }

  public void setDefaultInstantiationLevel(String defaultInstantiationLevel) {
    this.defaultInstantiationLevel = defaultInstantiationLevel;
  }

  public VnfdDf monitoringParameter(List<VnfdDfMonitoringParameterItem> monitoringParameter) {
    this.monitoringParameter = monitoringParameter;
    return this;
  }

  public VnfdDf addMonitoringParameterItem(VnfdDfMonitoringParameterItem monitoringParameterItem) {
    if (this.monitoringParameter == null) {
      this.monitoringParameter = new ArrayList<VnfdDfMonitoringParameterItem>();
    }
    this.monitoringParameter.add(monitoringParameterItem);
    return this;
  }

  /**
   * Defines the virtualised resources monitoring parameters on VNF level.
   * @return monitoringParameter
   **/
  //@Schema(description = "Defines the virtualised resources monitoring parameters on VNF level.")
  
    public List<VnfdDfMonitoringParameterItem> getMonitoringParameter() {
    return monitoringParameter;
  }

  public void setMonitoringParameter(List<VnfdDfMonitoringParameterItem> monitoringParameter) {
    this.monitoringParameter = monitoringParameter;
  }

  public VnfdDf id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this DF within the VNFD.
   * @return id
   **/
  //@Schema(description = "Identifier of this DF within the VNFD.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VnfdDf kduResourceProfile(List<KduResourceProfile> kduResourceProfile) {
      this.kduResourceProfile = kduResourceProfile;
      return this;
  }

  public VnfdDf addKduResourceProfileItem(KduResourceProfile kduResourceProfile) {
      if(this.kduResourceProfile == null)
        this.kduResourceProfile = new ArrayList<>();

      this.kduResourceProfile.add(kduResourceProfile);
      return this;
  }

  public List<KduResourceProfile> getKduResourceProfile() { return kduResourceProfile; }

  public void setKduResourceProfile(List<KduResourceProfile> kduResourceProfile) { this.kduResourceProfile = kduResourceProfile; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdDf vnfdDf = (VnfdDf) o;
    return Objects.equals(this.supportedVnfInterfaces, vnfdDf.supportedVnfInterfaces) &&
            Objects.equals(this.indicator, vnfdDf.indicator) &&
            Objects.equals(this.description, vnfdDf.description) &&
            Objects.equals(this.affinityOrAntiAffinityGroup, vnfdDf.affinityOrAntiAffinityGroup) &&
            Objects.equals(this.lcmOperationsConfiguration, vnfdDf.lcmOperationsConfiguration) &&
            Objects.equals(this.virtualLinkProfile, vnfdDf.virtualLinkProfile) &&
            Objects.equals(this.instantiationLevel, vnfdDf.instantiationLevel) &&
            Objects.equals(this.vduProfile, vnfdDf.vduProfile) &&
            Objects.equals(this.supportedOperation, vnfdDf.supportedOperation) &&
            Objects.equals(this.scalingAspect, vnfdDf.scalingAspect) &&
            Objects.equals(this.defaultInstantiationLevel, vnfdDf.defaultInstantiationLevel) &&
            Objects.equals(this.monitoringParameter, vnfdDf.monitoringParameter) &&
            Objects.equals(this.id, vnfdDf.id) &&
            Objects.equals(this.kduResourceProfile, vnfdDf.kduResourceProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(supportedVnfInterfaces, indicator, description, affinityOrAntiAffinityGroup,
            lcmOperationsConfiguration, virtualLinkProfile, instantiationLevel, vduProfile, supportedOperation,
            scalingAspect, defaultInstantiationLevel, monitoringParameter, id, kduResourceProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdDf {\n");
    
    sb.append("    supportedVnfInterfaces: ").append(toIndentedString(supportedVnfInterfaces)).append("\n");
    sb.append("    indicator: ").append(toIndentedString(indicator)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    affinityOrAntiAffinityGroup: ").append(toIndentedString(affinityOrAntiAffinityGroup)).append("\n");
    sb.append("    lcmOperationsConfiguration: ").append(toIndentedString(lcmOperationsConfiguration)).append("\n");
    sb.append("    virtualLinkProfile: ").append(toIndentedString(virtualLinkProfile)).append("\n");
    sb.append("    instantiationLevel: ").append(toIndentedString(instantiationLevel)).append("\n");
    sb.append("    vduProfile: ").append(toIndentedString(vduProfile)).append("\n");
    sb.append("    supportedOperation: ").append(toIndentedString(supportedOperation)).append("\n");
    sb.append("    scalingAspect: ").append(toIndentedString(scalingAspect)).append("\n");
    sb.append("    defaultInstantiationLevel: ").append(toIndentedString(defaultInstantiationLevel)).append("\n");
    sb.append("    monitoringParameter: ").append(toIndentedString(monitoringParameter)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    kduResourceProfile: ").append(toIndentedString(kduResourceProfile)).append("\n");
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
