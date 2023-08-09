package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



/**
 * SecurityGroupRuleSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class SecurityGroupRuleSchema   {
  /**
   * The direction in which the security group rule is applied. Permitted values: INGRESS, EGRESS. Defaults to INGRESS.
   */
  public enum DirectionEnum {
    INGRESS("ingress"),
    
    EGRESS("egress");

    private String value;

    DirectionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DirectionEnum fromValue(String text) {
      for (DirectionEnum b : DirectionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("direction")
  private DirectionEnum direction = null;

  /**
   * Indicates the protocol carried over the IP layer. Permitted values: any protocol defined in the IANA protocol registry, e.g. TCP, UDP, ICMP, etc. Defaults to TCP.
   */
  public enum ProtocolEnum {
    TCP("tcp"),
    
    UDP("udp"),
    
    ICMP("icmp");

    private String value;

    ProtocolEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ProtocolEnum fromValue(String text) {
      for (ProtocolEnum b : ProtocolEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("protocol")
  private ProtocolEnum protocol = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("port-range-max")
  private String portRangeMax = null;

  /**
   * Indicates the protocol carried over the Ethernet layer. Permitted values: IPV4, IPV6. Defaults to IPV4.
   */
  public enum EtherTypeEnum {
    IPV4("ipv4"),
    
    IPV6("ipv6");

    private String value;

    EtherTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static EtherTypeEnum fromValue(String text) {
      for (EtherTypeEnum b : EtherTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("ether-type")
  private EtherTypeEnum etherType = null;

  @JsonProperty("port-range-min")
  private String portRangeMin = null;

  @JsonProperty("security-group-rule_id")
  private String securityGroupRuleId = null;

  public SecurityGroupRuleSchema direction(DirectionEnum direction) {
    this.direction = direction;
    return this;
  }

  /**
   * The direction in which the security group rule is applied. Permitted values: INGRESS, EGRESS. Defaults to INGRESS.
   * @return direction
   **/
  //@Schema(description = "The direction in which the security group rule is applied. Permitted values: INGRESS, EGRESS. Defaults to INGRESS.")
  
    public DirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(DirectionEnum direction) {
    this.direction = direction;
  }

  public SecurityGroupRuleSchema protocol(ProtocolEnum protocol) {
    this.protocol = protocol;
    return this;
  }

  /**
   * Indicates the protocol carried over the IP layer. Permitted values: any protocol defined in the IANA protocol registry, e.g. TCP, UDP, ICMP, etc. Defaults to TCP.
   * @return protocol
   **/
  //@Schema(description = "Indicates the protocol carried over the IP layer. Permitted values: any protocol defined in the IANA protocol registry, e.g. TCP, UDP, ICMP, etc. Defaults to TCP.")
  
    public ProtocolEnum getProtocol() {
    return protocol;
  }

  public void setProtocol(ProtocolEnum protocol) {
    this.protocol = protocol;
  }

  public SecurityGroupRuleSchema description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human readable description of the security group rule.
   * @return description
   **/
  //@Schema(description = "Human readable description of the security group rule.")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public SecurityGroupRuleSchema portRangeMax(String portRangeMax) {
    this.portRangeMax = portRangeMax;
    return this;
  }

  /**
   * Indicates maximum port number in the range that is matched by the security group rule. Defaults to 65535.
   * @return portRangeMax
   **/
  //@Schema(description = "Indicates maximum port number in the range that is matched by the security group rule. Defaults to 65535.")
  
    public String getPortRangeMax() {
    return portRangeMax;
  }

  public void setPortRangeMax(String portRangeMax) {
    this.portRangeMax = portRangeMax;
  }

  public SecurityGroupRuleSchema etherType(EtherTypeEnum etherType) {
    this.etherType = etherType;
    return this;
  }

  /**
   * Indicates the protocol carried over the Ethernet layer. Permitted values: IPV4, IPV6. Defaults to IPV4.
   * @return etherType
   **/
  //@Schema(description = "Indicates the protocol carried over the Ethernet layer. Permitted values: IPV4, IPV6. Defaults to IPV4.")
  
    public EtherTypeEnum getEtherType() {
    return etherType;
  }

  public void setEtherType(EtherTypeEnum etherType) {
    this.etherType = etherType;
  }

  public SecurityGroupRuleSchema portRangeMin(String portRangeMin) {
    this.portRangeMin = portRangeMin;
    return this;
  }

  /**
   * Indicates minimum port number in the range that is matched by the security group rule. Defaults to 0.
   * @return portRangeMin
   **/
  //@Schema(description = "Indicates minimum port number in the range that is matched by the security group rule. Defaults to 0.")
  
    public String getPortRangeMin() {
    return portRangeMin;
  }

  public void setPortRangeMin(String portRangeMin) {
    this.portRangeMin = portRangeMin;
  }

  public SecurityGroupRuleSchema securityGroupRuleId(String securityGroupRuleId) {
    this.securityGroupRuleId = securityGroupRuleId;
    return this;
  }

  /**
   * Identifier of this SecurityGroupRule information element.
   * @return securityGroupRuleId
   **/
  //@Schema(description = "Identifier of this SecurityGroupRule information element.")
  
    public String getSecurityGroupRuleId() {
    return securityGroupRuleId;
  }

  public void setSecurityGroupRuleId(String securityGroupRuleId) {
    this.securityGroupRuleId = securityGroupRuleId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SecurityGroupRuleSchema securityGroupRuleSchema = (SecurityGroupRuleSchema) o;
    return Objects.equals(this.direction, securityGroupRuleSchema.direction) &&
        Objects.equals(this.protocol, securityGroupRuleSchema.protocol) &&
        Objects.equals(this.description, securityGroupRuleSchema.description) &&
        Objects.equals(this.portRangeMax, securityGroupRuleSchema.portRangeMax) &&
        Objects.equals(this.etherType, securityGroupRuleSchema.etherType) &&
        Objects.equals(this.portRangeMin, securityGroupRuleSchema.portRangeMin) &&
        Objects.equals(this.securityGroupRuleId, securityGroupRuleSchema.securityGroupRuleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(direction, protocol, description, portRangeMax, etherType, portRangeMin, securityGroupRuleId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SecurityGroupRuleSchema {\n");
    
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    portRangeMax: ").append(toIndentedString(portRangeMax)).append("\n");
    sb.append("    etherType: ").append(toIndentedString(etherType)).append("\n");
    sb.append("    portRangeMin: ").append(toIndentedString(portRangeMin)).append("\n");
    sb.append("    securityGroupRuleId: ").append(toIndentedString(securityGroupRuleId)).append("\n");
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
