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

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * 
 * REF IFA 005 v2.3.1 - 8.4.6.3
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VirtualStorageData implements DescriptorInformationElement {

	private String typeOfStorage;
	private int sizeOfStorage;
	
	public VirtualStorageData() { }
	
	/**
	 * Constructor
	 * 
	 * @param typeOfStorage Type of virtualised storage resource (e.g. volume, object)
	 * @param sizeOfStorage Size of virtualised storage resource (e.g. size of volume, in GB).
	 * 
	 */
	public VirtualStorageData(String typeOfStorage,
			int sizeOfStorage) { 
		this.typeOfStorage = typeOfStorage;
		this.sizeOfStorage = sizeOfStorage;
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

	@Override
	public void isValid() throws MalformattedElementException {
		if (typeOfStorage == null) throw new MalformattedElementException("Virtual Storage Data without type of storage");
	}

}
