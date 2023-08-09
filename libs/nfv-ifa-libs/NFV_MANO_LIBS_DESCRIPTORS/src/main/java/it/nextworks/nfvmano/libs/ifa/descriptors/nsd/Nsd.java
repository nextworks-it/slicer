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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.LifeCycleManagementScript;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualLinkProfile;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.UserAccessInfo;

/**
 * The NSD information element is a deployment template 
 * whose instances are used by the NFVO for the lifecycle management of NSs.
 * Ref. IFA 014 v2.3.1 - 6.2.2
 * 
 * @author nextworks
 *
 */
@Entity
public class Nsd implements DescriptorInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String nsdIdentifier;
	private String designer;
	private String version;
	private String nsdName;
	private String nsdInvariantId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> nestedNsdId = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> vnfdId = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> pnfdId = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsd", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Sapd> sapd = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsd", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NsVirtualLinkDesc> virtualLinkDesc = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsd", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Vnffgd> vnffgd = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsd", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MonitoredData> monitoredInfo = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsd", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NsAutoscalingRule> autoScalingRule = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsd", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<LifeCycleManagementScript> lifeCycleManagementScript = new ArrayList<>();
	
	@OneToMany(mappedBy = "nsd", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NsDf> nsDf = new ArrayList<>();
	
	@Embedded
	private SecurityParameters security;
	
	public Nsd() {
		//JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsdIdentifier	Identifier of this NSD information element. It Globally uniquely identifies an instance of the NSD.				
	 * @param designer Identifies the designer of the NSD.
	 * @param version Identifies the version of the NSD
	 * @param nsdName Provides the human readable name of the NSD.
	 * @param nsdInvariantId Identifies an NSD in a version independent manner. This attribute is invariant across versions of NSD.
	 * @param nestedNsdId References the NSD of a constituent nested NS.
	 * @param vnfdId References the VNFD of a constituent VNF.
	 * @param pnfdId References the PNFD of a constituent PNF.
	 * @param security Provides a signature to prevent tampering.
	 */
	public Nsd(String nsdIdentifier, 
			String designer,
			String version,
			String nsdName,
			String nsdInvariantId,
			List<String> nestedNsdId,
			List<String> vnfdId,
			List<String> pnfdId,
			SecurityParameters security) {
		this.nsdIdentifier = nsdIdentifier;
		this.designer = designer;
		this.version = version;
		this.nsdName = nsdName;
		this.nsdInvariantId = nsdInvariantId;
		if (nestedNsdId != null) this.nestedNsdId = nestedNsdId;
		if (vnfdId != null) this.vnfdId = vnfdId;
		if (pnfdId != null) this.pnfdId = pnfdId;
		this.security = security;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsdIdentifier	Identifier of this NSD information element. It Globally uniquely identifies an instance of the NSD.				
	 * @param designer Identifies the designer of the NSD.
	 * @param version Identifies the version of the NSD
	 * @param nsdName Provides the human readable name of the NSD.
	 * @param nsdInvariantId Identifies an NSD in a version independent manner. This attribute is invariant across versions of NSD.
	 * @param nestedNsdId References the NSD of a constituent nested NS.
	 * @param vnfdId References the VNFD of a constituent VNF.
	 * @param pnfdId References the PNFD of a constituent PNF.
	 * @param sapd Provides the descriptor of a service access point of the network service.
	 * @param virtualLinkDesc Provides the constituent VLDs.
	 * @param vnffgd Provides the descriptors of the applicable forwarding graphs.
	 * @param monitoredInfo Identifies either a virtualised resource-related performance metric or a VNF Indicator.
	 * @param autoScalingRule Specifies a rule to trigger a scaling action on a NS instantiated according to the NSD.
	 * @param lifeCycleManagementScript Provides a life cycle management script written in a Domain Specific Language (DSL).
	 * @param nsDf Identifies a Deployment Flavour within the scope of an NSD.
	 * @param security Provides a signature to prevent tampering.
	 */
	public Nsd(String nsdIdentifier, 
			String designer,
			String version,
			String nsdName,
			String nsdInvariantId,
			List<String> nestedNsdId,
			List<String> vnfdId,
			List<String> pnfdId,
			List<Sapd> sapd,
			List<NsVirtualLinkDesc> virtualLinkDesc,
			List<Vnffgd> vnffgd,
			List<MonitoredData> monitoredInfo,
			List<NsAutoscalingRule> autoScalingRule,
			List<LifeCycleManagementScript> lifeCycleManagementScript,
			List<NsDf> nsDf,
			SecurityParameters security) {
		this.nsdIdentifier = nsdIdentifier;
		this.designer = designer;
		this.version = version;
		this.nsdName = nsdName;
		this.nsdInvariantId = nsdInvariantId;
		if (nestedNsdId != null) this.nestedNsdId = nestedNsdId;
		if (vnfdId != null) this.vnfdId = vnfdId;
		if (pnfdId != null) this.pnfdId = pnfdId;
		if (sapd != null) this.sapd = sapd;
		if (virtualLinkDesc != null) this.virtualLinkDesc = virtualLinkDesc;
		if (vnffgd != null) this.vnffgd = vnffgd;
		if (monitoredInfo != null) this.monitoredInfo = monitoredInfo;
		if (autoScalingRule != null) this.autoScalingRule = autoScalingRule;
		if (lifeCycleManagementScript != null) this.lifeCycleManagementScript = lifeCycleManagementScript;
		if (this.nsDf != null) this.nsDf = nsDf;
		this.security = security;
	}
	
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the nsdIdentifier
	 */
	@JsonProperty("nsdIdentifier")
	public String getNsdIdentifier() {
		return nsdIdentifier;
	}

	/**
	 * @return the designer
	 */
	@JsonProperty("designer")
	public String getDesigner() {
		return designer;
	}

	/**
	 * @return the version
	 */
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	/**
	 * @return the nsdName
	 */
	@JsonProperty("nsdName")
	public String getNsdName() {
		return nsdName;
	}

	/**
	 * @return the nsdInvariantId
	 */
	@JsonProperty("nsdInvariantId")
	public String getNsdInvariantId() {
		return nsdInvariantId;
	}

	/**
	 * @return the nestedNsdId
	 */
	@JsonProperty("nestedNsdId")
	public List<String> getNestedNsdId() {
		return nestedNsdId;
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
	 * @return the sapd
	 */
	@JsonProperty("sapd")
	public List<Sapd> getSapd() {
		return sapd;
	}

	/**
	 * @return the virtualLinkDesc
	 */
	@JsonProperty("virtualLinkDesc")
	public List<NsVirtualLinkDesc> getVirtualLinkDesc() {
		return virtualLinkDesc;
	}

	/**
	 * @return the vnffgd
	 */
	@JsonProperty("vnffgd")
	public List<Vnffgd> getVnffgd() {
		return vnffgd;
	}

	/**
	 * @return the monitoredInfo
	 */
	@JsonProperty("monitoredInfo")
	public List<MonitoredData> getMonitoredInfo() {
		return monitoredInfo;
	}

	/**
	 * @return the autoScalingRule
	 */
	@JsonProperty("autoScalingRule")
	public List<NsAutoscalingRule> getAutoScalingRule() {
		return autoScalingRule;
	}

	/**
	 * @return the lifeCycleManagementScript
	 */
	@JsonProperty("lifeCycleManagementScript")
	public List<LifeCycleManagementScript> getLifeCycleManagementScript() {
		return lifeCycleManagementScript;
	}

	/**
	 * @return the nsDf
	 */
	@JsonProperty("nsDf")
	public List<NsDf> getNsDf() {
		return nsDf;
	}

	/**
	 * @return the security
	 */
	@JsonProperty("security")
	public SecurityParameters getSecurity() {
		return security;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.nsdIdentifier == null) throw new MalformattedElementException("NSD without NDS ID");
		if (this.designer == null) throw new MalformattedElementException("NSD without designer");
		if (this.version == null) throw new MalformattedElementException("NSD without version");
		if (this.nsdName == null) throw new MalformattedElementException("NSD without NDS name");
		if (this.nsdInvariantId == null) throw new MalformattedElementException("NSD without NDS Invariant ID");
		if ((this.nestedNsdId.isEmpty()) && (this.vnfdId.isEmpty())) throw new MalformattedElementException("NSD with nested NSD and VNFD ID both empty");
		if (this.sapd != null) {
			for (Sapd s : this.sapd) s.isValid();
		}
		if (this.virtualLinkDesc != null) {
			for (NsVirtualLinkDesc vl : this.virtualLinkDesc) vl.isValid();
		}
		if (this.vnffgd != null) {
			for (Vnffgd fg : this.vnffgd) fg.isValid();
		}
		if (this.monitoredInfo != null) {
			for (MonitoredData mi : this.monitoredInfo) mi.isValid();
		}
		if (this.autoScalingRule != null) {
			for (NsAutoscalingRule rule : this.autoScalingRule) rule.isValid();
		}
		if ((this.nsDf == null) || (this.nsDf.isEmpty())) {
			throw new MalformattedElementException("NSD without deployment flavour");
		} else {
			for (NsDf df : this.nsDf) df.isValid();
		}
		if (this.lifeCycleManagementScript != null) {
			for (LifeCycleManagementScript lcmScript : this.lifeCycleManagementScript) lcmScript.isValid();
		}
		if (this.security == null) {
			throw new MalformattedElementException("NSD without security parameters");
		} else this.security.isValid();
	}
	
	@JsonIgnore
	/**
	 * Returns the NS Deployment Flavour with the given ID
	 * 
	 * @param dfId ID of the deployment flavour
	 * @return the NS deployment flavour
	 * @throws NotExistingEntityException if the deployment flavour with the given ID does not exist
	 */
	public NsDf getNsDeploymentFlavour(String dfId) throws NotExistingEntityException {
		for (NsDf df : nsDf) {
			if (df.getNsDfId().equals(dfId)) return df;
		}
		throw new NotExistingEntityException("NS Deployment Flavour " + dfId + " not found.");
	}
	
	@JsonIgnore
	/**
	 * Returns the NS SAP attached to the NS VL with the given VLD ID.
	 * 
	 * @param vldId ID of the VL attached to the requested SAP.
	 * @return the NS SAP.
	 */
	public Sapd getSapForVl(String vldId) {
		for (Sapd s : sapd) {
			if (s.getNsVirtualLinkDescId().equals(vldId)) return s;
		}
		return null;
	}
	
	@JsonIgnore
	/**
	 * Returns the NS SAP for the given SAP ID.
	 * 
	 * @param sapdId the ID of the requested SAP
	 * @return the NS SAP
	 */
	public Sapd getSapFromSapId(String sapdId) {
		for (Sapd s: sapd) {
			if (s.getCpdId().equals(sapdId)) return s;
		}
		return null;
	}
	
	/**
	 * Returns a map with key = VNFD_ID and value a map with keys = [VNFD_ID, VNF_DF_ID, VNF_INSTANCES, 
	 * VNF_INSTANTIATION_LEVEL] and related values for the VNFs associated 
	 * to the NSD for the given NS flavour ID and NS instantiation level
	 * 
	 * @param nsFlavourId the NS Flavour ID
	 * @param nsInstantiationLevel the NS Instantiation level ID
	 * @return a map with key = VNFD_ID and value a map with keys = [VNFD_ID, VNF_DF_ID, VNF_INSTANCES, VNF_INSTANTIATION_LEVEL]
	 * @throws NotExistingEntityException if the deployment flavour or the NS instantiation level with the given IDs do not exist
	 */
	@JsonIgnore
	public Map<String,Map<String, String>> getVnfdDataFromFlavour(String nsFlavourId, String nsInstantiationLevel)
			throws NotExistingEntityException {
		Map<String,Map<String, String>> vnfdIds = new HashMap<>();
		NsDf nsDeploymentFlavour = getNsDeploymentFlavour(nsFlavourId);
		NsLevel nsLevel;
		if (nsInstantiationLevel != null) nsLevel = nsDeploymentFlavour.getNsLevel(nsInstantiationLevel);
		else nsLevel = nsDeploymentFlavour.getDefaultInstantiationLevel();
		List<VnfToLevelMapping> vnfLevels = nsLevel.getVnfToLevelMapping();
		for (VnfToLevelMapping vnf : vnfLevels) {
			VnfProfile vnfProfile = nsDeploymentFlavour.getVnfProfile(vnf.getVnfProfileId());
			int numInstances = vnf.getNumberOfInstances();
			String vnfdId = vnfProfile.getVnfdId();
			String vnfFlavourId = vnfProfile.getFlavourId();
			String vnfInstantiationLevel = vnfProfile.getInstantiationLevel();
			
			Map<String,String> vnfdDetail = new HashMap<>();
			vnfdDetail.put("VNFD_ID", vnfdId);
			vnfdDetail.put("VNF_DF_ID", vnfFlavourId);
			vnfdDetail.put("VNF_INSTANCES", String.valueOf(numInstances));
			vnfdDetail.put("VNF_INSTANTIATION_LEVEL", vnfInstantiationLevel);
			
			vnfdIds.put(vnfdId, vnfdDetail);
		}
		
		return vnfdIds;
	}
	
	
	@JsonIgnore
	public List<UserAccessInfo> getUserAccessInfo(String nsFlavourId, String nsInstantiationLevel) 
		throws NotExistingEntityException {
		
		List<UserAccessInfo> uais = new ArrayList<>(); 
		
		//Read the right NS level
		NsDf nsDeploymentFlavour = getNsDeploymentFlavour(nsFlavourId);
		NsLevel nsLevel;
		if (nsInstantiationLevel != null) nsLevel = nsDeploymentFlavour.getNsLevel(nsInstantiationLevel);
		else nsLevel = nsDeploymentFlavour.getDefaultInstantiationLevel();
		
		//Read the VNFs in the NS level and verify if they are connected to a VL associated to a SAP. This info is in the VNF profile
		List<VnfToLevelMapping> vnfLevels = nsLevel.getVnfToLevelMapping();
		for (VnfToLevelMapping vnf : vnfLevels) {
			VnfProfile vnfProfile = nsDeploymentFlavour.getVnfProfile(vnf.getVnfProfileId());
			String vnfdId = vnfProfile.getVnfdId();
			
			//Get all the VLs the VNF is connected to.
			List<NsVirtualLinkConnectivity> nsVlConns = vnfProfile.getNsVirtualLinkConnectivity();
			for (NsVirtualLinkConnectivity nsVlConn : nsVlConns) {
				
				//For each of the VLs connected to the VNF, it checks if the VL is connected to a SAP
				String vlProfileId = nsVlConn.getVirtualLinkProfileId();
				VirtualLinkProfile vlp = nsDeploymentFlavour.getVirtualLinkProfile(vlProfileId);
				String vldId = vlp.getVirtualLinkDescId();
				Sapd sapd = getSapForVl(vldId);
				if (sapd != null) {
					//This means that the VL of the current VL profile is associated to a SAP
					UserAccessInfo uai = new UserAccessInfo(sapd.getCpdId(), vnfdId, null, nsVlConn.getCpdId().get(0), null);
					uais.add(uai);
				}
			}
		}
		
		return uais;
	}

	public void setNsdIdentifier(String nsdIdentifier) {
		this.nsdIdentifier = nsdIdentifier;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setNsdName(String nsdName) {
		this.nsdName = nsdName;
	}

	public void setNsdInvariantId(String nsdInvariantId) {
		this.nsdInvariantId = nsdInvariantId;
	}

	public void setNestedNsdId(List<String> nestedNsdId) {
		this.nestedNsdId = nestedNsdId;
	}

	public void setVnfdId(List<String> vnfdId) {
		this.vnfdId = vnfdId;
	}

	public void setPnfdId(List<String> pnfdId) {
		this.pnfdId = pnfdId;
	}

	public void setSapd(List<Sapd> sapd) {
		this.sapd = sapd;
	}

	public void setVirtualLinkDesc(List<NsVirtualLinkDesc> virtualLinkDesc) {
		this.virtualLinkDesc = virtualLinkDesc;
	}

	public void setVnffgd(List<Vnffgd> vnffgd) {
		this.vnffgd = vnffgd;
	}

	public void setMonitoredInfo(List<MonitoredData> monitoredInfo) {
		this.monitoredInfo = monitoredInfo;
	}

	public void setAutoScalingRule(List<NsAutoscalingRule> autoScalingRule) {
		this.autoScalingRule = autoScalingRule;
	}

	public void setLifeCycleManagementScript(List<LifeCycleManagementScript> lifeCycleManagementScript) {
		this.lifeCycleManagementScript = lifeCycleManagementScript;
	}

	public void setNsDf(List<NsDf> nsDf) {
		this.nsDf = nsDf;
	}

	public void setSecurity(SecurityParameters security) {
		this.security = security;
	}
}
