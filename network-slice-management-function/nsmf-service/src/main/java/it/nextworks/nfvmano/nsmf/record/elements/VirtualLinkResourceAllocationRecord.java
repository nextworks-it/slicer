package it.nextworks.nfvmano.nsmf.record.elements;


import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.VirtualLinkResourceAllocation;
import it.nextworks.nfvmano.libs.vs.common.topology.SupportedServiceClassifier;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class VirtualLinkResourceAllocationRecord {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Long id;

    private String nsdId;
    private String virtualLinkId;
    private String ingressSipId;
    private String egressSipId;
    @ElementCollection(targetClass=String.class)
    private Map<SupportedServiceClassifier, String> serviceClassifierAllocation;
    private String defaultGw;
    private boolean isDefault;



    public VirtualLinkResourceAllocationRecord(){}

    public VirtualLinkResourceAllocationRecord(
                                         String nsdId,
                                         String virtualLinkId,
                                         String ingressSipId,
                                         String egressSipId,
                                         Map<SupportedServiceClassifier, String> serviceClassifierAllocation,
                                         String defaultGw,
                                         boolean isDefault
                                         ){

        this.nsdId=nsdId;
        this.virtualLinkId=virtualLinkId;
        this.ingressSipId=ingressSipId;
        this.egressSipId=egressSipId;
        this.serviceClassifierAllocation=serviceClassifierAllocation;
        this.defaultGw = defaultGw;
        this.isDefault = isDefault;

    }

    public String getDefaultGw() {
        return defaultGw;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getNsdId() {
        return nsdId;
    }

    public void setNsdId(String nsdId) {
        this.nsdId = nsdId;
    }

    public String getVirtualLinkId() {
        return virtualLinkId;
    }

    public void setVirtualLinkId(String virtualLinkId) {
        this.virtualLinkId = virtualLinkId;
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


    public VirtualLinkResourceAllocation getVirtualResourceAllocation(){
        return new VirtualLinkResourceAllocation(nsdId, virtualLinkId, ingressSipId, egressSipId, serviceClassifierAllocation, defaultGw, isDefault);
    }
}
