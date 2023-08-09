package it.nextworks.nfvmano.libs.vs.common.topology;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Switch extends TopologyNode{

    private String switchId;

    public Switch(){
        super();
        setNodeType(NodeType.SWITCH);
    }

    public Switch(String nodeId,
                  String zoneId,
                  PowerState powerState,
                  Set<TopologyCp> cps,
                  Map<String, List<String>> mapOnepSipUuids,
                  Set<LayerProtocol> supportedProtocolLayers,
                  Map<String, Float> coordinates,
                  String switchId){
        super(nodeId, zoneId, powerState, cps, mapOnepSipUuids, supportedProtocolLayers, NodeType.SWITCH, coordinates);
        this.switchId=switchId;
    }

    public String getSwitchId() {
        return switchId;
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }
}
