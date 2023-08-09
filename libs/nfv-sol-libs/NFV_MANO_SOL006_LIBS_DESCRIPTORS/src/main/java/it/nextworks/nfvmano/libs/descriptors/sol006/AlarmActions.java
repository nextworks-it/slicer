package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class AlarmActions {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("ok")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "alarm_actions_fk", referencedColumnName = "uuid")
    private List<AlarmActionsUrl> ok = null;

    @JsonProperty("insufficient-data")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "alarm_actions_fk", referencedColumnName = "uuid")
    private List<AlarmActionsUrl> insufficientData = null;

    @JsonProperty("alarm")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "alarm_actions_fk", referencedColumnName = "uuid")
    private List<AlarmActionsUrl> alarm = null;

    public AlarmActions ok(List<AlarmActionsUrl> ok) {
        this.ok = ok;
        return this;
    }

    public AlarmActions addOkItem(AlarmActionsUrl alarmActionsUrl) {
        if(this.ok == null)
            this.ok = new ArrayList<>();

        ok.add(alarmActionsUrl);

        return this;
    }

    public List<AlarmActionsUrl> getOk() { return ok; }

    public void setOk(List<AlarmActionsUrl> ok) { this.ok = ok; }

    public AlarmActions insufficientData(List<AlarmActionsUrl> insufficientData) {
        this.insufficientData = insufficientData;
        return this;
    }

    public AlarmActions addInsufficientDataItem(AlarmActionsUrl insufficientData) {
        if(this.insufficientData == null)
            this.insufficientData = new ArrayList<>();

        this.insufficientData.add(insufficientData);

        return this;
    }

    public List<AlarmActionsUrl> getInsufficientData() { return insufficientData; }

    public void setInsufficientData(List<AlarmActionsUrl> insufficientData) { this.insufficientData = insufficientData; }

    public AlarmActions alarm(List<AlarmActionsUrl> alarm) {
        this.alarm = alarm;
        return this;
    }

    public AlarmActions addAlarmItem(AlarmActionsUrl alarm) {
        if(this.alarm == null)
            this.alarm = new ArrayList<>();

        this.alarm.add(alarm);

        return this;
    }

    public List<AlarmActionsUrl> getAlarm() { return alarm; }

    public void setAlarm(List<AlarmActionsUrl> alarm) { this.alarm = alarm; }

    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        AlarmActions alarmActions = (AlarmActions) o;

        return Objects.equals(this.ok, alarmActions.ok) &&
                Objects.equals(this.insufficientData, alarmActions.insufficientData) &&
                Objects.equals(this.alarm, alarmActions.alarm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ok, insufficientData, alarm);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class AlarmActions {\n");
        sb.append("    ok: ").append(toIndentedString(ok)).append("\n");
        sb.append("    insufficient-data: ").append(toIndentedString(insufficientData)).append("\n");
        sb.append("    alarm: ").append(toIndentedString(alarm)).append("\n");
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
