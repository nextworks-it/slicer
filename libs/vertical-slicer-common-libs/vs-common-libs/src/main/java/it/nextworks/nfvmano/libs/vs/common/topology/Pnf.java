package it.nextworks.nfvmano.libs.vs.common.topology;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Pnf extends TopologyNode{

    private String pnfId;
    private String pnfInstanceId;
    private PnfType pnfType;
    private ProcessingCapabilities processingCapabilities;

    public Pnf(){
        super();
        setNodeType(NodeType.PNF);
    }

    public Pnf(String nodeId,
               String zoneId,
               PowerState powerState,
               Set<TopologyCp> cps,
               Map<String, List<String>> mapOnepSipUuids,
               Set<LayerProtocol> supportedProtocolLayers,
               Map<String, Float> coordinates,
               String pnfId,
               String pnfInstanceId,
               PnfType pnfType,
               ProcessingCapabilities processingCapabilities){
        super(nodeId, zoneId, powerState, cps, mapOnepSipUuids, supportedProtocolLayers, NodeType.PNF, coordinates);
        this.pnfId=pnfId;
        this.pnfInstanceId=pnfInstanceId;
        this.pnfType=pnfType;
        this.processingCapabilities=processingCapabilities;
    }

    public String getPnfId() {
        return pnfId;
    }

    public void setPnfId(String pnfId) {
        this.pnfId = pnfId;
    }

    public String getPnfInstanceId() {
        return pnfInstanceId;
    }

    public void setPnfInstanceId(String pnfInstanceId) {
        this.pnfInstanceId = pnfInstanceId;
    }

    public PnfType getPnfType() {
        return pnfType;
    }

    public void setPnfType(PnfType pnfType) {
        this.pnfType = pnfType;
    }

    public ProcessingCapabilities getProcessingCapabilities() {
        return processingCapabilities;
    }

    public void setProcessingCapabilities(ProcessingCapabilities processingCapabilities) {
        this.processingCapabilities = processingCapabilities;
    }
}
