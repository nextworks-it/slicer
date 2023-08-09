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

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.LinkBitrateRequirements;

/**
 * The VirtualLinkToLevelMapping information element 
 * specifies the profile to be used for a VL involved 
 * in a given NS level and bitrate requirements.
 * 
 * Ref. IFA 014 v2.3.1 - 6.7.5
 * 
 * @author nextworks
 *
 */
@Entity
public class VirtualLinkToLevelMapping implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private NsLevel nsLevel;
	
	private String virtualLinkProfileId;
	
	@Embedded
	private LinkBitrateRequirements bitRateRequirements;
	
	public VirtualLinkToLevelMapping() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsLevel	NS level this mapping belongs to
	 * @param virtualLinkProfileId Identifies the profile to be used for a VL involved in an NS level.
	 * @param bitRateRequirements Specifies the bit rate requirements for the NS level.
	 */
	public VirtualLinkToLevelMapping(NsLevel nsLevel,
			String virtualLinkProfileId,
			LinkBitrateRequirements bitRateRequirements) {
		this.nsLevel = nsLevel;
		this.virtualLinkProfileId = virtualLinkProfileId;
		this.bitRateRequirements = bitRateRequirements;
	}

	
	
	/**
	 * @return the virtualLinkProfileId
	 */
	@JsonProperty("virtualLinkProfileId")
	public String getVirtualLinkProfileId() {
		return virtualLinkProfileId;
	}

	/**
	 * @return the bitRateRequirements
	 */
	@JsonProperty("bitRateRequirements")
	public LinkBitrateRequirements getBitRateRequirements() {
		return bitRateRequirements;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.virtualLinkProfileId == null) throw new MalformattedElementException("VL to level mapping without VL profile ID");
		if (this.bitRateRequirements == null) throw new MalformattedElementException("VL to level mapping without bit rate requirements");
	}

	public void setNsLevel(NsLevel nsLevel) {
		this.nsLevel = nsLevel;
	}

	public void setVirtualLinkProfileId(String virtualLinkProfileId) {
		this.virtualLinkProfileId = virtualLinkProfileId;
	}

	public void setBitRateRequirements(LinkBitrateRequirements bitRateRequirements) {
		this.bitRateRequirements = bitRateRequirements;
	}
}
