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
 * REF IFA 005 v2.3.1 - 8.8.4.2
 * 
 * @author nextworks
 *
 */
public class ReservedVirtualNetwork implements InterfaceInformationElement {

	private String reservationId;
	private List<String> publicIps = new ArrayList<>();
	private VirtualNetworkAttributesReservationData networkAttributes;
	private List<VirtualNetworkPortReservationData> networkPorts = new ArrayList<>();
	private ReservationStatus reservationStatus;
	private Date startTime;
	private Date endTime;
	private Date expiryTime;
	private String zoneId;
	
	public ReservedVirtualNetwork() { }
	
	/**
	 * Constructor
	 * 
	 * @param reservationId Identifier of the resource reservation.
	 * @param publicIps List of public IP addresses that have been reserved.
	 * @param networkAttributes Information specifying additional attributes of the network resource that has been reserved.
	 * @param networkPorts List of specific network ports that have been reserved.
	 * @param reservationStatus Status of the network resource reservation, e.g. to indicate if a reservation is being used.
	 * @param startTime Timestamp to start the consumption of the resources.
	 * @param endTime Timestamp indicating the end time of the reservation (when it is expected that the resources will no longer be needed) and used by the VIM to schedule the reservation.
	 * @param expiryTime Timestamp indicating the time the VIM can release the reservation in case no allocation request against this reservation was made.
	 * @param zoneId References the resource zone where the virtual network resources have been reserved.
	 */
	public ReservedVirtualNetwork(String reservationId,
			List<String> publicIps,
			VirtualNetworkAttributesReservationData networkAttributes,
			List<VirtualNetworkPortReservationData> networkPorts,
			ReservationStatus reservationStatus,
			Date startTime,
			Date endTime,
			Date expiryTime,
			String zoneId) {
		this.reservationId = reservationId;
		if (publicIps != null) this.publicIps = publicIps;
		this.networkAttributes = networkAttributes;
		if (networkPorts != null) this.networkPorts = networkPorts;
		this.reservationStatus = reservationStatus;
		this.startTime = startTime;
		this.endTime = endTime;
		this.expiryTime = expiryTime;
		this.zoneId = zoneId;
	}

	
	
	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}

	/**
	 * @return the publicIps
	 */
	public List<String> getPublicIps() {
		return publicIps;
	}

	/**
	 * @return the networkAttributes
	 */
	public VirtualNetworkAttributesReservationData getNetworkAttributes() {
		return networkAttributes;
	}

	/**
	 * @return the networkPorts
	 */
	public List<VirtualNetworkPortReservationData> getNetworkPorts() {
		return networkPorts;
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

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (reservationId == null) throw new MalformattedElementException("Reserved virtual network without reservation ID");
		if (networkAttributes != null) networkAttributes.isValid();
		for (VirtualNetworkPortReservationData vp : networkPorts) vp.isValid();
	}

}
