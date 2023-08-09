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
 * REF IFA 005 v2.3.1 - 8.11.2.3
 * 
 * @author nextworks
 *
 */
public class VirtualComputeQuota implements InterfaceInformationElement {
	
	private String resourceGroupId;
	private int numVCPUs;
	private int numVcInstances;
	private int virtualMemSize;
	

	public VirtualComputeQuota() {	}

	
	/**
	 * Constructor
	 * 
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
	 * @param numVCPUs Number of CPU cores that have been restricted by the quota.
	 * @param numVcInstances Number of virtualisation container instances that have been restricted by the quota.
	 * @param virtualMemSize Size of virtual memory that has been restricted by the quota.
	 */
	public VirtualComputeQuota(String resourceGroupId, int numVCPUs, int numVcInstances, int virtualMemSize) {
		this.resourceGroupId = resourceGroupId;
		this.numVCPUs = numVCPUs;
		this.numVcInstances = numVcInstances;
		this.virtualMemSize = virtualMemSize;
	}



	/**
	 * @return the resourceGroupId
	 */
	public String getResourceGroupId() {
		return resourceGroupId;
	}



	/**
	 * @return the numVCPUs
	 */
	public int getNumVCPUs() {
		return numVCPUs;
	}



	/**
	 * @return the numVcInstances
	 */
	public int getNumVcInstances() {
		return numVcInstances;
	}



	/**
	 * @return the virtualMemSize
	 */
	public int getVirtualMemSize() {
		return virtualMemSize;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceGroupId == null) throw new MalformattedElementException("Virtual compute quota without resource group ID."); 
	}

}
