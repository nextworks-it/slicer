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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vcompute;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualComputeResourceInformation;

/**
 * Response to a query virtual compute resource info request
 * 
 *  REF IFA 005 v2.3.1 - 7.3.3.4
 * 
 * @author nextworks
 *
 */
public class QueryVirtualComputeResourceInfoResponse implements InterfaceMessage {

	private List<VirtualComputeResourceInformation> virtualisedResourceInformation = new ArrayList<>();
	
	public QueryVirtualComputeResourceInfoResponse() {}
	
	/**
	 * Constructor
	 * 
	 * @param virtualisedResourceInformation Virtualised compute resource information in the VIM that satisfies the query condition
	 */
	public QueryVirtualComputeResourceInfoResponse(List<VirtualComputeResourceInformation> virtualisedResourceInformation) {
		if (virtualisedResourceInformation != null) this.virtualisedResourceInformation = virtualisedResourceInformation;
	}
	
	/**
	 * @return the virtualisedResourceInformation
	 */
	public List<VirtualComputeResourceInformation> getVirtualisedResourceInformation() {
		return virtualisedResourceInformation;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (virtualisedResourceInformation == null) throw new MalformattedElementException("Query virtual compute resource information response without info");
		else {
			for (VirtualComputeResourceInformation vcr : virtualisedResourceInformation) vcr.isValid();
		}
	}

}
