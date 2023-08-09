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
 * This notification informs the receiver of the creation 
 * of a new VNF instance identifier and the associated instance 
 * of a VnfInfo information element, identified by that identifier.
 * 
 * REF IFA 007 v2.3.1 - 8.6.7
 * 
 * @author nextworks
 *
 */
public class VnfIdentifierCreationNotification implements InterfaceMessage {

	private String vnfInstanceId;
	
	public VnfIdentifierCreationNotification() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId The newly created VNF instance identifier.
	 */
	public VnfIdentifierCreationNotification(String vnfInstanceId) {
		this.vnfInstanceId = vnfInstanceId;
	}

	
	
	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("VNF ID creation notification without VNF ID");
	}

}
