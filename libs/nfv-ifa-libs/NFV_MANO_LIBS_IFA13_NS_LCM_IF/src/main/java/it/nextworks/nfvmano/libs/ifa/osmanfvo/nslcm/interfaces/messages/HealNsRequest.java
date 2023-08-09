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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.HealNsData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.HealVnfData;

/**
 * Message to request the healing of a Network Service instance.
 * 
 *  REF IFA 013 v2.3.1 - 7.3.9
 * 
 * @author nextworks
 *
 */
public class HealNsRequest implements InterfaceMessage {

	private String nsInstanceId; 
	private HealNsData healNsData;
	private List<HealVnfData> healVnfData = new ArrayList<>();
	
	public HealNsRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId The parameter identifies the NS instance which shall be healed.
	 * @param healNsData Provides the information needed to heal an NS
	 * @param healVnfData Provides the information needed to heal a VNF
	 */
	public HealNsRequest(String nsInstanceId, 
			HealNsData healNsData,
			List<HealVnfData> healVnfData) {
		this.nsInstanceId = nsInstanceId;
		this.healNsData = healNsData;
		if (healVnfData != null) this.healVnfData = healVnfData;
	}
	
	
	
	/**
	 * @return the nsInstanceId
	 */
	public String getNsInstanceId() {
		return nsInstanceId;
	}

	/**
	 * @return the healNsData
	 */
	public HealNsData getHealNsData() {
		return healNsData;
	}

	/**
	 * @return the healVnfData
	 */
	public List<HealVnfData> getHealVnfData() {
		return healVnfData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsInstanceId == null) throw new MalformattedElementException("Heal NS request without NS instance ID");
		if (healNsData != null) healNsData.isValid();
		if (healVnfData != null) {
			for (HealVnfData h:healVnfData) h.isValid();
		}
	}

}
