package it.nextworks.nfvmano.libs.vs.common.ra.elements;

import it.nextworks.nfvmano.libs.vs.common.topology.TopologyLink;
import it.nextworks.nfvmano.libs.vs.common.topology.TopologyNode;

import java.util.List;

public abstract class ExternalAlgorithmRequest {

    private String requestId;
    private List<TopologyNode> nodes;
    private List<TopologyLink> links;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<TopologyNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TopologyNode> nodes) {
        this.nodes = nodes;
    }

    public List<TopologyLink> getLinks() {
        return links;
    }

    public void setLinks(List<TopologyLink> links) {
        this.links = links;
    }
}
