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
 * VnfdLogicalnode
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdLogicalnode {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("requirement-detail")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<VnfdVdustoragerequirements> requirementDetail = null;

  @JsonProperty("id")
  private String id = null;

  public VnfdLogicalnode requirementDetail(List<VnfdVdustoragerequirements> requirementDetail) {
    this.requirementDetail = requirementDetail;
    return this;
  }

  public VnfdLogicalnode addRequirementDetailItem(VnfdVdustoragerequirements requirementDetailItem) {
    if (this.requirementDetail == null) {
      this.requirementDetail = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.requirementDetail.add(requirementDetailItem);
    return this;
  }

  /**
   * The logical node-level compute, memory and I/O requirements. An array of key-value pairs that articulate the deployment requirements.  This could include the number of CPU cores on this logical node, a memory configuration specific to a logical node (e.g. such as available in the Linux kernel via the libnuma library) or a requirement related to the association of an I/O device with the logical node.
   * @return requirementDetail
   **/
  //@Schema(description = "The logical node-level compute, memory and I/O requirements. An array of key-value pairs that articulate the deployment requirements.  This could include the number of CPU cores on this logical node, a memory configuration specific to a logical node (e.g. such as available in the Linux kernel via the libnuma library) or a requirement related to the association of an I/O device with the logical node.")

    public List<VnfdVdustoragerequirements> getRequirementDetail() {
    return requirementDetail;
  }

  public void setRequirementDetail(List<VnfdVdustoragerequirements> requirementDetail) {
    this.requirementDetail = requirementDetail;
  }

  public VnfdLogicalnode id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifies this set of logical node requirements.
   * @return id
   **/
  //@Schema(description = "Identifies this set of logical node requirements.")
  
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
    VnfdLogicalnode vnfdLogicalnode = (VnfdLogicalnode) o;
    return Objects.equals(this.requirementDetail, vnfdLogicalnode.requirementDetail) &&
        Objects.equals(this.id, vnfdLogicalnode.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requirementDetail, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdLogicalnode {\n");
    
    sb.append("    requirementDetail: ").append(toIndentedString(requirementDetail)).append("\n");
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
