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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.AffinityConstraint;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.vstorage.VirtualStorageFlavour;

/**
 * Request to allocate a new storage resource
 * 
 * REF IFA 005 v2.3.1 - sect. 7.5.1.2
 * 
 * @author nextworks
 *
 */
public class AllocateStorageRequest implements InterfaceMessage {

	private String storageName;
	private String reservationId;
	private VirtualStorageFlavour storageData;
	private List<AffinityConstraint> affinityConstraint = new ArrayList<>();
	private List<AffinityConstraint> antiAffinityConstraint = new ArrayList<>();
	private Map<String, String> metaData = new HashMap<>();
	private String resourceGroupId;
	private String locationConstraints;
	
	public AllocateStorageRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param storageName Name provided by the consumer for the virtualised storage resource to allocate. It can be used for identifying resources from consumer side.
	 * @param reservationId Identifier of the resource reservation applicable to this virtualised resource management operation.
	 * @param storageData The storage data provides information about the type and size of the storage.
	 * @param affinityConstraint Element with affinity information of the virtualised storage resource to allocate.
	 * @param antiAffinityConstraint Element with anti-affinity information of the virtualised storage resource to allocate.
	 * @param metaData List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
	 * @param locationConstraints If present, it defines location constraints for the resource(s) to be allocated, e.g. in what particular resource zone.
	 */
	public AllocateStorageRequest(String storageName, 
			String reservationId, 
			VirtualStorageFlavour storageData,
			List<AffinityConstraint> affinityConstraint, 
			List<AffinityConstraint> antiAffinityConstraint,
			Map<String, String> metaData, 
			String resourceGroupId, 
			String locationConstraints) {
		this.storageName = storageName;
		this.reservationId = reservationId;
		this.storageData = storageData;
		if (affinityConstraint != null) this.affinityConstraint = affinityConstraint;
		if (antiAffinityConstraint != null) this.antiAffinityConstraint = antiAffinityConstraint;
		if (metaData != null) this.metaData = metaData;
		this.resourceGroupId = resourceGroupId;
		this.locationConstraints = locationConstraints;
	}



	/**
	 * @return the storageName
	 */
	public String getStorageName() {
		return storageName;
	}

	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}

	/**
	 * @return the storageData
	 */
	public VirtualStorageFlavour getStorageData() {
		return storageData;
	}

	/**
	 * @return the affinityConstraint
	 */
	public List<AffinityConstraint> getAffinityConstraint() {
		return affinityConstraint;
	}

	/**
	 * @return the antiAffinityConstraint
	 */
	public List<AffinityConstraint> getAntiAffinityConstraint() {
		return antiAffinityConstraint;
	}

	/**
	 * @return the metaData
	 */
	public Map<String, String> getMetaData() {
		return metaData;
	}

	/**
	 * @return the resourceGroupId
	 */
	public String getResourceGroupId() {
		return resourceGroupId;
	}

	/**
	 * @return the locationConstraints
	 */
	public String getLocationConstraints() {
		return locationConstraints;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (storageData == null) throw new MalformattedElementException("Allocate storage request without storage data");
		else storageData.isValid();
		for (AffinityConstraint ac : affinityConstraint) ac.isValid();
		for (AffinityConstraint aac : antiAffinityConstraint) aac.isValid();
		if (resourceGroupId == null) throw new MalformattedElementException("Allocate storage request without resource group ID.");

	}

}
