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
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.enums.VnfChangeType;

/**
 * This information element provides information about added, deleted and modified VNF instances.
 * 
 * REF IFA 013 v2.3.1 - 8.3.2.3
 * 
 * @author nextworks
 *
 */
public class AffectedVnf implements InterfaceInformationElement {

	private String vnfInstanceId;
	private String vnfdId;
	private String vnfProfileId;
	private String vnfName;
	private VnfChangeType changeType;
	
	public AffectedVnf() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance.
	 * @param vnfdId Identifier of the VNFD of the VNF instance.
	 * @param vnfProfileId Identifier of the VNF profile of the NSD.
	 * @param vnfName Name of the VNF instance.
	 * @param changeType Signals the type of lifecycle change
	 */
	public AffectedVnf(String vnfInstanceId,
			String vnfdId,
			String vnfProfileId,
			String vnfName,
			VnfChangeType changeType) {	
		this.vnfInstanceId = vnfInstanceId;
		this.vnfdId = vnfdId;
		this.vnfProfileId = vnfProfileId;
		this.vnfName = vnfName;
		this.changeType = changeType;
	}
	
	

	/**
	 * @return the vnfInstanceId
	 */
	@JsonProperty("vnfInstanceId")
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the vnfdId
	 */
	@JsonProperty("vnfdId")
	public String getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the vnfProfileId
	 */
	@JsonProperty("vnfProfileId")
	public String getVnfProfileId() {
		return vnfProfileId;
	}

	/**
	 * @return the vnfName
	 */
	@JsonProperty("vnfName")
	public String getVnfName() {
		return vnfName;
	}

	/**
	 * @return the changeType
	 */
	@JsonProperty("changeType")
	public VnfChangeType getChangeType() {
		return changeType;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Affected VNF without VNF instance ID");
		if (vnfdId == null) throw new MalformattedElementException("Affected VNF without VNFD ID");
		if (vnfProfileId == null) throw new MalformattedElementException("Affected VNF without VNF profile ID");
		if (vnfName == null) throw new MalformattedElementException("Affected VNF without VNF name");
	}

}
