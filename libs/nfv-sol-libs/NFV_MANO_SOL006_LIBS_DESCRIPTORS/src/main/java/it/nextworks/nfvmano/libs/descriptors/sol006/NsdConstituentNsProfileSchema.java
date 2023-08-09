package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * NsdConstituentbaseelementidNsprofile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdConstituentNsProfileSchema {

  @JsonProperty("ns-profile")
  @Embedded
  private NsdConstituentNsProfile nsProfile = null;

  public NsdConstituentNsProfileSchema nsProfile(NsdConstituentNsProfile nsProfile) {
    this.nsProfile = nsProfile;
    return this;
  }

  /**
   * Get nsProfile
   * @return nsProfile
   **/
  //@Schema(description = "")
  

    public NsdConstituentNsProfile getNsProfile() {
    return nsProfile;
  }

  public void setNsProfile(NsdConstituentNsProfile nsProfile) {
    this.nsProfile = nsProfile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdConstituentNsProfileSchema nsdConstituentbaseelementidNsprofile = (NsdConstituentNsProfileSchema) o;
    return Objects.equals(this.nsProfile, nsdConstituentbaseelementidNsprofile.nsProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nsProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentbaseelementidNsprofile {\n");
    
    sb.append("    nsProfile: ").append(toIndentedString(nsProfile)).append("\n");
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
