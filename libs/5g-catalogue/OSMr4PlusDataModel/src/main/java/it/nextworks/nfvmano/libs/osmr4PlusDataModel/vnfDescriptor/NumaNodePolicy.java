package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumaNodePolicy {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("node-cnt")
    private Integer nodeCnt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("mem-policy")
    private String memPolicy;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Node> node;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getNodeCnt() {
        return nodeCnt;
    }

    public void setNodeCnt(Integer nodeCnt) {
        this.nodeCnt = nodeCnt;
    }

    public String getMemPolicy() {
        return memPolicy;
    }

    public void setMemPolicy(String memPolicy) {
        this.memPolicy = memPolicy;
    }

    public List<Node> getNode() {
        return node;
    }

    public void setNode(List<Node> node) {
        this.node = node;
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
        return "NumaNodePolicy{" +
                "nodeCnt=" + nodeCnt +
                ", memPolicy='" + memPolicy + '\'' +
                ", node=" + node +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
