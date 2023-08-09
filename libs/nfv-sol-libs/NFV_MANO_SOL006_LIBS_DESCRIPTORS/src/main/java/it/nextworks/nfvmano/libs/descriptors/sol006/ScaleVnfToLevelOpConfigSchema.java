package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * This information element defines attributes that affect the invocation of the ScaleVnfToLevel operation.
 */
//@Schema(description = "This information element defines attributes that affect the invocation of the ScaleVnfToLevel operation.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class ScaleVnfToLevelOpConfigSchema   {
  @JsonProperty("arbitrary-target-levels-supported")
  private Boolean arbitraryTargetLevelsSupported = null;

  @JsonProperty("parameter")

  private List<VnfdVdustoragerequirements> parameter = null;

  public ScaleVnfToLevelOpConfigSchema arbitraryTargetLevelsSupported(Boolean arbitraryTargetLevelsSupported) {
    this.arbitraryTargetLevelsSupported = arbitraryTargetLevelsSupported;
    return this;
  }

  /**
   * Signals whether scaling according to the parameter 'scaleInfo' is supported by this VNF.
   * @return arbitraryTargetLevelsSupported
   **/
  //@Schema(description = "Signals whether scaling according to the parameter 'scaleInfo' is supported by this VNF.")
  
    public Boolean isArbitraryTargetLevelsSupported() {
    return arbitraryTargetLevelsSupported;
  }

  public void setArbitraryTargetLevelsSupported(Boolean arbitraryTargetLevelsSupported) {
    this.arbitraryTargetLevelsSupported = arbitraryTargetLevelsSupported;
  }

  public ScaleVnfToLevelOpConfigSchema parameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
    return this;
  }

  public ScaleVnfToLevelOpConfigSchema addParameterItem(VnfdVdustoragerequirements parameterItem) {
    if (this.parameter == null) {
      this.parameter = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.parameter.add(parameterItem);
    return this;
  }

  /**
   * Array of KVP requirements for VNF-specific parameters to be passed when invoking the ScaleVnfToLevel operation.
   * @return parameter
   **/
  //@Schema(description = "Array of KVP requirements for VNF-specific parameters to be passed when invoking the ScaleVnfToLevel operation.")

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
    ScaleVnfToLevelOpConfigSchema scaleVnfToLevelOpConfigSchema = (ScaleVnfToLevelOpConfigSchema) o;
    return Objects.equals(this.arbitraryTargetLevelsSupported, scaleVnfToLevelOpConfigSchema.arbitraryTargetLevelsSupported) &&
        Objects.equals(this.parameter, scaleVnfToLevelOpConfigSchema.parameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(arbitraryTargetLevelsSupported, parameter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScaleVnfToLevelOpConfigSchema {\n");
    
    sb.append("    arbitraryTargetLevelsSupported: ").append(toIndentedString(arbitraryTargetLevelsSupported)).append("\n");
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
