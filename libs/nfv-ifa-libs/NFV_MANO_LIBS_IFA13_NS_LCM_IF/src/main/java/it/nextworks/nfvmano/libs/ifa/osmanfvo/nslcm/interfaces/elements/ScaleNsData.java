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
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.ScaleNsToLevelData;

/**
 * The ScaleNsData information element describes the information needed to scale an NS instance 
 * either by explicitly adding/removing existing VNF instances or by leveraging on the abstraction 
 * mechanism provided by the NS scaling aspects and NS levels information elements declared in the NSD.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.6
 * 
 * @author nextworks
 *
 */
public class ScaleNsData implements InterfaceInformationElement {

	private List<VnfInstanceData> vnfInstanceToBeAdded = new ArrayList<>();
	private List<String> vnfInstanceToBeRemoved = new ArrayList<>();
	private ScaleNsByStepsData scaleNsByStepsData;
	private ScaleNsToLevelData scaleNsToLevelData;
	private Map<String, String> additionalParamForNs = new HashMap<>();
	private List<ParamsForVnf> additionalParamForVnf = new ArrayList<>();
	private List<VnfLocationConstraints> locationConstraints = new ArrayList<>();
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceToBeAdded Specifies an existing VNF instance to be added to the NS instance as part of the scaling operation. If needed, the VNF Profile to be used for this VNF instance is also provided.
	 * @param vnfInstanceToBeRemoved Specifies a VNF instance to be removed from the NS instance as part of the scaling operation.
	 * @param scaleNsByStepsData Specifies the information needed to scale an NS instance by one or more scaling steps.
	 * @param scaleNsToLevelData Specifies the information needed to scale an NS instance to a target size.
	 * @param additionalParamForNs Allows the OSS/BSS to provide additional parameter(s) at the NS level necessary for the NS scaling
	 * @param additionalParamForVnf Allows the OSS/BSS to provide additional parameter(s) per VNF instance
	 * @param locationConstraints Defines the location constraints for the VNF to be instantiated as part of the NS scaling.
	 */
	public ScaleNsData(List<VnfInstanceData> vnfInstanceToBeAdded,
			List<String> vnfInstanceToBeRemoved,
			ScaleNsByStepsData scaleNsByStepsData,
			ScaleNsToLevelData scaleNsToLevelData,
			Map<String, String> additionalParamForNs,
			List<ParamsForVnf> additionalParamForVnf,
			List<VnfLocationConstraints> locationConstraints) {
		if (vnfInstanceToBeAdded != null) this.vnfInstanceToBeAdded = vnfInstanceToBeAdded;
		if (vnfInstanceToBeRemoved != null) this.vnfInstanceToBeRemoved = vnfInstanceToBeRemoved;
		this.scaleNsByStepsData = scaleNsByStepsData;
		this.scaleNsToLevelData = scaleNsToLevelData;
		if (additionalParamForNs != null) this.additionalParamForNs = additionalParamForNs;
		if (additionalParamForVnf != null) this.additionalParamForVnf = additionalParamForVnf;
		if (locationConstraints != null) this.locationConstraints = locationConstraints;
	}
	
	public ScaleNsData() {	}
	
	

	/**
	 * @return the vnfInstanceToBeAdded
	 */
	public List<VnfInstanceData> getVnfInstanceToBeAdded() {
		return vnfInstanceToBeAdded;
	}

	/**
	 * @return the vnfInstanceToBeRemoved
	 */
	public List<String> getVnfInstanceToBeRemoved() {
		return vnfInstanceToBeRemoved;
	}

	/**
	 * @return the scaleNsByStepsData
	 */
	public ScaleNsByStepsData getScaleNsByStepsData() {
		return scaleNsByStepsData;
	}

	/**
	 * @return the scaleNsToLevelData
	 */
	public ScaleNsToLevelData getScaleNsToLevelData() {
		return scaleNsToLevelData;
	}

	/**
	 * @return the additionalParamForNs
	 */
	public Map<String, String> getAdditionalParamForNs() {
		return additionalParamForNs;
	}

	/**
	 * @return the additionalParamForVnf
	 */
	public List<ParamsForVnf> getAdditionalParamForVnf() {
		return additionalParamForVnf;
	}

	/**
	 * @return the locationConstraints
	 */
	public List<VnfLocationConstraints> getLocationConstraints() {
		return locationConstraints;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceToBeAdded != null) {
			for (VnfInstanceData v : vnfInstanceToBeAdded) v.isValid();
		}
		if (scaleNsByStepsData != null) scaleNsByStepsData.isValid();
		if (scaleNsToLevelData != null) scaleNsToLevelData.isValid();
		if (additionalParamForVnf != null) {
			for (ParamsForVnf p : additionalParamForVnf) p.isValid();
		}
		if (locationConstraints != null) {
			for (VnfLocationConstraints l:locationConstraints) l.isValid();
		}
 	}

}
