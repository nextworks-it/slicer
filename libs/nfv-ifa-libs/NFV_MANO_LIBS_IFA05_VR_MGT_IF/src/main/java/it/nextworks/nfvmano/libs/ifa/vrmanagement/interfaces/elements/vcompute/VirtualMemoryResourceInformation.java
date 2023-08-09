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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute;


import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VirtualMemoryResourceInformation defines the virtual memory 
 * characteristics of consumable virtualised compute resource.
 * 
 * REF IFA 005 v2.3.1 - 8.3.3.4
 * 
 * @author nextworks
 *
 */
public class VirtualMemoryResourceInformation implements InterfaceInformationElement {

	private int virtualMemSize;
	private String virtualMemOversubscriptionPolicy;
	private boolean numaSupported;
	
	public VirtualMemoryResourceInformation() {	}
	
	/**
	 * Constructor
	 * 
	 * @param virtualMemSize Amount of virtual memory in MB
	 * @param virtualMemOversubscriptionPolicy The memory core oversubscription policy in terms of virtual memory to physical memory on the platform.
	 * @param numaSupported It specifies if the memory allocation can be cognisant of the relevant process/core allocation.
	 */
	public VirtualMemoryResourceInformation(int virtualMemSize,
			String virtualMemOversubscriptionPolicy,
			boolean numaSupported) {	
		this.virtualMemSize = virtualMemSize;
		this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
		this.numaSupported = numaSupported;
	}

	
	
	/**
	 * @return the virtualMemSize
	 */
	public int getVirtualMemSize() {
		return virtualMemSize;
	}

	/**
	 * @return the virtualMemOversubscriptionPolicy
	 */
	public String getVirtualMemOversubscriptionPolicy() {
		return virtualMemOversubscriptionPolicy;
	}

	/**
	 * @return the numaSupported
	 */
	public boolean isNumaSupported() {
		return numaSupported;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
