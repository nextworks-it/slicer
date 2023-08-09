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


import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VnfInstanceData specifies an existing VNF instance 
 * to be used in the NS instance and if needed, the VNF Profile
 * to use for this VNF instance.
 * Ref. IFA 013 v2.3.1 section 8.3.4.3
 * 
 * @author nextworks
 *
 */
public class VnfInstanceData implements InterfaceInformationElement {

	private String vnfInstanceId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String vnfProfileId;
	
	public VnfInstanceData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the existing VNF instance to be used in the NS.
	 * @param vnfProfileId Identifier of (Reference to) a vnfProfile defined in the NSD which the existing VNF instance shall be matched with.
	 */
	public VnfInstanceData(String vnfInstanceId, String vnfProfileId) {
		this.vnfInstanceId = vnfInstanceId;
		this.vnfProfileId = vnfProfileId;
	}

	
	
	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the vnfProfileId
	 */
	public String getVnfProfileId() {
		return vnfProfileId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.vnfInstanceId == null) throw new MalformattedElementException("VNF Instance Data without VNF instance ID");
	}

}
