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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Create NFP request
 * 
 * REF IFA 005 v2.3.1 - 7.4.5.2
 * 
 * @author nextworks
 *
 */
public class CreateNfpRequest implements InterfaceMessage {

	private List<String> virtualNetworkPort = new ArrayList<>();
	private int totalVnp;
	private String nfpRule;	// format still to be defined
	
	public CreateNfpRequest() {	}
	
	/**
	 * Classifier
	 * 
	 * @param virtualNetworkPort The identification of a virtual network port
	 * @param totalVnp Total number of virtual network ports in this NFP.
	 * @param nfpRule NFP classification and selection rule(s).
	 */
	public CreateNfpRequest(List<String> virtualNetworkPort,
			int totalVnp,
			String nfpRule) {	
		if (virtualNetworkPort != null) this.virtualNetworkPort = virtualNetworkPort;
		this.totalVnp = totalVnp;
		this.nfpRule = nfpRule;
	}
	
	

	/**
	 * @return the virtualNetworkPort
	 */
	public List<String> getVirtualNetworkPort() {
		return virtualNetworkPort;
	}

	/**
	 * @return the totalVnp
	 */
	public int getTotalVnp() {
		return totalVnp;
	}

	/**
	 * @return the nfpRule
	 */
	public String getNfpRule() {
		return nfpRule;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((virtualNetworkPort == null) || (virtualNetworkPort.isEmpty())) throw new MalformattedElementException("Create NFP request without network port");
		if (nfpRule == null) throw new MalformattedElementException("Create NFP request without classifier rule");
	}

}
