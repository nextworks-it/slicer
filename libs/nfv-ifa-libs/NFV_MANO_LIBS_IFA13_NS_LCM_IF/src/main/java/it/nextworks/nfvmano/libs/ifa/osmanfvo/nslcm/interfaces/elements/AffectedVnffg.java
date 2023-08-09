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

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.enums.VnffgChangeType;

/**
 * This information element provides information about added, deleted or modified VNFFG instances.
 * 
 * REF IFA 013 v2.3.1 - 8.3.2.6
 * 
 * @author nextworks
 *
 */
public class AffectedVnffg implements InterfaceInformationElement {

	private String vnffgId;
	private String vnffgdId;
	private VnffgChangeType changeType;
	
	public AffectedVnffg() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnffgId Identifier of the VNFFG instance.
	 * @param vnffgdId Identifier of the VNFFGD of the VNFFG instance.
	 * @param changeType Signals the type of lifecycle change
	 */
	public AffectedVnffg(String vnffgId,
			String vnffgdId,
			VnffgChangeType changeType) {
		this.vnffgdId = vnffgdId;
		this.vnffgId = vnffgId;
		this.changeType = changeType;
	}
	
	

	/**
	 * @return the vnffgId
	 */
	@JsonProperty("vnffgId")
	public String getVnffgId() {
		return vnffgId;
	}

	/**
	 * @return the vnffgdId
	 */
	@JsonProperty("vnffgdId")
	public String getVnffgdId() {
		return vnffgdId;
	}

	/**
	 * @return the changeType
	 */
	@JsonProperty("changeType")
	public VnffgChangeType getChangeType() {
		return changeType;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnffgId == null) throw new MalformattedElementException("Affected VNFFG without ID");
		if (vnffgdId == null) throw new MalformattedElementException("Affected VNFFG without descriptor ID");
	}

}
