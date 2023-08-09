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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.NetworkSubnet;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.VirtualNetwork;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.VirtualNetworkPort;


/**
 * Reply to an allocate network request, sent from the VIM 
 * 
 * REF IFA 005 v2.3.1 - 7.4.1.2
 * 
 * @author nextworks
 *
 */
public class AllocateNetworkResponse implements InterfaceMessage {

	private VirtualNetwork networkData;
	private NetworkSubnet subnetData;
	private VirtualNetworkPort networkPortData;
	
	public AllocateNetworkResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param networkData The data relative to the instantiated virtualised network resource.
	 * @param subnetData The data relative to the instantiated subnet resource.
	 * @param networkPortData The data relative to the instantiated virtualised network port resource.
	 */
	public AllocateNetworkResponse(VirtualNetwork networkData,
			NetworkSubnet subnetData,
			VirtualNetworkPort networkPortData) {
		this.networkData = networkData;
		this.subnetData = subnetData;
		this.networkPortData = networkPortData;
	}
	
	

	/**
	 * @return the networkData
	 */
	public VirtualNetwork getNetworkData() {
		return networkData;
	}

	/**
	 * @return the subnetData
	 */
	public NetworkSubnet getSubnetData() {
		return subnetData;
	}

	/**
	 * @return the networkPortData
	 */
	public VirtualNetworkPort getNetworkPortData() {
		return networkPortData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (networkData != null) networkData.isValid();
		if (networkPortData != null) networkPortData.isValid();
		if (subnetData != null) subnetData.isValid();
	}

}
