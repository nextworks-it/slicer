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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.VimResourceType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.CapacityInformation;


/**
 * This notification informs the receiver of changes in the capacity of virtual 
 * resources managed by the VIM. 
 * 
 * REF IFA 005 v2.3.1 - 8.7.4
 * 
 * 
 * @author nextworks
 *
 */
public class CapacityChangeNotification implements InterfaceMessage {

	private String changeId;
	private String zoneId;
	private VimResourceType resourceDescriptor;
	private CapacityInformation capacityInformation;
	
	public CapacityChangeNotification() { }
	
	/**
	 * Constructor
	 * 
	 * @param changeId It identifies a change in the capacity.
	 * @param zoneId The Resource Zone for which the capacity has changed. When omitted the total capacity managed by the VIM is reported.
	 * @param capacityInformation The available, total, reserved and/or allocated capacity of the Resource Zone, or the available, total, reserved and/or allocated capacity of the VIM in case the Resource Zone is omitted.
	 */
	public CapacityChangeNotification(String changeId,
			String zoneId,
			VimResourceType resourceDescriptor,
			CapacityInformation capacityInformation) { 
		this.changeId = changeId;
		this.zoneId = zoneId;
		this.resourceDescriptor = resourceDescriptor;
		this.capacityInformation = capacityInformation;
	}
	
	

	/**
	 * @return the changeId
	 */
	public String getChangeId() {
		return changeId;
	}

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @return the capacityInformation
	 */
	public CapacityInformation getCapacityInformation() {
		return capacityInformation;
	}
	
	

	/**
	 * @return the resourceDescriptor
	 */
	public VimResourceType getResourceDescriptor() {
		return resourceDescriptor;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (changeId == null) throw new MalformattedElementException("Capacity change notification without change ID.");
		if (capacityInformation == null) throw new MalformattedElementException("Capacity change notification without capacity information.");
	}

}
