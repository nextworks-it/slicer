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
 * ConnectivityType
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class ConnectivityType {
  @SerializedName("flowPattern")
  private String flowPattern = null;

  /**
   * Gets or Sets layerProtocol
   */
  @JsonAdapter(LayerProtocolEnum.Adapter.class)
  public enum LayerProtocolEnum {
    ETHERNET("ETHERNET"),
    
    MPLS("MPLS"),
    
    ODU2("ODU2"),
    
    IPV4("IPV4"),
    
    IPV6("IPV6"),
    
    PSEUDO_WIRE("PSEUDO_WIRE"),
    
    OTSIA("OTSIA"),
    
    OCH("OCH"),
    
    OTU("OTU"),
    
    ODU("ODU"),
    
    SDM("SDM"),
    
    AROF("AROF"),
    
    NOT_SPECIFIED("NOT_SPECIFIED");

    private String value;

    LayerProtocolEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static LayerProtocolEnum fromValue(String text) {
      for (LayerProtocolEnum b : LayerProtocolEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<LayerProtocolEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final LayerProtocolEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public LayerProtocolEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return LayerProtocolEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("layerProtocol")
  private LayerProtocolEnum layerProtocol = null;

  public ConnectivityType flowPattern(String flowPattern) {
    this.flowPattern = flowPattern;
    return this;
  }

   /**
   * Get flowPattern
   * @return flowPattern
  **/
  @ApiModelProperty(value = "")
  public String getFlowPattern() {
    return flowPattern;
  }

  public void setFlowPattern(String flowPattern) {
    this.flowPattern = flowPattern;
  }

  public ConnectivityType layerProtocol(LayerProtocolEnum layerProtocol) {
    this.layerProtocol = layerProtocol;
    return this;
  }

   /**
   * Get layerProtocol
   * @return layerProtocol
  **/
  @ApiModelProperty(value = "")
  public LayerProtocolEnum getLayerProtocol() {
    return layerProtocol;
  }

  public void setLayerProtocol(LayerProtocolEnum layerProtocol) {
    this.layerProtocol = layerProtocol;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectivityType connectivityType = (ConnectivityType) o;
    return Objects.equals(this.flowPattern, connectivityType.flowPattern) &&
        Objects.equals(this.layerProtocol, connectivityType.layerProtocol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flowPattern, layerProtocol);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConnectivityType {\n");
    
    sb.append("    flowPattern: ").append(toIndentedString(flowPattern)).append("\n");
    sb.append("    layerProtocol: ").append(toIndentedString(layerProtocol)).append("\n");
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

