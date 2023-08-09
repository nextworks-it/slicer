package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actions {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ActionUrl> ok;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("insufficient-data")
    private List<ActionUrl> insufficientData;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ActionUrl> alarm;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public List<ActionUrl> getOk() {
        return ok;
    }

    public void setOk(List<ActionUrl> ok) {
        this.ok = ok;
    }

    public List<ActionUrl> getInsufficientData() {
        return insufficientData;
    }

    public void setInsufficientData(List<ActionUrl> insufficientData) {
        this.insufficientData = insufficientData;
    }

    public List<ActionUrl> getAlarm() {
        return alarm;
    }

    public void setAlarm(List<ActionUrl> alarm) {
        this.alarm = alarm;
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
        return "Actions{" +
                "ok=" + ok +
                ", insufficientData=" + insufficientData +
                ", alarm=" + alarm +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
