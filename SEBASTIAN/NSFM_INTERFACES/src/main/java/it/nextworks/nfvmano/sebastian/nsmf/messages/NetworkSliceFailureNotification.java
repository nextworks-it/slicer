/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.nsmf.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

public class NetworkSliceFailureNotification implements InterfaceMessage {

	private String nsiId;
	private String failureMessage;
	
	
	/**
	 * Constructor
	 * 
	 * @param nsiId ID of the network slice the notification refers to
	 * @param failureMessage description of the failure
	 */
	public NetworkSliceFailureNotification(String nsiId, String failureMessage) {
		this.nsiId = nsiId;
		this.failureMessage = failureMessage;
	}

	public NetworkSliceFailureNotification(){}

	public String getNsiId() {
		return nsiId;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsiId == null) throw new MalformattedElementException("Network slice failure notification without slice ID");
	}

}
