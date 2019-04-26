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
package it.nextworks.nfvmano.sebastian.catalogue.elements;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Embeddable
public class VsdSla implements DescriptorInformationElement {

	private ServiceCreationTimeRange serviceCreationTime;
	private AvailabilityCoverageRange availabilityCoverage;
	@Column(name = "low_cost_required", nullable = true)
	private boolean lowCostRequired = false;
	
	public VsdSla() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param serviceCreationTime 
	 * @param availabilityCoverage
	 * @param lowCostRequired
	 */
	public VsdSla(ServiceCreationTimeRange serviceCreationTime,
			AvailabilityCoverageRange availabilityCoverage,
			boolean lowCostRequired) {
		this.serviceCreationTime = serviceCreationTime;
		this.availabilityCoverage = availabilityCoverage;
		this.lowCostRequired = lowCostRequired;
	}

	
	
	/**
	 * @return the serviceCreationTime
	 */
	public ServiceCreationTimeRange getServiceCreationTime() {
		return serviceCreationTime;
	}

	/**
	 * @return the availabilityCoverage
	 */
	public AvailabilityCoverageRange getAvailabilityCoverage() {
		return availabilityCoverage;
	}

	/**
	 * @return the lowCostRequired
	 */
	public boolean isLowCostRequired() {
		return lowCostRequired;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
