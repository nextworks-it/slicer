package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * LocalAffinityOrAntiAffinityRule
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class LocalAffinityOrAntiAffinityRule {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("local-affinity-or-anti-affinity-rule")
  @ElementCollection
  @CollectionTable(name = "local_affinity_or_anti_affinity_rule_loc_aff_or_anti_aff_rule")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<LocalAffinityOrAntiaffinityRuleItem> localAffinityOrAntiAffinityRule = null;

  public LocalAffinityOrAntiAffinityRule localAffinityOrAntiAffinityRule(List<LocalAffinityOrAntiaffinityRuleItem> localAffinityOrAntiAffinityRule) {
    this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
    return this;
  }

  public LocalAffinityOrAntiAffinityRule addLocalAffinityOrAntiAffinityRuleItem(LocalAffinityOrAntiaffinityRuleItem localAffinityOrAntiAffinityRuleItem) {
    if (this.localAffinityOrAntiAffinityRule == null) {
      this.localAffinityOrAntiAffinityRule = new ArrayList<LocalAffinityOrAntiaffinityRuleItem>();
    }
    this.localAffinityOrAntiAffinityRule.add(localAffinityOrAntiAffinityRuleItem);
    return this;
  }

  /**
   * Get localAffinityOrAntiAffinityRule
   * @return localAffinityOrAntiAffinityRule
   **/
  //@Schema(description = "")

    public List<LocalAffinityOrAntiaffinityRuleItem> getLocalAffinityOrAntiAffinityRule() {
    return localAffinityOrAntiAffinityRule;
  }

  public void setLocalAffinityOrAntiAffinityRule(List<LocalAffinityOrAntiaffinityRuleItem> localAffinityOrAntiAffinityRule) {
    this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocalAffinityOrAntiAffinityRule localAffinityOrAntiAffinityRule = (LocalAffinityOrAntiAffinityRule) o;
    return Objects.equals(this.localAffinityOrAntiAffinityRule, localAffinityOrAntiAffinityRule.localAffinityOrAntiAffinityRule);
  }

  @Override
  public int hashCode() {
    return Objects.hash(localAffinityOrAntiAffinityRule);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocalAffinityOrAntiAffinityRule {\n");
    
    sb.append("    localAffinityOrAntiAffinityRule: ").append(toIndentedString(localAffinityOrAntiAffinityRule)).append("\n");
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
