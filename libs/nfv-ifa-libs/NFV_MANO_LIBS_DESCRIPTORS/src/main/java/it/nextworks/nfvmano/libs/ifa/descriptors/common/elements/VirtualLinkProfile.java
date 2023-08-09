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
package it.nextworks.nfvmano.libs.ifa.descriptors.common.elements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
import it.nextworks.nfvmano.libs.ifa.common.elements.AffinityRule;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.NsDf;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.VnfDf;


/**
 * The VirtualLinkProfile information element specifies 
 * a profile for instantiating VLs of a particular NS DF 
 * according to a specific VLD and VL DF.
 * 
 * Ref. IFA 014 v2.3.1 - 6.3.4
 * 
 * @author nextworks
 *
 */
@Entity
public class VirtualLinkProfile implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsDf nsDf;
	
	@JsonIgnore
	@ManyToOne
	private VnfDf vnfDf;
	
	private String virtualLinkProfileId;
	private String virtualLinkDescId;
	private String flavourId;
	
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
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="root",column=@Column(name="maxRoot")),
	    @AttributeOverride(name="leaf",column=@Column(name="maxLeaf"))
	  })
	private LinkBitrateRequirements maxBitrateRequirements; 
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="root",column=@Column(name="minRoot")),
	    @AttributeOverride(name="leaf",column=@Column(name="minLeaf"))
	  })
	private LinkBitrateRequirements minBitrateRequirements;
	
	public VirtualLinkProfile() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsDf	NS deployment flavour this VL profile belongs to
	 * @param virtualLinkProfileId Uniquely identifies this VirtualLinkProfile information element.
	 * @param virtualLinkDescId Uniquely references a VLD
	 * @param flavourId Identifies a flavour within the VLD
	 * @param localAffinityOrAntiAffinityRule Specifies affinity and anti-affinity rules applicable between VLs instantiated from the referenced VLD.
	 * @param affinityOrAntiAffinityGroupId Identifies an affinity or anti-affinity group the VLs instantiated according to the VlProfile belong to.
	 * @param maxBitrateRequirements Specifies the maximum bitrate requirements for a VL instantiated according to this profile.
	 * @param minBitrateRequirements Specifies the minimum bitrate requirements for a VL instantiated according to this profile.
	 */
	public VirtualLinkProfile(NsDf nsDf,
			String virtualLinkProfileId,
			String virtualLinkDescId,
			String flavourId,
			List<AffinityRule> localAffinityOrAntiAffinityRule,
			List<String> affinityOrAntiAffinityGroupId,
			LinkBitrateRequirements maxBitrateRequirements,
			LinkBitrateRequirements minBitrateRequirements) {
		this.nsDf = nsDf;
		this.virtualLinkProfileId = virtualLinkProfileId;
		this.virtualLinkDescId = virtualLinkDescId;
		this.flavourId = flavourId;
		if (localAffinityOrAntiAffinityRule != null) this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
		if (affinityOrAntiAffinityGroupId != null) this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
		if (maxBitrateRequirements != null) this.maxBitrateRequirements = maxBitrateRequirements;
		if (minBitrateRequirements != null) this.minBitrateRequirements = minBitrateRequirements;
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfDf	VNF deployment flavour this VL profile belongs to
	 * @param virtualLinkProfileId Uniquely identifies this VirtualLinkProfile information element.
	 * @param virtualLinkDescId Uniquely references a VLD
	 * @param flavourId Identifies a flavour within the VLD
	 * @param localAffinityOrAntiAffinityRule Specifies affinity and anti-affinity rules applicable between VLs instantiated from the referenced VLD.
	 * @param affinityOrAntiAffinityGroupId Identifies an affinity or anti-affinity group the VLs instantiated according to the VlProfile belong to.
	 * @param maxBitrateRequirements Specifies the maximum bitrate requirements for a VL instantiated according to this profile.
	 * @param minBitrateRequirements Specifies the minimum bitrate requirements for a VL instantiated according to this profile.
	 */
	public VirtualLinkProfile(VnfDf vnfDf,
			String virtualLinkProfileId,
			String virtualLinkDescId,
			String flavourId,
			List<AffinityRule> localAffinityOrAntiAffinityRule,
			List<String> affinityOrAntiAffinityGroupId,
			LinkBitrateRequirements maxBitrateRequirements,
			LinkBitrateRequirements minBitrateRequirements) {
		this.vnfDf = vnfDf;
		this.virtualLinkProfileId = virtualLinkProfileId;
		this.virtualLinkDescId = virtualLinkDescId;
		this.flavourId = flavourId;
		if (localAffinityOrAntiAffinityRule != null) this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
		if (affinityOrAntiAffinityGroupId != null) this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
		if (maxBitrateRequirements != null) this.maxBitrateRequirements = maxBitrateRequirements;
		if (minBitrateRequirements != null) this.minBitrateRequirements = minBitrateRequirements;
	}
	
	

	/**
	 * @return the virtualLinkProfileId
	 */
	@JsonProperty("virtualLinkProfileId")
	public String getVirtualLinkProfileId() {
		return virtualLinkProfileId;
	}

	/**
	 * @return the virtualLinkDescId
	 */
	@JsonProperty("virtualLinkDescId")
	public String getVirtualLinkDescId() {
		return virtualLinkDescId;
	}

	/**
	 * @return the flavourId
	 */
	@JsonProperty("flavourId")
	public String getFlavourId() {
		return flavourId;
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
	 * @return the maxBitrateRequirements
	 */
	@JsonProperty("maxBitrateRequirements")
	public LinkBitrateRequirements getMaxBitrateRequirements() {
		return maxBitrateRequirements;
	}

	/**
	 * @return the minBitrateRequirements
	 */
	@JsonProperty("minBitrateRequirements")
	public LinkBitrateRequirements getMinBitrateRequirements() {
		return minBitrateRequirements;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.virtualLinkProfileId == null) throw new MalformattedElementException("VL profile without ID");
		if (this.virtualLinkDescId == null) throw new MalformattedElementException("VL profile without VLD ID");
		if (this.flavourId == null) throw new MalformattedElementException("VL profile without VL flavour ID");
		if (this.minBitrateRequirements == null) {
			throw new MalformattedElementException("VL profile without min bitrate requirements");
		} else {
			this.minBitrateRequirements.isValid();
		}
		if (this.maxBitrateRequirements == null) {
			throw new MalformattedElementException("VL profile without max bitrate requirements");
		} else {
			this.maxBitrateRequirements.isValid();
		}
		if (this.localAffinityOrAntiAffinityRule != null) {
			for (AffinityRule rule : this.localAffinityOrAntiAffinityRule) rule.isValid();
		}
	}

	public void setNsDf(NsDf nsDf) {
		this.nsDf = nsDf;
	}

	public void setVnfDf(VnfDf vnfDf) {
		this.vnfDf = vnfDf;
	}

	public void setVirtualLinkProfileId(String virtualLinkProfileId) {
		this.virtualLinkProfileId = virtualLinkProfileId;
	}

	public void setVirtualLinkDescId(String virtualLinkDescId) {
		this.virtualLinkDescId = virtualLinkDescId;
	}

	public void setFlavourId(String flavourId) {
		this.flavourId = flavourId;
	}

	public void setLocalAffinityOrAntiAffinityRule(List<AffinityRule> localAffinityOrAntiAffinityRule) {
		this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
	}

	public void setAffinityOrAntiAffinityGroupId(List<String> affinityOrAntiAffinityGroupId) {
		this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
	}

	public void setMaxBitrateRequirements(LinkBitrateRequirements maxBitrateRequirements) {
		this.maxBitrateRequirements = maxBitrateRequirements;
	}

	public void setMinBitrateRequirements(LinkBitrateRequirements minBitrateRequirements) {
		this.minBitrateRequirements = minBitrateRequirements;
	}
}
