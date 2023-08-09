package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * NsdMonitoredinfo
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class NsdMonitoredinfo {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("monitoring-parameter")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "monitoring_parameter_fk", referencedColumnName = "uuid")
  private NsdMonitoringparameter monitoringParameter = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("vnf-indicator-info")
  @Embedded
  private NsdVnfindicatorinfo vnfIndicatorInfo = null;

  public NsdMonitoredinfo monitoringParameter(NsdMonitoringparameter monitoringParameter) {
    this.monitoringParameter = monitoringParameter;
    return this;
  }

  /**
   * Get monitoringParameter
   * @return monitoringParameter
   **/
  //@Schema(description = "")
  

    public NsdMonitoringparameter getMonitoringParameter() {
    return monitoringParameter;
  }

  public void setMonitoringParameter(NsdMonitoringparameter monitoringParameter) {
    this.monitoringParameter = monitoringParameter;
  }

  public NsdMonitoredinfo id(String id) {
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

  public NsdMonitoredinfo vnfIndicatorInfo(NsdVnfindicatorinfo vnfIndicatorInfo) {
    this.vnfIndicatorInfo = vnfIndicatorInfo;
    return this;
  }

  /**
   * Get vnfIndicatorInfo
   * @return vnfIndicatorInfo
   **/
  //@Schema(description = "")
  

    public NsdVnfindicatorinfo getVnfIndicatorInfo() {
    return vnfIndicatorInfo;
  }

  public void setVnfIndicatorInfo(NsdVnfindicatorinfo vnfIndicatorInfo) {
    this.vnfIndicatorInfo = vnfIndicatorInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsdMonitoredinfo nsdMonitoredinfo = (NsdMonitoredinfo) o;
    return Objects.equals(this.monitoringParameter, nsdMonitoredinfo.monitoringParameter) &&
        Objects.equals(this.id, nsdMonitoredinfo.id) &&
        Objects.equals(this.vnfIndicatorInfo, nsdMonitoredinfo.vnfIndicatorInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(monitoringParameter, id, vnfIndicatorInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsdMonitoredinfo {\n");
    
    sb.append("    monitoringParameter: ").append(toIndentedString(monitoringParameter)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    vnfIndicatorInfo: ").append(toIndentedString(vnfIndicatorInfo)).append("\n");
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
