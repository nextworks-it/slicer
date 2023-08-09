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
 * NsdDependencies
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdDependencies {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("secondary-id")
  @Embedded
  private NsdSecondaryid secondaryId = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("primary-id")
  @Embedded
  private NsdPrimaryid primaryId = null;

  public NsdDependencies secondaryId(NsdSecondaryid secondaryId) {
    this.secondaryId = secondaryId;
    return this;
  }

  /**
   * Get secondaryId
   * @return secondaryId
   **/
  //@Schema(description = "")
  

    public NsdSecondaryid getSecondaryId() {
    return secondaryId;
  }

  public void setSecondaryId(NsdSecondaryid secondaryId) {
    this.secondaryId = secondaryId;
  }

  public NsdDependencies id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  //@Schema(description = "")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public NsdDependencies primaryId(NsdPrimaryid primaryId) {
    this.primaryId = primaryId;
    return this;
  }

  /**
   * Get primaryId
   * @return primaryId
   **/
  //@Schema(description = "")
  

    public NsdPrimaryid getPrimaryId() {
    return primaryId;
  }

  public void setPrimaryId(NsdPrimaryid primaryId) {
    this.primaryId = primaryId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdDependencies nsdDependencies = (NsdDependencies) o;
    return Objects.equals(this.secondaryId, nsdDependencies.secondaryId) &&
        Objects.equals(this.id, nsdDependencies.id) &&
        Objects.equals(this.primaryId, nsdDependencies.primaryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(secondaryId, id, primaryId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdDependencies {\n");
    
    sb.append("    secondaryId: ").append(toIndentedString(secondaryId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    primaryId: ").append(toIndentedString(primaryId)).append("\n");
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
