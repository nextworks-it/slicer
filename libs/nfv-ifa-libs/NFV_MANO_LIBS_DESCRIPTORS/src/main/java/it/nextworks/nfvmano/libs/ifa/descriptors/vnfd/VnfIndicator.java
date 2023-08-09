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

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VnfIndicator information element defines the indicator the VNF supports.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.11.2
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VnfIndicator implements DescriptorInformationElement {

	private String indicatorId;
	private String name;
	private String indicatorValue;
	private VnfIndicatorSource source;
	
	
	
	public VnfIndicator() {	}
	
	/**
	 * Construcotr
	 * 
	 * @param indicatorId Unique identifier.
	 * @param name The human readable name of the VnfIndicator.
	 * @param indicatorValue Defines the allowed values or value ranges of this indicator.
	 * @param source Describe the source of the indicator.
	 */
	public VnfIndicator(String indicatorId,
			String name,
			String indicatorValue,
			VnfIndicatorSource source) {
		this.indicatorId = indicatorId;
		this.name = name;
		this.indicatorValue = indicatorValue;
		this.source = source;
	}

	
	
	/**
	 * @return the indicatorId
	 */
	@JsonProperty("id")
	public String getIndicatorId() {
		return indicatorId;
	}

	/**
	 * @return the name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * @return the indicatorValue
	 */
	@JsonProperty("indicatorValue")
	public String getIndicatorValue() {
		return indicatorValue;
	}

	/**
	 * @return the source
	 */
	@JsonProperty("source")
	public VnfIndicatorSource getSource() {
		return source;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (indicatorId == null) throw new MalformattedElementException("VNF indicator without ID");
	}

}
