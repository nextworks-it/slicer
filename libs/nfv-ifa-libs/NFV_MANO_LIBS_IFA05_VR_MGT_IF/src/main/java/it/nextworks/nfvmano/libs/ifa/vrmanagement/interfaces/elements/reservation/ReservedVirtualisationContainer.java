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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualCpu;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualMemory;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualNetworkInterface;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vstorage.VirtualStorage;

/**
 * This clause describes the attributes for the ReservedVirtualisationContainer information element.
 * 
 * REF IFA 005 v2.1.1 - 8.8.5.3
 * 
 * @author nextworks
 *
 */
public class ReservedVirtualisationContainer implements InterfaceInformationElement {

	private String containerId;
	private String flavourId;
	private List<String> accelerationCapability = new ArrayList<>();
	private VirtualMemory virtualMemory;
	private VirtualCpu virtualCpu;
	private List<VirtualStorage> virtualDisks = new ArrayList<>();
	private List<VirtualNetworkInterface> virtualNetworkInterface = new ArrayList<>();
	private String zoneId;
	
	public ReservedVirtualisationContainer() {	}
	
	/**
	 * Constructor
	 * 
	 * @param containerId The identifier of the virtualisation container that has been reserved.
	 * @param flavourId Identifier of the given compute flavour used to reserve the virtualisation container.
	 * @param accelerationCapability Selected acceleration capabilities
	 * @param virtualMemory The virtual memory of the reserved virtualisation container.
	 * @param virtualCpu The virtual CPU(s) of the reserved virtualisation container.
	 * @param virtualDisks Element with information of the virtualised storage resources attached to the reserved virtualisation container.
	 * @param virtualNetworkInterface Element with information of the virtual network interfaces of the reserved virtualisation container.
	 * @param zoneId References the resource zone where the virtualisation container has been reserved.
	 */
	public ReservedVirtualisationContainer(String containerId,
			String flavourId,
			List<String> accelerationCapability,
			VirtualMemory virtualMemory,
			VirtualCpu virtualCpu,
			List<VirtualStorage> virtualDisks,
			List<VirtualNetworkInterface> virtualNetworkInterface,
			String zoneId) {	
		this.containerId = containerId;
		this.flavourId = flavourId;
		if (accelerationCapability != null) this.accelerationCapability = accelerationCapability;
		this.virtualMemory = virtualMemory;
		this.virtualCpu = virtualCpu;
		if (virtualDisks != null) this.virtualDisks = virtualDisks;
		if (virtualNetworkInterface != null) this.virtualNetworkInterface = virtualNetworkInterface;
		this.zoneId = zoneId;
	}

	
	
	/**
	 * @return the containerId
	 */
	public String getContainerId() {
		return containerId;
	}

	/**
	 * @return the flavourId
	 */
	public String getFlavourId() {
		return flavourId;
	}

	/**
	 * @return the accelerationCapability
	 */
	public List<String> getAccelerationCapability() {
		return accelerationCapability;
	}

	/**
	 * @return the virtualMemory
	 */
	public VirtualMemory getVirtualMemory() {
		return virtualMemory;
	}

	/**
	 * @return the virtualCpu
	 */
	public VirtualCpu getVirtualCpu() {
		return virtualCpu;
	}

	/**
	 * @return the virtualDisks
	 */
	public List<VirtualStorage> getVirtualDisks() {
		return virtualDisks;
	}

	/**
	 * @return the virtualNetworkInterface
	 */
	public List<VirtualNetworkInterface> getVirtualNetworkInterface() {
		return virtualNetworkInterface;
	}

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (containerId == null) throw new MalformattedElementException("Reserved virtualization contained without ID");
		if (flavourId == null) throw new MalformattedElementException("Reserved virtualization contained without flavour ID");
		if (virtualMemory == null) throw new MalformattedElementException("Reserved virtualization contained without virtual memory");
		else virtualMemory.isValid();
		if (virtualCpu == null) throw new MalformattedElementException("Reserved virtualization contained without virtual CPU");
		else virtualCpu.isValid();
		if ((virtualDisks == null) || (virtualDisks.isEmpty())) throw new MalformattedElementException("Reserved virtualization contained without virtual disks");
		else for (VirtualStorage vd : virtualDisks) vd.isValid();
		for (VirtualNetworkInterface vni : virtualNetworkInterface) vni.isValid();
	}

}
