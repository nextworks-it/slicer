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
 * Response to a delete PNFD request
 * REF IFA 013 v2.3.1 - 7.2.10
 * 
 * @author nextworks
 *
 */
public class DeletePnfdResponse implements InterfaceMessage {

	private List<String> deletedPnfdInfoId = new ArrayList<>();
	
	public DeletePnfdResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param deletedPnfdInfoId Identifier of the deleted PNFD version(s).
	 */
	public DeletePnfdResponse(List<String> deletedPnfdInfoId) {
		if (deletedPnfdInfoId != null) this.deletedPnfdInfoId = deletedPnfdInfoId;
	}
	
	
	/**
	 * @return the deletedPnfdInfoId
	 */
	public List<String> getDeletedPnfdInfoId() {
		return deletedPnfdInfoId;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {	}

}
