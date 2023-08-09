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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * A virtual network interface resource is a communication endpoint under an instantiated compute resource.
 * 
 * REF IFA 005 v2.3.1 - 8.4.3.6
 * 
 * @author nextworks
 *
 */
public class VirtualNetworkInterface implements InterfaceInformationElement {

	private String resourceId;
	private String ownerId;
	private String networkId;
	private String networkPortId;
	private List<String> ipAddress = new ArrayList<>();
	private String typeVirtualNic;
	private List<String> typeConfiguration = new ArrayList<>();
	private String macAddress;
	private int bandwidth;
	private List<String> accelerationCapability = new ArrayList<>(); 
	private OperationalState operationalState;
	private Map<String, String> metadata = new HashMap<>();
	
	public VirtualNetworkInterface() {}
	
	/**
	 * Constructor
	 * 
	 * @param resourceId Identifier of the virtual network interface.
	 * @param ownerId Identifier of the owner of the network interface
	 * @param networkId In the case when the virtual network interface is attached to the network, it identifies such a network.
	 * @param networkPortId If the virtual network interface is attached to a specific network port, it identifies such a network port.
	 * @param ipAddress The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to.
	 * @param typeVirtualNic Type of network interface.
	 * @param typeConfiguration Extra configuration that the virtual network interface supports based on the type of virtual network interface
	 * @param macAddress The MAC address of the virtual network interface.
	 * @param bandwidth The bandwidth of the virtual network interface (in Mbps).
	 * @param accelerationCapability It specifies if the virtual network interface requires certain acceleration capabilities
	 * @param operationalState The operational state of the virtual network interface.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualNetworkInterface(String resourceId,
			String ownerId,
			String networkId,
			String networkPortId,
			List<String> ipAddress,
			String typeVirtualNic,
			List<String> typeConfiguration,
			String macAddress,
			int bandwidth,
			List<String> accelerationCapability,
			OperationalState operationalState,
			Map<String, String> metadata) {
		this.resourceId = resourceId;
		this.ownerId = ownerId;
		this.networkId = networkId;
		this.networkPortId = networkPortId;
		if (ipAddress != null) this.ipAddress = ipAddress;
		this.typeVirtualNic = typeVirtualNic;
		if (typeConfiguration != null) this.typeConfiguration = typeConfiguration;
		this.macAddress = macAddress;
		this.bandwidth = bandwidth;
		if (accelerationCapability != null) this.accelerationCapability = accelerationCapability;
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
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @return the networkId
	 */
	public String getNetworkId() {
		return networkId;
	}

	/**
	 * @return the networkPortId
	 */
	public String getNetworkPortId() {
		return networkPortId;
	}

	/**
	 * @return the ipAddress
	 */
	public List<String> getIpAddress() {
		return ipAddress;
	}

	/**
	 * @return the typeVirtualNic
	 */
	public String getTypeVirtualNic() {
		return typeVirtualNic;
	}

	/**
	 * @return the typeConfiguration
	 */
	public List<String> getTypeConfiguration() {
		return typeConfiguration;
	}

	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @return the bandwidth
	 */
	public int getBandwidth() {
		return bandwidth;
	}

	/**
	 * @return the accelerationCapability
	 */
	public List<String> getAccelerationCapability() {
		return accelerationCapability;
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
		if (resourceId == null) throw new MalformattedElementException("Virtual network interface without resource ID");
		if (ownerId == null) throw new MalformattedElementException("Virtual network interface without owner ID");
		if (macAddress == null) throw new MalformattedElementException("Virtual network interface without MAC address");
	}

}
