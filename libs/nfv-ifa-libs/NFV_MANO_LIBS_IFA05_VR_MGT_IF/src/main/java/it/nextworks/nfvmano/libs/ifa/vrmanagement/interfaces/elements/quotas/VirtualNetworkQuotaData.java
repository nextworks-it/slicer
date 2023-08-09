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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.quotas;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * REF IFA 005 v2.3.1 - 8.11.3.2
 * 
 * @author nextworks
 *
 */
public class VirtualNetworkQuotaData implements InterfaceInformationElement {

	private int numPublicIps;
	private int numPorts;
	private int numSubnets;
	
	public VirtualNetworkQuotaData() {	}
	
	

	/**
	 * Constructor
	 * 
	 * @param numPublicIps Number of public IP addresses to be restricted by the quota
	 * @param numPorts Number of ports to be restricted by the quota
	 * @param numSubnets Number of subnets to be restricted by the quota
	 */
	public VirtualNetworkQuotaData(int numPublicIps, int numPorts, int numSubnets) {
		this.numPublicIps = numPublicIps;
		this.numPorts = numPorts;
		this.numSubnets = numSubnets;
	}



	/**
	 * @return the numPublicIps
	 */
	public int getNumPublicIps() {
		return numPublicIps;
	}



	/**
	 * @return the numPorts
	 */
	public int getNumPorts() {
		return numPorts;
	}



	/**
	 * @return the numSubnets
	 */
	public int getNumSubnets() {
		return numSubnets;
	}



	@Override
	public void isValid() throws MalformattedElementException {	}

}
