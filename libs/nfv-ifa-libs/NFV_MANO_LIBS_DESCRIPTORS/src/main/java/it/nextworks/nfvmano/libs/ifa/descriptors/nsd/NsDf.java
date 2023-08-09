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
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.AffinityOrAntiAffinityGroup;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VirtualLinkProfile;

/**
 * The NsDf (network service deployment flavour) information element 
 * specifies the properties of a variant of an NS.
 * Ref. IFA 014 v2.3.1 - 6.3.2
 * 
 * @author nextworks
 *
 */
@Entity
public class NsDf implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Nsd nsd;
	
	private String nsDfId;
	private String flavourKey;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsDf", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<VnfProfile> vnfProfile = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsDf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<PnfProfile> pnfProfile = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsDf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VirtualLinkProfile> virtualLinkProfile = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsDf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NsScalingAspect> scalingAspect = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<AffinityOrAntiAffinityGroup> affinityOrAntiAffinityGroup = new ArrayList<>();
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "nsDf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<NsLevel> nsInstantiationLevel = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String defaultNsInstantiationLevelId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsDf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NsProfile> nsProfile = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "nsDf", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Dependencies> dependencies = new ArrayList<>();
	
	public NsDf() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsd Network Service Descriptor this deployment flavour refers to
	 * @param nsDfId Identifies this NsDf information element. It identifies a NS DF within the NSD.
	 * @param flavourKey Assurance parameter against which this flavour is being described.
	 * @param affinityOrAntiAffinityGroup Specifies affinity or anti-affinity Group relationship applicable between the VNF instances created using different VNFDs, the Virtual Link instances created using different NsVirtualLinkDescs or the nested NS instances created using different NSDs in the same affinity or anti-affinity group.
	 * @param defaultNsInstantiationLevelId Identifies the NS level which represents the default NS instantiation level for this DF.
	 *
	 */
	public NsDf(Nsd nsd,
			String nsDfId,
			String flavourKey,
			List<AffinityOrAntiAffinityGroup> affinityOrAntiAffinityGroup,
			String defaultNsInstantiationLevelId) {
		this.nsd = nsd;
		this.nsDfId = nsDfId;
		this.flavourKey = flavourKey;
		if (affinityOrAntiAffinityGroup != null) this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
		this.defaultNsInstantiationLevelId = defaultNsInstantiationLevelId;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsd Network Service Descriptor this deployment flavour refers to
	 * @param nsDfId Identifies this NsDf information element. It identifies a NS DF within the NSD.
	 * @param flavourKey Assurance parameter against which this flavour is being described.
	 * @param vnfProfile VNF profile to be used for the NS flavour
	 * @param pnfProfile PNF profile to be used for the NS flavour
	 * @param virtualLinkProfile VL profile to be used for the NS flavour
	 * @param scalingAspect The scaling aspects supported by this DF of the NS.
	 * @param affinityOrAntiAffinityGroup Specifies affinity or anti-affinity Group relationship applicable between the VNF instances created using different VNFDs, the Virtual Link instances created using different NsVirtualLinkDescs or the nested NS instances created using different NSDs in the same affinity or anti-affinity group.
	 * @param nsInstantiationLevel Describes the details of an NS level.
	 * @param defaultNsInstantiationLevelId Identifies the NS level which represents the default NS instantiation level for this DF.
	 * @param nsProfile Specifies a NS Profile supported by this NS DF.
	 * @param dependencies Specifies the order in which instances of the VNFs and/or nested NSs have to be created.
	 */
	public NsDf(Nsd nsd,
			String nsDfId,
			String flavourKey,
			List<VnfProfile> vnfProfile,
			List<PnfProfile> pnfProfile,
			List<VirtualLinkProfile> virtualLinkProfile,
			List<NsScalingAspect> scalingAspect,
			List<AffinityOrAntiAffinityGroup> affinityOrAntiAffinityGroup,
			List<NsLevel> nsInstantiationLevel,
			String defaultNsInstantiationLevelId,
			List<NsProfile> nsProfile,
			List<Dependencies> dependencies) {
		this.nsd = nsd;
		this.nsDfId = nsDfId;
		this.flavourKey = flavourKey;
		if (vnfProfile != null) this.vnfProfile = vnfProfile;
		if (pnfProfile != null) this.pnfProfile = pnfProfile;
		if (virtualLinkProfile != null) this.virtualLinkProfile = virtualLinkProfile;
		if (scalingAspect != null) this.scalingAspect = scalingAspect;
		if (affinityOrAntiAffinityGroup != null) this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
		if (nsInstantiationLevel != null) this.nsInstantiationLevel = nsInstantiationLevel;
		this.defaultNsInstantiationLevelId = defaultNsInstantiationLevelId;
		if (nsProfile != null) this.nsProfile = nsProfile;
		if (dependencies != null) this.dependencies = dependencies;
	}
	
	

	/**
	 * @return the nsDfId
	 */
	@JsonProperty("nsDfId")
	public String getNsDfId() {
		return nsDfId;
	}

	/**
	 * @return the flavourKey
	 */
	@JsonProperty("flavourKey")
	public String getFlavourKey() {
		return flavourKey;
	}

	/**
	 * @return the vnfProfile
	 */
	@JsonProperty("vnfProfile")
	public List<VnfProfile> getVnfProfile() {
		return vnfProfile;
	}

	/**
	 * @return the pnfProfile
	 */
	@JsonProperty("pnfProfile")
	public List<PnfProfile> getPnfProfile() {
		return pnfProfile;
	}

	/**
	 * @return the virtualLinkProfile
	 */
	@JsonProperty("virtualLinkProfile")
	public List<VirtualLinkProfile> getVirtualLinkProfile() {
		return virtualLinkProfile;
	}

	/**
	 * @return the scalingAspect
	 */
	@JsonProperty("scalingAspect")
	public List<NsScalingAspect> getScalingAspect() {
		return scalingAspect;
	}

	/**
	 * @return the affinityOrAntiAffinityGroup
	 */
	@JsonProperty("affinityOrAntiAffinityGroup")
	public List<AffinityOrAntiAffinityGroup> getAffinityOrAntiAffinityGroup() {
		return affinityOrAntiAffinityGroup;
	}

	/**
	 * @return the nsInstantiationLevel
	 */
	@JsonProperty("nsInstantiationLevel")
	public List<NsLevel> getNsInstantiationLevel() {
		return nsInstantiationLevel;
	}

	/**
	 * @return the defaultNsInstantiationLevelId
	 */
	@JsonProperty("defaultNsInstantiationLevelId")
	public String getDefaultNsInstantiationLevelId() {
		return defaultNsInstantiationLevelId;
	}

	/**
	 * @return the nsProfile
	 */
	@JsonProperty("nsProfile")
	public List<NsProfile> getNsProfile() {
		return nsProfile;
	}

	/**
	 * @return the dependencies
	 */
	@JsonProperty("dependencies")
	public List<Dependencies> getDependencies() {
		return dependencies;
	}
	
	

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.nsDfId == null) throw new MalformattedElementException("NS DF without ID");
		if (this.flavourKey == null) throw new MalformattedElementException("NS DF without flavour key");
		if (this.vnfProfile != null) {
			for (VnfProfile pr : this.vnfProfile) pr.isValid();
		}
		if (this.pnfProfile != null) {
			for (PnfProfile pr : this.pnfProfile) pr.isValid();
		}
		if (this.virtualLinkProfile != null) {
			for (VirtualLinkProfile pr : this.virtualLinkProfile) pr.isValid();
		}
		if (this.scalingAspect != null) {
			for (NsScalingAspect sa : this.scalingAspect) sa.isValid();
		}
		if (this.affinityOrAntiAffinityGroup != null) {
			for (AffinityOrAntiAffinityGroup gr : this.affinityOrAntiAffinityGroup) gr.isValid();
		}
		if ((this.nsInstantiationLevel == null) || (this.nsInstantiationLevel.isEmpty())) {
			throw new MalformattedElementException("NS DF without instantiation level");
		} else {
			for (NsLevel l : this.nsInstantiationLevel) l.isValid();
		}
		if ((this.nsInstantiationLevel.size()>1) && (this.defaultNsInstantiationLevelId == null))
			throw new MalformattedElementException("NS DF without default NS instantiation level ID, but multiple NS levels");
		if (this.nsProfile != null) {
			for (NsProfile pr : this.nsProfile) pr.isValid();
		}
		if (this.dependencies != null) {
			for (Dependencies d : this.dependencies) d.isValid();
		}
	}
	
	@JsonIgnore
	public NsLevel getNsLevel(String nsLevelId) throws NotExistingEntityException {
		for (NsLevel l : nsInstantiationLevel) {
			if (l.getNsLevelId().equals(nsLevelId)) return l;
		}
		throw new NotExistingEntityException("NS level " + nsLevelId + " not found in DF " + nsDfId);
	}
	
	@JsonIgnore
	public NsLevel getDefaultInstantiationLevel() throws NotExistingEntityException {
		if ((nsInstantiationLevel == null) || (nsInstantiationLevel.isEmpty())) {
			throw new NotExistingEntityException("DF " + nsDfId + " without NS levels");
		} else if (nsInstantiationLevel.size()==1) {
			return nsInstantiationLevel.get(0);
		} else {
			if (defaultNsInstantiationLevelId == null) {
				throw new NotExistingEntityException("DF " + nsDfId + " without default NS level and with multiple NS levels");
			}
			return getNsLevel(defaultNsInstantiationLevelId);
		}
	}
	
	@JsonIgnore
	public VnfProfile getVnfProfile(String vnfdId) throws NotExistingEntityException {
		for (VnfProfile vp : vnfProfile) {
			if (vp.getVnfProfileId().equals(vnfdId)) return vp;
		}
		throw new NotExistingEntityException("VNF profile for VNFD ID " + vnfdId + " not found");
	}
	
	public VirtualLinkProfile getVirtualLinkProfile(String vlProfileId) throws NotExistingEntityException {
		for (VirtualLinkProfile vlp : virtualLinkProfile) {
			if (vlp.getVirtualLinkProfileId().equals(vlProfileId)) return vlp;
		}
		throw new NotExistingEntityException("VL profile for VL profile ID " + vlProfileId + " not found");
	}

	public void setNsd(Nsd nsd) {
		this.nsd = nsd;
	}

	public void setNsDfId(String nsDfId) {
		this.nsDfId = nsDfId;
	}

	public void setFlavourKey(String flavourKey) {
		this.flavourKey = flavourKey;
	}

	public void setVnfProfile(List<VnfProfile> vnfProfile) {
		this.vnfProfile = vnfProfile;
	}

	public void setPnfProfile(List<PnfProfile> pnfProfile) {
		this.pnfProfile = pnfProfile;
	}

	public void setVirtualLinkProfile(List<VirtualLinkProfile> virtualLinkProfile) {
		this.virtualLinkProfile = virtualLinkProfile;
	}

	public void setScalingAspect(List<NsScalingAspect> scalingAspect) {
		this.scalingAspect = scalingAspect;
	}

	public void setAffinityOrAntiAffinityGroup(List<AffinityOrAntiAffinityGroup> affinityOrAntiAffinityGroup) {
		this.affinityOrAntiAffinityGroup = affinityOrAntiAffinityGroup;
	}

	public void setNsInstantiationLevel(List<NsLevel> nsInstantiationLevel) {
		this.nsInstantiationLevel = nsInstantiationLevel;
	}

	public void setDefaultNsInstantiationLevelId(String defaultNsInstantiationLevelId) {
		this.defaultNsInstantiationLevelId = defaultNsInstantiationLevelId;
	}

	public void setNsProfile(List<NsProfile> nsProfile) {
		this.nsProfile = nsProfile;
	}

	public void setDependencies(List<Dependencies> dependencies) {
		this.dependencies = dependencies;
	}
}
