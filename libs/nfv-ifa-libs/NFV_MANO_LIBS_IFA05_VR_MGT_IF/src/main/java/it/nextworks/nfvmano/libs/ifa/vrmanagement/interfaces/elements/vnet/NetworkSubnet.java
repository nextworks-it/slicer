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
import it.nextworks.nfvmano.libs.ifa.common.enums.IpVersion;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element describes an instantiated subnet resource.
 *  
 *  REF IFA 005 v2.3.1 - 8.4.5.3
 * 
 * @author nextworks
 *
 */
public class NetworkSubnet implements InterfaceInformationElement {

	private String resourceId;
	private String networkId;
	private IpVersion ipVersion;
	private String gatewayIp;
	private String cidr;
	private boolean dhcpEnabled;
	private List<String> addressPool = new ArrayList<>();
	private OperationalState operationalState;
	private Map<String, String> metadata = new HashMap<>();
	
	public NetworkSubnet() { }

	/**
	 * Constructor
	 * 
	 * @param resourceId Identifier of the virtualised sub-network.
	 * @param networkId The identifier of the virtualised network that the virtualised sub-network is attached to. 
	 * @param ipVersion The IP version of the network/subnetwork.
	 * @param gatewayIp The IP address of the network/subnetwork gateway.
	 * @param cidr The CIDR of the network/subnetwork, i.e. network address and subnet mask.
	 * @param isDhcpEnabled True when DHCP is enabled for this network/subnetwork, or false otherwise.
	 * @param addressPool Address pools for the network/subnetwork. The cardinality can be 0 when VIM is allowed to allocate all addresses in the CIDR except for the address of the network/subnetwork gateway.
	 * @param operationalState The operational state of the virtualised sub-network.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public NetworkSubnet(String resourceId,
			String networkId,
			IpVersion ipVersion,
			String gatewayIp,
			String cidr,
			boolean isDhcpEnabled,
			List<String> addressPool,
			OperationalState operationalState,
			Map<String, String> metadata) { 
		this.resourceId = resourceId;
		this.networkId = networkId;
		this.ipVersion = ipVersion;
		this.gatewayIp = gatewayIp;
		this.cidr = cidr;
		this.dhcpEnabled = isDhcpEnabled;
		if (addressPool != null) this.addressPool = addressPool;
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
	 * @return the ipVersion
	 */
	public IpVersion getIpVersion() {
		return ipVersion;
	}

	/**
	 * @return the gatewayIp
	 */
	public String getGatewayIp() {
		return gatewayIp;
	}

	/**
	 * @return the isDhcpEnabled
	 */
	public boolean isDhcpEnabled() {
		return dhcpEnabled;
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

	
	
	/**
	 * @return the cidr
	 */
	public String getCidr() {
		return cidr;
	}

	/**
	 * @return the addressPool
	 */
	public List<String> getAddressPool() {
		return addressPool;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceId == null) throw new MalformattedElementException("Subnet without resource ID");
		if (gatewayIp == null) throw new MalformattedElementException("Subnet without gateway IP");
	}

}
