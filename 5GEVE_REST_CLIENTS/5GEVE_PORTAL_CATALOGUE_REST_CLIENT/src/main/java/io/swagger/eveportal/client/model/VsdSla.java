/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.eveportal.client.model;

import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * VsdSla
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class VsdSla {
  /**
   * Gets or Sets availabilityCoverage
   */
  @JsonAdapter(AvailabilityCoverageEnum.Adapter.class)
  public enum AvailabilityCoverageEnum {
    AVAILABILITY_COVERAGE_HIGH("AVAILABILITY_COVERAGE_HIGH"),
    
    AVAILABILITY_COVERAGE_MEDIUM("AVAILABILITY_COVERAGE_MEDIUM"),
    
    UNDEFINED("UNDEFINED");

    private String value;

    AvailabilityCoverageEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AvailabilityCoverageEnum fromValue(String text) {
      for (AvailabilityCoverageEnum b : AvailabilityCoverageEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<AvailabilityCoverageEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AvailabilityCoverageEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AvailabilityCoverageEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return AvailabilityCoverageEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("availabilityCoverage")
  private AvailabilityCoverageEnum availabilityCoverage = null;

  @SerializedName("lowCostRequired")
  private Boolean lowCostRequired = null;

  /**
   * Gets or Sets serviceCreationTime
   */
  @JsonAdapter(ServiceCreationTimeEnum.Adapter.class)
  public enum ServiceCreationTimeEnum {
    SERVICE_CREATION_TIME_LOW("SERVICE_CREATION_TIME_LOW"),
    
    SERVICE_CREATION_TIME_MEDIUM("SERVICE_CREATION_TIME_MEDIUM"),
    
    UNDEFINED("UNDEFINED");

    private String value;

    ServiceCreationTimeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ServiceCreationTimeEnum fromValue(String text) {
      for (ServiceCreationTimeEnum b : ServiceCreationTimeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ServiceCreationTimeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ServiceCreationTimeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ServiceCreationTimeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ServiceCreationTimeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("serviceCreationTime")
  private ServiceCreationTimeEnum serviceCreationTime = null;

  public VsdSla availabilityCoverage(AvailabilityCoverageEnum availabilityCoverage) {
    this.availabilityCoverage = availabilityCoverage;
    return this;
  }

   /**
   * Get availabilityCoverage
   * @return availabilityCoverage
  **/
  @ApiModelProperty(value = "")
  public AvailabilityCoverageEnum getAvailabilityCoverage() {
    return availabilityCoverage;
  }

  public void setAvailabilityCoverage(AvailabilityCoverageEnum availabilityCoverage) {
    this.availabilityCoverage = availabilityCoverage;
  }

  public VsdSla lowCostRequired(Boolean lowCostRequired) {
    this.lowCostRequired = lowCostRequired;
    return this;
  }

   /**
   * Get lowCostRequired
   * @return lowCostRequired
  **/
  @ApiModelProperty(value = "")
  public Boolean isLowCostRequired() {
    return lowCostRequired;
  }

  public void setLowCostRequired(Boolean lowCostRequired) {
    this.lowCostRequired = lowCostRequired;
  }

  public VsdSla serviceCreationTime(ServiceCreationTimeEnum serviceCreationTime) {
    this.serviceCreationTime = serviceCreationTime;
    return this;
  }

   /**
   * Get serviceCreationTime
   * @return serviceCreationTime
  **/
  @ApiModelProperty(value = "")
  public ServiceCreationTimeEnum getServiceCreationTime() {
    return serviceCreationTime;
  }

  public void setServiceCreationTime(ServiceCreationTimeEnum serviceCreationTime) {
    this.serviceCreationTime = serviceCreationTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VsdSla vsdSla = (VsdSla) o;
    return Objects.equals(this.availabilityCoverage, vsdSla.availabilityCoverage) &&
        Objects.equals(this.lowCostRequired, vsdSla.lowCostRequired) &&
        Objects.equals(this.serviceCreationTime, vsdSla.serviceCreationTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(availabilityCoverage, lowCostRequired, serviceCreationTime);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VsdSla {\n");
    
    sb.append("    availabilityCoverage: ").append(toIndentedString(availabilityCoverage)).append("\n");
    sb.append("    lowCostRequired: ").append(toIndentedString(lowCostRequired)).append("\n");
    sb.append("    serviceCreationTime: ").append(toIndentedString(serviceCreationTime)).append("\n");
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

