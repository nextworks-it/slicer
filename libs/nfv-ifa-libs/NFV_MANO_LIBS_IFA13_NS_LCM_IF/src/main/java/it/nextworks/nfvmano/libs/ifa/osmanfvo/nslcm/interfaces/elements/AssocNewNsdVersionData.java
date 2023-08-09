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


import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The AssocNewNsdVersionData information element specifies 
 * a new NSD version that is associated to the NS instance.
 * After issuing the Update NS operation with updateType=AssocNewNsdVersion, 
 * the NFVO shall use the referred NSD as a basis for the given NS instance. 
 * Different versions of the same NSD have same nsdInvariantId, but different 
 * nsdId attributes, therefore if the nsdInvariantId of the NSD version that 
 * is to be associated to this NS instance is different from the one used before, the
 * NFVO shall reject the request. Only new versions of the same NSD can be associated 
 * to an existing NS instance.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.19
 * 
 * @author nextworks
 *
 */
public class AssocNewNsdVersionData implements InterfaceInformationElement {

	private String newNsdId;
	private boolean sync;
	
	public AssocNewNsdVersionData() { }
	
	/**
	 * Constructor
	 * 
	 * @param newNsdId Identifier of the new NSD version that is to be associated to the NS instance.
	 * @param sync Specify whether the NS instance should be automatically synchronized to the new NSD by the NFVO (in case of true value) or the NFVO should not do any action (in case of a false value) and wait for further guidance from OSS/BSS (i.e. waiting for OSS/BSS to issue NS lifecycle management operation to explicitly add/remove VNFs and modify information of VNF instances according to the new NSD).
	 */
	public AssocNewNsdVersionData(String newNsdId, boolean sync) {
		this.newNsdId = newNsdId;
		this.sync = sync;
	}
	
	

	/**
	 * @return the newNsdId
	 */
	public String getNewNsdId() {
		return newNsdId;
	}

	/**
	 * @return the sync
	 */
	public boolean isSync() {
		return sync;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (newNsdId == null) throw new MalformattedElementException("Assoc new NSD version data without new NSD ID");
	}

}
