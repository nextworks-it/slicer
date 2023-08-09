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
 * The ScalingAspect information element describes the details of an aspect used for horizontal scaling.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.10.2
 * 
 * @author nextworks
 *
 */
@Embeddable
public class ScalingAspect implements DescriptorInformationElement {

	private String saId;
	private String saName;
	private String saDescription;
	private String associatedGroup;
	private int maxScaleLevel;
	
	public ScalingAspect() { }
	
	/**
	 * Constructor
	 * 
	 * @param saId Unique identifier of this aspect in the VNFD.
	 * @param saName Human readable name of the aspect.
	 * @param saDescription Human readable description of the aspect.
	 * @param associatedGroup Reference to the group of Vnfd elements defining this aspect.
	 * @param maxScaleLevel The maximum scaleLevel for total number of scaling steps that can be applied w.r.t. this aspect.
	 */
	public ScalingAspect(String saId,
			String saName,
			String saDescription,
			String associatedGroup,
			int maxScaleLevel) { 
		this.saDescription = saDescription;
		this.saId = saId;
		this.saName = saName;
		this.associatedGroup = associatedGroup;
		this.maxScaleLevel = maxScaleLevel;
	}

	
	
	/**
	 * @return the saId
	 */
	@JsonProperty("id")
	public String getSaId() {
		return saId;
	}

	/**
	 * @return the saName
	 */
	@JsonProperty("name")
	public String getSaName() {
		return saName;
	}

	/**
	 * @return the saDescription
	 */
	@JsonProperty("description")
	public String getSaDescription() {
		return saDescription;
	}

	/**
	 * @return the associatedGroup
	 */
	@JsonProperty("associatedGroup")
	public String getAssociatedGroup() {
		return associatedGroup;
	}

	/**
	 * @return the maxScaleLevel
	 */
	@JsonProperty("maxScaleLevel")
	public int getMaxScaleLevel() {
		return maxScaleLevel;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (saId == null) throw new MalformattedElementException("Scaling aspect without ID");
		if (saName == null) throw new MalformattedElementException("Scaling aspect without name");
		if (saDescription == null) throw new MalformattedElementException("Scaling aspect without description");
	}

}
