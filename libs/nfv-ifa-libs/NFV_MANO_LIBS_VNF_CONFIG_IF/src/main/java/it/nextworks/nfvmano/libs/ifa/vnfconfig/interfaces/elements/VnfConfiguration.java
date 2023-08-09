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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.KeyValuePair;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This data type provides the list of attributes for the configuration of a VNF instance.
 * 
 * REF IFA 007 v2.3.1 - 9.2.2
 * 
 * @author nextworks
 *
 */
public class VnfConfiguration implements InterfaceInformationElement {

	private List<CpConfiguration> cpConfiguration = new ArrayList<>();
	private String dhcpServer;
	private Set<KeyValuePair> vnfSpecificData = new HashSet<>();
	
	
	public VnfConfiguration() {	}
	
	/**
	 * Constructor
	 * 
	 * @param cpConfiguration External CPs
	 * @param dhcpServer Identifies a DHCP server that the VNF can use to obtain IP addresses to be assigned to its external CPs.
	 * @param vnfSpecificData Configuration object containing values of VNF configurable properties
	 */
	public VnfConfiguration(
			List<CpConfiguration> cpConfiguration,
			String dhcpServer,
			Set<KeyValuePair> vnfSpecificData
	) {
		if (cpConfiguration != null) this.cpConfiguration = cpConfiguration;
		if (vnfSpecificData != null) this.vnfSpecificData = vnfSpecificData;
		this.dhcpServer = dhcpServer;
	}

	public VnfConfiguration(
			List<CpConfiguration> cpConfiguration,
			String dhcpServer,
			KeyValuePair... vnfSpecificData
	) {
		this(cpConfiguration, dhcpServer, new HashSet<>(Arrays.asList(vnfSpecificData)));
	}

	
	
	/**
	 * @return the cpConfiguration
	 */
	public List<CpConfiguration> getCpConfiguration() {
		return cpConfiguration;
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
		for (CpConfiguration cc : cpConfiguration) cc.isValid();
	}

}
