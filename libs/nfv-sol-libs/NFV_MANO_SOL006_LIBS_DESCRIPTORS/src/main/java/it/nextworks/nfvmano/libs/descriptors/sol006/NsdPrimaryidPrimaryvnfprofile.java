package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdPrimaryidPrimaryvnfprofile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdPrimaryidPrimaryvnfprofile {

  @JsonProperty("primary-vnf-profile")
  private String primaryVnfProfile = null;

  public NsdPrimaryidPrimaryvnfprofile primaryVnfProfile(String primaryVnfProfile) {
    this.primaryVnfProfile = primaryVnfProfile;
    return this;
  }

  /**
   * Get primaryVnfProfile
   * @return primaryVnfProfile
   **/
  //@Schema(description = "")
  
    public String getPrimaryVnfProfile() {
    return primaryVnfProfile;
  }

  public void setPrimaryVnfProfile(String primaryVnfProfile) {
    this.primaryVnfProfile = primaryVnfProfile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdPrimaryidPrimaryvnfprofile nsdPrimaryidPrimaryvnfprofile = (NsdPrimaryidPrimaryvnfprofile) o;
    return Objects.equals(this.primaryVnfProfile, nsdPrimaryidPrimaryvnfprofile.primaryVnfProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(primaryVnfProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdPrimaryidPrimaryvnfprofile {\n");
    
    sb.append("    primaryVnfProfile: ").append(toIndentedString(primaryVnfProfile)).append("\n");
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
