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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.ScaleInfo;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The ScaleToLevelData information element describes the information 
 * needed to scale a VNF instance to a target size.
 * The target size is either expressed as an instantiation level of 
 * that DF as defined in the VNFD, or given as a list of scale
 * levels, one per scaling aspect of that DF. 
 * Instantiation levels and scaling aspects are declared in the VNFD. 
 * The NFVO shall then invoke the ScaleVnfToLevel operation towards the appropriate VNFM.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.10
 * 
 * @author nextworks
 *
 */
public class ScaleVnfToLevelData implements InterfaceInformationElement {

	private String instantiationLevelId;
	private List<ScaleInfo> scaleInfo = new ArrayList<>();
	private Map<String, String> additionalParam = new HashMap<>();
	
	public ScaleVnfToLevelData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param instantiationLevelId Identifier of (reference to) the target instantiation level of the current DF which the VNF instance is requested to be scaled.
	 * @param scaleInfo For each scaling aspect of the current DF, defines the target scale level to which the VNF instance is to be scaled.
	 * @param additionalParam Additional parameters passed by the NFVO as input to the scaling process, specific to the VNF instance being scaled.
	 */
	public ScaleVnfToLevelData(String instantiationLevelId,
			List<ScaleInfo> scaleInfo,
			Map<String, String> additionalParam) {
		this.instantiationLevelId = instantiationLevelId;
		if (scaleInfo != null) this.scaleInfo = scaleInfo;
		if (additionalParam != null) this.additionalParam = additionalParam;
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
		if(scaleInfo != null) {
			for (ScaleInfo s: scaleInfo) s.isValid();
		}
	}

}
