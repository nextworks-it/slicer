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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.KeyValuePair;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This data type provides the list of attributes for the configuration of a VNFC instance.
 * 
 * REF IFA 007 v2.3.1 - 9.2.3
 * 
 * @author nextworks
 *
 */
public class VnfcConfiguration implements InterfaceInformationElement {

	private String vnfcId;
	private List<CpConfiguration> cp = new ArrayList<>();
	private String dhcpServer;
	private Set<KeyValuePair> vnfSpecificData = new HashSet<>();
	
	public VnfcConfiguration() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfcId Uniquely identifies a VNFC instance within the namespace of a specific VNF instance.
	 * @param cp internal CPs
	 * @param dhcpServer Identifies a DHCP server that the VNF can use to obtain IP addresses to be assigned to its CPs.
	 * @param vnfSpecificData Configuration object containing values of VNFC configurable properties
	 */
	public VnfcConfiguration(String vnfcId,
			List<CpConfiguration> cp,
			String dhcpServer,
			Set<KeyValuePair> vnfSpecificData) { 
		this.vnfcId = vnfcId;
		if (cp != null) this.cp = cp;
		this.dhcpServer = dhcpServer;
		if (vnfSpecificData != null) this.vnfSpecificData = vnfSpecificData;
	}
	
	

	/**
	 * @return the vnfcId
	 */
	public String getVnfcId() {
		return vnfcId;
	}

	/**
	 * @return the cp
	 */
	public List<CpConfiguration> getCp() {
		return cp;
	}

	/**
	 * @return the dhcpServer
	 */
	public String getDhcpServer() {
		return dhcpServer;
	}

	/**
	 * @return the vnfSpecificData
	 */
	public Set<KeyValuePair> getVnfSpecificData() {
		return vnfSpecificData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfcId == null) throw new MalformattedElementException("VNFC configuration without VNFC ID");
		for (CpConfiguration cc : cp) cc.isValid();
	}

}
