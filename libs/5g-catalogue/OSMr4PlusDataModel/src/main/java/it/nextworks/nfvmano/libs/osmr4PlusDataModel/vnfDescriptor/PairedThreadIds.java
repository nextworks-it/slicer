package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class PairedThreadIds {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("thread-a")
    private Integer threadA;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("thread-b")
    private Integer threadB;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getThreadA() {
        return threadA;
    }

    public void setThreadA(Integer threadA) {
        this.threadA = threadA;
    }

    public Integer getThreadB() {
        return threadB;
    }

    public void setThreadB(Integer threadB) {
        this.threadB = threadB;
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
        return "PairedThreadIds{" +
                "threadA=" + threadA +
                ", threadB=" + threadB +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
