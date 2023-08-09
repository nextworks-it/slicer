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
 * NsdAffinityorantiaffinitygroup
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdAffinityOrAntiaffinityGroup {

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

  public NsdAffinityOrAntiaffinityGroup scope(ScopeEnum scope) {
    this.scope = scope;
    return this;
  }

  /**
   * Specifies the scope of the affinity or anti-affinity relationship e.g. a NFVI node, an NFVI PoP, etc.
   * @return scope
   **/
  //@Schema(description = "Specifies the scope of the affinity or anti-affinity relationship e.g. a NFVI node, an NFVI PoP, etc.")
  
    public ScopeEnum getScope() {
    return scope;
  }

  public void setScope(ScopeEnum scope) {
    this.scope = scope;
  }

  public NsdAffinityOrAntiaffinityGroup type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Specifies the type of relationship that the members of the group have: 'affinity' or 'anti-affinity.'
   * @return type
   **/
  //@Schema(description = "Specifies the type of relationship that the members of the group have: 'affinity' or 'anti-affinity.'")
  
    public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public NsdAffinityOrAntiaffinityGroup id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of Identifier of this AffinityOrAntiAffinityGroup information element.
   * @return id
   **/
  //@Schema(description = "Identifier of Identifier of this AffinityOrAntiAffinityGroup information element.")
  
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
    NsdAffinityOrAntiaffinityGroup nsdAffinityorantiaffinitygroup = (NsdAffinityOrAntiaffinityGroup) o;
    return Objects.equals(this.scope, nsdAffinityorantiaffinitygroup.scope) &&
        Objects.equals(this.type, nsdAffinityorantiaffinitygroup.type) &&
        Objects.equals(this.id, nsdAffinityorantiaffinitygroup.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scope, type, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdAffinityorantiaffinitygroup {\n");
    
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
