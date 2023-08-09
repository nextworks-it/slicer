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

/**
 * Request to update an NFP
 * 
 * REF IFA 005 v2.3.1 - 7.4.5.6
 * 
 * @author nextworks
 *
 */
public class UpdateNfpRequest implements InterfaceMessage {

	private String nfpId;
	private String nfpRule;
	
	public UpdateNfpRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param nfpId Identifier of the NFP to be modified.
	 * @param nfpRule NFP classification and selection rule
	 */
	public UpdateNfpRequest(String nfpId,
			String nfpRule) {
		this.nfpId = nfpId;
		this.nfpRule = nfpRule;
	}
	
	

	/**
	 * @return the nfpId
	 */
	public String getNfpId() {
		return nfpId;
	}

	/**
	 * @return the nfpRule
	 */
	public String getNfpRule() {
		return nfpRule;
	}

	@Override
	public void isValid() throws MalformattedElementException {	
		if (nfpId == null) throw new MalformattedElementException("NFP update request without NFP ID");
		if (nfpRule == null) throw new MalformattedElementException("NFP update request without NFP rule");
	}

}
