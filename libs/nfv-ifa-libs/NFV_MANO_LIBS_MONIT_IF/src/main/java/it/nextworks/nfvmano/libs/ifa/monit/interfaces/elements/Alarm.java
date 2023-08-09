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
package it.nextworks.nfvmano.libs.ifa.monit.interfaces.elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.enums.AlarmSeverity;
import it.nextworks.nfvmano.libs.ifa.common.enums.AlarmState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The Alarm information element encapsulates information about an alarm.
 *  
 * REF IFA 013 v2.3.1 - 8.5.4 
 * REF IFA 005 v2.1.1 - 8.6.4
 * REF IFA 007 v2.3.1 - 8.8.4
 * 
 * @author nextworks
 *
 */
public class Alarm implements InterfaceInformationElement {

	private String alarmId;
	private String managedObjectId;
	private FaultyResourceInfo rootCauseFaultyResource;
	private Date alarmRaisedTime;
	private Date alarmChangedTime;
	private Date alarmClearedTime;
	private AlarmState state;
	private AlarmSeverity perceivedSeverity;
	private Date eventTime;
	private String faultType;
	private String probableCause;
	private boolean isRootCause;
	private List<String> correlatedAlarmId = new ArrayList<String>();
	private List<String> faultDetails = new ArrayList<String>();
	
	public Alarm() { }
	
	/**
	 * Constructor
	 * 
	 * @param alarmId Alarm identifier.
	 * @param managedObjectId Identifier of the affected managed Object. The Managed Objects for this information element will be virtualised resources.
	 * @param rootCauseFaultyResource The virtualised resources that are causing the VNF fault.
	 * @param alarmRaisedTime It indicates the date and time when the alarm is first raised by the managed object.
	 * @param alarmChangedTime The last date and time when the alarm was changed.
	 * @param alarmClearedTime The date and time the alarm was cleared.
	 * @param state State of the alarm.
	 * @param perceivedSeverity Perceived severity of the managed object failure.
	 * @param eventTime Time when the fault was observed.
	 * @param faultType Type of the fault.
	 * @param probableCause Information about the probable cause of the fault.
	 */
	public Alarm(String alarmId,
			String managedObjectId,
			FaultyResourceInfo rootCauseFaultyResource,
			Date alarmRaisedTime,
			Date alarmChangedTime,
			Date alarmClearedTime,
			AlarmState state,
			AlarmSeverity perceivedSeverity,
			Date eventTime,
			String faultType,
			String probableCause,
			boolean isRootCause,
			List<String> correlatedAlarmId,
			List<String> faultDetails) { 
		this.alarmId = alarmId;
		this.managedObjectId = managedObjectId;
		this.rootCauseFaultyResource = rootCauseFaultyResource;
		this.alarmRaisedTime = alarmRaisedTime;
		this.alarmChangedTime = alarmChangedTime;
		this.alarmClearedTime = alarmClearedTime;
		this.state = state;
		this.perceivedSeverity = perceivedSeverity;
		this.eventTime = eventTime;
		this.faultType = faultType;
		this.probableCause = probableCause;
		this.isRootCause = isRootCause;
		if (correlatedAlarmId != null) this.correlatedAlarmId = correlatedAlarmId;
		if (faultDetails != null) this.faultDetails = faultDetails;
	}
	
	

	/**
	 * @return the alarmId
	 */
	public String getAlarmId() {
		return alarmId;
	}

	/**
	 * @return the managedObjectId
	 */
	public String getManagedObjectId() {
		return managedObjectId;
	}

	/**
	 * @return the alarmRaisedTime
	 */
	public Date getAlarmRaisedTime() {
		return alarmRaisedTime;
	}

	/**
	 * @return the alarmChangedTime
	 */
	public Date getAlarmChangedTime() {
		return alarmChangedTime;
	}

	/**
	 * @return the alarmClearedTime
	 */
	public Date getAlarmClearedTime() {
		return alarmClearedTime;
	}

	/**
	 * @return the state
	 */
	public AlarmState getState() {
		return state;
	}

	/**
	 * @return the perceivedSeverity
	 */
	public AlarmSeverity getPerceivedSeverity() {
		return perceivedSeverity;
	}

	/**
	 * @return the eventTime
	 */
	public Date getEventTime() {
		return eventTime;
	}

	/**
	 * @return the faultType
	 */
	public String getFaultType() {
		return faultType;
	}
	
	

	/**
	 * @return the probableCause
	 */
	public String getProbableCause() {
		return probableCause;
	}

	/**
	 * @return the isRootCause
	 */
	public boolean isRootCause() {
		return isRootCause;
	}

	/**
	 * @return the correlatedAlarmId
	 */
	public List<String> getCorrelatedAlarmId() {
		return correlatedAlarmId;
	}

	/**
	 * @return the faultDetails
	 */
	public List<String> getFaultDetails() {
		return faultDetails;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (alarmId == null) throw new MalformattedElementException("Alarm without ID");
		if (rootCauseFaultyResource != null) rootCauseFaultyResource.isValid();
		if (managedObjectId == null) throw new MalformattedElementException("Alarm without managed object ID");
		if (alarmRaisedTime == null) throw new MalformattedElementException("Alarm without raised time");
		if (eventTime == null) throw new MalformattedElementException("Alarm without event time");
		if (probableCause == null) throw new MalformattedElementException("Alarm without probable cause");
	}

}
