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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

/**
 * The VirtualStorageDesc information element supports the specifications 
 * of requirements related to virtual storage resources.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.9.4
 * 
 * @author nextworks
 *
 */
@Embeddable
public class VirtualStorageDesc implements DescriptorInformationElement {

	private String storageId;
	private String typeOfStorage;
	private int sizeOfStorage;
	private boolean rdmaEnabled;
	private String swImageDesc;
	
	public VirtualStorageDesc() {}
	
	/**
	 * Constructor
	 * 
	 * @param storageId Unique identifier of this VirtualStorageDesc in the VNFD.
	 * @param typeOfStorage Type of virtualised storage resource (e.g. volume, object).
	 * @param sizeOfStorage Size of virtualised storage resource (e.g. size of volume, in GB).
	 * @param rdmaEnabled Indicate if the storage support RDMA.
	 * @param swImageDesc Software image to be loaded on the VirtualStorage Resource created based on this VirtualStorageDesc.
	 */
	public VirtualStorageDesc(String storageId,
			String typeOfStorage,
			int sizeOfStorage,
			boolean rdmaEnabled,
			String swImageDesc) {
		this.storageId = storageId;
		this.typeOfStorage = typeOfStorage;
		this.sizeOfStorage = sizeOfStorage;
		this.rdmaEnabled = rdmaEnabled;
		this.swImageDesc = swImageDesc;
	}

	/**
	 * @return the storageId
	 */
	@JsonProperty("id")
	public String getStorageId() {
		return storageId;
	}



	/**
	 * @return the typeOfStorage
	 */
	@JsonProperty("typeOfStorage")
	public String getTypeOfStorage() {
		return typeOfStorage;
	}

	/**
	 * @return the sizeOfStorage
	 */
	@JsonProperty("sizeOfStorage")
	public int getSizeOfStorage() {
		return sizeOfStorage;
	}

	/**
	 * @return the rdmaEnabled
	 */
	@JsonProperty("rdmaEnabled")
	public boolean isRdmaEnabled() {
		return rdmaEnabled;
	}

	/**
	 * @return the swImageDesc
	 */
	@JsonProperty("swImageDesc")
	public String getSwImageDesc() {
		return swImageDesc;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (storageId == null) throw new MalformattedElementException("Virtual storage descriptor without id");
		if (typeOfStorage == null) throw new MalformattedElementException("Virtual storage descriptor without type");
		if (sizeOfStorage <= 0) throw new MalformattedElementException("Virtual storage descriptor with a not valid sizeOfStorage");
	}

}
