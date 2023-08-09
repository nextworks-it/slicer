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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to scale a virtual storage resource. 
 * 
 * REF IFA 005 v2.3.1 - sect. 7.5.1.7
 * 
 * @author nextworks
 *
 */
public class ScaleStorageRequest implements InterfaceMessage {

	private String storageId;
	private int newSize;
	
	public ScaleStorageRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param storageId Identifier of the virtualised storage resource to scale.
	 * @param newSize Resized amount of allocated storage virtualised resource.
	 */
	public ScaleStorageRequest(String storageId,
			int newSize) {
		this.storageId = storageId;
		this.newSize = newSize;
	}
	
	

	/**
	 * @return the storageId
	 */
	public String getStorageId() {
		return storageId;
	}

	/**
	 * @return the newSize
	 */
	public int getNewSize() {
		return newSize;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (storageId == null) throw new MalformattedElementException("Scale storage request without ID.");
	}

}
