package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NsdPositiondescid
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdPositiondescid {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("cp-profile-id")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "nsd_position_desc_id_fk", referencedColumnName = "uuid")
  private List<NsdCpprofileid> cpProfileId = null;

  @JsonProperty("id")
  private String id = null;

  public NsdPositiondescid cpProfileId(List<NsdCpprofileid> cpProfileId) {
    this.cpProfileId = cpProfileId;
    return this;
  }

  public NsdPositiondescid addCpProfileIdItem(NsdCpprofileid cpProfileIdItem) {
    if (this.cpProfileId == null) {
      this.cpProfileId = new ArrayList<NsdCpprofileid>();
    }
    this.cpProfileId.add(cpProfileIdItem);
    return this;
  }

  /**
   * References the profile of a connection point to be traversed by the traffic flows matching the criteria. This shall be a connection point attached to one of the constituent VNFs and PNFs of the parent VNFFG, or a SAP of one of the constituent nested NSs of the parent VNFFG.
   * @return cpProfileId
   **/
  //@Schema(description = "References the profile of a connection point to be traversed by the traffic flows matching the criteria. This shall be a connection point attached to one of the constituent VNFs and PNFs of the parent VNFFG, or a SAP of one of the constituent nested NSs of the parent VNFFG.")

    public List<NsdCpprofileid> getCpProfileId() {
    return cpProfileId;
  }

  public void setCpProfileId(List<NsdCpprofileid> cpProfileId) {
    this.cpProfileId = cpProfileId;
  }

  public NsdPositiondescid id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this NfpPositionDesc element.
   * @return id
   **/
  //@Schema(description = "Identifier of this NfpPositionDesc element.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdPositiondescid nsdPositiondescid = (NsdPositiondescid) o;
    return Objects.equals(this.cpProfileId, nsdPositiondescid.cpProfileId) &&
        Objects.equals(this.id, nsdPositiondescid.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpProfileId, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdPositiondescid {\n");
    
    sb.append("    cpProfileId: ").append(toIndentedString(cpProfileId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
