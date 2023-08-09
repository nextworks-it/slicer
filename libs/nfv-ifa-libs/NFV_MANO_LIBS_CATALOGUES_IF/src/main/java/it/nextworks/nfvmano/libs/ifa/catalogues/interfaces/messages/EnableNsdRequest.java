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
package it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to enable an NSD already on-boarded.
 * REF IFA 013 v2.3.1 - 7.2.3
 * 
 * @author nextworks
 *
 */
public class EnableNsdRequest implements InterfaceMessage {

	private String nsdInfoId;
	
	public EnableNsdRequest() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsdInfoId Identifier of the on-boarded instance of the NSD.
	 */
	public EnableNsdRequest(String nsdInfoId) {
		this.nsdInfoId = nsdInfoId;
	}
	
	

	/**
	 * @return the nsdInfoId
	 */
	public String getNsdInfoId() {
		return nsdInfoId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.nsdInfoId == null) throw new MalformattedElementException("Enable NSD request without NSD info ID");
	}

}
