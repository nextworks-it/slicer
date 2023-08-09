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
 * Delete threshold response
 * 
 * REF IFA 013 v2.3.1 - 7.5.8
 * 
 * @author nextworks
 *
 */
public class DeleteThresholdsResponse implements InterfaceMessage {

	private List<String> deletedThresholdId = new ArrayList<>();
	
	public DeleteThresholdsResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param deletedThresholdId Identifiers of the thresholds that have been deleted successfully.
	 */
	public DeleteThresholdsResponse(List<String> deletedThresholdId) {
		if (deletedThresholdId != null) this.deletedThresholdId = deletedThresholdId;
	}

	
	
	/**
	 * @return the deletedThresholdId
	 */
	public List<String> getDeletedThresholdId() {
		return deletedThresholdId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((deletedThresholdId == null) || (deletedThresholdId.isEmpty())) throw new MalformattedElementException("Delete threshold response without IDs");
	}

}
