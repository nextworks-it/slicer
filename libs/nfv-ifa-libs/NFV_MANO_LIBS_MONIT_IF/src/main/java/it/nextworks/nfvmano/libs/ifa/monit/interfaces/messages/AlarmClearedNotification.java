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

import java.util.Date;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This notification informs the receiver of the clearing of an alarm. 
 * The alarm's perceived severity 
 * has been set to "cleared" since the corresponding fault has been solved
 * 
 * REF IFA 005 v2.1.1 - 8.6.3
 * REF IFA 013 v2.3.1 - 7.6.3 - 8.5.3
 * REF IFA 007 v2.3.1 - 7.5.3 - 8.8.3
 * 
 * @author nextworks
 *
 */
public class AlarmClearedNotification implements InterfaceMessage {

	private String alarmId;
	private Date alarmClearedTime;
	
	public AlarmClearedNotification() {	}
	
	/**
	 * Constructor
	 * 
	 * @param alarmId Alarm identifier
	 * @param alarmClearedTime The date and time the alarm was cleared
	 */
	public AlarmClearedNotification(String alarmId,
			Date alarmClearedTime) {	
		this.alarmId = alarmId;
		this.alarmClearedTime = alarmClearedTime;
	}

	
	
	/**
	 * @return the alarmId
	 */
	public String getAlarmId() {
		return alarmId;
	}

	/**
	 * @return the alarmClearedTime
	 */
	public Date getAlarmClearedTime() {
		return alarmClearedTime;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (alarmId == null) throw new MalformattedElementException("Alarm cleared notification without alarm ID");
		if (alarmClearedTime == null) throw new MalformattedElementException("Alarm cleared notification without cleared time");
	}

}
