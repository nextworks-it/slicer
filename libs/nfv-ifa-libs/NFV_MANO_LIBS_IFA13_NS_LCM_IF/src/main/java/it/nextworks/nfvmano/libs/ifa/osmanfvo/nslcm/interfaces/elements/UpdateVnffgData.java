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
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element specifies the parameters needed 
 * for the update of an existing VNFFG instance.
 *
 * REF IFA 013 v2.3.1 - 8.3.4.22
 * 
 * @author nextworks
 *
 */
public class UpdateVnffgData implements InterfaceInformationElement {

	private String vnffgId;
	private List<NfpData> nfp = new ArrayList<>();
	private List<String> nfpId = new ArrayList<>();
	
	
	public UpdateVnffgData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param vnffgId Identifier of an existing VNFFG information element to be updated for the NS Instance.
	 * @param nfp Indicate the desired new NFP(s) for a given VNFFG after the operations of addition/removal of NS components (e.g. VNFs, VLs, etc.) have been completed, or indicate the updated or newly created NFP classification and selection rule which applied to an existing NFP.
	 * @param nfpId Identifier(s) of the NFP to be deleted from a given VNFFG
	 */
	public UpdateVnffgData(String vnffgId,
			List<NfpData> nfp,
			List<String> nfpId) {
		this.vnffgId = vnffgId;
		if (nfp != null) this.nfp = nfp;
		if (nfpId != null) this.nfpId = nfpId;
	}
	
	

	/**
	 * @return the vnffgId
	 */
	public String getVnffgId() {
		return vnffgId;
	}

	/**
	 * @return the nfp
	 */
	public List<NfpData> getNfp() {
		return nfp;
	}

	/**
	 * @return the nfpId
	 */
	public List<String> getNfpId() {
		return nfpId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vnffgId == null) throw new MalformattedElementException("Update VNFFG data without VNFFG ID");

	}

}
