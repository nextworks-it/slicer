package it.nextworks.nfvmano.libs.vs.common.ra.messages.compute;

import it.nextworks.nfvmano.libs.ifa.templates.nst.NST;
import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceSubnetInstance;
import it.nextworks.nfvmano.libs.vs.common.topology.NetworkTopology;

import java.util.List;

public class ResourceAllocationComputeRequest {

    private String requestId;
    private String nsiId;
    private NST nst;

    private String tenant;
    private List<NetworkSliceSubnetInstance> sharableSlices;
    private NetworkTopology topology;
    public ResourceAllocationComputeRequest(){}

    public ResourceAllocationComputeRequest(String nsiId, String tenant){
        this.nsiId=nsiId;
        this.tenant=tenant;
    }

    public ResourceAllocationComputeRequest(String requestId,
                                            String nsiId,
                                            NST nst,
                                            String tenant,
                                            List<NetworkSliceSubnetInstance> sharableSlices,
                                            NetworkTopology topology
    ){
        this.requestId=requestId;
        this.nsiId=nsiId;
        this.nst=nst;
        this.tenant=tenant;
        this.sharableSlices =sharableSlices;
        this.topology=topology;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getNsiId() {
        return nsiId;
    }

    public void setNsiId(String nsiId) {
        this.nsiId = nsiId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public NST getNst() {
        return nst;
    }

    public void setNst(NST nst) {
        this.nst = nst;
    }

    public List<NetworkSliceSubnetInstance> getSharableSlices() {
        return sharableSlices;
    }

    public void setSharableSlices(List<NetworkSliceSubnetInstance> sharableSlices) {
        this.sharableSlices = sharableSlices;
    }

    public NetworkTopology getTopology() {
        return topology;
    }

    public void setTopology(NetworkTopology topology) {
        this.topology = topology;
    }
}
