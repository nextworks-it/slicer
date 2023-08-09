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
 * SecurityGroupRule
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class SecurityGroupRule {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("security-group-rule")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "security_group_rule_fk", referencedColumnName = "uuid")
  private List<SecuritygroupruleSecuritygrouprule> securityGroupRule = null;

  public SecurityGroupRule securityGroupRule(List<SecuritygroupruleSecuritygrouprule> securityGroupRule) {
    this.securityGroupRule = securityGroupRule;
    return this;
  }

  public SecurityGroupRule addSecurityGroupRuleItem(SecuritygroupruleSecuritygrouprule securityGroupRuleItem) {
    if (this.securityGroupRule == null) {
      this.securityGroupRule = new ArrayList<SecuritygroupruleSecuritygrouprule>();
    }
    this.securityGroupRule.add(securityGroupRuleItem);
    return this;
  }

  /**
   * Defines security group rules to be used by the VNF.
   * @return securityGroupRule
   **/
  //@Schema(description = "Defines security group rules to be used by the VNF.")

    public List<SecuritygroupruleSecuritygrouprule> getSecurityGroupRule() {
    return securityGroupRule;
  }

  public void setSecurityGroupRule(List<SecuritygroupruleSecuritygrouprule> securityGroupRule) {
    this.securityGroupRule = securityGroupRule;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SecurityGroupRule securityGroupRule = (SecurityGroupRule) o;
    return Objects.equals(this.securityGroupRule, securityGroupRule.securityGroupRule);
  }

  @Override
  public int hashCode() {
    return Objects.hash(securityGroupRule);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SecurityGroupRule {\n");
    
    sb.append("    securityGroupRule: ").append(toIndentedString(securityGroupRule)).append("\n");
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
