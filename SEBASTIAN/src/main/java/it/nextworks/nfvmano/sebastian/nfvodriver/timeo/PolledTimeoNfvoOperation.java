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
package it.nextworks.nfvmano.sebastian.nfvodriver.timeo;

import it.nextworks.nfvmano.libs.common.enums.OperationStatus;

public class PolledTimeoNfvoOperation {

	String operationId;
	OperationStatus expectedStatus;
	String nfvNsiId;
	
	//acceptable values for operation: NS_INSTANTIATION, NS_SCALING, NS_TERMINATION, NS_UPDATING, NS_HEALING
	String operationType;
	
	/**
	 * Constructor
	 * 
	 * @param operationId ID of the operation to be polled
	 * @param expectedStatus expected status of the operation
	 * @param nfvNsiId ID of the Network Service instance this operation refers to
	 * @param operationType ID of the type of operation to be polled. Acceptable values are: NS_INSTANTIATION, NS_SCALING, NS_TERMINATION, NS_UPDATING, NS_HEALING
	 */
	public PolledTimeoNfvoOperation(String operationId, 
			OperationStatus expectedStatus,
			String nfvNsiId,
			String operationType) {
		this.operationId = operationId;
		this.expectedStatus = expectedStatus;
		this.nfvNsiId = nfvNsiId;
		this.operationType = operationType;
	}

	/**
	 * @return the operationId
	 */
	public String getOperationId() {
		return operationId;
	}

	/**
	 * @return the expectedStatus
	 */
	public OperationStatus getExpectedStatus() {
		return expectedStatus;
	}

	/**
	 * @return the nfvNsiId
	 */
	public String getNfvNsiId() {
		return nfvNsiId;
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}
	
	

}
