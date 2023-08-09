package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


/**
 * NsdConstituentcpdidPnf
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdConstituentcpdidPnf {

  @JsonProperty("pnf")
  @Embedded
  private NsdConstituentcpdidPnfPnf pnf = null;

  public NsdConstituentcpdidPnf pnf(NsdConstituentcpdidPnfPnf pnf) {
    this.pnf = pnf;
    return this;
  }

  /**
   * Get pnf
   * @return pnf
   **/
  //@Schema(description = "")
  

    public NsdConstituentcpdidPnfPnf getPnf() {
    return pnf;
  }

  public void setPnf(NsdConstituentcpdidPnfPnf pnf) {
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
    NsdConstituentcpdidPnf nsdConstituentcpdidPnf = (NsdConstituentcpdidPnf) o;
    return Objects.equals(this.pnf, nsdConstituentcpdidPnf.pnf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnf);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentcpdidPnf {\n");
    
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
