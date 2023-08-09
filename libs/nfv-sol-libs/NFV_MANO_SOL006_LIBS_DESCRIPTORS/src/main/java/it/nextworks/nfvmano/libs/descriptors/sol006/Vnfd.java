package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vnfd
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class Vnfd extends SecurityGroupRule {

  @JsonProperty("default-localization-language")
  private String defaultLocalizationLanguage = null;

  @JsonProperty("ext-cpd")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<ExtCpd> extCpd = null;

  @JsonProperty("id")
  @Column(unique = true)
  private String id = null;

  @JsonProperty("sw-image-desc")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdSwimagedesc> swImageDesc = null;

  @JsonProperty("element-group")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdElementgroup> elementGroup = null;

  @JsonProperty("virtual-storage-desc")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdVirtualstoragedesc> virtualStorageDesc = null;

  @JsonProperty("indicator")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdIndicator> indicator = null;

  @JsonProperty("virtual-compute-desc")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdVirtualcomputedesc> virtualComputeDesc = null;

  @JsonProperty("vnfm-info")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> vnfmInfo = null;

  @JsonProperty("product-info-name")
  private String productInfoName = null;

  @JsonProperty("modifiable-attributes")
  @Embedded
  private VnfdModifiableattributes modifiableAttributes = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("provider")
  private String provider = null;

  @JsonProperty("product-name")
  private String productName = null;

  @JsonProperty("df")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdDf> df = null;

  @JsonProperty("software-version")
  private String softwareVersion = null;

  @JsonProperty("configurable-properties")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_configurable_properties_fk", referencedColumnName = "uuid")
  private VnfdConfigurableproperties configurableProperties = null;

  @JsonProperty("auto-scale")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> autoScale = null;

  @JsonProperty("lifecycle-management-script")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdLifecyclemanagementscript> lifecycleManagementScript = null;

  @JsonProperty("vdu")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdVdu> vdu = null;

  @JsonProperty("localization-language")
  private String localizationLanguage = null;

  @JsonProperty("int-virtual-link-desc")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<IntVirtualLinkDesc> intVirtualLinkDesc = null;

  @JsonProperty("product-info-description")
  private String productInfoDescription = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("kdu")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<Kdu> kdu = null;

  @JsonProperty("k8s-cluster")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "k8s_cluster_fk", referencedColumnName = "uuid")
  private K8sCluster k8sCluster = null;

  @JsonProperty("service")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdKduService> service = null;

  @JsonProperty("mgmt-cp")
  private String mgmtCp = null;

  @JsonProperty("vip")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_fk", referencedColumnName = "uuid")
  private List<VnfdVip> vip = null;

  public Vnfd defaultLocalizationLanguage(String defaultLocalizationLanguage) {
    this.defaultLocalizationLanguage = defaultLocalizationLanguage;
    return this;
  }

  /**
   * Default localization language that is instantiated if no information about selected localization language is available. Shall be present if 'localization-language' is present and shall be absent otherwise.
   * @return defaultLocalizationLanguage
   **/
  //@Schema(description = "Default localization language that is instantiated if no information about selected localization language is available. Shall be present if 'localization-language' is present and shall be absent otherwise.")
  
    public String getDefaultLocalizationLanguage() {
    return defaultLocalizationLanguage;
  }

  public void setDefaultLocalizationLanguage(String defaultLocalizationLanguage) {
    this.defaultLocalizationLanguage = defaultLocalizationLanguage;
  }

  public Vnfd extCpd(List<ExtCpd> extCpd) {
    this.extCpd = extCpd;
    return this;
  }

  public Vnfd addExtCpdItem(ExtCpd extCpdItem) {
    if (this.extCpd == null) {
      this.extCpd = new ArrayList<ExtCpd>();
    }
    this.extCpd.add(extCpdItem);
    return this;
  }

  /**
   * Describes an external interface exposed by this VNF enabling connection with a Virual Link
   * @return extCpd
   **/
  //@Schema(description = "Describes an external interface exposed by this VNF enabling connection with a Virual Link")
  
    public List<ExtCpd> getExtCpd() {
    return extCpd;
  }

  public void setExtCpd(List<ExtCpd> extCpd) {
    this.extCpd = extCpd;
  }

  public Vnfd id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this VNFD information element. This attribute shall be globally unique. The format will be defined in the data model specification phase.
   * @return id
   **/
  //@Schema(description = "Identifier of this VNFD information element. This attribute shall be globally unique. The format will be defined in the data model specification phase.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Vnfd swImageDesc(List<VnfdSwimagedesc> swImageDesc) {
    this.swImageDesc = swImageDesc;
    return this;
  }

  public Vnfd addSwImageDescItem(VnfdSwimagedesc swImageDescItem) {
    if (this.swImageDesc == null) {
      this.swImageDesc = new ArrayList<VnfdSwimagedesc>();
    }
    this.swImageDesc.add(swImageDescItem);
    return this;
  }

  /**
   * Defines descriptors of software images to be used by the VNF.
   * @return swImageDesc
   **/
  //@Schema(description = "Defines descriptors of software images to be used by the VNF.")

    public List<VnfdSwimagedesc> getSwImageDesc() {
    return swImageDesc;
  }

  public void setSwImageDesc(List<VnfdSwimagedesc> swImageDesc) {
    this.swImageDesc = swImageDesc;
  }

  public Vnfd elementGroup(List<VnfdElementgroup> elementGroup) {
    this.elementGroup = elementGroup;
    return this;
  }

  public Vnfd addElementGroupItem(VnfdElementgroup elementGroupItem) {
    if (this.elementGroup == null) {
      this.elementGroup = new ArrayList<VnfdElementgroup>();
    }
    this.elementGroup.add(elementGroupItem);
    return this;
  }

  /**
   * Describes the associated elements of a VNFD for a certain purpose during VNF lifecycle management.
   * @return elementGroup
   **/
  //@Schema(description = "Describes the associated elements of a VNFD for a certain purpose during VNF lifecycle management.")

    public List<VnfdElementgroup> getElementGroup() {
    return elementGroup;
  }

  public void setElementGroup(List<VnfdElementgroup> elementGroup) {
    this.elementGroup = elementGroup;
  }

  public Vnfd virtualStorageDesc(List<VnfdVirtualstoragedesc> virtualStorageDesc) {
    this.virtualStorageDesc = virtualStorageDesc;
    return this;
  }

  public Vnfd addVirtualStorageDescItem(VnfdVirtualstoragedesc virtualStorageDescItem) {
    if (this.virtualStorageDesc == null) {
      this.virtualStorageDesc = new ArrayList<VnfdVirtualstoragedesc>();
    }
    this.virtualStorageDesc.add(virtualStorageDescItem);
    return this;
  }

  /**
   * Storage requirements for a Virtual Storage instance attached to the VNFC created from this VDU
   * @return virtualStorageDesc
   **/
  //@Schema(description = "Storage requirements for a Virtual Storage instance attached to the VNFC created from this VDU")

    public List<VnfdVirtualstoragedesc> getVirtualStorageDesc() {
    return virtualStorageDesc;
  }

  public void setVirtualStorageDesc(List<VnfdVirtualstoragedesc> virtualStorageDesc) {
    this.virtualStorageDesc = virtualStorageDesc;
  }

  public Vnfd indicator(List<VnfdIndicator> indicator) {
    this.indicator = indicator;
    return this;
  }

  public Vnfd addIndicatorItem(VnfdIndicator indicatorItem) {
    if (this.indicator == null) {
      this.indicator = new ArrayList<VnfdIndicator>();
    }
    this.indicator.add(indicatorItem);
    return this;
  }

  /**
   * Declares the VNF indicators that are supported by this VNF.
   * @return indicator
   **/
  //@Schema(description = "Declares the VNF indicators that are supported by this VNF.")

    public List<VnfdIndicator> getIndicator() {
    return indicator;
  }

  public void setIndicator(List<VnfdIndicator> indicator) {
    this.indicator = indicator;
  }

  public Vnfd virtualComputeDesc(List<VnfdVirtualcomputedesc> virtualComputeDesc) {
    this.virtualComputeDesc = virtualComputeDesc;
    return this;
  }

  public Vnfd addVirtualComputeDescItem(VnfdVirtualcomputedesc virtualComputeDescItem) {
    if (this.virtualComputeDesc == null) {
      this.virtualComputeDesc = new ArrayList<VnfdVirtualcomputedesc>();
    }
    this.virtualComputeDesc.add(virtualComputeDescItem);
    return this;
  }

  /**
   * Defines descriptors of virtual compute resources to be used by the VNF.
   * @return virtualComputeDesc
   **/
  //@Schema(description = "Defines descriptors of virtual compute resources to be used by the VNF.")

    public List<VnfdVirtualcomputedesc> getVirtualComputeDesc() {
    return virtualComputeDesc;
  }

  public void setVirtualComputeDesc(List<VnfdVirtualcomputedesc> virtualComputeDesc) {
    this.virtualComputeDesc = virtualComputeDesc;
  }

  public Vnfd vnfmInfo(List<String> vnfmInfo) {
    this.vnfmInfo = vnfmInfo;
    return this;
  }

  public Vnfd addVnfmInfoItem(String vnfmInfoItem) {
    if (this.vnfmInfo == null) {
      this.vnfmInfo = new ArrayList<String>();
    }
    this.vnfmInfo.add(vnfmInfoItem);
    return this;
  }

  /**
   * Get vnfmInfo
   * @return vnfmInfo
   **/
  //@Schema(description = "")
  
    public List<String> getVnfmInfo() {
    return vnfmInfo;
  }

  public void setVnfmInfo(List<String> vnfmInfo) {
    this.vnfmInfo = vnfmInfo;
  }

  public Vnfd productInfoName(String productInfoName) {
    this.productInfoName = productInfoName;
    return this;
  }

  /**
   * Human readable name of the VNFD. Can change during the VNF Product lifetime.
   * @return productInfoName
   **/
  //@Schema(description = "Human readable name of the VNFD. Can change during the VNF Product lifetime.")
  
    public String getProductInfoName() {
    return productInfoName;
  }

  public void setProductInfoName(String productInfoName) {
    this.productInfoName = productInfoName;
  }

  public Vnfd modifiableAttributes(VnfdModifiableattributes modifiableAttributes) {
    this.modifiableAttributes = modifiableAttributes;
    return this;
  }

  /**
   * Get modifiableAttributes
   * @return modifiableAttributes
   **/
  //@Schema(description = "")
  

    public VnfdModifiableattributes getModifiableAttributes() {
    return modifiableAttributes;
  }

  public void setModifiableAttributes(VnfdModifiableattributes modifiableAttributes) {
    this.modifiableAttributes = modifiableAttributes;
  }

  public Vnfd version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Identifies the version of the VNFD
   * @return version
   **/
  //@Schema(description = "Identifies the version of the VNFD")
  
    public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Vnfd provider(String provider) {
    this.provider = provider;
    return this;
  }

  /**
   * Provider of the VNF and of the VNFD
   * @return provider
   **/
  //@Schema(description = "Provider of the VNF and of the VNFD")
  
    public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public Vnfd productName(String productName) {
    this.productName = productName;
    return this;
  }

  /**
   * Name to identify the VNF Product. Invariant for the VNF Product lifetime.
   * @return productName
   **/
  //@Schema(description = "Name to identify the VNF Product. Invariant for the VNF Product lifetime.")
  
    public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Vnfd df(List<VnfdDf> df) {
    this.df = df;
    return this;
  }

  public Vnfd addDfItem(VnfdDf dfItem) {
    if (this.df == null) {
      this.df = new ArrayList<VnfdDf>();
    }
    this.df.add(dfItem);
    return this;
  }

  /**
   * Describes a specific Deployment Flavour (DF) of a VNF with specific requirements for capacity and performance.
   * @return df
   **/
  //@Schema(description = "Describes a specific Deployment Flavour (DF) of a VNF with specific requirements for capacity and performance.")

    public List<VnfdDf> getDf() {
    return df;
  }

  public void setDf(List<VnfdDf> df) {
    this.df = df;
  }

  public Vnfd softwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
    return this;
  }

  /**
   * Software version of the VNF. This is changed when there is any change to the software that is included in the VNF Package
   * @return softwareVersion
   **/
  //@Schema(description = "Software version of the VNF. This is changed when there is any change to the software that is included in the VNF Package")
  
    public String getSoftwareVersion() {
    return softwareVersion;
  }

  public void setSoftwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
  }

  public Vnfd configurableProperties(VnfdConfigurableproperties configurableProperties) {
    this.configurableProperties = configurableProperties;
    return this;
  }

  /**
   * Get configurableProperties
   * @return configurableProperties
   **/
  //@Schema(description = "")
  

    public VnfdConfigurableproperties getConfigurableProperties() {
    return configurableProperties;
  }

  public void setConfigurableProperties(VnfdConfigurableproperties configurableProperties) {
    this.configurableProperties = configurableProperties;
  }

  public Vnfd autoScale(List<String> autoScale) {
    this.autoScale = autoScale;
    return this;
  }

  public Vnfd addAutoScaleItem(String autoScaleItem) {
    if (this.autoScale == null) {
      this.autoScale = new ArrayList<String>();
    }
    this.autoScale.add(autoScaleItem);
    return this;
  }

  /**
   * Get autoScale
   * @return autoScale
   **/
  //@Schema(description = "")
  
    public List<String> getAutoScale() {
    return autoScale;
  }

  public void setAutoScale(List<String> autoScale) {
    this.autoScale = autoScale;
  }

  public Vnfd lifecycleManagementScript(List<VnfdLifecyclemanagementscript> lifecycleManagementScript) {
    this.lifecycleManagementScript = lifecycleManagementScript;
    return this;
  }

  public Vnfd addLifecycleManagementScriptItem(VnfdLifecyclemanagementscript lifecycleManagementScriptItem) {
    if (this.lifecycleManagementScript == null) {
      this.lifecycleManagementScript = new ArrayList<VnfdLifecyclemanagementscript>();
    }
    this.lifecycleManagementScript.add(lifecycleManagementScriptItem);
    return this;
  }

  /**
   * Includes a list of events and corresponding management scripts performed for the VNF.
   * @return lifecycleManagementScript
   **/
  //@Schema(description = "Includes a list of events and corresponding management scripts performed for the VNF.")

    public List<VnfdLifecyclemanagementscript> getLifecycleManagementScript() {
    return lifecycleManagementScript;
  }

  public void setLifecycleManagementScript(List<VnfdLifecyclemanagementscript> lifecycleManagementScript) {
    this.lifecycleManagementScript = lifecycleManagementScript;
  }

  public Vnfd vdu(List<VnfdVdu> vdu) {
    this.vdu = vdu;
    return this;
  }

  public Vnfd addVduItem(VnfdVdu vduItem) {
    if (this.vdu == null) {
      this.vdu = new ArrayList<VnfdVdu>();
    }
    this.vdu.add(vduItem);
    return this;
  }

  /**
   * The Virtualisation Deployment Unit (VDU) is a construct supporting the description of the deployment and operational behaviour of a VNF component, or the entire VNF if it was not componentized in components.
   * @return vdu
   **/
  //@Schema(description = "The Virtualisation Deployment Unit (VDU) is a construct supporting the description of the deployment and operational behaviour of a VNF component, or the entire VNF if it was not componentized in components.")

    public List<VnfdVdu> getVdu() {
    return vdu;
  }

  public void setVdu(List<VnfdVdu> vdu) {
    this.vdu = vdu;
  }

  public Vnfd localizationLanguage(String localizationLanguage) {
    this.localizationLanguage = localizationLanguage;
    return this;
  }

  /**
   * Information about the language of the VNF.
   * @return localizationLanguage
   **/
  //@Schema(description = "Information about the language of the VNF.")
  
    public String getLocalizationLanguage() {
    return localizationLanguage;
  }

  public void setLocalizationLanguage(String localizationLanguage) {
    this.localizationLanguage = localizationLanguage;
  }

  public Vnfd intVirtualLinkDesc(List<IntVirtualLinkDesc> intVirtualLinkDesc) {
    this.intVirtualLinkDesc = intVirtualLinkDesc;
    return this;
  }

  public Vnfd addIntVirtualLinkDescItem(IntVirtualLinkDesc intVirtualLinkDescItem) {
    if (this.intVirtualLinkDesc == null) {
      this.intVirtualLinkDesc = new ArrayList<IntVirtualLinkDesc>();
    }
    this.intVirtualLinkDesc.add(intVirtualLinkDescItem);
    return this;
  }

  /**
   * Represents the type of network connectivity mandated by the VNF provider between two or more CPs which includes at least one internal CP.
   * @return intVirtualLinkDesc
   **/
  //@Schema(description = "Represents the type of network connectivity mandated by the VNF provider between two or more CPs which includes at least one internal CP.")
  
    public List<IntVirtualLinkDesc> getIntVirtualLinkDesc() {
    return intVirtualLinkDesc;
  }

  public void setIntVirtualLinkDesc(List<IntVirtualLinkDesc> intVirtualLinkDesc) {
    this.intVirtualLinkDesc = intVirtualLinkDesc;
  }

  public Vnfd productInfoDescription(String productInfoDescription) {
    this.productInfoDescription = productInfoDescription;
    return this;
  }

  /**
   * Human readable description of the VNFD. Can change during the VNF Product lifetime.
   * @return productInfoDescription
   **/
  //@Schema(description = "Human readable description of the VNFD. Can change during the VNF Product lifetime.")
  
    public String getProductInfoDescription() {
    return productInfoDescription;
  }

  public void setProductInfoDescription(String productInfoDescription) {
    this.productInfoDescription = productInfoDescription;
  }

  public Vnfd description(String description) {
      this.description = description;
      return this;
  }

  public String getDescription() { return description; }

  public void setDescription(String description) { this.description = description; }

  public Vnfd kdu(List<Kdu> kdu) {
      this.kdu = kdu;
      return this;
  }

  public Vnfd addKduItem(Kdu kdu) {
      if(this.kdu == null)
        this.kdu = new ArrayList<>();

      this.kdu.add(kdu);
      return this;
  }

  public List<Kdu> getKdu() { return kdu; }

  public void setKdu(List<Kdu> kdu) { this.kdu = kdu; }

  public Vnfd k8sCluster(K8sCluster k8sCluster) {
      this.k8sCluster = k8sCluster;
      return this;
  }

  public K8sCluster getK8sCluster() { return k8sCluster; }

  public void setK8sCluster(K8sCluster k8sCluster) { this.k8sCluster = k8sCluster; }

  public Vnfd service(List<VnfdKduService> service) {
      this.service = service;
      return this;
  }

  public Vnfd addServiceItem(VnfdKduService service) {
      if(this.service == null)
        this.service = new ArrayList<>();

      this.service.add(service);
      return this;
  }

  public List<VnfdKduService> getService() { return service; }

  public void setService(List<VnfdKduService> service) { this.service = service; }

  public Vnfd mgmtCp(String mgmtCp) {
      this.mgmtCp = mgmtCp;
      return this;
  }

  public String getMgmtCp() { return mgmtCp; }

  public void setMgmtCp(String mgmtCp) { this.mgmtCp = mgmtCp; }

  public Vnfd vip(List<VnfdVip> vip) {
      this.vip = vip;
      return this;
  }

  public Vnfd addVipItem(VnfdVip vip) {
      if(this.vip == null)
        this.vip = new ArrayList<>();

      this.vip.add(vip);
      return this;
  }

  public List<VnfdVip> getVip() { return vip; }

  public void setVip(List<VnfdVip> vip) { this.vip = vip; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vnfd vnfd = (Vnfd) o;
    return Objects.equals(this.defaultLocalizationLanguage, vnfd.defaultLocalizationLanguage) &&
            Objects.equals(this.extCpd, vnfd.extCpd) &&
            Objects.equals(this.id, vnfd.id) &&
            Objects.equals(this.swImageDesc, vnfd.swImageDesc) &&
            Objects.equals(this.elementGroup, vnfd.elementGroup) &&
            Objects.equals(this.virtualStorageDesc, vnfd.virtualStorageDesc) &&
            Objects.equals(this.indicator, vnfd.indicator) &&
            Objects.equals(this.virtualComputeDesc, vnfd.virtualComputeDesc) &&
            Objects.equals(this.vnfmInfo, vnfd.vnfmInfo) &&
            Objects.equals(this.productInfoName, vnfd.productInfoName) &&
            Objects.equals(this.modifiableAttributes, vnfd.modifiableAttributes) &&
            Objects.equals(this.version, vnfd.version) &&
            Objects.equals(this.provider, vnfd.provider) &&
            Objects.equals(this.productName, vnfd.productName) &&
            Objects.equals(this.df, vnfd.df) &&
            Objects.equals(this.softwareVersion, vnfd.softwareVersion) &&
            Objects.equals(this.configurableProperties, vnfd.configurableProperties) &&
            Objects.equals(this.autoScale, vnfd.autoScale) &&
            Objects.equals(this.lifecycleManagementScript, vnfd.lifecycleManagementScript) &&
            Objects.equals(this.vdu, vnfd.vdu) &&
            Objects.equals(this.localizationLanguage, vnfd.localizationLanguage) &&
            Objects.equals(this.intVirtualLinkDesc, vnfd.intVirtualLinkDesc) &&
            Objects.equals(this.productInfoDescription, vnfd.productInfoDescription) &&
            Objects.equals(this.description, vnfd.description) &&
            Objects.equals(this.kdu, vnfd.kdu) &&
            Objects.equals(this.k8sCluster, vnfd.k8sCluster) &&
            Objects.equals(this.service, vnfd.service) &&
            Objects.equals(this.mgmtCp, vnfd.mgmtCp) &&
            Objects.equals(this.vip, vnfd.vip) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(defaultLocalizationLanguage, extCpd, id, swImageDesc, elementGroup, virtualStorageDesc,
            indicator, virtualComputeDesc, vnfmInfo, productInfoName, modifiableAttributes, version, provider,
            productName, df, softwareVersion, configurableProperties, autoScale, lifecycleManagementScript, vdu,
            localizationLanguage, intVirtualLinkDesc, productInfoDescription, description, kdu, k8sCluster,
            service, mgmtCp, vip, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vnfd {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    defaultLocalizationLanguage: ").append(toIndentedString(defaultLocalizationLanguage)).append("\n");
    sb.append("    extCpd: ").append(toIndentedString(extCpd)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    swImageDesc: ").append(toIndentedString(swImageDesc)).append("\n");
    sb.append("    elementGroup: ").append(toIndentedString(elementGroup)).append("\n");
    sb.append("    virtualStorageDesc: ").append(toIndentedString(virtualStorageDesc)).append("\n");
    sb.append("    indicator: ").append(toIndentedString(indicator)).append("\n");
    sb.append("    virtualComputeDesc: ").append(toIndentedString(virtualComputeDesc)).append("\n");
    sb.append("    vnfmInfo: ").append(toIndentedString(vnfmInfo)).append("\n");
    sb.append("    productInfoName: ").append(toIndentedString(productInfoName)).append("\n");
    sb.append("    modifiableAttributes: ").append(toIndentedString(modifiableAttributes)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("    df: ").append(toIndentedString(df)).append("\n");
    sb.append("    softwareVersion: ").append(toIndentedString(softwareVersion)).append("\n");
    sb.append("    configurableProperties: ").append(toIndentedString(configurableProperties)).append("\n");
    sb.append("    autoScale: ").append(toIndentedString(autoScale)).append("\n");
    sb.append("    lifecycleManagementScript: ").append(toIndentedString(lifecycleManagementScript)).append("\n");
    sb.append("    vdu: ").append(toIndentedString(vdu)).append("\n");
    sb.append("    localizationLanguage: ").append(toIndentedString(localizationLanguage)).append("\n");
    sb.append("    intVirtualLinkDesc: ").append(toIndentedString(intVirtualLinkDesc)).append("\n");
    sb.append("    productInfoDescription: ").append(toIndentedString(productInfoDescription)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    kdu: ").append(toIndentedString(kdu)).append("\n");
    sb.append("    k8sCluster: ").append(toIndentedString(k8sCluster)).append("\n");
    sb.append("    service: ").append(toIndentedString(service)).append("\n");
    sb.append("    mgmtCp: ").append(toIndentedString(mgmtCp)).append("\n");
    sb.append("    vip: ").append(toIndentedString(vip)).append("\n");
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
