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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.nfp.Nfp;

/**
 * Response to an update NFP request
 * 
 * REF IFA 005 v2.3.1 - 7.4.5.6
 * 
 * @author nextworks
 *
 */
public class UpdateNfpResponse implements InterfaceMessage {

	private Nfp nfpInfo;
	
	public UpdateNfpResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param nfpInfo Provide the updated NFP information of the NFP instance.
	 */
	public UpdateNfpResponse(Nfp nfpInfo) { 
		this.nfpInfo = nfpInfo;
	}

	/**
	 * @return the nfpInfo
	 */
	public Nfp getNfpInfo() {
		return nfpInfo;
	}

	@Override
	public void isValid() throws MalformattedElementException {	
		if (nfpInfo == null) throw new MalformattedElementException("Update NFP response without NFP info");
		else nfpInfo.isValid();
	}

}
