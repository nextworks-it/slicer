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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vnet.VirtualNetworkResourceInformation;


public class QueryVirtualNetworkResourceInfoResponse implements InterfaceMessage {

	private List<VirtualNetworkResourceInformation> virtualisedResourceInformation = new ArrayList<>();
	
	public QueryVirtualNetworkResourceInfoResponse() {}
	
	/**
	 * Constructor
	 * 
	 * @param virtualisedResourceInformation Virtualised network resource information in the VIM that satisfies the query condition
	 */
	public QueryVirtualNetworkResourceInfoResponse(List<VirtualNetworkResourceInformation> virtualisedResourceInformation) {
		if (virtualisedResourceInformation != null) this.virtualisedResourceInformation = virtualisedResourceInformation;
	}
	
	/**
	 * @return the virtualisedResourceInformation
	 */
	public List<VirtualNetworkResourceInformation> getVirtualisedResourceInformation() {
		return virtualisedResourceInformation;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (virtualisedResourceInformation == null) throw new MalformattedElementException("Query virtual network resource information response without info");
		else {
			for (VirtualNetworkResourceInformation vcr : virtualisedResourceInformation) vcr.isValid();
		}
	}

}
