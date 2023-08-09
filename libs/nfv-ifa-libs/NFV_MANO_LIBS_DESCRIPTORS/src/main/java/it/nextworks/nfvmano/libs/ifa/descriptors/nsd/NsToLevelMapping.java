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
 * The NsToLevelMapping information element specifies 
 * the profile to be used for a nested NS involved in 
 * a given NS level and the required number of instances.
 * 
 * Ref. IFA 014 v2.3.1 - 6.7.6
 * 
 * @author nextworks
 *
 */
@Embeddable
public class NsToLevelMapping implements DescriptorInformationElement {

	private String nsProfileId;
	private int numberOfInstances;
	
	public NsToLevelMapping() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsProfileId Identifies the profile to be used for a nested NS involved in the NS level.
	 * @param numberOfInstances Specifies the number of nested NS instances required for the NS scale level.
	 */
	public NsToLevelMapping(String nsProfileId,
			int numberOfInstances) {
		this.nsProfileId = nsProfileId;
		this.numberOfInstances = numberOfInstances;
	}
	
	

	/**
	 * @return the nsProfileId
	 */
	@JsonProperty("nsProfileId")
	public String getNsProfileId() {
		return nsProfileId;
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
		if (this.nsProfileId == null) throw new MalformattedElementException("NS to level mapping without NS profile ID");
	}

	public void setNsProfileId(String nsProfileId) {
		this.nsProfileId = nsProfileId;
	}

	public void setNumberOfInstances(int numberOfInstances) {
		this.numberOfInstances = numberOfInstances;
	}
}
