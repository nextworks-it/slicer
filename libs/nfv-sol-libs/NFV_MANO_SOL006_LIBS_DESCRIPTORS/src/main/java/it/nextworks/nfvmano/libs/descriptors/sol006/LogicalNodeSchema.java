package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



/**
 * LogicalNodeSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class LogicalNodeSchema   {
  @JsonProperty("requirement-detail")

  private List<VnfdVdustoragerequirements> requirementDetail = null;

  @JsonProperty("logical-node_id")
  private String logicalNodeId = null;

  public LogicalNodeSchema requirementDetail(List<VnfdVdustoragerequirements> requirementDetail) {
    this.requirementDetail = requirementDetail;
    return this;
  }

  public LogicalNodeSchema addRequirementDetailItem(VnfdVdustoragerequirements requirementDetailItem) {
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

  public LogicalNodeSchema logicalNodeId(String logicalNodeId) {
    this.logicalNodeId = logicalNodeId;
    return this;
  }

  /**
   * Identifies this set of logical node requirements.
   * @return logicalNodeId
   **/
  //@Schema(description = "Identifies this set of logical node requirements.")
  
    public String getLogicalNodeId() {
    return logicalNodeId;
  }

  public void setLogicalNodeId(String logicalNodeId) {
    this.logicalNodeId = logicalNodeId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogicalNodeSchema logicalNodeSchema = (LogicalNodeSchema) o;
    return Objects.equals(this.requirementDetail, logicalNodeSchema.requirementDetail) &&
        Objects.equals(this.logicalNodeId, logicalNodeSchema.logicalNodeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requirementDetail, logicalNodeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogicalNodeSchema {\n");
    
    sb.append("    requirementDetail: ").append(toIndentedString(requirementDetail)).append("\n");
    sb.append("    logicalNodeId: ").append(toIndentedString(logicalNodeId)).append("\n");
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
