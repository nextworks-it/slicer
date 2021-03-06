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
 * AutoscalingAction
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class AutoscalingAction {
  @SerializedName("scaleNsToLevelData")
  private ScaleNsToLevelData scaleNsToLevelData = null;

  /**
   * Gets or Sets scaleType
   */
  @JsonAdapter(ScaleTypeEnum.Adapter.class)
  public enum ScaleTypeEnum {
    NS("SCALE_NS"),
    
    VNF("SCALE_VNF");

    private String value;

    ScaleTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ScaleTypeEnum fromValue(String text) {
      for (ScaleTypeEnum b : ScaleTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ScaleTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ScaleTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ScaleTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ScaleTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("scaleType")
  private ScaleTypeEnum scaleType = null;

  public AutoscalingAction scaleNsToLevelData(ScaleNsToLevelData scaleNsToLevelData) {
    this.scaleNsToLevelData = scaleNsToLevelData;
    return this;
  }

   /**
   * Get scaleNsToLevelData
   * @return scaleNsToLevelData
  **/
  @ApiModelProperty(value = "")
  public ScaleNsToLevelData getScaleNsToLevelData() {
    return scaleNsToLevelData;
  }

  public void setScaleNsToLevelData(ScaleNsToLevelData scaleNsToLevelData) {
    this.scaleNsToLevelData = scaleNsToLevelData;
  }

  public AutoscalingAction scaleType(ScaleTypeEnum scaleType) {
    this.scaleType = scaleType;
    return this;
  }

   /**
   * Get scaleType
   * @return scaleType
  **/
  @ApiModelProperty(value = "")
  public ScaleTypeEnum getScaleType() {
    return scaleType;
  }

  public void setScaleType(ScaleTypeEnum scaleType) {
    this.scaleType = scaleType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AutoscalingAction autoscalingAction = (AutoscalingAction) o;
    return Objects.equals(this.scaleNsToLevelData, autoscalingAction.scaleNsToLevelData) &&
        Objects.equals(this.scaleType, autoscalingAction.scaleType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scaleNsToLevelData, scaleType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AutoscalingAction {\n");
    
    sb.append("    scaleNsToLevelData: ").append(toIndentedString(scaleNsToLevelData)).append("\n");
    sb.append("    scaleType: ").append(toIndentedString(scaleType)).append("\n");
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

