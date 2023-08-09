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

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element defines the characteristics of consumable virtualised compute resources.
 * 
 * REF IFA 005 v2.3.1 - 8.3.3.2
 * 
 * @author nextworks
 *
 */
public class VirtualComputeResourceInformation implements InterfaceInformationElement {

	private String computeResourceTypeId;
	private VirtualMemoryResourceInformation virtualMemory;
	private VirtualCpuResourceInformation virtualCPU;
	private List<String> accelerationCapability = new ArrayList<>();
	
	public VirtualComputeResourceInformation() { }
	
	/**
	 * Constructor
	 * 
	 * @param computeResourceTypeId Identifier of the consumable virtualised compute resource type.
	 * @param virtualMemory It defines the virtual memory characteristics of the consumable virtualised compute resource
	 * @param virtualCPU It defines the virtual CPU(s) characteristics of the consumable virtualised compute resource
	 * @param accelerationCapability Acceleration capabilities (e.g. crypto, GPU) for the consumable virtualised compute resources from the set of capabilities offered by the compute node acceleration resources.
	 */
	public VirtualComputeResourceInformation(String computeResourceTypeId,
			VirtualMemoryResourceInformation virtualMemory,
			VirtualCpuResourceInformation virtualCPU,
			List<String> accelerationCapability) { 
		this.computeResourceTypeId = computeResourceTypeId;
		this.virtualMemory = virtualMemory;
		this.virtualCPU = virtualCPU;
		if (accelerationCapability != null) this.accelerationCapability = accelerationCapability;
	}
	
	
	
	/**
	 * @return the computeResourceTypeId
	 */
	public String getComputeResourceTypeId() {
		return computeResourceTypeId;
	}

	/**
	 * @return the virtualMemory
	 */
	public VirtualMemoryResourceInformation getVirtualMemory() {
		return virtualMemory;
	}

	/**
	 * @return the virtualCPU
	 */
	public VirtualCpuResourceInformation getVirtualCPU() {
		return virtualCPU;
	}

	/**
	 * @return the accelerationCapability
	 */
	public List<String> getAccelerationCapability() {
		return accelerationCapability;
	}

	@Override
	public void isValid() throws MalformattedElementException {	
		if (computeResourceTypeId == null) throw new MalformattedElementException("Virtual compute resource information without type ID");
		if (virtualMemory != null) virtualMemory.isValid();
		if (virtualCPU != null) virtualCPU.isValid();
	}

}
