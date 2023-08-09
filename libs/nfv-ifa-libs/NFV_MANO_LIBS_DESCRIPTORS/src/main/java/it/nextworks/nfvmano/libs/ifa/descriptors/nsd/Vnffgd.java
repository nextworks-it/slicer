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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The Vnffgd information element specifies a topology of connectivity 
 * of a NS and optionally forwarding rules applicable to the traffic 
 * conveyed over this topology.
 * 
 * Ref. IFA 014 v2.3.1 - 6.4.2
 * 
 * @author nextworks
 *
 */
@Entity
public class Vnffgd implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Nsd nsd;
	
	private String vnffgdId;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> vnfdId = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> pnfdId = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> virtualLinkDescId = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> cpdPoolId = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "fg", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Nfpd> nfpd = new ArrayList<>();
	
	public Vnffgd() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsd NSD this VNFFGD belongs to 
	 * @param vnffgdId Identifier of this Vnffgd information element. It uniquely identifies a VNFFGD.
	 * @param vnfdId References the VNFD of a constituent VNF.
	 * @param pnfdId References the PNFD of a constituent PNF.
	 * @param virtualLinkDescId References the VLD of a constituent VL.
	 * @param cpdPoolId A reference to a pool of descriptors of connection points attached to one of the constituent VNFs and PNFs and/or one of the SAPs of the parent NS or of a nested NS.
	 * @param nfpd The network forwarding path associated to the VNFFG.
	 */
	public Vnffgd(Nsd nsd,
			String vnffgdId,
			List<String> vnfdId,
			List<String> pnfdId,
			List<String> virtualLinkDescId,
			List<String> cpdPoolId,
			List<Nfpd> nfpd) {
		this.nsd = nsd;
		this.vnffgdId = vnffgdId;
		if (vnfdId != null) this.vnfdId = vnfdId;
		if (pnfdId != null) this.pnfdId = pnfdId;
		if (virtualLinkDescId != null) this.virtualLinkDescId = virtualLinkDescId;
		if (cpdPoolId != null) this.cpdPoolId = cpdPoolId;
		if (nfpd != null) this.nfpd = nfpd;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsd NSD this VNFFGD belongs to 
	 * @param vnffgdId Identifier of this Vnffgd information element. It uniquely identifies a VNFFGD.
	 * @param vnfdId References the VNFD of a constituent VNF.
	 * @param pnfdId References the PNFD of a constituent PNF.
	 * @param virtualLinkDescId References the VLD of a constituent VL.
	 * @param cpdPoolId A reference to a pool of descriptors of connection points attached to one of the constituent VNFs and PNFs and/or one of the SAPs of the parent NS or of a nested NS.
	 */
	public Vnffgd(Nsd nsd,
			String vnffgdId,
			List<String> vnfdId,
			List<String> pnfdId,
			List<String> virtualLinkDescId,
			List<String> cpdPoolId) {
		this.nsd = nsd;
		this.vnffgdId = vnffgdId;
		if (vnfdId != null) this.vnfdId = vnfdId;
		if (pnfdId != null) this.pnfdId = pnfdId;
		if (virtualLinkDescId != null) this.virtualLinkDescId = virtualLinkDescId;
		if (cpdPoolId != null) this.cpdPoolId = cpdPoolId;
	}
	
	

	/**
	 * @return the vnffgdId
	 */
	@JsonProperty("vnffgdId")
	public String getVnffgdId() {
		return vnffgdId;
	}

	/**
	 * @return the vnfdId
	 */
	@JsonProperty("vnfdId")
	public List<String> getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the pnfdId
	 */
	@JsonProperty("pnfdId")
	public List<String> getPnfdId() {
		return pnfdId;
	}

	/**
	 * @return the virtualLinkDescId
	 */
	@JsonProperty("virtualLinkDescId")
	public List<String> getVirtualLinkDescId() {
		return virtualLinkDescId;
	}

	/**
	 * @return the cpdPoolId
	 */
	@JsonProperty("cpdPoolId")
	public List<String> getCpdPoolId() {
		return cpdPoolId;
	}

	/**
	 * @return the nfpd
	 */
	@JsonProperty("nfpd")
	public List<Nfpd> getNfpd() {
		return nfpd;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.vnffgdId == null) throw new MalformattedElementException("VNFFGD without ID");
		if ((this.vnfdId == null) || (this.vnfdId.isEmpty())) {
			throw new MalformattedElementException("VNFFGD without VNFD IDs");
		}
		if ((this.virtualLinkDescId == null) || (this.virtualLinkDescId.isEmpty())) {
			throw new MalformattedElementException("VNFFGD without VLD IDs");
		}
		if ((this.cpdPoolId == null) || (this.cpdPoolId.isEmpty())) {
			throw new MalformattedElementException("VNFFGD without CPD pool IDs");
		}
	}

	public void setNsd(Nsd nsd) {
		this.nsd = nsd;
	}

	public void setVnffgdId(String vnffgdId) {
		this.vnffgdId = vnffgdId;
	}

	public void setVnfdId(List<String> vnfdId) {
		this.vnfdId = vnfdId;
	}

	public void setPnfdId(List<String> pnfdId) {
		this.pnfdId = pnfdId;
	}

	public void setVirtualLinkDescId(List<String> virtualLinkDescId) {
		this.virtualLinkDescId = virtualLinkDescId;
	}

	public void setCpdPoolId(List<String> cpdPoolId) {
		this.cpdPoolId = cpdPoolId;
	}

	public void setNfpd(List<Nfpd> nfpd) {
		this.nfpd = nfpd;
	}
}
