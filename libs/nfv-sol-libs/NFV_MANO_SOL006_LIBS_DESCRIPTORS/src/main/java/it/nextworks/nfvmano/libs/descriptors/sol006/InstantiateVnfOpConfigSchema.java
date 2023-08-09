package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * Configuration parameters for the InstantiateVnf operation.
 */
//@Schema(description = "Configuration parameters for the InstantiateVnf operation.")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class InstantiateVnfOpConfigSchema   {
  @JsonProperty("parameter")

  private List<VnfdVdustoragerequirements> parameter = null;

  public InstantiateVnfOpConfigSchema parameter(List<VnfdVdustoragerequirements> parameter) {
    this.parameter = parameter;
    return this;
  }

  public InstantiateVnfOpConfigSchema addParameterItem(VnfdVdustoragerequirements parameterItem) {
    if (this.parameter == null) {
      this.parameter = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.parameter.add(parameterItem);
    return this;
  }

  /**
   * Array of KVP requirements for VNF-specific parameters to be passed when invoking the InstantiateVnf operation.
   * @return parameter
   **/
  //@Schema(description = "Array of KVP requirements for VNF-specific parameters to be passed when invoking the InstantiateVnf operation.")

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
    InstantiateVnfOpConfigSchema instantiateVnfOpConfigSchema = (InstantiateVnfOpConfigSchema) o;
    return Objects.equals(this.parameter, instantiateVnfOpConfigSchema.parameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parameter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstantiateVnfOpConfigSchema {\n");
    
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
