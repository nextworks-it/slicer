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

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.VirtualCpuData;
import it.nextworks.nfvmano.libs.ifa.common.elements.VirtualMemoryData;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vstorage.VirtualStorageData;

/**
 * The VirtualComputeFlavour information element encapsulates information 
 * for compute flavours. A compute flavour includes information about number 
 * of virtual CPUs, size of virtual memory, size of virtual storage, and virtual network 
 * interfaces. The NetworkInterfaceType information element encapsulates information 
 * of a virtual network interface for a compute resource.
 * 
 * REF IFA 005 v2.3.1 - 8.4.2.2
 * 
 * @author nextworks
 *
 */
@Entity
public class VirtualComputeFlavour implements InterfaceInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String flavourId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> accelerationCapability;
	
	@Embedded
	private VirtualMemoryData virtualMemory;
	
	@Embedded
	private VirtualCpuData virtualCpu;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VirtualStorageData> storageAttributes = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "vcf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VirtualNetworkInterfaceData> virtualNetworkInterface = new ArrayList<>();
	
	public VirtualComputeFlavour() { }
	
	/**
	 * Constructor
	 * 
	 * @param flavourId Identifier given to the compute flavour.
	 * @param accelerationCapability Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources.
	 * @param virtualMemory The virtual memory of the virtualised compute.
	 * @param virtualCpu The virtual CPU(s) of the virtualised compute.
	 * @param storageAttributes Element containing information about the size of virtualised storage resource (e.g. size of volume, in GB), the type of storage (e.g. volume, object), and support for RDMA.
	 * @param virtualNetworkInterface The virtual network interfaces of the virtualised compute.
	 */
	public VirtualComputeFlavour(String flavourId,
			List<String> accelerationCapability,
			VirtualMemoryData virtualMemory,
			VirtualCpuData virtualCpu,
			List<VirtualStorageData> storageAttributes,
			List<VirtualNetworkInterfaceData> virtualNetworkInterface) { 
		this.flavourId = flavourId;
		if (accelerationCapability != null) this.accelerationCapability = accelerationCapability;
		this.virtualCpu = virtualCpu;
		this.virtualMemory = virtualMemory;
		if (storageAttributes != null) this.storageAttributes = storageAttributes;
		if (virtualNetworkInterface != null) this.virtualNetworkInterface = virtualNetworkInterface;
	}

	/**
	 * Constructor
	 * 
	 * @param flavourId Identifier given to the compute flavour.
	 * @param accelerationCapability Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources.
	 * @param virtualMemory The virtual memory of the virtualised compute.
	 * @param virtualCpu The virtual CPU(s) of the virtualised compute.
	 * @param storageAttributes Element containing information about the size of virtualised storage resource (e.g. size of volume, in GB), the type of storage (e.g. volume, object), and support for RDMA.
	 */
	public VirtualComputeFlavour(String flavourId,
			List<String> accelerationCapability,
			VirtualMemoryData virtualMemory,
			VirtualCpuData virtualCpu,
			List<VirtualStorageData> storageAttributes) { 
		this.flavourId = flavourId;
		if (accelerationCapability != null) this.accelerationCapability = accelerationCapability;
		this.virtualCpu = virtualCpu;
		this.virtualMemory = virtualMemory;
		if (storageAttributes != null) this.storageAttributes = storageAttributes;
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
	public VirtualMemoryData getVirtualMemory() {
		return virtualMemory;
	}

	/**
	 * @return the virtualCpu
	 */
	public VirtualCpuData getVirtualCpu() {
		return virtualCpu;
	}

	/**
	 * @return the storageAttributes
	 */
	public List<VirtualStorageData> getStorageAttributes() {
		return storageAttributes;
	}

	/**
	 * @return the virtualNetworkInterface
	 */
	public List<VirtualNetworkInterfaceData> getVirtualNetworkInterface() {
		return virtualNetworkInterface;
	}
	
	

	/**
	 * @param flavourId the flavourId to set
	 */
	public void setFlavourId(String flavourId) {
		this.flavourId = flavourId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (flavourId == null) throw new MalformattedElementException("Virtual compute flavour without flavour ID");
		if (virtualMemory == null) throw new MalformattedElementException("Virtual compute flavour without virtual memory");
		if (virtualCpu == null) throw new MalformattedElementException("Virtual compute flavour without virtual CPU");
		for (VirtualStorageData vsd : storageAttributes) vsd.isValid();
		for (VirtualNetworkInterfaceData vnic : virtualNetworkInterface) vnic.isValid();
	}

}
