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
 * The VirtualStorageFlavour information element encapsulates information 
 * for storage flavours. A storage flavour includes information about the 
 * size of the storage, and the type of storage.
 * 
 *  REF IFA 005 v2.3.1 - sect. 8.4.6.2
 * 
 * @author nextworks
 *
 */
public class VirtualStorageFlavour implements InterfaceInformationElement {

	private String flavourId;
	private VirtualStorageData storageAttributes;
	
	public VirtualStorageFlavour() { }
	
	/**
	 * Constructor
	 * 
	 * @param flavourId Identifier of the storage flavour.
	 * @param storageAttributes Element containing information about the size of virtualised storage resource (e.g. size of volume, in GB), the type of storage (e.g. volume, object), and support for RDMA
	 */
	public VirtualStorageFlavour(String flavourId, 
			VirtualStorageData storageAttributes) {
		this.flavourId = flavourId;
		this.storageAttributes = storageAttributes;
	}



	/**
	 * @return the flavourId
	 */
	public String getFlavourId() {
		return flavourId;
	}

	/**
	 * @return the storageAttributes
	 */
	public VirtualStorageData getStorageAttributes() {
		return storageAttributes;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (flavourId == null) throw new MalformattedElementException("Virtual storage flavour without ID");
		if (storageAttributes == null) throw new MalformattedElementException("Virtual storage flavour without attributes");
		else storageAttributes.isValid();
	}

}
