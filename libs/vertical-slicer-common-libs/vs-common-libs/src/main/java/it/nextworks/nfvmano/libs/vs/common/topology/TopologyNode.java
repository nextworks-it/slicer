/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.libs.vs.common.topology;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.*;

/**
 * Created by Marco Capitani on 24/04/17.
 *
 * @author Marco Capitani (m.capitani AT nextworks.it)
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "nodeType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ComputeNode.class, name = "COMPUTE"),
        @JsonSubTypes.Type(value = Pnf.class, name = "PNF"),
        @JsonSubTypes.Type(value = Switch.class, name = "SWITCH")
})
public class TopologyNode {

    private String nodeId;

    private String zoneId;

    private PowerState powerState;

    @JsonIgnore
    private Set<TopologyCp> cps = new HashSet<>();

    private Map<String, List<String>> mapOnepSipUuids;

    private Set<LayerProtocol> supportedProtocolLayers = new HashSet<>();

    private NodeType nodeType;

    public TopologyNode(){}

    public TopologyNode(String nodeId,
                        String zoneId,
                        PowerState powerState,
                        Set<TopologyCp> cps,
                        Map<String, List<String>> mapOnepSipUuids,
                        Set<LayerProtocol> supportedProtocolLayers,
                        NodeType nodeType,
                        Map<String, Float> coordinates
                        ) {
        if (nodeId == null) {
            throw new IllegalArgumentException("Node Id must not be null.");
        }
        this.nodeId = nodeId;
        this.powerState = powerState;
        this.cps = cps;
        this.zoneId = zoneId;
        this.mapOnepSipUuids=mapOnepSipUuids;
        this.supportedProtocolLayers=supportedProtocolLayers;
        this.nodeType=nodeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TopologyNode node = (TopologyNode) o;

        return nodeId.equals(node.nodeId);
    }

    @Override
    public int hashCode() {
        return nodeId.hashCode();
    }

    @Override
    public String toString() {
        return nodeId;
    }

    /*public boolean isOn() {
        return !powerState.equals(PowerState.POWER_OFF) && !powerState.equals(PowerState.SLEEPING);
    }*/

    public void turnOn() {
        powerState = PowerState.HIGH_POWER;
    }

    public void setCps(Set<TopologyCp> cps){ this.cps=cps;}

	/**
	 * @return the supportedProtocolLayers
	 */
	public Set<LayerProtocol> getSupportedProtocolLayers() {
		return supportedProtocolLayers;
	}

    public PowerState getPowerState() {
        return powerState;
    }

    public void setPowerState(PowerState powerState) {
        this.powerState = powerState;
    }

    public Set<TopologyCp> getCps() {
        return cps;
    }

    public Map<String, List<String>> getMapOnepSipUuids() {
        return mapOnepSipUuids;
    }

    public void setMapOnepSipUuids(Map<String, List<String>> mapOnepSipUuids) {
        this.mapOnepSipUuids = mapOnepSipUuids;
    }

    public void setSupportedProtocolLayers(Set<LayerProtocol> supportedProtocolLayers) {
        this.supportedProtocolLayers = supportedProtocolLayers;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
