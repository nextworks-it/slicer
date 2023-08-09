package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This information element defines attributes that affect the invocation of the TerminateVnf operation.
 */
//@Schema(description = "This information element defines attributes that affect the invocation of the TerminateVnf operation.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
@Table(name = "vnfd_lcm_ops_config_terminate_vnf_op_config")
public class VnfdLcmoperationsconfigurationTerminatevnfopconfig {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("min-graceful-termination")
  private String minGracefulTermination = null;

  @JsonProperty("parameter")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<VnfdVdustoragerequirements> parameter = null;

  @JsonProperty("max-recommended-graceful-termination")
  private String maxRecommendedGracefulTermination = null;

  public VnfdLcmoperationsconfigurationTerminatevnfopconfig minGracefulTermination(String minGracefulTermination) {
    this.minGracefulTermination = minGracefulTermination;
    return this;
  }

  /**
   * Minimum timeout value for graceful termination of a VNF instance.
   * @return minGracefulTermination
   **/
  //@Schema(description = "Minimum timeout value for graceful termination of a VNF instance.")
  
    public String getMinGracefulTermination() {
    return minGracefulTermination;
  }

  public void setMinGracefulTermination(String minGracefulTermination) {
    this.minGracefulTermination = minGracefulTermination;
  }

  public VnfdLcmoperationsconfigurationTerminatevnfopconfig parameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
    return this;
  }

  public VnfdLcmoperationsconfigurationTerminatevnfopconfig addParameterItem(VnfdVdustoragerequirements parameterItem) {
    if (this.parameter == null) {
      this.parameter = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.parameter.add(parameterItem);
    return this;
  }

  /**
   * Array of KVP requirements for VNF-specific parameters to be passed when invoking the TerminateVnf operation.
   * @return parameter
   **/
  //@Schema(description = "Array of KVP requirements for VNF-specific parameters to be passed when invoking the TerminateVnf operation.")

    public List<VnfdVdustoragerequirements> getParameter() {
    return parameter;
  }

  public void setParameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
  }

  public VnfdLcmoperationsconfigurationTerminatevnfopconfig maxRecommendedGracefulTermination(String maxRecommendedGracefulTermination) {
    this.maxRecommendedGracefulTermination = maxRecommendedGracefulTermination;
    return this;
  }

  /**
   * Maximum recommended timeout value that can be needed to gracefully terminate a VNF instance of a particular type under certain conditions, such as maximum load condition. This is provided by VNF provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.
   * @return maxRecommendedGracefulTermination
   **/
  //@Schema(description = "Maximum recommended timeout value that can be needed to gracefully terminate a VNF instance of a particular type under certain conditions, such as maximum load condition. This is provided by VNF provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.")
  
    public String getMaxRecommendedGracefulTermination() {
    return maxRecommendedGracefulTermination;
  }

  public void setMaxRecommendedGracefulTermination(String maxRecommendedGracefulTermination) {
    this.maxRecommendedGracefulTermination = maxRecommendedGracefulTermination;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdLcmoperationsconfigurationTerminatevnfopconfig vnfdLcmoperationsconfigurationTerminatevnfopconfig = (VnfdLcmoperationsconfigurationTerminatevnfopconfig) o;
    return Objects.equals(this.minGracefulTermination, vnfdLcmoperationsconfigurationTerminatevnfopconfig.minGracefulTermination) &&
        Objects.equals(this.parameter, vnfdLcmoperationsconfigurationTerminatevnfopconfig.parameter) &&
        Objects.equals(this.maxRecommendedGracefulTermination, vnfdLcmoperationsconfigurationTerminatevnfopconfig.maxRecommendedGracefulTermination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minGracefulTermination, parameter, maxRecommendedGracefulTermination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdLcmoperationsconfigurationTerminatevnfopconfig {\n");
    
    sb.append("    minGracefulTermination: ").append(toIndentedString(minGracefulTermination)).append("\n");
    sb.append("    parameter: ").append(toIndentedString(parameter)).append("\n");
    sb.append("    maxRecommendedGracefulTermination: ").append(toIndentedString(maxRecommendedGracefulTermination)).append("\n");
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
