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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 *The information element describes the information needed to heal
 *a VNF that is part of an NS. The NFVO shall then 
 *invoke the HealVNF operation towards the appropriate VNFM
 * 
 *  REF IFA 013 v2.3.1 - 8.3.4.25
 * 
 * @author nextworks
 *
 */
public class HealVnfData  implements InterfaceInformationElement {

	private String vnfInstanceId;
	private String cause;
	private Map<String, String> additionalParam = new HashMap<>();
	
	public HealVnfData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifies the VNF instance, part of the NS, requiring a healing action.
	 * @param cause Indicates the reason why a healing procedure is required.
	 * @param additionalParam Additional parameters passed by the NFVO as input to the healing process, specific to the VNF being healed.
	 */
	public HealVnfData(String vnfInstanceId,
			String cause,
			Map<String, String> additionalParam) {
		this.vnfInstanceId = vnfInstanceId;
		this.cause = cause;
		this.additionalParam = additionalParam;
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
		if (vnfInstanceId != null) throw new MalformattedElementException("Heal VNF data without VNF instance ID");
	}

}
