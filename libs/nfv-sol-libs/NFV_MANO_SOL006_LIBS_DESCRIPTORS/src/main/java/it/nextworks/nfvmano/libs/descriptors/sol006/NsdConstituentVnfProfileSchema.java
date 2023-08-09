package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


/**
 * NsdConstituentbaseelementidVnfprofile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdConstituentVnfProfileSchema {

  @JsonProperty("vnf-profile")
  @Embedded
  private NsdConstituentVnfProfile vnfProfile = null;

  public NsdConstituentVnfProfileSchema vnfProfile(NsdConstituentVnfProfile vnfProfile) {
    this.vnfProfile = vnfProfile;
    return this;
  }

  /**
   * Get vnfProfile
   * @return vnfProfile
   **/
  //@Schema(description = "")
  

    public NsdConstituentVnfProfile getVnfProfile() {
    return vnfProfile;
  }

  public void setVnfProfile(NsdConstituentVnfProfile vnfProfile) {
    this.vnfProfile = vnfProfile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdConstituentVnfProfileSchema nsdConstituentbaseelementidVnfprofile = (NsdConstituentVnfProfileSchema) o;
    return Objects.equals(this.vnfProfile, nsdConstituentbaseelementidVnfprofile.vnfProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnfProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentbaseelementidVnfprofile {\n");
    
    sb.append("    vnfProfile: ").append(toIndentedString(vnfProfile)).append("\n");
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
