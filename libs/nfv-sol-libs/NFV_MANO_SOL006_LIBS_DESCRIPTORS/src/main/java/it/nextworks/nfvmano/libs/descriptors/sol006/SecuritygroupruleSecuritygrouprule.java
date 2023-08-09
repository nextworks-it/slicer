package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.DirectionEnum;
import it.nextworks.nfvmano.libs.common.enums.EtherTypeEnum;
import it.nextworks.nfvmano.libs.common.enums.ProtocolEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * SecuritygroupruleSecuritygrouprule
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
public class SecuritygroupruleSecuritygrouprule {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("direction")
  private DirectionEnum direction = null;

  @JsonProperty("protocol")
  private ProtocolEnum protocol = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("port-range-max")
  private String portRangeMax = null;

  @JsonProperty("ether-type")
  private EtherTypeEnum etherType = null;

  @JsonProperty("port-range-min")
  private String portRangeMin = null;

  @JsonProperty("id")
  private String id = null;

  public SecuritygroupruleSecuritygrouprule direction(DirectionEnum direction) {
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

  public SecuritygroupruleSecuritygrouprule protocol(ProtocolEnum protocol) {
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

  public SecuritygroupruleSecuritygrouprule description(String description) {
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

  public SecuritygroupruleSecuritygrouprule portRangeMax(String portRangeMax) {
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

  public SecuritygroupruleSecuritygrouprule etherType(EtherTypeEnum etherType) {
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

  public SecuritygroupruleSecuritygrouprule portRangeMin(String portRangeMin) {
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

  public SecuritygroupruleSecuritygrouprule id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this SecurityGroupRule information element.
   * @return id
   **/
  //@Schema(description = "Identifier of this SecurityGroupRule information element.")
  
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
    SecuritygroupruleSecuritygrouprule securitygroupruleSecuritygrouprule = (SecuritygroupruleSecuritygrouprule) o;
    return Objects.equals(this.direction, securitygroupruleSecuritygrouprule.direction) &&
        Objects.equals(this.protocol, securitygroupruleSecuritygrouprule.protocol) &&
        Objects.equals(this.description, securitygroupruleSecuritygrouprule.description) &&
        Objects.equals(this.portRangeMax, securitygroupruleSecuritygrouprule.portRangeMax) &&
        Objects.equals(this.etherType, securitygroupruleSecuritygrouprule.etherType) &&
        Objects.equals(this.portRangeMin, securitygroupruleSecuritygrouprule.portRangeMin) &&
        Objects.equals(this.id, securitygroupruleSecuritygrouprule.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(direction, protocol, description, portRangeMax, etherType, portRangeMin, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SecuritygroupruleSecuritygrouprule {\n");
    
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    portRangeMax: ").append(toIndentedString(portRangeMax)).append("\n");
    sb.append("    etherType: ").append(toIndentedString(etherType)).append("\n");
    sb.append("    portRangeMin: ").append(toIndentedString(portRangeMin)).append("\n");
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
