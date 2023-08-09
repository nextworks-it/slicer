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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.elements.TimePeriodInformation;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.enums.ResourceCapacityType;

/**
 * Query for VIM resource capacity
 * 
 * REF IFA 005 v2.3.1 - section 7.3.4.2, 7.4.4.2, 7.5.4.2
 * 
 * @author nextworks
 *
 */
public class QueryResourceCapacityRequest implements InterfaceMessage {
	
	private String zoneId;
	private ResourceCapacityType resourceTypeId;
	private String resourceCriteria;
	private List<String> attributeSelector = new ArrayList<String>();
	private TimePeriodInformation timePeriod;

	public QueryResourceCapacityRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param zoneId When specified this parameter identifies the resource zone for which the capacity is requested. When not specified the total capacity managed by the VIM instance will be returned. 
	 * @param resourceTypeId Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity
	 * @param resourceCriteria Input capacity parameter for selecting the capabilities for which the issuer wants to know the available, total, reserved and/or allocated capacity
	 * @param attributeSelector Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) is queried. When not present, all four values are requested. 
	 * @param timePeriod The time interval for which capacity is queried. When omitted, an interval starting "now" is used. The time interval can be specified since resource reservations can be made for a specified time interval.
	 */
	public QueryResourceCapacityRequest(String zoneId,
			ResourceCapacityType resourceTypeId,
			String resourceCriteria,
			List<String> attributeSelector,
			TimePeriodInformation timePeriod) { 
		this.zoneId = zoneId;
		this.resourceTypeId = resourceTypeId;
		this.resourceCriteria = resourceCriteria;
		if (attributeSelector != null) this.attributeSelector = attributeSelector;
		this.timePeriod = timePeriod;
	}
	
	
	
	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @return the resourceTypeId
	 */
	public ResourceCapacityType getResourceTypeId() {
		return resourceTypeId;
	}

	/**
	 * @return the resourceCriteria
	 */
	public String getResourceCriteria() {
		return resourceCriteria;
	}

	/**
	 * @return the attributeSelector
	 */
	public List<String> getAttributeSelector() {
		return attributeSelector;
	}

	/**
	 * @return the timePeriod
	 */
	public TimePeriodInformation getTimePeriod() {
		return timePeriod;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
