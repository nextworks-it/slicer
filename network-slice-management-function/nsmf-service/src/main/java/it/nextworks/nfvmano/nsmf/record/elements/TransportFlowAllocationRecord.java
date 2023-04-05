package it.nextworks.nfvmano.nsmf.record.elements;

import it.nextworks.nfvmano.libs.vs.common.ra.elements.TransportFlowAllocation;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.TransportFlowType;

import javax.persistence.Embeddable;

@Embeddable
public class TransportFlowAllocationRecord {

    private TransportFlowType transportFlowType;

    private String defaultGw;

    private boolean active;

    public TransportFlowAllocationRecord() {
    }

    public TransportFlowAllocationRecord(TransportFlowType transportFlowType, String defaultGw, boolean isDefault) {
        this.transportFlowType = transportFlowType;
        this.defaultGw = defaultGw;
        this.active = isDefault;
    }

    public TransportFlowType getTransportFlowType() {
        return transportFlowType;
    }

    public boolean isActive() {
        return active;
    }

    public String getDefaultGw() {
        return defaultGw;
    }

    public TransportFlowAllocation getTransportSegmentAllocation(){
        return new TransportFlowAllocation(transportFlowType, defaultGw, active);
    }
}
