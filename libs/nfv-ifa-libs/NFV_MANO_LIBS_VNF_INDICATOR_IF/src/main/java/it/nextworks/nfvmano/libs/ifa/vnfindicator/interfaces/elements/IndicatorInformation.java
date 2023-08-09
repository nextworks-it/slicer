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
package it.nextworks.nfvmano.libs.ifa.vnfindicator.interfaces.elements;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides the indicator values of a VNF instance.
 * 
 * REF IFA 007 v2.3.1 - 8.10.3
 * 
 * @author nextworks
 *
 */
public class IndicatorInformation implements InterfaceInformationElement {

	private String vnfInstanceId;
	private String indicatorId;
	private String indicatorValue;
	private String indicatorName;
	
	public IndicatorInformation() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId		Identifies the VNF instance which provides the indicator value(s).
	 * @param indicatorId		Identifier of the indicator.
	 * @param indicatorValue	Provides the value of the indicator.
	 * @param indicatorName		Human readable name of the indicator.
	 */
	public IndicatorInformation(String vnfInstanceId,
			String indicatorId,
			String indicatorValue,
			String indicatorName) {
		this.vnfInstanceId = vnfInstanceId;
		this.indicatorId = indicatorId;
		this.indicatorValue = indicatorValue;
		this.indicatorName = indicatorName;
	}

	/**
	 * 
	 * @return the ID of the VNF instance which provides the indicator value(s). 
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * 
	 * @return the identifier of the indicator.
	 */
	public String getIndicatorId() {
		return indicatorId;
	}

	/**
	 * 
	 * @return the value of the indicator.
	 */
	public String getIndicatorValue() {
		return indicatorValue;
	}

	/**
	 * 
	 * @return the human readable name of the indicator.
	 */
	public String getIndicatorName() {
		return indicatorName;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Indicator information without VNF instance ID");
		if (indicatorId == null) throw new MalformattedElementException("Indicator information without indicator ID");
		if (indicatorValue == null) throw new MalformattedElementException("Indicator information without indicator value");
	}
}
