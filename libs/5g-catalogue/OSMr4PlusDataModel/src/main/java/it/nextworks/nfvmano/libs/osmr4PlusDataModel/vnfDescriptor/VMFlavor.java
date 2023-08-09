package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class VMFlavor {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vcpu-count")
    private Integer vcpuCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("memory-mb")
    private Integer memoryMb;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("storage-gb")
    private Integer storageGb;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getVcpuCount() {
        return vcpuCount;
    }

    public void setVcpuCount(Integer vcpuCount) {
        this.vcpuCount = vcpuCount;
    }

    public Integer getMemoryMb() {
        return memoryMb;
    }

    public void setMemoryMb(Integer memoryMb) {
        this.memoryMb = memoryMb;
    }

    public Integer getStorageGb() {
        return storageGb;
    }

    public void setStorageGb(Integer storageGb) {
        this.storageGb = storageGb;
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
        return "VMFlavor{" +
                "vcpuCount=" + vcpuCount +
                ", memoryMb=" + memoryMb +
                ", storageGb=" + storageGb +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
