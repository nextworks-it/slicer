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
 * The virtual CPU(s)of the virtualised compute.
 */
//@Schema(description = "The virtual CPU(s)of the virtualised compute.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdVirtualcpu {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("num-virtual-cpu")
  private String numVirtualCpu = null;

  @JsonProperty("clock")
  private String clock = null;

  @JsonProperty("pinning")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "vnfd_virtual_cpu_pinnning_fk", referencedColumnName = "uuid")
  private VnfdVirtualcpuPinning pinning = null;

  @JsonProperty("cpu-architecture")
  private String cpuArchitecture = null;

  @JsonProperty("vdu-cpu-requirements")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<VnfdVdustoragerequirements> vduCpuRequirements = null;

  @JsonProperty("oversubscription-policy")
  private String oversubscriptionPolicy = null;

  @JsonProperty("cpu-quota")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "cpu_quota_fk", referencedColumnName = "uuid")
  private CpuQuota cpuQuota = null;

  public VnfdVirtualcpu numVirtualCpu(String numVirtualCpu) {
    this.numVirtualCpu = numVirtualCpu;
    return this;
  }

  /**
   * Number of virtual CPUs.
   * @return numVirtualCpu
   **/
  //@Schema(description = "Number of virtual CPUs.")
  
    public String getNumVirtualCpu() {
    return numVirtualCpu;
  }

  public void setNumVirtualCpu(String numVirtualCpu) {
    this.numVirtualCpu = numVirtualCpu;
  }

  public VnfdVirtualcpu clock(String clock) {
    this.clock = clock;
    return this;
  }

  /**
   * Minimum virtual CPU clock rate (e.g. in MHz). The cardinality can be 0 during the allocation request, if no particular value is requested.
   * @return clock
   **/
  //@Schema(description = "Minimum virtual CPU clock rate (e.g. in MHz). The cardinality can be 0 during the allocation request, if no particular value is requested.")
  
    public String getClock() {
    return clock;
  }

  public void setClock(String clock) {
    this.clock = clock;
  }

  public VnfdVirtualcpu pinning(VnfdVirtualcpuPinning pinning) {
    this.pinning = pinning;
    return this;
  }

  /**
   * Get pinning
   * @return pinning
   **/
  //@Schema(description = "")
  

    public VnfdVirtualcpuPinning getPinning() {
    return pinning;
  }

  public void setPinning(VnfdVirtualcpuPinning pinning) {
    this.pinning = pinning;
  }

  public VnfdVirtualcpu cpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
    return this;
  }

  /**
   * CPU architecture type. Examples are x86, ARM. The cardinality can be 0 during the allocation request, if no particular CPU architecture type is requested.
   * @return cpuArchitecture
   **/
  //@Schema(description = "CPU architecture type. Examples are x86, ARM. The cardinality can be 0 during the allocation request, if no particular CPU architecture type is requested.")
  
    public String getCpuArchitecture() {
    return cpuArchitecture;
  }

  public void setCpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
  }

  public VnfdVirtualcpu vduCpuRequirements(List<VnfdVdustoragerequirements> vduCpuRequirements) {
    this.vduCpuRequirements = vduCpuRequirements;
    return this;
  }

  public VnfdVirtualcpu addVduCpuRequirementsItem(VnfdVdustoragerequirements vduCpuRequirementsItem) {
    if (this.vduCpuRequirements == null) {
      this.vduCpuRequirements = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.vduCpuRequirements.add(vduCpuRequirementsItem);
    return this;
  }

  /**
   * Array of key-value pair requirements on the compute (CPU) for the VDU.
   * @return vduCpuRequirements
   **/
  //@Schema(description = "Array of key-value pair requirements on the compute (CPU) for the VDU.")

    public List<VnfdVdustoragerequirements> getVduCpuRequirements() {
    return vduCpuRequirements;
  }

  public void setVduCpuRequirements(List<VnfdVdustoragerequirements> vduCpuRequirements) {
    this.vduCpuRequirements = vduCpuRequirements;
  }

  public VnfdVirtualcpu oversubscriptionPolicy(String oversubscriptionPolicy) {
    this.oversubscriptionPolicy = oversubscriptionPolicy;
    return this;
  }

  /**
   * The CPU core oversubscription policy e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 during the allocation request, if no particular value is requested.
   * @return oversubscriptionPolicy
   **/
  //@Schema(description = "The CPU core oversubscription policy e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 during the allocation request, if no particular value is requested.")
  
    public String getOversubscriptionPolicy() {
    return oversubscriptionPolicy;
  }

  public void setOversubscriptionPolicy(String oversubscriptionPolicy) {
    this.oversubscriptionPolicy = oversubscriptionPolicy;
  }

  public VnfdVirtualcpu cpuQuota(CpuQuota cpuQuota) {
      this.cpuQuota = cpuQuota;
      return this;
  }

  public CpuQuota getCpuQuota() { return cpuQuota; }

  public void setCpuQuota(CpuQuota cpuQuota) { this.cpuQuota = cpuQuota; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdVirtualcpu vnfdVirtualcpu = (VnfdVirtualcpu) o;
    return Objects.equals(this.numVirtualCpu, vnfdVirtualcpu.numVirtualCpu) &&
            Objects.equals(this.clock, vnfdVirtualcpu.clock) &&
            Objects.equals(this.pinning, vnfdVirtualcpu.pinning) &&
            Objects.equals(this.cpuArchitecture, vnfdVirtualcpu.cpuArchitecture) &&
            Objects.equals(this.vduCpuRequirements, vnfdVirtualcpu.vduCpuRequirements) &&
            Objects.equals(this.oversubscriptionPolicy, vnfdVirtualcpu.oversubscriptionPolicy) &&
            Objects.equals(this.cpuQuota, vnfdVirtualcpu.cpuQuota);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numVirtualCpu, clock, pinning, cpuArchitecture, vduCpuRequirements, oversubscriptionPolicy, cpuQuota);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdVirtualcpu {\n");
    
    sb.append("    numVirtualCpu: ").append(toIndentedString(numVirtualCpu)).append("\n");
    sb.append("    clock: ").append(toIndentedString(clock)).append("\n");
    sb.append("    pinning: ").append(toIndentedString(pinning)).append("\n");
    sb.append("    cpuArchitecture: ").append(toIndentedString(cpuArchitecture)).append("\n");
    sb.append("    vduCpuRequirements: ").append(toIndentedString(vduCpuRequirements)).append("\n");
    sb.append("    oversubscriptionPolicy: ").append(toIndentedString(oversubscriptionPolicy)).append("\n");
    sb.append("    cpuQuota: ").append(toIndentedString(cpuQuota)).append("\n");
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
