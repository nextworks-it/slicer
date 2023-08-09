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
 * The InstantiateVnfData information element specifies the parameters 
 * that are needed for VNF instantiation. This information element is 
 * used for the bottom-up NS creation when the OSS/BSS explicitly requests 
 * VNF instantiation for a given NS.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.12
 * 
 * @author nextworks
 *
 */
public class InstantiateVnfData implements InterfaceInformationElement {

	private String vnfdId;
	private String flavourId;
	private String instantiationLevelId;
	private String vnfInstanceName;
	private String vnfInstanceDescription;
	private List<ExtVirtualLinkData> extVirtualLink = new ArrayList<>();
	private List<ExtManagedVirtualLinkData> extManagedVirtualLink = new ArrayList<>();
	private String localizationLanguage;
	private Map<String, String> additionalParam = new HashMap<String, String>();
	private VnfLocationConstraints locationConstraint;
	
	public InstantiateVnfData() {	}

	/**
	 * Constructor
	 * 
	 * @param vnfdId Information sufficient to identify the VNFD which defines the VNF to be instantiated.
	 * @param flavourId Identifier of the VNF DF to be instantiated.
	 * @param instantiationLevelId Identifier of the instantiation level of the DF to be instantiated. If not present, the default instantiation level as declared in the VNFD shall be instantiated.
	 * @param vnfInstanceName Human-readable name of the VNF instance to be created.
	 * @param vnfInstanceDescription Human-readable description of the VNF instance to be created.
	 * @param extVirtualLink Information about external VLs to connect the VNF to.
	 * @param extManagedVirtualLink Information about internal VLs that are managed by other entities than the VNFM.
	 * @param localizationLanguage Localization language of the VNF to be instantiated.
	 * @param additionalParam Additional parameters passed by the NFVO as input to the instantiation process, specific to the VNF being instantiated.
	 * @param locationConstraint Defines the location constraints for the VNF to be instantiated as part of the NS instantiation.
	 */
	public InstantiateVnfData(String vnfdId,
			String flavourId,
			String instantiationLevelId,
			String vnfInstanceName,
			String vnfInstanceDescription,
			List<ExtVirtualLinkData> extVirtualLink,
			List<ExtManagedVirtualLinkData> extManagedVirtualLink,
			String localizationLanguage,
			Map<String, String> additionalParam,
			VnfLocationConstraints locationConstraint) {
		this.vnfdId = vnfdId;
		this.flavourId = flavourId;
		this.instantiationLevelId = instantiationLevelId;
		this.vnfInstanceName = vnfInstanceName;
		this.vnfInstanceDescription = vnfInstanceDescription;
		if (extManagedVirtualLink != null) this.extManagedVirtualLink = extManagedVirtualLink;
		if (extVirtualLink != null) this.extVirtualLink = extVirtualLink;
		this.localizationLanguage = localizationLanguage;
		if (additionalParam != null) this.additionalParam = additionalParam;
		this.locationConstraint = locationConstraint;
	}

	/**
	 * @return the vnfdId
	 */
	public String getVnfdId() {
		return vnfdId;
	}



	/**
	 * @return the flavourId
	 */
	public String getFlavourId() {
		return flavourId;
	}



	/**
	 * @return the instantiationLevelId
	 */
	public String getInstantiationLevelId() {
		return instantiationLevelId;
	}



	/**
	 * @return the vnfInstanceName
	 */
	public String getVnfInstanceName() {
		return vnfInstanceName;
	}



	/**
	 * @return the vnfInstanceDescription
	 */
	public String getVnfInstanceDescription() {
		return vnfInstanceDescription;
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
	 * @return the localizationLanguage
	 */
	public String getLocalizationLanguage() {
		return localizationLanguage;
	}



	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}



	/**
	 * @return the locationConstraint
	 */
	public VnfLocationConstraints getLocationConstraint() {
		return locationConstraint;
	}



	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfdId ==null) throw new MalformattedElementException("Instantiate VNF data without VNFD ID");
		if (flavourId ==null) throw new MalformattedElementException("Instantiate VNF data without flavour ID");
		if (extVirtualLink != null) {
			for (ExtVirtualLinkData vl : extVirtualLink) vl.isValid();
		}
		if (extManagedVirtualLink != null) {
			for (ExtManagedVirtualLinkData vl : extManagedVirtualLink) vl.isValid();
		}
		if (locationConstraint != null) locationConstraint.isValid();
	}

}
