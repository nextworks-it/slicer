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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.quotas.VirtualStorageQuotaData;

/**
 * Request to update a storage resource quota.
 * REF IFA 005 v2.3.1 - 7.9.3.4
 * 
 * @author nextworks
 *
 */
public class UpdateStorageResourceQuotaRequest implements InterfaceMessage {

	private String resourceGroupId;
	private VirtualStorageQuotaData virtualStorageQuota;
	
	public UpdateStorageResourceQuotaRequest() { }
	
	

	/**
	 * Constructor
	 * 
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
	 * @param virtualStorageQuota New amount of storage resources to be restricted by the quota.
	 */
	public UpdateStorageResourceQuotaRequest(String resourceGroupId, VirtualStorageQuotaData virtualStorageQuota) {
		this.resourceGroupId = resourceGroupId;
		this.virtualStorageQuota = virtualStorageQuota;
	}



	/**
	 * @return the resourceGroupId
	 */
	public String getResourceGroupId() {
		return resourceGroupId;
	}



	/**
	 * @return the virtualStorageQuota
	 */
	public VirtualStorageQuotaData getVirtualStorageQuota() {
		return virtualStorageQuota;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceGroupId == null) throw new MalformattedElementException("Update storage quota request without resource group ID.");
		if (virtualStorageQuota == null) throw new MalformattedElementException("Update storage quota request without quota description.");
		else virtualStorageQuota.isValid();
	}

}
