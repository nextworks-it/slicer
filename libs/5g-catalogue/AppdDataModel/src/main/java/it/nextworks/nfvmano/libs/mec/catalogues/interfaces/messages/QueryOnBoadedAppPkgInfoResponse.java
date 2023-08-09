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
package it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.elements.AppPackageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Reply to a MEC App package info query
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.2/8
 * 
 * @author nextworks
 *
 */
public class QueryOnBoadedAppPkgInfoResponse implements InterfaceMessage {

	private List<AppPackageInfo> queryResult = new ArrayList<AppPackageInfo>();
	
	public QueryOnBoadedAppPkgInfoResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param queryResult details of the on-boarded application packages matching the input filter
	 */
	public QueryOnBoadedAppPkgInfoResponse(List<AppPackageInfo> queryResult) {
		if (queryResult != null) this.queryResult = queryResult;
	}
	
	

	/**
	 * @return the queryResult
	 */
	public List<AppPackageInfo> getQueryResult() {
		return queryResult;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (AppPackageInfo api : queryResult) api.isValid();
	}

}
