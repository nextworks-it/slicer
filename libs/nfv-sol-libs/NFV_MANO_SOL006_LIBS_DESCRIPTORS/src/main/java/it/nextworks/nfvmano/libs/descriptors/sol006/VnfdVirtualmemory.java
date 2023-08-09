package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.common.enums.MemPageSizeEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * The virtual memory of the virtualised compute.
 */
//@Schema(description = "The virtual memory of the virtualised compute.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdVirtualmemory {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("vdu-mem-requirements")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<VnfdVdustoragerequirements> vduMemRequirements = null;

  @JsonProperty("numa-enabled")
  private Boolean numaEnabled = null;

  @JsonProperty("over-subscription-policy")
  private String overSubscriptionPolicy = null;

  @JsonProperty("size")
  private Double size = null;

  @JsonProperty("numa-node-policy")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "numa_node_policy_fk", referencedColumnName = "uuid")
  private NumaNodePolicy numaNodePolicy = null;

  @JsonProperty("mempage-size")
  private MemPageSizeEnum memPageSizeEnum = null;

  @JsonProperty("mem-quota")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "mem_quota_fk", referencedColumnName = "uuid")
  private MemQuota memQuota = null;

  public VnfdVirtualmemory vduMemRequirements(List<VnfdVdustoragerequirements> vduMemRequirements) {
    this.vduMemRequirements = vduMemRequirements;
    return this;
  }

  public VnfdVirtualmemory addVduMemRequirementsItem(VnfdVdustoragerequirements vduMemRequirementsItem) {
    if (this.vduMemRequirements == null) {
      this.vduMemRequirements = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.vduMemRequirements.add(vduMemRequirementsItem);
    return this;
  }

  /**
   * Array of key-value pair requirements on the memory for the VDU.
   * @return vduMemRequirements
   **/
  //@Schema(description = "Array of key-value pair requirements on the memory for the VDU.")

    public List<VnfdVdustoragerequirements> getVduMemRequirements() {
    return vduMemRequirements;
  }

  public void setVduMemRequirements(List<VnfdVdustoragerequirements> vduMemRequirements) {
    this.vduMemRequirements = vduMemRequirements;
  }

  public VnfdVirtualmemory numaEnabled(Boolean numaEnabled) {
    this.numaEnabled = numaEnabled;
    return this;
  }

  /**
   * It specifies the memory allocation to be cognisant of the relevant process/core allocation. The cardinality can be 0 during the allocation request, if no particular value is requested.
   * @return numaEnabled
   **/
  //@Schema(description = "It specifies the memory allocation to be cognisant of the relevant process/core allocation. The cardinality can be 0 during the allocation request, if no particular value is requested.")
  
    public Boolean isNumaEnabled() {
    return numaEnabled;
  }

  public void setNumaEnabled(Boolean numaEnabled) {
    this.numaEnabled = numaEnabled;
  }

  public VnfdVirtualmemory overSubscriptionPolicy(String overSubscriptionPolicy) {
    this.overSubscriptionPolicy = overSubscriptionPolicy;
    return this;
  }

  /**
   * The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 during the allocation request, if no particular value is requested.
   * @return overSubscriptionPolicy
   **/
  //@Schema(description = "The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 during the allocation request, if no particular value is requested.")
  
    public String getOverSubscriptionPolicy() {
    return overSubscriptionPolicy;
  }

  public void setOverSubscriptionPolicy(String overSubscriptionPolicy) {
    this.overSubscriptionPolicy = overSubscriptionPolicy;
  }

  public VnfdVirtualmemory size(Double size) {
    this.size = size;
    return this;
  }

  /**
   * Amount of virtual memory in GB.
   * @return size
   **/
  //@Schema(description = "Amount of virtual memory in GB.")
  
    public Double getSize() {
    return size;
  }

  public void setSize(Double size) {
    this.size = size;
  }

  public VnfdVirtualmemory numaNodePolicy(NumaNodePolicy numaNodePolicy) {
      this.numaNodePolicy = numaNodePolicy;
      return this;
  }

  public NumaNodePolicy getNumaNodePolicy() { return numaNodePolicy; }

  public void setNumaNodePolicy(NumaNodePolicy numaNodePolicy) { this.numaNodePolicy = numaNodePolicy; }

  public VnfdVirtualmemory memPageSizeEnum(MemPageSizeEnum memPageSizeEnum) {
      this.memPageSizeEnum = memPageSizeEnum;
      return this;
  }

  public MemPageSizeEnum getMemPageSizeEnum() { return memPageSizeEnum; }

  public void setMemPageSizeEnum(MemPageSizeEnum memPageSizeEnum) { this.memPageSizeEnum = memPageSizeEnum; }

  public VnfdVirtualmemory memQuota(MemQuota memQuota) {
      this.memQuota = memQuota;
      return this;
  }

  public MemQuota getMemQuota() { return memQuota; }

  public void setMemQuota(MemQuota memQuota) { this.memQuota = memQuota; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdVirtualmemory vnfdVirtualmemory = (VnfdVirtualmemory) o;
    return Objects.equals(this.vduMemRequirements, vnfdVirtualmemory.vduMemRequirements) &&
            Objects.equals(this.numaEnabled, vnfdVirtualmemory.numaEnabled) &&
            Objects.equals(this.overSubscriptionPolicy, vnfdVirtualmemory.overSubscriptionPolicy) &&
            Objects.equals(this.size, vnfdVirtualmemory.size) &&
            Objects.equals(this.numaNodePolicy, vnfdVirtualmemory.numaNodePolicy) &&
            Objects.equals(this.memPageSizeEnum, vnfdVirtualmemory.memPageSizeEnum) &&
            Objects.equals(this.memQuota, vnfdVirtualmemory.memQuota);
    }

  @Override
  public int hashCode() {
    return Objects.hash(vduMemRequirements, numaEnabled, overSubscriptionPolicy,
            size, numaNodePolicy, memPageSizeEnum, memQuota);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdVirtualmemory {\n");
    
    sb.append("    vduMemRequirements: ").append(toIndentedString(vduMemRequirements)).append("\n");
    sb.append("    numaEnabled: ").append(toIndentedString(numaEnabled)).append("\n");
    sb.append("    overSubscriptionPolicy: ").append(toIndentedString(overSubscriptionPolicy)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    numaNodePolicy: ").append(toIndentedString(numaNodePolicy)).append("\n");
    sb.append("    memPageSizeEnum: ").append(toIndentedString(memPageSizeEnum)).append("\n");
    sb.append("    memQuota: ").append(toIndentedString(memQuota)).append("\n");
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
