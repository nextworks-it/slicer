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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vcompute;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Response to a terminate compute request
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.5
 * 
 * @author nextworks
 *
 */
public class TerminateComputeResponse implements InterfaceMessage {

	private List<String> computeId = new ArrayList<>();
	
	public TerminateComputeResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param computeId Identifier(s) of the virtualised compute resource(s) successfully terminated.
	 */
	public TerminateComputeResponse(List<String> computeId) {
		if (computeId != null) this.computeId = computeId;
	}
	
	/**
	 * @return the computeId
	 */
	public List<String> getComputeId() {
		return computeId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((computeId == null) || (computeId.isEmpty())) throw new MalformattedElementException("Terminate compute response without compute IDs");
	}

}
