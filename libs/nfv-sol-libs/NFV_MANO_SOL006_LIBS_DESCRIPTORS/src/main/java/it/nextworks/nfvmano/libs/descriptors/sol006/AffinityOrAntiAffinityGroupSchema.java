package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.enums.ScopeEnum;
import it.nextworks.nfvmano.libs.common.enums.TypeEnum;

import javax.persistence.Embeddable;

/**
 * AffinityOrAntiAffinityGroupSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class AffinityOrAntiAffinityGroupSchema {

  @JsonProperty("affinity-or-anti-affinity-group_id")
  private String affinityOrAntiAffinityGroupId = null;

  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("scope")
  private ScopeEnum scope = null;

  public AffinityOrAntiAffinityGroupSchema affinityOrAntiAffinityGroupId(String affinityOrAntiAffinityGroupId) {
    this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
    return this;
  }

  /**
   * Identifier of Identifier of this AffinityOrAntiAffinityGroup information element.
   * @return affinityOrAntiAffinityGroupId
   **/
  //@Schema(description = "Identifier of Identifier of this AffinityOrAntiAffinityGroup information element.")
  
    public String getAffinityOrAntiAffinityGroupId() {
    return affinityOrAntiAffinityGroupId;
  }

  public void setAffinityOrAntiAffinityGroupId(String affinityOrAntiAffinityGroupId) {
    this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
  }

  public AffinityOrAntiAffinityGroupSchema type(TypeEnum type) {
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

  public AffinityOrAntiAffinityGroupSchema scope(ScopeEnum scope) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AffinityOrAntiAffinityGroupSchema affinityOrAntiAffinityGroupSchema = (AffinityOrAntiAffinityGroupSchema) o;
    return Objects.equals(this.affinityOrAntiAffinityGroupId, affinityOrAntiAffinityGroupSchema.affinityOrAntiAffinityGroupId) &&
        Objects.equals(this.type, affinityOrAntiAffinityGroupSchema.type) &&
        Objects.equals(this.scope, affinityOrAntiAffinityGroupSchema.scope);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityOrAntiAffinityGroupId, type, scope);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AffinityOrAntiAffinityGroupSchema {\n");
    
    sb.append("    affinityOrAntiAffinityGroupId: ").append(toIndentedString(affinityOrAntiAffinityGroupId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
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
