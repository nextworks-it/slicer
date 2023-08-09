package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



/**
 * NfvSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class NfvSchema   {
  @JsonProperty("pnfd")

  private List<Pnfd> pnfd = null;

  @JsonProperty("nsd")

  private List<Nsd> nsd = null;

  @JsonProperty("vnfd")

  private List<Vnfd> vnfd = null;

  public NfvSchema pnfd(List<Pnfd> pnfd) {
    this.pnfd = pnfd;
    return this;
  }

  public NfvSchema addPnfdItem(Pnfd pnfdItem) {
    if (this.pnfd == null) {
      this.pnfd = new ArrayList<Pnfd>();
    }
    this.pnfd.add(pnfdItem);
    return this;
  }

  /**
   * The Pnfd information element is a deployment template        enabling on-boarding PNFs and referencing them from an        NSD. It focuses on connectivity aspects only.
   * @return pnfd
   **/

    public List<Pnfd> getPnfd() {
    return pnfd;
  }

  public void setPnfd(List<Pnfd> pnfd) {
    this.pnfd = pnfd;
  }

  public NfvSchema nsd(List<Nsd> nsd) {
    this.nsd = nsd;
    return this;
  }

  public NfvSchema addNsdItem(Nsd nsdItem) {
    if (this.nsd == null) {
      this.nsd = new ArrayList<Nsd>();
    }
    this.nsd.add(nsdItem);
    return this;
  }

  /**
   * The NSD information element is a deployment template whose instances are used by the NFVO for the lifecycle management of NSs.
   * @return nsd
   **/

    public List<Nsd> getNsd() {
    return nsd;
  }

  public void setNsd(List<Nsd> nsd) {
    this.nsd = nsd;
  }

  public NfvSchema vnfd(List<Vnfd> vnfd) {
    this.vnfd = vnfd;
    return this;
  }

  public NfvSchema addVnfdItem(Vnfd vnfdItem) {
    if (this.vnfd == null) {
      this.vnfd = new ArrayList<Vnfd>();
    }
    this.vnfd.add(vnfdItem);
    return this;
  }

  /**
   * A VNF Descriptor (VNFD) is a deployment template which describes a VNF in terms of deployment and operational behaviour requirements. It also contains connectivity, interface and virtualised resource requirements
   * @return vnfd
   **/

    public List<Vnfd> getVnfd() {
    return vnfd;
  }

  public void setVnfd(List<Vnfd> vnfd) {
    this.vnfd = vnfd;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfvSchema nfvSchema = (NfvSchema) o;
    return Objects.equals(this.pnfd, nfvSchema.pnfd) &&
        Objects.equals(this.nsd, nfvSchema.nsd) &&
        Objects.equals(this.vnfd, nfvSchema.vnfd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfd, nsd, vnfd);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfvSchema {\n");
    
    sb.append("    pnfd: ").append(toIndentedString(pnfd)).append("\n");
    sb.append("    nsd: ").append(toIndentedString(nsd)).append("\n");
    sb.append("    vnfd: ").append(toIndentedString(vnfd)).append("\n");
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
