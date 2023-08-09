package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * VnffgdSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class VnffgdSchema   {
  @JsonProperty("cpd-pool")

  private List<NsdCpdpool> cpdPool = null;

  @JsonProperty("virtual-link-profile-id")
  private String virtualLinkProfileId = null;

  @JsonProperty("vnf-profile-id")

  private List<String> vnfProfileId = null;

  @JsonProperty("nfpd")

  private List<NsdNfpd> nfpd = null;

  @JsonProperty("vnffgd_id")
  private String vnffgdId = null;

  @JsonProperty("nested-ns-profile-id")

  private List<String> nestedNsProfileId = null;

  @JsonProperty("pnf-profile-id")

  private List<String> pnfProfileId = null;

  public VnffgdSchema cpdPool(List<NsdCpdpool> cpdPool) {
    this.cpdPool = cpdPool;
    return this;
  }

  public VnffgdSchema addCpdPoolItem(NsdCpdpool cpdPoolItem) {
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

  public VnffgdSchema virtualLinkProfileId(String virtualLinkProfileId) {
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

  public VnffgdSchema vnfProfileId(List<String> vnfProfileId) {
    this.vnfProfileId = vnfProfileId;
    return this;
  }

  public VnffgdSchema addVnfProfileIdItem(String vnfProfileIdItem) {
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

  public VnffgdSchema nfpd(List<NsdNfpd> nfpd) {
    this.nfpd = nfpd;
    return this;
  }

  public VnffgdSchema addNfpdItem(NsdNfpd nfpdItem) {
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

  public VnffgdSchema vnffgdId(String vnffgdId) {
    this.vnffgdId = vnffgdId;
    return this;
  }

  /**
   * Identifier of this Vnffgd information element. It uniquely identifies a VNFFGD.
   * @return vnffgdId
   **/
  //@Schema(description = "Identifier of this Vnffgd information element. It uniquely identifies a VNFFGD.")
  
    public String getVnffgdId() {
    return vnffgdId;
  }

  public void setVnffgdId(String vnffgdId) {
    this.vnffgdId = vnffgdId;
  }

  public VnffgdSchema nestedNsProfileId(List<String> nestedNsProfileId) {
    this.nestedNsProfileId = nestedNsProfileId;
    return this;
  }

  public VnffgdSchema addNestedNsProfileIdItem(String nestedNsProfileIdItem) {
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

  public VnffgdSchema pnfProfileId(List<String> pnfProfileId) {
    this.pnfProfileId = pnfProfileId;
    return this;
  }

  public VnffgdSchema addPnfProfileIdItem(String pnfProfileIdItem) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnffgdSchema vnffgdSchema = (VnffgdSchema) o;
    return Objects.equals(this.cpdPool, vnffgdSchema.cpdPool) &&
        Objects.equals(this.virtualLinkProfileId, vnffgdSchema.virtualLinkProfileId) &&
        Objects.equals(this.vnfProfileId, vnffgdSchema.vnfProfileId) &&
        Objects.equals(this.nfpd, vnffgdSchema.nfpd) &&
        Objects.equals(this.vnffgdId, vnffgdSchema.vnffgdId) &&
        Objects.equals(this.nestedNsProfileId, vnffgdSchema.nestedNsProfileId) &&
        Objects.equals(this.pnfProfileId, vnffgdSchema.pnfProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdPool, virtualLinkProfileId, vnfProfileId, nfpd, vnffgdId, nestedNsProfileId, pnfProfileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnffgdSchema {\n");
    
    sb.append("    cpdPool: ").append(toIndentedString(cpdPool)).append("\n");
    sb.append("    virtualLinkProfileId: ").append(toIndentedString(virtualLinkProfileId)).append("\n");
    sb.append("    vnfProfileId: ").append(toIndentedString(vnfProfileId)).append("\n");
    sb.append("    nfpd: ").append(toIndentedString(nfpd)).append("\n");
    sb.append("    vnffgdId: ").append(toIndentedString(vnffgdId)).append("\n");
    sb.append("    nestedNsProfileId: ").append(toIndentedString(nestedNsProfileId)).append("\n");
    sb.append("    pnfProfileId: ").append(toIndentedString(pnfProfileId)).append("\n");
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
