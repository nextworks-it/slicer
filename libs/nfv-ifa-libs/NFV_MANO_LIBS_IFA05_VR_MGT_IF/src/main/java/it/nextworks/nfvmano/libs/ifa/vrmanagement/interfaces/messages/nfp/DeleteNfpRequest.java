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

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to delete an NFP
 * REF IFA 005 v2.3.1 - 7.4.5.4
 * 
 * @author nextworks
 *
 */
public class DeleteNfpRequest implements InterfaceMessage {

	private List<String> nfpId = new ArrayList<String>();
	
	public DeleteNfpRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param nfpId A unique identification of the NFP(s) to be deleted.
	 */
	public DeleteNfpRequest(List<String> nfpId) {	
		if (nfpId != null) this.nfpId = nfpId;
	}
	
	

	/**
	 * @return the nfpId
	 */
	public List<String> getNfpId() {
		return nfpId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((nfpId == null) || (nfpId.isEmpty())) throw new MalformattedElementException("Delete NFP request without IDs");
	}

}
