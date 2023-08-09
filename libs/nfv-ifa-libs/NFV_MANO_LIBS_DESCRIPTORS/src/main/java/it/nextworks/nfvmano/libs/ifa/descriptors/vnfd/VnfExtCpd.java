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
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.3.2
 * 
 * @author nextworks
 *
 */
@Entity
@DiscriminatorValue("VNFEXTCPD")
public class VnfExtCpd extends Cpd {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Vnfd vnfd;
	
	private String intVirtualLinkDesc;
	private String intCpd;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements = new ArrayList<>();
	
	public VnfExtCpd() {
		// JPA only
	}

	/**
	 * Constructor
	 * 
	 * @param vnfd VNFD this external connection point belongs to
	 * @param cpdId ID of the external connection point
	 * @param layerProtocol layer protocol of the connection point
	 * @param cpRole role of the connection protocol
	 * @param description human readable description of the connection point
	 * @param addressData Provides information on the addresses to be assigned to the CP(s) instantiated from this CPD
	 * @param intVirtualLinkDesc Reference to the internal Virtual Link Descriptor (VLD) to which CPs instantiated from this external CP Descriptor (CPD) connect
	 * @param intCpd Reference to the internal VDU CPD which is used to instantiate internal CPs. These internal CPs are, in turn, exposed as external CPs defined by this external CPD.
	 * @param virtualNetworkInterfaceRequirements Specifies requirements on a virtual network interface realising the CPs instantiated from this CPD.
	 */
	public VnfExtCpd(Vnfd vnfd, String cpdId, LayerProtocol layerProtocol, CpRole cpRole, String description, List<AddressData> addressData,
			String intVirtualLinkDesc, String intCpd, List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements) {
		super(cpdId, layerProtocol, cpRole, description, addressData);
		this.vnfd = vnfd;
		this.intCpd = intCpd;
		this.intVirtualLinkDesc = intVirtualLinkDesc;
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
	 * @return the intCpd
	 */
	@JsonProperty("intCpd")
	public String getIntCpd() {
		return intCpd;
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
		if ((intVirtualLinkDesc == null) && (intCpd == null)) throw new MalformattedElementException("VNF external connection point without reference to internal VLD ID or CPD ID");
		for (VirtualNetworkInterfaceRequirements r : virtualNetworkInterfaceRequirements) r.isValid();
	}

}
