package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.common.enums.CpuThreadPinningPolicyEnum;
import it.nextworks.nfvmano.libs.common.enums.PolicyEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * The virtual CPU pinning configuration for the virtualised compute resource.
 */
//@Schema(description = "The virtual CPU pinning configuration for the virtualised compute resource.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdVirtualcpuPinning {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("policy")
  private PolicyEnum policy = null;

  @JsonProperty("rule")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<VnfdVdustoragerequirements> rule = null;

  @JsonProperty("thread-policy")
  private CpuThreadPinningPolicyEnum threadPolicy = null;

  public VnfdVirtualcpuPinning policy(PolicyEnum policy) {
    this.policy = policy;
    return this;
  }

  /**
   * The policy can take values of 'static' or 'dynamic'. In case of 'static' the virtual CPU cores are requested to be allocated to logical CPU cores according to the rules defined in virtualCpuPinningRules. In case of 'dynamic' the allocation of virtual CPU cores to logical CPU cores is decided by the VIM. (e.g. SMT (Simultaneous MultiThreading) requirements).
   * @return policy
   **/
  //@Schema(description = "The policy can take values of 'static' or 'dynamic'. In case of 'static' the virtual CPU cores are requested to be allocated to logical CPU cores according to the rules defined in virtualCpuPinningRules. In case of 'dynamic' the allocation of virtual CPU cores to logical CPU cores is decided by the VIM. (e.g. SMT (Simultaneous MultiThreading) requirements).")
  
    public PolicyEnum getPolicy() {
    return policy;
  }

  public void setPolicy(PolicyEnum policy) {
    this.policy = policy;
  }

  public VnfdVirtualcpuPinning rule(List<VnfdVdustoragerequirements> rule) {
    this.rule = rule;
    return this;
  }

  public VnfdVirtualcpuPinning addRuleItem(VnfdVdustoragerequirements ruleItem) {
    if (this.rule == null) {
      this.rule = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.rule.add(ruleItem);
    return this;
  }

  /**
   * A list of rules that should be considered during the allocation of the virtual CPUs to logical CPUs in case of 'static' virtualCpuPinningPolicy.
   * @return rule
   **/
  //@Schema(description = "A list of rules that should be considered during the allocation of the virtual CPUs to logical CPUs in case of 'static' virtualCpuPinningPolicy.")

    public List<VnfdVdustoragerequirements> getRule() {
    return rule;
  }

  public void setRule(List<VnfdVdustoragerequirements> rule) {
    this.rule = rule;
  }

  public VnfdVirtualcpuPinning threadPolicy(CpuThreadPinningPolicyEnum threadPolicy) {
      this.threadPolicy = threadPolicy;
      return this;
  }

  public CpuThreadPinningPolicyEnum getThreadPolicy() { return threadPolicy; }

  public void setThreadPolicy(CpuThreadPinningPolicyEnum threadPolicy) { this.threadPolicy = threadPolicy; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdVirtualcpuPinning vnfdVirtualcpuPinning = (VnfdVirtualcpuPinning) o;
    return Objects.equals(this.policy, vnfdVirtualcpuPinning.policy) &&
            Objects.equals(this.rule, vnfdVirtualcpuPinning.rule) &&
            Objects.equals(this.threadPolicy, vnfdVirtualcpuPinning.threadPolicy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policy, rule, threadPolicy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdVirtualcpuPinning {\n");

    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
    sb.append("    rule: ").append(toIndentedString(rule)).append("\n");
    sb.append("    threadPolicy: ").append(toIndentedString(threadPolicy)).append("\n");
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
