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


import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The ParamsForVnf specifies additional parameters for an NS instance on a per VNF instance basis.
 * Ref. IFA 013 v2.3.1 section 8.3.4.5
 * 
 * @author nextworks
 *
 */
public class ParamsForVnf implements InterfaceInformationElement {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String vnfProfileId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, String> additionalParam = new HashMap<>();
	
	public ParamsForVnf() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfProfileId Identifier of (reference to) a vnfProfile to which the additional parameters apply.
	 * @param additionalParam Additional parameters that are to be applied per VNF instance.
	 */
	public ParamsForVnf(String vnfProfileId, Map<String,String> additionalParam) {
		this.vnfProfileId = vnfProfileId;
		if (additionalParam != null) this.additionalParam = additionalParam;
	}
	
	

	/**
	 * @return the vnfProfileId
	 */
	public String getVnfProfileId() {
		return vnfProfileId;
	}

	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		// TODO Auto-generated method stub

	}

}
