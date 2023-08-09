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
package it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This notification informs the receiver that performance information is available.
 * 
 * REF IFA 013 v2.3.1 - 7.5.5 - 8.4.8
 * REF IFA 007 v2.3.1 - 7.4.5 - 8.7.8
 * 
 * @author nextworks
 *
 */
public class PerformanceInformationAvailableNotification implements InterfaceMessage {

	private List<String> objectInstanceId = new ArrayList<>();
	
	public PerformanceInformationAvailableNotification() { }
	
	/**
	 * Constructor
	 * 
	 * @param objectInstanceId Object instances for which performance information is available.
	 */
	public PerformanceInformationAvailableNotification(List<String> objectInstanceId) {
		if (objectInstanceId != null) this.objectInstanceId = objectInstanceId;
	}
	
	

	/**
	 * @return the objectInstanceId
	 */
	public List<String> getObjectInstanceId() {
		return objectInstanceId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((objectInstanceId == null) || (objectInstanceId.isEmpty())) throw new MalformattedElementException("Performance info available notification without object instance ID");
	}

}
