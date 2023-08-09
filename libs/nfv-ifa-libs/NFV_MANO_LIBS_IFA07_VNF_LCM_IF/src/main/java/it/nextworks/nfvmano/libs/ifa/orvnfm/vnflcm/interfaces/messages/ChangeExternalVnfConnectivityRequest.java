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
import it.nextworks.nfvmano.libs.ifa.common.elements.ExtVirtualLinkData;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.VimConnectionInfo;

/**
 * This operation enables changing the external connectivity of a VNF instance. 
 * The types of changes that this operation supports are:
 * • Disconnect the external CPs that are connected to a particular external VL, 
 * and connect them to a different external VL.
 * • Change the connectivity parameters of the existing external CPs, 
 * including changing addresses.
 * 
 * To change the connection of external CP instances based on certain external CPDs 
 * from a "source" external VL to a different "target" external VL, 
 * the identifier of the "target" external VL shall be sent in the "extVirtualLinkId" 
 * attribute of the "extVirtualLink" parameter, and the "extCp" attributes of that 
 * parameter shall refer via the "cpdId" attribute to the external CPDs of the 
 * corresponding external connection point instances that are to be reconnected 
 * to the target external VL.
 * 
 * To change the connectivity parameters of the external CPs connected to a particular 
 * external VL, including changing addresses, the identifier of that external VL shall 
 * be sent in the "extVirtualLinkId" attribute of the "extVirtualLink" parameter, 
 * and the "extCp" attribute of that parameter shall contain at least those entries 
 * with modified parameters.
 * 
 * REF IFA 007 v2.3.1 - 7.2.18
 * 
 * @author nextworks
 *
 */
public class ChangeExternalVnfConnectivityRequest implements InterfaceMessage {

	private String vnfInstanceId;
	private List<ExtVirtualLinkData> extVirtualLink = new ArrayList<>();
	private Map<String, String> additionalParam = new HashMap<>();
	private List<VimConnectionInfo> vimConnectionInfo = new ArrayList<>();
	
	public ChangeExternalVnfConnectivityRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnfInstanceId Identifier of the VNF instance.
	 * @param extVirtualLink Information about external VLs to change
	 * @param additionalParam Additional parameters passed by the NFVO as input to the Change External VNF Connectivity operation, specific to the VNF of which the external VLs are being changed as declared in the VNFD
	 * @param vimConnectionInfo Information about VIM connection(s) for managing resources for the VNF instance, or 	external virtual links.
	 */
	public ChangeExternalVnfConnectivityRequest(String vnfInstanceId,
			List<ExtVirtualLinkData> extVirtualLink,
			Map<String,String> additionalParam,
			List<VimConnectionInfo> vimConnectionInfo) {
		this.vnfInstanceId = vnfInstanceId;
		if (extVirtualLink != null) this.extVirtualLink = extVirtualLink;
		if (additionalParam != null) this.additionalParam = additionalParam;
		if (vimConnectionInfo != null) this.vimConnectionInfo = vimConnectionInfo;
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

	/**
	 * @return the vimConnectionInfo
	 */
	public List<VimConnectionInfo> getVimConnectionInfo() {
		return vimConnectionInfo;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnfInstanceId == null) throw new MalformattedElementException("Change external VNF connectivity request without VNF instance ID");
		if ((extVirtualLink == null) || (extVirtualLink.isEmpty())) throw new MalformattedElementException("Change external VNF connectivity request without external VL data");
		else for (ExtVirtualLinkData vl : extVirtualLink) vl.isValid();
		for (VimConnectionInfo vci : vimConnectionInfo) vci.isValid();
	}

}
