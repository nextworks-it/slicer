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

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.elements.Alarm;


/**
 * This notification informs the receiver of an alarm. 
 * 
 * REF IFA 005 v2.1.1 - 8.6.2
 * REF IFA 013 v2.3.1 - 7.6.3 - 8.5.2
 * REF IFA 007 v2.3.1 - 7.5.3 - 8.8.2
 * 
 * @author nextworks
 *
 */
public class AlarmNotification implements InterfaceMessage {

	private Alarm alarm;
	
	public AlarmNotification() { }
	
	/**
	 * Constructor
	 * 
	 * @param alarm Information about an alarm including AlarmId, affected virtualised resource identifier, and FaultDetails.
	 */
	public AlarmNotification(Alarm alarm) { 
		this.alarm = alarm;
	}
	
	/**
	 * @return the alarm
	 */
	public Alarm getAlarm() {
		return alarm;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (alarm == null) throw new MalformattedElementException("Alarm notification without alarm");
		else alarm.isValid();
	}

}
