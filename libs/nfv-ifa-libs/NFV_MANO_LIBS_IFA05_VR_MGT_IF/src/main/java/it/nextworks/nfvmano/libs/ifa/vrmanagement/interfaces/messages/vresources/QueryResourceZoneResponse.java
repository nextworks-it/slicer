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
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.ResourceZone;


/**
 * Reponse to a query compute or storage resource zone request
 * 
 * Ref. IFA 005 v2.3.1 section 7.3.4.5 - 7.4.5.6
 * 
 * 
 * @author nextworks
 *
 */
public class QueryResourceZoneResponse implements InterfaceMessage {

	private List<ResourceZone> zoneInfo = new ArrayList<>();
	
	public QueryResourceZoneResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param zoneInfo The filtered information that has been retrieved about the Resource Zone
	 */
	public QueryResourceZoneResponse(List<ResourceZone> zoneInfo) {	
		this.zoneInfo = zoneInfo;
	}
	
	

	/**
	 * @return the zoneInfo
	 */
	public List<ResourceZone> getZoneInfo() {
		return zoneInfo;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (ResourceZone rz: zoneInfo) rz.isValid();
	}

}
