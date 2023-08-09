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
package it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages;



import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;

/**
 * Request to on-board an NSD in the NFVO.
 * 
 *  REF IFA 013 v2.3.1 - 7.2.2
 *
 * @author nextworks
 *
 */
public class OnboardNsdRequest implements InterfaceMessage {

	private  Nsd nsd;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, String> userDefinedData = new HashMap<>();
	
	public OnboardNsdRequest() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * constructor
	 * 	
	 * @param nsd NSD to be on-boarded.
	 * @param userDefinedData User defined data for the NSD.
	 */
	public OnboardNsdRequest(Nsd nsd,
			 Map<String, String> userDefinedData) {
		this.nsd = nsd;
		if (userDefinedData != null) this.userDefinedData = userDefinedData;
	}
	
	/**
	 * @return the nsd
	 */
	public Nsd getNsd() {
		return nsd;
	}

	/**
	 * @return the userDefinedData
	 */
	public Map<String, String> getUserDefinedData() {
		return userDefinedData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsd == null) {
			throw new MalformattedElementException("On board NSD request without NSD");
		}
		else nsd.isValid();
	}
	
}
