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

import java.util.Date;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element defines a single performance value with its associated time stamp.
 * 
 * REF IFA 005 v2.1.1 - 8.5.7
 * REF IFA 013 v2.3.1 - 8.4.7
 * 
 * 
 * @author nextworks
 *
 */
public class PerformanceValueEntry implements InterfaceInformationElement {

	private Date timeStamp;
	private String performanceValue;
	
	public PerformanceValueEntry() { }
	
	/**
	 * Constructor
	 * 
	 * @param timeStamp Timestamp indicating when the data was collected.
	 * @param performanceValue Value of the metric collected.
	 */
	public PerformanceValueEntry(Date timeStamp,
			String performanceValue) {
		this.timeStamp = timeStamp;
		this.performanceValue = performanceValue;
	}

	
	
	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @return the performanceValue
	 */
	public String getPerformanceValue() {
		return performanceValue;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (timeStamp == null) throw new MalformattedElementException("Performance value without timestamp");
		if (performanceValue == null) throw new MalformattedElementException("Performance value without value");
	}

}
