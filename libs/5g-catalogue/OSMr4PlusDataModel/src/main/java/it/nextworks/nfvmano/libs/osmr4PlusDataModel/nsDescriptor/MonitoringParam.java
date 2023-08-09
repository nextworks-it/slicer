package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitoringParam {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("aggregation-type")
    private String aggregationType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("group-tag")
    private String groupTag;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("widget-type")
    private String widgetType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String units;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("value-type")
    private String valueType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("numeric-constraints")
    private NumericConstraints numericConstraints;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("text-constraints")
    private TextConstraints textConstraints;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("value-integer")
    private Integer valueInteger;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("value-decimal")
    private Float valueDecimal;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("value-string")
    private String valueString;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnfd-monitoring-param")
    private List<VNFDMonitoringParam> vnfdMonitoringParams;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }

    public String getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public NumericConstraints getNumericConstraints() {
        return numericConstraints;
    }

    public void setNumericConstraints(NumericConstraints numericConstraints) {
        this.numericConstraints = numericConstraints;
    }

    public TextConstraints getTextConstraints() {
        return textConstraints;
    }

    public void setTextConstraints(TextConstraints textConstraints) {
        this.textConstraints = textConstraints;
    }

    public Integer getValueInteger() {
        return valueInteger;
    }

    public void setValueInteger(Integer valueInteger) {
        this.valueInteger = valueInteger;
    }

    public Float getValueDecimal() {
        return valueDecimal;
    }

    public void setValueDecimal(Float valueDecimal) {
        this.valueDecimal = valueDecimal;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public List<VNFDMonitoringParam> getVnfdMonitoringParams() {
        return vnfdMonitoringParams;
    }

    public void setVnfdMonitoringParams(List<VNFDMonitoringParam> vnfdMonitoringParams) {
        this.vnfdMonitoringParams = vnfdMonitoringParams;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "MonitoringParam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", aggregationType='" + aggregationType + '\'' +
                ", groupTag='" + groupTag + '\'' +
                ", widgetType='" + widgetType + '\'' +
                ", units='" + units + '\'' +
                ", valueType='" + valueType + '\'' +
                ", numericConstraints=" + numericConstraints +
                ", textConstraints=" + textConstraints +
                ", valueInteger=" + valueInteger +
                ", valueDecimal=" + valueDecimal +
                ", valueString='" + valueString + '\'' +
                ", vnfdMonitoringParams=" + vnfdMonitoringParams +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
