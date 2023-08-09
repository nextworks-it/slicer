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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to terminate a resource quota.
 * 
 * REF IFA 005 v2.3.1 - 7.9.1.5
 * REF IFA 005 v2.3.1 - 7.9.2.5
 * REF IFA 005 v2.3.1 - 7.9.3.5
 * 
 * 
 * @author nextworks
 *
 */
public class TerminateResourceQuotaRequest implements InterfaceMessage {

	private List<String> resourceGroupId = new ArrayList<>();
	
	public TerminateResourceQuotaRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
	 */
	public TerminateResourceQuotaRequest(List<String> resourceGroupId) {
		if (resourceGroupId != null) this.resourceGroupId = resourceGroupId;
	}
	
	

	/**
	 * @return the resourceGroupId
	 */
	public List<String> getResourceGroupId() {
		return resourceGroupId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceGroupId.isEmpty()) throw new MalformattedElementException("Terminate resource quota request without resource group IDs.");
	}

}
