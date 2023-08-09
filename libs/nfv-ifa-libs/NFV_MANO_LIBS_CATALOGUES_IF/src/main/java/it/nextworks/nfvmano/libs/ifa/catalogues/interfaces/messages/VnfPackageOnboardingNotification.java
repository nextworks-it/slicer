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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This notification indicates the on-boarding of a VNF Package. 
 * 
 *  REF IFA 013 v2.3.1 - 8.6.8
 * 
 * @author nextworks
 *
 */
public class VnfPackageOnboardingNotification implements InterfaceMessage {

	private String onboardedVnfPkgInfoId;
	private String vnfdId;
	
	public VnfPackageOnboardingNotification() {	}
	
	/**
	 * Constructor
	 * 
	 * @param onboardedVnfPkgInfoId Identifier of information held by the NFVO about the specific on- boarded VNF Package. This identifier was allocated by the NFVO.
	 * @param vnfdId Identifier that identifies the VNF Package
	 */
	@JsonCreator
	public VnfPackageOnboardingNotification(@JsonProperty("onboardedVnfPkgInfoId") String onboardedVnfPkgInfoId,
			@JsonProperty("vnfdId") String vnfdId) {
		this.onboardedVnfPkgInfoId = onboardedVnfPkgInfoId;
		this.vnfdId = vnfdId;
	}
	
	

	/**
	 * @return the onboardedVnfPkgInfoId
	 */
	public String getOnboardedVnfPkgInfoId() {
		return onboardedVnfPkgInfoId;
	}

	/**
	 * @return the vnfdId
	 */
	public String getVnfdId() {
		return vnfdId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (onboardedVnfPkgInfoId == null) throw new MalformattedElementException("VNF package on-boarding notification without VNF package ID");
		if (vnfdId == null) throw new MalformattedElementException("VNF package on-boarding notification without VNFD ID");
	}

}
