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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages;

import java.util.Date;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Message to request the termination of a NS.
 * 
 * REF IFA 013 v2.3.1 - 7.3.7
 * 
 * @author nextworks
 *
 */
public class TerminateNsRequest implements InterfaceMessage {

	private String nsInstanceId;
	private Date terminateTime;
	
	public TerminateNsRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId Identifier of the NS instance to terminate.
	 * @param terminateTime Timestamp indicating the end time of the NS, i.e. the NS will be terminated automatically at this timestamp. If not present the NS termination takes place immediately.
	 */
	public TerminateNsRequest(String nsInstanceId, Date terminateTime) {
		this.nsInstanceId  = nsInstanceId;
		this.terminateTime = terminateTime;
	}

	/**
	 * @return the nsInstanceId
	 */
	public String getNsInstanceId() {
		return nsInstanceId;
	}



	/**
	 * @return the terminateTime
	 */
	public Date getTerminateTime() {
		return terminateTime;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (nsInstanceId == null) throw new MalformattedElementException("Terminate NS request without NS Instance ID");
	}
	
}
