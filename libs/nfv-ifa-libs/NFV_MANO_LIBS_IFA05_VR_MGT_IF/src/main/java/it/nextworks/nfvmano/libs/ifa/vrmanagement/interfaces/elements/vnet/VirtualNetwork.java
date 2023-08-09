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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.NetworkSegmentType;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element describes an instantiated virtual network resource.
 *  
 *  REF IFA 005 v2.3.1 - 8.4.5.2
 * 
 * @author nextworks
 *
 */
public class VirtualNetwork implements InterfaceInformationElement {

	private String networkResourceId;
	private String networkResourceName;
	private List<String> subnet = new ArrayList<>();
	private List<VirtualNetworkPort> networkPort = new ArrayList<>();
	private int bandwidth;
	private NetworkSegmentType networkType;
	private String segmentType;
	private List<NetworkQoS> networkQoS = new ArrayList<>();
	private boolean shared;
	private String sharingCriteria;
	private String zoneId;
	private OperationalState operationalState;
	
	//Used to notify the status of the resource - Key: VIM_RESOURCE_STATUS
	private Map<String, String> metadata = new HashMap<>();
	
	
	public VirtualNetwork() { }
	
	/**
	 * Constructor
	 * 
	 * @param networkResourceId Identifier of the virtualised network resource.
	 * @param networkResourceName Name of the virtualised network resource.
	 * @param subnet Only present if the network provides layer 3 connectivity.
	 * @param networkPort Element providing information of an instantiated virtual network port
	 * @param bandwidth Minimum network bandwidth (in Mbps).
	 * @param networkType The type of network that maps to the virtualised network.
	 * @param segmentType The isolated segment for the virtualised network.
	 * @param networkQoS Element providing information about Quality of Service attributes that the network supports
	 * @param isShared It defines whether the virtualised network is shared among consumers.
	 * @param sharingCriteria Only present for shared networks. Indicate the sharing criteria for this network. This criteria might be a list of authorized consumers.
	 * @param zoneId If present, it identifies the Resource Zone where the virtual network resources have been allocated.
	 * @param operationalState The operational state of the virtualised network.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualNetwork(String networkResourceId,
			String networkResourceName,
			List<String> subnet,
			List<VirtualNetworkPort> networkPort,
			int bandwidth,
			NetworkSegmentType networkType,
			String segmentType,
			List<NetworkQoS> networkQoS,
			boolean isShared,
			String sharingCriteria,
			String zoneId,
			OperationalState operationalState,
			Map<String, String> metadata) {
		this.networkResourceId = networkResourceId;
		this.networkResourceName = networkResourceName;
		if (subnet != null) this.subnet = subnet;
		if (networkPort != null) this.networkPort = networkPort;
		this.bandwidth = bandwidth;
		this.networkType = networkType;
		this.segmentType =segmentType;
		if (networkQoS != null) this.networkQoS = networkQoS;
		this.shared = isShared;
		this.sharingCriteria = sharingCriteria;
		this.zoneId = zoneId;
		this.operationalState = operationalState;
		if (metadata != null) this.metadata = metadata;
	}

	/**
	 * @return the networkResourceId
	 */
	public String getNetworkResourceId() {
		return networkResourceId;
	}




	/**
	 * @return the networkResourceName
	 */
	public String getNetworkResourceName() {
		return networkResourceName;
	}




	/**
	 * @return the subnet
	 */
	public List<String> getSubnet() {
		return subnet;
	}




	/**
	 * @return the networkPort
	 */
	public List<VirtualNetworkPort> getNetworkPort() {
		return networkPort;
	}




	/**
	 * @return the bandwidth
	 */
	public int getBandwidth() {
		return bandwidth;
	}




	/**
	 * @return the networkType
	 */
	public NetworkSegmentType getNetworkType() {
		return networkType;
	}




	/**
	 * @return the segmentType
	 */
	public String getSegmentType() {
		return segmentType;
	}




	/**
	 * @return the networkQoS
	 */
	public List<NetworkQoS> getNetworkQoS() {
		return networkQoS;
	}




	/**
	 * @return the isShared
	 */
	public boolean isShared() {
		return shared;
	}




	/**
	 * @return the sharingCriteria
	 */
	public String getSharingCriteria() {
		return sharingCriteria;
	}




	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}




	/**
	 * @return the operationalState
	 */
	public OperationalState getOperationalState() {
		return operationalState;
	}




	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}




	@Override
	public void isValid() throws MalformattedElementException {
		if (networkResourceId == null) throw new MalformattedElementException("Virtual network without resource ID");
		if (networkPort != null) {
			for (VirtualNetworkPort n: networkPort) n.isValid();
		}
		if (networkQoS != null) {
			for (NetworkQoS n: networkQoS) n.isValid();
		}
	}

}
