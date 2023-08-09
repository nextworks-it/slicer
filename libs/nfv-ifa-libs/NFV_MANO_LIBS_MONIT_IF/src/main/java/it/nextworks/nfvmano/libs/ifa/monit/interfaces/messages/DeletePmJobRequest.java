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
package it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Delete PM job request
 * 
 * REF IFA 013 v2.3.1 - 7.5.3
 * 
 * 
 * @author nextworks
 *
 */
public class DeletePmJobRequest implements InterfaceMessage {

	private List<String> pmJobId = new ArrayList<>();
	
	public DeletePmJobRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param pmJobId Identifiers of the PM job to be deleted.
	 */
	public DeletePmJobRequest(List<String> pmJobId) {
		if (pmJobId != null) this.pmJobId = pmJobId;
	}

	
	
	/**
	 * @return the pmJobId
	 */
	public List<String> getPmJobId() {
		return pmJobId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((pmJobId == null) || (pmJobId.isEmpty())) throw new MalformattedElementException("Delete PM job request without IDs");
	}

}
