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
package it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * Response to an on-board application package request.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.5
 * 
 * @author nextworks
 *
 */
public class OnboardAppPackageResponse implements InterfaceMessage {

	private String onboardedAppPkgId;
	private String appDId;
	
	public OnboardAppPackageResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param onboardedAppPkgId Identifier of the on-boarded the Application package.
	 * @param appDId Identifier that identifies the application package in a globally unique way.
	 */
	public OnboardAppPackageResponse(String onboardedAppPkgId,
                                     String appDId) {
		this.onboardedAppPkgId = onboardedAppPkgId;
		this.appDId = appDId;
	}
	
	

	/**
	 * @return the onboardedAppPkgId
	 */
	public String getOnboardedAppPkgId() {
		return onboardedAppPkgId;
	}

	/**
	 * @return the appDId
	 */
	public String getAppDId() {
		return appDId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (onboardedAppPkgId == null) throw new MalformattedElementException("On-board app package response without app package ID");
		if (appDId == null) throw new MalformattedElementException("On-board app package response without appD ID");
	}

}
