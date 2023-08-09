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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vstorage.VirtualStorage;

/**
 * Response to a virtual storage query.
 * 
 * REF IFA 005 v2.3.1 - sect. 7.5.1.3
 * 
 * @author nextworks
 *
 */
public class QueryVirtualStorageResponse implements InterfaceMessage {

	public List<VirtualStorage> queryResult = new ArrayList<>();
	
	public QueryVirtualStorageResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param queryResult Element containing information about the virtual storage resource(s) matching the filter.
	 */
	public QueryVirtualStorageResponse(List<VirtualStorage> queryResult) {
		if (queryResult != null) this.queryResult = queryResult;
	}
	
	

	/**
	 * @return the queryResult
	 */
	public List<VirtualStorage> getQueryResult() {
		return queryResult;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VirtualStorage vs : queryResult) vs.isValid();
	}

}
