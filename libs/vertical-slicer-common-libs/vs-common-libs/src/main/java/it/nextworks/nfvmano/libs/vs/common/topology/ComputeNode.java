package it.nextworks.nfvmano.libs.vs.common.topology;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ComputeNode extends TopologyNode{

    private String computeNodeId;

    private int hddSize; // in GB
    private int memory; // in GB
    private int vCPUs;
    private ProcessingCapabilities processingCapabilities;

    public ComputeNode(){
        super();
        setNodeType(NodeType.COMPUTE);
    }

    public ComputeNode(String nodeId,
                       String zoneId,
                       PowerState powerState,
                       Set<TopologyCp> cps,
                       Map<String, List<String>> mapOnepSipUuids,
                       Set<LayerProtocol> supportedProtocolLayers,
                       Map<String, Float> coordinates,
                       String computeNodeId,
                       int hddSize,
                       int memory,
                       int vCPUs,
                       ProcessingCapabilities processingCapabilities){
        super(nodeId, zoneId, powerState, cps, mapOnepSipUuids, supportedProtocolLayers, NodeType.COMPUTE, coordinates);
        this.computeNodeId=computeNodeId;
        this.hddSize=hddSize;
        this.memory=memory;
        this.vCPUs=vCPUs;
        this.processingCapabilities=processingCapabilities;
    }

    public String getComputeNodeId() {
        return computeNodeId;
    }

    public void setComputeNodeId(String computeNodeId) {
        this.computeNodeId = computeNodeId;
    }

    public int getHddSize() {
        return hddSize;
    }

    public void setHddSize(int hddSize) {
        this.hddSize = hddSize;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getvCPUs() {
        return vCPUs;
    }

    public void setvCPUs(int vCPUs) {
        this.vCPUs = vCPUs;
    }

    public ProcessingCapabilities getProcessingCapabilities() {
        return processingCapabilities;
    }

    public void setProcessingCapabilities(ProcessingCapabilities processingCapabilities) {
        this.processingCapabilities = processingCapabilities;
    }
}
