package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdPrimaryidPrimarynsprofile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdPrimaryidPrimarynsprofile {

  @JsonProperty("primary-ns-profile")
  private String primaryNsProfile = null;

  public NsdPrimaryidPrimarynsprofile primaryNsProfile(String primaryNsProfile) {
    this.primaryNsProfile = primaryNsProfile;
    return this;
  }

  /**
   * Get primaryNsProfile
   * @return primaryNsProfile
   **/
  //@Schema(description = "")
  
    public String getPrimaryNsProfile() {
    return primaryNsProfile;
  }

  public void setPrimaryNsProfile(String primaryNsProfile) {
    this.primaryNsProfile = primaryNsProfile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdPrimaryidPrimarynsprofile nsdPrimaryidPrimarynsprofile = (NsdPrimaryidPrimarynsprofile) o;
    return Objects.equals(this.primaryNsProfile, nsdPrimaryidPrimarynsprofile.primaryNsProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(primaryNsProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdPrimaryidPrimarynsprofile {\n");
    
    sb.append("    primaryNsProfile: ").append(toIndentedString(primaryNsProfile)).append("\n");
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
