package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestEpa {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("trusted-execution")
    private boolean trustedExecution;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("mempage-size")
    private String mempageSize;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cpu-pinning-policy")
    private String cpuPinningPolicy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cpu-thread-pinning-policy")
    private String cpuThreadPinningPolicy;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("pcie-device")
    private List<PcieDevice> pcieDeviceList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("numa-unaware")
    private String numaUnaware;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("numa-node-policy")
    private NumaNodePolicy numaNodePolicy;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public boolean isTrustedExecution() {
        return trustedExecution;
    }

    public void setTrustedExecution(boolean trustedExecution) {
        this.trustedExecution = trustedExecution;
    }

    public String getMempageSize() {
        return mempageSize;
    }

    public void setMempageSize(String mempageSize) {
        this.mempageSize = mempageSize;
    }

    public String getCpuPinningPolicy() {
        return cpuPinningPolicy;
    }

    public void setCpuPinningPolicy(String cpuPinningPolicy) {
        this.cpuPinningPolicy = cpuPinningPolicy;
    }

    public String getCpuThreadPinningPolicy() {
        return cpuThreadPinningPolicy;
    }

    public void setCpuThreadPinningPolicy(String cpuThreadPinningPolicy) {
        this.cpuThreadPinningPolicy = cpuThreadPinningPolicy;
    }

    public List<PcieDevice> getPcieDeviceList() {
        return pcieDeviceList;
    }

    public void setPcieDeviceList(List<PcieDevice> pcieDeviceList) {
        this.pcieDeviceList = pcieDeviceList;
    }

    public String getNumaUnaware() {
        return numaUnaware;
    }

    public void setNumaUnaware(String numaUnaware) {
        this.numaUnaware = numaUnaware;
    }

    public NumaNodePolicy getNumaNodePolicy() {
        return numaNodePolicy;
    }

    public void setNumaNodePolicy(NumaNodePolicy numaNodePolicy) {
        this.numaNodePolicy = numaNodePolicy;
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
        return "GuestEpa{" +
                "trustedExecution=" + trustedExecution +
                ", mempageSize='" + mempageSize + '\'' +
                ", cpuPinningPolicy='" + cpuPinningPolicy + '\'' +
                ", cpuThreadPinningPolicy='" + cpuThreadPinningPolicy + '\'' +
                ", pcieDeviceList=" + pcieDeviceList +
                ", numaUnaware='" + numaUnaware + '\'' +
                ", numaNodePolicy=" + numaNodePolicy +
                ", otherProperties=" + otherProperties +
                '}';
    }
}