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
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Response to a terminate resource reservation request.
 * 
 * REF IFA 005 v2.3.1 - 7.8.1.5
 * REF IFA 005 v2.3.1 - 7.8.2.5
 * REF IFA 005 v2.3.1 - 7.8.3.5
 * 
 * @author nextworks
 *
 */
public class TerminateResourceReservationResponse implements InterfaceMessage {

	private List<String> reservationId = new ArrayList<>();
	
	public TerminateResourceReservationResponse() {	}
	
	public TerminateResourceReservationResponse(List<String> reservationId) {
		if (reservationId != null) this.reservationId = reservationId;
	}

	/**
	 * @return the reservationId
	 */
	public List<String> getReservationId() {
		return reservationId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((reservationId == null) || (reservationId.isEmpty())) throw new MalformattedElementException("Terminate resource reservation response withoud IDs");
	}

}
