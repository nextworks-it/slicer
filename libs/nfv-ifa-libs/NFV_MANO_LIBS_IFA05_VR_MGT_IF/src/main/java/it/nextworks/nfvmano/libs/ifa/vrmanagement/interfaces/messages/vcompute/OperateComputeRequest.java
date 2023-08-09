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

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.ComputeOperation;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to execute specific operation command on instantiated virtualised compute resources
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.6
 * 
 * @author nextworks
 *
 */
public class OperateComputeRequest implements InterfaceMessage {

	private String computeId;
	private ComputeOperation computeOperation;
	private Map<String, String> computeOperationInputData = new HashMap<>();
 	
	public OperateComputeRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param computeId Identifier of the virtualised compute resource to operate.
	 * @param computeOperation Type of operation to perform on the virtualised compute resource.
	 * @param computeOperationInputData Additional parameters associated to the operation to perform.
	 */
	public OperateComputeRequest(String computeId,
			ComputeOperation computeOperation,
			Map<String, String> computeOperationInputData) { 
		this.computeId = computeId;
		this.computeOperation = computeOperation;
		if (computeOperationInputData != null) this.computeOperationInputData = computeOperationInputData; 
	}
	
	

	/**
	 * @return the computeId
	 */
	public String getComputeId() {
		return computeId;
	}

	/**
	 * @return the computeOperation
	 */
	public ComputeOperation getComputeOperation() {
		return computeOperation;
	}

	/**
	 * @return the computeOperationInputData
	 */
	public Map<String, String> getComputeOperationInputData() {
		return computeOperationInputData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (computeId == null) throw new MalformattedElementException("Operate compute request without resource ID");
		if (computeOperation == null) throw new MalformattedElementException("Operate compute request without operation");
	}

}
