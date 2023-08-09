package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * VirtualComputeDescSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class VirtualComputeDescSchema   {
  @JsonProperty("compute-requirements")
  private String computeRequirements = null;

  @JsonProperty("virtual-memory")
  private VnfdVirtualmemory virtualMemory = null;

  @JsonProperty("logical-node")

  private List<VnfdLogicalnode> logicalNode = null;

  @JsonProperty("virtual-cpu")
  private VnfdVirtualcpu virtualCpu = null;

  @JsonProperty("virtual-compute-desc_id")
  private String virtualComputeDescId = null;

  @JsonProperty("request-additional-capability")

  private List<VnfdRequestadditionalcapability> requestAdditionalCapability = null;

  public VirtualComputeDescSchema computeRequirements(String computeRequirements) {
    this.computeRequirements = computeRequirements;
    return this;
  }

  /**
   * Specifies compute requirements.
   * @return computeRequirements
   **/
  //@Schema(description = "Specifies compute requirements.")
  
    public String getComputeRequirements() {
    return computeRequirements;
  }

  public void setComputeRequirements(String computeRequirements) {
    this.computeRequirements = computeRequirements;
  }

  public VirtualComputeDescSchema virtualMemory(VnfdVirtualmemory virtualMemory) {
    this.virtualMemory = virtualMemory;
    return this;
  }

  /**
   * Get virtualMemory
   * @return virtualMemory
   **/
  //@Schema(description = "")
  

    public VnfdVirtualmemory getVirtualMemory() {
    return virtualMemory;
  }

  public void setVirtualMemory(VnfdVirtualmemory virtualMemory) {
    this.virtualMemory = virtualMemory;
  }

  public VirtualComputeDescSchema logicalNode(List<VnfdLogicalnode> logicalNode) {
    this.logicalNode = logicalNode;
    return this;
  }

  public VirtualComputeDescSchema addLogicalNodeItem(VnfdLogicalnode logicalNodeItem) {
    if (this.logicalNode == null) {
      this.logicalNode = new ArrayList<VnfdLogicalnode>();
    }
    this.logicalNode.add(logicalNodeItem);
    return this;
  }

  /**
   * The logical node requirements.
   * @return logicalNode
   **/
  //@Schema(description = "The logical node requirements.")

    public List<VnfdLogicalnode> getLogicalNode() {
    return logicalNode;
  }

  public void setLogicalNode(List<VnfdLogicalnode> logicalNode) {
    this.logicalNode = logicalNode;
  }

  public VirtualComputeDescSchema virtualCpu(VnfdVirtualcpu virtualCpu) {
    this.virtualCpu = virtualCpu;
    return this;
  }

  /**
   * Get virtualCpu
   * @return virtualCpu
   **/
  //@Schema(description = "")
  

    public VnfdVirtualcpu getVirtualCpu() {
    return virtualCpu;
  }

  public void setVirtualCpu(VnfdVirtualcpu virtualCpu) {
    this.virtualCpu = virtualCpu;
  }

  public VirtualComputeDescSchema virtualComputeDescId(String virtualComputeDescId) {
    this.virtualComputeDescId = virtualComputeDescId;
    return this;
  }

  /**
   * Unique identifier of this VirtualComputeDesc in the VNFD.
   * @return virtualComputeDescId
   **/
  //@Schema(description = "Unique identifier of this VirtualComputeDesc in the VNFD.")
  
    public String getVirtualComputeDescId() {
    return virtualComputeDescId;
  }

  public void setVirtualComputeDescId(String virtualComputeDescId) {
    this.virtualComputeDescId = virtualComputeDescId;
  }

  public VirtualComputeDescSchema requestAdditionalCapability(List<VnfdRequestadditionalcapability> requestAdditionalCapability) {
    this.requestAdditionalCapability = requestAdditionalCapability;
    return this;
  }

  public VirtualComputeDescSchema addRequestAdditionalCapabilityItem(VnfdRequestadditionalcapability requestAdditionalCapabilityItem) {
    if (this.requestAdditionalCapability == null) {
      this.requestAdditionalCapability = new ArrayList<VnfdRequestadditionalcapability>();
    }
    this.requestAdditionalCapability.add(requestAdditionalCapabilityItem);
    return this;
  }

  /**
   * Get requestAdditionalCapability
   * @return requestAdditionalCapability
   **/
  //@Schema(description = "")

    public List<VnfdRequestadditionalcapability> getRequestAdditionalCapability() {
    return requestAdditionalCapability;
  }

  public void setRequestAdditionalCapability(List<VnfdRequestadditionalcapability> requestAdditionalCapability) {
    this.requestAdditionalCapability = requestAdditionalCapability;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualComputeDescSchema virtualComputeDescSchema = (VirtualComputeDescSchema) o;
    return Objects.equals(this.computeRequirements, virtualComputeDescSchema.computeRequirements) &&
        Objects.equals(this.virtualMemory, virtualComputeDescSchema.virtualMemory) &&
        Objects.equals(this.logicalNode, virtualComputeDescSchema.logicalNode) &&
        Objects.equals(this.virtualCpu, virtualComputeDescSchema.virtualCpu) &&
        Objects.equals(this.virtualComputeDescId, virtualComputeDescSchema.virtualComputeDescId) &&
        Objects.equals(this.requestAdditionalCapability, virtualComputeDescSchema.requestAdditionalCapability);
  }

  @Override
  public int hashCode() {
    return Objects.hash(computeRequirements, virtualMemory, logicalNode, virtualCpu, virtualComputeDescId, requestAdditionalCapability);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualComputeDescSchema {\n");
    
    sb.append("    computeRequirements: ").append(toIndentedString(computeRequirements)).append("\n");
    sb.append("    virtualMemory: ").append(toIndentedString(virtualMemory)).append("\n");
    sb.append("    logicalNode: ").append(toIndentedString(logicalNode)).append("\n");
    sb.append("    virtualCpu: ").append(toIndentedString(virtualCpu)).append("\n");
    sb.append("    virtualComputeDescId: ").append(toIndentedString(virtualComputeDescId)).append("\n");
    sb.append("    requestAdditionalCapability: ").append(toIndentedString(requestAdditionalCapability)).append("\n");
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
