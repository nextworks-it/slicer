package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * This information element defines attributes that affect the invocation of the HealVnf operation.
 */
//@Schema(description = "This information element defines attributes that affect the invocation of the HealVnf operation.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
@Table(name = "vnfd_lcm_ops_config_heal_vnf_op_config")
public class VnfdLcmoperationsconfigurationHealvnfopconfig {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("cause")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> cause = null;

  @JsonProperty("parameter")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<VnfdVdustoragerequirements> parameter = null;

  public VnfdLcmoperationsconfigurationHealvnfopconfig cause(List<String> cause) {
    this.cause = cause;
    return this;
  }

  public VnfdLcmoperationsconfigurationHealvnfopconfig addCauseItem(String causeItem) {
    if (this.cause == null) {
      this.cause = new ArrayList<String>();
    }
    this.cause.add(causeItem);
    return this;
  }

  /**
   * Get cause
   * @return cause
   **/
  //@Schema(description = "")
  
    public List<String> getCause() {
    return cause;
  }

  public void setCause(List<String> cause) {
    this.cause = cause;
  }

  public VnfdLcmoperationsconfigurationHealvnfopconfig parameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
    return this;
  }

  public VnfdLcmoperationsconfigurationHealvnfopconfig addParameterItem(VnfdVdustoragerequirements parameterItem) {
    if (this.parameter == null) {
      this.parameter = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.parameter.add(parameterItem);
    return this;
  }

  /**
   * Array of KVP requirements for VNF-specific parameters to be passed when invoking the HealVnf operation.
   * @return parameter
   **/
  //@Schema(description = "Array of KVP requirements for VNF-specific parameters to be passed when invoking the HealVnf operation.")

    public List<VnfdVdustoragerequirements> getParameter() {
    return parameter;
  }

  public void setParameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdLcmoperationsconfigurationHealvnfopconfig vnfdLcmoperationsconfigurationHealvnfopconfig = (VnfdLcmoperationsconfigurationHealvnfopconfig) o;
    return Objects.equals(this.cause, vnfdLcmoperationsconfigurationHealvnfopconfig.cause) &&
        Objects.equals(this.parameter, vnfdLcmoperationsconfigurationHealvnfopconfig.parameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cause, parameter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdLcmoperationsconfigurationHealvnfopconfig {\n");
    
    sb.append("    cause: ").append(toIndentedString(cause)).append("\n");
    sb.append("    parameter: ").append(toIndentedString(parameter)).append("\n");
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
