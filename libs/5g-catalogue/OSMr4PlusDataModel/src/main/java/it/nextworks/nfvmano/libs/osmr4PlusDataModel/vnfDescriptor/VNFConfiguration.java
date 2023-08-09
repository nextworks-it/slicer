package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNFConfiguration {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Script script;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("juju")
    private JujuCharm jujuCharm;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("config-primitive")
    private List<ServicePrimitive> servicePrimitives;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("initial-config-primitive")
    private List<InitialConfigurationPrimitive> initialConfigurationPrimitives;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Metrics> metrics;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("config-access")
    private ConfigAccess configAccess;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public JujuCharm getJujuCharm() {
        return jujuCharm;
    }

    public void setJujuCharm(JujuCharm jujuCharm) {
        this.jujuCharm = jujuCharm;
    }

    public List<ServicePrimitive> getServicePrimitives() {
        return servicePrimitives;
    }

    public void setServicePrimitives(List<ServicePrimitive> servicePrimitives) {
        this.servicePrimitives = servicePrimitives;
    }

    public List<InitialConfigurationPrimitive> getInitialConfigurationPrimitives() {
        return initialConfigurationPrimitives;
    }

    public void setInitialConfigurationPrimitives(List<InitialConfigurationPrimitive> initialConfigurationPrimitives) {
        this.initialConfigurationPrimitives = initialConfigurationPrimitives;
    }

    public List<Metrics> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metrics> metrics) {
        this.metrics = metrics;
    }

    public ConfigAccess getConfigAccess() {
        return configAccess;
    }

    public void setConfigAccess(ConfigAccess configAccess) {
        this.configAccess = configAccess;
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
        return "VNFConfiguration{" +
                "script=" + script +
                ", jujuCharm=" + jujuCharm +
                ", servicePrimitives=" + servicePrimitives +
                ", initialConfigurationPrimitives=" + initialConfigurationPrimitives +
                ", metrics=" + metrics +
                ", configAccess" + configAccess +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
