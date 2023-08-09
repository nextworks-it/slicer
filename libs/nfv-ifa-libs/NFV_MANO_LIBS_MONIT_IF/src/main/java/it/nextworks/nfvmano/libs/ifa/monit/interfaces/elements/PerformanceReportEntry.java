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
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.enums.MonitoringObjectType;

/**
 * This information element defines a single performance report entry. 
 * This performance report entry is for a given metric of a given object instance, 
 * but can include multiple collected values.
 * 
 * REF IFA 005 v2.1.1 - 8.5.6
 * REF IFA 013 v2.3.1 - 8.4.6
 * 
 * 
 * @author nextworks
 *
 */
public class PerformanceReportEntry implements InterfaceInformationElement {

	private MonitoringObjectType objectType;
	private String objectInstanceId;
	private String performanceMetric;
	private List<PerformanceValueEntry> performanceValue = new ArrayList<>();
	
	public PerformanceReportEntry() { }

	/**
	 * Constructor
	 * 
	 * @param objectType Defines the object type
	 * @param objectInstanceId The object instance for which the performance metric is reported.
	 * @param performanceMetric Name of the metric collected
	 * @param performanceValue List of performance values with associated timestamp.
	 */
	public PerformanceReportEntry(MonitoringObjectType objectType,
			String objectInstanceId,
			String performanceMetric,
			List<PerformanceValueEntry> performanceValue) { 
		this.objectType = objectType;
		this.objectInstanceId = objectInstanceId;
		this.performanceMetric = performanceMetric;
		if (performanceValue != null) this.performanceValue = performanceValue;
	}
	
	
	
	/**
	 * @return the objectType
	 */
	public MonitoringObjectType getObjectType() {
		return objectType;
	}

	/**
	 * @return the objectInstanceId
	 */
	public String getObjectInstanceId() {
		return objectInstanceId;
	}

	/**
	 * @return the performanceMetric
	 */
	public String getPerformanceMetric() {
		return performanceMetric;
	}

	/**
	 * @return the performanceValue
	 */
	public List<PerformanceValueEntry> getPerformanceValue() {
		return performanceValue;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (objectInstanceId == null) throw new MalformattedElementException("Performance report entry without object instance ID");
		if (performanceMetric == null) throw new MalformattedElementException("Performance report entry without metric name");
		if ((performanceValue == null) || (performanceValue.isEmpty()))
			throw new MalformattedElementException("Performance entry without value");
		else {
			for (PerformanceValueEntry e : performanceValue) e.isValid();
		}
	}

}
