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
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Pnfd;

/**
 * Request to on-board a PNFD in the NFVO.
 * 
 *  REF IFA 013 v2.3.1 - 7.2.8
 *
 * @author nextworks
 *
 */
public class OnboardPnfdRequest implements InterfaceMessage {
	
	private Pnfd pnfd;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, String> userDefinedData = new HashMap<>();

	public OnboardPnfdRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param pnfd PNFD to be on-boarded.
	 * @param userDefinedData User defined data for the PNFD.
	 */
	public OnboardPnfdRequest(Pnfd pnfd,
			Map<String, String> userDefinedData) {
		this.pnfd = pnfd;
		if (userDefinedData != null) this.userDefinedData = userDefinedData;
	}

	/**
	 * @return the pnfd
	 */
	public Pnfd getPnfd() {
		return pnfd;
	}

	/**
	 * @return the userDefinedData
	 */
	public Map<String, String> getUserDefinedData() {
		return userDefinedData;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (pnfd == null) {
			throw new MalformattedElementException("On board PNFD request without PNFD");
		}
		else pnfd.isValid();
	}

}
