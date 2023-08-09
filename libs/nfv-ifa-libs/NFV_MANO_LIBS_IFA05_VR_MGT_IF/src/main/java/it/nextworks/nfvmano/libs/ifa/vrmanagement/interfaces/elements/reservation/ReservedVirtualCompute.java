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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.ReservationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The compute resource reservation information element encapsulate information 
 * about a reservation for virtualised compute resources. 
 * It includes information about virtual compute resource pool and 
 * virtualisation container reservations.
 * 
 * REF IFA 005 v2.3.1 - 8.8.2
 * 
 * 
 * @author nextworks
 *
 */
public class ReservedVirtualCompute implements InterfaceInformationElement {

	private String reservationId;
	private ReservedComputePool computePoolReserved;
	private List<ReservedVirtualisationContainer> virtualisationContainerReserved = new ArrayList<ReservedVirtualisationContainer>();
	private ReservationStatus reservationStatus;
	private Date startTime;
	private Date endTime;
	private Date expiryTime;
	
	public ReservedVirtualCompute() {	}
	
	/**
	 * Constructor
	 * 
	 * @param reservationId Identifier of the resource reservation.
	 * @param computePoolReserved Information about compute resources that have been reserved
	 * @param virtualisationContainerReserved Information about the virtualisation container(s) that have been reserved.
	 * @param reservationStatus Status of the compute resource reservation
	 * @param startTime Timestamp to start the consumption of the resources
	 * @param endTime Timestamp indicating the end time of the reservation (when it is expected that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.
	 * @param expiryTime Timestamp indicating the time the VIM can release the reservation in case no allocation request against this reservation was made.
	 */
	public ReservedVirtualCompute(String reservationId,
			ReservedComputePool computePoolReserved,
			List<ReservedVirtualisationContainer> virtualisationContainerReserved,
			ReservationStatus reservationStatus,
			Date startTime,
			Date endTime,
			Date expiryTime) {
		this.reservationId = reservationId;
		this.computePoolReserved = computePoolReserved;
		if (virtualisationContainerReserved != null) this.virtualisationContainerReserved = virtualisationContainerReserved;
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
	 * @return the computePoolReserved
	 */
	public ReservedComputePool getComputePoolReserved() {
		return computePoolReserved;
	}

	/**
	 * @return the virtualisationContainerReserved
	 */
	public List<ReservedVirtualisationContainer> getVirtualisationContainerReserved() {
		return virtualisationContainerReserved;
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
		if (reservationId == null) throw new MalformattedElementException("Reserved virtual compute without reservation ID");
		if (computePoolReserved != null) computePoolReserved.isValid();
		for (ReservedVirtualisationContainer rvc : virtualisationContainerReserved) rvc.isValid();
	}

}
