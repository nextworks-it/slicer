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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.enums.DegreeHealing;

/**
 * This information element describes the information needed to heal an NS.
 * 
 *  REF IFA 013 v2.3.1 - 8.3.4.24
 * 
 * @author nextworks
 *
 */
public class HealNsData implements InterfaceInformationElement {

	private List<String> actionsHealing = new ArrayList<>();
	private DegreeHealing degreeHealing;
	private String healScript;
	
	public HealNsData() { }
	
	/**
	 * Constructor
	 * 
	 * @param degreeHealing Indicates the degree of healing
	 * @param actionsHealing May be used to specify dedicated healing actions in a particular order.
	 * @param healScript Provides a reference to a script from the NSD that shall be used to execute dedicate healing actions in a particular order.
	 */
	public HealNsData(DegreeHealing degreeHealing,
			List<String> actionsHealing,
			String healScript) {
		this.degreeHealing = degreeHealing;
		if (actionsHealing != null) this.actionsHealing = actionsHealing;
		this.healScript = healScript;
	}

	
	
	/**
	 * @return the healScript
	 */
	public String getHealScript() {
		return healScript;
	}

	/**
	 * @return the actionsHealing
	 */
	public List<String> getActionsHealing() {
		return actionsHealing;
	}

	/**
	 * @return the degreeHealing
	 */
	public DegreeHealing getDegreeHealing() {
		return degreeHealing;
	}

	@Override
	public void isValid() throws MalformattedElementException {}

}
