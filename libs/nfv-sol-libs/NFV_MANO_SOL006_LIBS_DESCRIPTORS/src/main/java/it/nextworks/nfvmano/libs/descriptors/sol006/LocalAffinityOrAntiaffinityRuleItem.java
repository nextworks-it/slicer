package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.enums.AffinityScopeEnum;
import it.nextworks.nfvmano.libs.common.enums.AffinityTypeEnum;

import javax.persistence.Embeddable;

/**
 * LocalaffinityorantiaffinityruleLocalaffinityorantiaffinityrule
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class LocalAffinityOrAntiaffinityRuleItem {

  @JsonProperty("affinity-type")
  private AffinityTypeEnum affinityType = null;

  @JsonProperty("affinity-scope")
  private AffinityScopeEnum affinityScope = null;

  public LocalAffinityOrAntiaffinityRuleItem affinityType(AffinityTypeEnum affinityType) {
    this.affinityType = affinityType;
    return this;
  }

  /**
   * Specifies whether the rule is an affinity rule or an anti-affinity rule.
   * @return affinityType
   **/
  //@Schema(description = "Specifies whether the rule is an affinity rule or an anti-affinity rule.")
  
    public AffinityTypeEnum getAffinityType() {
    return affinityType;
  }

  public void setAffinityType(AffinityTypeEnum affinityType) {
    this.affinityType = affinityType;
  }

  public LocalAffinityOrAntiaffinityRuleItem affinityScope(AffinityScopeEnum affinityScope) {
    this.affinityScope = affinityScope;
    return this;
  }

  /**
   * Specifies the scope of the rule, possible values are 'NFVI-PoP', 'Zone', 'ZoneGroup', 'NFVI-node'.
   * @return affinityScope
   **/
  //@Schema(description = "Specifies the scope of the rule, possible values are 'NFVI-PoP', 'Zone', 'ZoneGroup', 'NFVI-node'.")
  
    public AffinityScopeEnum getAffinityScope() {
    return affinityScope;
  }

  public void setAffinityScope(AffinityScopeEnum affinityScope) {
    this.affinityScope = affinityScope;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocalAffinityOrAntiaffinityRuleItem localaffinityorantiaffinityruleLocalaffinityorantiaffinityrule = (LocalAffinityOrAntiaffinityRuleItem) o;
    return Objects.equals(this.affinityType, localaffinityorantiaffinityruleLocalaffinityorantiaffinityrule.affinityType) &&
        Objects.equals(this.affinityScope, localaffinityorantiaffinityruleLocalaffinityorantiaffinityrule.affinityScope);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityType, affinityScope);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocalaffinityorantiaffinityruleLocalaffinityorantiaffinityrule {\n");
    
    sb.append("    affinityType: ").append(toIndentedString(affinityType)).append("\n");
    sb.append("    affinityScope: ").append(toIndentedString(affinityScope)).append("\n");
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
