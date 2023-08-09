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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vstorage.VirtualStorageResouceInformation;

/**
 * This message provides information for the various types of 
 * virtualised storage resources managed by the VIM.
 * 
 *  REF IFA 005 v2.3.1 - 7.5.3.4
 *  
 * @author nextworks
 *
 */
public class QueryVirtualStorageResourceInfoResponse implements InterfaceMessage {

	private List<VirtualStorageResouceInformation> virtualisedResourceInformation = new ArrayList<>();
	
	public QueryVirtualStorageResourceInfoResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param virtualisedResourceInformation Virtualised storage resources information in the VIM that satisfies the query condition.
	 */
	public QueryVirtualStorageResourceInfoResponse(List<VirtualStorageResouceInformation> virtualisedResourceInformation) {
		if (virtualisedResourceInformation != null) this.virtualisedResourceInformation = virtualisedResourceInformation;
	}
	
	

	/**
	 * @return the virtualisedResourceInformation
	 */
	public List<VirtualStorageResouceInformation> getVirtualisedResourceInformation() {
		return virtualisedResourceInformation;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VirtualStorageResouceInformation vsri : virtualisedResourceInformation) vsri.isValid();
	}

}
