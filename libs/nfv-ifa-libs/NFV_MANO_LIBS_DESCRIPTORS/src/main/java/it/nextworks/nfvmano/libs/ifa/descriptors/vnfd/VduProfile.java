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
package it.nextworks.nfvmano.libs.ifa.descriptors.vnfd;

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
import it.nextworks.nfvmano.libs.ifa.common.elements.AffinityRule;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VduProfile describes additional instantiation data for a given VDU used in a DF.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.8.3
 * 
 * @author nextworks
 *
 */
@Entity
public class VduProfile implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private VnfDf vnfDf;
	
	private String vduId;
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
	
	public VduProfile() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfDf VNF deployment flavour this VDU profile belongs to
	 * @param vduId Uniquely identifies a VDU.
	 * @param minNumberOfInstances Minimum number of instances of the VNFC based on this VDU that is permitted to exist for this flavour.
	 * @param maxNumberOfInstances Maximum number of instances of the VNFC based on this VDU that is permitted to exist for this flavour.
	 * @param localAffinityOrAntiAffinityRule Specifies affinity or anti-affinity rules applicable between the virtualisation containers (e.g. virtual machines) to be created based on this VDU.
	 * @param affinityOrAntiAffinityGroupId Identifier(s) of the affinity or anti-affinity group(s) the VDU belongs to
	 */
	public VduProfile(VnfDf vnfDf,
			String vduId,
			int minNumberOfInstances,
			int maxNumberOfInstances,
			List<AffinityRule> localAffinityOrAntiAffinityRule,
			List<String> affinityOrAntiAffinityGroupId) {
		this.vnfDf = vnfDf;
		this.vduId = vduId;
		this.minNumberOfInstances = minNumberOfInstances;
		this.maxNumberOfInstances = maxNumberOfInstances;
		if (localAffinityOrAntiAffinityRule != null) this.localAffinityOrAntiAffinityRule = localAffinityOrAntiAffinityRule;
		if (affinityOrAntiAffinityGroupId != null) this.affinityOrAntiAffinityGroupId = affinityOrAntiAffinityGroupId;
	}
	
	

	/**
	 * @return the vduId
	 */
	@JsonProperty("vduId")
	public String getVduId() {
		return vduId;
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

	@Override
	public void isValid() throws MalformattedElementException {
		if (vduId == null) throw new MalformattedElementException("VDU profile without VDU ID");
		for (AffinityRule r : localAffinityOrAntiAffinityRule) r.isValid();
	}

}
