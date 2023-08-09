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

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The NsVirtuallLinkConnectivity information element 
 * describes connection information between a connection 
 * point and a NS virtual Link.
 * 
 * Ref. IFA 014 v2.3.1 - 6.3.7
 * 
 * @author nextworks
 *
 */
@Entity
public class NsVirtualLinkConnectivity implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private VnfProfile vnfProfile;
	
	@JsonIgnore
	@ManyToOne
	private PnfProfile pnfProfile;
	
	@JsonIgnore
	@ManyToOne
	private NsProfile nsProfile;	//added since v2.4.1
	
	private String virtualLinkProfileId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> cpdId = new ArrayList<>();
	
	public NsVirtualLinkConnectivity() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfProfile the VNF profile this element belongs to
	 * @param virtualLinkProfileId Reference an NS VL profile.
	 * @param cpdId References the descriptor of a CP on a VNF/PNF or a SAP which connects to VLs instantiated from the profile identified by the virtualLinkProfileId
	 */
	public NsVirtualLinkConnectivity(VnfProfile vnfProfile,
			String virtualLinkProfileId,
			List<String> cpdId) {
		this.vnfProfile = vnfProfile;
		this.virtualLinkProfileId = virtualLinkProfileId;
		if (cpdId != null) this.cpdId = cpdId;
	}
	
	/**
	 * Constructor
	 * 
	 * @param pnfProfile the PNF profile this element belongs to
	 * @param virtualLinkProfileId Reference an NS VL profile.
	 * @param cpdId References the descriptor of a CP on a VNF/PNF or a SAP which connects to VLs instantiated from the profile identified by the virtualLinkProfileId
	 */
	public NsVirtualLinkConnectivity(PnfProfile pnfProfile,
			String virtualLinkProfileId,
			List<String> cpdId) {
		this.pnfProfile = pnfProfile;
		this.virtualLinkProfileId = virtualLinkProfileId;
		if (cpdId != null) this.cpdId = cpdId;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsProfile the NS profile this element belongs to
	 * @param virtualLinkProfileId Reference an NS VL profile.
	 * @param cpdId References the descriptor of a CP on a VNF/PNF or a SAP which connects to VLs instantiated from the profile identified by the virtualLinkProfileId
	 */
	public NsVirtualLinkConnectivity(NsProfile nsProfile,
			String virtualLinkProfileId,
			List<String> cpdId) {
		this.nsProfile = nsProfile;
		this.virtualLinkProfileId = virtualLinkProfileId;
		if (cpdId != null) this.cpdId = cpdId;
	}
	

	/**
	 * @return the virtualLinkProfileId
	 */
	@JsonProperty("virtualLinkProfileId")
	public String getVirtualLinkProfileId() {
		return virtualLinkProfileId;
	}

	/**
	 * @return the cpdId
	 */
	@JsonProperty("cpdId")
	public List<String> getCpdId() {
		return cpdId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.virtualLinkProfileId == null) throw new MalformattedElementException("NS VL connectivity without VL profile ID");
		if ((this.cpdId == null) || (this.cpdId.isEmpty())) 
			throw new MalformattedElementException("NS VL connectivity without CPD IDs");
	}

	public void setVnfProfile(VnfProfile vnfProfile) {
		this.vnfProfile = vnfProfile;
	}

	public void setPnfProfile(PnfProfile pnfProfile) {
		this.pnfProfile = pnfProfile;
	}

	public void setNsProfile(NsProfile nsProfile) {
		this.nsProfile = nsProfile;
	}

	public void setVirtualLinkProfileId(String virtualLinkProfileId) {
		this.virtualLinkProfileId = virtualLinkProfileId;
	}

	public void setCpdId(List<String> cpdId) {
		this.cpdId = cpdId;
	}
}
