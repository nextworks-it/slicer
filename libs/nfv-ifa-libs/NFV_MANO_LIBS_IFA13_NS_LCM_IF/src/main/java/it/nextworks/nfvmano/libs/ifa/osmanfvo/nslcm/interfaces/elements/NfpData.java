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
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.Rule;

/**
 * This information element contains information needed
 * to create or modify an NFP instance.
 * 
 *  REF IFA 013 v2.3.1 - 8.3.4.23
 * 
 * @author nextworks
 *
 */
public class NfpData implements InterfaceInformationElement {

	private String nfpId;
	private String nfpName;
	private String description;
	private List<String> cp = new ArrayList<>();
	private Rule nfpRule;
	
	public NfpData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param nfpId Identifier of the NFP to be modified
	 * @param nfpName Human readable name for the NFP
	 * @param description Human readable description for the NFP
	 * @param cp Identifier(s) of the CPs and SAPs which the NFP passes by.
	 * @param nfpRule NFP classification and selection rule
	 */
	public NfpData(String nfpId,
			String nfpName,
			String description,
			List<String> cp,
			Rule nfpRule) {
		this.nfpId = nfpId;
		this.nfpName = nfpName;
		this.description = description;
		this.nfpRule = nfpRule;
		if (cp != null) this.cp = cp;
	}
	
	

	/**
	 * @return the nfpId
	 */
	public String getNfpId() {
		return nfpId;
	}

	/**
	 * @return the nfpName
	 */
	public String getNfpName() {
		return nfpName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the cp
	 */
	public List<String> getCp() {
		return cp;
	}

	/**
	 * @return the nfpRule
	 */
	public Rule getNfpRule() {
		return nfpRule;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nfpRule != null) nfpRule.isValid();
	}

}
