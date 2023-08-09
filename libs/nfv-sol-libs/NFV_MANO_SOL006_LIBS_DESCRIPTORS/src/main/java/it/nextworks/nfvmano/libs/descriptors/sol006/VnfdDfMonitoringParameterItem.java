package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

/**
 * AllOfvnfdDfMonitoringParameterItems
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class VnfdDfMonitoringParameterItem extends MonitoringParameter {

  @JsonProperty("id")
  private String id = null;

  public VnfdDfMonitoringParameterItem id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the monitoring parameter.
   * @return id
   **/
  //@Schema(description = "Unique identifier of the monitoring parameter.")
  
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
    VnfdDfMonitoringParameterItem allOfvnfdDfMonitoringParameterItems = (VnfdDfMonitoringParameterItem) o;
    return Objects.equals(this.id, allOfvnfdDfMonitoringParameterItems.id) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllOfvnfdDfMonitoringParameterItems {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
