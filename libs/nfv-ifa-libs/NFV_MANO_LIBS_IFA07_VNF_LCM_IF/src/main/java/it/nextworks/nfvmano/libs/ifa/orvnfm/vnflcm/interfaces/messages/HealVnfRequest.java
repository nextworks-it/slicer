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
package it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to heal a VNF
 * 
 * REF IFA 007 v2.3.1 - 7.2.10
 * 
 * @author nextworks
 *
 */
public class HealVnfRequest implements InterfaceMessage {

	private String vnfInstanceId;
	private String cause;
	private Map<String, String> additionalParam = new HashMap<>();
	
	public HealVnfRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifies the VNF instance requiring a healing action.
	 * @param cause Indicates the reason why a healing procedure is required.
	 * @param additionalParam Additional parameters passed by the NFVO as input to the healing process, specific to the VNF being healed.
	 */
	public HealVnfRequest(String vnfInstanceId,
			String cause,
			Map<String,String> additionalParam) {
		this.vnfInstanceId = vnfInstanceId;
		this.cause = cause;
		if (additionalParam != null) this.additionalParam = additionalParam;
	}
	
	

	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}

	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.vnfInstanceId == null) throw new MalformattedElementException("VNF heal request without VNF instance ID");

	}

}
