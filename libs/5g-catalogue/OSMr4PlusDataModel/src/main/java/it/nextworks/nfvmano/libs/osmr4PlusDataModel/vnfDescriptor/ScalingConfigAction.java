package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ScalingConfigAction {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String trigger;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vnf-config-primitive-name-ref")
    private String vnfConfigPrimitiveNameRef;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getVnfConfigPrimitiveNameRef() {
        return vnfConfigPrimitiveNameRef;
    }

    public void setVnfConfigPrimitiveNameRef(String vnfConfigPrimitiveNameRef) {
        this.vnfConfigPrimitiveNameRef = vnfConfigPrimitiveNameRef;
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
        return "ScalingConfigAction{" +
                "trigger='" + trigger + '\'' +
                ", vnfConfigPrimitiveNameRef='" + vnfConfigPrimitiveNameRef + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
