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
 * NsdVnffgd
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdVnffgd {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("cpd-pool")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_vnf_fgd_fk", referencedColumnName = "uuid")
  private List<NsdCpdpool> cpdPool = null;

  @JsonProperty("virtual-link-profile-id")
  private String virtualLinkProfileId = null;

  @JsonProperty("vnf-profile-id")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> vnfProfileId = null;

  @JsonProperty("nfpd")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_vnf_fgd_fk", referencedColumnName = "uuid")
  private List<NsdNfpd> nfpd = null;

  @JsonProperty("nested-ns-profile-id")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> nestedNsProfileId = null;

  @JsonProperty("pnf-profile-id")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> pnfProfileId = null;

  @JsonProperty("id")
  private String id = null;

  public NsdVnffgd cpdPool(List<NsdCpdpool> cpdPool) {
    this.cpdPool = cpdPool;
    return this;
  }

  public NsdVnffgd addCpdPoolItem(NsdCpdpool cpdPoolItem) {
    if (this.cpdPool == null) {
      this.cpdPool = new ArrayList<NsdCpdpool>();
    }
    this.cpdPool.add(cpdPoolItem);
    return this;
  }

  /**
   * Describes a pool of descriptors of connection points attached to one of the constituent VNFs and PNFs and/or one of the SAPs of the parent NS or of a nested NS.
   * @return cpdPool
   **/
  //@Schema(description = "Describes a pool of descriptors of connection points attached to one of the constituent VNFs and PNFs and/or one of the SAPs of the parent NS or of a nested NS.")

    public List<NsdCpdpool> getCpdPool() {
    return cpdPool;
  }

  public void setCpdPool(List<NsdCpdpool> cpdPool) {
    this.cpdPool = cpdPool;
  }

  public NsdVnffgd virtualLinkProfileId(String virtualLinkProfileId) {
    this.virtualLinkProfileId = virtualLinkProfileId;
    return this;
  }

  /**
   * References the Virtual Link Profile of a constituent   VL.
   * @return virtualLinkProfileId
   **/
  //@Schema(description = "References the Virtual Link Profile of a constituent   VL.")
  
    public String getVirtualLinkProfileId() {
    return virtualLinkProfileId;
  }

  public void setVirtualLinkProfileId(String virtualLinkProfileId) {
    this.virtualLinkProfileId = virtualLinkProfileId;
  }

  public NsdVnffgd vnfProfileId(List<String> vnfProfileId) {
    this.vnfProfileId = vnfProfileId;
    return this;
  }

  public NsdVnffgd addVnfProfileIdItem(String vnfProfileIdItem) {
    if (this.vnfProfileId == null) {
      this.vnfProfileId = new ArrayList<String>();
    }
    this.vnfProfileId.add(vnfProfileIdItem);
    return this;
  }

  /**
   * Get vnfProfileId
   * @return vnfProfileId
   **/
  //@Schema(description = "")
  
    public List<String> getVnfProfileId() {
    return vnfProfileId;
  }

  public void setVnfProfileId(List<String> vnfProfileId) {
    this.vnfProfileId = vnfProfileId;
  }

  public NsdVnffgd nfpd(List<NsdNfpd> nfpd) {
    this.nfpd = nfpd;
    return this;
  }

  public NsdVnffgd addNfpdItem(NsdNfpd nfpdItem) {
    if (this.nfpd == null) {
      this.nfpd = new ArrayList<NsdNfpd>();
    }
    this.nfpd.add(nfpdItem);
    return this;
  }

  /**
   * The network forwarding path associated to the VNFFG.
   * @return nfpd
   **/
  //@Schema(description = "The network forwarding path associated to the VNFFG.")

    public List<NsdNfpd> getNfpd() {
    return nfpd;
  }

  public void setNfpd(List<NsdNfpd> nfpd) {
    this.nfpd = nfpd;
  }

  public NsdVnffgd nestedNsProfileId(List<String> nestedNsProfileId) {
    this.nestedNsProfileId = nestedNsProfileId;
    return this;
  }

  public NsdVnffgd addNestedNsProfileIdItem(String nestedNsProfileIdItem) {
    if (this.nestedNsProfileId == null) {
      this.nestedNsProfileId = new ArrayList<String>();
    }
    this.nestedNsProfileId.add(nestedNsProfileIdItem);
    return this;
  }

  /**
   * Get nestedNsProfileId
   * @return nestedNsProfileId
   **/
  //@Schema(description = "")
  
    public List<String> getNestedNsProfileId() {
    return nestedNsProfileId;
  }

  public void setNestedNsProfileId(List<String> nestedNsProfileId) {
    this.nestedNsProfileId = nestedNsProfileId;
  }

  public NsdVnffgd pnfProfileId(List<String> pnfProfileId) {
    this.pnfProfileId = pnfProfileId;
    return this;
  }

  public NsdVnffgd addPnfProfileIdItem(String pnfProfileIdItem) {
    if (this.pnfProfileId == null) {
      this.pnfProfileId = new ArrayList<String>();
    }
    this.pnfProfileId.add(pnfProfileIdItem);
    return this;
  }

  /**
   * Get pnfProfileId
   * @return pnfProfileId
   **/
  //@Schema(description = "")
  
    public List<String> getPnfProfileId() {
    return pnfProfileId;
  }

  public void setPnfProfileId(List<String> pnfProfileId) {
    this.pnfProfileId = pnfProfileId;
  }

  public NsdVnffgd id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this Vnffgd information element. It uniquely identifies a VNFFGD.
   * @return id
   **/
  //@Schema(description = "Identifier of this Vnffgd information element. It uniquely identifies a VNFFGD.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdVnffgd nsdVnffgd = (NsdVnffgd) o;
    return Objects.equals(this.cpdPool, nsdVnffgd.cpdPool) &&
        Objects.equals(this.virtualLinkProfileId, nsdVnffgd.virtualLinkProfileId) &&
        Objects.equals(this.vnfProfileId, nsdVnffgd.vnfProfileId) &&
        Objects.equals(this.nfpd, nsdVnffgd.nfpd) &&
        Objects.equals(this.nestedNsProfileId, nsdVnffgd.nestedNsProfileId) &&
        Objects.equals(this.pnfProfileId, nsdVnffgd.pnfProfileId) &&
        Objects.equals(this.id, nsdVnffgd.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdPool, virtualLinkProfileId, vnfProfileId, nfpd, nestedNsProfileId, pnfProfileId, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdVnffgd {\n");
    
    sb.append("    cpdPool: ").append(toIndentedString(cpdPool)).append("\n");
    sb.append("    virtualLinkProfileId: ").append(toIndentedString(virtualLinkProfileId)).append("\n");
    sb.append("    vnfProfileId: ").append(toIndentedString(vnfProfileId)).append("\n");
    sb.append("    nfpd: ").append(toIndentedString(nfpd)).append("\n");
    sb.append("    nestedNsProfileId: ").append(toIndentedString(nestedNsProfileId)).append("\n");
    sb.append("    pnfProfileId: ").append(toIndentedString(pnfProfileId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
