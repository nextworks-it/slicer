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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.reservation;

import java.util.Date;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.ReservationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 *  REF IFA 005 v2.3.1 - 8.8.6.2
 * 
 * @author nextworks
 *
 */
public class ReservedVirtualStorage implements InterfaceInformationElement {

	private String reservationId;
	private ReservedStoragePool storagePoolReserved;
	private ReservationStatus reservationStatus;
	private Date startTime;
	private Date endTime;
	private Date expiryTime;
	
	public ReservedVirtualStorage() { }
	
	/**
	 * Constructor
	 * 
	 * @param reservationId Identifier of the resource reservation.
	 * @param storagePoolReserved Information about storage resources that have been reserved
	 * @param reservationStatus Status of the storage resource reservation
	 * @param startTime Indication when the consumption of the resources starts
	 * @param endTime Indication when the reservation ends (when it is expected that the resources will no longer be needed) and used by the VIM to schedule the reservation
	 * @param expiryTime Indication when the VIM can release the reservation in case no allocation request against this reservation was made.
	 */
	public ReservedVirtualStorage(String reservationId,
			ReservedStoragePool storagePoolReserved,
			ReservationStatus reservationStatus,
			Date startTime,
			Date endTime,
			Date expiryTime) { 
		this.reservationId = reservationId;
		this.storagePoolReserved = storagePoolReserved;
		this.reservationStatus = reservationStatus;
		this.startTime = startTime;
		this.endTime = endTime;
		this.expiryTime = expiryTime;
	}

	
	
	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}

	/**
	 * @return the storagePoolReserved
	 */
	public ReservedStoragePool getStoragePoolReserved() {
		return storagePoolReserved;
	}

	/**
	 * @return the reservationStatus
	 */
	public ReservationStatus getReservationStatus() {
		return reservationStatus;
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

	@Override
	public void isValid() throws MalformattedElementException {
		if (reservationId == null) throw new MalformattedElementException("Reserved virtual storage without reservation ID.");
		if (storagePoolReserved != null) storagePoolReserved.isValid();
	}

}
