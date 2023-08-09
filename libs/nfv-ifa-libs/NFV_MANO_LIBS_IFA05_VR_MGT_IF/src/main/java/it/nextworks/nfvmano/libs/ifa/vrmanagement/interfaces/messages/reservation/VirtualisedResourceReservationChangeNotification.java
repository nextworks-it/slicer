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

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Notification about a change in a virtual resource reservation.
 * 
 * REF IFA 005 v2.3.1 - 8.8.7
 * 
 * @author nextworks
 *
 */
public class VirtualisedResourceReservationChangeNotification implements InterfaceMessage {

	private String changeId;
	private String reservationId;
	private String vimId;
	private String changeType;
	private Map<String, String> changedReservationData = new HashMap<>();
	
	public VirtualisedResourceReservationChangeNotification() {	}

	
	/**
	 * Constructor
	 * 
	 * @param changeId Unique identifier of the change on the virtualised resource reservation
	 * @param reservationId The reservation being changed.
	 * @param vimId The VIM reporting the change.
	 * @param changeType It categorizes the type of change. Possible values can be related to an update of the reservation or a change in the resources part of the reservation.
	 * @param changedReservationData Details of the changes of the reservation.
	 */
	public VirtualisedResourceReservationChangeNotification(String changeId, String reservationId, String vimId,
			String changeType, Map<String, String> changedReservationData) {
		this.changeId = changeId;
		this.reservationId = reservationId;
		this.vimId = vimId;
		this.changeType = changeType;
		this.changedReservationData = changedReservationData;
	}



	/**
	 * @return the changeId
	 */
	public String getChangeId() {
		return changeId;
	}



	/**
	 * @return the reservationId
	 */
	public String getReservationId() {
		return reservationId;
	}



	/**
	 * @return the vimId
	 */
	public String getVimId() {
		return vimId;
	}



	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}



	/**
	 * @return the changedReservationData
	 */
	public Map<String, String> getChangedReservationData() {
		return changedReservationData;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (changeId == null) throw new MalformattedElementException("Virtual resource reservation change notification without change ID.");
		if (reservationId == null) throw new MalformattedElementException("Virtual resource reservation change notification without reservation ID.");
		if (vimId == null) throw new MalformattedElementException("Virtual resource reservation change notification without VIM ID.");
		if (changeType == null) throw new MalformattedElementException("Virtual resource reservation change notification without change type.");
	}

}
