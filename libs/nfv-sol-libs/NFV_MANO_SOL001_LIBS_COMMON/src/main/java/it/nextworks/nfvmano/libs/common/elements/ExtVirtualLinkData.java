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
package it.nextworks.nfvmano.libs.common.elements;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * This information element provides the information of an external VL to be
 * used as a parameter passed to NS lifecycle management interface.
 * 
 * REF IFA 013 v2.1.1 - 8.3.4.13 REF IFA 007 v2.1.1 - 8.12.2
 * 
 * @author nextworks
 *
 */
public class ExtVirtualLinkData implements InterfaceInformationElement {

	private String extVirtualLinkId;
	private String vimId;
	private String resourceProviderId;
	private String resourceId;
	private List<VnfExtCpData> extCp = new ArrayList<>();

	/**
	 * Construction
	 * 
	 * @param extVirtualLinkId   Identifier of this external VL instance, if
	 *                           provided.
	 * @param vimId              Identifier of the VIM that manages this resource.
	 *                           This attribute shall be supported and present if
	 *                           VNF-related resource management in direct mode is
	 *                           applicable.
	 * @param resourceProviderId Identifies the entity responsible for the
	 *                           management of the resource. This attribute shall be
	 *                           supported and present when VNF-related Resource
	 *                           Management in indirect mode is applicable.
	 * @param resourceId         Identifier of the resource in the scope of the VIM
	 *                           or the resource provider.
	 * @param extCp              External CPs of the VNF to be connected to this VL.
	 */
	public ExtVirtualLinkData(String extVirtualLinkId, String vimId, String resourceProviderId, String resourceId,
			List<VnfExtCpData> extCp) {
		this.extVirtualLinkId = extVirtualLinkId;
		this.vimId = vimId;
		this.resourceId = resourceId;
		this.resourceProviderId = resourceProviderId;
		if (extCp != null)
			this.extCp = extCp;
	}

	public ExtVirtualLinkData() {
	}

	/**
	 * @return the extVirtualLinkId
	 */
	public String getExtVirtualLinkId() {
		return extVirtualLinkId;
	}

	/**
	 * @return the vimId
	 */
	@JsonProperty("vimConnectionId")
	public String getVimId() {
		return vimId;
	}

	/**
	 * @return the resourceProviderId
	 */
	public String getResourceProviderId() {
		return resourceProviderId;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @return the extCp
	 */
	public List<VnfExtCpData> getExtCp() {
		return extCp;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceId == null)
			throw new MalformattedElementException("External VL data without resource ID");
		if ((extCp == null) || (extCp.isEmpty())) {
			throw new MalformattedElementException("External VL data without external CPs");
		} else {
			for (VnfExtCpData cp : extCp)
				cp.isValid();
		}
	}

}
