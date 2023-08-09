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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vstorage;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element defines the characteristics of consumable virtual storage resources.
 * 
 *  REF IFA 005 v2.3.1 - 8.3.4
 * 
 * @author nextworks
 *
 */
public class VirtualStorageResouceInformation implements InterfaceInformationElement {

	private String storageResourceTypeId;
	private String typeOfStorage;
	private int sizeOfStorage; //in GB
	private boolean rdmaSupported;
	
	public VirtualStorageResouceInformation() {	}
	
	
	/**
	 * Constructor
	 * 
	 * @param storageResourceTypeId Identifier of the consumable virtualised storage resource type.
	 * @param typeOfStorage Type of virtualised storage resource (e.g. volume, object).
	 * @param sizeOfStorage Size of virtualised storage resource (e.g. size of volume, in GB).
	 * @param rdmaSupported It indicates if the storage supports RDMA.
	 */
	public VirtualStorageResouceInformation(String storageResourceTypeId, String typeOfStorage, int sizeOfStorage,
			boolean rdmaSupported) {
		this.storageResourceTypeId = storageResourceTypeId;
		this.typeOfStorage = typeOfStorage;
		this.sizeOfStorage = sizeOfStorage;
		this.rdmaSupported = rdmaSupported;
	}



	/**
	 * @return the storageResourceTypeId
	 */
	public String getStorageResourceTypeId() {
		return storageResourceTypeId;
	}



	/**
	 * @return the typeOfStorage
	 */
	public String getTypeOfStorage() {
		return typeOfStorage;
	}



	/**
	 * @return the sizeOfStorage
	 */
	public int getSizeOfStorage() {
		return sizeOfStorage;
	}



	/**
	 * @return the rdmaSupported
	 */
	public boolean isRdmaSupported() {
		return rdmaSupported;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (storageResourceTypeId == null) throw new MalformattedElementException("Virtual storage resource info wihtout resource type ID");
		if (typeOfStorage == null) throw new MalformattedElementException("Virtual storage resource info wihtout type of storage");
	}

}
