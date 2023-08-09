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
 * This information element defines attributes that affect the invocation of the OperateVnf operation.
 */
//@Schema(description = "This information element defines attributes that affect the invocation of the OperateVnf operation.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
@Table(name = "vnfd_lcm_ops_config_operate_vnf_op_config")
public class VnfdLcmoperationsconfigurationOperatevnfopconfig {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("parameter")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<VnfdVdustoragerequirements> parameter = null;

  @JsonProperty("max-recommended-graceful-stop-timeout")
  private String maxRecommendedGracefulStopTimeout = null;

  @JsonProperty("min-graceful-stop-timeout")
  private String minGracefulStopTimeout = null;

  @JsonProperty("day1-2")
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name = "operate-vnf-op-config_fk", referencedColumnName = "uuid")
  private List<Day1_2> day1_2 = null;

  public VnfdLcmoperationsconfigurationOperatevnfopconfig parameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
    return this;
  }

  public VnfdLcmoperationsconfigurationOperatevnfopconfig addParameterItem(VnfdVdustoragerequirements parameterItem) {
    if (this.parameter == null) {
      this.parameter = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.parameter.add(parameterItem);
    return this;
  }

  /**
   * Array of KVP requirements for VNF-specific parameters to be passed when invoking the OperateVnf operation.
   * @return parameter
   **/
  //@Schema(description = "Array of KVP requirements for VNF-specific parameters to be passed when invoking the OperateVnf operation.")

    public List<VnfdVdustoragerequirements> getParameter() {
    return parameter;
  }

  public void setParameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
  }

  public VnfdLcmoperationsconfigurationOperatevnfopconfig maxRecommendedGracefulStopTimeout(String maxRecommendedGracefulStopTimeout) {
    this.maxRecommendedGracefulStopTimeout = maxRecommendedGracefulStopTimeout;
    return this;
  }

  /**
   * Maximum recommended timeout value that can be needed to gracefully stop a VNF instance of a particular type under certain conditions, such as maximum load condition. This is provided by VNF provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.
   * @return maxRecommendedGracefulStopTimeout
   **/
  //@Schema(description = "Maximum recommended timeout value that can be needed to gracefully stop a VNF instance of a particular type under certain conditions, such as maximum load condition. This is provided by VNF provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.")
  
    public String getMaxRecommendedGracefulStopTimeout() {
    return maxRecommendedGracefulStopTimeout;
  }

  public void setMaxRecommendedGracefulStopTimeout(String maxRecommendedGracefulStopTimeout) {
    this.maxRecommendedGracefulStopTimeout = maxRecommendedGracefulStopTimeout;
  }

  public VnfdLcmoperationsconfigurationOperatevnfopconfig minGracefulStopTimeout(String minGracefulStopTimeout) {
    this.minGracefulStopTimeout = minGracefulStopTimeout;
    return this;
  }

  /**
   * Minimum timeout value for graceful stop of a VNF instance.
   * @return minGracefulStopTimeout
   **/
  //@Schema(description = "Minimum timeout value for graceful stop of a VNF instance.")
  
    public String getMinGracefulStopTimeout() {
    return minGracefulStopTimeout;
  }

  public void setMinGracefulStopTimeout(String minGracefulStopTimeout) {
    this.minGracefulStopTimeout = minGracefulStopTimeout;
  }

  public VnfdLcmoperationsconfigurationOperatevnfopconfig day1_2(List<Day1_2> day1_2) {
      this.day1_2 = day1_2;
      return this;
  }

  public VnfdLcmoperationsconfigurationOperatevnfopconfig addDay1_2Item(Day1_2 day1_2) {
      if(this.day1_2 == null)
        this.day1_2 = new ArrayList<>();

      this.day1_2.add(day1_2);
      return this;
  }

  public List<Day1_2> getDay1_2() { return day1_2; }

  public void setDay1_2(List<Day1_2> day1_2) { this.day1_2 = day1_2; }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdLcmoperationsconfigurationOperatevnfopconfig vnfdLcmoperationsconfigurationOperatevnfopconfig = (VnfdLcmoperationsconfigurationOperatevnfopconfig) o;
    return Objects.equals(this.parameter, vnfdLcmoperationsconfigurationOperatevnfopconfig.parameter) &&
            Objects.equals(this.maxRecommendedGracefulStopTimeout, vnfdLcmoperationsconfigurationOperatevnfopconfig.maxRecommendedGracefulStopTimeout) &&
            Objects.equals(this.minGracefulStopTimeout, vnfdLcmoperationsconfigurationOperatevnfopconfig.minGracefulStopTimeout) &&
            Objects.equals(this.day1_2, vnfdLcmoperationsconfigurationOperatevnfopconfig.day1_2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parameter, maxRecommendedGracefulStopTimeout, minGracefulStopTimeout, day1_2);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdLcmoperationsconfigurationOperatevnfopconfig {\n");
    
    sb.append("    parameter: ").append(toIndentedString(parameter)).append("\n");
    sb.append("    maxRecommendedGracefulStopTimeout: ").append(toIndentedString(maxRecommendedGracefulStopTimeout)).append("\n");
    sb.append("    minGracefulStopTimeout: ").append(toIndentedString(minGracefulStopTimeout)).append("\n");
    sb.append("    day1_2: ").append(toIndentedString(day1_2)).append("\n");
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
