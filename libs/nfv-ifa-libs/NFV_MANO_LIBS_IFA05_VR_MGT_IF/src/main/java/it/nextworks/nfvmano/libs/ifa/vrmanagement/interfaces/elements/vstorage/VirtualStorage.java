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

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The VirtualStorage information element encapsulates information 
 * of an instantiated virtualised storage resource.
 * 
 * REF IFA 005 v2.3.1 - 8.4.7.2
 * 
 * @author nextworks
 *
 */
public class VirtualStorage implements InterfaceInformationElement {

	private String storageId;
	private String storageName;
	private String flavourId;
	private String typeOfStorage;
	private int sizeOfStorage;
	private boolean rdmaEnabled;
	private String ownerId;
	private String zoneId;
	private String hostId;
	private OperationalState operationalState;
	private Map<String, String> metadata = new HashMap<>();
	
	public VirtualStorage() { }
	
	/**
	 * Constructor
	 * 
	 * @param storageId Identifier of the virtualised storage resource.
	 * @param storageName Name of the virtualised storage resource.
	 * @param flavourId Identifier of the storage flavour used to instantiate this virtual storage.
	 * @param typeOfStorage Type of virtualised storage resource (e.g. volume, object).
	 * @param sizeOfStorage Size of virtualised storage resource (e.g. size of volume, in GB).
	 * @param rdmaEnabled Indicates if the storage supports RDMA.
	 * @param ownerId Identifier of the virtualised resource that owns and uses such a virtualised storage resource.
	 * @param zoneId If present, it identifies the Resource Zone where the virtual storage resources have been allocated.
	 * @param hostId Identifier of the host where the virtualised storage resource is allocated.
	 * @param operationalState Operational state of the resource.
	 * @param metadata List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 */
	public VirtualStorage(String storageId,
			String storageName,
			String flavourId,
			String typeOfStorage,
			int sizeOfStorage,
			boolean rdmaEnabled,
			String ownerId,
			String zoneId,
			String hostId,
			OperationalState operationalState,
			Map<String, String> metadata) {
		this.storageId = storageId;
		this.storageName = storageName;
		this.flavourId = flavourId;
		this.typeOfStorage = typeOfStorage;
		this.sizeOfStorage = sizeOfStorage;
		this.rdmaEnabled = rdmaEnabled;
		this.ownerId = ownerId;
		this.zoneId = zoneId;
		this.hostId = hostId;
		this.operationalState = operationalState;
		if (metadata != null) this.metadata = metadata;
	}
	
	

	/**
	 * @return the storageId
	 */
	public String getStorageId() {
		return storageId;
	}

	/**
	 * @return the storageName
	 */
	public String getStorageName() {
		return storageName;
	}

	/**
	 * @return the flavourId
	 */
	public String getFlavourId() {
		return flavourId;
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
	 * @return the rdmaEnabled
	 */
	public boolean isRdmaEnabled() {
		return rdmaEnabled;
	}

	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}

	/**
	 * @return the operationalState
	 */
	public OperationalState getOperationalState() {
		return operationalState;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (storageId == null) throw new MalformattedElementException("Virtual storage without storage ID");
		if (flavourId == null) throw new MalformattedElementException("Virtual storage without flavour ID");
	}

}
