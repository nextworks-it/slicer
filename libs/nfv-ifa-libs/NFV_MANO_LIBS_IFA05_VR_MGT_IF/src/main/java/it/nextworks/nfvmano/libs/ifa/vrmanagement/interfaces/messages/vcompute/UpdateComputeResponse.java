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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualCompute;


/**
 * Response to an update compute request
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.3
 * 
 * @author nextworks
 *
 */
public class UpdateComputeResponse implements InterfaceMessage {

	private String computeId;
	private VirtualCompute computeData;
	
	public UpdateComputeResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param computeId The identifier of the virtualised compute resource that has been updated. This parameter has the same value as the input parameter.
	 * @param computeData Element containing information of the updated attributes of the instantiated virtualised compute resource.
	 */
	public UpdateComputeResponse(String computeId,
			VirtualCompute computeData) {
		this.computeId = computeId;
		this.computeData = computeData;
	}
	
	

	/**
	 * @return the computeId
	 */
	public String getComputeId() {
		return computeId;
	}

	/**
	 * @return the computeData
	 */
	public VirtualCompute getComputeData() {
		return computeData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (computeId == null) throw new MalformattedElementException("Update compute response without compute ID");
		if (computeData == null) throw new MalformattedElementException("Update compute response without compute data");
		else computeData.isValid();
	}

}
