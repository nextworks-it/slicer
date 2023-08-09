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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 *  Request to disable a VNF package
 *  
 *  REF IFA 013 v2.3.1 - 7.7.4
 * 
 * @author nextworks
 *
 */
public class DisableVnfPackageRequest implements InterfaceMessage {

	private String onboardedVnfPkgInfoId;
	
	public DisableVnfPackageRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param onboardedVnfPkgInfoId Identifier of information held by the NFVO about the specific on-boarded VNF Package. This identifier was allocated by the NFVO.
	 */
	public DisableVnfPackageRequest(String onboardedVnfPkgInfoId) {	
		this.onboardedVnfPkgInfoId = onboardedVnfPkgInfoId;
	}
	
	/**
	 * @return the onboardedVnfPkgInfoId
	 */
	public String getOnboardedVnfPkgInfoId() {
		return onboardedVnfPkgInfoId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (onboardedVnfPkgInfoId == null) throw new MalformattedElementException("Disable VNF package request without package info ID.");
	}

}
