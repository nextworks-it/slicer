package it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.elements;

public class E2EQoS {

    private String sfcReq;
    private int latency;
    private int throughput;
    private String survivalTime;

    public E2EQoS(){}

    public E2EQoS(String sfcReq, int latency, int throughput, String survivalTime){
        this.sfcReq=sfcReq;
        this.latency=latency;
        this.throughput=throughput;
        this.survivalTime=survivalTime;
    }

    public String getSfcReq() {
        return sfcReq;
    }

    public void setSfcReq(String sfcReq) {
        this.sfcReq = sfcReq;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public int getThroughput() {
        return throughput;
    }

    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }

    public String getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(String survivalTime) {
        this.survivalTime = survivalTime;
    }
}
