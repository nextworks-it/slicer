package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

/**
 * AllOfnsdNsinstantiationlevelVirtualLinkToLevelMappingItems
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VirtualLinkToLevelMappingItem extends LinkBitrateRequirements {

  @JsonProperty("virtual-link-profile-id")
  private String virtualLinkProfileId = null;

  public VirtualLinkToLevelMappingItem virtualLinkProfileId(String virtualLinkProfileId) {
    this.virtualLinkProfileId = virtualLinkProfileId;
    return this;
  }

  /**
   * Identifies the profile to be used for a VL involved in an NS level.
   * @return virtualLinkProfileId
   **/
  //@Schema(description = "Identifies the profile to be used for a VL involved in an NS level.")
  
    public String getVirtualLinkProfileId() {
    return virtualLinkProfileId;
  }

  public void setVirtualLinkProfileId(String virtualLinkProfileId) {
    this.virtualLinkProfileId = virtualLinkProfileId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualLinkToLevelMappingItem allOfnsdNsinstantiationlevelVirtualLinkToLevelMappingItems = (VirtualLinkToLevelMappingItem) o;
    return Objects.equals(this.virtualLinkProfileId, allOfnsdNsinstantiationlevelVirtualLinkToLevelMappingItems.virtualLinkProfileId) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLinkProfileId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllOfnsdNsinstantiationlevelVirtualLinkToLevelMappingItems {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    virtualLinkProfileId: ").append(toIndentedString(virtualLinkProfileId)).append("\n");
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
