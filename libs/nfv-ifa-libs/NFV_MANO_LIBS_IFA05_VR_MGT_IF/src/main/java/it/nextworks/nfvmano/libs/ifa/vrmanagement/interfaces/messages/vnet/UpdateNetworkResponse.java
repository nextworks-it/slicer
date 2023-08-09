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
 * Response to an update network request
 * 
 * REF IFA 005 v2.3.1 - 7.4.1.4
 * 
 * @author nextworks
 *
 */
public class UpdateNetworkResponse implements InterfaceMessage {

	private String networkResourceId;
	private VirtualNetwork networkData;
	private NetworkSubnet subnetData;
	private VirtualNetworkPort networkPortData;
	
	
	public UpdateNetworkResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param networkResourceId The identifier of the virtualised network resource that has been updated. This parameter has the same value as the input parameter
	 * @param networkData If network types are updated satisfactorily, it contains the data relative to the updated network.
	 * @param subnetData If subnet types are updated satisfactorily, it contains the data relative to the updated subnet.
	 * @param networkPortData If network port types are updated satisfactorily, it contains the data relative to the updated network port.
	 */
	public UpdateNetworkResponse(String networkResourceId,
			VirtualNetwork networkData,
			NetworkSubnet subnetData,
			VirtualNetworkPort networkPortData) {
		this.networkResourceId = networkResourceId;
		this.networkData = networkData;
		this.subnetData = subnetData;
		this.networkPortData = networkPortData;
	}
	
	

	/**
	 * @return the networkResourceId
	 */
	public String getNetworkResourceId() {
		return networkResourceId;
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
		if (networkResourceId == null) throw new MalformattedElementException("Update network response without network resource ID");
		if (networkData != null) networkData.isValid();
		if (networkPortData != null) networkPortData.isValid();
		if (subnetData != null) subnetData.isValid();
	}

}
