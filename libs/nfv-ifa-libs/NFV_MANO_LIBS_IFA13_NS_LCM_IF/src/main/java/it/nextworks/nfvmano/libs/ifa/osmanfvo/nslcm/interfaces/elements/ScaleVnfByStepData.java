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

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.enums.ScalingDirection;

/**
 * The ScaleByStepData information element describes the information 
 * needed to scale a VNF instance by steps. The NFVO shall then invoke 
 * the ScaleVNF operation towards the appropriate VNFM.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.11
 * 
 * @author nextworks
 *
 */
public class ScaleVnfByStepData implements InterfaceInformationElement {

	private ScalingDirection type;
	private String aspectId;
	private int numberOfSteps;
	private Map<String, String> additionalParam = new HashMap<>();
	
	/**
	 * Constructor
	 * 
	 * @param type Defines the type of the scale operation requested (scale out, scale in).
	 * @param aspectId Identifier of (reference to) the aspect of the VNF that is requested to be scaled, as declared in the VNFD.
	 * @param numberOfSteps Number of scaling steps. It shall be a positive number. Defaults to 1.
	 * @param additionalParam Additional parameters passed by the NFVO as input to the scaling process, specific to the VNF instance being scaled.
	 */
	public ScaleVnfByStepData(ScalingDirection type,
			String aspectId,
			int numberOfSteps,
			Map<String, String> additionalParam) {
		this.type = type;
		this.aspectId = aspectId;
		this.numberOfSteps = numberOfSteps;
		if (additionalParam != null) this.additionalParam = additionalParam;
	}
	
	public ScaleVnfByStepData() {	}
	
	

	/**
	 * @return the type
	 */
	public ScalingDirection getType() {
		return type;
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

	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (aspectId == null) throw new MalformattedElementException("Scale By Step Data without aspect ID");
	}

}
