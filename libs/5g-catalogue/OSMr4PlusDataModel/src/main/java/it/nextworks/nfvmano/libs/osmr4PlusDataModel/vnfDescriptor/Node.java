package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<VCPU> vcpu;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("memory-mb")
    private Integer memoryMb;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("num-cores")
    private Integer numCores;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("paired-threads")
    private PairedThreads pairedThreads;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("num-threads")
    private Integer numThreads;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<VCPU> getVcpu() {
        return vcpu;
    }

    public void setVcpu(List<VCPU> vcpu) {
        this.vcpu = vcpu;
    }

    public Integer getMemoryMb() {
        return memoryMb;
    }

    public void setMemoryMb(Integer memoryMb) {
        this.memoryMb = memoryMb;
    }

    public Integer getNumCores() {
        return numCores;
    }

    public void setNumCores(Integer numCores) {
        this.numCores = numCores;
    }

    public PairedThreads getPairedThreads() {
        return pairedThreads;
    }

    public void setPairedThreads(PairedThreads pairedThreads) {
        this.pairedThreads = pairedThreads;
    }

    public Integer getNumThreads() {
        return numThreads;
    }

    public void setNumThreads(Integer numThreads) {
        this.numThreads = numThreads;
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
        return "Node{" +
                "id='" + id + '\'' +
                ", vcpu=" + vcpu +
                ", memoryMb=" + memoryMb +
                ", numCores=" + numCores +
                ", pairedThreads=" + pairedThreads +
                ", numThreads=" + numThreads +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
