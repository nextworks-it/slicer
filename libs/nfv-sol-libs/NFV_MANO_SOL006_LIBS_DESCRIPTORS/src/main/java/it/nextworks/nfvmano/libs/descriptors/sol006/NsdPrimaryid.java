package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * References a VnfProfile or NsProfile.
 */
//@Schema(description = "References a VnfProfile or NsProfile.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdPrimaryid {

  @JsonProperty("primary-ns-profile")
  @Embedded
  private NsdPrimaryidPrimarynsprofile primaryNsProfile = null;

  @JsonProperty("primary-vnf-profile")
  @Embedded
  private NsdPrimaryidPrimaryvnfprofile primaryVnfProfile = null;

  public NsdPrimaryid primaryNsProfile(NsdPrimaryidPrimarynsprofile primaryNsProfile) {
    this.primaryNsProfile = primaryNsProfile;
    return this;
  }

  /**
   * Get primaryNsProfile
   * @return primaryNsProfile
   **/
  //@Schema(description = "")
  

    public NsdPrimaryidPrimarynsprofile getPrimaryNsProfile() {
    return primaryNsProfile;
  }

  public void setPrimaryNsProfile(NsdPrimaryidPrimarynsprofile primaryNsProfile) {
    this.primaryNsProfile = primaryNsProfile;
  }

  public NsdPrimaryid primaryVnfProfile(NsdPrimaryidPrimaryvnfprofile primaryVnfProfile) {
    this.primaryVnfProfile = primaryVnfProfile;
    return this;
  }

  /**
   * Get primaryVnfProfile
   * @return primaryVnfProfile
   **/
  //@Schema(description = "")
  

    public NsdPrimaryidPrimaryvnfprofile getPrimaryVnfProfile() {
    return primaryVnfProfile;
  }

  public void setPrimaryVnfProfile(NsdPrimaryidPrimaryvnfprofile primaryVnfProfile) {
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
    NsdPrimaryid nsdPrimaryid = (NsdPrimaryid) o;
    return Objects.equals(this.primaryNsProfile, nsdPrimaryid.primaryNsProfile) &&
        Objects.equals(this.primaryVnfProfile, nsdPrimaryid.primaryVnfProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(primaryNsProfile, primaryVnfProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdPrimaryid {\n");
    
    sb.append("    primaryNsProfile: ").append(toIndentedString(primaryNsProfile)).append("\n");
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
