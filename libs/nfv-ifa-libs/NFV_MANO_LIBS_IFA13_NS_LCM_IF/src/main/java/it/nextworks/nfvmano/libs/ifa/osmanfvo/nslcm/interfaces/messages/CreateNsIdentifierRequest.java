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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to create a new NS identifier
 * 
 * REF IFA 013 v2.3.1 - 7.3.2
 * 
 * @author nextworks
 *
 */
public class CreateNsIdentifierRequest implements InterfaceMessage {

	private String nsdId;
	private String nsName;
	private String nsDescription;
	
	//This is not standard
	private String tenantId;
	
	public CreateNsIdentifierRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param nsdId Reference to the NSD used to create this NS instance.
	 * @param nsName Human readable name of the NS instance.
	 * @param nsDescription Human readable description of the NS instance.
	 * @param tenantId ID of the tenant requesting the installation of the Network Service
	 */
	public CreateNsIdentifierRequest(String nsdId, String nsName, String nsDescription, String tenantId) {
		this.nsdId = nsdId;
		this.nsName = nsName;
		this.nsDescription = nsDescription;
		this.tenantId = tenantId;
	}

	
	
	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @return the nsdId
	 */
	public String getNsdId() {
		return nsdId;
	}

	/**
	 * @return the nsName
	 */
	public String getNsName() {
		return nsName;
	}

	/**
	 * @return the nsDescription
	 */
	public String getNsDescription() {
		return nsDescription;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsdId == null)
			throw new MalformattedElementException("Create NS ID request without NSD ID");
		if (nsDescription == null)
			throw new MalformattedElementException("Create NS ID request without NS Description");
		if (nsName == null)
			throw new MalformattedElementException("Create NS ID request without NS Name");
		if (tenantId == null)
			throw new MalformattedElementException("Create NS ID request without tenant ID");
	}

}
