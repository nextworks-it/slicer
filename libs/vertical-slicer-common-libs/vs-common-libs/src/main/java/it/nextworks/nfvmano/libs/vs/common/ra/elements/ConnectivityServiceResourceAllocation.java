package it.nextworks.nfvmano.libs.vs.common.ra.elements;

public class ConnectivityServiceResourceAllocation {

    private String ingressSipId;
    private String egressSipId;
    private int capacity; //It is assumed that the measurement unit is GBit/s

    public ConnectivityServiceResourceAllocation() {
    }

    public ConnectivityServiceResourceAllocation(String ingressSipId, String egressSipId, int capacity) {
        this.ingressSipId = ingressSipId;
        this.egressSipId = egressSipId;
        this.capacity = capacity;
    }

    public String getIngressSipId() {
        return ingressSipId;
    }

    public void setIngressSipId(String ingressSipId) {
        this.ingressSipId = ingressSipId;
    }

    public String getEgressSipId() {
        return egressSipId;
    }

    public void setEgressSipId(String egressSipId) {
        this.egressSipId = egressSipId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
