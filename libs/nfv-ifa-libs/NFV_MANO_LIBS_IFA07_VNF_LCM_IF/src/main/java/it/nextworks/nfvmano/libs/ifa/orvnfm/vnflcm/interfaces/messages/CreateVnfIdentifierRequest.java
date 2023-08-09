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
package it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to create a VNF identifier
 * 
 * REF IFA 007 v2.3.1 - 7.2.2
 * 
 * @author nextworks
 *
 */
public class CreateVnfIdentifierRequest implements InterfaceMessage {

	private String vnfdId;
	private String vnfInstanceName;
	private String vnfInstanceDescription;
	
	public CreateVnfIdentifierRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfdId Identifier that identifies the VNFD which defines the VNF instance to be created.
	 * @param vnfInstanceName Human-readable name of the VNF instance to be created.
	 * @param vnfInstanceDescription Human-readable description of the VNF instance to be created.
	 */
	public CreateVnfIdentifierRequest(String vnfdId,
			String vnfInstanceName,
			String vnfInstanceDescription) {
		this.vnfdId = vnfdId;
		this.vnfInstanceDescription = vnfInstanceDescription;
		this.vnfInstanceName = vnfInstanceName;
	}
	
	

	/**
	 * @return the vnfdId
	 */
	public String getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the vnfInstanceName
	 */
	public String getVnfInstanceName() {
		return vnfInstanceName;
	}

	/**
	 * @return the vnfInstanceDescription
	 */
	public String getVnfInstanceDescription() {
		return vnfInstanceDescription;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfdId == null) throw new MalformattedElementException("Create VNF ID request without VNFD ID.");
	}

}
