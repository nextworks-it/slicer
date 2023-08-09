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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.CapacityThreshold;

/**
 * Request of subscription to compute capacity change notifications.
 * 
 * REF IFA 005 v2.3.1 - section 7.3.4.3, 7.4.4.3, 7.5.4.3
 *  
 * @author nextworks
 *
 */
public class SubscribeResourceCapacityNotificationsRequest implements InterfaceMessage {

	private String zoneId;
	private String resourceTypeId;
	private Map<String, String> resourceCriteria = new HashMap<>();
	private List<CapacityThreshold> threshold = new ArrayList<>();
	private List<String> attributeSelector = new ArrayList<>();
	
	public SubscribeResourceCapacityNotificationsRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param zoneId When specified this parameter identifies the Resource Zone for which the capacity change notifications are is requested. When not specified the total capacity managed by the VIM instance will be notified.
	 * @param resourceTypeId Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity
	 * @param resourceCriteria Input capacity computation parameter for selecting the virtual memory, virtual CPU and acceleration capabilities for which the issuer wants to know the available, total, reserved and/or allocated capacity.
	 * @param threshold When specified this parameter indicates a capacity value which, once crossed, will trigger a notification. When not specified, notifications are issued at every change
	 * @param attributeSelector Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) the subscription refers to. When not present, all four values are requested.
	 */
	public SubscribeResourceCapacityNotificationsRequest(String zoneId,
			String resourceTypeId,
			Map<String,String> resourceCriteria,
			List<CapacityThreshold> threshold,
			List<String> attributeSelector) {	
		this.zoneId = zoneId;
		this.resourceTypeId = resourceTypeId;
		if (resourceCriteria != null) this.resourceCriteria = resourceCriteria;
		if (threshold != null) this.threshold = threshold;
		if (attributeSelector != null) this.attributeSelector = attributeSelector;
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
	public String getResourceTypeId() {
		return resourceTypeId;
	}

	/**
	 * @return the resourceCriteria
	 */
	public Map<String, String> getResourceCriteria() {
		return resourceCriteria;
	}

	/**
	 * @return the threshold
	 */
	public List<CapacityThreshold> getThreshold() {
		return threshold;
	}

	/**
	 * @return the attributeSelector
	 */
	public List<String> getAttributeSelector() {
		return attributeSelector;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (CapacityThreshold thr : threshold) thr.isValid();
	}

}
