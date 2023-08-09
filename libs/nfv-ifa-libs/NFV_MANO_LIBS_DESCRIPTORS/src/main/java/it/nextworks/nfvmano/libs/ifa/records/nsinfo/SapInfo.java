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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;

/**
 * This information element provides information about an SAP of an NS instance.
 * Ref. IFA 013 v2.3.1 section 8.3.3.12
 * 
 * @author nextworks
 *
 */
@Entity
public class SapInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsInfo nsInfo;
	
	private String sapInstanceId;
	private String sapdId;
	private String sapName;
	private String description;
	private String address;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<UserAccessInfo> userAccessInfo = new ArrayList<>();	//Note: this is NOT standard
	
	
	
	public SapInfo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInfo NS info this element belongs to
	 * @param sapInstanceId Identifier of this SapInfo information element, identifying the SAP instance.
	 * @param sapdId Reference to the SAPD for this SAP.
	 * @param sapName Human readable name for the SAP.
	 * @param description Human readable description for the SAP.
	 * @param address Address for this SAP. In some cases, the NFVO provides the address.
	 * @param userAccessInfo User Access Info 
	 */
	public SapInfo(NsInfo nsInfo,
			String sapInstanceId,
			String sapdId,
			String sapName,
			String description,
			String address,
			List<UserAccessInfo> userAccessInfo) {
		this.nsInfo = nsInfo;
		this.sapdId = sapdId;
		this.sapInstanceId = sapInstanceId;
		this.sapName = sapName;
		this.description = description;
		this.address = address;
		if (userAccessInfo != null) this.userAccessInfo = userAccessInfo;
	}
	
	

	/**
	 * @return the sapInstanceId
	 */
	public String getSapInstanceId() {
		return sapInstanceId;
	}

	/**
	 * @return the sapdId
	 */
	public String getSapdId() {
		return sapdId;
	}

	/**
	 * @return the sapName
	 */
	public String getSapName() {
		return sapName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	public void addUserAccessInfo(String sapdId, String vnfdId, String vnfId, String vnfExtCpdId, String address) {
		this.userAccessInfo.add(new UserAccessInfo(sapdId, vnfdId, vnfId, vnfExtCpdId, address));
	}
	
	public void removeUserAccessInfo(String vnfId, String vnfExtCpdId) throws NotExistingEntityException {
		for (UserAccessInfo uai : userAccessInfo) {
			if (uai.getVnfId().equals(vnfId) && uai.getVnfExtCpdId().equals(vnfExtCpdId)) {
				userAccessInfo.remove(uai);
				return;
			}
		}
		throw new NotExistingEntityException("User Access Info for VNF " + vnfId + " and VNF external connection point " + vnfExtCpdId + " not found.");
	}
	
	public void removeUserAccessInfo(String vnfId) {
		for (UserAccessInfo uai : userAccessInfo) {
			if (uai.getVnfId().equals(vnfId)) {
				userAccessInfo.remove(uai);
			}
		}
	}
	
	

	/**
	 * @return the userAccessInfo
	 */
	public List<UserAccessInfo> getUserAccessInfo() {
		return userAccessInfo;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (sapInstanceId == null) throw new MalformattedElementException("SAP Info without SAP instance ID");
		if (sapdId == null) throw new MalformattedElementException("SAP Info without SAPD ID");
		if (sapName == null) throw new MalformattedElementException("SAP Info without SAP name");
		if (description == null) throw new MalformattedElementException("SAP Info without SAP description");
		if (address == null) throw new MalformattedElementException("SAP Info without SAP address");
	}

}
