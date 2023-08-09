package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdSecondaryidSecondarynsprofile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdSecondaryidSecondarynsprofile {

  @JsonProperty("secondary-ns-profile")
  private String secondaryNsProfile = null;

  public NsdSecondaryidSecondarynsprofile secondaryNsProfile(String secondaryNsProfile) {
    this.secondaryNsProfile = secondaryNsProfile;
    return this;
  }

  /**
   * Get secondaryNsProfile
   * @return secondaryNsProfile
   **/
  //@Schema(description = "")
  
    public String getSecondaryNsProfile() {
    return secondaryNsProfile;
  }

  public void setSecondaryNsProfile(String secondaryNsProfile) {
    this.secondaryNsProfile = secondaryNsProfile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdSecondaryidSecondarynsprofile nsdSecondaryidSecondarynsprofile = (NsdSecondaryidSecondarynsprofile) o;
    return Objects.equals(this.secondaryNsProfile, nsdSecondaryidSecondarynsprofile.secondaryNsProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(secondaryNsProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdSecondaryidSecondarynsprofile {\n");
    
    sb.append("    secondaryNsProfile: ").append(toIndentedString(secondaryNsProfile)).append("\n");
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
