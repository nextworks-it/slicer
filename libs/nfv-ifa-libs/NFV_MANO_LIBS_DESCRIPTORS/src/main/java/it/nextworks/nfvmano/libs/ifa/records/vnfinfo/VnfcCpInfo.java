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
package it.nextworks.nfvmano.libs.ifa.records.vnfinfo;

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

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.VimResourceStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides information related to a CP of a VNFC.
 * 
 * REF IFA 007 v2.3.1 - 8.5.14
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfcCpInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private VnfcResourceInfo vri;
	
	private String cpInstanceId;
	private String cpdId;
	private String vnfExtCpId;
	
	@JsonIgnore
	private VimResourceStatus vimResourceStatus;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> address = new ArrayList<>();
	
	public VnfcCpInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vri VNFC resource info this CP info belongs to
	 * @param cpInstanceId Identifier of this VnfExtCpInfo information element.
	 * @param cpdId Identifier of the external Connection Point Descriptor (CPD), VnfExtCpd, in the VNFD.
	 * @param vnfExtCpId When the VNFC CP is exposed as external CP of the VNF, the identifier of this external VNF CP.
	 * @param address List of network addresses that have been configured (statically or dynamically) on the CP.
	 */
	public VnfcCpInfo(VnfcResourceInfo vri,
			String cpInstanceId,
			String cpdId,
			String vnfExtCpId,
			List<String> address) {
		this.vri = vri;
		this.cpInstanceId = cpInstanceId;
		this.cpdId = cpdId;
		this.vnfExtCpId = vnfExtCpId;
		if (address != null) this.address = address;
		this.vimResourceStatus = VimResourceStatus.INSTANTIATING;
	}

	
	
	/**
	 * @return the cpInstanceId
	 */
	public String getCpInstanceId() {
		return cpInstanceId;
	}

	/**
	 * @return the cpdId
	 */
	public String getCpdId() {
		return cpdId;
	}

	/**
	 * @return the address
	 */
	public List<String> getAddress() {
		return address;
	}

	/**
	 * @return the vimResourceStatus
	 */
	@JsonIgnore
	public VimResourceStatus getVimResourceStatus() {
		return vimResourceStatus;
	}
	
	

	/**
	 * @return the vnfExtCpId
	 */
	public String getVnfExtCpId() {
		return vnfExtCpId;
	}

	/**
	 * @param vimResourceStatus the vimResourceStatus to set
	 */
	@JsonIgnore
	public void setVimResourceStatus(VimResourceStatus vimResourceStatus) {
		this.vimResourceStatus = vimResourceStatus;
	}
	
	
	public void addAddress(String address) {
		this.address.add(address);
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (cpInstanceId == null) throw new MalformattedElementException("VNF external CP info without CP ID");
		if (cpdId == null) throw new MalformattedElementException("VNF external CP info without CPD ID");
	}

}
