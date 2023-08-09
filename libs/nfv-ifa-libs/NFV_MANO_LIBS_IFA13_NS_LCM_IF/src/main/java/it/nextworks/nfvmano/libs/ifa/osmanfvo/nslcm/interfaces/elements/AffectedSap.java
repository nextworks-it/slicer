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
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.enums.SapChangeType;

/**
 * This information element provides information about added, removed or modified SAP of an NS.
 * 
 * REF IFA 013 v2.3.1 - 8.3.2.8
 * 
 * @author nextworks
 *
 */
public class AffectedSap implements InterfaceInformationElement {

	private String sapInstanceId;
	private String sapdId;
	private String sapName;
	private SapChangeType changeType;
	
	public AffectedSap() { }
	
	/**
	 * Constructor
	 * 
	 * @param sapInstanceId Identifier of this SapInfo information element, identifying the SAP instance.
	 * @param sapdId Reference to the SAPD for this SAP.
	 * @param sapName Human readable name for the SAP.
	 * @param changeType Signals the type of lifecycle change.
	 */
	public AffectedSap(String sapInstanceId,
			String sapdId,
			String sapName,
			SapChangeType changeType) { 
		this.sapInstanceId = sapInstanceId;
		this.sapdId = sapdId;
		this.sapName = sapName;
		this.changeType = changeType;
	}
	
	

	/**
	 * @return the sapInstanceId
	 */
	@JsonProperty("sapInstanceId")
	public String getSapInstanceId() {
		return sapInstanceId;
	}

	/**
	 * @return the sapdId
	 */
	@JsonProperty("sapdId")
	public String getSapdId() {
		return sapdId;
	}

	/**
	 * @return the sapName
	 */
	@JsonProperty("sapName")
	public String getSapName() {
		return sapName;
	}

	/**
	 * @return the changeType
	 */
	@JsonProperty("changeType")
	public SapChangeType getChangeType() {
		return changeType;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (sapInstanceId == null) throw new MalformattedElementException("Affected SAP without ID");
		if (sapdId == null) throw new MalformattedElementException("Affected SAP without SAPD ID");
		if (sapName == null) throw new MalformattedElementException("Affected SAP without name");
	}

}
