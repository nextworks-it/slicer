package it.nextworks.nfvmano.libs.vs.common.ra.elements;


import it.nextworks.nfvmano.libs.vs.common.topology.SupportedServiceClassifier;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VirtualLinkResourceAllocation {



    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nsdId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String virtualLinkId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ingressSipId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String egressSipId;
    private Map<SupportedServiceClassifier, String> serviceClassifierAllocation;
    private String defaultGw;


    @JsonProperty("default")
    private boolean isDefault;

    public VirtualLinkResourceAllocation(){}

    public VirtualLinkResourceAllocation(
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
        this.defaultGw=defaultGw;
        this.isDefault=isDefault;
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

    public Map<SupportedServiceClassifier, String> getServiceClassifierAllocation() {
        return serviceClassifierAllocation;
    }

    public void setServiceClassifierAllocation(Map<SupportedServiceClassifier, String> serviceClassifierAllocation) {
        this.serviceClassifierAllocation = serviceClassifierAllocation;
    }
}
