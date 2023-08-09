package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScalingGroupDescriptor {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("scaling-policy")
    private List<ScalingPolicy> scalingPolicies;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("vdu")
    private List<VduReference> vduList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("min-instance-count")
    private Integer minInstanceCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("max-instance-count")
    private Integer maxInstanceCount;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("scaling-config-action")
    private List<ScalingConfigAction> scalingConfigActions;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScalingPolicy> getScalingPolicies() {
        return scalingPolicies;
    }

    public void setScalingPolicies(List<ScalingPolicy> scalingPolicies) {
        this.scalingPolicies = scalingPolicies;
    }

    public List<VduReference> getVduList() {
        return vduList;
    }

    public void setVduList(List<VduReference> vduList) {
        this.vduList = vduList;
    }

    public Integer getMinInstanceCount() {
        return minInstanceCount;
    }

    public void setMinInstanceCount(Integer minInstanceCount) {
        this.minInstanceCount = minInstanceCount;
    }

    public Integer getMaxInstanceCount() {
        return maxInstanceCount;
    }

    public void setMaxInstanceCount(Integer maxInstanceCount) {
        this.maxInstanceCount = maxInstanceCount;
    }

    public List<ScalingConfigAction> getScalingConfigActions() {
        return scalingConfigActions;
    }

    public void setScalingConfigActions(List<ScalingConfigAction> scalingConfigActions) {
        this.scalingConfigActions = scalingConfigActions;
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
        return "ScalingGroupDescriptor{" +
                "name='" + name + '\'' +
                ", scalingPolicies=" + scalingPolicies +
                ", vduList=" + vduList +
                ", minInstanceCount=" + minInstanceCount +
                ", maxInstanceCount=" + maxInstanceCount +
                ", scalingConfigActions=" + scalingConfigActions +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
