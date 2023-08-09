package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * VirtualLinkConnectivitySchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VirtualLinkConnectivitySchema {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("constituent-cpd-id")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<NsdConstituentcpdid2> constituentCpdId = null;

  @JsonProperty("virtual-link-profile-id")
  private String virtualLinkProfileId = null;

  public VirtualLinkConnectivitySchema constituentCpdId(List<NsdConstituentcpdid2> constituentCpdId) {
    this.constituentCpdId = constituentCpdId;
    return this;
  }

  public VirtualLinkConnectivitySchema addConstituentCpdIdItem(NsdConstituentcpdid2 constituentCpdIdItem) {
    if (this.constituentCpdId == null) {
      this.constituentCpdId = new ArrayList<NsdConstituentcpdid2>();
    }
    this.constituentCpdId.add(constituentCpdIdItem);
    return this;
  }

  /**
   * Describes a connection point on a VNF/PNF or a SAP which connects to virtual links instantiated from the profile identified in the virtualLinkProfileId attribute.
   * @return constituentCpdId
   **/
  //@Schema(description = "Describes a connection point on a VNF/PNF or a SAP which connects to virtual links instantiated from the profile identified in the virtualLinkProfileId attribute.")

    public List<NsdConstituentcpdid2> getConstituentCpdId() {
    return constituentCpdId;
  }

  public void setConstituentCpdId(List<NsdConstituentcpdid2> constituentCpdId) {
    this.constituentCpdId = constituentCpdId;
  }

  public VirtualLinkConnectivitySchema virtualLinkProfileId(String virtualLinkProfileId) {
    this.virtualLinkProfileId = virtualLinkProfileId;
    return this;
  }

  /**
   * Reference an NS VL profile.
   * @return virtualLinkProfileId
   **/
  //@Schema(description = "Reference an NS VL profile.")
  
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
    VirtualLinkConnectivitySchema virtualLinkConnectivitySchema = (VirtualLinkConnectivitySchema) o;
    return Objects.equals(this.constituentCpdId, virtualLinkConnectivitySchema.constituentCpdId) &&
        Objects.equals(this.virtualLinkProfileId, virtualLinkConnectivitySchema.virtualLinkProfileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(constituentCpdId, virtualLinkProfileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualLinkConnectivitySchema {\n");
    
    sb.append("    constituentCpdId: ").append(toIndentedString(constituentCpdId)).append("\n");
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
