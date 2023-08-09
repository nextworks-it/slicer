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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.ResponseCode;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.NsInfo;


/**
 * Message to provide information about an NS
 * 
 * REF IFA 013 v2.3.1 - 7.3.6
 * 
 * @author nextworks
 *
 */
public class QueryNsResponse implements InterfaceMessage {

	private List<NsInfo> queryNsResult = new ArrayList<>();
	
	public QueryNsResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param responseCode result of the query
	 * @param queryNsResult Information on the NS and VNF instances part of the NS matching the input filter.
	 */
	public QueryNsResponse(ResponseCode responseCode, List<NsInfo> queryNsResult) {
		if (queryNsResult != null) this.queryNsResult = queryNsResult;
	}

	
	/**
	 * @return the queryNsResult
	 */
	public List<NsInfo> getQueryNsResult() {
		return queryNsResult;
	}


	@Override
	public void isValid() throws MalformattedElementException {
		if (queryNsResult != null) {
			for (NsInfo info : queryNsResult) info.isValid();
		}
	}

}
