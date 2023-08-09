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
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The NetworkSubnetData information element encapsulates information to allocate or 
 * update virtualised sub-networks.
 * 
 *  REF IFA 005 v2.3.1 - 8.4.4.4
 * 
 * @author nextworks
 *
 */
public class NetworkSubnetData implements InterfaceInformationElement {

	private String networkId;
	private IpVersion ipVersion;
	private String gatewayIp;
	private String cidr;
	private boolean isDhcpEnabled;
	private List<String> addressPool = new ArrayList<>();
	private Map<String, String> metadata = new HashMap<>();
	
	public NetworkSubnetData() { }
	
	/**
	 * Constructor
	 * 
	 * @param networkId The identifier of the virtualised network that the virtualised sub-network is attached to.
	 * @param ipVersion The IP version of the network/subnetwork.
	 * @param gatewayIp Specifies the IP address of the network/subnetwork gateway when the gateway is selected by the requestor.
	 * @param cidr The CIDR of the network/subnetwork, i.e. network address and subnet mask.
	 * @param isDhcpEnabled True when DHCP is to be enabled for this network/subnetwork, or false otherwise.
	 * @param addressPool Address pools for the network/subnetwork. The cardinality can be 0 when VIM is allowed to allocate all addresses in the CIDR except for the address of the network/subnetwork gateway.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public NetworkSubnetData(String networkId,
	 IpVersion ipVersion,
	 String gatewayIp,
	 String cidr,
	 boolean isDhcpEnabled,
	 List<String> addressPool,
	 Map<String, String> metadata) {
		this.networkId = networkId;
		this.ipVersion = ipVersion;
		this.gatewayIp = gatewayIp;
		this.isDhcpEnabled = isDhcpEnabled;
		if (addressPool != null) this.addressPool = addressPool;
		if (metadata != null) this.metadata = metadata;
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
		return isDhcpEnabled;
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

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
