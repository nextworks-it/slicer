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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.AffinityConstraint;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.reservation.StoragePoolReservation;


/**
 * Request to create a reservation for storage resources.
 * 
 *  REF IFA 005 v2.3.1 - 7.8.3.2
 * 
 * @author nextworks
 *
 */
public class CreateStorageResourceReservationRequest implements InterfaceMessage {

	private StoragePoolReservation storagePoolReservation;
	private Date startTime;
	private Date endTime;
	private Date expiryTime;
	private List<AffinityConstraint> affinityConstraint = new ArrayList<>();
	private List<AffinityConstraint> antiAffinityConstraint = new ArrayList<>();
	private String locationConstraints;
	private String resourceGroupId;
	
	public CreateStorageResourceReservationRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param storagePoolReservation Type and configuration of virtualised storage that need to be reserved. E.g. amount of storage resources that need to be reserved
	 * @param startTime Timestamp to start the consumption of the resources.
	 * @param endTime Timestamp indicating the end time of the reservation (when the issuer of the request expects that the resources will no longer be needed) and used by the VIM to schedule the reservation.
	 * @param expiryTime Timestamp indicating the time the VIM can release the reservation in case no allocation request against this reservation was made.
	 * @param affinityConstraint Element with affinity information of the virtual network resources to reserve.
	 * @param antiAffinityConstraint Element with anti-affinity information of the virtual network resources to reserve.
	 * @param locationConstraints If present, it defines location constraints for the resource(s) is (are) requested to be reserved
	 * @param resourceGroupId Unique identifier of the "infrastructure resource group", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
	 */
	public CreateStorageResourceReservationRequest(StoragePoolReservation storagePoolReservation,
			Date startTime,
			Date endTime,
			Date expiryTime,
			List<AffinityConstraint> affinityConstraint,
			List<AffinityConstraint> antiAffinityConstraint,
			String locationConstraints,
			String resourceGroupId) {
		this.storagePoolReservation = storagePoolReservation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.expiryTime = expiryTime;
		if (affinityConstraint != null) this.affinityConstraint = affinityConstraint;
		if (antiAffinityConstraint != null) this.antiAffinityConstraint = antiAffinityConstraint;
		this.locationConstraints = locationConstraints;
		this.resourceGroupId = resourceGroupId;
	}
	
	

	

	/**
	 * @return the storagePoolReservation
	 */
	public StoragePoolReservation getStoragePoolReservation() {
		return storagePoolReservation;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @return the expiryTime
	 */
	public Date getExpiryTime() {
		return expiryTime;
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
	 * @return the locationConstraints
	 */
	public String getLocationConstraints() {
		return locationConstraints;
	}

	/**
	 * @return the resourceGroupId
	 */
	public String getResourceGroupId() {
		return resourceGroupId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (storagePoolReservation == null) throw new MalformattedElementException("Create storage reservation request without reservation");
		else storagePoolReservation.isValid();
		for (AffinityConstraint ac : affinityConstraint) ac.isValid();
		for (AffinityConstraint ac : antiAffinityConstraint) ac.isValid();
		if (resourceGroupId == null) throw new MalformattedElementException("Create storage reservation request without resource group ID");
	}

}
