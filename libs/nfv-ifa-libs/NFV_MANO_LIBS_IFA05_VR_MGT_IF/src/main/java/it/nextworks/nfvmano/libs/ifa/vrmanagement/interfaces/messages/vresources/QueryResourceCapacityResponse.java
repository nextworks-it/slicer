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
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.CapacityInformation;


/**
 * Query for VIM resource capacity
 * 
 * REF IFA 005 v2.3.1 - section 7.3.4.2, 7.4.4.2, 7.5.4.2
 * 
 * @author nextworks
 *
 */
public class QueryResourceCapacityResponse implements InterfaceMessage {
	
	private CapacityInformation capacityResponse;

	public QueryResourceCapacityResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param capacityResponse The capacity during the requested time period. The scope is according to parameter zoneId of the request during the time interval.
	 */
	public QueryResourceCapacityResponse(CapacityInformation capacityResponse) {	
		this.capacityResponse = capacityResponse;
	}
	
	

	/**
	 * @return the capacityResponse
	 */
	public CapacityInformation getCapacityResponse() {
		return capacityResponse;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (capacityResponse == null) throw new MalformattedElementException("Query capacity response without response element");

	}

}
