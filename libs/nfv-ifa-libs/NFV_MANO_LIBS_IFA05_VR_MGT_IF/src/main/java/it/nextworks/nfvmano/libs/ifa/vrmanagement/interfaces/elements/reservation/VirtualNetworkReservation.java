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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.reservation;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * REF IFA 005 v2.3.1 - 8.8.4.3
 * 
 * @author nextworks
 *
 */
public class VirtualNetworkReservation implements InterfaceInformationElement {

	private int numPublicIps;
	private VirtualNetworkAttributesReservationData networkAttributes;
	private List<VirtualNetworkPortReservationData> networkPorts = new ArrayList<>(); 
	
	public VirtualNetworkReservation() { }
	
	/**
	 * Constructor
	 * 
	 * @param numPublicIps Number of public IP addresses to be reserved.
	 * @param networkAttributes Information specifying additional attributes of the network resource to be reserved
	 * @param networkPorts List of specific network ports to be reserved
	 */
	public VirtualNetworkReservation(int numPublicIps,
			VirtualNetworkAttributesReservationData networkAttributes,
			List<VirtualNetworkPortReservationData> networkPorts) {
		this.numPublicIps = numPublicIps;
		this.networkAttributes = networkAttributes;
		if (networkPorts != null) this.networkPorts = networkPorts;
	}

	
	
	/**
	 * @return the numPublicIps
	 */
	public int getNumPublicIps() {
		return numPublicIps;
	}

	/**
	 * @return the networkAttributes
	 */
	public VirtualNetworkAttributesReservationData getNetworkAttributes() {
		return networkAttributes;
	}

	/**
	 * @return the networkPorts
	 */
	public List<VirtualNetworkPortReservationData> getNetworkPorts() {
		return networkPorts;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (networkAttributes != null) networkAttributes.isValid();
		for (VirtualNetworkPortReservationData vnprd : networkPorts) vnprd.isValid();
	}

}
