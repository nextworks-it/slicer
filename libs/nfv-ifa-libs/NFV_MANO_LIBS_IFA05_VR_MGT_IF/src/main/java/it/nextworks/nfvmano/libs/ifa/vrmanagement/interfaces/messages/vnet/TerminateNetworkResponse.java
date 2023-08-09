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

/**
 * Response to terminate network request
 * 
 * REF IFA 005 v2.3.1 - 7.4.1.5
 * 
 * @author nextworks
 *
 */
public class TerminateNetworkResponse implements InterfaceMessage {

	private List<String> networkResourceId = new ArrayList<>();

	
	public TerminateNetworkResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param networkResourceId Identifier and type of the virtualised network resource(s) successfully terminated
	 */
	public TerminateNetworkResponse(List<String> networkResourceId) {	
		if (networkResourceId != null) this.networkResourceId = networkResourceId;
	}
	
	

	/**
	 * @return the networkResourceId
	 */
	public List<String> getNetworkResourceId() {
		return networkResourceId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((networkResourceId == null) || (networkResourceId.isEmpty()))
			throw new MalformattedElementException("Terminate network response without resource IDs");
	}

}
