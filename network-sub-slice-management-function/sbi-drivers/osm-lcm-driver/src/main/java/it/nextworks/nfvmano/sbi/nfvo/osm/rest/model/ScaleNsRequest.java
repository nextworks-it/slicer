/*
 * OSM NB API featuring ETSI NFV SOL005
 * This is Open Source MANO Northbound API featuring ETSI NFV SOL005. For more information on OSM, you can visit [http://osm.etsi.org](http://osm.etsi.org). You can send us your comments and questions to OSM_TECH@list.etsi.org or join the [OpenSourceMANO Slack Workplace](https://join.slack.com/t/opensourcemano/shared_invite/enQtMzQ3MzYzNTQ0NDIyLWVkNTE4ZjZjNWI0ZTQyN2VhOTI1MjViMzU1NWYwMWM3ODI4NTQyY2VlODA2ZjczMWIyYTFkZWNiZmFkM2M2ZDk) 
 *
 * OpenAPI spec version: 1.0.0
 * Contact: OSM_TECH@list.etsi.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package it.nextworks.nfvmano.sbi.nfvo.osm.rest.model;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;
import java.util.Objects;

/**
 * ScaleNsRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-03-04T13:48:18.253Z[GMT]")
public class ScaleNsRequest {
  /**
   * Gets or Sets scaleType
   */
  @JsonAdapter(ScaleTypeEnum.Adapter.class)
  public enum ScaleTypeEnum {
    SCALE_VNF("SCALE_VNF");

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
    public static ScaleTypeEnum fromValue(String input) {
      for (ScaleTypeEnum b : ScaleTypeEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<ScaleTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ScaleTypeEnum enumeration) throws IOException {
        jsonWriter.value(String.valueOf(enumeration.getValue()));
      }

      @Override
      public ScaleTypeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return ScaleTypeEnum.fromValue((String)(value));
      }
    }
  }  @SerializedName("scaleType")
  private ScaleTypeEnum scaleType = null;

  @SerializedName("timeout_ns_scale")
  private Integer timeoutNsScale = null;

  @SerializedName("scaleVnfData")
  private ScaleNsRequestScaleVnfData scaleVnfData = null;

  public ScaleNsRequest scaleType(ScaleTypeEnum scaleType) {
    this.scaleType = scaleType;
    return this;
  }

   /**
   * Get scaleType
   * @return scaleType
  **/
  @Schema(required = true, description = "")
  public ScaleTypeEnum getScaleType() {
    return scaleType;
  }

  public void setScaleType(ScaleTypeEnum scaleType) {
    this.scaleType = scaleType;
  }

  public ScaleNsRequest timeoutNsScale(Integer timeoutNsScale) {
    this.timeoutNsScale = timeoutNsScale;
    return this;
  }

   /**
   * timeout for the scale operation
   * @return timeoutNsScale
  **/
  @Schema(description = "timeout for the scale operation")
  public Integer getTimeoutNsScale() {
    return timeoutNsScale;
  }

  public void setTimeoutNsScale(Integer timeoutNsScale) {
    this.timeoutNsScale = timeoutNsScale;
  }

  public ScaleNsRequest scaleVnfData(ScaleNsRequestScaleVnfData scaleVnfData) {
    this.scaleVnfData = scaleVnfData;
    return this;
  }

   /**
   * Get scaleVnfData
   * @return scaleVnfData
  **/
  @Schema(required = true, description = "")
  public ScaleNsRequestScaleVnfData getScaleVnfData() {
    return scaleVnfData;
  }

  public void setScaleVnfData(ScaleNsRequestScaleVnfData scaleVnfData) {
    this.scaleVnfData = scaleVnfData;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScaleNsRequest scaleNsRequest = (ScaleNsRequest) o;
    return Objects.equals(this.scaleType, scaleNsRequest.scaleType) &&
        Objects.equals(this.timeoutNsScale, scaleNsRequest.timeoutNsScale) &&
        Objects.equals(this.scaleVnfData, scaleNsRequest.scaleVnfData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scaleType, timeoutNsScale, scaleVnfData);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScaleNsRequest {\n");
    
    sb.append("    scaleType: ").append(toIndentedString(scaleType)).append("\n");
    sb.append("    timeoutNsScale: ").append(toIndentedString(timeoutNsScale)).append("\n");
    sb.append("    scaleVnfData: ").append(toIndentedString(scaleVnfData)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
