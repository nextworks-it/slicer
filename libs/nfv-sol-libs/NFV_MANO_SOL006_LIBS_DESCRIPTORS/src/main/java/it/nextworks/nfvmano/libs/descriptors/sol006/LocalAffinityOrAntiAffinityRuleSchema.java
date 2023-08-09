package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import org.springframework.validation.annotation.Validated;

/**
 * LocalAffinityOrAntiAffinityRuleSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class LocalAffinityOrAntiAffinityRuleSchema   {
  /**
   * Specifies whether the rule is an affinity rule or an anti-affinity rule.
   */
  public enum AffinityTypeEnum {
    AFFINITY("affinity"),
    
    ANTI_AFFINITY("anti-affinity");

    private String value;

    AffinityTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AffinityTypeEnum fromValue(String text) {
      for (AffinityTypeEnum b : AffinityTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("affinity-type")
  private AffinityTypeEnum affinityType = null;

  /**
   * Specifies the scope of the rule, possible values are 'NFVI-PoP', 'Zone', 'ZoneGroup', 'NFVI-node'.
   */
  public enum AffinityScopeEnum {
    NFVI_NODE("nfvi-node"),
    
    ZONE_GROUP("zone-group"),
    
    ZONE("zone"),
    
    NFVI_POP("nfvi-pop");

    private String value;

    AffinityScopeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AffinityScopeEnum fromValue(String text) {
      for (AffinityScopeEnum b : AffinityScopeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("affinity-scope")
  private AffinityScopeEnum affinityScope = null;

  public LocalAffinityOrAntiAffinityRuleSchema affinityType(AffinityTypeEnum affinityType) {
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

  public LocalAffinityOrAntiAffinityRuleSchema affinityScope(AffinityScopeEnum affinityScope) {
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
    LocalAffinityOrAntiAffinityRuleSchema localAffinityOrAntiAffinityRuleSchema = (LocalAffinityOrAntiAffinityRuleSchema) o;
    return Objects.equals(this.affinityType, localAffinityOrAntiAffinityRuleSchema.affinityType) &&
        Objects.equals(this.affinityScope, localAffinityOrAntiAffinityRuleSchema.affinityScope);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityType, affinityScope);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocalAffinityOrAntiAffinityRuleSchema {\n");
    
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
