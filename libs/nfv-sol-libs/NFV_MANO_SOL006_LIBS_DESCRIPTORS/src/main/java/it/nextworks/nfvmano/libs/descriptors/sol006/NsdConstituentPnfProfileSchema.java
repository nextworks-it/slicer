package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * NsdConstituentbaseelementidPnfprofile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdConstituentPnfProfileSchema {

  @JsonProperty("pnf-profile")
  @Embedded
  private NsdConstituentPnfProfile pnfProfile = null;

  public NsdConstituentPnfProfileSchema pnfProfile(NsdConstituentPnfProfile pnfProfile) {
    this.pnfProfile = pnfProfile;
    return this;
  }

  /**
   * Get pnfProfile
   * @return pnfProfile
   **/
  //@Schema(description = "")
  

    public NsdConstituentPnfProfile getPnfProfile() {
    return pnfProfile;
  }

  public void setPnfProfile(NsdConstituentPnfProfile pnfProfile) {
    this.pnfProfile = pnfProfile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdConstituentPnfProfileSchema nsdConstituentbaseelementidPnfprofile = (NsdConstituentPnfProfileSchema) o;
    return Objects.equals(this.pnfProfile, nsdConstituentbaseelementidPnfprofile.pnfProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentbaseelementidPnfprofile {\n");
    
    sb.append("    pnfProfile: ").append(toIndentedString(pnfProfile)).append("\n");
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
