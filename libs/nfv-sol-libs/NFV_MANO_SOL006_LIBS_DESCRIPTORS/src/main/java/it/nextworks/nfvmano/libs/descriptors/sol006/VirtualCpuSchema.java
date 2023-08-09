package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



/**
 * The virtual CPU(s)of the virtualised compute.
 */
//@Schema(description = "The virtual CPU(s)of the virtualised compute.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class VirtualCpuSchema   {
  @JsonProperty("num-virtual-cpu")
  private String numVirtualCpu = null;

  @JsonProperty("clock")
  private String clock = null;

  @JsonProperty("pinning")
  private VnfdVirtualcpuPinning pinning = null;

  @JsonProperty("cpu-architecture")
  private String cpuArchitecture = null;

  @JsonProperty("vdu-cpu-requirements")

  private List<VnfdVdustoragerequirements> vduCpuRequirements = null;

  @JsonProperty("oversubscription-policy")
  private String oversubscriptionPolicy = null;

  public VirtualCpuSchema numVirtualCpu(String numVirtualCpu) {
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

  public VirtualCpuSchema clock(String clock) {
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

  public VirtualCpuSchema pinning(VnfdVirtualcpuPinning pinning) {
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

  public VirtualCpuSchema cpuArchitecture(String cpuArchitecture) {
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

  public VirtualCpuSchema vduCpuRequirements(List<VnfdVdustoragerequirements> vduCpuRequirements) {
    this.vduCpuRequirements = vduCpuRequirements;
    return this;
  }

  public VirtualCpuSchema addVduCpuRequirementsItem(VnfdVdustoragerequirements vduCpuRequirementsItem) {
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

  public VirtualCpuSchema oversubscriptionPolicy(String oversubscriptionPolicy) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualCpuSchema virtualCpuSchema = (VirtualCpuSchema) o;
    return Objects.equals(this.numVirtualCpu, virtualCpuSchema.numVirtualCpu) &&
        Objects.equals(this.clock, virtualCpuSchema.clock) &&
        Objects.equals(this.pinning, virtualCpuSchema.pinning) &&
        Objects.equals(this.cpuArchitecture, virtualCpuSchema.cpuArchitecture) &&
        Objects.equals(this.vduCpuRequirements, virtualCpuSchema.vduCpuRequirements) &&
        Objects.equals(this.oversubscriptionPolicy, virtualCpuSchema.oversubscriptionPolicy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numVirtualCpu, clock, pinning, cpuArchitecture, vduCpuRequirements, oversubscriptionPolicy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualCpuSchema {\n");
    
    sb.append("    numVirtualCpu: ").append(toIndentedString(numVirtualCpu)).append("\n");
    sb.append("    clock: ").append(toIndentedString(clock)).append("\n");
    sb.append("    pinning: ").append(toIndentedString(pinning)).append("\n");
    sb.append("    cpuArchitecture: ").append(toIndentedString(cpuArchitecture)).append("\n");
    sb.append("    vduCpuRequirements: ").append(toIndentedString(vduCpuRequirements)).append("\n");
    sb.append("    oversubscriptionPolicy: ").append(toIndentedString(oversubscriptionPolicy)).append("\n");
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
