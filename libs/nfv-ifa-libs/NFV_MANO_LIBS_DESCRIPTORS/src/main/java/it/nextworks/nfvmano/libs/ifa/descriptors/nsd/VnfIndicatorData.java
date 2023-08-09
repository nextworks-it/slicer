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
 * The VnfIndicatorData information identifies a VNF indicator in a VNFD.
 * Ref. IFA 014 v2.3.1 - 6.2.7
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VnfIndicatorData implements DescriptorInformationElement {

	private String vnfdId;
	private String vnfIndicator;
	
	public VnfIndicatorData() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfdId Reference to a VNFD
	 * @param vnfIndicator Reference to a VNF indicator within the VNFD
	 */
	public VnfIndicatorData(String vnfdId, 
			String vnfIndicator) {
		this.vnfdId = vnfdId;
		this.vnfIndicator = vnfIndicator;
	}
	
	/**
	 * @return the vnfdId
	 */
	@JsonProperty("vnfdId")
	public String getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the vnfIndicator
	 */
	@JsonProperty("vnfIndicator")
	public String getVnfIndicator() {
		return vnfIndicator;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.vnfdId == null) throw new MalformattedElementException("VNF indicator data without VNFD ID");
		if (this.vnfIndicator == null) throw new MalformattedElementException("VNF indicator data without VNF indicator ID");
	}

	public void setVnfdId(String vnfdId) {
		this.vnfdId = vnfdId;
	}

	public void setVnfIndicator(String vnfIndicator) {
		this.vnfIndicator = vnfIndicator;
	}
}
