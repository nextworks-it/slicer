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
import java.util.Collections;
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

/**
 * The NsProfile information element specifies 
 * the profile to be used for a nested NS.
 * 
 * Ref. IFA 014 v2.3.1 - 6.3.11
 * 
 * @author nextworks
 *
 */
@Entity
public class NsProfile implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsDf nsDf;
	
	private String nsProfileId;
	private String nsdId;
	private String nsDeploymentFlavourId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nsInstantiationLevelId;
	
	private int minNumberOfInstances;
	private int maxNumberOfInstances;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> affinityOrAntiaffinityGroupId = new ArrayList<>();
	
	@OneToMany(mappedBy = "nsProfile", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<NsVirtualLinkConnectivity> nsVirtualLinkConnectivity = new ArrayList<>();
	
	
	public NsProfile() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * 
	 * @param nsDf NS deployment flavour this NS profile refers to
	 * @param nsProfileId Identifies an NS profile
	 * @param nsdId Identifies the NSD applicable to NS instantiated according to this profile
	 * @param nsDfId Identifies the applicable network service DF within the scope of the NSD.
	 * @param nsInstantiationLevelId Identifies the NS level within the referenced NS DF to be used in the context of the parent NS instantiation. If not present, the default NS instantiation level as declared in the referenced NSD shall be used.
	 * @param minNumberOfInstances Minimum number of nested NS instances based on the referenced NSD that is permitted to exist for this NsProfile.
	 * @param maxNumberOfInstances Maximum number of nested NS instances based on the referenced NSD that is permitted to exist for this NsProfile.
	 * @param affinityOrAntiaffinityGroupId Identifies an affinity or anti-affinity group the NSs created according to this NsProfile belong to.
	 * @param nsVirtualLinkConnectivity Describes the connectivity of the NS this NS profile describes with the vLinks
	 */
	public NsProfile(
			NsDf nsDf,
			String nsProfileId,
			String nsdId,
			String nsDfId,
			String nsInstantiationLevelId,
			int minNumberOfInstances,
			int maxNumberOfInstances,
			List<String> affinityOrAntiaffinityGroupId,
			List<NsVirtualLinkConnectivity> nsVirtualLinkConnectivity
	) {
		this.nsDf = nsDf;
		this.nsProfileId = nsProfileId;
		this.nsdId = nsdId;
		this.nsDeploymentFlavourId = nsDfId;
		this.nsInstantiationLevelId = nsInstantiationLevelId;
		this.minNumberOfInstances = minNumberOfInstances;
		this.maxNumberOfInstances = maxNumberOfInstances;
		if (nsVirtualLinkConnectivity != null) this.nsVirtualLinkConnectivity = nsVirtualLinkConnectivity;
		if (affinityOrAntiaffinityGroupId != null) this.affinityOrAntiaffinityGroupId = affinityOrAntiaffinityGroupId;
	}

	/**
	 * Constructor
	 *
	 *
	 * @param nsDf NS deployment flavour this NS profile refers to
	 * @param nsProfileId Identifies an NS profile
	 * @param nsdId Identifies the NSD applicable to NS instantiated according to this profile
	 * @param nsDfId Identifies the applicable network service DF within the scope of the NSD.
	 * @param nsInstantiationLevelId Identifies the NS level within the referenced NS DF to be used in the context of the parent NS instantiation. If not present, the default NS instantiation level as declared in the referenced NSD shall be used.
	 * @param minNumberOfInstances Minimum number of nested NS instances based on the referenced NSD that is permitted to exist for this NsProfile.
	 * @param maxNumberOfInstances Maximum number of nested NS instances based on the referenced NSD that is permitted to exist for this NsProfile.
	 * @param affinityOrAntiaffinityGroupId Identifies an affinity or anti-affinity group the NSs created according to this NsProfile belong to.
	 */
	public NsProfile(
			NsDf nsDf,
			String nsProfileId,
			String nsdId,
			String nsDfId,
			String nsInstantiationLevelId,
			int minNumberOfInstances,
			int maxNumberOfInstances,
			List<String> affinityOrAntiaffinityGroupId
	) {
		this(
				nsDf,
				nsProfileId,
				nsdId,
				nsDfId,
				nsInstantiationLevelId,
				minNumberOfInstances,
				maxNumberOfInstances,
				affinityOrAntiaffinityGroupId,
				Collections.emptyList()
		);
	}

	/**
	 * @return the nsProfileId
	 */
	@JsonProperty("nsProfileId")
	public String getNsProfileId() {
		return nsProfileId;
	}

	/**
	 * @return the nsdId
	 */
	@JsonProperty("nsdId")
	public String getNsdId() {
		return nsdId;
	}

	/**
	 * @return the nsDfId
	 */
	@JsonProperty("nsDfId")
	public String getNsDeploymentFlavourId() {
		return nsDeploymentFlavourId;
	}

	/**
	 * @return the nsInstantiationLevelId
	 */
	@JsonProperty("nsInstantiationLevelId")
	public String getNsInstantiationLevelId() {
		return nsInstantiationLevelId;
	}

	/**
	 * @return the minNumberOfInstances
	 */
	@JsonProperty("minNumberOfInstances")
	public int getMinNumberOfInstances() {
		return minNumberOfInstances;
	}

	/**
	 * @return the maxNumberOfInstances
	 */
	@JsonProperty("maxNumberOfInstances")
	public int getMaxNumberOfInstances() {
		return maxNumberOfInstances;
	}

	/**
	 * @return the affinityOrAntiaffinityGroupId
	 */
	@JsonProperty("affinityOrAntiaffinityGroupId")
	public List<String> getAffinityOrAntiaffinityGroupId() {
		return affinityOrAntiaffinityGroupId;
	}
	
	/**
	 * @return the nsVirtualLinkConnectivity
	 */
	@JsonProperty("nsVirtualLinkConnectivity")
	public List<NsVirtualLinkConnectivity> getNsVirtualLinkConnectivity() {
		return nsVirtualLinkConnectivity;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.nsProfileId == null) throw new MalformattedElementException("NS profile without ID");
		if (this.nsdId == null) throw new MalformattedElementException("NS profile without NSD ID");
		if (this.nsDeploymentFlavourId == null) throw new MalformattedElementException("NS profile without NS DF ID");
		if (this.nsVirtualLinkConnectivity != null) {
			for (NsVirtualLinkConnectivity vlc : this.nsVirtualLinkConnectivity) vlc.isValid();
		}
	}

	public void setNsDf(NsDf nsDf) {
		this.nsDf = nsDf;
	}

	public void setNsProfileId(String nsProfileId) {
		this.nsProfileId = nsProfileId;
	}

	public void setNsdId(String nsdId) {
		this.nsdId = nsdId;
	}

	public void setNsDeploymentFlavourId(String nsDeploymentFlavourId) {
		this.nsDeploymentFlavourId = nsDeploymentFlavourId;
	}

	public void setNsInstantiationLevelId(String nsInstantiationLevelId) {
		this.nsInstantiationLevelId = nsInstantiationLevelId;
	}

	public void setMinNumberOfInstances(int minNumberOfInstances) {
		this.minNumberOfInstances = minNumberOfInstances;
	}

	public void setMaxNumberOfInstances(int maxNumberOfInstances) {
		this.maxNumberOfInstances = maxNumberOfInstances;
	}

	public void setAffinityOrAntiaffinityGroupId(List<String> affinityOrAntiaffinityGroupId) {
		this.affinityOrAntiaffinityGroupId = affinityOrAntiaffinityGroupId;
	}

	public void setNsVirtualLinkConnectivity(List<NsVirtualLinkConnectivity> nsVirtualLinkConnectivity) {
		this.nsVirtualLinkConnectivity = nsVirtualLinkConnectivity;
	}
}
