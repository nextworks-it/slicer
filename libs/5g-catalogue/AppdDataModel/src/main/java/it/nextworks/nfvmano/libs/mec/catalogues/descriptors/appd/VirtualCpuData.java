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
 * The VirtualCpuData information element supports the specification 
 * of requirements related to virtual CPU(s) of a virtual compute resource.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.9.2.3
 * REF IFA 005 v2.3.1 - 8.4.2.3
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VirtualCpuData implements DescriptorInformationElement {

	private String cpuArchitecture;
	private int numVirtualCpu;
	private int virtualCpuClock;
	
	private String virtualCpuOversubscriptionPolicy;
	
	//TODO: add CPU pinning and requirements
	
	public VirtualCpuData() {}
	
	/**
	 * Constructor
	 * 
	 * @param cpuArchitecture CPU architecture type.
	 * @param numVirtualCpu Number of virtual CPUs.
	 * @param virtualCpuClock Minimum virtual CPU clock rate (e.g. in MHz).
	 * @param virtualCpuOversubscriptionPolicy The CPU core oversubscription policy e.g. the relation of virtual CPU cores to physical CPU cores/threads.
	 */
	public VirtualCpuData(String cpuArchitecture,
			int numVirtualCpu,
			int virtualCpuClock,
			String virtualCpuOversubscriptionPolicy) {
		this.cpuArchitecture = cpuArchitecture;
		this.numVirtualCpu = numVirtualCpu;
		this.virtualCpuClock = virtualCpuClock;
		this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
	}
	
	
	
	/**
	 * @return the cpuArchitecture
	 */
	@JsonProperty("cpuArchitecture")
	public String getCpuArchitecture() {
		return cpuArchitecture;
	}

	/**
	 * @return the numVirtualCpu
	 */
	@JsonProperty("numVirtualCpu")
	public int getNumVirtualCpu() {
		return numVirtualCpu;
	}

	/**
	 * @return the virtualCpuClock
	 */
	@JsonProperty("virtualCpuClock")
	public int getVirtualCpuClock() {
		return virtualCpuClock;
	}
	
	/**
	 * @return the virtualCpuOversubscriptionPolicy
	 */
	@JsonProperty("virtualCpuOversubscriptionPolicy")
	public String getVirtualCpuOversubscriptionPolicy() {
		return virtualCpuOversubscriptionPolicy;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if(numVirtualCpu <= 0) throw new MalformattedElementException("Virtual Cpu Data with a not valid numVirtualCpu");
	}

}
