/*
 * 5GEVE Experiment LCM
 * The API of the 5GEVE Experiment Lifecycle Manager
 *
 * OpenAPI spec version: 1.0
 * Contact: info@nextworks.it
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.elcm.client.model;

import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * UpdateExperimentStatusRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-27T08:54:11.910Z")



public class UpdateExperimentStatusRequest {
  @SerializedName("experimentId")
  private String experimentId = null;

  /**
   * Gets or Sets status
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    SCHEDULING("SCHEDULING"),
    
    ACCEPTED("ACCEPTED"),
    
    READY("READY"),
    
    INSTANTIATING("INSTANTIATING"),
    
    INSTANTIATED("INSTANTIATED"),
    
    RUNNING_EXECUTION("RUNNING_EXECUTION"),
    
    TERMINATING("TERMINATING"),
    
    TERMINATED("TERMINATED"),
    
    FAILED("FAILED"),
    
    REFUSED("REFUSED"),
    
    ABORTED("ABORTED");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return StatusEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("status")
  private StatusEnum status = null;

  public UpdateExperimentStatusRequest experimentId(String experimentId) {
    this.experimentId = experimentId;
    return this;
  }

   /**
   * Get experimentId
   * @return experimentId
  **/
  @ApiModelProperty(value = "")
  public String getExperimentId() {
    return experimentId;
  }

  public void setExperimentId(String experimentId) {
    this.experimentId = experimentId;
  }

  public UpdateExperimentStatusRequest status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateExperimentStatusRequest updateExperimentStatusRequest = (UpdateExperimentStatusRequest) o;
    return Objects.equals(this.experimentId, updateExperimentStatusRequest.experimentId) &&
        Objects.equals(this.status, updateExperimentStatusRequest.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(experimentId, status);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateExperimentStatusRequest {\n");
    
    sb.append("    experimentId: ").append(toIndentedString(experimentId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
