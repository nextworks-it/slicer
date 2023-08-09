package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;




/**
 * DependenciesSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class DependenciesSchema   {
  @JsonProperty("secondary-id")
  private NsdSecondaryid secondaryId = null;

  @JsonProperty("dependencies_id")
  private String dependenciesId = null;

  @JsonProperty("primary-id")
  private NsdPrimaryid primaryId = null;

  public DependenciesSchema secondaryId(NsdSecondaryid secondaryId) {
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

  public DependenciesSchema dependenciesId(String dependenciesId) {
    this.dependenciesId = dependenciesId;
    return this;
  }

  /**
   * Get dependenciesId
   * @return dependenciesId
   **/
  //@Schema(description = "")
  
    public String getDependenciesId() {
    return dependenciesId;
  }

  public void setDependenciesId(String dependenciesId) {
    this.dependenciesId = dependenciesId;
  }

  public DependenciesSchema primaryId(NsdPrimaryid primaryId) {
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
    DependenciesSchema dependenciesSchema = (DependenciesSchema) o;
    return Objects.equals(this.secondaryId, dependenciesSchema.secondaryId) &&
        Objects.equals(this.dependenciesId, dependenciesSchema.dependenciesId) &&
        Objects.equals(this.primaryId, dependenciesSchema.primaryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(secondaryId, dependenciesId, primaryId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DependenciesSchema {\n");
    
    sb.append("    secondaryId: ").append(toIndentedString(secondaryId)).append("\n");
    sb.append("    dependenciesId: ").append(toIndentedString(dependenciesId)).append("\n");
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
