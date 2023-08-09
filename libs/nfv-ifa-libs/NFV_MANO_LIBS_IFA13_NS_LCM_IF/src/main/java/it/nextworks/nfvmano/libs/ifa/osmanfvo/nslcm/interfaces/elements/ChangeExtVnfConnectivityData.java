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
import it.nextworks.nfvmano.libs.ifa.common.elements.ExtVirtualLinkData;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The ChangeExtVnfConnectivityData information element specifies the external connectivity 
 * to change for the VNF.
 * The types of changes that this operation supports are:
 * - Disconnect the external CPs that are connected to a particular external VL, 
 * and connect them to a different external VL.
 * - Change the connectivity parameters of the existing external CPs, including changing addresses.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.29
 * 
 * @author nextworks
 *
 */
public class ChangeExtVnfConnectivityData implements InterfaceInformationElement {

	private String vnfInstanceId;
	private List<ExtVirtualLinkData> extVirtualLink = new ArrayList<>();
	private Map<String, String> additionalParam = new HashMap<>();
	
	public ChangeExtVnfConnectivityData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance.
	 * @param extVirtualLink Information about external VLs to change (e.g. connect the VNF to).
	 * @param additionalParam Additional parameters passed by the OSS as input to the external connectivity change process, specific to the VNF being changed, as declared in the VNFD
	 */
	public ChangeExtVnfConnectivityData(String vnfInstanceId,
			List<ExtVirtualLinkData> extVirtualLink,
			Map<String, String> additionalParam) {	
		this.vnfInstanceId = vnfInstanceId;
		if (extVirtualLink != null) this.extVirtualLink = extVirtualLink;
		if (additionalParam != null) this.additionalParam = additionalParam;
	}
	
	

	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}

	/**
	 * @return the extVirtualLink
	 */
	public List<ExtVirtualLinkData> getExtVirtualLink() {
		return extVirtualLink;
	}

	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Change ext VNF connectivity data without VNF ID.");
		if ((extVirtualLink == null) || (extVirtualLink.isEmpty())) throw new MalformattedElementException("Change ext VNF connectivity data without external virtual links.");
		else {
			for (ExtVirtualLinkData vl : extVirtualLink) vl.isValid();
		}
	}

}
