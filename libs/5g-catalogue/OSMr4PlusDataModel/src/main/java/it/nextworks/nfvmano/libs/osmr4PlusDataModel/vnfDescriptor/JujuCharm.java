package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class JujuCharm {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String charm;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean proxy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vca-relationships")
    private VCARelationships vcaRelationships;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getCharm() {
        return charm;
    }

    public void setCharm(String charm) {
        this.charm = charm;
    }

    public boolean isProxy() {
        return proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    public VCARelationships getVcaRelationships() {
        return vcaRelationships;
    }

    public void setVcaRelationships(VCARelationships vcaRelationships) {
        this.vcaRelationships = vcaRelationships;
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
        return "JujuCharm{" +
                "charm='" + charm + '\'' +
                ", proxy=" + proxy +
                ", vcaRelationships=" + vcaRelationships +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
