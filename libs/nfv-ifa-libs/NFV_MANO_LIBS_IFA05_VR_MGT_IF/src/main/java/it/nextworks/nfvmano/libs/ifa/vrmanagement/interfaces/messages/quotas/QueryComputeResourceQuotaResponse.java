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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.quotas;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.quotas.VirtualComputeQuota;

/**
 * Response to a query compute quotas request.
 * 
 * REF IFA 005 v2.3.1 - 7.9.1.3
 * 
 * @author nextworks
 *
 */
public class QueryComputeResourceQuotaResponse implements InterfaceMessage {

	private List<VirtualComputeQuota> queryResult = new ArrayList<>();
	
	public QueryComputeResourceQuotaResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param queryResult Element containing information about the quota resource.
	 */
	public QueryComputeResourceQuotaResponse(List<VirtualComputeQuota> queryResult) {
		if (queryResult != null) this.queryResult = queryResult;
	}
	

	/**
	 * @return the queryResult
	 */
	public List<VirtualComputeQuota> getQueryResult() {
		return queryResult;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (VirtualComputeQuota qr : queryResult) qr.isValid();
	}

}
