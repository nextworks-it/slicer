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

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The ModifyVnfInfoData information element specifies for a VNF 
 * instance the information that is requested to be modified. 
 * The information to be modified shall comply with the associated NSD
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.17
 * 
 * @author nextworks
 *
 */
public class ModifyVnfInfoData implements InterfaceInformationElement {

	private String vnfInstanceId;
	private Map<String, String> newValues = new HashMap<>();
	
	public ModifyVnfInfoData() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance for which the writable attributes of VnfInfo are requested to be modified.
	 * @param newValues Contains the set of attributes to update. The key in the KeyValuePair indicates the name of an attribute that is writable through the interface whose value is to be updated. The value in the KeyValuePair indicates the new attribute value.
	 */
	public ModifyVnfInfoData(String vnfInstanceId, Map<String,String> newValues) {
		this.vnfInstanceId = vnfInstanceId;
		if (newValues != null) this.newValues = newValues;
	}
	
	

	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the newValues
	 */
	public Map<String, String> getNewValues() {
		return newValues;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Modify VNF info data without VNF instance ID");
		if ((newValues == null) || (newValues.isEmpty())) throw new MalformattedElementException("Modify VNF info data without new parameter values");
	}

}
