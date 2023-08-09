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
import it.nextworks.nfvmano.libs.ifa.descriptors.onboardedvnfpackage.OnboardedVnfPkgInfo;


/**
 * Reply to a VNF package info query
 * 
 * REF IFA 013 v2.3.1 - 7.7.6
 * 
 * @author nextworks
 *
 */
public class QueryOnBoardedVnfPkgInfoResponse implements InterfaceMessage {

	private List<OnboardedVnfPkgInfo> queryResult = new ArrayList<>();
	
	public QueryOnBoardedVnfPkgInfoResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param queryResult Details of the on-boarded VNF Packages matching the input filter.
	 */
	public QueryOnBoardedVnfPkgInfoResponse(List<OnboardedVnfPkgInfo> queryResult) {
		if (queryResult != null) this.queryResult = queryResult;
	}
	
	/**
	 * @return the queryResult
	 */
	public List<OnboardedVnfPkgInfo> getQueryResult() {
		return queryResult;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (OnboardedVnfPkgInfo pkg : queryResult) pkg.isValid();
	}

}
