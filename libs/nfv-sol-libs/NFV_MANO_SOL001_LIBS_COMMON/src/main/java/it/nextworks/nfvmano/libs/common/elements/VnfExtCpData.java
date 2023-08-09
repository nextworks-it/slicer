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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * This information element provides input information related to an external
 * CP.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.14 REF IFA 007 v2.3.1 - 8.12.3
 * 
 * @author nextworks
 *
 */
public class VnfExtCpData implements InterfaceInformationElement {

	private String cpdId;
	private List<String> fixedAddresses = new ArrayList<>();
	private Map<String, String> dynamicAddresses = new HashMap<>();

	public VnfExtCpData() {
	}

	/**
	 * Constructor
	 * 
	 * @param cpdId            Identifier of the CPD in the VNFD.
	 * @param fixedAddresses   List of (fixed) network addresses that need to be
	 *                         configured on the CP. It shall be provided for
	 *                         configuring fixed addresses.
	 * @param dynamicAddresses List of parameter sets for the assignment of dynamic
	 *                         addresses. It should be possible to define per
	 *                         parameter set the number of network addresses to be
	 *                         assigned dynamically. Other parameters could be, e.g.
	 *                         valid address ranges or subnets.
	 */
	public VnfExtCpData(String cpdId, List<String> fixedAddresses, Map<String, String> dynamicAddresses) {
		this.cpdId = cpdId;
		if (fixedAddresses != null)
			this.fixedAddresses = fixedAddresses;
		if (dynamicAddresses != null)
			this.dynamicAddresses = dynamicAddresses;
	}

	/**
	 * @return the cpdId
	 */
	public String getCpdId() {
		return cpdId;
	}

	/**
	 * @return the fixedAddresses
	 */
	public List<String> getFixedAddresses() {
		return fixedAddresses;
	}

	/**
	 * @return the dynamicAddresses
	 */
	public Map<String, String> getDynamicAddresses() {
		return dynamicAddresses;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (cpdId == null)
			throw new MalformattedElementException("VNF external CP data without CPD ID");
	}

}
