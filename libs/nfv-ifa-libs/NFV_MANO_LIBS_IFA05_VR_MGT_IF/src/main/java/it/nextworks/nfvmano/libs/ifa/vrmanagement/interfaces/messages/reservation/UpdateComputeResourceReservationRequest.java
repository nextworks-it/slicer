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
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.reservation.ComputePoolReservation;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.reservation.VirtualisationContainerReservation;

/**
 * Request to update a reservation for a compute resource
 * REF IFA 005 v2.3.1 - 7.8.1.4
 * 
 * @author nextworks
 *
 */
public class UpdateComputeResourceReservationRequest implements InterfaceMessage {

	private String reservationId;
	private ComputePoolReservation computePoolReservation;
	private List<VirtualisationContainerReservation> virtualisationContainerReservation = new ArrayList<>();
	private Date startTime;
	private Date endTime;
	private Date expiryTime;
	
	public UpdateComputeResourceReservationRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param reservationId Identifier of the existing resource reservation to be updated.
	 * @param computePoolReservation New amount of compute resources to be reserved.
	 * @param virtualisationContainerReservation New virtualisation containers to be reserved (e.g. following a specific compute "flavour").
	 * @param startTime New timestamp to start the consumption of the resource.
	 * @param endTime Timestamp indicating the end time of the reservation
	 * @param expiryTime New timestamp indicating the time the VIM can release the reservation in case no allocation request against this reservation was made.
	 */
	public UpdateComputeResourceReservationRequest(String reservationId,
			ComputePoolReservation computePoolReservation,
			List<VirtualisationContainerReservation> virtualisationContainerReservation,
			Date startTime,
			Date endTime,
			Date expiryTime) {	
		this.reservationId = reservationId;
		this.computePoolReservation = computePoolReservation;
		if (virtualisationContainerReservation != null) this.virtualisationContainerReservation = virtualisationContainerReservation;
		this.startTime = startTime;
		this.expiryTime = expiryTime;
		this.endTime = endTime;
	}

	
	
	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}

	/**
	 * @return the computePoolReservation
	 */
	public ComputePoolReservation getComputePoolReservation() {
		return computePoolReservation;
	}

	/**
	 * @return the virtualisationContainerReservation
	 */
	public List<VirtualisationContainerReservation> getVirtualisationContainerReservation() {
		return virtualisationContainerReservation;
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
		if (reservationId == null) throw new MalformattedElementException("Update compute reservation request without reservation ID");
		if (computePoolReservation != null) computePoolReservation.isValid();
		for (VirtualisationContainerReservation vcr : virtualisationContainerReservation) vcr.isValid();
	}

}
