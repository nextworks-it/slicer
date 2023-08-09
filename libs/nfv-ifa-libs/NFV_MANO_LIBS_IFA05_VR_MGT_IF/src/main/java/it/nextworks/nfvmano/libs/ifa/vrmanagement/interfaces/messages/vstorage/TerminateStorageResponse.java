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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Response to a terminate storage request
 * 
 * REF IFA 005 v2.3.1 - sect. 7.5.1.5
 * 
 * @author nextworks
 *
 */
public class TerminateStorageResponse implements InterfaceMessage {

	private List<String> storageId = new ArrayList<>();
	
	public TerminateStorageResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param storageId Identifier of the virtualised storage resource(s) successfully terminated.
	 */
	public TerminateStorageResponse(List<String> storageId) {
		if (storageId != null) this.storageId = storageId;
	}
	
	

	/**
	 * @return the storageId
	 */
	public List<String> getStorageId() {
		return storageId;
	}

	@Override
	public void isValid() throws MalformattedElementException {	
		if (storageId.isEmpty()) throw new MalformattedElementException("Terminate storage response without IDs");
	}

}
