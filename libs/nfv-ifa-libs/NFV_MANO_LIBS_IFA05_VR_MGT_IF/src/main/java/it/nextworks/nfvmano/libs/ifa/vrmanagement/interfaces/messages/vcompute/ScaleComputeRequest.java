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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;


/**
 * Request for scaling a virtualised compute resource by adding 
 * or removing capacity in terms of virtual CPUs and virtual memory.
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.7
 * 
 * @author nextworks
 *
 */
public class ScaleComputeRequest implements InterfaceMessage {

	private String computeId;
	private String computeFlavourId;
	
	public ScaleComputeRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param computeId Identifier of the virtualised compute resource to scale.
	 * @param computeFlavourId Identifier of the Compute Flavour, what provides information about the particular memory, CPU and disk resources for virtualised compute resource to allocate
	 */
	public ScaleComputeRequest(String computeId,
			String computeFlavourId) {	
		this.computeId = computeId;
		this.computeFlavourId = computeFlavourId;
	}
	
	

	/**
	 * @return the computeId
	 */
	public String getComputeId() {
		return computeId;
	}

	

	/**
	 * @return the computeFlavourId
	 */
	public String getComputeFlavourId() {
		return computeFlavourId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (computeId == null) throw new MalformattedElementException("Scale compute request without resource ID");
		if (computeFlavourId == null) throw new MalformattedElementException("Scale compute request without flavour");
	}

}
