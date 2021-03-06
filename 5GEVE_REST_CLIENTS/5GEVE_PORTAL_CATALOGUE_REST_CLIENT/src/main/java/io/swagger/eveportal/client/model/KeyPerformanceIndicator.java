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
import java.util.ArrayList;
import java.util.List;

/**
 * KeyPerformanceIndicator
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class KeyPerformanceIndicator {
  @SerializedName("formula")
  private String formula = null;

  @SerializedName("interval")
  private String interval = null;

  /**
   * Gets or Sets kpiGraphType
   */
  @JsonAdapter(KpiGraphTypeEnum.Adapter.class)
  public enum KpiGraphTypeEnum {
    LINE("LINE"),
    
    PIE("PIE"),
    
    COUNTER("COUNTER"),
    
    GAUGE("GAUGE");

    private String value;

    KpiGraphTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static KpiGraphTypeEnum fromValue(String text) {
      for (KpiGraphTypeEnum b : KpiGraphTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<KpiGraphTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final KpiGraphTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public KpiGraphTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return KpiGraphTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("kpiGraphType")
  private KpiGraphTypeEnum kpiGraphType = null;

  @SerializedName("kpiId")
  private String kpiId = null;

  @SerializedName("metricIds")
  private List<String> metricIds = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("unit")
  private String unit = null;

  public KeyPerformanceIndicator formula(String formula) {
    this.formula = formula;
    return this;
  }

   /**
   * Get formula
   * @return formula
  **/
  @ApiModelProperty(value = "")
  public String getFormula() {
    return formula;
  }

  public void setFormula(String formula) {
    this.formula = formula;
  }

  public KeyPerformanceIndicator interval(String interval) {
    this.interval = interval;
    return this;
  }

   /**
   * Get interval
   * @return interval
  **/
  @ApiModelProperty(value = "")
  public String getInterval() {
    return interval;
  }

  public void setInterval(String interval) {
    this.interval = interval;
  }

  public KeyPerformanceIndicator kpiGraphType(KpiGraphTypeEnum kpiGraphType) {
    this.kpiGraphType = kpiGraphType;
    return this;
  }

   /**
   * Get kpiGraphType
   * @return kpiGraphType
  **/
  @ApiModelProperty(value = "")
  public KpiGraphTypeEnum getKpiGraphType() {
    return kpiGraphType;
  }

  public void setKpiGraphType(KpiGraphTypeEnum kpiGraphType) {
    this.kpiGraphType = kpiGraphType;
  }

  public KeyPerformanceIndicator kpiId(String kpiId) {
    this.kpiId = kpiId;
    return this;
  }

   /**
   * Get kpiId
   * @return kpiId
  **/
  @ApiModelProperty(value = "")
  public String getKpiId() {
    return kpiId;
  }

  public void setKpiId(String kpiId) {
    this.kpiId = kpiId;
  }

  public KeyPerformanceIndicator metricIds(List<String> metricIds) {
    this.metricIds = metricIds;
    return this;
  }

  public KeyPerformanceIndicator addMetricIdsItem(String metricIdsItem) {
    if (this.metricIds == null) {
      this.metricIds = new ArrayList<String>();
    }
    this.metricIds.add(metricIdsItem);
    return this;
  }

   /**
   * Get metricIds
   * @return metricIds
  **/
  @ApiModelProperty(value = "")
  public List<String> getMetricIds() {
    return metricIds;
  }

  public void setMetricIds(List<String> metricIds) {
    this.metricIds = metricIds;
  }

  public KeyPerformanceIndicator name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public KeyPerformanceIndicator unit(String unit) {
    this.unit = unit;
    return this;
  }

   /**
   * Get unit
   * @return unit
  **/
  @ApiModelProperty(value = "")
  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeyPerformanceIndicator keyPerformanceIndicator = (KeyPerformanceIndicator) o;
    return Objects.equals(this.formula, keyPerformanceIndicator.formula) &&
        Objects.equals(this.interval, keyPerformanceIndicator.interval) &&
        Objects.equals(this.kpiGraphType, keyPerformanceIndicator.kpiGraphType) &&
        Objects.equals(this.kpiId, keyPerformanceIndicator.kpiId) &&
        Objects.equals(this.metricIds, keyPerformanceIndicator.metricIds) &&
        Objects.equals(this.name, keyPerformanceIndicator.name) &&
        Objects.equals(this.unit, keyPerformanceIndicator.unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(formula, interval, kpiGraphType, kpiId, metricIds, name, unit);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KeyPerformanceIndicator {\n");
    
    sb.append("    formula: ").append(toIndentedString(formula)).append("\n");
    sb.append("    interval: ").append(toIndentedString(interval)).append("\n");
    sb.append("    kpiGraphType: ").append(toIndentedString(kpiGraphType)).append("\n");
    sb.append("    kpiId: ").append(toIndentedString(kpiId)).append("\n");
    sb.append("    metricIds: ").append(toIndentedString(metricIds)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
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

