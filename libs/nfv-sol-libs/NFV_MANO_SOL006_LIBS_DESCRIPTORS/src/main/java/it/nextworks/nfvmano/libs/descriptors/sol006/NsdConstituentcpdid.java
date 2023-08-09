package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A reference to the descriptor of a connection point attached to one of the constituent VNFs and PNFs or to the descriptor of a NS SAP.
 */
//@Schema(description = "A reference to the descriptor of a connection point attached to one of the constituent VNFs and PNFs or to the descriptor of a NS SAP.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdConstituentcpdid {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("vnf")
  @Embedded
  private NsdConstituentcpdidVnf vnf = null;

  @JsonProperty("ns")
  @Embedded
  private NsdConstituentcpdidNs ns = null;

  @JsonProperty("pnf")
  @Embedded
  private NsdConstituentcpdidPnf pnf = null;

  public NsdConstituentcpdid vnf(NsdConstituentcpdidVnf vnf) {
    this.vnf = vnf;
    return this;
  }

  /**
   * Get vnf
   * @return vnf
   **/
  //@Schema(description = "")
  

    public NsdConstituentcpdidVnf getVnf() {
    return vnf;
  }

  public void setVnf(NsdConstituentcpdidVnf vnf) {
    this.vnf = vnf;
  }

  public NsdConstituentcpdid ns(NsdConstituentcpdidNs ns) {
    this.ns = ns;
    return this;
  }

  /**
   * Get ns
   * @return ns
   **/
  //@Schema(description = "")
  

    public NsdConstituentcpdidNs getNs() {
    return ns;
  }

  public void setNs(NsdConstituentcpdidNs ns) {
    this.ns = ns;
  }

  public NsdConstituentcpdid pnf(NsdConstituentcpdidPnf pnf) {
    this.pnf = pnf;
    return this;
  }

  /**
   * Get pnf
   * @return pnf
   **/
  //@Schema(description = "")
  

    public NsdConstituentcpdidPnf getPnf() {
    return pnf;
  }

  public void setPnf(NsdConstituentcpdidPnf pnf) {
    this.pnf = pnf;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdConstituentcpdid nsdConstituentcpdid = (NsdConstituentcpdid) o;
    return Objects.equals(this.vnf, nsdConstituentcpdid.vnf) &&
        Objects.equals(this.ns, nsdConstituentcpdid.ns) &&
        Objects.equals(this.pnf, nsdConstituentcpdid.pnf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnf, ns, pnf);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentcpdid {\n");
    
    sb.append("    vnf: ").append(toIndentedString(vnf)).append("\n");
    sb.append("    ns: ").append(toIndentedString(ns)).append("\n");
    sb.append("    pnf: ").append(toIndentedString(pnf)).append("\n");
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
