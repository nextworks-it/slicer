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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.ThresholdType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element defines thresholds for sending capacity change notifications.
 * 
 * REF IFA 005 v2.3.1 - 8.7.5
 * 
 * 
 * @author nextworks
 *
 */
public class CapacityThreshold implements DescriptorInformationElement {

	private ThresholdType thresholdType;
	private CapacityThresholdDetails thresholdDetails;
	
	public CapacityThreshold() { }
	
	/**
	 * Constructor
	 * 
	 * @param thresholdType Defines the type of threshold.
	 * @param thresholdDetails Details of the threshold.
	 */
	public CapacityThreshold(ThresholdType thresholdType,
			CapacityThresholdDetails thresholdDetails) { 
		this.thresholdDetails = thresholdDetails;
		this.thresholdType = thresholdType;
	}
	
	

	/**
	 * @return the thresholdType
	 */
	public ThresholdType getThresholdType() {
		return thresholdType;
	}

	

	/**
	 * @return the thresholdDetails
	 */
	public CapacityThresholdDetails getThresholdDetails() {
		return thresholdDetails;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (thresholdDetails == null) throw new MalformattedElementException("Capacity threshold without threshold details");
		else thresholdDetails.isValid();
	}

}
