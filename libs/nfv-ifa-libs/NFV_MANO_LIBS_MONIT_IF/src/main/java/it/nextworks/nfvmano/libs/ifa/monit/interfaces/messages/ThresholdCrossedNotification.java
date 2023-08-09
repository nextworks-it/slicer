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
import it.nextworks.nfvmano.libs.ifa.common.enums.ThresholdCrossingDirection;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This notification informs the receiver that a threshold value has been crossed.
 * 
 * REF IFA 013 v2.3.1 - 7.5.9
 * REF IFA 007 v2.3.1 - 7.4.5 - 8.7.9
 * 
 * 
 * @author nextworks
 *
 */
public class ThresholdCrossedNotification implements InterfaceMessage {

	private String thresholdId;
	private ThresholdCrossingDirection crossingDirection;
	private String objectInstanceId;
	private String performanceMetric;
	private int performanceValue;
	
	public ThresholdCrossedNotification() {	}
	
	/**
	 * Constructor
	 * 
	 * @param thresholdId Threshold which has been crossed
	 * @param crossingDirection An indication of whether the threshold was crossed in upward or downward direction.
	 * @param objectInstanceId Object instance for which the threshold has been crossed.
	 * @param performanceMetric Performance metric associated with the threshold.
	 * @param performanceValue Value of the metric that resulted in threshold crossing.
	 */
	public ThresholdCrossedNotification(String thresholdId,
			ThresholdCrossingDirection crossingDirection,
			String objectInstanceId,
			String performanceMetric,
			int performanceValue) {
		this.thresholdId = thresholdId;
		this.crossingDirection = crossingDirection;
		this.objectInstanceId = objectInstanceId;
		this.performanceMetric = performanceMetric;
		this.performanceValue = performanceValue;
	}
	
	

	/**
	 * @return the thresholdId
	 */
	public String getThresholdId() {
		return thresholdId;
	}

	/**
	 * @return the crossingDirection
	 */
	public ThresholdCrossingDirection getCrossingDirection() {
		return crossingDirection;
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
	public int getPerformanceValue() {
		return performanceValue;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (thresholdId == null) throw new MalformattedElementException("Threshold crossed notification without threshold ID");
		if (objectInstanceId == null) throw new MalformattedElementException("Threshold crossed notification without object instance ID");
		if (performanceMetric == null) throw new MalformattedElementException("Threshold crossed notification without performance metric");
	}

}
