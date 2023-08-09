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

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Multiple instances of this information element 
 * (at least one for each resource type) are used 
 * to specify the available (i.e. consumable), 
 * reserved, allocated and the total capacity managed 
 * by the VIM or in a resource zone managed by the VIM.
 * 
 * REF IFA 005 v2.3.1 - 8.7.3
 * 
 * @author nextworks
 *
 */
public class CapacityInformation implements InterfaceInformationElement {

	private Map<String,String> availableCapacity = new HashMap<String, String>();
	private Map<String,String> reservedCapacity = new HashMap<String, String>();
	private Map<String,String> totalCapacity = new HashMap<String, String>();
	private Map<String,String> allocatedCapacity = new HashMap<String, String>();
	
	public CapacityInformation() {	}

	/**
	 * Constructor
	 * 
	 * @param availableCapacity The free capacity available for allocation and reservation
	 * @param reservedCapacity The reserved capacity
	 * @param totalCapacity The total capacity is usually specified as a fixed capacity without variations in time
	 * @param allocatedCapacity The allocated capacity is usually specified as the current allocated capacity
	 */
	public CapacityInformation(Map<String,String> availableCapacity,
			Map<String,String> reservedCapacity,
			Map<String,String> totalCapacity,
			Map<String,String> allocatedCapacity) {	
		if (availableCapacity != null) this.availableCapacity = availableCapacity;
		if (reservedCapacity != null) this.reservedCapacity = reservedCapacity;
		if (totalCapacity != null) this.totalCapacity = totalCapacity;
		if (allocatedCapacity != null) this.allocatedCapacity = allocatedCapacity;
	}
	
	
	
	/**
	 * @return the availableCapacity
	 */
	public Map<String, String> getAvailableCapacity() {
		return availableCapacity;
	}

	/**
	 * @return the reservedCapacity
	 */
	public Map<String, String> getReservedCapacity() {
		return reservedCapacity;
	}

	/**
	 * @return the totalCapacity
	 */
	public Map<String, String> getTotalCapacity() {
		return totalCapacity;
	}

	/**
	 * @return the allocatedCapacity
	 */
	public Map<String, String> getAllocatedCapacity() {
		return allocatedCapacity;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
