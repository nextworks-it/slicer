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
import it.nextworks.nfvmano.libs.ifa.common.enums.VnfScaleType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element describes the information needed, 
 * either to scale a VNF instance to a given level, 
 * or to scale a VNF instance by steps.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.9
 * 
 * @author nextworks
 *
 */
public class ScaleVnfData implements InterfaceInformationElement {

	private String vnfInstanceId;
	private VnfScaleType type;
	private ScaleVnfToLevelData scaleToLevelData;
	private ScaleVnfByStepData scaleByStepData;
	
	public ScaleVnfData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance being scaled.
	 * @param type Defines the type of the scale VNF operation requested
	 * @param scaleToLevelData Provides the information needed when scaling to a given level.
	 * @param scaleByStepData Provides the information needed when scaling by steps.
	 */
	public ScaleVnfData(String vnfInstanceId,
			VnfScaleType type,
			ScaleVnfToLevelData scaleToLevelData,
			ScaleVnfByStepData scaleByStepData) {
		this.vnfInstanceId = vnfInstanceId;
		this.type = type;
		this.scaleToLevelData = scaleToLevelData;
		this.scaleByStepData = scaleByStepData;
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
	 * @return the scaleToLevelData
	 */
	public ScaleVnfToLevelData getScaleToLevelData() {
		return scaleToLevelData;
	}

	/**
	 * @return the scaleByStepData
	 */
	public ScaleVnfByStepData getScaleByStepData() {
		return scaleByStepData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("ScaleVnfData without vnf instance ID");
		if (scaleToLevelData != null) scaleToLevelData.isValid();
		if (scaleByStepData != null) scaleByStepData.isValid();
	}

}
