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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * A virtual interface represents the data of a virtual network interface specific 
 * to a Virtual Compute Resource instance.
 * 
 * REF IFA 005 v2.3.1 - sect. 7.4.3.7
 * 
 * @author nextworks
 *
 */
public class VirtualInterfaceData implements InterfaceInformationElement {
	
	private List<String> ipAddress = new ArrayList<>();
	private String macAddress;

	public VirtualInterfaceData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param ipAddress The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.
	 * @param macAddress The MAC address desired for the virtual network interface. The cardinality can be 0 to allow for network interface without specific MAC address configuration.
	 */
	public VirtualInterfaceData(List<String> ipAddress,
			String macAddress) {	
		if (ipAddress != null) this.ipAddress = ipAddress;
		this.macAddress = macAddress;
	}
	
	

	/**
	 * @return the ipAddress
	 */
	public List<String> getIpAddress() {
		return ipAddress;
	}

	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
