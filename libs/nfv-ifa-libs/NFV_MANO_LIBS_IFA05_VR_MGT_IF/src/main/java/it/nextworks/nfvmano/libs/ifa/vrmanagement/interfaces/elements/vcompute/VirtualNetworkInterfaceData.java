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

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * A virtual network interface is a communication endpoint under a compute resource.
 * 
 * REF IFA 005 v2.3.1 - 8.4.2.6
 * 
 * @author nextworks
 *
 */
@Entity
public class VirtualNetworkInterfaceData implements InterfaceInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private VirtualComputeFlavour vcf;
	
	private String networkId;
	private String networkPortId;
	private String typeVirtualNic;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> typeConfiguration = new ArrayList<>();
	
	private String macAddress; 
	private int bandwidth;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> accelerationCapability = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> metadata = new HashMap<>();
	
	public VirtualNetworkInterfaceData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vcf Virtual Compute Flavour this virtual network interface data belongs to
	 * @param networkId In the case when the virtual network interface is attached to the network, it identifies such a network.
	 * @param networkPortId If the virtual network interface is attached to a specific network port, it identifies such a network port.
	 * @param typeVirtualNic Type of network interface. 
	 * @param typeConfiguration Extra configuration that the virtual network interface supports based on the type of virtual network interface, including support for SR-IOV with configuration of virtual functions (VF).
	 * @param macAddress The MAC address desired for the virtual network interface.
	 * @param bandwidth The bandwidth of the virtual network interface (in Mbps).
	 * @param accelerationCapability It specifies if the virtual network interface requires certain acceleration capabilities
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualNetworkInterfaceData(VirtualComputeFlavour vcf,
			String networkId,
			String networkPortId,
			String typeVirtualNic,
			List<String> typeConfiguration,
			String macAddress,
			int bandwidth,
			List<String> accelerationCapability,
			Map<String, String> metadata) {
		this.vcf = vcf;
		this.networkId = networkId;
		this.networkPortId = networkPortId;
		this.typeVirtualNic = typeVirtualNic;
		if (typeConfiguration != null) this.typeConfiguration = typeConfiguration;
		this.macAddress = macAddress;
		if (accelerationCapability != null) this.accelerationCapability = accelerationCapability;
		if (metadata != null) this.metadata = metadata;
	}
	
	/**
	 * Constructor
	 * 
	 * @param networkId In the case when the virtual network interface is attached to the network, it identifies such a network.
	 * @param networkPortId If the virtual network interface is attached to a specific network port, it identifies such a network port.
	 * @param typeVirtualNic Type of network interface. 
	 * @param typeConfiguration Extra configuration that the virtual network interface supports based on the type of virtual network interface, including support for SR-IOV with configuration of virtual functions (VF).
	 * @param macAddress The MAC address desired for the virtual network interface.
	 * @param bandwidth The bandwidth of the virtual network interface (in Mbps).
	 * @param accelerationCapability It specifies if the virtual network interface requires certain acceleration capabilities
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualNetworkInterfaceData(String networkId,
			String networkPortId,
			String typeVirtualNic,
			List<String> typeConfiguration,
			String macAddress,
			int bandwidth,
			List<String> accelerationCapability,
			Map<String, String> metadata) {	
		this.networkId = networkId;
		this.networkPortId = networkPortId;
		this.typeVirtualNic = typeVirtualNic;
		if (typeConfiguration != null) this.typeConfiguration = typeConfiguration;
		this.macAddress = macAddress;
		if (accelerationCapability != null) this.accelerationCapability = accelerationCapability;
		if (metadata != null) this.metadata = metadata;
	}

	
	
	/**
	 * @return the networkId
	 */
	public String getNetworkId() {
		return networkId;
	}

	/**
	 * @return the networkPortId
	 */
	public String getNetworkPortId() {
		return networkPortId;
	}

	/**
	 * @return the typeVirtualNic
	 */
	public String getTypeVirtualNic() {
		return typeVirtualNic;
	}

	/**
	 * @return the typeConfiguration
	 */
	public List<String> getTypeConfiguration() {
		return typeConfiguration;
	}

	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @return the bandwidth
	 */
	public int getBandwidth() {
		return bandwidth;
	}

	/**
	 * @return the accelerationCapability
	 */
	public List<String> getAccelerationCapability() {
		return accelerationCapability;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	@Override
	public void isValid() throws MalformattedElementException { }

}
