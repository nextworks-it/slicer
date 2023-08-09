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

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.AffinityScope;
import it.nextworks.nfvmano.libs.ifa.common.enums.AffinityType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The AffinityOrAntiAffinityGroup describes the affinity or 
 * anti-affinity relationship applicable between the VNF
 * instances created using different VnfProfiles, the Virtual 
 * Link instances created using different VlProfiles or the nested
 * NS instances created using different NsProfiles.
 * 
 * Ref. IFA 014 v2.3.1 - 6.3.5
 * 
 * @author nextworks
 *
 */
@Embeddable
public class AffinityOrAntiAffinityGroup implements DescriptorInformationElement {

	private String groupId;
	private AffinityType affinityType;
	private AffinityScope affinityScope;
	
	public AffinityOrAntiAffinityGroup() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param groupId	Identifier of this AffinityOrAntiAffinityGroup information element.
	 * @param affinityType Specifies the type of relationship that the members of the group have: "affinity" or "anti-affinity".
	 * @param affinityScope Specifies the scope of the affinity or anti-affinity relationship e.g. a NFVI node, an NFVI PoP, etc.
	 */
	public AffinityOrAntiAffinityGroup(String groupId,
			AffinityType affinityType,
			AffinityScope affinityScope) {
		this.groupId = groupId;
		this.affinityType = affinityType;
		this.affinityScope = affinityScope;
	}
	
	/**
	 * @return the groupId
	 */
	@JsonProperty("groupId")
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @return the affinityType
	 */
	@JsonProperty("affinityOrAntiAffinity")
	public AffinityType getAffinityType() {
		return affinityType;
	}

	/**
	 * @return the affinityScope
	 */
	@JsonProperty("scope")
	public AffinityScope getAffinityScope() {
		return affinityScope;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.groupId == null) throw new MalformattedElementException("Affinity group without ID");
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setAffinityType(AffinityType affinityType) {
		this.affinityType = affinityType;
	}

	public void setAffinityScope(AffinityScope affinityScope) {
		this.affinityScope = affinityScope;
	}
}
