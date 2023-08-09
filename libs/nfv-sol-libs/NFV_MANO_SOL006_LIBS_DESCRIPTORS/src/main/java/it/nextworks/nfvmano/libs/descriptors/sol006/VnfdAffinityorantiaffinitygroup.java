package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.ScopeEnum;
import it.nextworks.nfvmano.libs.common.enums.TypeEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * VnfdAffinityorantiaffinitygroup
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdAffinityorantiaffinitygroup {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("scope")
  private ScopeEnum scope = null;

  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("id")
  private String id = null;

  public VnfdAffinityorantiaffinitygroup scope(ScopeEnum scope) {
    this.scope = scope;
    return this;
  }

  /**
   * Specifies the scope of the rule, possible values are 'NFVI-PoP', 'Zone', 'ZoneGroup', 'NFVI-node'.
   * @return scope
   **/
  //@Schema(description = "Specifies the scope of the rule, possible values are 'NFVI-PoP', 'Zone', 'ZoneGroup', 'NFVI-node'.")
  
    public ScopeEnum getScope() {
    return scope;
  }

  public void setScope(ScopeEnum scope) {
    this.scope = scope;
  }

  public VnfdAffinityorantiaffinitygroup type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Specifies whether the rule is an affinity rule or an anti-affinity rule.
   * @return type
   **/
  //@Schema(description = "Specifies whether the rule is an affinity rule or an anti-affinity rule.")
  
    public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public VnfdAffinityorantiaffinitygroup id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifies an affinity or anti-affinity group to which the affinity or anti-affinity rule applies.
   * @return id
   **/
  //@Schema(description = "Identifies an affinity or anti-affinity group to which the affinity or anti-affinity rule applies.")
  
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
    VnfdAffinityorantiaffinitygroup vnfdAffinityorantiaffinitygroup = (VnfdAffinityorantiaffinitygroup) o;
    return Objects.equals(this.scope, vnfdAffinityorantiaffinitygroup.scope) &&
        Objects.equals(this.type, vnfdAffinityorantiaffinitygroup.type) &&
        Objects.equals(this.id, vnfdAffinityorantiaffinitygroup.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scope, type, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdAffinityorantiaffinitygroup {\n");
    
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
