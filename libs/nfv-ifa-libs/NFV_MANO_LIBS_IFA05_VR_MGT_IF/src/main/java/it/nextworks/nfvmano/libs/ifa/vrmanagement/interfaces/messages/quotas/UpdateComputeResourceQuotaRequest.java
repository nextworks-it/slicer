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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.quotas.VirtualComputeQuotaData;

/**
 * Request to update a compute resource quota.
 * 
 * REF IFA 005 v2.3.1 - 7.9.1.4
 * 
 * @author nextworks
 *
 */
public class UpdateComputeResourceQuotaRequest implements InterfaceMessage {

	private String resourceGroupId;
	private VirtualComputeQuotaData virtualComputeQuota;
	
	public UpdateComputeResourceQuotaRequest() { }

	
	
	/**
	 * Constructor
	 * 
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
	 * @param virtualComputeQuota New amount of compute resources to be restricted by the quota.
	 */
	public UpdateComputeResourceQuotaRequest(String resourceGroupId, VirtualComputeQuotaData virtualComputeQuota) {
		this.resourceGroupId = resourceGroupId;
		this.virtualComputeQuota = virtualComputeQuota;
	}



	/**
	 * @return the resourceGroupId
	 */
	public String getResourceGroupId() {
		return resourceGroupId;
	}



	/**
	 * @return the virtualComputeQuota
	 */
	public VirtualComputeQuotaData getVirtualComputeQuota() {
		return virtualComputeQuota;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceGroupId == null) throw new MalformattedElementException("Update compute quota request without resource group ID.");
		if (virtualComputeQuota == null) throw new MalformattedElementException("Update compute quota request without quota description");
		else virtualComputeQuota.isValid();
	}

}
