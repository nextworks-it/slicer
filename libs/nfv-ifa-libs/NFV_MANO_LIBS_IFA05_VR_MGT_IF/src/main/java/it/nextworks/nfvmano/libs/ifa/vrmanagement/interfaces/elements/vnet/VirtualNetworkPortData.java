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

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VirtualNetworkPortData information element encapsulates information 
 * to allocate or update virtual network ports for network resources. 
 * A network port is a communication endpoint under a network.
 * 
 *  REF IFA 005 v2.3.1 - 8.4.4.5
 * 
 * @author nextworks
 *
 */
public class VirtualNetworkPortData implements InterfaceInformationElement {

	private String portType;
	private String networkId;
	private String segmentId;
	private int bandwidth;
	private Map<String, String> metadata = new HashMap<>();
	
	public VirtualNetworkPortData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param portType Type of network port. Examples of types are access ports (layer 2 or 3), or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks.
	 * @param networkId Identifier of the network that the port belongs to. When creating a port, such port needs to be part of a network.
	 * @param segmentId The isolated segment the network port belongs to. For instance, for a "vlan", it corresponds to the vlan identifier; and for a "gre", this corresponds to a gre key.
	 * @param bandwidth The bandwidth of the virtual network port (in Mbps).
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualNetworkPortData(String portType,
			String networkId,
			String segmentId,
			int bandwidth,
			Map<String, String> metadata) {
		this.portType = portType;
		this.networkId = networkId;
		this.segmentId = segmentId;
		this.bandwidth = bandwidth;
		if (metadata != null) this.metadata = metadata;
	}
	
	

	/**
	 * @return the portType
	 */
	public String getPortType() {
		return portType;
	}

	/**
	 * @return the networkId
	 */
	public String getNetworkId() {
		return networkId;
	}

	/**
	 * @return the segmentId
	 */
	public String getSegmentId() {
		return segmentId;
	}

	/**
	 * @return the bandwidth
	 */
	public int getBandwidth() {
		return bandwidth;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (networkId != null) throw new MalformattedElementException("Virtual Network Port Data without network ID");
	}

}
