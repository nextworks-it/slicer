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

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element contains information about a VNFFG instance.
 * Ref. IFA 013 v2.3.1 section 8.3.3.13
 * 
 * @author nextworks
 *
 */
@Entity
public class VnffgInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsInfo nsInfo;
	
	private String vnffgId;
	private String vnffgdId;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> vnfId = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> pnfId = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> virtualLinkId = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> cpId = new ArrayList<>();
	
	@OneToMany(mappedBy = "vnffgInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Nfp> nfp = new ArrayList<>();
	
	public VnffgInfo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInfo NS info this element belongs to
	 * @param vnffgId Identifier of the Vnffg information element.
	 * @param vnffgdId Identifier of the VNFFGD used to instantiate this VNFFG.
	 * @param vnfId Identifier(s) of the constituent VNF instance(s) of the VNFFG.
	 * @param pnfId Identifier(s) of the constituent PNF instance(s) of the VNFFG.
	 * @param virtualLinkId Identifier(s) of the constituent VL instance(s) of the VNFFG.
	 * @param cpId Identifiers of the CP instances attached to the constituent VNFs and PNFs or the sap instances of the VNFFG
	 *
	 */
	public VnffgInfo(NsInfo nsInfo, 
			String vnffgId,
			String vnffgdId,
			List<String> vnfId,
			List<String> pnfId,
			List<String> virtualLinkId,
			List<String> cpId) {
		this.nsInfo = nsInfo;
		this.vnffgdId = vnffgdId;
		this.vnffgId = vnffgId;
		if (vnfId != null) this.vnfId = vnfId;
		if (pnfId != null) this.pnfId = pnfId;
		if (virtualLinkId != null) this.virtualLinkId = virtualLinkId;
		if (cpId != null) this.cpId = cpId;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInfo NS info this element belongs to
	 * @param vnffgId Identifier of the Vnffg information element.
	 * @param vnffgdId Identifier of the VNFFGD used to instantiate this VNFFG.
	 * @param vnfId Identifier(s) of the constituent VNF instance(s) of the VNFFG.
	 * @param pnfId Identifier(s) of the constituent PNF instance(s) of the VNFFG.
	 * @param virtualLinkId Identifier(s) of the constituent VL instance(s) of the VNFFG.
	 * @param cpId Identifiers of the CP instances attached to the constituent VNFs and PNFs or the sap instances of the VNFFG
	 * @param nfp Information on the NFPs of this VNFFG.
	 */
	public VnffgInfo(NsInfo nsInfo, 
			String vnffgId,
			String vnffgdId,
			List<String> vnfId,
			List<String> pnfId,
			List<String> virtualLinkId,
			List<String> cpId,
			List<Nfp> nfp) {
		this.nsInfo = nsInfo;
		this.vnffgdId = vnffgdId;
		this.vnffgId = vnffgId;
		if (vnfId != null) this.vnfId = vnfId;
		if (pnfId != null) this.pnfId = pnfId;
		if (virtualLinkId != null) this.virtualLinkId = virtualLinkId;
		if (cpId != null) this.cpId = cpId;
		if (nfp != null) this.nfp = nfp;
	}
	
	

	/**
	 * @return the vnffgId
	 */
	public String getVnffgId() {
		return vnffgId;
	}

	/**
	 * @return the vnffgdId
	 */
	public String getVnffgdId() {
		return vnffgdId;
	}

	/**
	 * @return the vnfId
	 */
	public List<String> getVnfId() {
		return vnfId;
	}

	/**
	 * @return the pnfId
	 */
	public List<String> getPnfId() {
		return pnfId;
	}

	/**
	 * @return the virtualLinkId
	 */
	public List<String> getVirtualLinkId() {
		return virtualLinkId;
	}

	/**
	 * @return the cpId
	 */
	public List<String> getCpId() {
		return cpId;
	}

	/**
	 * @return the nfp
	 */
	public List<Nfp> getNfp() {
		return nfp;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnffgdId == null) throw new MalformattedElementException("VNFFG Info without VNFFGD ID");
		if (vnffgId == null) throw new MalformattedElementException("VNFFG Info without VNFFG ID");
		if ((vnfId == null) || (vnfId.isEmpty())) throw new MalformattedElementException("VNFFG Info without VNF ID");
		if ((virtualLinkId == null) || (virtualLinkId.isEmpty())) throw new MalformattedElementException("VNFFG Info without VL ID");
		if ((cpId == null) || (cpId.isEmpty())) throw new MalformattedElementException("VNFFG Info without CP ID");
		if ((nfp == null) || (nfp.isEmpty())) {
			throw new MalformattedElementException("VNFFG Info without NFP");
		} else {
			for (Nfp n:nfp) n.isValid();
		}
	}

}
