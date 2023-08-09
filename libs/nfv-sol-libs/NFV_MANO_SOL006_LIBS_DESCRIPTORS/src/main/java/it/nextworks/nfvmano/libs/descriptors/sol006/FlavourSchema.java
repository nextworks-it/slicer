package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * FlavourSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class FlavourSchema {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("qos")
  @Embedded
  private FlavourSchemaQos qos = null;

  @JsonProperty("id")
  private String flavourId = null;

  public FlavourSchema qos(FlavourSchemaQos qos) {
    this.qos = qos;
    return this;
  }

  /**
   * Get qos
   * @return qos
   **/
  //@Schema(description = "")
  

    public FlavourSchemaQos getQos() {
    return qos;
  }

  public void setQos(FlavourSchemaQos qos) {
    this.qos = qos;
  }

  public FlavourSchema flavourId(String flavourId) {
    this.flavourId = flavourId;
    return this;
  }

  /**
   * Identifies a flavour within a VnfVirtualLinkDesc.
   * @return flavourId
   **/
  //@Schema(description = "Identifies a flavour within a VnfVirtualLinkDesc.")
  
    public String getFlavourId() {
    return flavourId;
  }

  public void setFlavourId(String flavourId) {
    this.flavourId = flavourId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FlavourSchema flavourSchema = (FlavourSchema) o;
    return Objects.equals(this.qos, flavourSchema.qos) &&
        Objects.equals(this.flavourId, flavourSchema.flavourId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(qos, flavourId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlavourSchema {\n");
    
    sb.append("    qos: ").append(toIndentedString(qos)).append("\n");
    sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n");
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
