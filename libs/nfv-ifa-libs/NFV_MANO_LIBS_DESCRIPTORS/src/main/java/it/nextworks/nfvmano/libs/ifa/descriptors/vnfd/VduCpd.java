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
package it.nextworks.nfvmano.libs.ifa.descriptors.vnfd;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
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
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.enums.CpRole;
import it.nextworks.nfvmano.libs.ifa.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.AddressData;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.Cpd;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualNetworkInterfaceRequirements;

/**
 * A VduCpd information element is a type of Cpd and describes network 
 * connectivity between a VNFC instance (based on this VDU) and an internal VL.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.6.4
 * 
 * @author nextworks
 *
 */
@Entity
@DiscriminatorValue("VDUCPD")
public class VduCpd extends Cpd {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Vdu vdu;
	
	private String intVirtualLinkDesc;
	
	private int bitrateRequirement;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements = new ArrayList<>();
	
	public VduCpd() {
		// JPA only
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param vdu VDU this connection point belongs to
	 * @param cpdId ID of the connection point
	 * @param layerProtocol Layer protocol
	 * @param cpRole Role of the connection point
	 * @param description Description of the connection point
	 * @param addressData Address data associated to the connection point
	 * @param intVirtualLinkDesc Reference of the internal VLD which this internal CPD connects to.
	 * @param bitrateRequirement Bitrate requirement on this CP.
	 * @param virtualNetworkInterfaceRequirements Specifies requirements on a virtual network interface realising the CPs instantiated from this CPD.
	 */
	public VduCpd(Vdu vdu, 
			String cpdId, 
			LayerProtocol layerProtocol, 
			CpRole cpRole, 
			String description, 
			List<AddressData> addressData,
			String intVirtualLinkDesc, 
			int bitrateRequirement, 
			List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements) {
		super(cpdId, layerProtocol, cpRole, description, addressData);
		this.vdu = vdu;
		this.intVirtualLinkDesc = intVirtualLinkDesc;
		this.bitrateRequirement = bitrateRequirement;
		if (virtualNetworkInterfaceRequirements != null) this.virtualNetworkInterfaceRequirements = virtualNetworkInterfaceRequirements;
	}
	
	
	
	/**
	 * @return the intVirtualLinkDesc
	 */
	@JsonProperty("intVirtualLinkDesc")
	public String getIntVirtualLinkDesc() {
		return intVirtualLinkDesc;
	}


	/**
	 * @return the bitrateRequirement
	 */
	@JsonProperty("bitrateRequirement")
	public int getBitrateRequirement() {
		return bitrateRequirement;
	}


	/**
	 * @return the virtualNetworkInterfaceRequirements
	 */
	@JsonProperty("virtualNetworkInterfaceRequirements")
	public List<VirtualNetworkInterfaceRequirements> getVirtualNetworkInterfaceRequirements() {
		return virtualNetworkInterfaceRequirements;
	}


	@Override
	public void isValid() throws MalformattedElementException {
		super.isValid();
		for (VirtualNetworkInterfaceRequirements vnir : virtualNetworkInterfaceRequirements) vnir.isValid();
	}
		

}
