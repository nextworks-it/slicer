package it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.elements;

import it.nextworks.nfvmano.libs.vs.common.topology.ProcessingCapabilities;

import java.util.Map;

public class Node {

    private String nodeId;
    private ExtNodeType type;
    private Map<String, Double> position; // key: X or Y, value: real position
    private ProcessingCapabilities processingCapabilities;
    private Map<String, Double> processingInfra;

    public Node(){}

    public Node(String nodeId,
                ExtNodeType nodeType,
                Map<String, Double> position,
                ProcessingCapabilities processingCapabilities,
                Map<String, Double> processingInfra) {
        this.nodeId = nodeId;
        this.type = nodeType;
        this.position = position;
        this.processingCapabilities = processingCapabilities;
        this.processingInfra = processingInfra;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public ExtNodeType getType() {
        return type;
    }

    public void setType(ExtNodeType type) {
        this.type = type;
    }

    public Map<String, Double> getPosition() {
        return position;
    }

    public void setPosition(Map<String, Double> position) {
        this.position = position;
    }

    public ProcessingCapabilities getProcessingCapabilities() {
        return processingCapabilities;
    }

    public void setProcessingCapabilities(ProcessingCapabilities processingCapabilities) {
        this.processingCapabilities = processingCapabilities;
    }

    public Map<String, Double> getProcessingInfra() {
        return processingInfra;
    }

    public void setProcessingInfra(Map<String, Double> processingInfra) {
        this.processingInfra = processingInfra;
    }
}
