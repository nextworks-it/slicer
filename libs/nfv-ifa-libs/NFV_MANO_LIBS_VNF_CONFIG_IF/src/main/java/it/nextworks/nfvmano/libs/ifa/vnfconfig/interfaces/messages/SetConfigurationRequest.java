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
package it.nextworks.nfvmano.libs.ifa.vnfconfig.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vnfconfig.interfaces.elements.VnfConfiguration;
import it.nextworks.nfvmano.libs.ifa.vnfconfig.interfaces.elements.VnfcConfiguration;

/**
 * Message sent from the VNFM to set the configuration parameters of 
 * a VNF instance and its VNFC instance(s) or individual VNFC instances
 * 
 * REF IFA 007 v2.3.1 - 6.2.3
 * 
 * @author nextworks
 *
 */
public class SetConfigurationRequest implements InterfaceMessage {

	private String vnfInstanceId;
	private VnfConfiguration vnfConfigurationData;
	private List<VnfcConfiguration> vnfcConfigurationData = new ArrayList<>();
	
	
	public SetConfigurationRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Uniquely identifies the VNF instance
	 * @param vnfConfigurationData Configuration data for the VNF instance
	 * @param vnfcConfigurationData Configuration data for a VNFC instance
	 */
	public SetConfigurationRequest(String vnfInstanceId,
			VnfConfiguration vnfConfigurationData,
			List<VnfcConfiguration> vnfcConfigurationData) {
		this.vnfInstanceId = vnfInstanceId;
		this.vnfConfigurationData = vnfConfigurationData;
		if (vnfcConfigurationData != null) this.vnfcConfigurationData = vnfcConfigurationData;
	}
	
	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the vnfConfigurationData
	 */
	public VnfConfiguration getVnfConfigurationData() {
		return vnfConfigurationData;
	}

	/**
	 * @return the vnfcConfigurationData
	 */
	public List<VnfcConfiguration> getVnfcConfigurationData() {
		return vnfcConfigurationData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfConfigurationData != null) vnfConfigurationData.isValid();
		for (VnfcConfiguration vc : vnfcConfigurationData) vc.isValid();
	}

}
