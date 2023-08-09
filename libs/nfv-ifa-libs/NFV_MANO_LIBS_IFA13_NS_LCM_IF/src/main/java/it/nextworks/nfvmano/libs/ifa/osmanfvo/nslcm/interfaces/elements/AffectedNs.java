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
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.enums.NsChangeType;

/**
 * This information element provides information about added, deleted or modified nested NSs.
 *  
 * REF IFA 013 v2.3.1 - 8.3.2.7
 * 
 * @author nextworks
 *
 */
public class AffectedNs implements InterfaceInformationElement {

	private String nsInstanceId;
	private String nsdId;
	private NsChangeType changeType;
	
	public AffectedNs() {	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId Identifier of the nested NS instance.
	 * @param nsdId Identifier of the NSD of the nested NS instance.
	 * @param changeType Signals the type of lifecycle change.
	 */
	public AffectedNs(String nsInstanceId,
			String nsdId,
			NsChangeType changeType) {	
		this.nsdId = nsdId;
		this.nsInstanceId = nsInstanceId;
		this.changeType = changeType;
	}
	
	

	/**
	 * @return the nsInstanceId
	 */
	@JsonProperty("nsInstanceId")
	public String getNsInstanceId() {
		return nsInstanceId;
	}

	/**
	 * @return the nsdId
	 */
	@JsonProperty("nsdId")
	public String getNsdId() {
		return nsdId;
	}

	/**
	 * @return the changeType
	 */
	@JsonProperty("changeType")
	public NsChangeType getChangeType() {
		return changeType;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsInstanceId == null) throw new MalformattedElementException("Affected NS without ID");
		if (nsdId == null) throw new MalformattedElementException("Affected NS without descriptor ID");
	}

}
