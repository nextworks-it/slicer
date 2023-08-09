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

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VnfToLevelMapping information element specifies 
 * the profile to be used for a VNF involved in a given NS level
 * and the required number of instances.
 * 
 * Ref. IFA 014 v2.3.1 - 6.7.4
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VnfToLevelMapping implements DescriptorInformationElement {

	private String vnfProfileId;
	private int numberOfInstances;
	
	public VnfToLevelMapping() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfProfileId Identifies the profile to be used for a VNF involved in an NS level.
	 * @param numberOfInstances Specifies the number of VNF instances required for an NS level.
	 */
	public VnfToLevelMapping(String vnfProfileId,
			int numberOfInstances) {
		this.vnfProfileId = vnfProfileId;
		this.numberOfInstances = numberOfInstances;
	}
	
	/**
	 * @return the vnfProfileId
	 */
	@JsonProperty("vnfProfileId")
	public String getVnfProfileId() {
		return vnfProfileId;
	}

	/**
	 * @return the numberOfInstances
	 */
	@JsonProperty("numberOfInstances")
	public int getNumberOfInstances() {
		return numberOfInstances;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.vnfProfileId == null) throw new MalformattedElementException("VNF to level mapping without VNF profile ID");
	}

	public void setVnfProfileId(String vnfProfileId) {
		this.vnfProfileId = vnfProfileId;
	}

	public void setNumberOfInstances(int numberOfInstances) {
		this.numberOfInstances = numberOfInstances;
	}
}
