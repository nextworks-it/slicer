package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * NsdMonitoringparameter
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdMonitoringparameter {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("performance-metric")
  private String performanceMetric = null;

  @JsonProperty("collection-period")
  private String collectionPeriod = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  public NsdMonitoringparameter performanceMetric(String performanceMetric) {
    this.performanceMetric = performanceMetric;
    return this;
  }

  /**
   * Defines the virtualised resource-related performance metric.
   * @return performanceMetric
   **/
  //@Schema(description = "Defines the virtualised resource-related performance metric.")
  
    public String getPerformanceMetric() {
    return performanceMetric;
  }

  public void setPerformanceMetric(String performanceMetric) {
    this.performanceMetric = performanceMetric;
  }

  public NsdMonitoringparameter collectionPeriod(String collectionPeriod) {
    this.collectionPeriod = collectionPeriod;
    return this;
  }

  /**
   * An attribute that describes the periodicity at which to collect the performance information.
   * @return collectionPeriod
   **/
  //@Schema(description = "An attribute that describes the periodicity at which to collect the performance information.")
  
    public String getCollectionPeriod() {
    return collectionPeriod;
  }

  public void setCollectionPeriod(String collectionPeriod) {
    this.collectionPeriod = collectionPeriod;
  }

  public NsdMonitoringparameter id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of this monitoring parameter information element.
   * @return id
   **/
  //@Schema(description = "Unique identifier of this monitoring parameter information element.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public NsdMonitoringparameter name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Human readable name of the monitoring parameter.
   * @return name
   **/
  //@Schema(description = "Human readable name of the monitoring parameter.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdMonitoringparameter nsdMonitoringparameter = (NsdMonitoringparameter) o;
    return Objects.equals(this.performanceMetric, nsdMonitoringparameter.performanceMetric) &&
        Objects.equals(this.collectionPeriod, nsdMonitoringparameter.collectionPeriod) &&
        Objects.equals(this.id, nsdMonitoringparameter.id) &&
        Objects.equals(this.name, nsdMonitoringparameter.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(performanceMetric, collectionPeriod, id, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdMonitoringparameter {\n");
    
    sb.append("    performanceMetric: ").append(toIndentedString(performanceMetric)).append("\n");
    sb.append("    collectionPeriod: ").append(toIndentedString(collectionPeriod)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
