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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

/**
 * This information element describes requested additional capability 
 * for a particular VDU. Such a capability may be for acceleration or specific tasks.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.9.5
 * 
 * @author nextworks
 *
 */
@Embeddable
public class RequestedAdditionalCapabilityData implements DescriptorInformationElement {

	private String name;
	private boolean mandatory;
	private String minVersion;
	private String preferredVersion;
	private String targetParameter;
	
	public RequestedAdditionalCapabilityData() { }
	
	/**
	 * Constructor
	 * 
	 * @param name Identifies a requested additional capability for the VDU.
	 * @param mandatory Indicates whether the requested additional capability is mandatory for successful operation.
	 * @param minVersion Identifies the minimum version of the requested additional capability.
	 * @param preferredVersion Identifies the preferred version of the requested additional capability.
	 * @param targetParameter Identifies specific attributes, dependent on the requested additional capability type.
	 */
	public RequestedAdditionalCapabilityData(String name,
                                             boolean mandatory,
                                             String minVersion,
                                             String preferredVersion,
                                             String targetParameter) {
		this.name = name;
		this.mandatory = mandatory;
		this.minVersion = minVersion;
		this.preferredVersion = preferredVersion;
		this.targetParameter = targetParameter;
	}
	
	

	/**
	 * @return the name
	 */
	@JsonProperty("requestedAdditionalCapabilityName")
	public String getName() {
		return name;
	}

	/**
	 * @return the mandatory
	 */
	@JsonProperty("supportMandatory")
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * @return the minVersion
	 */
	@JsonProperty("minRequestedAdditionalCapabilityVersion")
	public String getMinVersion() {
		return minVersion;
	}

	/**
	 * @return the preferredVersion
	 */
	@JsonProperty("preferredRequestedAdditionalCapabilityVersion")
	public String getPreferredVersion() {
		return preferredVersion;
	}

	/**
	 * @return the targetParameter
	 */
	@JsonProperty("targetPerformanceParameters")
	public String getTargetParameter() {
		return targetParameter;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (name == null) throw new MalformattedElementException("Requested Additional Capability without name");
	}

}
