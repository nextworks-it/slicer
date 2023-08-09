package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;



/**
 * The virtual CPU pinning configuration for the virtualised compute resource.
 */
//@Schema(description = "The virtual CPU pinning configuration for the virtualised compute resource.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class PinningSchema   {
  /**
   * The policy can take values of 'static' or 'dynamic'. In case of 'static' the virtual CPU cores are requested to be allocated to logical CPU cores according to the rules defined in virtualCpuPinningRules. In case of 'dynamic' the allocation of virtual CPU cores to logical CPU cores is decided by the VIM. (e.g. SMT (Simultaneous MultiThreading) requirements).
   */
  public enum PolicyEnum {
    STATIC("static"),
    
    DYNAMIC("dynamic");

    private String value;

    PolicyEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PolicyEnum fromValue(String text) {
      for (PolicyEnum b : PolicyEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("policy")
  private PolicyEnum policy = null;

  @JsonProperty("rule")

  private List<VnfdVdustoragerequirements> rule = null;

  public PinningSchema policy(PolicyEnum policy) {
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

  public PinningSchema rule(List<VnfdVdustoragerequirements> rule) {
    this.rule = rule;
    return this;
  }

  public PinningSchema addRuleItem(VnfdVdustoragerequirements ruleItem) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PinningSchema pinningSchema = (PinningSchema) o;
    return Objects.equals(this.policy, pinningSchema.policy) &&
        Objects.equals(this.rule, pinningSchema.rule);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policy, rule);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PinningSchema {\n");
    
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
    sb.append("    rule: ").append(toIndentedString(rule)).append("\n");
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
