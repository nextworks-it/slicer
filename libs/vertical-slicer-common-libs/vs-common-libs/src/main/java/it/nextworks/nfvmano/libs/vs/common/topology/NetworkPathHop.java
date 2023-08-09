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
import java.util.Map;

/**
 * This information element models a hop of a network path
 * 
 * @author nextworks
 *
 */
public class NetworkPathHop {

	@JsonIgnore
	private NetworkPath np;

	private int hopNumber;			//hop number					--> this can be removed
	private String nodeId;			//ID of the network node
	private String ingressPortId;	//ID of the ingress port
	private String egressPortId;	//ID of the egress port
	private String incomingLinkId;	//ID of the incoming link
	private String outgoingLinkId;	//ID of the outgoing link
	private int hopQueue;			//hop egress queue
	private boolean first;			//true if this is the first hop --> this can be removed
	private boolean last;			//true if this is the last hop	--> this can be removed
	
	private String ingressServiceInterfacePoint;
	private String egressServiceInterfacePoint;

	private Map<String, String> hopProperties;
	public NetworkPathHop() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param hopNumber hop number
	 * @param nodeId ID of the network node
	 * @param ingressPortId ID of the ingress port
	 * @param egressPortId ID of the egress port
	 * @param incomingLinkId ID of the incoming link
	 * @param outgoingLinkId ID of the outgoing link
	 * @param hopQueue hop egress queue
	 * @param first true if this is the first hop
	 * @param last true if this is the last hop
	 * @param ingressServiceInterfacePoint ingress Service Interface Point - valid for first hop only - used when an ingress port can be associated to multiple service interface points as in TAPI 
	 * @param egressServiceInterfacePoint egress Service Interface Point - valid for last hop only - used when an egress port can be associated to multiple service interface points as in TAPI
	 * @param hopProperties additional Hop Properties, used to provide TAPI parameters
	 */
	public NetworkPathHop(int hopNumber,
			String nodeId,
			String ingressPortId,
			String egressPortId,
			String incomingLinkId,
			String outgoingLinkId,
			int hopQueue,
			boolean first,
			boolean last,
			String ingressServiceInterfacePoint,
			String egressServiceInterfacePoint,
			Map<String, String> hopProperties) {
		this.hopNumber = hopNumber;
		this.nodeId = nodeId;
		this.incomingLinkId = incomingLinkId;
		this.ingressPortId = ingressPortId;
		this.egressPortId = egressPortId;
		this.outgoingLinkId = outgoingLinkId;
		this.hopQueue = hopQueue;
		this.first = first;
		this.last = last;
		this.ingressServiceInterfacePoint = ingressServiceInterfacePoint;
		this.egressServiceInterfacePoint = egressServiceInterfacePoint;
		this.hopProperties = hopProperties;
	}


	public NetworkPathHop(int hopNumber,
						  String nodeId,
						  String ingressPortId,
						  String egressPortId,
						  String incomingLinkId,
						  String outgoingLinkId,
						  int hopQueue,
						  boolean first,
						  boolean last,
						  String ingressServiceInterfacePoint,
						  String egressServiceInterfacePoint
						 ) {
		this.hopNumber = hopNumber;
		this.nodeId = nodeId;
		this.incomingLinkId = incomingLinkId;
		this.ingressPortId = ingressPortId;
		this.egressPortId = egressPortId;
		this.outgoingLinkId = outgoingLinkId;
		this.hopQueue = hopQueue;
		this.first = first;
		this.last = last;
		this.ingressServiceInterfacePoint = ingressServiceInterfacePoint;
		this.egressServiceInterfacePoint = egressServiceInterfacePoint;

	}

	public void setHopProperties(Map<String, String> hopProperties) {
		this.hopProperties = hopProperties;
	}

	/**
	 * Constructor
	 * 
	 * @param np network path this hop belongs to
	 * @param hopNumber hop number
	 * @param nodeId ID of the network node
	 * @param ingressPortId ID of the ingress port
	 * @param egressPortId ID of the egress port
	 * @param incomingLinkId ID of the incoming link
	 * @param outgoingLinkId ID of the outgoing link
	 * @param hopQueue hop egress queue
	 * @param first true if this is the first hop
	 * @param last true if this is the last hop
	 * @param ingressServiceInterfacePoint ingress Service Interface Point - valid for first hop only - used when an ingress port can be associated to multiple service interface points as in TAPI 
	 * @param egressServiceInterfacePoint egress Service Interface Point - valid for last hop only - used when an egress port can be associated to multiple service interface points as in TAPI
	 */
	public NetworkPathHop(NetworkPath np,
			int hopNumber,
			String nodeId,
			String ingressPortId,
			String egressPortId,
			String incomingLinkId,
			String outgoingLinkId,
			int hopQueue,
			boolean first,
			boolean last,
			String ingressServiceInterfacePoint,
			String egressServiceInterfacePoint) {
		this.np = np;
		this.hopNumber = hopNumber;
		this.nodeId = nodeId;
		this.incomingLinkId = incomingLinkId;
		this.ingressPortId = ingressPortId;
		this.egressPortId = egressPortId;
		this.outgoingLinkId = outgoingLinkId;
		this.hopQueue = hopQueue;
		this.first = first;
		this.last = last;
		this.ingressServiceInterfacePoint = ingressServiceInterfacePoint;
		this.egressServiceInterfacePoint = egressServiceInterfacePoint;
	}
	

	/**
	 * @return the hopNumber
	 */
	public int getHopNumber() {
		return hopNumber;
	}

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @return the ingressPortId
	 */
	public String getIngressPortId() {
		return ingressPortId;
	}

	/**
	 * @return the egressPortId
	 */
	public String getEgressPortId() {
		return egressPortId;
	}

	/**
	 * @return the incomingLinkId
	 */
	public String getIncomingLinkId() {
		return incomingLinkId;
	}

	/**
	 * @return the outgoingLinkId
	 */
	public String getOutgoingLinkId() {
		return outgoingLinkId;
	}

	/**
	 * @return the hopQueue
	 */
	public int getHopQueue() { return hopQueue; }

	/**
	 * @return the first
	 */
	public boolean isFirst() {
		return first;
	}

	/**
	 * @return the last
	 */
	public boolean isLast() {
		return last;
	}

	/**
	 * @return the ingressServiceInterfacePoint
	 */
	public String getIngressServiceInterfacePoint() {
		return ingressServiceInterfacePoint;
	}

	/**
	 * @return the egressServiceInterfacePoint
	 */
	public String getEgressServiceInterfacePoint() {
		return egressServiceInterfacePoint;
	}


	public Map<String, String> getHopProperties() {
		return hopProperties;
	}

	public void setNp(NetworkPath np) {
		this.np = np;
	}

	public void setHopNumber(int hopNumber) {
		this.hopNumber = hopNumber;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setIngressPortId(String ingressPortId) {
		this.ingressPortId = ingressPortId;
	}

	public void setEgressPortId(String egressPortId) {
		this.egressPortId = egressPortId;
	}

	public void setIncomingLinkId(String incomingLinkId) {
		this.incomingLinkId = incomingLinkId;
	}

	public void setOutgoingLinkId(String outgoingLinkId) {
		this.outgoingLinkId = outgoingLinkId;
	}

	public void setHopQueue(int hopQueue) {
		this.hopQueue = hopQueue;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public void setIngressServiceInterfacePoint(String ingressServiceInterfacePoint) {
		this.ingressServiceInterfacePoint = ingressServiceInterfacePoint;
	}

	public void setEgressServiceInterfacePoint(String egressServiceInterfacePoint) {
		this.egressServiceInterfacePoint = egressServiceInterfacePoint;
	}
}
