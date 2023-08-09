package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Reference to the profile of an NS constituent.
 */
//@Schema(description = "Reference to the profile of an NS constituent.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdConstituentBaseElementId {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("ns-profile")
  @Embedded
  private NsdConstituentNsProfileSchema nsProfile = null;

  @JsonProperty("vnf-profile")
  @Embedded
  private NsdConstituentVnfProfileSchema vnfProfile = null;

  @JsonProperty("pnf-profile")
  @Embedded
  private NsdConstituentPnfProfileSchema pnfProfile = null;

  public NsdConstituentBaseElementId nsProfile(NsdConstituentNsProfileSchema nsProfile) {
    this.nsProfile = nsProfile;
    return this;
  }

  /**
   * Get nsProfile
   * @return nsProfile
   **/
  //@Schema(description = "")
  

    public NsdConstituentNsProfileSchema getNsProfile() {
    return nsProfile;
  }

  public void setNsProfile(NsdConstituentNsProfileSchema nsProfile) {
    this.nsProfile = nsProfile;
  }

  public NsdConstituentBaseElementId vnfProfile(NsdConstituentVnfProfileSchema vnfProfile) {
    this.vnfProfile = vnfProfile;
    return this;
  }

  /**
   * Get vnfProfile
   * @return vnfProfile
   **/
  //@Schema(description = "")
  

    public NsdConstituentVnfProfileSchema getVnfProfile() {
    return vnfProfile;
  }

  public void setVnfProfile(NsdConstituentVnfProfileSchema vnfProfile) {
    this.vnfProfile = vnfProfile;
  }

  public NsdConstituentBaseElementId pnfProfile(NsdConstituentPnfProfileSchema pnfProfile) {
    this.pnfProfile = pnfProfile;
    return this;
  }

  /**
   * Get pnfProfile
   * @return pnfProfile
   **/
  //@Schema(description = "")
  

    public NsdConstituentPnfProfileSchema getPnfProfile() {
    return pnfProfile;
  }

  public void setPnfProfile(NsdConstituentPnfProfileSchema pnfProfile) {
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
    NsdConstituentBaseElementId nsdConstituentbaseelementid = (NsdConstituentBaseElementId) o;
    return Objects.equals(this.nsProfile, nsdConstituentbaseelementid.nsProfile) &&
        Objects.equals(this.vnfProfile, nsdConstituentbaseelementid.vnfProfile) &&
        Objects.equals(this.pnfProfile, nsdConstituentbaseelementid.pnfProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nsProfile, vnfProfile, pnfProfile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentbaseelementid {\n");
    
    sb.append("    nsProfile: ").append(toIndentedString(nsProfile)).append("\n");
    sb.append("    vnfProfile: ").append(toIndentedString(vnfProfile)).append("\n");
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
