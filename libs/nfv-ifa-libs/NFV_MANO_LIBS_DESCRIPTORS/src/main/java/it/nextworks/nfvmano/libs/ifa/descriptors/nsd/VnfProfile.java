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
import it.nextworks.nfvmano.libs.ifa.common.elements.AffinityRule;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VnfProfile information element specifies a profile for 
 * instantiating VNFs of a particular NS DF according to a
 * specific VNFD and VNF DF.
 * 
 * Ref. IFA 014 v2.3.1 - 6.3.3
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfProfile implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsDf nsDf;
	
	private String vnfProfileId;
	private String vnfdId;
	private String flavourId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String instantiationLevel;
	
	private int minNumberOfInstances;
	private int maxNumberOfInstances;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<AffinityRule> localAffinityOrAntiAffinityRule = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> affinityOrAntiAffinityGroupId = new ArrayList<>();
	
	@OneToMany(mappedBy = "vnfProfile", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<NsVirtualLinkConnectivity> nsVirtualLinkConnectivity = new ArrayList<>();
	
	
	//This is an extension OUT of the standard
	@OneToMany(mappedBy = "vnfProfile", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<VnfLCMScripts> script = new ArrayList<>();
	
	
	public VnfProfile() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsDf	NS deployment flavour this VNF profile refers to
	 * @param vnfProfileId Identifier of this vnfProfile information element. It uniquely identifies a VnfProfile.
	 * @param vnfdId References a VNFD.
	 * @param flavourId Identifies a flavour within the VNFD.
	 * @param instantiationLevel Identifier of the instantiation level of the VNF DF to be used for instantiation. If not present, the default instantiation level as declared in the VNFD shall be used.
	 * @param minNumberOfInstances Minimum number of instances of the VNF based on this VNFD that is permitted to exist for this VnfProfile.
	 * @param maxNumberOfInstances Maximum number of instances of the VNF based on this VNFD that is permitted to exist for this VnfProfile.
	 * @param localAffinityOrAntiAffinityRule Specifies affinity and anti-affinity rules applicable between VNF instances created from this profile.
	 * @param affinityOrAntiAffinityGroupId Identifier(s) of the affinity or anti-affinity group(s) the VnfProfile belongs to.
	 * @param nsVirtualLinkConnectivity Defines the connection information of the VNF, it contains connection relationship between a VNF connection point and a NS virtual Link.
	 */
	public VnfProfile(NsDf nsDf,
			String vnfProfileId,
			String vnfdId,
			String flavourId,
			String instantiationLevel,
			int minNumberOfInstances,
			int maxNumberOfInstances,
			List<AffinityRule> localAffinityOrAntiAffinityRule,
			List<String> affinityOrAntiAffinityGroupId,
			List<NsVirtualLinkConnectivity> nsVirtualLinkConnectivity) {
		this.nsDf = nsDf;
		this.vnfProfileId = vnfProfileId;
		this.vnfdId = vnfdId;
		this.flavourId = flavourId;
		this.instantiationLevel = instantiationLevel;
		this.minNumberOfInstances = minNumberOfInstances;
		this.maxNumberOfInstances = maxNumberOfInstances;
		if (localAffinityOrAntiAffinityRule != null) this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
		if (affinityOrAntiAffinityGroupId != null) this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
		if (nsVirtualLinkConnectivity != null) this.nsVirtualLinkConnectivity = nsVirtualLinkConnectivity;
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsDf	NS deployment flavour this VNF profile refers to
	 * @param vnfProfileId Identifier of this vnfProfile information element. It uniquely identifies a VnfProfile.
	 * @param vnfdId References a VNFD.
	 * @param flavourId Identifies a flavour within the VNFD.
	 * @param instantiationLevel Identifier of the instantiation level of the VNF DF to be used for instantiation. If not present, the default instantiation level as declared in the VNFD shall be used.
	 * @param minNumberOfInstances Minimum number of instances of the VNF based on this VNFD that is permitted to exist for this VnfProfile.
	 * @param maxNumberOfInstances Maximum number of instances of the VNF based on this VNFD that is permitted to exist for this VnfProfile.
	 * @param localAffinityOrAntiAffinityRule Specifies affinity and anti-affinity rules applicable between VNF instances created from this profile.
	 * @param affinityOrAntiAffinityGroupId Identifier(s) of the affinity or anti-affinity group(s) the VnfProfile belongs to.
	 */
	public VnfProfile(NsDf nsDf,
			String vnfProfileId,
			String vnfdId,
			String flavourId,
			String instantiationLevel,
			int minNumberOfInstances,
			int maxNumberOfInstances,
			List<AffinityRule> localAffinityOrAntiAffinityRule,
			List<String> affinityOrAntiAffinityGroupId) {
		this.nsDf = nsDf;
		this.vnfProfileId = vnfProfileId;
		this.vnfdId = vnfdId;
		this.flavourId = flavourId;
		this.instantiationLevel = instantiationLevel;
		this.minNumberOfInstances = minNumberOfInstances;
		this.maxNumberOfInstances = maxNumberOfInstances;
		if (localAffinityOrAntiAffinityRule != null) this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
		if (affinityOrAntiAffinityGroupId != null) this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
	}
	

	/**
	 * @return the vnfProfileId
	 */
	@JsonProperty("vnfProfileId")
	public String getVnfProfileId() {
		return vnfProfileId;
	}

	/**
	 * @return the vnfdId
	 */
	@JsonProperty("vnfdId")
	public String getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the flavourId
	 */
	@JsonProperty("flavourId")
	public String getFlavourId() {
		return flavourId;
	}

	/**
	 * @return the instantiationLevel
	 */
	@JsonProperty("instantiationLevel")
	public String getInstantiationLevel() {
		return instantiationLevel;
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
	 * @return the localAffinityOrAntiAffinityRule
	 */
	@JsonProperty("localAffinityOrAntiAffinityRule")
	public List<AffinityRule> getLocalAffinityOrAntiAffinityRule() {
		return localAffinityOrAntiAffinityRule;
	}

	/**
	 * @return the affinityOrAntiAffinityGroupId
	 */
	@JsonProperty("affinityOrAntiAffinityGroupId")
	public List<String> getAffinityOrAntiAffinityGroupId() {
		return affinityOrAntiAffinityGroupId;
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
		if (this.vnfProfileId == null) throw new MalformattedElementException("VNF profile without profile ID");
		if (this.vnfdId == null) throw new MalformattedElementException("VNF profile without VNFD ID");
		if (this.flavourId == null) throw new MalformattedElementException("VNF profile without VNF flavour ID");
		if (this.maxNumberOfInstances < this.minNumberOfInstances) throw new MalformattedElementException("VNF profile with unacceptable min/max instances boundaries");
		if ((this.nsVirtualLinkConnectivity == null) || (this.nsVirtualLinkConnectivity.isEmpty())) { 
			throw new MalformattedElementException("VNF profile without NS VL connectivity");
		} else {
			for (NsVirtualLinkConnectivity vlc : this.nsVirtualLinkConnectivity) vlc.isValid();
		}
		if (this.localAffinityOrAntiAffinityRule != null) {
			for (AffinityRule rule : this.localAffinityOrAntiAffinityRule) rule.isValid();
		}
	}
	
	/**
	 * @return the script
	 */
	public List<VnfLCMScripts> getScript() {
		return script;
	}

	public void setNsDf(NsDf nsDf) {
		this.nsDf = nsDf;
	}

	public void setVnfProfileId(String vnfProfileId) {
		this.vnfProfileId = vnfProfileId;
	}

	public void setVnfdId(String vnfdId) {
		this.vnfdId = vnfdId;
	}

	public void setFlavourId(String flavourId) {
		this.flavourId = flavourId;
	}

	public void setInstantiationLevel(String instantiationLevel) {
		this.instantiationLevel = instantiationLevel;
	}

	public void setMinNumberOfInstances(int minNumberOfInstances) {
		this.minNumberOfInstances = minNumberOfInstances;
	}

	public void setMaxNumberOfInstances(int maxNumberOfInstances) {
		this.maxNumberOfInstances = maxNumberOfInstances;
	}

	public void setLocalAffinityOrAntiAffinityRule(List<AffinityRule> localAffinityOrAntiAffinityRule) {
		this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
	}

	public void setAffinityOrAntiAffinityGroupId(List<String> affinityOrAntiAffinityGroupId) {
		this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
	}

	public void setNsVirtualLinkConnectivity(List<NsVirtualLinkConnectivity> nsVirtualLinkConnectivity) {
		this.nsVirtualLinkConnectivity = nsVirtualLinkConnectivity;
	}

	public void setScript(List<VnfLCMScripts> script) {
		this.script = script;
	}
}
