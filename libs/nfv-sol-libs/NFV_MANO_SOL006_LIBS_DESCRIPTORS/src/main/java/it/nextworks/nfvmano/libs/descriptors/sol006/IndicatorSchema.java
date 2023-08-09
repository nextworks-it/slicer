package it.nextworks.nfvmano.libs.descriptors.sol006;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;



/**
 * IndicatorSchema
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-09T19:09:38.446+01:00[Europe/Rome]")


public class IndicatorSchema   {
  @JsonProperty("indicator-value")

  private List<String> indicatorValue = null;

  @JsonProperty("indicator_id")
  private String indicatorId = null;

  @JsonProperty("name")
  private String name = null;

  /**
   * Describe the source of the indicator. The possible values are: • VNF. • EM. • Both. This tells the consumer where to send the subscription request.
   */
  public enum SourceEnum {
    VNF("vnf"),
    
    EM("em"),
    
    BOTH("both");

    private String value;

    SourceEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static SourceEnum fromValue(String text) {
      for (SourceEnum b : SourceEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("source")
  private SourceEnum source = null;

  public IndicatorSchema indicatorValue(List<String> indicatorValue) {
    this.indicatorValue = indicatorValue;
    return this;
  }

  public IndicatorSchema addIndicatorValueItem(String indicatorValueItem) {
    if (this.indicatorValue == null) {
      this.indicatorValue = new ArrayList<String>();
    }
    this.indicatorValue.add(indicatorValueItem);
    return this;
  }

  /**
   * Get indicatorValue
   * @return indicatorValue
   **/
  //@Schema(description = "")
  
    public List<String> getIndicatorValue() {
    return indicatorValue;
  }

  public void setIndicatorValue(List<String> indicatorValue) {
    this.indicatorValue = indicatorValue;
  }

  public IndicatorSchema indicatorId(String indicatorId) {
    this.indicatorId = indicatorId;
    return this;
  }

  /**
   * Unique identifier
   * @return indicatorId
   **/
  //@Schema(description = "Unique identifier")
  
    public String getIndicatorId() {
    return indicatorId;
  }

  public void setIndicatorId(String indicatorId) {
    this.indicatorId = indicatorId;
  }

  public IndicatorSchema name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The human readable name of the VnfIndicator.
   * @return name
   **/
  //@Schema(description = "The human readable name of the VnfIndicator.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public IndicatorSchema source(SourceEnum source) {
    this.source = source;
    return this;
  }

  /**
   * Describe the source of the indicator. The possible values are: • VNF. • EM. • Both. This tells the consumer where to send the subscription request.
   * @return source
   **/
  //@Schema(description = "Describe the source of the indicator. The possible values are: • VNF. • EM. • Both. This tells the consumer where to send the subscription request.")
  
    public SourceEnum getSource() {
    return source;
  }

  public void setSource(SourceEnum source) {
    this.source = source;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IndicatorSchema indicatorSchema = (IndicatorSchema) o;
    return Objects.equals(this.indicatorValue, indicatorSchema.indicatorValue) &&
        Objects.equals(this.indicatorId, indicatorSchema.indicatorId) &&
        Objects.equals(this.name, indicatorSchema.name) &&
        Objects.equals(this.source, indicatorSchema.source);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indicatorValue, indicatorId, name, source);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IndicatorSchema {\n");
    
    sb.append("    indicatorValue: ").append(toIndentedString(indicatorValue)).append("\n");
    sb.append("    indicatorId: ").append(toIndentedString(indicatorId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
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
