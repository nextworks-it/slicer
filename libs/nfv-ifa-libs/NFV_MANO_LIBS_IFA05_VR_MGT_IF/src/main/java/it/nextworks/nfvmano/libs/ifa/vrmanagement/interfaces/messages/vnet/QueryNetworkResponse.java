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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vnet;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.NetworkSubnet;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.VirtualNetwork;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.VirtualNetworkPort;

/**
 * Response to a query network request.
 * 
 * REF IFA 005 v2.3.1 - 7.4.1.3
 * 
 * @author nextworks
 *
 */
public class QueryNetworkResponse implements InterfaceMessage {

	//Note: the standard version includes only network data. This has been updated to cover also subnets and ports.
	
	private List<VirtualNetwork> networkData = new ArrayList<>();
	private List<NetworkSubnet> subnetData = new ArrayList<>();
	private List<VirtualNetworkPort> networkPortData = new ArrayList<>();
	
	
	public QueryNetworkResponse() { }
	
	public QueryNetworkResponse(List<VirtualNetwork> networkData,
			List<NetworkSubnet> subnetData,
			List<VirtualNetworkPort> networkPortData) {
		if (networkData != null) this.networkData = networkData;
		if (subnetData != null) this.subnetData = subnetData;
		if (networkPortData != null) this.networkPortData = networkPortData;
	}
	
	

	/**
	 * @return the networkData
	 */
	public List<VirtualNetwork> getNetworkData() {
		return networkData;
	}

	/**
	 * @return the subnetData
	 */
	public List<NetworkSubnet> getSubnetData() {
		return subnetData;
	}

	/**
	 * @return the networkPortData
	 */
	public List<VirtualNetworkPort> getNetworkPortData() {
		return networkPortData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (networkData != null) {
			for (VirtualNetwork n: networkData) n.isValid();
		}
		if (subnetData != null) {
			for (NetworkSubnet s: subnetData) s.isValid();
		}
		if (networkPortData != null) {
			for (VirtualNetworkPort p: networkPortData) p.isValid();
		}
	}

}
