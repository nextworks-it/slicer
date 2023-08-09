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


import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This clause describes the attributes for the ReservedComputePool information element.
 * 
 * REF IFA 005 v2.1.1 - 8.8.3.3
 * 
 * @author nextworks
 *
 */
public class ReservedComputePool implements InterfaceInformationElement {

	private int numCpuCores;
	private int numVcInstances;
	private int virtualMemSize;
	private VirtualComputeAttributesReservationData computeAttributes;
	private String zoneId;
	
	public ReservedComputePool() {	}
	
	/**
	 * Constructor
	 * 
	 * 
	 * @param numCpuCores Number of CPU cores that have been reserved.
	 * @param numVcInstances Number of virtual container instances that have been reserved.
	 * @param virtualMemSize Size of virtual memory that has been reserved.
	 * @param computeAttributes Information specifying additional attributes of the virtual compute resource that have been reserved.
	 * @param zoneId References the resource zone where the virtual compute resources have been reserved.
	 */
	public ReservedComputePool(int numCpuCores,
			int numVcInstances,
			int virtualMemSize,
			VirtualComputeAttributesReservationData computeAttributes,
			String zoneId) {
		this.numCpuCores = numCpuCores;
		this.numVcInstances = numVcInstances;
		this.virtualMemSize = virtualMemSize;
		this.computeAttributes = computeAttributes;
		this.zoneId = zoneId;
	}

	
	
	/**
	 * @return the numCpuCores
	 */
	public int getNumCpuCores() {
		return numCpuCores;
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

	/**
	 * @return the computeAttributes
	 */
	public VirtualComputeAttributesReservationData getComputeAttributes() {
		return computeAttributes;
	}

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (computeAttributes != null) computeAttributes.isValid();
	}

}
