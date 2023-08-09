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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.nfp;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.nfp.Nfp;

/**
 * Response to a query NFP request
 * 
 * REF IFA 005 v2.3.1 - 7.4.5.3
 * 
 * @author nextworks
 *
 */
public class QueryNfpResponse implements InterfaceMessage {

	private List<Nfp> nfpResult = new ArrayList<>();
	
	public QueryNfpResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param nfpResult Provide the result for the query.
	 */
	public QueryNfpResponse(List<Nfp> nfpResult) {	
		if (nfpResult != null) this.nfpResult = nfpResult;
	}
	
	

	/**
	 * @return the nfpResult
	 */
	public List<Nfp> getNfpResult() {
		return nfpResult;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (Nfp nfp : nfpResult) nfp.isValid();
	}

}
