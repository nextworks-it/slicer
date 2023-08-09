package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The QoS information element specifies quality of service parameters applicable to a VL.
 */
//@Schema(description = "The QoS information element specifies quality of service parameters applicable to a VL.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class QosSchema {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("packet-delay-variation")
  private String packetDelayVariation = null;

  @JsonProperty("latency")
  private String latency = null;

  @JsonProperty("priority")
  private String priority = null;

  @JsonProperty("packet-loss-ratio")
  private Double packetLossRatio = null;

  public QosSchema packetDelayVariation(String packetDelayVariation) {
    this.packetDelayVariation = packetDelayVariation;
    return this;
  }

  /**
   * Specifies the maximum jitter in ms.
   * @return packetDelayVariation
   **/
  //@Schema(description = "Specifies the maximum jitter in ms.")
  
    public String getPacketDelayVariation() {
    return packetDelayVariation;
  }

  public void setPacketDelayVariation(String packetDelayVariation) {
    this.packetDelayVariation = packetDelayVariation;
  }

  public QosSchema latency(String latency) {
    this.latency = latency;
    return this;
  }

  /**
   * Specifies the maximum latency in ms.
   * @return latency
   **/
  //@Schema(description = "Specifies the maximum latency in ms.")
  
    public String getLatency() {
    return latency;
  }

  public void setLatency(String latency) {
    this.latency = latency;
  }

  public QosSchema priority(String priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Specifies the priority level in case of congestion on the underlying physical links.
   * @return priority
   **/
  //@Schema(description = "Specifies the priority level in case of congestion on the underlying physical links.")
  
    public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public QosSchema packetLossRatio(Double packetLossRatio) {
    this.packetLossRatio = packetLossRatio;
    return this;
  }

  /**
   * Specifies the maximum packet loss ratio.
   * @return packetLossRatio
   **/
  //@Schema(description = "Specifies the maximum packet loss ratio.")
  
    public Double getPacketLossRatio() {
    return packetLossRatio;
  }

  public void setPacketLossRatio(Double packetLossRatio) {
    this.packetLossRatio = packetLossRatio;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QosSchema qosSchema = (QosSchema) o;
    return Objects.equals(this.packetDelayVariation, qosSchema.packetDelayVariation) &&
        Objects.equals(this.latency, qosSchema.latency) &&
        Objects.equals(this.priority, qosSchema.priority) &&
        Objects.equals(this.packetLossRatio, qosSchema.packetLossRatio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(packetDelayVariation, latency, priority, packetLossRatio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QosSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    packetDelayVariation: ").append(toIndentedString(packetDelayVariation)).append("\n");
    sb.append("    latency: ").append(toIndentedString(latency)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    packetLossRatio: ").append(toIndentedString(packetLossRatio)).append("\n");
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
