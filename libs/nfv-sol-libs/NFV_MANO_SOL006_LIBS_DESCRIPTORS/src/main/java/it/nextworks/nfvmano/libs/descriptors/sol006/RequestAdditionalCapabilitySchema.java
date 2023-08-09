package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;



/**
 * RequestAdditionalCapabilitySchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class RequestAdditionalCapabilitySchema   {
  @JsonProperty("support-mandatory")
  private Boolean supportMandatory = null;

  @JsonProperty("min-version")
  private String minVersion = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("target-performance-parameters")

  private List<VnfdVdustoragerequirements> targetPerformanceParameters = null;

  @JsonProperty("preferred-version")
  private String preferredVersion = null;

  public RequestAdditionalCapabilitySchema supportMandatory(Boolean supportMandatory) {
    this.supportMandatory = supportMandatory;
    return this;
  }

  /**
   * Indicates whether the requested additional capability is mandatory for successful operation.
   * @return supportMandatory
   **/
  //@Schema(description = "Indicates whether the requested additional capability is mandatory for successful operation.")
  
    public Boolean isSupportMandatory() {
    return supportMandatory;
  }

  public void setSupportMandatory(Boolean supportMandatory) {
    this.supportMandatory = supportMandatory;
  }

  public RequestAdditionalCapabilitySchema minVersion(String minVersion) {
    this.minVersion = minVersion;
    return this;
  }

  /**
   * Identifies the minimum version of the requested additional capability.
   * @return minVersion
   **/
  //@Schema(description = "Identifies the minimum version of the requested additional capability.")
  
    public String getMinVersion() {
    return minVersion;
  }

  public void setMinVersion(String minVersion) {
    this.minVersion = minVersion;
  }

  public RequestAdditionalCapabilitySchema name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Identifies a requested additional capability for the VDU. ETSI GS NFV-IFA 002 [i.1] describes acceleration capabilities.
   * @return name
   **/
  //@Schema(description = "Identifies a requested additional capability for the VDU. ETSI GS NFV-IFA 002 [i.1] describes acceleration capabilities.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RequestAdditionalCapabilitySchema targetPerformanceParameters(List<VnfdVdustoragerequirements> targetPerformanceParameters) {
    this.targetPerformanceParameters = targetPerformanceParameters;
    return this;
  }

  public RequestAdditionalCapabilitySchema addTargetPerformanceParametersItem(VnfdVdustoragerequirements targetPerformanceParametersItem) {
    if (this.targetPerformanceParameters == null) {
      this.targetPerformanceParameters = new ArrayList<VnfdVdustoragerequirements>();
    }
    this.targetPerformanceParameters.add(targetPerformanceParametersItem);
    return this;
  }

  /**
   * Identifies specific attributes, dependent on the requested additional capability type.
   * @return targetPerformanceParameters
   **/
  //@Schema(description = "Identifies specific attributes, dependent on the requested additional capability type.")

    public List<VnfdVdustoragerequirements> getTargetPerformanceParameters() {
    return targetPerformanceParameters;
  }

  public void setTargetPerformanceParameters(List<VnfdVdustoragerequirements> targetPerformanceParameters) {
    this.targetPerformanceParameters = targetPerformanceParameters;
  }

  public RequestAdditionalCapabilitySchema preferredVersion(String preferredVersion) {
    this.preferredVersion = preferredVersion;
    return this;
  }

  /**
   * Identifies the preferred version of the requested additional capability.
   * @return preferredVersion
   **/
  //@Schema(description = "Identifies the preferred version of the requested additional capability.")
  
    public String getPreferredVersion() {
    return preferredVersion;
  }

  public void setPreferredVersion(String preferredVersion) {
    this.preferredVersion = preferredVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestAdditionalCapabilitySchema requestAdditionalCapabilitySchema = (RequestAdditionalCapabilitySchema) o;
    return Objects.equals(this.supportMandatory, requestAdditionalCapabilitySchema.supportMandatory) &&
        Objects.equals(this.minVersion, requestAdditionalCapabilitySchema.minVersion) &&
        Objects.equals(this.name, requestAdditionalCapabilitySchema.name) &&
        Objects.equals(this.targetPerformanceParameters, requestAdditionalCapabilitySchema.targetPerformanceParameters) &&
        Objects.equals(this.preferredVersion, requestAdditionalCapabilitySchema.preferredVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(supportMandatory, minVersion, name, targetPerformanceParameters, preferredVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestAdditionalCapabilitySchema {\n");
    
    sb.append("    supportMandatory: ").append(toIndentedString(supportMandatory)).append("\n");
    sb.append("    minVersion: ").append(toIndentedString(minVersion)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    targetPerformanceParameters: ").append(toIndentedString(targetPerformanceParameters)).append("\n");
    sb.append("    preferredVersion: ").append(toIndentedString(preferredVersion)).append("\n");
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
