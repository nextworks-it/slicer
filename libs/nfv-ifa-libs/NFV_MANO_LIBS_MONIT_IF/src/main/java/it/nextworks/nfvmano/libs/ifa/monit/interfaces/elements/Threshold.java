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
import it.nextworks.nfvmano.libs.ifa.common.enums.ThresholdType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides the details of a threshold.
 * 
 * REF IFA 013 v2.3.1 - 7.5.9
 * REF IFA 005 v2.1.1 - 8.5.4
 * REF IFA 007 v2.3.1 - 8.7.8
 * 
 * @author nextworks
 *
 */
public class Threshold implements InterfaceInformationElement {

	private String thresholdId;
	private List<ObjectSelection> objectSelector = new ArrayList<>();
	private String performanceMetric;
	private ThresholdType thresholdType;
	private ThresholdDetails thresholdDetails;
	
	public Threshold() { }
	
	/**
	 * Constructor
	 * 
	 * @param thresholdId Id of threshold.
	 * @param objectSelector Defines the object instances associated with the threshold.
	 * @param performanceMetric Defines the performance metric associated with the threshold
	 * @param thresholdType Type of threshold.
	 * @param thresholdDetails Details of the threshold: value to be crossed, details on the notification to be generated.
	 */
	public Threshold(String thresholdId,
			List<ObjectSelection> objectSelector,
			String performanceMetric,
			ThresholdType thresholdType,
			ThresholdDetails thresholdDetails) {
		this.thresholdId = thresholdId;
		this.performanceMetric = performanceMetric;
		if (objectSelector != null) this.objectSelector = objectSelector;
		this.thresholdType = thresholdType;
		this.thresholdDetails = thresholdDetails;
	}

	
	
	/**
	 * @return the thresholdId
	 */
	public String getThresholdId() {
		return thresholdId;
	}

	/**
	 * @return the objectSelector
	 */
	public List<ObjectSelection> getObjectSelector() {
		return objectSelector;
	}

	/**
	 * @return the performanceMetric
	 */
	public String getPerformanceMetric() {
		return performanceMetric;
	}

	/**
	 * @return the thresholdType
	 */
	public ThresholdType getThresholdType() {
		return thresholdType;
	}

	/**
	 * @return the thresholdDetails
	 */
	public ThresholdDetails getThresholdDetails() {
		return thresholdDetails;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (thresholdId == null) throw new MalformattedElementException("Threshold without ID");
		if (performanceMetric == null) throw new MalformattedElementException("Threshold without performance metrics");
		if (thresholdDetails == null) throw new MalformattedElementException("Threshold without details");
		if ((objectSelector == null) || (objectSelector.isEmpty())) throw new MalformattedElementException("Threshold without object selector");
		else {
			for (ObjectSelection os : objectSelector) os.isValid();
		}
	}

}
