package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * NsdConstituentbaseelementidPnfprofilePnfprofile
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class NsdConstituentPnfProfile {

  @JsonProperty("pnf-profile-id")
  private String pnfProfileId = null;

  public NsdConstituentPnfProfile pnfProfileId(String pnfProfileId) {
    this.pnfProfileId = pnfProfileId;
    return this;
  }

  /**
   * Get pnfProfileId
   * @return pnfProfileId
   **/
  //@Schema(description = "")
  
    public String getPnfProfileId() {
    return pnfProfileId;
  }

  public void setPnfProfileId(String pnfProfileId) {
    this.pnfProfileId = pnfProfileId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdConstituentPnfProfile nsdConstituentbaseelementidPnfprofilePnfprofile = (NsdConstituentPnfProfile) o;
    return Objects.equals(this.pnfProfileId, nsdConstituentbaseelementidPnfprofilePnfprofile.pnfProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfProfileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdConstituentbaseelementidPnfprofilePnfprofile {\n");
    
    sb.append("    pnfProfileId: ").append(toIndentedString(pnfProfileId)).append("\n");
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
