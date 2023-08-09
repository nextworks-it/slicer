package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostEpa {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cpu-model")
    private String cpuModel;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cpu-arch")
    private String cpuArch;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cpu-vendor")
    private String cpuVendor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cpu-socket-count")
    private Integer cpuSocketCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cpu-core-count")
    private Integer cpuCoreCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cpu-core-thread-count")
    private Integer cpuCoreThreadCount;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("cpu-feature")
    private List<CPUFeature> cpuFeatures;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("om-cpu-model-string")
    private String omCpuModelString;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("om-cpu-feature")
    private List<CPUFeature> omCpuFeatures;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getCpuArch() {
        return cpuArch;
    }

    public void setCpuArch(String cpuArch) {
        this.cpuArch = cpuArch;
    }

    public String getCpuVendor() {
        return cpuVendor;
    }

    public void setCpuVendor(String cpuVendor) {
        this.cpuVendor = cpuVendor;
    }

    public Integer getCpuSocketCount() {
        return cpuSocketCount;
    }

    public void setCpuSocketCount(Integer cpuSocketCount) {
        this.cpuSocketCount = cpuSocketCount;
    }

    public Integer getCpuCoreCount() {
        return cpuCoreCount;
    }

    public void setCpuCoreCount(Integer cpuCoreCount) {
        this.cpuCoreCount = cpuCoreCount;
    }

    public Integer getCpuCoreThreadCount() {
        return cpuCoreThreadCount;
    }

    public void setCpuCoreThreadCount(Integer cpuCoreThreadCount) {
        this.cpuCoreThreadCount = cpuCoreThreadCount;
    }

    public List<CPUFeature> getCpuFeatures() {
        return cpuFeatures;
    }

    public void setCpuFeatures(List<CPUFeature> cpuFeatures) {
        this.cpuFeatures = cpuFeatures;
    }

    public String getOmCpuModelString() {
        return omCpuModelString;
    }

    public void setOmCpuModelString(String omCpuModelString) {
        this.omCpuModelString = omCpuModelString;
    }

    public List<CPUFeature> getOmCpuFeatures() {
        return omCpuFeatures;
    }

    public void setOmCpuFeatures(List<CPUFeature> omCpuFeatures) {
        this.omCpuFeatures = omCpuFeatures;
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
        return "HostEpa{" +
                "cpuModel='" + cpuModel + '\'' +
                ", cpuArch='" + cpuArch + '\'' +
                ", cpuVendor='" + cpuVendor + '\'' +
                ", cpuSocketCount=" + cpuSocketCount +
                ", cpuCoreCount=" + cpuCoreCount +
                ", cpuCoreThreadCount=" + cpuCoreThreadCount +
                ", cpuFeatures=" + cpuFeatures +
                ", omCpuModelString='" + omCpuModelString + '\'' +
                ", omCpuFeatures=" + omCpuFeatures +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
