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
package it.nextworks.nfvmano.libs.common.elements;

import java.util.Date;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * This information element specifies a time period for which capacity is
 * queried. REF IFA 005 v2.3.1 - 8.7.2
 * 
 * @author nextworks
 *
 */
public class TimePeriodInformation implements DescriptorInformationElement {

	private Date startTime;
	private Date stopTime;

	public TimePeriodInformation() {
	}

	/**
	 * Constructor
	 * 
	 * @param startTime The start time for which capacity is queried
	 * @param stopTime  The stop time for which capacity is queried
	 */
	public TimePeriodInformation(Date startTime, Date stopTime) {
		this.startTime = startTime;
		this.stopTime = stopTime;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @return the stopTime
	 */
	public Date getStopTime() {
		return stopTime;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (startTime == null)
			throw new MalformattedElementException("Time period information without start time");
		if (stopTime == null)
			throw new MalformattedElementException("Time period information without stop time");
	}

}
