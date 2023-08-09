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

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;


/**
 * Message to provide information about an NSD
 * REF IFA 013 v2.3.1 - 7.2.7
 * 
 * @author nextworks
 *
 */
public class QueryNsdResponse implements InterfaceMessage {

	private List<NsdInfo> queryResult = new ArrayList<>();
	
	public QueryNsdResponse() {	}
	
	/**
	 * Constructor 
	 * 
	 * @param queryResult Details of the on-boarded NSD(s) matching the input filter.
	 */
	public QueryNsdResponse(List<NsdInfo> queryResult) {
		if (queryResult != null) this.queryResult = queryResult;
	}
	
	
	
	/**
	 * @return the queryResult
	 */
	public List<NsdInfo> getQueryResult() {
		return queryResult;
	}
	
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (this.queryResult != null) {
			for (NsdInfo i:this.queryResult) i.isValid();
		}
	}

}
