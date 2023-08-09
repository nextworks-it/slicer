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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vstorage;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Response to an operate virtual storage request.
 * 
 * REF IFA 005 v2.3.1 - sect. 7.5.1.6
 * 
 * @author nextworks
 *
 */
public class OperateStorageResponse implements InterfaceMessage {

	private String storageId;
	private Map<String, String> storageOperationData = new HashMap<>();

	
	public OperateStorageResponse() { }
	
	
	/**
	 * Constructor
	 * 
	 * @param storageId The same identifier used in the input parameter is returned.
	 * @param storageOperationData Set of values depending on the type of operation. For instance, when a snapshot operation is requested, this field provides information about the identifier of the snapshot.
	 */
	public OperateStorageResponse(String storageId, Map<String, String> storageOperationData) {
		this.storageId = storageId;
		if (storageOperationData != null) this.storageOperationData = storageOperationData;
	}



	/**
	 * @return the storageId
	 */
	public String getStorageId() {
		return storageId;
	}



	/**
	 * @return the storageOperationData
	 */
	public Map<String, String> getStorageOperationData() {
		return storageOperationData;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (storageId == null) throw new MalformattedElementException("Operate storage response without storage ID.");
	}

}
