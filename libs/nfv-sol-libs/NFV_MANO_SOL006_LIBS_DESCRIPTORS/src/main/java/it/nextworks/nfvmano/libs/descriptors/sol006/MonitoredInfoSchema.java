package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;


/**
 * MonitoredInfoSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class MonitoredInfoSchema   {
  @JsonProperty("monitored-info_id")
  private String monitoredInfoId = null;

  @JsonProperty("monitoring-parameter")
  private NsdMonitoringparameter monitoringParameter = null;

  @JsonProperty("vnf-indicator-info")
  private NsdVnfindicatorinfo vnfIndicatorInfo = null;

  public MonitoredInfoSchema monitoredInfoId(String monitoredInfoId) {
    this.monitoredInfoId = monitoredInfoId;
    return this;
  }

  /**
   * Get monitoredInfoId
   * @return monitoredInfoId
   **/
  //@Schema(description = "")
  
    public String getMonitoredInfoId() {
    return monitoredInfoId;
  }

  public void setMonitoredInfoId(String monitoredInfoId) {
    this.monitoredInfoId = monitoredInfoId;
  }

  public MonitoredInfoSchema monitoringParameter(NsdMonitoringparameter monitoringParameter) {
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

  public MonitoredInfoSchema vnfIndicatorInfo(NsdVnfindicatorinfo vnfIndicatorInfo) {
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
    MonitoredInfoSchema monitoredInfoSchema = (MonitoredInfoSchema) o;
    return Objects.equals(this.monitoredInfoId, monitoredInfoSchema.monitoredInfoId) &&
        Objects.equals(this.monitoringParameter, monitoredInfoSchema.monitoringParameter) &&
        Objects.equals(this.vnfIndicatorInfo, monitoredInfoSchema.vnfIndicatorInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(monitoredInfoId, monitoringParameter, vnfIndicatorInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MonitoredInfoSchema {\n");
    
    sb.append("    monitoredInfoId: ").append(toIndentedString(monitoredInfoId)).append("\n");
    sb.append("    monitoringParameter: ").append(toIndentedString(monitoringParameter)).append("\n");
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
