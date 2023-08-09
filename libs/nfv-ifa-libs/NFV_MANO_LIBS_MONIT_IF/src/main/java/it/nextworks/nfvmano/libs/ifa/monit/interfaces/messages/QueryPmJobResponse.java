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
package it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.elements.PmJob;

/**
 * Query PM job response
 * 
 * REF IFA 013 v2.3.1 - 7.5.6
 * REF IFA 007 v2.3.1 - 7.4.6
 * 
 * 
 * @author nextworks
 *
 */
public class QueryPmJobResponse implements InterfaceMessage {

	private List<PmJob> pmJobDetails = new ArrayList<>();
	
	public QueryPmJobResponse() {	}
	
	/**
	 * Constructor 
	 *  
	 * @param pmJobDetails Details of PM jobs matching the input filter.
	 */
	public QueryPmJobResponse(List<PmJob> pmJobDetails) {
		if (pmJobDetails != null) this.pmJobDetails = pmJobDetails;
	}
	
	

	/**
	 * @return the pmJobDetails
	 */
	public List<PmJob> getPmJobDetails() {
		return pmJobDetails;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (pmJobDetails.isEmpty()) throw new MalformattedElementException("Query PM job response without PM jobs");
		for (PmJob pj : pmJobDetails) pj.isValid();
	}

}
