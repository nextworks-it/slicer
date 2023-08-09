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
 * Request to modify a VNF
 * 
 * REF IFA 007 v2.3.1 - 7.2.12
 * 
 * @author nextworks
 *
 */
public class ModifyVnfInformationRequest implements InterfaceMessage {

	private String vnfInstanceId;
	private Map<String, String> newValues = new HashMap<>();
	
	public ModifyVnfInformationRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance for which the writeable attributes of VnfInfo are requested to be modified.
	 * @param newValues Contains the set of attributes to update.
	 */
	public ModifyVnfInformationRequest(String vnfInstanceId,
			Map<String, String> newValues) {	
		this.vnfInstanceId = vnfInstanceId;
		if (newValues != null) this.newValues = newValues;
	}

	
	
	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the newValues
	 */
	public Map<String, String> getNewValues() {
		return newValues;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Modify VNF request without ID");
		if ((newValues == null) || (newValues.isEmpty())) throw new MalformattedElementException("Modify VNF request without new values");
	}

}
