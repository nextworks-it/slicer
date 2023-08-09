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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

/**
 * The VirtualMemoryData information element supports 
 * the specification of requirements related to virtual 
 * memory of a virtual compute resource.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.9.3
 * REF IFA 005 v2.3.1 - 8.4.2.5
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VirtualMemoryData implements DescriptorInformationElement {

	private int virtualMemSize;
	private String virtualMemOversubscriptionPolicy;
	private boolean numaEnabled;
	
	public VirtualMemoryData() {}
	
	/**
	 * Constructor
	 * 
	 * @param virtualMemSize Amount of virtual Memory (e.g. in MB).
	 * @param virtualMemOversubscriptionPolicy The memory core oversubscription policy in terms of virtual memory to physical memory on the platform.
	 * @param numaEnabled It specifies the memory allocation to be cognisant of the relevant process/core allocation.
	 */
	public VirtualMemoryData(int virtualMemSize,
			String virtualMemOversubscriptionPolicy,
			boolean numaEnabled) {
		this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
		this.virtualMemSize = virtualMemSize;
		this.numaEnabled = numaEnabled;
	}
	
	
	/**
	 * @return the virtualMemSize
	 */
	@JsonProperty("virtualMemSize")
	public int getVirtualMemSize() {
		return virtualMemSize;
	}

	/**
	 * @return the virtualMemOversubscriptionPolicy
	 */
	@JsonProperty("virtualMemOversubscriptionPolicy")
	public String getVirtualMemOversubscriptionPolicy() {
		return virtualMemOversubscriptionPolicy;
	}

	/**
	 * @return the numaEnabled
	 */
	@JsonProperty("numaEnabled")
	public boolean isNumaEnabled() {
		return numaEnabled;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if(virtualMemSize <= 0) throw new MalformattedElementException("Virtual Memory Data with a not valid virtualMemSize");
	}

}
