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
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.elements.Alarm;


/**
 * This operation enables the service consumer to query the active alarms.
 * 
 * REF IFA 013 v2.3.1 - 7.6.4
 * REF IFA 007 v2.3.1 - 7.5.4
 * REF IFA 005 v2.3.1 - 7.6.4
 * 
 * @author nextworks
 *
 */
public class GetAlarmListResponse implements InterfaceMessage {
	
	private List<Alarm> alarm = new ArrayList<>();

	public GetAlarmListResponse() {	}
	
	/**
	 * Constructor
	 * 
	 * @param alarm Information about the alarms
	 */
	public GetAlarmListResponse(List<Alarm> alarm) {	
		if (alarm != null) this.alarm = alarm;
	}
	
	

	/**
	 * @return the alarm
	 */
	public List<Alarm> getAlarm() {
		return alarm;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		for (Alarm a : alarm) a.isValid();
	}

}
