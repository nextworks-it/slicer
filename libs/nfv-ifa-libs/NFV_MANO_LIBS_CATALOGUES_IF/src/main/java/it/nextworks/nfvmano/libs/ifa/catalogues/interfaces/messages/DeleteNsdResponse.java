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

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Response to a delete NSD request
 * REF IFA 013 v2.3.1 - 7.2.6
 * 
 * @author nextworks
 *
 */
public class DeleteNsdResponse implements InterfaceMessage {

	private List<String> nsdInfoId = new ArrayList<>();
	
	public DeleteNsdResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param nsdInfoId Identifier of the deleted NSD version(s).
	 */
	public DeleteNsdResponse(List<String> nsdInfoId) {
		if (nsdInfoId != null) this.nsdInfoId = nsdInfoId;
	}
	
	
	/**
	 * @return the nsdInfoId
	 */
	public List<String> getNsdInfoId() {
		return nsdInfoId;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {	}

}
