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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualCompute;

/**
 * Response to a query virtual compute resource
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.3
 * 
 * @author nextworks
 *
 */
public class QueryComputeResponse implements InterfaceMessage {

	private List<VirtualCompute> virtualCompute = new ArrayList<>(); 
	
	public QueryComputeResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param virtualCompute Element containing information about the virtual compute resource(s) matching the filter.
	 */
	public QueryComputeResponse(List<VirtualCompute> virtualCompute) {	
		if (virtualCompute != null) this.virtualCompute = virtualCompute;
	}
	
	

	/**
	 * @return the virtualCompute
	 */
	public List<VirtualCompute> getVirtualCompute() {
		return virtualCompute;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VirtualCompute vc : virtualCompute) vc.isValid();
	}

}
