package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class PcieDevice {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("device-id")
    private String deviceID;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        return "PcieDevice{" +
                "deviceID='" + deviceID + '\'' +
                ", count=" + count +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
