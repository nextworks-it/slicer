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
package it.nextworks.nfvmano.libs.ifa.common.elements;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The ScaleInfo information element represents a scale level for a particular scaling aspect.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.8.8
 * 
 * @author nextworks
 *
 */
@Embeddable
public class ScaleInfo implements DescriptorInformationElement {

	private String aspectId;
	private int scaleLevel;
	
	public ScaleInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param aspectId Identifier of the scaling aspect.
	 * @param scaleLevel The scale level, greater than or equal to 0.
	 */
	public ScaleInfo(String aspectId,
			int scaleLevel) {
		this.aspectId = aspectId;
		this.scaleLevel = scaleLevel;
	}
	
	

	/**
	 * @return the aspectId
	 */
	@JsonProperty("aspectId")
	public String getAspectId() {
		return aspectId;
	}

	/**
	 * @return the scaleLevel
	 */
	@JsonProperty("scaleLevel")
	public int getScaleLevel() {
		return scaleLevel;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (aspectId == null) throw new MalformattedElementException("Scale info without aspect ID");
	}

	public void setAspectId(String aspectId) {
		this.aspectId = aspectId;
	}

	public void setScaleLevel(int scaleLevel) {
		this.scaleLevel = scaleLevel;
	}
}
