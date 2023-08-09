package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * AffinityOrAntiAffinityGroupIdSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class AffinityOrAntiAffinityGroupIdSchema {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("id")
  private String affinityOrAntiAffinityGroupIdId = null;

  public AffinityOrAntiAffinityGroupIdSchema affinityOrAntiAffinityGroupIdId(String affinityOrAntiAffinityGroupIdId) {
    this.affinityOrAntiAffinityGroupIdId = affinityOrAntiAffinityGroupIdId;
    return this;
  }

  /**
   * Get affinityOrAntiAffinityGroupIdId
   * @return affinityOrAntiAffinityGroupIdId
   **/
  //@Schema(description = "")
  
    public String getAffinityOrAntiAffinityGroupIdId() {
    return affinityOrAntiAffinityGroupIdId;
  }

  public void setAffinityOrAntiAffinityGroupIdId(String affinityOrAntiAffinityGroupIdId) {
    this.affinityOrAntiAffinityGroupIdId = affinityOrAntiAffinityGroupIdId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AffinityOrAntiAffinityGroupIdSchema affinityOrAntiAffinityGroupIdSchema = (AffinityOrAntiAffinityGroupIdSchema) o;
    return Objects.equals(this.affinityOrAntiAffinityGroupIdId, affinityOrAntiAffinityGroupIdSchema.affinityOrAntiAffinityGroupIdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityOrAntiAffinityGroupIdId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AffinityOrAntiAffinityGroupIdSchema {\n");
    
    sb.append("    affinityOrAntiAffinityGroupIdId: ").append(toIndentedString(affinityOrAntiAffinityGroupIdId)).append("\n");
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
