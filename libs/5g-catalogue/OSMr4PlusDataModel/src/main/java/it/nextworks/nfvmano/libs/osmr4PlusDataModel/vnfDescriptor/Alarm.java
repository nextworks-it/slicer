package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Alarm {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("alarm-id")
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vdur-id")
    private String vdurId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Actions actions;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean repeat;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean enabled;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String severity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String metric;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String statistic;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String operation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer period;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer evaluations;

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

    public String getVdurId() {
        return vdurId;
    }

    public void setVdurId(String vdurId) {
        this.vdurId = vdurId;
    }

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getStatistic() {
        return statistic;
    }

    public void setStatistic(String statistic) {
        this.statistic = statistic;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Integer evaluations) {
        this.evaluations = evaluations;
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
        return "Alarm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", vdurId='" + vdurId + '\'' +
                ", actions=" + actions +
                ", repeat=" + repeat +
                ", enabled=" + enabled +
                ", severity='" + severity + '\'' +
                ", metric='" + metric + '\'' +
                ", statistic='" + statistic + '\'' +
                ", operation='" + operation + '\'' +
                ", value=" + value +
                ", period=" + period +
                ", evaluations=" + evaluations +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
