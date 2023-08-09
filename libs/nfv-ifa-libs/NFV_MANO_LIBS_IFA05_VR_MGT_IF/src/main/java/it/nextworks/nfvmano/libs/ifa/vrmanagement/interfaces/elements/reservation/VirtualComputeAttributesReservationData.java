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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.reservation;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This clause describes the attributes for the VirtualComputeAttributesReservationData information element.
 * 
 * REF IFA 005 v2.3.1 - 8.8.3.4
 * 
 * @author nextworks
 *
 */
public class VirtualComputeAttributesReservationData implements InterfaceInformationElement {

	private List<String> accelerationCapability = new ArrayList<>();
	private String cpuArchitecture;
	private String virtualCpuOversubscriptionPolicy;
	
	public VirtualComputeAttributesReservationData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param accelerationCapability Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources
	 * @param cpuArchitecture CPU architecture type.
	 * @param virtualCpuOversubscriptionPolicy The CPU core oversubscription policy in terms of virtual CPU cores to physical CPU cores/threads on the platform.
	 */
	public VirtualComputeAttributesReservationData(List<String> accelerationCapability,
			String cpuArchitecture,
			String virtualCpuOversubscriptionPolicy) {
		if (accelerationCapability != null) this.accelerationCapability = accelerationCapability;
		this.cpuArchitecture = cpuArchitecture;
		this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
	}
	
	

	/**
	 * @return the accelerationCapability
	 */
	public List<String> getAccelerationCapability() {
		return accelerationCapability;
	}

	/**
	 * @return the cpuArchitecture
	 */
	public String getCpuArchitecture() {
		return cpuArchitecture;
	}

	/**
	 * @return the virtualCpuOversubscriptionPolicy
	 */
	public String getVirtualCpuOversubscriptionPolicy() {
		return virtualCpuOversubscriptionPolicy;
	}

	@Override
	public void isValid() throws MalformattedElementException {

	}

}
