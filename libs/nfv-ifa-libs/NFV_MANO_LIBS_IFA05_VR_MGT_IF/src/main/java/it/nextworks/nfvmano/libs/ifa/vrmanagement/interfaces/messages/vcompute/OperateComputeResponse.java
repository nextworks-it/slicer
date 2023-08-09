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
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vcompute.VirtualCompute;


/**
 * Response to an operate compute request
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.6
 * 
 * @author nextworks
 *
 */
public class OperateComputeResponse implements InterfaceMessage {

	private VirtualCompute computeData;
	private Map<String, String> computeOperationOutputData = new HashMap<>();
	
	public OperateComputeResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param computeData Element containing information of the new status of the operated virtualised compute resource.
	 * @param computeOperationOutputData Set of output values depending on the type of operation.
	 */
	public OperateComputeResponse(VirtualCompute computeData,
			Map<String, String> computeOperationOutputData) { 
		this.computeData = computeData;
		if (computeOperationOutputData != null) this.computeOperationOutputData = computeOperationOutputData;
	}

	
	
	/**
	 * @return the computeData
	 */
	public VirtualCompute getComputeData() {
		return computeData;
	}

	/**
	 * @return the computeOperationOutputData
	 */
	public Map<String, String> getComputeOperationOutputData() {
		return computeOperationOutputData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (computeData == null) throw new MalformattedElementException("Operate compute response without compute data");
		else computeData.isValid();
	}

}
