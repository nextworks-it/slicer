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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.nfp;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * REF IFA 005 v2.3.1 - 8.9
 * 
 * 
 * @author nextworks
 *
 */
public class Nfp implements DescriptorInformationElement {

	private String nfpId;
	private List<String> virtualNetworkPort = new ArrayList<>();
	private int totalVnp;
	private String nfpRule;
	private OperationalState nfpState;
	
	public Nfp() {	}
	
	/**
	 * Constructor
	 * 
	 * @param nfpId Identification of the NFP
	 * @param virtualNetworkPort The identification of a virtual network port.
	 * @param totalVnp Total number of virtual network ports in this NFP queried for.
	 * @param nfpRule NFP classification and selection rule(s).
	 * @param nfpState An indication of whether the NFP is enabled or disabled.
	 */
	public Nfp(String nfpId,
			List<String> virtualNetworkPort,
			int totalVnp,
			String nfpRule,
			OperationalState nfpState) {
		this.nfpId = nfpId;
		if (virtualNetworkPort != null) this.virtualNetworkPort = virtualNetworkPort;
		this.totalVnp = totalVnp;
		this.nfpRule = nfpRule;
		this.nfpState = nfpState;
	}
	
	

	/**
	 * @return the nfpId
	 */
	public String getNfpId() {
		return nfpId;
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

	/**
	 * @return the nfpState
	 */
	public OperationalState getNfpState() {
		return nfpState;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nfpId == null) throw new MalformattedElementException("NFP without ID");
		if ((virtualNetworkPort == null) || (virtualNetworkPort.isEmpty())) throw new MalformattedElementException("NFP without virtual network port");
	}

}
