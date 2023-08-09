package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * VnfdVirtualcomputedesc
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdVirtualcomputedesc {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("compute-requirements")
  private String computeRequirements = null;

  @JsonProperty("virtual-memory")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_virtual_memory_fk", referencedColumnName = "uuid")
  private VnfdVirtualmemory virtualMemory = null;

  @JsonProperty("logical-node")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_virtual_compute_desc_fk", referencedColumnName = "uuid")
  private List<VnfdLogicalnode> logicalNode = null;

  @JsonProperty("virtual-cpu")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_virtual_cpu_fk", referencedColumnName = "uuid")
  private VnfdVirtualcpu virtualCpu = null;

  @JsonProperty("request-additional-capability")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "vnfd_virtual_compute_desc_fk", referencedColumnName = "uuid")
  private List<VnfdRequestadditionalcapability> requestAdditionalCapability = null;

  @JsonProperty("id")
  private String id = null;

  public VnfdVirtualcomputedesc computeRequirements(String computeRequirements) {
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

  public VnfdVirtualcomputedesc virtualMemory(VnfdVirtualmemory virtualMemory) {
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

  public VnfdVirtualcomputedesc logicalNode(List<VnfdLogicalnode> logicalNode) {
    this.logicalNode = logicalNode;
    return this;
  }

  public VnfdVirtualcomputedesc addLogicalNodeItem(VnfdLogicalnode logicalNodeItem) {
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

  public VnfdVirtualcomputedesc virtualCpu(VnfdVirtualcpu virtualCpu) {
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

  public VnfdVirtualcomputedesc requestAdditionalCapability(List<VnfdRequestadditionalcapability> requestAdditionalCapability) {
    this.requestAdditionalCapability = requestAdditionalCapability;
    return this;
  }

  public VnfdVirtualcomputedesc addRequestAdditionalCapabilityItem(VnfdRequestadditionalcapability requestAdditionalCapabilityItem) {
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

  public VnfdVirtualcomputedesc id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of this VirtualComputeDesc in the VNFD.
   * @return id
   **/
  //@Schema(description = "Unique identifier of this VirtualComputeDesc in the VNFD.")
  
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
    VnfdVirtualcomputedesc vnfdVirtualcomputedesc = (VnfdVirtualcomputedesc) o;
    return Objects.equals(this.computeRequirements, vnfdVirtualcomputedesc.computeRequirements) &&
        Objects.equals(this.virtualMemory, vnfdVirtualcomputedesc.virtualMemory) &&
        Objects.equals(this.logicalNode, vnfdVirtualcomputedesc.logicalNode) &&
        Objects.equals(this.virtualCpu, vnfdVirtualcomputedesc.virtualCpu) &&
        Objects.equals(this.requestAdditionalCapability, vnfdVirtualcomputedesc.requestAdditionalCapability) &&
        Objects.equals(this.id, vnfdVirtualcomputedesc.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(computeRequirements, virtualMemory, logicalNode, virtualCpu, requestAdditionalCapability, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdVirtualcomputedesc {\n");
    
    sb.append("    computeRequirements: ").append(toIndentedString(computeRequirements)).append("\n");
    sb.append("    virtualMemory: ").append(toIndentedString(virtualMemory)).append("\n");
    sb.append("    logicalNode: ").append(toIndentedString(logicalNode)).append("\n");
    sb.append("    virtualCpu: ").append(toIndentedString(virtualCpu)).append("\n");
    sb.append("    requestAdditionalCapability: ").append(toIndentedString(requestAdditionalCapability)).append("\n");
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
