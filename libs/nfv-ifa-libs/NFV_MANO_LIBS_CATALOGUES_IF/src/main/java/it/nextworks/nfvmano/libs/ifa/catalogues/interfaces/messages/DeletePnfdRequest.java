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
 * Message to request the deletion of one or more PNFDs
 * REF IFA 013 v2.3.1 - 7.2.10
 * 
 * @author nextworks
 *
 */
public class DeletePnfdRequest implements InterfaceMessage {

	private List<String> pnfdInfoId = new ArrayList<>();
	private boolean applyOnAllVersions;
	
	public DeletePnfdRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param pnfdInfoId Identifier of the on-boarded instance(s) of the PNFD to be deleted
	 * @param applyOnAllVersion Indicates if the delete operation is to be applied on all versions of this NSD. By default, if not present, it applies only on the current version.
	 */
	public DeletePnfdRequest(List<String> pnfdInfoId,
			boolean applyOnAllVersion) {
		this.applyOnAllVersions = applyOnAllVersion;
		if (pnfdInfoId != null) this.pnfdInfoId = pnfdInfoId;
	}
	
	

	/**
	 * @return the pnfdInfoId
	 */
	public List<String> getPnfdInfoId() {
		return pnfdInfoId;
	}

	/**
	 * @return the applyOnAllVersions
	 */
	public boolean isApplyOnAllVersions() {
		return applyOnAllVersions;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((this.pnfdInfoId == null) || (this.pnfdInfoId.isEmpty())) 
			throw new MalformattedElementException("Delete PNFD request without PNFD info IDs");
	}

}
