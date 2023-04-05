package it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.elements;

public class Vnf {

    private String vnfdId;
    private String type;
    private int cpuResources;
    private int maximumThroughput;

    public Vnf() {}

    public Vnf(String vnfdId, String type, int cpuResources, int maximumThroughput) {
        this.vnfdId = vnfdId;
        this.type = type;
        this.cpuResources = cpuResources;
        this.maximumThroughput = maximumThroughput;
    }

    public String getVnfdId() {
        return vnfdId;
    }

    public void setVnfdId(String vnfdId) {
        this.vnfdId = vnfdId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCpuResources() {
        return cpuResources;
    }

    public void setCpuResources(int cpuResources) {
        this.cpuResources = cpuResources;
    }

    public int getMaximumThroughput() {
        return maximumThroughput;
    }

    public void setMaximumThroughput(int maximumThroughput) {
        this.maximumThroughput = maximumThroughput;
    }
}
