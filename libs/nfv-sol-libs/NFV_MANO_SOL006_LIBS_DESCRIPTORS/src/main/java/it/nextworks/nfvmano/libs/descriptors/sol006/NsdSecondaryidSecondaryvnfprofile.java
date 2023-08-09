package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdSecondaryidSecondaryvnfprofile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdSecondaryidSecondaryvnfprofile {

  @JsonProperty("secondary-vnf-profile")
  private String secondaryVnfProfile = null;

  public NsdSecondaryidSecondaryvnfprofile secondaryVnfProfile(String secondaryVnfProfile) {
    this.secondaryVnfProfile = secondaryVnfProfile;
    return this;
  }

  /**
   * Get secondaryVnfProfile
   * @return secondaryVnfProfile
   **/
  //@Schema(description = "")
  
    public String getSecondaryVnfProfile() {
    return secondaryVnfProfile;
  }

  public void setSecondaryVnfProfile(String secondaryVnfProfile) {
    this.secondaryVnfProfile = secondaryVnfProfile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdSecondaryidSecondaryvnfprofile nsdSecondaryidSecondaryvnfprofile = (NsdSecondaryidSecondaryvnfprofile) o;
    return Objects.equals(this.secondaryVnfProfile, nsdSecondaryidSecondaryvnfprofile.secondaryVnfProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(secondaryVnfProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdSecondaryidSecondaryvnfprofile {\n");
    
    sb.append("    secondaryVnfProfile: ").append(toIndentedString(secondaryVnfProfile)).append("\n");
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
