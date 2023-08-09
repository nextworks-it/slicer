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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements;


import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.enums.ScalingDirection;

/**
 * The ScaleNsByStepsData information element describes the information needed 
 * to scale an NS instance by one or more scaling steps, with respect 
 * to a particular NS scaling aspect. 
 * 
 * Performing a scaling step means increasing/decreasing the capacity of an NS 
 * instance in a discrete manner, i.e. moving from one NS scale level to another. 
 * The NS scaling aspects and their corresponding NS scale levels applicable 
 * to the NS instance are declared in the NSD.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.7
 * 
 * @author nextworks
 *
 */
public class ScaleNsByStepsData implements InterfaceInformationElement {

	private ScalingDirection scalingDirection;
	private String aspectId;
	private int numberOfSteps;
	
	public ScaleNsByStepsData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param scalingDirection Specifies the scaling direction.
	 * @param aspectId Provides the aspect of the NS that is requested to be scaled, as declared in the NSD. (Ref. to NsScalingAspect)
	 * @param numberOfSteps Specifies the number of scaling steps to be performed. Defaults to 1.
	 */
	public ScaleNsByStepsData(ScalingDirection scalingDirection,
			String aspectId,
			int numberOfSteps) {
		this.scalingDirection = scalingDirection;
		this.aspectId = aspectId;
		this.numberOfSteps = numberOfSteps;
	}
	
	

	/**
	 * @return the scalingDirection
	 */
	public ScalingDirection getScalingDirection() {
		return scalingDirection;
	}

	/**
	 * @return the aspectId
	 */
	public String getAspectId() {
		return aspectId;
	}

	/**
	 * @return the numberOfSteps
	 */
	public int getNumberOfSteps() {
		return numberOfSteps;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.aspectId == null) throw new MalformattedElementException("ScaleNS by steps without aspect ID.");
	}

}
