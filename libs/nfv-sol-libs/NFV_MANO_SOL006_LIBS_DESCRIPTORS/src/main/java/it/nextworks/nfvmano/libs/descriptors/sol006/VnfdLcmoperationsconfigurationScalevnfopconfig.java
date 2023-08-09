package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Configuration parameters for the ScaleVnf operation.
 */
//@Schema(description = "Configuration parameters for the ScaleVnf operation.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")

@Entity
@Table(name = "vnfd_lcm_ops_config_scale_vnf_op_config")
public class VnfdLcmoperationsconfigurationScalevnfopconfig {

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("parameter")
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<VnfdVdustoragerequirements> parameter = null;

  @JsonProperty("scaling-by-more-than-one-step-supported")
  private Boolean scalingByMoreThanOneStepSupported = null;

  public VnfdLcmoperationsconfigurationScalevnfopconfig parameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
    return this;
  }

  public VnfdLcmoperationsconfigurationScalevnfopconfig addParameterItem(VnfdVdustoragerequirements parameterItem) {
    if (this.parameter == null) {
      this.parameter = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.parameter.add(parameterItem);
    return this;
  }

  /**
   * Array of KVP requirements for VNFspecific parameters to be passed when invoking the ScaleVnf operation.
   * @return parameter
   **/
  //@Schema(description = "Array of KVP requirements for VNFspecific parameters to be passed when invoking the ScaleVnf operation.")

    public List<VnfdVdustoragerequirements> getParameter() {
    return parameter;
  }

  public void setParameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
  }

  public VnfdLcmoperationsconfigurationScalevnfopconfig scalingByMoreThanOneStepSupported(Boolean scalingByMoreThanOneStepSupported) {
    this.scalingByMoreThanOneStepSupported = scalingByMoreThanOneStepSupported;
    return this;
  }

  /**
   * Signals whether passing a value larger than one in the numScalingSteps parameter of the ScaleVnf operation is supported by this VNF. Default is FALSE, i.e. 'not supported'.
   * @return scalingByMoreThanOneStepSupported
   **/
  //@Schema(description = "Signals whether passing a value larger than one in the numScalingSteps parameter of the ScaleVnf operation is supported by this VNF. Default is FALSE, i.e. 'not supported'.")
  
    public Boolean isScalingByMoreThanOneStepSupported() {
    return scalingByMoreThanOneStepSupported;
  }

  public void setScalingByMoreThanOneStepSupported(Boolean scalingByMoreThanOneStepSupported) {
    this.scalingByMoreThanOneStepSupported = scalingByMoreThanOneStepSupported;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfdLcmoperationsconfigurationScalevnfopconfig vnfdLcmoperationsconfigurationScalevnfopconfig = (VnfdLcmoperationsconfigurationScalevnfopconfig) o;
    return Objects.equals(this.parameter, vnfdLcmoperationsconfigurationScalevnfopconfig.parameter) &&
        Objects.equals(this.scalingByMoreThanOneStepSupported, vnfdLcmoperationsconfigurationScalevnfopconfig.scalingByMoreThanOneStepSupported);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parameter, scalingByMoreThanOneStepSupported);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfdLcmoperationsconfigurationScalevnfopconfig {\n");
    
    sb.append("    parameter: ").append(toIndentedString(parameter)).append("\n");
    sb.append("    scalingByMoreThanOneStepSupported: ").append(toIndentedString(scalingByMoreThanOneStepSupported)).append("\n");
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
