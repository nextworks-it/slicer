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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.quotas;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * REF IFA 005 v2.3.1 - 8.11.4.3
 * 
 * @author nextworks
 *
 */
public class VirtualStorageQuota implements InterfaceInformationElement {

	private String resourceGroupId;
	private int storageSize;
	private int numSnapshots;
	private int numVolumes;
	
	public VirtualStorageQuota() {	}

	
	
	/**
	 * Constructor
	 * 
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
	 * @param storageSize Size of virtualised storage resource that has been restricted by the quota.
	 * @param numSnapshots Number of snapshots that have been restricted by the quota.
	 * @param numVolumes Number of volumes that have been restricted by the quota.
	 */
	public VirtualStorageQuota(String resourceGroupId, int storageSize, int numSnapshots, int numVolumes) {
		this.resourceGroupId = resourceGroupId;
		this.storageSize = storageSize;
		this.numSnapshots = numSnapshots;
		this.numVolumes = numVolumes;
	}



	/**
	 * @return the resourceGroupId
	 */
	public String getResourceGroupId() {
		return resourceGroupId;
	}



	/**
	 * @return the storageSize
	 */
	public int getStorageSize() {
		return storageSize;
	}



	/**
	 * @return the numSnapshots
	 */
	public int getNumSnapshots() {
		return numSnapshots;
	}



	/**
	 * @return the numVolumes
	 */
	public int getNumVolumes() {
		return numVolumes;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceGroupId == null) throw new MalformattedElementException("Virtual storage quota without resource group ID.");
	}

}
