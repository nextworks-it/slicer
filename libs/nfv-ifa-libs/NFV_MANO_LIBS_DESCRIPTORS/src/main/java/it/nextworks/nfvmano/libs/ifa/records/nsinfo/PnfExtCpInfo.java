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
package it.nextworks.nfvmano.libs.ifa.records.nsinfo;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element provides information about the external CP of the PNF.
 * Ref. IFA 013 v2.3.1 section 8.3.3.14
 * 
 * @author nextworks
 *
 */
@Embeddable
public class PnfExtCpInfo implements DescriptorInformationElement {

	private String cpdId;
	private String address;
	
	public PnfExtCpInfo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Construction
	 * 
	 * @param cpdId Identifier of (reference to) the Connection Point Descriptor (CPD) for this CP.
	 * @param address Address for this CP.
	 */
	public PnfExtCpInfo(String cpdId, String address) {
		this.cpdId = cpdId;
		this.address = address;
	}
	
	
	
	/**
	 * @return the cpdId
	 */
	public String getCpdId() {
		return cpdId;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.cpdId == null) throw new MalformattedElementException("PNF ext cp info without CP ID");
		if (this.address == null) throw new MalformattedElementException("PNF ext cp info without address");
	}

}
