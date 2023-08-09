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

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.MonitoringParameter;
import it.nextworks.nfvmano.libs.ifa.common.elements.ScaleInfo;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperativeState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;

/**
 * This information element provides run-time information specific to an instantiated VNF instance.
 * 
 * REF IFA 007 v2.3.1 - 8.5.3
 * 
 * @author nextworks
 *
 */
@Entity
public class InstantiatedVnfInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="vnf_info_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private VnfInfo vnfInfo;
	
	private String flavourId;
	
	private OperativeState vnfState;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<ScaleInfo> scaleStatus = new ArrayList<>();
	
	@OneToMany(mappedBy = "iVnfInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VnfExtCpInfo> extCpInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "iVnfInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ExtVirtualLinkInfo> extVirtualLinkInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "iVnfInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ExtManagedVirtualLinkInfo> extManagedVirtualLinkInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<MonitoringParameter> monitoringParameter = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String localizationLanguage;

//	Removed in version 2.3.1	
//	@JsonInclude(JsonInclude.Include.NON_EMPTY)
//	@ElementCollection(fetch=FetchType.EAGER)
//	@Fetch(FetchMode.SELECT)
//	@Cascade(org.hibernate.annotations.CascadeType.ALL)
//	private List<VimInfo> vimInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "iVnfInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VnfcResourceInfo> vnfcResourceInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "iVnfInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VirtualLinkResourceInfo> virtualLinkResourceInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "iVnfInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VirtualStorageResourceInfo> virtualStorageResourceInfo = new ArrayList<>();
	
	@JsonIgnore
	private String managementIp;
	
	public InstantiatedVnfInfo() {
		// JPA only
	}

// Removed vimInfo from v2.3.1	
//	public InstantiatedVnfInfo(VnfInfo vnfInfo,
//			String flavourId,
//			OperativeState vnfState,
//			List<ScaleInfo> scaleStatus,
//			List<MonitoringParameter> monitoringParameter,
//			String localizationLanguage,
//			List<VimInfo> vimInfo) {
//		this.vnfInfo = vnfInfo;
//		this.flavourId = flavourId;
//		this.vnfState = vnfState;
//		if (scaleStatus != null) this.scaleStatus = scaleStatus;
//		if (monitoringParameter != null) this.monitoringParameter = monitoringParameter;
//		this.localizationLanguage = localizationLanguage;
//		if (vimInfo != null) this.vimInfo = vimInfo;
//	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInfo the VNF info this structure refers to
	 * @param flavourId Identifier of the VNF DF applied to this VNF instance.
	 * @param vnfState The state of the VNF instance.
	 * @param scaleStatus Scale status of the VNF, one entry per aspect.
	 * @param monitoringParameter Performance metrics tracked by VNFM (e.g. for auto-scaling purposes) and their current (as known to the VNFM) values.
	 * @param localizationLanguage Information about localization language of the VNF
	 */
	public InstantiatedVnfInfo(VnfInfo vnfInfo,
			String flavourId,
			OperativeState vnfState,
			List<ScaleInfo> scaleStatus,
			List<MonitoringParameter> monitoringParameter,
			String localizationLanguage) {
		this.vnfInfo = vnfInfo;
		this.flavourId = flavourId;
		this.vnfState = vnfState;
		if (scaleStatus != null) this.scaleStatus = scaleStatus;
		if (monitoringParameter != null) this.monitoringParameter = monitoringParameter;
		this.localizationLanguage = localizationLanguage;
	}
	
	

	/**
	 * @return the vnfInfo
	 */
	public VnfInfo getVnfInfo() {
		return vnfInfo;
	}

	/**
	 * @return the flavourId
	 */
	public String getFlavourId() {
		return flavourId;
	}

	/**
	 * @return the vnfState
	 */
	public OperativeState getVnfState() {
		return vnfState;
	}

	/**
	 * @return the scaleStatus
	 */
	public List<ScaleInfo> getScaleStatus() {
		return scaleStatus;
	}

	/**
	 * @return the extCpInfo
	 */
	public List<VnfExtCpInfo> getExtCpInfo() {
		return extCpInfo;
	}

	/**
	 * @return the extVirtualLinkInfo
	 */
	public List<ExtVirtualLinkInfo> getExtVirtualLinkInfo() {
		return extVirtualLinkInfo;
	}

	/**
	 * @return the extManagedVirtualLinkInfo
	 */
	public List<ExtManagedVirtualLinkInfo> getExtManagedVirtualLinkInfo() {
		return extManagedVirtualLinkInfo;
	}

	/**
	 * @return the monitoringParameter
	 */
	public List<MonitoringParameter> getMonitoringParameter() {
		return monitoringParameter;
	}

	/**
	 * @return the localizationLanguage
	 */
	public String getLocalizationLanguage() {
		return localizationLanguage;
	}

// Removed from v2.3.1	
//	/**
//	 * @return the vimInfo
//	 */
//	public List<VimInfo> getVimInfo() {
//		return vimInfo;
//	}

	/**
	 * @return the vnfcResourceInfo
	 */
	public List<VnfcResourceInfo> getVnfcResourceInfo() {
		return vnfcResourceInfo;
	}

	/**
	 * @return the virtualLinkResourceInfo
	 */
	@JsonProperty("vnfVirtualLinkResourceInfo")
	public List<VirtualLinkResourceInfo> getVirtualLinkResourceInfo() {
		return virtualLinkResourceInfo;
	}

	/**
	 * @return the virtualStorageResourceInfo
	 */
	public List<VirtualStorageResourceInfo> getVirtualStorageResourceInfo() {
		return virtualStorageResourceInfo;
	}

	
	
	/**
	 * @param vnfState the vnfState to set
	 */
	public void setVnfState(OperativeState vnfState) {
		this.vnfState = vnfState;
	}
	
	@JsonIgnore
	public VnfExtCpInfo getExtCpFromCpdId(String cpdId) throws NotExistingEntityException {
		for (VnfExtCpInfo cp : extCpInfo) {
			if (cp.getCpdId().equals(cpdId)) return cp;
		}
		throw new NotExistingEntityException("VNF external connection point with CPD ID " + cpdId + " not found in instantiated VNF info");
	}
	
	@JsonIgnore
	public List<VnfcResourceInfo> getVnfcResourceInfoFromVduId(String vduId) throws NotExistingEntityException {
		List<VnfcResourceInfo> result = new ArrayList<>();
		for (VnfcResourceInfo vnfc : vnfcResourceInfo) {
			if (vnfc.getVduId().equals(vduId)) result.add(vnfc);
		}
		if (result.isEmpty()) throw new NotExistingEntityException("VNFC for VDU " + vduId + " not found in instantiated VNF info");
		return result;
	}
	
	

	/**
	 * @return the managementIp
	 */
	@JsonIgnore
	public String getManagementIp() {
		return managementIp;
	}

	/**
	 * @param managementIp the managementIp to set
	 */
	@JsonIgnore
	public void setManagementIp(String managementIp) {
		this.managementIp = managementIp;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (flavourId == null) throw new MalformattedElementException("Instantiated VNF info without flavour ID.");
		if (scaleStatus != null) {
			for (ScaleInfo s : scaleStatus) s.isValid();
		}
		if ((extCpInfo == null) || (extCpInfo.isEmpty())) throw new MalformattedElementException("Instantiated VNF info without external CP.");
		else {
			for (VnfExtCpInfo cp : extCpInfo) cp.isValid();
		}
		for (ExtVirtualLinkInfo vl : extVirtualLinkInfo) vl.isValid();
		for (ExtManagedVirtualLinkInfo vl : extManagedVirtualLinkInfo) vl.isValid();
		for (MonitoringParameter mp : monitoringParameter) mp.isValid();
//		for (VimInfo vi : vimInfo) vi.isValid();
		for (VnfcResourceInfo ri : vnfcResourceInfo) ri.isValid();
		for (VirtualLinkResourceInfo ri : virtualLinkResourceInfo) ri.isValid();
		for (VirtualStorageResourceInfo ri : virtualStorageResourceInfo) ri.isValid();
	}

}
