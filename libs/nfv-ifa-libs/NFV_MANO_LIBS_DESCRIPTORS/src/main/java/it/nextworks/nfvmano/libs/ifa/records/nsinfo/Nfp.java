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

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.Rule;

/**
 * The Nfp information element defines the information related to the NFP (Network Forwarding Path).
 * Ref. IFA 013 v2.3.1 section 8.3.3.15
 * 
 * @author nextworks
 *
 */
@Entity
public class Nfp implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private VnffgInfo vnffgInfo;
	
	private String nfpId;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> cpId = new ArrayList<>();
	
	private int totalCp;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "nfp", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Rule nfpRule;
	
	private OperationalState nfpState;
	
	public Nfp() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnffgInfo VNFFG info this element belongs to
	 * @param nfpId Identifier of this Nfp information element.
	 * @param cpId Identifier(s) of the CPs and/or SAPs which the NFP passes by (Reference to VnfExtCpInfo or PnfExtCpInfo or SapInfo
	 * @param totalCp Total number of CPs in this NFP.
	 * @param nfpState An indication of whether the NFP is enabled or disabled.
	 */
	public Nfp(VnffgInfo vnffgInfo,
			String nfpId,
			List<String> cpId,
			int totalCp,
			OperationalState nfpState) {
		this.vnffgInfo = vnffgInfo;
		this.nfpId = nfpId;
		if (cpId != null) this.cpId = cpId;
		this.totalCp = totalCp;
		this.nfpState = nfpState;
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnffgInfo VNFFG info this element belongs to
	 * @param nfpId Identifier of this Nfp information element.
	 * @param cpId Identifier(s) of the CPs and/or SAPs which the NFP passes by (Reference to VnfExtCpInfo or PnfExtCpInfo or SapInfo
	 * @param totalCp Total number of CPs in this NFP.
	 * @param nfpRule NFP classification and selection rule.
	 * @param nfpState An indication of whether the NFP is enabled or disabled.
	 */
	public Nfp(VnffgInfo vnffgInfo,
			String nfpId,
			List<String> cpId,
			int totalCp,
			Rule nfpRule,
			OperationalState nfpState) {
		this.vnffgInfo = vnffgInfo;
		this.nfpId = nfpId;
		if (cpId != null) this.cpId = cpId;
		this.totalCp = totalCp;
		this.nfpRule = nfpRule;
		this.nfpState = nfpState;
	}

	
	
	/**
	 * @return the nfpId
	 */
	public String getNfpId() {
		return nfpId;
	}

	/**
	 * @return the cpId
	 */
	public List<String> getCpId() {
		return cpId;
	}

	/**
	 * @return the totalCp
	 */
	public int getTotalCp() {
		return totalCp;
	}

	/**
	 * @return the nfpRule
	 */
	public Rule getNfpRule() {
		return nfpRule;
	}

	/**
	 * @return the nfpState
	 */
	public OperationalState getNfpState() {
		return nfpState;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nfpId == null) throw new MalformattedElementException("NFP without ID");
		if ((cpId == null) || (cpId.isEmpty())) throw new MalformattedElementException("NFP without CP IDs");
		if (nfpRule == null) throw new MalformattedElementException("NFP without rule");
	}

}
