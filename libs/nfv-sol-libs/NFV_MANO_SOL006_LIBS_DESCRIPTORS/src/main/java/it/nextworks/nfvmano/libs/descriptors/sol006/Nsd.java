package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Nsd
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class Nsd extends SecurityParameters {

  @JsonProperty("nested-nsd-id")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> nestedNsdId = null;

  @JsonProperty("designer")
  private String designer = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("sapd")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_fk", referencedColumnName = "uuid")
  private List<NsdSapd> sapd = null;

  @JsonProperty("lifecycle-management-script")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<NsdLifecyclemanagementscript> lifecycleManagementScript = null;

  @JsonProperty("virtual-link-desc")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_fk", referencedColumnName = "uuid")
  private List<VirtualLinkDesc> virtualLinkDesc = null;

  @JsonProperty("invariant-id")
  private String invariantId = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("autoscale-rule")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> autoscaleRule = null;

  @JsonProperty("vnffgd")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_fk", referencedColumnName = "uuid")
  private List<NsdVnffgd> vnffgd = null;

  @JsonProperty("df")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_fk", referencedColumnName = "uuid")
  private List<NsdDf> df = null;

  @JsonProperty("pnfd-id")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> pnfdId = null;

  @JsonProperty("vnfd-id")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> vnfdId = null;

  @JsonProperty("id")
  @Column(unique = true)
  private String id = null;

  @JsonProperty("ns-configuration")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "ns_configuration_fk", referencedColumnName = "uuid")
  private NsConfiguration nsConfiguration = null;

  @JsonProperty("description")
  private String description = null;

  public Nsd nestedNsdId(List<String> nestedNsdId) {
    this.nestedNsdId = nestedNsdId;
    return this;
  }

  public Nsd addNestedNsdIdItem(String nestedNsdIdItem) {
    if (this.nestedNsdId == null) {
      this.nestedNsdId = new ArrayList<String>();
    }
    this.nestedNsdId.add(nestedNsdIdItem);
    return this;
  }

  /**
   * Get nestedNsdId
   * @return nestedNsdId
   **/
  //@Schema(description = "")
  
    public List<String> getNestedNsdId() {
    return nestedNsdId;
  }

  public void setNestedNsdId(List<String> nestedNsdId) {
    this.nestedNsdId = nestedNsdId;
  }

  public Nsd designer(String designer) {
    this.designer = designer;
    return this;
  }

  /**
   * Identifies the designer of the NSD.
   * @return designer
   **/
  //@Schema(description = "Identifies the designer of the NSD.")
  
    public String getDesigner() {
    return designer;
  }

  public void setDesigner(String designer) {
    this.designer = designer;
  }

  public Nsd name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Provides the human readable name of the NSD.
   * @return name
   **/
  //@Schema(description = "Provides the human readable name of the NSD.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Nsd sapd(List<NsdSapd> sapd) {
    this.sapd = sapd;
    return this;
  }

  public Nsd addSapdItem(NsdSapd sapdItem) {
    if (this.sapd == null) {
      this.sapd = new ArrayList<NsdSapd>();
    }
    this.sapd.add(sapdItem);
    return this;
  }

  /**
   * Provides the descriptor of a service access point of the network service.
   * @return sapd
   **/
  //@Schema(description = "Provides the descriptor of a service access point of the network service.")

    public List<NsdSapd> getSapd() {
    return sapd;
  }

  public void setSapd(List<NsdSapd> sapd) {
    this.sapd = sapd;
  }

  public Nsd lifecycleManagementScript(List<NsdLifecyclemanagementscript> lifecycleManagementScript) {
    this.lifecycleManagementScript = lifecycleManagementScript;
    return this;
  }

  public Nsd addLifecycleManagementScriptItem(NsdLifecyclemanagementscript lifecycleManagementScriptItem) {
    if (this.lifecycleManagementScript == null) {
      this.lifecycleManagementScript = new ArrayList<NsdLifecyclemanagementscript>();
    }
    this.lifecycleManagementScript.add(lifecycleManagementScriptItem);
    return this;
  }

  /**
   * Get lifecycleManagementScript
   * @return lifecycleManagementScript
   **/
  //@Schema(description = "")

    public List<NsdLifecyclemanagementscript> getLifecycleManagementScript() {
    return lifecycleManagementScript;
  }

  public void setLifecycleManagementScript(List<NsdLifecyclemanagementscript> lifecycleManagementScript) {
    this.lifecycleManagementScript = lifecycleManagementScript;
  }

  public Nsd virtualLinkDesc(List<VirtualLinkDesc> virtualLinkDesc) {
    this.virtualLinkDesc = virtualLinkDesc;
    return this;
  }

  public Nsd addVirtualLinkDescItem(VirtualLinkDesc virtualLinkDescItem) {
    if (this.virtualLinkDesc == null) {
      this.virtualLinkDesc = new ArrayList<VirtualLinkDesc>();
    }
    this.virtualLinkDesc.add(virtualLinkDescItem);
    return this;
  }

  /**
   * Provides the constituent VLDs.
   * @return virtualLinkDesc
   **/
  //@Schema(description = "Provides the constituent VLDs.")
  
    public List<VirtualLinkDesc> getVirtualLinkDesc() {
    return virtualLinkDesc;
  }

  public void setVirtualLinkDesc(List<VirtualLinkDesc> virtualLinkDesc) {
    this.virtualLinkDesc = virtualLinkDesc;
  }

  public Nsd invariantId(String invariantId) {
    this.invariantId = invariantId;
    return this;
  }

  /**
   * Identifies an NSD in a version independent manner. This attribute is invariant across versions of the network service descriptor.
   * @return invariantId
   **/
  //@Schema(description = "Identifies an NSD in a version independent manner. This attribute is invariant across versions of the network service descriptor.")
  
    public String getInvariantId() {
    return invariantId;
  }

  public void setInvariantId(String invariantId) {
    this.invariantId = invariantId;
  }

  public Nsd version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Identifies the version of the NSD.
   * @return version
   **/
  //@Schema(description = "Identifies the version of the NSD.")
  
    public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Nsd autoscaleRule(List<String> autoscaleRule) {
    this.autoscaleRule = autoscaleRule;
    return this;
  }

  public Nsd addAutoscaleRuleItem(String autoscaleRuleItem) {
    if (this.autoscaleRule == null) {
      this.autoscaleRule = new ArrayList<String>();
    }
    this.autoscaleRule.add(autoscaleRuleItem);
    return this;
  }

  /**
   * Get autoscaleRule
   * @return autoscaleRule
   **/
  //@Schema(description = "")
  
    public List<String> getAutoscaleRule() {
    return autoscaleRule;
  }

  public void setAutoscaleRule(List<String> autoscaleRule) {
    this.autoscaleRule = autoscaleRule;
  }

  public Nsd vnffgd(List<NsdVnffgd> vnffgd) {
    this.vnffgd = vnffgd;
    return this;
  }

  public Nsd addVnffgdItem(NsdVnffgd vnffgdItem) {
    if (this.vnffgd == null) {
      this.vnffgd = new ArrayList<NsdVnffgd>();
    }
    this.vnffgd.add(vnffgdItem);
    return this;
  }

  /**
   * Provides the descriptors of the applicable forwarding graphs.
   * @return vnffgd
   **/
  //@Schema(description = "Provides the descriptors of the applicable forwarding graphs.")

    public List<NsdVnffgd> getVnffgd() {
    return vnffgd;
  }

  public void setVnffgd(List<NsdVnffgd> vnffgd) {
    this.vnffgd = vnffgd;
  }

  public Nsd df(List<NsdDf> df) {
    this.df = df;
    return this;
  }

  public Nsd addDfItem(NsdDf dfItem) {
    if (this.df == null) {
      this.df = new ArrayList<NsdDf>();
    }
    this.df.add(dfItem);
    return this;
  }

  /**
   * Identifies a DF within the scope of an NSD.
   * @return df
   **/
  //@Schema(description = "Identifies a DF within the scope of an NSD.")

    public List<NsdDf> getDf() {
    return df;
  }

  public void setDf(List<NsdDf> df) {
    this.df = df;
  }

  public Nsd pnfdId(List<String> pnfdId) {
    this.pnfdId = pnfdId;
    return this;
  }

  public Nsd addPnfdIdItem(String pnfdIdItem) {
    if (this.pnfdId == null) {
      this.pnfdId = new ArrayList<String>();
    }
    this.pnfdId.add(pnfdIdItem);
    return this;
  }

  /**
   * Get pnfdId
   * @return pnfdId
   **/
  //@Schema(description = "")
  
    public List<String> getPnfdId() {
    return pnfdId;
  }

  public void setPnfdId(List<String> pnfdId) {
    this.pnfdId = pnfdId;
  }

  public Nsd vnfdId(List<String> vnfdId) {
    this.vnfdId = vnfdId;
    return this;
  }

  public Nsd addVnfdIdItem(String vnfdIdItem) {
    if (this.vnfdId == null) {
      this.vnfdId = new ArrayList<String>();
    }
    this.vnfdId.add(vnfdIdItem);
    return this;
  }

  /**
   * Get vnfdId
   * @return vnfdId
   **/
  //@Schema(description = "")
  
    public List<String> getVnfdId() {
    return vnfdId;
  }

  public void setVnfdId(List<String> vnfdId) {
    this.vnfdId = vnfdId;
  }

  public Nsd id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this NSD information element. It Globally uniquely identifies an instance of the NSD.
   * @return id
   **/
  //@Schema(description = "Identifier of this NSD information element. It Globally uniquely identifies an instance of the NSD.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Nsd nsConfiguration(NsConfiguration nsConfiguration) {
      this.nsConfiguration = nsConfiguration;
      return this;
  }

  public NsConfiguration getNsConfiguration() { return nsConfiguration; }

  public void setNsConfiguration(NsConfiguration nsConfiguration) { this.nsConfiguration = nsConfiguration; }

  public Nsd description(String description) {
      this.description = description;
      return this;
  }

  public String getDescription() { return description; }

  public void setDescription(String description) { this.description = description; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Nsd nsd = (Nsd) o;
    return Objects.equals(this.nestedNsdId, nsd.nestedNsdId) &&
            Objects.equals(this.designer, nsd.designer) &&
            Objects.equals(this.name, nsd.name) &&
            Objects.equals(this.sapd, nsd.sapd) &&
            Objects.equals(this.lifecycleManagementScript, nsd.lifecycleManagementScript) &&
            Objects.equals(this.virtualLinkDesc, nsd.virtualLinkDesc) &&
            Objects.equals(this.invariantId, nsd.invariantId) &&
            Objects.equals(this.version, nsd.version) &&
            Objects.equals(this.autoscaleRule, nsd.autoscaleRule) &&
            Objects.equals(this.vnffgd, nsd.vnffgd) &&
            Objects.equals(this.df, nsd.df) &&
            Objects.equals(this.pnfdId, nsd.pnfdId) &&
            Objects.equals(this.vnfdId, nsd.vnfdId) &&
            Objects.equals(this.id, nsd.id) &&
            Objects.equals(this.nsConfiguration, nsd.nsConfiguration) &&
            Objects.equals(this.description, nsd.description) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nestedNsdId, designer, name, sapd, lifecycleManagementScript, virtualLinkDesc, invariantId,
            version, autoscaleRule, vnffgd, df, pnfdId, vnfdId, id, nsConfiguration, description, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Nsd {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    nestedNsdId: ").append(toIndentedString(nestedNsdId)).append("\n");
    sb.append("    designer: ").append(toIndentedString(designer)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    sapd: ").append(toIndentedString(sapd)).append("\n");
    sb.append("    lifecycleManagementScript: ").append(toIndentedString(lifecycleManagementScript)).append("\n");
    sb.append("    virtualLinkDesc: ").append(toIndentedString(virtualLinkDesc)).append("\n");
    sb.append("    invariantId: ").append(toIndentedString(invariantId)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    autoscaleRule: ").append(toIndentedString(autoscaleRule)).append("\n");
    sb.append("    vnffgd: ").append(toIndentedString(vnffgd)).append("\n");
    sb.append("    df: ").append(toIndentedString(df)).append("\n");
    sb.append("    pnfdId: ").append(toIndentedString(pnfdId)).append("\n");
    sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nsConfiguration: ").append(toIndentedString(nsConfiguration)).append("\n");
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
