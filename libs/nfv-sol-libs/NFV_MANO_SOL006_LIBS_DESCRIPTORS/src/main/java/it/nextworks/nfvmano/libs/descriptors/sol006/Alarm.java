package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.enums.OperationEnum;
import it.nextworks.nfvmano.libs.common.enums.SeverityEnum;
import it.nextworks.nfvmano.libs.common.enums.StatisticEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Alarm {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("alarm-id")
    private String alarmId = null;

    @JsonProperty("vnf-monitoring-param-ref")
    private String vnfMonitoringParamRef = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("vdur-id")
    private String vdurId = null;

    @JsonProperty("actions")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "actions_fk", referencedColumnName = "uuid")
    private AlarmActions actions = null;

    @JsonProperty("repeat")
    private Boolean repeat = null;

    @JsonProperty("enabled")
    private Boolean enabled = null;

    @JsonProperty("severity")
    private SeverityEnum severity = null;

    @JsonProperty("statistic")
    private StatisticEnum statistic = null;

    @JsonProperty("operation")
    private OperationEnum operation = null;

    @JsonProperty("value")
    private Double value = null;

    @JsonProperty("period")
    private String period = null;

    @JsonProperty("evaluations")
    private String evaluations = null;

    public Alarm alarmId(String alarmId) {
        this.alarmId = alarmId;
        return this;
    }

    public String getAlarmId() { return alarmId; }

    public void setAlarmId(String alarmId) { this.alarmId = alarmId; }

    public Alarm vnfMonitoringParamRef(String vnfMonitoringParamRef) {
        this.vnfMonitoringParamRef = vnfMonitoringParamRef;
        return this;
    }

    public String getVnfMonitoringParamRef() { return vnfMonitoringParamRef; }

    public void setVnfMonitoringParamRef(String vnfMonitoringParamRef) { this.vnfMonitoringParamRef = vnfMonitoringParamRef; }

    public Alarm name(String name) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Alarm description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Alarm vdurId(String vdurId) {
        this.vdurId = vdurId;
        return this;
    }

    public String getVdurId() { return vdurId; }

    public void setVdurId(String vdurId) { this.vdurId = vdurId; }

    public Alarm actions(AlarmActions actions) {
        this.actions = actions;
        return this;
    }

    public AlarmActions getActions() { return actions; }

    public void setActions(AlarmActions actions) { this.actions = actions; }

    public Alarm repeat(Boolean repeat) {
        this.repeat = repeat;
        return this;
    }

    public Boolean getRepeat() { return repeat; }

    public void setRepeat(Boolean repeat) { this.repeat = repeat; }

    public Alarm enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Boolean getEnabled() { return enabled; }

    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public Alarm severity(SeverityEnum severity) {
        this.severity = severity;
        return this;
    }

    public SeverityEnum getSeverity() { return severity; }

    public void setSeverity(SeverityEnum severity) { this.severity = severity; }

    public Alarm statistic(StatisticEnum statistic) {
        this.statistic = statistic;
        return this;
    }

    public StatisticEnum getStatistic() { return statistic; }

    public void setStatistic(StatisticEnum statistic) { this.statistic = statistic; }

    public Alarm operation(OperationEnum operation) {
        this.operation = operation;
        return this;
    }

    public OperationEnum getOperation() { return operation; }

    public void setOperation(OperationEnum operation) { this.operation = operation; }

    public Alarm value(Double value) {
        this.value = value;
        return this;
    }

    public Double getValue() { return value; }

    public void setValue(Double value) { this.value = value; }

    public Alarm period(String period) {
        this.period = period;
        return this;
    }

    public String getPeriod() { return period; }

    public void setPeriod(String period) { this.period = period; }

    public Alarm evaluations(String evaluations) {
        this.evaluations = evaluations;
        return this;
    }

    public String getEvaluations() { return evaluations; }

    public void setEvaluations(String evaluations) { this.evaluations = evaluations; }

    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        Alarm alarm = (Alarm) o;

        return Objects.equals(this.alarmId, alarm.alarmId) &&
                Objects.equals(this.vnfMonitoringParamRef, alarm.vnfMonitoringParamRef) &&
                Objects.equals(this.name, alarm.name) &&
                Objects.equals(this.description, alarm.description) &&
                Objects.equals(this.vdurId, alarm.vdurId) &&
                Objects.equals(this.actions, alarm.actions) &&
                Objects.equals(this.repeat, alarm.repeat) &&
                Objects.equals(this.enabled, alarm.enabled) &&
                Objects.equals(this.severity, alarm.severity) &&
                Objects.equals(this.statistic, alarm.statistic) &&
                Objects.equals(this.operation, alarm.operation) &&
                Objects.equals(this.value, alarm.value) &&
                Objects.equals(this.period, alarm.period) &&
                Objects.equals(this.evaluations, alarm.evaluations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alarmId, vnfMonitoringParamRef, name, description, vdurId, actions,
                repeat, enabled, severity, statistic, operation, value, period, evaluations);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class Alarm {\n");
        sb.append("    alarmId: ").append(toIndentedString(alarmId)).append("\n");
        sb.append("    vnfMonitoringParamRef: ").append(toIndentedString(vnfMonitoringParamRef)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    vdurId: ").append(toIndentedString(vdurId)).append("\n");
        sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
        sb.append("    repeat: ").append(toIndentedString(repeat)).append("\n");
        sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
        sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
        sb.append("    statistic: ").append(toIndentedString(statistic)).append("\n");
        sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
        sb.append("    value: ").append(toIndentedString(value)).append("\n");
        sb.append("    period: ").append(toIndentedString(period)).append("\n");
        sb.append("    evaluations: ").append(toIndentedString(evaluations)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
