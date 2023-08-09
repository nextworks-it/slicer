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
import it.nextworks.nfvmano.libs.ifa.common.elements.ExtManagedVirtualLinkData;
import it.nextworks.nfvmano.libs.ifa.common.elements.ExtVirtualLinkData;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VimConnectionInfo;

/**
 * Request to change a VNF flavour
 * 
 * REF IFA 007 v2.3.1 - 7.2.6
 * 
 * @author nextworks
 *
 */
public class ChangeVnfFlavourRequest implements InterfaceMessage {

	private String vnfInstanceId;
	private String newFlavourId;
	private String instantiationLevelId;
	private List<ExtVirtualLinkData> extVirtualLink = new ArrayList<>();
	private List<ExtManagedVirtualLinkData> extManagedVirtualLink = new ArrayList<>();
	private List<VimConnectionInfo> vimConnectionInfo = new ArrayList<>();
	private Map<String, String> additionalParam = new HashMap<>();
	
	public ChangeVnfFlavourRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance to be modified.
	 * @param newFlavourId Identifier of the new VNF DF to apply to this VNF instance.
	 * @param instantiationLevelId Identifier of the instantiation level of the DF to be used. If not present, the default instantiation level as declared in the VNFD shall be used.
	 * @param extVirtualLink Information about external VLs to connect the VNF to.
	 * @param extManagedVirtualLink Information about internal VLs that are managed by other entities than the VNFM
	 * @param vimConnectionInfo Information about VIM connection(s) for managing resources for the VNF instance, or external/externally-managed virtual links.
	 * @param additionalParam Additional parameters passed by the NFVO as input to the flavour change process, specific to the VNF being modified.
	 */
	public ChangeVnfFlavourRequest(String vnfInstanceId,
			String newFlavourId,
			String instantiationLevelId,
			List<ExtVirtualLinkData> extVirtualLink,
			List<ExtManagedVirtualLinkData> extManagedVirtualLink,
			List<VimConnectionInfo> vimConnectionInfo,
			Map<String, String> additionalParam) {
		this.vnfInstanceId = vnfInstanceId;
		this.newFlavourId = newFlavourId;
		this.instantiationLevelId = instantiationLevelId;
		if (extVirtualLink != null) this.extVirtualLink = extVirtualLink;
		if (extManagedVirtualLink != null) this.extManagedVirtualLink = extManagedVirtualLink;
		if (vimConnectionInfo != null) this.vimConnectionInfo = vimConnectionInfo;
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
	 * @return the vimConnectionInfo
	 */
	public List<VimConnectionInfo> getVimConnectionInfo() {
		return vimConnectionInfo;
	}

	/**
	 * @return the additionalParam
	 */
	public Map<String, String> getAdditionalParam() {
		return additionalParam;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Change VNF scale request without VNF instance ID");
		if (newFlavourId == null) throw new MalformattedElementException("Change VNF scale request without new flavour ID");
		for (ExtVirtualLinkData vl : extVirtualLink) vl.isValid();
		for (ExtManagedVirtualLinkData vl : extManagedVirtualLink) vl.isValid();
		for (VimConnectionInfo vci : vimConnectionInfo) vci.isValid();
	}

}
