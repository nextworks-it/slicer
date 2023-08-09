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

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VirtualCpuResourceInformation defines the virtual CPU(s) characteristics 
 * of consumable virtualised compute resource.
 * 
 * 
 * REF IFA 005 v2.1.1 - 8.3.3.3
 * 
 * 
 * @author nextworks
 *
 */
public class VirtualCpuResourceInformation implements InterfaceInformationElement {

	private String cpuArchitecture;
	private int numVirtualCpu;
	private int virtualCpuClock;
	private String virtualCpuOversubscriptionPolicy;
	private boolean virtualCpuPinningSupported;
	
	public VirtualCpuResourceInformation() {}
	
	/**
	 * Constructor
	 * 
	 * @param cpuArchitecture CPU architecture type.
	 * @param numVirtualCpu Number of virtual CPUs.
	 * @param virtualCpuClock Minimum virtual CPU clock rate in MHz
	 * @param virtualCpuOversubscriptionPolicy The CPU core oversubscription policy, e.g. the relation of virtual CPU cores to physical CPU cores/threads.
	 * @param virtualCpuPinningSupported It defines whether CPU pinning capability is available on the consumable virtualised compute resource.
	 */
	public VirtualCpuResourceInformation(String cpuArchitecture,
			int numVirtualCpu,
			int virtualCpuClock,
			String virtualCpuOversubscriptionPolicy,
			boolean virtualCpuPinningSupported) {
		this.cpuArchitecture = cpuArchitecture;
		this.numVirtualCpu = numVirtualCpu;
		this.virtualCpuClock = virtualCpuClock;
		this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
		this.virtualCpuPinningSupported = virtualCpuPinningSupported;
	}

	
	
	/**
	 * @return the cpuArchitecture
	 */
	public String getCpuArchitecture() {
		return cpuArchitecture;
	}

	/**
	 * @return the numVirtualCpu
	 */
	public int getNumVirtualCpu() {
		return numVirtualCpu;
	}

	/**
	 * @return the virtualCpuClock
	 */
	@JsonProperty("cpuClock")
	public int getVirtualCpuClock() {
		return virtualCpuClock;
	}

	/**
	 * @return the virtualCpuOversubscriptionPolicy
	 */
	public String getVirtualCpuOversubscriptionPolicy() {
		return virtualCpuOversubscriptionPolicy;
	}

	/**
	 * @return the virtualCpuPinningSupported
	 */
	public boolean isVirtualCpuPinningSupported() {
		return virtualCpuPinningSupported;
	}

	@Override
	public void isValid() throws MalformattedElementException { }

}
