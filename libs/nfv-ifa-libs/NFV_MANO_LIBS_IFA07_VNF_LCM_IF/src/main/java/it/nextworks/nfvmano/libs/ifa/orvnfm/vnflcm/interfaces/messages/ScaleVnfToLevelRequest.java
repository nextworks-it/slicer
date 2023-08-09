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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.elements.ScaleInfo;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to scale the VNF to a target level.
 * 
 * REF IFA 007 v2.3.1 - 7.2.5
 * 
 * @author nextworks
 *
 */
public class ScaleVnfToLevelRequest implements InterfaceMessage {

	private String vnfInstanceId;
	private String instantiationLevelId;
	private List<ScaleInfo> scaleInfo = new ArrayList<>();
	private Map<String, String> additionalParam = new HashMap<>();
	
	public ScaleVnfToLevelRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance to which this scaling request is related.
	 * @param instantiationLevelId Identifier of the target instantiation level of the current DF to which the VNF is requested to be scaled.
	 * @param scaleInfo For each scaling aspect of the current DF, defines the target scale level to which the VNF is to be scaled.
	 * @param additionalParam Additional parameters passed by the NFVO as input to the scaling process, specific to the VNF being scaled.
	 */
	public ScaleVnfToLevelRequest(String vnfInstanceId,
			String instantiationLevelId,
			List<ScaleInfo> scaleInfo,
			Map<String, String> additionalParam) { 
		this.vnfInstanceId = vnfInstanceId;
		this.instantiationLevelId = instantiationLevelId;
		if (scaleInfo != null) this.scaleInfo = scaleInfo;
	}

	
	
	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the instantiationLevelId
	 */
	public String getInstantiationLevelId() {
		return instantiationLevelId;
	}

	/**
	 * @return the scaleInfo
	 */
	public List<ScaleInfo> getScaleInfo() {
		return scaleInfo;
	}

	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Scale VNF to level request without VNF instance ID");
		for (ScaleInfo si : scaleInfo) si.isValid();
	}

}
