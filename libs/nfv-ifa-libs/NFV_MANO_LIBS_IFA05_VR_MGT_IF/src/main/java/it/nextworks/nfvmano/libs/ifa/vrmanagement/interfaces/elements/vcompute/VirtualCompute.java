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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vstorage.VirtualStorage;


/**
 * 
 * 
 * REF IFA 005 v2.3.1 - 8.4.3.2
 * 
 * @author nextworks
 *
 */
public class VirtualCompute implements InterfaceInformationElement {

	private String computeId;
	private String computeName;
	private String flavourId;
	private List<String> accelerationCapability = new ArrayList<>();
	private VirtualCpu virtualCpu;
	private VirtualMemory virtualMemory;
	private List<VirtualNetworkInterface> virtualNetworkInterface = new ArrayList<>();
	private List<VirtualStorage> virtualDisks = new ArrayList<>();
	private String vcImageId;
	private String zoneId;
	private String hostId;
	private OperationalState operationalState;
	private Map<String, String> metadata = new HashMap<>();
	
	public VirtualCompute() { }
	
	/**
	 * Constructor
	 * 
	 * @param computeId Identifier of the virtualised compute resource.
	 * @param computeName Name of the virtualised compute resource.
	 * @param flavourId Identifier of the given compute flavour used to instantiate this virtual compute.
	 * @param accelerationCapability Selected acceleration capabilities from the set of capabilities offered by the compute node acceleration resources.
	 * @param virtualCpu The virtual CPU(s) of the virtualised compute.
	 * @param virtualMemory The virtual memory of the compute.
	 * @param virtualNetworkInterface Element with information of the instantiated virtual network interfaces of the compute resource.
	 * @param virtualDisks Element with information of the virtualised storage resources (volumes, ephemeral that are attached to the compute resource.
	 * @param vcImageId dentifier of the virtualisation container software image
	 * @param zoneId If present, it identifies the Resource Zone where the virtual compute resources have been allocated.
	 * @param hostId Identifier of the host the virtualised compute resource is allocated on.
	 * @param operationalState Operational state of the compute resource.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualCompute(String computeId,
			String computeName,
			String flavourId,
			List<String> accelerationCapability,
			VirtualCpu virtualCpu,
			VirtualMemory virtualMemory,
			List<VirtualNetworkInterface> virtualNetworkInterface,
			List<VirtualStorage> virtualDisks,
			String vcImageId,
			String zoneId,
			String hostId,
			OperationalState operationalState,
			Map<String, String> metadata) {
		this.computeId = computeId;
		this.computeName = computeName;
		this.flavourId = flavourId;
		if (accelerationCapability != null) this.accelerationCapability = accelerationCapability;
		this.virtualCpu = virtualCpu;
		this.virtualMemory = virtualMemory;
		if (virtualNetworkInterface != null) this.virtualNetworkInterface = virtualNetworkInterface;
		if (virtualDisks != null) this.virtualDisks = virtualDisks;
		this.vcImageId = vcImageId;
		this.zoneId = zoneId;
		this.hostId = hostId;
		this.operationalState = operationalState;
		if (metadata != null) this.metadata = metadata;
	}

	
	
	/**
	 * @return the computeId
	 */
	public String getComputeId() {
		return computeId;
	}

	/**
	 * @return the computeName
	 */
	public String getComputeName() {
		return computeName;
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
	 * @return the virtualCpu
	 */
	public VirtualCpu getVirtualCpu() {
		return virtualCpu;
	}

	/**
	 * @return the virtualMemory
	 */
	public VirtualMemory getVirtualMemory() {
		return virtualMemory;
	}

	/**
	 * @return the virtualNetworkInterface
	 */
	public List<VirtualNetworkInterface> getVirtualNetworkInterface() {
		return virtualNetworkInterface;
	}

	/**
	 * @return the virtualDisks
	 */
	public List<VirtualStorage> getVirtualDisks() {
		return virtualDisks;
	}

	/**
	 * @return the vcImageId
	 */
	public String getVcImageId() {
		return vcImageId;
	}

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}

	/**
	 * @return the operationalState
	 */
	public OperationalState getOperationalState() {
		return operationalState;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}
	
	@JsonIgnore
	public VirtualNetworkInterface getVirtualNetworkInterface(String portId) throws NotExistingEntityException {
		for (VirtualNetworkInterface vnic : virtualNetworkInterface) {
			if (vnic.getNetworkPortId().equals(portId)) return vnic;
		}
		throw new NotExistingEntityException("VNIC for port " + portId + " not found in virtual compute " + computeId);
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (computeId == null) throw new MalformattedElementException("Virtual Compute without compute ID");
		if (flavourId == null) throw new MalformattedElementException("Virtual Compute without flavour ID");
		if (virtualCpu == null) throw new MalformattedElementException("Virtual Compute without virtual CPU");
		else virtualCpu.isValid();
		if (virtualMemory == null) throw new MalformattedElementException("Virtual Compute without virtual memory");
		else virtualMemory.isValid();
		for (VirtualNetworkInterface vif : virtualNetworkInterface) vif.isValid();
		if ((virtualDisks == null) || (virtualDisks.isEmpty()))
			throw new MalformattedElementException("Virtual Compute without virtual disks");
		else for (VirtualStorage vs : virtualDisks) vs.isValid();
		if (hostId == null) throw new MalformattedElementException("Virtual Compute without host ID");
	}

}
