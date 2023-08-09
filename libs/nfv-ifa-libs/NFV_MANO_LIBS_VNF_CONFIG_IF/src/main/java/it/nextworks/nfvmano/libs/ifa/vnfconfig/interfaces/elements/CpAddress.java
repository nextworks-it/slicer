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
package it.nextworks.nfvmano.libs.ifa.vnfconfig.interfaces.elements;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This data type provides the list of attributes associated to a CP instance address.
 * 
 * REF IFA 007 v2.3.1 - 9.2.5
 * 
 * @author nextworks
 *
 */
public class CpAddress implements InterfaceInformationElement {

	private String address;
	private boolean useDynamicAddress;
	private String port;
	
	public CpAddress() { }
	
	/**
	 * Constructor
	 * 
	 * @param address The address assigned to the CP instance (e.g. IP address, MAC address, etc.). It shall be provided for configuring a fixed address.
	 * @param useDynamicAddress It determines whether an address shall be assigned dynamically. It shall be provided if a dynamic address needs to be configured on the CP.
	 * @param port The port assigned to the CP instance (e.g. IP port number, Ethernet port number, etc.).
	 */
	public CpAddress(String address,
			boolean useDynamicAddress,
			String port) {
		this.address = address;
		this.useDynamicAddress = useDynamicAddress;
		this.port = port;
	}
	
	

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the useDynamicAddress
	 */
	public boolean isUseDynamicAddress() {
		return useDynamicAddress;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
