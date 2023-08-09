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
package it.nextworks.nfvmano.libs.ifa.records.nsinfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides information about a PNF that is part of an NS instance.
 * Ref. IFA 013 v2.4.1 section 8.3.3.9
 * 
 * @author nextworks
 *
 */
@Entity
public class PnfInfo implements DescriptorInformationElement {


	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsInfo nsInfo;
	
	private String pnfId;
	private String pnfName;
	private String pnfdId;
	private String pnfdInfoId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String pnfProfileId;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<PnfExtCpInfo> cpInfo = new ArrayList<>();
	
	public PnfInfo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInfo NS info this element belongs to
	 * @param pnfId Identifier of the PNF. Assigned by OSS and provided to NFVO.
	 * @param pnfName Human readable name of the PNF.
	 * @param pnfdId Identifier of the PNFD.
	 * @param pnfdInfoId Identifier of (reference to) the PNFD information related to this PNF.
	 * @param pnfProfileId Identifier of (reference to) the PNF Profile to be used for this PNF.
	 * @param cpInfo Information on the external CP of the PNF.
	 */
	public PnfInfo(NsInfo nsInfo, String pnfId, String pnfName, String pnfdId, String pnfdInfoId, String pnfProfileId, List<PnfExtCpInfo> cpInfo) {
		this.nsInfo = nsInfo;
		this.pnfId = pnfId;
		this.pnfdId = pnfdId;
		this.pnfdInfoId = pnfdInfoId;
		this.pnfName = pnfName;
		this.pnfProfileId = pnfProfileId;
		if (cpInfo != null) this.cpInfo = cpInfo;
	}
	
	

	/**
	 * @return the pnfName
	 */
	public String getPnfName() {
		return pnfName;
	}

	/**
	 * @return the pnfdInfoId
	 */
	public String getPnfdInfoId() {
		return pnfdInfoId;
	}

	/**
	 * @return the cpInfo
	 */
	public List<PnfExtCpInfo> getCpInfo() {
		return cpInfo;
	}
	
	

	/**
	 * @return the nsInfo
	 */
	public NsInfo getNsInfo() {
		return nsInfo;
	}

	/**
	 * @return the pnfId
	 */
	public String getPnfId() {
		return pnfId;
	}

	/**
	 * @return the pnfdId
	 */
	public String getPnfdId() {
		return pnfdId;
	}

	/**
	 * @return the pnfProfileId
	 */
	public String getPnfProfileId() {
		return pnfProfileId;
	}
	
	/**
	 * @return the id
	 */
	@JsonIgnore
	public Long getId() {
		return id;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.pnfId == null) throw new MalformattedElementException("PNF info without PNF ID");
		if (this.pnfdId == null) throw new MalformattedElementException("PNF info without PNFD ID");
		if (this.pnfdInfoId == null) throw new MalformattedElementException("PNF info without PNFD info ID");
		if (this.pnfName == null) throw new MalformattedElementException("PNF info without name");
		//if (this.pnfProfileId == null) throw new MalformattedElementException("PNF info without PNF profile ID");
		if ((this.cpInfo == null) || (this.cpInfo.isEmpty())) {
			throw new MalformattedElementException("PNF info without CP information");
		} else {
			for (PnfExtCpInfo i : this.cpInfo) i.isValid();
		}
	}
}
