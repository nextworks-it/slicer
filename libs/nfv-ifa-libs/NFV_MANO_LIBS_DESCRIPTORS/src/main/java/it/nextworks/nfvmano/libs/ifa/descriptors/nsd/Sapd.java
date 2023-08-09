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
package it.nextworks.nfvmano.libs.ifa.descriptors.nsd;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.enums.CpRole;
import it.nextworks.nfvmano.libs.ifa.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.AddressData;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.Cpd;

/**
 * The SAPD information element specifies the information used to instantiate the service access points of an NS.
 * Ref. IFA 014 v2.3.1 - 6.2.3
 * 
 * @author nextworks
 *
 */
@Entity
@DiscriminatorValue("SAPD")
public class Sapd extends Cpd {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Nsd nsd;
	
	private boolean sapAddressAssignment;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nsVirtualLinkDescId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String associatedCpdId;
	
	public Sapd() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsd Network Service Descriptor this SAPD belongs to.
	 * @param cpdId Identifier of this Cpd information element.
	 * @param layerProtocol Identifies a protocol that the connection points corresponding to the CPD support for connectivity purposes (e.g. Ethernet, MPLS, ODU2, IPV4, IPV6, Pseudo-Wire, etc.).
	 * @param cpRole Identifies the role of the connection points corresponding to the CPD in the context of the traffic flow patterns in the VNF, PNF or NS.
	 * @param description Human-readable information on the purpose of the connection point
	 * @param addressData Provides information on the addresses to be assigned to the CP(s) instantiated from this CPD
	 * @param sapAddressAssignment Specify whether the SAP address assignment is under the responsibility of management and orchestration functions or not. If it is set to True, management and orchestration functions are responsible for assigning addresses to the access points instantiated from this SAPD. 
	 * @param nsVirtualLinkDescId References the descriptor of the NS VL instance to which the SAP instantiated from this SAPD connects to
	 * @param associatedCpdId References the descriptor of VNF or PNF external connection points the SAPs instantiated from this SAPD are mapped to.
	 */
	public Sapd(Nsd nsd,
			String cpdId,
			LayerProtocol layerProtocol,
			CpRole cpRole,
			String description,
			List<AddressData> addressData,
			boolean sapAddressAssignment,
			String nsVirtualLinkDescId,
			String associatedCpdId) {
		super(cpdId, layerProtocol, cpRole, description, addressData);
		this.sapAddressAssignment = sapAddressAssignment;
		this.nsVirtualLinkDescId = nsVirtualLinkDescId;
		this.associatedCpdId = associatedCpdId;
		this.nsd = nsd;
	}
	
	

	/**
	 * @return the sapAddressAssignment
	 */
	@JsonProperty("sapAddressAssignment")
	public boolean isSapAddressAssignment() {
		return sapAddressAssignment;
	}

	/**
	 * @return the nsVirtualLinkDescId
	 */
	@JsonProperty("nsVirtualLinkDescId")
	public String getNsVirtualLinkDescId() {
		return nsVirtualLinkDescId;
	}

	/**
	 * @return the associatedCpdId
	 */
	@JsonProperty("associatedCpdId")
	public String getAssociatedCpdId() {
		return associatedCpdId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		super.isValid();
		if ((this.associatedCpdId == null) && (this.nsVirtualLinkDescId == null)) throw new MalformattedElementException("Sapd without associated CPD ID and without NS VLD ID"); 
	}

	public void setNsd(Nsd nsd) {
		this.nsd = nsd;
	}

	public void setSapAddressAssignment(boolean sapAddressAssignment) {
		this.sapAddressAssignment = sapAddressAssignment;
	}

	public void setNsVirtualLinkDescId(String nsVirtualLinkDescId) {
		this.nsVirtualLinkDescId = nsVirtualLinkDescId;
	}

	public void setAssociatedCpdId(String associatedCpdId) {
		this.associatedCpdId = associatedCpdId;
	}
}
