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
import it.nextworks.nfvmano.libs.ifa.common.elements.ExtManagedVirtualLinkData;
import it.nextworks.nfvmano.libs.ifa.common.elements.ExtVirtualLinkData;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The ChangeVnfFlavourData specifies existing VNF instance for which 
 * the DF needs to be changed. This specifies the new DF, the instantiationLevel 
 * of the new DF that may be used and the additional parameters as input for 
 * the flavour change.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.15
 * 
 * @author nextworks
 *
 */
public class ChangeVnfFlavourData implements InterfaceInformationElement {

	private String vnfInstanceId;
	private String newFlavourId;
	private String instantiationLevelId;
	private List<ExtVirtualLinkData> extVirtualLink = new ArrayList<>();
	private List<ExtManagedVirtualLinkData> extManagedVirtualLink = new ArrayList<>();
	private Map<String, String> additionalParam = new HashMap<>();
	
	public ChangeVnfFlavourData() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance to be modified.
	 * @param newFlavourId Identifier of the new VNF DF to apply to this VNF instance.
	 * @param instantiationLevelId Identifier of the instantiation level of the DF to be used. If not present, the default instantiation level as declared in the VNFD shall be used.
	 * @param extVirtualLink Information about external VLs to connect the VNF to.
	 * @param extManagedVirtualLink Information about internal VLs that are managed by other entities than the VNFM.
	 * @param additionalParam Additional parameters passed by the NFVO as input to the flavour change process, specific to the VNF being modified.
	 */
	public ChangeVnfFlavourData(String vnfInstanceId,
			String newFlavourId,
			String instantiationLevelId,
			List<ExtVirtualLinkData> extVirtualLink,
			List<ExtManagedVirtualLinkData> extManagedVirtualLink,
			Map<String, String> additionalParam) {
		this.vnfInstanceId = vnfInstanceId;
		this.newFlavourId = newFlavourId;
		this.instantiationLevelId = instantiationLevelId;
		if (extVirtualLink != null) this.extVirtualLink = extVirtualLink;
		if (extManagedVirtualLink != null) this.extManagedVirtualLink = extManagedVirtualLink;
		if (additionalParam != null) this.additionalParam = additionalParam;
	}
	

	/**
	 * @return the vnfInstanceId
	 */
	public String getVnfInstanceId() {
		return vnfInstanceId;
	}



	/**
	 * @return the newFlavourId
	 */
	public String getNewFlavourId() {
		return newFlavourId;
	}



	/**
	 * @return the instantiationLevelId
	 */
	public String getInstantiationLevelId() {
		return instantiationLevelId;
	}



	/**
	 * @return the extVirtualLink
	 */
	public List<ExtVirtualLinkData> getExtVirtualLink() {
		return extVirtualLink;
	}



	/**
	 * @return the extManagedVirtualLink
	 */
	public List<ExtManagedVirtualLinkData> getExtManagedVirtualLink() {
		return extManagedVirtualLink;
	}



	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Change VNF flavour data without VNF instance ID");
		if (newFlavourId == null) throw new MalformattedElementException("Change VNF flavour data without new flavour ID");
		if (extVirtualLink != null) {
			for (ExtVirtualLinkData vl: extVirtualLink) vl.isValid();
		}
		if (extManagedVirtualLink != null) {
			for (ExtManagedVirtualLinkData vl: extManagedVirtualLink) vl.isValid();
		}
	}

}
