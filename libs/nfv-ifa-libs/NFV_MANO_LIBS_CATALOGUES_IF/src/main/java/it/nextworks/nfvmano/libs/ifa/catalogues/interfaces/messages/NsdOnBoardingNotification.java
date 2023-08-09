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
 * This notification indicates the on-boarding of an NSD.
 * It is produced when a new NSD is on-boarded.
 * 
 * Ref. IFA 013 v2.3.1 - 8.2.6
 * 
 * @author nextworks
 *
 */
public class NsdOnBoardingNotification implements InterfaceMessage {

	String nsdInfoId;
	String nsdId;
	
	public NsdOnBoardingNotification() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsdInfoId Identifier of the on-boarded instance of the NSD.
	 * @param nsdId Identifies the NSD being on-boarded.
	 */
	public NsdOnBoardingNotification(String nsdInfoId,
			String nsdId) {
		this.nsdInfoId = nsdInfoId;
		this.nsdId = nsdId;
	}
	
	

	/**
	 * @return the nsdInfoId
	 */
	public String getNsdInfoId() {
		return nsdInfoId;
	}

	/**
	 * @return the nsdId
	 */
	public String getNsdId() {
		return nsdId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.nsdInfoId == null) throw new MalformattedElementException("NSD on boarding notification without NSD Info ID");
		if (this.nsdId == null) throw new MalformattedElementException("NSD on boarding notification without NSD ID");
	}

}
