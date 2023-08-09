package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * NsdConstituentcpdidVnf
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdConstituentcpdidVnf {

  @JsonProperty("vnf")
  @Embedded
  private NsdConstituentcpdidVnfVnf vnf = null;

  public NsdConstituentcpdidVnf vnf(NsdConstituentcpdidVnfVnf vnf) {
    this.vnf = vnf;
    return this;
  }

  /**
   * Get vnf
   * @return vnf
   **/
  //@Schema(description = "")
  

    public NsdConstituentcpdidVnfVnf getVnf() {
    return vnf;
  }

  public void setVnf(NsdConstituentcpdidVnfVnf vnf) {
    this.vnf = vnf;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdConstituentcpdidVnf nsdConstituentcpdidVnf = (NsdConstituentcpdidVnf) o;
    return Objects.equals(this.vnf, nsdConstituentcpdidVnf.vnf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnf);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentcpdidVnf {\n");
    
    sb.append("    vnf: ").append(toIndentedString(vnf)).append("\n");
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
