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
package it.nextworks.nfvmano.libs.ifa.orvnfm.vnflcm.interfaces.messages;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.VnfScaleType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to scale a VNF.
 * 
 * REF IFA 007 v2.3.1 - 7.2.4
 * 
 * @author nextworks
 *
 */
public class ScaleVnfRequest implements InterfaceMessage {

	private String vnfInstanceId;
	private VnfScaleType type;
	private String aspectId;
	private int numberOfSteps;
	private Map<String, String> additionalParam = new HashMap<>();
	
	public ScaleVnfRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance to which this scaling request is related.
	 * @param type Defines the type of the scale operation requested
	 * @param aspectId Identifies the aspect of the VNF that is requested to be scaled, as declared in the VNFD.
	 * @param numberOfSteps Number of scaling steps to be executed as part of this ScaleVnf operation.
	 * @param addtionalParam Additional parameters passed by the NFVO as input to the scaling process, specific to the VNF being scaled.
	 */
	public ScaleVnfRequest(String vnfInstanceId,
			VnfScaleType type,
			String aspectId,
			int numberOfSteps,
			Map<String, String> addtionalParam) { 
		this.vnfInstanceId = vnfInstanceId;
		this.type = type;
		this.aspectId = aspectId;
		this.numberOfSteps = numberOfSteps;
		if (addtionalParam != null) this.additionalParam = addtionalParam;
	}
	
	

	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the type
	 */
	public VnfScaleType getType() {
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
		if (vnfInstanceId == null) throw new MalformattedElementException("Scale VNF request without VNF instance ID");
		if (aspectId == null) throw new MalformattedElementException("Scale VNF request without aspect ID");
	}

}
