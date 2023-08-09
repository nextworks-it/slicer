package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

/**
 * QoS of the VL.
 */
//@Schema(description = "QoS of the VL.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Embeddable
public class FlavourSchemaQos {

  @JsonProperty("packet-delay-variation")
  private String packetDelayVariation = null;

  @JsonProperty("latency")
  private String latency = null;

  @JsonProperty("packet-loss-ratio")
  private Double packetLossRatio = null;

  public FlavourSchemaQos packetDelayVariation(String packetDelayVariation) {
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

  public FlavourSchemaQos latency(String latency) {
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

  public FlavourSchemaQos packetLossRatio(Double packetLossRatio) {
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
    FlavourSchemaQos flavourSchemaQos = (FlavourSchemaQos) o;
    return Objects.equals(this.packetDelayVariation, flavourSchemaQos.packetDelayVariation) &&
        Objects.equals(this.latency, flavourSchemaQos.latency) &&
        Objects.equals(this.packetLossRatio, flavourSchemaQos.packetLossRatio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(packetDelayVariation, latency, packetLossRatio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlavourSchemaQos {\n");
    
    sb.append("    packetDelayVariation: ").append(toIndentedString(packetDelayVariation)).append("\n");
    sb.append("    latency: ").append(toIndentedString(latency)).append("\n");
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
