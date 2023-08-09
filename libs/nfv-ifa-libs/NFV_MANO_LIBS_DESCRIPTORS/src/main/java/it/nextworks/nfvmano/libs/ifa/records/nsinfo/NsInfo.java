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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
import it.nextworks.nfvmano.libs.ifa.common.elements.AffinityRule;
import it.nextworks.nfvmano.libs.ifa.common.enums.InstantiationState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;

/**
 * This information element provides run-time information about an NS instance.
 * Ref. IFA 013 v2.3.1 section 8.3.3.2
 * 
 * @author nextworks
 *
 */
@Entity
public class NsInfo implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	
	private String nsInstanceId;
	private String nsName;
	private String description;
	private String nsdId;
	
	@JsonIgnore
	private String tenantId;
	
	@JsonIgnore
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> configurationParameters = new HashMap<>();
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String flavourId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> vnfInfoId = new ArrayList<String>();
	
	//KEY: VNF info ID; value: index
	@JsonIgnore
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, Integer> vnfInfoMap = new HashMap<>(); 

	//KEY: VNF info ID; value: VNFD ID
	@JsonIgnore
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> vnfInfoVnfdIdMap = new HashMap<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<PnfInfo> pnfInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NsVirtualLinkInfo> virtualLinkInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VnffgInfo> vnffgInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsInfo", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SapInfo> sapInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> nestedNsInfoId = new ArrayList<>();
	
//	@JsonInclude(JsonInclude.Include.NON_EMPTY)
//	@ElementCollection(fetch=FetchType.EAGER)
//	@Fetch(FetchMode.SELECT)
//	@Cascade(org.hibernate.annotations.CascadeType.ALL)
//	private List<UserAccessInfo> userAccessInfo = new ArrayList<>();	//Note: this is NOT standard
//	
	private InstantiationState nsState;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<NsScaleInfo> nsScaleStatus = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<AffinityRule> additionalAffinityOrAntiAffinityRule = new ArrayList<>();
	
	//This is not standard
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String monitoringDashboardUrl;
	
	
	public NsInfo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId Identifier of this NsInfo information element, identifying the NS instance.
	 * @param nsName Human readable name of the NS instance.
	 * @param description Human readable description of the NS instance.
	 * @param nsdId Reference to the NSD used to instantiate this NS.
	 * @param flavourId Reference to the flavour of the NSD used to instantiate this NS.
	 * @param vnfInfoId Reference to information on constituent VNFs of this NS.
	 * @param nestedNsInfoId Reference to information on nested NSs of this NS.
	 * @param nsState The state of the NS.
	 * @param nsScaleStatus Represents for each NS scaling aspect declared in the applicable DF
	 * @param additionalAffinityOrAntiAffinityRule Information on the additional affinity or anti-affinity rule from NS instantiation operation. Shall not conflict with rules already specified in the NSD. 
	 * @param tenantId ID of the tenant the NS info belongs to
	 * @param configurationParameters the configuration parameters provided by the user
	 * 
	 */
	public NsInfo(String nsInstanceId,
			String nsName,
			String description,
			String nsdId,
			String flavourId,
			List<String> vnfInfoId, 
			List<String> nestedNsInfoId, 
			InstantiationState nsState,
			List<NsScaleInfo> nsScaleStatus, 
			List<AffinityRule> additionalAffinityOrAntiAffinityRule,
			String tenantId,
			Map<String, String> configurationParameters) {
		this.nsInstanceId = nsInstanceId;
		this.nsName = nsName;
		this.description = description;
		this.nsdId = nsdId;
		this.flavourId = flavourId;
		if (vnfInfoId != null) this.vnfInfoId = vnfInfoId;
		if (nestedNsInfoId != null) this.nestedNsInfoId = nestedNsInfoId;
		this.nsState = nsState;
		if (nsScaleStatus != null) this.nsScaleStatus = nsScaleStatus;
		if (additionalAffinityOrAntiAffinityRule != null) this.additionalAffinityOrAntiAffinityRule = additionalAffinityOrAntiAffinityRule;
		this.tenantId = tenantId;
		if (configurationParameters != null) this.configurationParameters = configurationParameters;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId Identifier of this NsInfo information element, identifying the NS instance.
	 * @param nsName Human readable name of the NS instance.
	 * @param description Human readable description of the NS instance.
	 * @param nsdId Reference to the NSD used to instantiate this NS.
	 * @param flavourId Reference to the flavour of the NSD used to instantiate this NS.
	 * @param vnfInfoId Reference to information on constituent VNFs of this NS.
	 * @param pnfInfo Information on the PNF(s) that are part of this NS.
	 * @param virtualLinkInfo Information on the VLs of this NS.
	 * @param vnffgInfo Information on the VNFFGs of this NS.
	 * @param nestedNsInfoId Reference to information on nested NSs of this NS.
	 * @param nsState The state of the NS.
	 * @param nsScaleStatus Represents for each NS scaling aspect declared in the applicable DF
	 * @param additionalAffinityOrAntiAffinityRule Information on the additional affinity or anti-affinity rule from NS instantiation operation. Shall not conflict with rules already specified in the NSD. 
	 * @param tenantId ID of the tenant the NS info belongs to
	 * 
	 */
	public NsInfo(String nsInstanceId,
			String nsName,
			String description,
			String nsdId,
			String flavourId,
			List<String> vnfInfoId, 
			List<PnfInfo> pnfInfo, 
			List<NsVirtualLinkInfo> virtualLinkInfo, 
			List<VnffgInfo> vnffgInfo, 
			List<String> nestedNsInfoId, 
			InstantiationState nsState,
			List<NsScaleInfo> nsScaleStatus, 
			List<AffinityRule> additionalAffinityOrAntiAffinityRule,
			String tenantId) {
		this.nsInstanceId = nsInstanceId;
		this.nsName = nsName;
		this.description = description;
		this.nsdId = nsdId;
		this.flavourId = flavourId;
		if (vnfInfoId != null) this.vnfInfoId = vnfInfoId;
		if (pnfInfo != null) this.pnfInfo = pnfInfo;
		if (virtualLinkInfo != null) this.virtualLinkInfo = virtualLinkInfo;
		if (vnffgInfo != null) this.vnffgInfo = vnffgInfo;
		if (nestedNsInfoId != null) this.nestedNsInfoId = nestedNsInfoId;
		this.nsState = nsState;
		if (nsScaleStatus != null) this.nsScaleStatus = nsScaleStatus;
		if (additionalAffinityOrAntiAffinityRule != null) this.additionalAffinityOrAntiAffinityRule = additionalAffinityOrAntiAffinityRule;
		this.tenantId = tenantId;
	}

	
	
	/**
	 * @param nsInstanceId
	 * @param nsName
	 * @param description
	 * @param nsdId
	 * @param tenantId
	 * @param configurationParameters
	 * @param flavourId
	 * @param vnfInfoId
	 * @param pnfInfo
	 * @param virtualLinkInfo
	 * @param vnffgInfo
	 * @param sapInfo
	 * @param nestedNsInfoId
	 * @param nsState
	 * @param nsScaleStatus
	 * @param additionalAffinityOrAntiAffinityRule
	 * @param monitoringDashboardUrl
	 */
	public NsInfo(String nsInstanceId, String nsName, String description, String nsdId, String tenantId,
			Map<String, String> configurationParameters, String flavourId, List<String> vnfInfoId,
			List<PnfInfo> pnfInfo,
			List<NsVirtualLinkInfo> virtualLinkInfo, List<VnffgInfo> vnffgInfo, List<SapInfo> sapInfo,
			List<String> nestedNsInfoId, InstantiationState nsState, List<NsScaleInfo> nsScaleStatus,
			List<AffinityRule> additionalAffinityOrAntiAffinityRule, String monitoringDashboardUrl) {
		this.nsInstanceId = nsInstanceId;
		this.nsName = nsName;
		this.description = description;
		this.nsdId = nsdId;
		this.tenantId = tenantId;
		this.configurationParameters = configurationParameters;
		this.flavourId = flavourId;
		this.vnfInfoId = vnfInfoId;
		this.pnfInfo = pnfInfo;
		this.virtualLinkInfo = virtualLinkInfo;
		this.vnffgInfo = vnffgInfo;
		this.sapInfo = sapInfo;
		this.nestedNsInfoId = nestedNsInfoId;
		this.nsState = nsState;
		this.nsScaleStatus = nsScaleStatus;
		this.additionalAffinityOrAntiAffinityRule = additionalAffinityOrAntiAffinityRule;
		this.monitoringDashboardUrl = monitoringDashboardUrl;
	}

	/**
	 * @param flavourId the flavourId to set
	 */
	public void setFlavourId(String flavourId) {
		this.flavourId = flavourId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param nsInstanceId the nsInstanceId to set
	 */
	public void setNsInstanceId(String nsInstanceId) {
		this.nsInstanceId = nsInstanceId;
	}

	/**
	 * @return the nsInstanceId
	 */
	public String getNsInstanceId() {
		return nsInstanceId;
	}

	/**
	 * @return the nsName
	 */
	public String getNsName() {
		return nsName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the nsdId
	 */
	public String getNsdId() {
		return nsdId;
	}

	/**
	 * @return the flavourId
	 */
	public String getFlavourId() {
		return flavourId;
	}

	/**
	 * @return the vnfInfoId
	 */
	public List<String> getVnfInfoId() {
		return vnfInfoId;
	}

	/**
	 * @return the pnfInfo
	 */
	public List<PnfInfo> getPnfInfo() {
		return pnfInfo;
	}

	/**
	 * @return the virtualLinkInfo
	 */
	public List<NsVirtualLinkInfo> getVirtualLinkInfo() {
		return virtualLinkInfo;
	}

	/**
	 * @return the vnffgInfo
	 */
	public List<VnffgInfo> getVnffgInfo() {
		return vnffgInfo;
	}

	/**
	 * @return the sapInfo
	 */
	public List<SapInfo> getSapInfo() {
		return sapInfo;
	}

	/**
	 * @return the nestedNsInfoId
	 */
	public List<String> getNestedNsInfoId() {
		return nestedNsInfoId;
	}

	/**
	 * @return the nsState
	 */
	public InstantiationState getNsState() {
		return nsState;
	}

	/**
	 * @return the nsScaleStatus
	 */
	public List<NsScaleInfo> getNsScaleStatus() {
		return nsScaleStatus;
	}

	/**
	 * @return the additionalAffinityOrAntiAffinityRule
	 */
	public List<AffinityRule> getAdditionalAffinityOrAntiAffinityRule() {
		return additionalAffinityOrAntiAffinityRule;
	}
	
	

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	public void addVnfInfo(String vnfInfo, int index, String vnfdId) {
		vnfInfoId.add(vnfInfo);
		vnfInfoMap.put(vnfInfo, index);
		vnfInfoVnfdIdMap.put(vnfInfo, vnfdId);
	}
	
	public void removeVnfInfo(String vnfInfo) {
		vnfInfoId.remove(vnfInfo);
		vnfInfoMap.remove(vnfInfo);
		vnfInfoVnfdIdMap.remove(vnfInfo);
	}
	
	@JsonIgnore
	public String getVnfInfoIdFromVnfdIdAndVnfIndex(String vnfdId, int vnfIndex) throws NotExistingEntityException {
		List<String> vnfInfoIdsWithSameVnfdId = new ArrayList<>();
		for (Map.Entry<String, String> e : vnfInfoVnfdIdMap.entrySet()) {
			if (e.getValue().equals(vnfdId)) vnfInfoIdsWithSameVnfdId.add(e.getKey());
		}
		if (vnfInfoIdsWithSameVnfdId.isEmpty()) throw new NotExistingEntityException("VNF info with VNFD ID " + vnfdId + " not found in NS instance " + nsInstanceId);
		List<String> target = new ArrayList<>();
		for (String vnfInfoId : vnfInfoIdsWithSameVnfdId) {
			if (vnfInfoMap.get(vnfInfoId) == vnfIndex) target.add(vnfInfoId);
		}
		if (target.size() != 1) throw new NotExistingEntityException("VNF info with VNFD ID " + vnfdId + " and VNF index " + vnfIndex + " not found in NS instance " + nsInstanceId);
		return target.get(0);
	}
	
	/**
	 * @return the vnfInfoMap
	 */
	@JsonIgnore
	public Map<String, Integer> getVnfInfoMap() {
		return vnfInfoMap;
	}
	
	@JsonIgnore
	public List<String> getVnfInfoIdFromVnfdId(String vnfdId) {
		List<String> infoIds = new ArrayList<>();
		for (Map.Entry<String, String> e : vnfInfoVnfdIdMap.entrySet()) {
			if (e.getValue().equals(vnfdId)) infoIds.add(e.getKey());
		}
		return infoIds;
	}

	/**
	 * @return the vnfInfoVnfdIdMap
	 */
	@JsonIgnore
	public Map<String, String> getVnfInfoVnfdIdMap() {
		return vnfInfoVnfdIdMap;
	}

	/**
	 * @param nsState the nsState to set
	 */
	public void setNsState(InstantiationState nsState) {
		this.nsState = nsState;
	}
	
	/**
	 * Set the NS instantiation level
	 * 	
	 * @param instantiationLevelId ID of the current NS instantiation level
	 */
	@JsonIgnore
	public void setInstantiationLevel(String instantiationLevelId) {
		NsScaleInfo nsScaleInfoItem = new NsScaleInfo(null, instantiationLevelId);
		List<NsScaleInfo> nsScaleInfo = new ArrayList<>();
		nsScaleInfo.add(nsScaleInfoItem);
		this.nsScaleStatus = nsScaleInfo;
	}
	
	
	
//	public void addSapInfo(String sapInstanceId, String sapdId,	String sapName,	String description,	String address) {
//		this.sapInfo.add(new SapInfo(sapInstanceId, sapdId, sapName, description, address));
//	}
//	
//	public void removeSapInfo(String sapInstanceId) throws NotExistingEntityException {
//		
//		for (SapInfo s : sapInfo) {
//			if (s.getSapInstanceId().equals(sapInstanceId)) {
//				sapInfo.remove(s);
//				return;
//			}
//		}
//		throw new NotExistingEntityException("SAP info for SAP instance ID " + sapInstanceId + " not found");
//	}
//	
	
	
	/**
	 * @return the monitoringDashboardUrl
	 */
	public String getMonitoringDashboardUrl() {
		return monitoringDashboardUrl;
	}

	/**
	 * @param monitoringDashboardUrl the monitoringDashboardUrl to set
	 */
	public void setMonitoringDashboardUrl(String monitoringDashboardUrl) {
		this.monitoringDashboardUrl = monitoringDashboardUrl;
	}

	public SapInfo getSapInfoFromSapdId(String sapdId) throws NotExistingEntityException {
		for (SapInfo s : sapInfo) {
			if (s.getSapdId().equals(sapdId)) return s;
		}
		throw new NotExistingEntityException("SAP info for SAPD ID " + sapdId + " not found");
	}
	
		
	/**
	 * @return the configurationParameters
	 */
	@JsonIgnore
	public Map<String, String> getConfigurationParameters() {
		return configurationParameters;
	}

	/**
	 * @param configurationParameters the configurationParameters to set
	 */
	@JsonIgnore 
	public void setConfigurationParameters(Map<String, String> configurationParameters) {
		if (configurationParameters != null) this.configurationParameters = configurationParameters;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsInstanceId == null) throw new MalformattedElementException("NS Info without NS instance ID");
		if (nsName == null) throw new MalformattedElementException("NS Info without NS name");
		if (description == null) throw new MalformattedElementException("NS Info without description");
		if (nsdId == null) throw new MalformattedElementException("NS Info without NSD ID");
		if (pnfInfo != null) {
			for (PnfInfo p: pnfInfo) p.isValid();
		}
		if (virtualLinkInfo != null) {
			for (NsVirtualLinkInfo vl: virtualLinkInfo) vl.isValid();
		}
		if (vnffgInfo != null) {
			for (VnffgInfo i : vnffgInfo) i.isValid();
		}
		if (sapInfo != null) {
			for (SapInfo i : sapInfo) i.isValid();
		}
		if (nsScaleStatus != null) {
			for (NsScaleInfo i : nsScaleStatus) i.isValid();
		}
		if (additionalAffinityOrAntiAffinityRule != null) {
			for (AffinityRule r : additionalAffinityOrAntiAffinityRule) r.isValid();
		}
	}

}
