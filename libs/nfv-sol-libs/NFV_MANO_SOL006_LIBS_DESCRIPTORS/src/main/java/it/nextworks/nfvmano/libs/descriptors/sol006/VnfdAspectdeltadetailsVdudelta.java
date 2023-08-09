package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * VnfdAspectdeltadetailsVdudelta
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdAspectdeltadetailsVdudelta {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("number-of-instances")
  private String numberOfInstances = null;

  @JsonProperty("id")
  private String id = null;

  public VnfdAspectdeltadetailsVdudelta numberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
    return this;
  }

  /**
   * Number of instances of VNFC based on this VDU to deploy for an instantiation level or for a scaling delta. Shall be zero or greater.
   * @return numberOfInstances
   **/
  //@Schema(description = "Number of instances of VNFC based on this VDU to deploy for an instantiation level or for a scaling delta. Shall be zero or greater.")
  
    public String getNumberOfInstances() {
    return numberOfInstances;
  }

  public void setNumberOfInstances(String numberOfInstances) {
    this.numberOfInstances = numberOfInstances;
  }

  public VnfdAspectdeltadetailsVdudelta id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Uniquely identifies a VDU.
   * @return id
   **/
  //@Schema(description = "Uniquely identifies a VDU.")
  
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
    VnfdAspectdeltadetailsVdudelta vnfdAspectdeltadetailsVdudelta = (VnfdAspectdeltadetailsVdudelta) o;
    return Objects.equals(this.numberOfInstances, vnfdAspectdeltadetailsVdudelta.numberOfInstances) &&
        Objects.equals(this.id, vnfdAspectdeltadetailsVdudelta.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfInstances, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdAspectdeltadetailsVdudelta {\n");
    
    sb.append("    numberOfInstances: ").append(toIndentedString(numberOfInstances)).append("\n");
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
