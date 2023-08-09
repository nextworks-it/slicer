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
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element describes an instantiated virtual network port resource.
 *  
 *  REF IFA 005 v2.3.1 - 8.4.5.4
 * 
 * @author nextworks
 *
 */
public class VirtualNetworkPort implements InterfaceInformationElement {

	private String resourceId;
	private String networkId;
	private String attachedResourceId;
	private String portType;
	private String segmentId;
	private int bandwidth;
	private OperationalState operationalState;
	private Map<String, String> metadata = new HashMap<>();
	
	
	public VirtualNetworkPort() { }

	/**
	 * Constructor
	 * 
	 * @param resourceId Identifier of the virtual network port.
	 * @param networkId Identifier of the network that the port belongs to. When creating a port, such port needs to be part of a network.
	 * @param attachedResourceId Identifier of the attached resource to the network port (e.g. a virtualised compute resource, or identifier of the virtual network interface).
	 * @param portType Type of network port.
	 * @param segmentId The isolated segment the network port belongs to.
	 * @param bandwidth The bandwidth of the virtual network port (in Mbps).
	 * @param operationalState The operational state of the virtual network port.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualNetworkPort(String resourceId,
			String networkId,
			String attachedResourceId,
			String portType,
			String segmentId,
			int bandwidth,
			OperationalState operationalState,
			Map<String, String> metadata) { 
		this.resourceId = resourceId;
		this.networkId = networkId;
		this.attachedResourceId = attachedResourceId;
		this.portType = portType;
		this.segmentId = segmentId;
		this.bandwidth = bandwidth;
		this.operationalState = operationalState;
		if (metadata != null) this.metadata = metadata;
	}
	
	

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @return the networkId
	 */
	public String getNetworkId() {
		return networkId;
	}

	/**
	 * @return the attachedResourceId
	 */
	public String getAttachedResourceId() {
		return attachedResourceId;
	}

	/**
	 * @return the portType
	 */
	public String getPortType() {
		return portType;
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
		if (resourceId == null) throw new MalformattedElementException("Virtual network port without resource ID");
		if (networkId == null) throw new MalformattedElementException("Virtual network port without network ID");
	}

}
