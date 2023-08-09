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

import java.util.*;

public class NetworkTopology {

	public List<TopologyNode> nodes;
	public List<TopologyLink> links;
	public List<TopologyServiceInterfacePoint> sips;
	private Map<String, TopologyNode> id2nodeMap;
	private Map<String, List<TopologyCp>> id2cpMap;
	//private Map<String, TopologyCp> addressMap;

	public double processing; // in W/Kb TODO should become a mapping flavor -> PC
	// TODO: yes, it is node-agnostic, and it needs to be such.

	public NetworkTopology(){}

	public NetworkTopology(List<TopologyNode> nodes, List<TopologyLink> links, List<TopologyServiceInterfacePoint> sips) {
		this.nodes = nodes;
		this.links = links;
		id2nodeMap = new HashMap<>();
		for (TopologyNode node : nodes) {
			id2nodeMap.put(node.getNodeId(), node);
		}
		this.sips=sips;
		id2cpMap=new HashMap<>();
		/*id2cpMap = nodes.stream()
				.flatMap((n) -> n.cps.stream())
				.collect(Collectors.toMap((cp) -> cp.cpId, Function.identity()));
		/*addressMap = nodes.stream()
				.flatMap((n) -> n.cps.stream().filter((cp) -> cp.address != null))
				.collect(Collectors.toMap((cp) -> cp.address, Function.identity()));*/

		this.processing = 6E-6;
		validate();
	}

	private void validate() {
		for (TopologyLink link : links) {
			if (!(link.getSource() == (id2nodeMap.get(link.getSource().getNodeId())))) {
				throw new IllegalArgumentException(String.format("Link %s source not found in topology.", link));
			}
			if (!(link.getDestination() == (id2nodeMap.get(link.getDestination().getNodeId())))) {
				throw new IllegalArgumentException(String.format("Link %s destination not found in topology.", link));
			}
		}
	}

	public TopologyNode fetchNodeById(String nodeId) {
		return id2nodeMap.get(nodeId);
	}

	public void addNode(TopologyNode node) {
		TopologyNode previousNode = id2nodeMap.get(node.getNodeId());
		if (previousNode != null) {
			if (previousNode != node) {
				throw new IllegalArgumentException("Node with id " + node.getNodeId() + " already in the topology.");
			}
			// Else skip adding
		} else {
			nodes.add(node);
			id2nodeMap.put(node.getNodeId(), node);
			/*for (TopologyCp cp : node.cps) {
				TopologyCp prevCp = addressMap.get(cp.address);
				if (prevCp == cp) {
					continue;
				}
				if (null != prevCp) {
					throw new IllegalStateException("Address " + cp.address + " already in use.");
				}
				// else
				addressMap.put(cp.address, cp);
			}*/
		}
	}

	public void addCp(String node, TopologyCp cp) {
		TopologyNode topoNode = id2nodeMap.get(node);
		addCp(topoNode, cp);
	}


	public void addCp(TopologyNode node, TopologyCp cp) {
		/*TopologyCp prevCp = addressMap.get(cp.address);
		if (prevCp == cp) {
			return;
		}
		if (prevCp != null) {
			throw new IllegalStateException("Address " + cp.address + " already in use.");
		}*/
		node.getCps().add(cp);

		List<TopologyCp> cps;
		if(id2cpMap.containsKey(node.getNodeId())) {
			cps = id2cpMap.get(node.getNodeId());
			cps.add(cp);
		}else{
			cps=new ArrayList<>();
			cps.add(cp);
		}
		id2cpMap.put(node.getNodeId(), cps);
		/*if (cp.address != null) {
			addressMap.put(cp.address, cp);
		}*/
	}

	public void addLink(TopologyLink link) {
		if (!(link.getSource() == (id2nodeMap.get(link.getSource().getNodeId())))) {
			throw new IllegalArgumentException(String.format("Link %s source not found in topology.", link));
		}
		if (!(link.getDestination() == (id2nodeMap.get(link.getDestination().getNodeId())))) {
			throw new IllegalArgumentException(String.format("Link %s destination not found in topology.", link));
		}
		links.add(link);
	}

	public void addLink(String linkId,
						TopologyNode source, TopologyNode dest,
						String sourceAddress, String sourcePort,
						String destAddress, String destPort,
						double power, int bandwidth, boolean duplex, LinkType linkType, float delay) {
		TopologyLink newLink = new TopologyLink(linkId, source, dest, null, null, power, bandwidth, linkType, delay);
		TopologyCp sourceCp = new TopologyCp(source, newLink, null, sourceAddress, sourcePort);
		TopologyCp destCp = new TopologyCp(dest, null, newLink, destAddress, destPort);
		newLink.setSourceCp(sourceCp);
		newLink.setDestinationCp(destCp);
		addCp(source, sourceCp);
		addCp(dest, destCp);
		addLink(newLink);
		if (duplex) {
			TopologyLink reverseLink = new TopologyLink(
					"rev_" + linkId, dest, source, destCp, sourceCp, power, bandwidth, linkType, delay
			);
			//sourceCp.incomingLink = reverseLink;
			sourceCp.setIncomingLink(reverseLink);
			//destCp.outgoingLink = reverseLink;
			destCp.setOutgoingLink(reverseLink);
			addLink(reverseLink);
		}
	}

	public void addLink(String linkId,
						TopologyNode source, TopologyNode dest,
						String sourceAddress, String sourcePort,
						String destAddress, String destPort,
						double power, int bandwidth, LinkType linkType, float delay) {
		addLink(linkId, source, dest, sourceAddress, sourcePort, destAddress, destPort, power, bandwidth, true, linkType, delay);
	}

	/*public TopologyCp getCpByAddress(String address) {
		return addressMap.get(address);
	}*/

	public TopologyCp getCpById(String nodeId, String cpId) {
		for(TopologyCp cp: id2cpMap.get(nodeId))
			if(cp.getCpId().equals(cpId))
				return cp;
		return null;
	}

	@Override
	public String toString() {
		return String.format("Nodes: %s. Links: %s.", nodes, links);
	}

	public List<TopologyServiceInterfacePoint> getSips() {
		return sips;
	}

	public void setSips(List<TopologyServiceInterfacePoint> sips) {
		this.sips = sips;
	}

	public TopologyServiceInterfacePoint getSipById(String id){
		for(TopologyServiceInterfacePoint sip: sips)
			if(sip.getSipId().equals(id))
				return sip;
		return null;
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

	public Map<String, TopologyNode> getId2nodeMap() {
		return id2nodeMap;
	}

	public void setId2nodeMap(Map<String, TopologyNode> id2nodeMap) {
		this.id2nodeMap = id2nodeMap;
	}

	public Map<String, List<TopologyCp>> getId2cpMap() {
		return id2cpMap;
	}

	public void setId2cpMap(Map<String, List<TopologyCp>> id2cpMap) {
		this.id2cpMap = id2cpMap;
	}

	public double getProcessing() {
		return processing;
	}

	public void setProcessing(double processing) {
		this.processing = processing;
	}
}
